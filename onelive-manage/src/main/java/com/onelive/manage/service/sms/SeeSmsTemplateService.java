package com.onelive.manage.service.sms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sms.SmsTemplateQueryReq;
import com.onelive.common.mybatis.entity.SeeSmsTemplate;

/**
 * <p>
 * 短信模板表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-21
 */
public interface SeeSmsTemplateService extends IService<SeeSmsTemplate> {

    PageInfo<SeeSmsTemplate> getList(SmsTemplateQueryReq smsTemplateQueryReq);
}
