package com.onelive.api.modules.sys.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.mem.PayExchangeCurrencyService;
import com.onelive.api.service.sys.*;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.enums.ClientType;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.platformConfig.CustomerSericeLangDto;
import com.onelive.common.model.req.login.CheckImgReq;
import com.onelive.common.model.req.login.CheckSmsReq;
import com.onelive.common.model.req.login.SmsReq;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.sys.CountryVO;
import com.onelive.common.model.vo.sys.PublicConfigVO;
import com.onelive.common.model.vo.sys.SysAppVersionVO;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.redis.SysBusinessRedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysBusiness
 * @Desc 系统基础信息类
 * @Date 2021/3/23 14:12
 */
@Component
public class SysBusiness {

    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private SysAppVersionService sysAppVersionService;
    @Resource
    private LocaleMessageSourceService localeMessageSourceService;
    @Resource
    private SysShortMsgService sysShortMsgService;
    @Resource
    private SysBusParameterService sysBusParameterService;
    @Resource
    private SysParameterService sysParameterService;
    @Resource
    private PayExchangeCurrencyService payExchangeCurrencyService;


    /**
     * 获取各个国家的语言切换
     * @return
     */
    public List<SelectStringVO> queryAllLanguage(){
        List<SysCountry> list = sysCountryService.getAllAvailableCountry();
        List<SelectStringVO> returnList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            returnList =  list.stream().map(a->{
                SelectStringVO vo = new SelectStringVO();
                vo.setValue(a.getLang());
                vo.setMsg(localeMessageSourceService.getMessage(a.getLang()));
                return vo;
            }).collect(Collectors.toList());
        }
        return returnList;
    }

    /**
     * 获取所有地区列表
     * @return
     */
    public List<CountryVO> queryAllCountry(){
        List<SysCountry> list = sysCountryService.getAllAvailableCountry();
        List<CountryVO> returnList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            returnList =  list.stream().map(a->{
                CountryVO vo = new CountryVO();
                vo.setCountryCode(a.getCountryCode());
                vo.setAreaCode(a.getAreaCode());
                vo.setName(localeMessageSourceService.getMessage(a.getCountryCode()));
                vo.setLang(a.getLang());
                return vo;
            }).collect(Collectors.toList());
        }
        return returnList;
    }



    /**
     * 发送短信
     *
     * @param req
     * @return
     */
    public Long sendSmsCode(SmsReq req) {
        if (req.getSendType() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR.getCode(), "发送失败");
        }
        if (StringUtils.isBlank(req.getMobilePhone())) {
            throw new BusinessException(StatusCode.SMS_PHONE_EMPTY);
        }
        return sysShortMsgService.sendSmsCode(req);
    }

    /**
     * 验证手机验证码
     *
     * @param req
     * @return
     */
    public void checkSmsCode(CheckSmsReq req) throws Exception{
        sysShortMsgService.checkSmsCode(req.getMobilePhone(), req.getAreaCode(),req.getSmsCode(), req.getSendType());
    }

    /**
     * 验证图片验证码
     * @param req
     * @throws Exception
     */
    public void checkImgCode(CheckImgReq req) throws Exception{
        //验证图片验证码是否正确
        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(req.getCaptchaKey());
        if(!req.getImgCode().equalsIgnoreCase(trueImgCode)){
            throw new BusinessException(StatusCode.IMG_CODE_ERROR);
        }
    }

    /**
     * 验证图片验证码
     * @param imgCode
     * @param captchaKey
     * @throws Exception
     */
    public void checkImgCodeTow(String imgCode , String captchaKey) throws Exception{
        //验证图片验证码是否正确
        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(captchaKey);
        if(!imgCode.equalsIgnoreCase(trueImgCode)){
            throw new BusinessException(StatusCode.IMG_CODE_ERROR);
        }
    }



    /**
     * 查询App版本
     * @return
     */
    public SysAppVersionVO queryAppVersion() {
        String code = LoginInfoUtil.getSource().toLowerCase();
        QueryWrapper<SysAppVersion> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysAppVersion::getCode,code).eq(SysAppVersion::getAppType, ClientType.USER_CLIENT.getType()).last("limit 1");
        SysAppVersion appVersion = sysAppVersionService.getBaseMapper().selectOne(queryWrapper);
        SysAppVersionVO vo = new SysAppVersionVO();
        if(appVersion!=null) {
            BeanCopyUtil.copyProperties(appVersion, vo);
        }
        return vo;
    }


    /**
     * 查询国家区号列表
     * @return
     */
    public List<SysCountryAreaCodeVO> getAreaCodeList() {
        return sysCountryService.getAreaCodeList();
    }


    /**
     * 获取平台币单位
     * @return
     */
    public PublicConfigVO getPublicConfig() {
        String countryCode = LoginInfoUtil.getCountryCode();
        if(StringUtils.isBlank(countryCode)){
            countryCode = LangConstants.LANG_VN;
        }
        PublicConfigVO vo = new PublicConfigVO();
        //平台币单位
        SysBusParameter goldUnitParam = sysBusParameterService.getByCode(PayConstants.PLATFORM_GOLD_UNIT);
        if(goldUnitParam!=null){
            vo.setGoldUnit(goldUnitParam.getParamValue());
        }
        //银币单位
        SysBusParameter silverUnitParam = sysBusParameterService.getByCode(PayConstants.PLATFORM_SILVER_UNIT);
        if(silverUnitParam!=null){
            vo.setSilverBeanUnit(silverUnitParam.getParamValue());
        }

        //当前语言的客服
        SysParameter onlineServiceParam = sysParameterService.getByCode(SysParameterConstants.CUSTOMER_SERVICE);
        String paramValue = onlineServiceParam.getParamValue();
        if (StringUtils.isNotBlank(paramValue)) {
            List<CustomerSericeLangDto> customerSericeLangDtoList = JSON.parseArray(paramValue,
                    CustomerSericeLangDto.class);
            List<CustomerSericeLangDto> list = customerSericeLangDtoList.stream()
                    .filter(t -> LoginInfoUtil.getLang().equals(t.getLang())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(list)){
                list = customerSericeLangDtoList.stream().filter(t->LangConstants.LANG_CN.equals(t.getLang())).collect(Collectors.toList());
            }
            if (CollectionUtil.isNotEmpty(list)) {
                CustomerSericeLangDto dto = list.get(0);
                vo.setOnlineServiceUrl(dto.getContext());
            }
        }

        //查询汇率
        vo.setCountryCode(countryCode);
        SysCountry country = SysBusinessRedisUtils.getCountryInfo(countryCode);
        if(country!=null) {
            PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(country.getLocalCurrency());
            if(exchangeCurrency!=null){
               BeanCopyUtil.copyProperties(exchangeCurrency,vo);
            }
        }
        return vo;
    }
}
    