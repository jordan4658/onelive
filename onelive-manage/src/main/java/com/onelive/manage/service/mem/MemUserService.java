package com.onelive.manage.service.mem;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.dto.mem.UserGroupCountDTO;
import com.onelive.common.model.req.mem.MemUserListReq;
import com.onelive.common.model.req.mem.MemUserUpdateFrozenReq;
import com.onelive.common.model.req.report.UserReportReq;
import com.onelive.common.model.vo.mem.MemUserListVO;
import com.onelive.common.model.vo.report.UserReportVO;
import com.onelive.common.mybatis.entity.MemUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
public interface MemUserService extends IService<MemUser> {

    /**
     * 查询账号是否存在
     *
     * @param account
     * @return
     */
    Boolean isExistByAccount(String account);

    /**
     * 保存主播用户表信息
     * @param dto
     * @return
     */
    MemUser saveUserAnchor(UserAnchorDTO dto) throws Exception;

    /**
     * 查询用户列表
     * @param req
     * @return
     */
    PageInfo<MemUserListVO> getList(MemUserListReq req);

    /**
     * 批量更新用户封停/解封状态
     */
    void updateFrozenUsers(MemUserUpdateFrozenReq req);

    /**
     * 通过账号获取用户信息
     *
     * @param account
     * @return
     */
    MemUser queryByAccount(String account);

    /**
     * 批量更新用户返点状态
     * @param userIds
     * @param isCommission
     */
    void updateCommissionUsers(ArrayList<Long> userIds, Boolean isCommission);
    
    /**
     * 用户风控
    * <b>Description:</b><br> 
    * @param startDate
    * @param endDate
    * @param countryId
    * @param equip
    * @param userAccount
    * @param ip
    * @param pageNum
    * @param pageSize
    * @return
    * @Note
     */
    public PageInfo<MemUser> getRiskList(String startDate, String endDate, Long countryId, String equip, String userAccount, String ip,Integer pageNum, Integer pageSize);

    /**
     * 根据用户层级IDs查询对应的用户数量
     * @param groupIds
     * @return
     */
    List<UserGroupCountDTO> getByUserGroupId(List<Long> groupIds,Long currencyId);

    /**
     * 获取会员报表
     * @param req
     * @return
     */
    PageInfo<UserReportVO> queryUserReport(UserReportReq req);
    
	/**
	 * 根据用户id查询用表基本信息
	 * @param userId
	 * @return
	 */
	MemUser queryById(Long userId);
}
