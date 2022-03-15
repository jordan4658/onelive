package com.onelive.api.service.mem;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.req.live.LiveIndexSearchReq;
import com.onelive.common.model.vo.live.LiveAnchorInfoVO;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.mem.MemUserAnchorSearchListVO;
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
     * 通过账号获取用户信息
     *
     * @param account
     * @return
     */
    MemUser queryByAccount(String account);

    /**
     * 通过用户唯一标识获取用户信息
     * @param accno
     * @return
     */
    MemUser queryByAccno(String accno);


    /**
     * 保存主播用户表信息
     * @param dto
     * @return
     */
    MemUser saveUserAnchor(UserAnchorDTO dto) throws Exception;
    
	/**
	 * 	多条件搜索主播/用户信息
	 * 		模糊匹配:匹配accno,用户昵称,直播间标题  精确匹配：房间号， userid，房间号
	 * @return
	 */
    List<MemUserAnchorSearchListVO> search(LiveIndexSearchReq req);

	LiveUserDetailVO getUserDetailInfo(Long userId);

    /**
     * 查询主播信息
     * @param userId
     * @return
     */
    LiveAnchorInfoVO getAnchorInfoById(Long userId);
    
    /**
     * 		根据用户id查询信息，用户列表展示，不需要详细数据
     * @param userId
     * @return
     */
    LiveUserDetailVO getUserDetailForList(Long userId);
    
    /**
     * 	根据userType查询用户
     * 
     * @param userAccount
     * @param merchantCode
     * @param userType
     * @return
     */
    MemUser queryByAccount(String userAccount, String merchantCode, Integer userType);

	/**
	 * 根据用户id查询用表基本信息
	 * @param userId
	 * @return
	 */
	MemUser queryById(Long userId);
}
