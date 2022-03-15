package com.onelive.manage.common.queue;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.model.dto.sys.CountryAddrParseDTO;
import com.onelive.common.model.dto.sys.CreateCountryAddrDTO;
import com.onelive.common.mybatis.entity.SysCountryAddr;
import com.onelive.common.utils.others.JacksonUtil;
import com.onelive.manage.common.config.RabbitConfig;
import com.onelive.manage.service.sys.SysCountryAddrService;
import com.onelive.manage.utils.http.HttpUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @ClassName : MQConsumer
 * @Description : 消息队列消费者
 */
@Component
@Slf4j
public class MQConsumer {

    @Resource
    private SysCountryAddrService sysCountryAddrService;

    /**
     * google地址数据API
     */
    private static final String url = "https://chromium-i18n.appspot.com/ssl-address/data";



    /**
     * @description: 生成国家地址
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_country", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.COUNTRY_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"*.country"}
    ), containerFactory = "customContainerFactory")
    @Transactional
    public void createCountryAddr(CreateCountryAddrDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("生成国家地址处理，msg:{}", JSONObject.toJSONString(dto));
        try {
            if (dto != null && StrUtil.isNotBlank(dto.getLang())) {
                String lang = dto.getLang();
                String[] arr = lang.split("_");
                String key = arr[1];
                log.info("=============================== 删除旧信息 ! ======================================");
                //添加数据前先清除原有数据, 防止重复
                QueryWrapper<SysCountryAddr> queryWrapper=new QueryWrapper<>();
                queryWrapper.lambda().eq(SysCountryAddr::getLang,lang);
                sysCountryAddrService.remove(queryWrapper);
                log.info("=============================== 读取新信息 ! ======================================");
                //采集数据
                readData(0L,0,lang,url,key);
            }
            channel.basicAck(tag, false);
            log.info("=============================== 生成国家地址信息 成功! ======================================");
        } catch (Exception e) {
            log.error("生成国家地址信息异常", e);
        }
    }


/*    public static void main(String[] args) throws Exception {
       //String url2 = "https://www.gstatic.com/chrome/autofill/libaddressinput/chromium-i18n/ssl-address/data/";
        String lang = "ms_MY";
        String[] arr = lang.split("_");
        String key = arr[1];
        readData(0L,lang,url,key);
    }*/


    public void readData(Long parentId,int level, String lang, String parentUrl,String addr) throws IOException {
        if(level>2){
            return;
        }
        String url = parentUrl+ "/" + URLEncoder.createDefault().encode(addr, Charset.defaultCharset());
        System.out.println(url);
        String json = HttpUtils.doGet(url);
        CountryAddrParseDTO dto = JacksonUtil.fromJson(json, CountryAddrParseDTO.class);
        SysCountryAddr countryAddr = new SysCountryAddr();
        countryAddr.setPid(parentId);
        countryAddr.setLang(lang);
        String name = dto.getName();
        //如果没有名称, 使用key值
        if(StrUtil.isBlank(name)){
            countryAddr.setName(dto.getKey());
        }else {
            //汉语地区使用中文名称
            if ("CHINA".equals(name)) {
                countryAddr.setName("中国");
            } else if ("TAIWAN".equals(name)) {
                countryAddr.setName("中国台湾");
            } else if ("HONG KONG".equals(name)) {
                countryAddr.setName("中国香港");
            } else {
                countryAddr.setName(name);
            }
        }
        sysCountryAddrService.save(countryAddr);
        Long id = countryAddr.getId();
        String sub_keys = dto.getSub_keys();
        if(StrUtil.isNotBlank(sub_keys)){
            String[] addrs = sub_keys.split("~");
            for(String subAddr:addrs){
                readData(id, level+1, lang,url,subAddr);
            }
        }
    }


}
