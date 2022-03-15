package com.onelive.common.model.dto.agent;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AgentInviteRecordDto implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 	邀请码所属用户ID,0表示系统(没有邀请码)
     */
    private Long inviteUserId;
    
    /**
     * 	下级用户的投注额
     */
    private BigDecimal betAmount;
    
    
	/**
	 * 	是否主播
	 */
	private Boolean isAuthor;
    

}
