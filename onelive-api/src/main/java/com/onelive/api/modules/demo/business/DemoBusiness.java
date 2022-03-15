package com.onelive.api.modules.demo.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onelive.api.service.demo.TestUserService;
import com.onelive.common.model.vo.demo.TestUserVO;
import com.onelive.common.mybatis.entity.TestUser;
import com.onelive.common.utils.redis.RedisUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @ClassName DemoBusiness
 * @Desc demo对外接口业务类，只服务于controller层，禁止bussines类互相调用，形成相互依赖
 * @Date 2021/3/19 19:33
 */
@Component
@Slf4j
public class DemoBusiness {


    @Resource
    private TestUserService testUserService;



    // redis存实体 --- redis取实体 例子
    public void redisToEntity() {
        //1、redis存实体
        TestUserVO vo = new TestUserVO();
        vo.setAge(123);
        RedisUtil.set("demo", JSON.toJSONString(vo));
        //2、redis取实体
        TestUserVO testUserVO = JSONObject.parseObject((String) RedisUtil.get("demo"), TestUserVO.class);
        log.info(JSONObject.toJSONString(testUserVO));
    }


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<TestUser> getSlave(Integer pageNum, Integer pageSize) {
        PageInfo<TestUser> pageInfo = testUserService.getSlave(pageNum, pageSize);
        return pageInfo;
    }

    public void insert_master(TestUser user) {
        testUserService.insert_master(user);
    }



    @Transactional
    public void testTransaction() {
        TestUser testUser = new TestUser();
        testUser.setName("vvdsf");
        testUser.setAge(1122);
        testUserService.insert_master(testUser);

        testUser = new TestUser();
        testUser.setName("5r5r");
        testUser.setAge(66);
        //testUserService.insert_slave(testUser);
        testUserService.getById(12);
    }
}
    