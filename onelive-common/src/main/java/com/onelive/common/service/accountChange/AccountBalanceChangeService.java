package com.onelive.common.service.accountChange;


import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import org.springframework.transaction.annotation.Transactional;

public interface AccountBalanceChangeService {

    /**
     * 金币、银豆 账变
     * @param changeVO
     * @return
     */
    @Transactional
    BalanceChangeDTO publicAccountBalanceChange(MemAccountChangeVO changeVO);


}
