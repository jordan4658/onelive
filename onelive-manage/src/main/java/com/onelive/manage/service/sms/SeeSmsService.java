package com.onelive.manage.service.sms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sms.SmsQueryReq;
import com.onelive.common.mybatis.entity.SeeSms;

/**
 * <p>
 * 短信方式表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-21
 */
public interface SeeSmsService extends IService<SeeSms> {

    PageInfo<SeeSms> getList(SmsQueryReq smsQueryReq);
}
