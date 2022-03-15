package com.onelive.common.mybatis.mapper.slave.mem;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.mem.UserGroupCountDTO;
import com.onelive.common.model.req.mem.MemUserListReq;
import com.onelive.common.model.req.mem.MemUserUpdateFrozenReq;
import com.onelive.common.model.req.report.UserReportReq;
import com.onelive.common.model.vo.live.LiveAnchorInfoVO;
import com.onelive.common.model.vo.mem.MemUserAnchorSearchListVO;
import com.onelive.common.model.vo.mem.MemUserListVO;
import com.onelive.common.model.vo.report.UserReportVO;
import com.onelive.common.mybatis.entity.MemUser;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
public interface SlaveMemUserMapper extends BaseMapper<MemUser> {

    List<MemUserListVO> getList(MemUserListReq req);

    void updateFrozenUsers(MemUserUpdateFrozenReq req);

    void updateCommissionUsers(ArrayList<Long> ids, Boolean isCommission);

    /**
	 * 	多条件搜索主播/用户信息
	 * 		模糊匹配:匹配accno,用户昵称,直播间标题  精确匹配：房间号， userid，房间号
	 * @return
	 */
    List<MemUserAnchorSearchListVO> conditionSearch(String context, String merchantCode);

    /**
     * 查询主播信息
     *
     * @param anchorUserId 主播的用户ID
     * @param userId       查询的用户ID
     * @return
     */
    LiveAnchorInfoVO getAnchorInfoById(@Param("anchorUserId") Long anchorUserId, @Param("userId") Long userId);

    List<UserGroupCountDTO> getByUserGroupId(@Param("list") List<Long> groupIds, @Param("currencyId") Long currencyId);

    List<UserReportVO> queryUserReport(UserReportReq req);

}
