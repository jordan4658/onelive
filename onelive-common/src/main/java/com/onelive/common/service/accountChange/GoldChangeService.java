package com.onelive.common.service.accountChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.mybatis.entity.MemGoldchange;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员金额变动明细 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
public interface GoldChangeService extends IService<MemGoldchange> {

    Integer insetGoldChange(MemGoldchange memGoldchange);


    List<MemGoldchangeVO> rechargeRecord(Date startDate, Date endDate, String account);
}
