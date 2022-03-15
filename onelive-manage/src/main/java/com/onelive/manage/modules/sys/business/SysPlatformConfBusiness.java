package com.onelive.manage.modules.sys.business;

import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysBargainingChipConfigListReq;
import com.onelive.common.model.req.sys.SysBargainingChipConfigReq;
import com.onelive.common.model.req.sys.SysBargainingChipConfigStatusReq;
import com.onelive.common.model.vo.sys.SysBargainingChipConfigListVO;
import com.onelive.common.mybatis.entity.SysBargainingChipConfig;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.sys.SysBargainingChipConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SysPlatformConfBusiness {

    @Resource
    private SysBargainingChipConfigService sysBargainingChipConfigService;

    public void saveBargainingChipConfig(SysBargainingChipConfigListReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        String accLogin = loginUser.getAccLogin();

        List<SysBargainingChipConfigReq> list = req.getList();
        if(list ==null || list.size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        List<SysBargainingChipConfig> addList = new LinkedList<>();
        List<SysBargainingChipConfig> updateList = new LinkedList<>();

        //判断数据
        for(SysBargainingChipConfigReq item:list){
           //检查组参数是否为空
           checkParamIsNull(item);
           //检查参数是否合法
           checkParam(item);
            SysBargainingChipConfig config = new SysBargainingChipConfig();
            BeanCopyUtil.copyProperties(item,config);
           //检查是否有ID,有ID的更新,没有ID的新增
            if(item.getId()==null){
                config.setCreateUser(accLogin);
                addList.add(config);
            }else{
                config.setUpdateUser(accLogin);
                updateList.add(config);
            }
        }

        //新增或更新数据
        if(addList.size()>0){
            MysqlMethod.batchInsert(SysBargainingChipConfig.class,addList);
        }

        if(updateList.size()>0){
            MysqlMethod.batchUpdate(SysBargainingChipConfig.class,updateList);
        }

    }

    /**
     * 检查参数合理性
     * @param item
     */
    private void checkParam(SysBargainingChipConfigReq item) {
        BigDecimal b1 = item.getBargainingChip1();
        BigDecimal b2 = item.getBargainingChip2();
        BigDecimal b3 = item.getBargainingChip3();
        BigDecimal b4 = item.getBargainingChip4();
        BigDecimal b5 = item.getBargainingChip5();
        BigDecimal b6 = item.getBargainingChip6();
        BigDecimal b7 = item.getBargainingChip7();
        BigDecimal cmin = item.getCustomizeMin();
        BigDecimal cmax = item.getCustomizeMax();
        BigDecimal bmin = item.getBargainingChipMin();
        BigDecimal bmax = item.getBargainingChipMax();

        if(b1.doubleValue()<0       || b2.doubleValue()<0       || b3.doubleValue()<0
           || b4.doubleValue()<0    || b5.doubleValue()<0       || b6.doubleValue()<0
           || b7.doubleValue()<0    || cmin.doubleValue()<=0    || cmax.doubleValue()<=0
           || bmin.doubleValue()<=0 || bmax.doubleValue()<=0){
            throw new BusinessException("筹码值不能小于0");
        }

        List<BigDecimal> list = new LinkedList<>();
        if(b1.doubleValue()>0){
            list.add(b1);
        }
        if(b2.doubleValue()>0){
            list.add(b2);
        }
        if(b3.doubleValue()>0){
            list.add(b3);
        }
        if(b4.doubleValue()>0){
            list.add(b4);
        }
        if(b5.doubleValue()>0){
            list.add(b5);
        }
        if(b6.doubleValue()>0){
            list.add(b6);
        }
        if(b7.doubleValue()>0){
            list.add(b7);
        }

        //如果没有配置筹码, 抛出异常
        if(list.size()==0){
            throw new BusinessException("至少配置一个筹码值");
        }

        //后面的筹码要比前面的筹码大
        if(list.size()>1){
            for(int i=list.size()-1;i>0;i--){
                BigDecimal current = list.get(i);
                BigDecimal pre = list.get(i - 1);
                if(current.compareTo(pre)!=1){
                    throw new BusinessException("后面的筹码值必须比前面的筹码值大");
                }
            }
        }

        //最小下注金额必须跟筹码1相同, 且不能为0
        if(bmin.compareTo(b1)!=0 || bmin.doubleValue()==0){
            throw new BusinessException("最小下注筹码值必须跟筹码1相同,且不能为0");
        }

        //自定义最小筹码不能小于筹码1,且不能为0
        if(cmin.compareTo(b1)==-1 || cmin.doubleValue()==0){
            throw new BusinessException("自定义最小筹码不能小于筹码1,且不能为0");
        }

        //自定义最大筹码值和最大下注筹码值不能小于最小筹码值
        if(cmax.compareTo(cmin)==-1 || bmax.compareTo(bmin)==-1){
            throw new BusinessException("自定义最大筹码值不能小于自定义最小筹码值, 最大下注筹码值不能小于最小下注筹码值");
        }

    }

    /**
     * 判断参数是否为空
     */
    private void checkParamIsNull(SysBargainingChipConfigReq item) {
        if(item.getBargainingChip1()==null      || item.getBargainingChip2()==null
        || item.getBargainingChip3()==null      || item.getBargainingChip4()== null
        || item.getBargainingChip5()==null      || item.getBargainingChip6()==null
        || item.getBargainingChip7()==null
        || item.getShowUnit()==null             || item.getCountryCode()==null
        || item.getCustomizeMax()==null         || item.getCustomizeMin()==null
        || item.getBargainingChipMin()==null    || item.getBargainingChipMax()==null){
            throw new BusinessException("筹码参数不能为空");
        }
    }


    /**
     * 查询筹码配置列表
     * @return
     */
    public List<SysBargainingChipConfigListVO> getBargainingChipConfigList() {

        List<SysBargainingChipConfig> list = sysBargainingChipConfigService.list();

        List<SysBargainingChipConfigListVO> voList = BeanCopyUtil.copyCollection(list, SysBargainingChipConfigListVO.class);

        return voList;
    }

    /**
     * 切换配置项状态
     * @param req
     * @param loginUser
     */
    public void switchBargainingChipConfigStatus(SysBargainingChipConfigStatusReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        String accLogin = loginUser.getAccLogin();

        Boolean status = req.getStatus();
        if(req.getId()==null || status==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }


        //查询数据库中的数据
        SysBargainingChipConfig dbConfig = sysBargainingChipConfigService.getById(req.getId());
        if(dbConfig==null){
            throw new BusinessException("找不到数据!");
        }
        dbConfig.setStatus(status);
        sysBargainingChipConfigService.updateById(dbConfig);
    }
}
