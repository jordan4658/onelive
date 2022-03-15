package com.onelive.anchor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
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
