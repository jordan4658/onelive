package com.onelive.api.modules.sys.business;

import com.onelive.api.service.sys.SysBusParameterService;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.common.SelectBankStringVO;
import com.onelive.common.mybatis.entity.SysBusParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: SysBusParameterBusiness
 * @date 创建时间：2021/4/6 15:34
 */
@Slf4j
@Component
public class SysBusParameterBusiness {

    @Resource
    private SysBusParameterService sysBusParameterService;

    /**
     * 获取银行卡下拉列表
     *
     * @param code
     * @return
     */
    public List<SelectBankStringVO> getByBankList(String code) {
        if (StringUtils.isBlank(code)) {
            throw new BusinessException("业务参数父代码为空！");
        }
        List<SelectBankStringVO> listVo = new ArrayList<>();
        List<SysBusParameter> list = sysBusParameterService.getByParentCode(code);
        for (SysBusParameter sysBusParameter : list) {
            SelectBankStringVO vo = new SelectBankStringVO();
            vo.setValue(sysBusParameter.getParamCode());
            vo.setDes(sysBusParameter.getParamValue());
            vo.setLogo(sysBusParameter.getRemark());
            listVo.add(vo);
        }
        return listVo;
    }

}
