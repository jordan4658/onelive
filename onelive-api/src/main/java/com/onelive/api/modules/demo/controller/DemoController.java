package com.onelive.api.modules.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.onelive.common.utils.redis.RedisUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.onelive.api.modules.demo.business.DemoBusiness;
import com.onelive.api.service.demo.TestUserService;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.annotation.AopEncryption;
import com.onelive.common.annotation.SupperAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.client.DemoClient;
import com.onelive.common.client.PayClientFeignClient;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.demo.TestUserReq;
import com.onelive.common.model.vo.demo.TestUserVO;
import com.onelive.common.mybatis.entity.TestUser;
import com.onelive.common.mybatis.mapper.master.demo.TestUsersMapper;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.AesUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DemoController
 * @Desc demo
 * @Author 森林
 * @Date 2021/3/13 19:56
 */
@RestController
@RequestMapping(value = "/demo")
@Api(tags = "demo-api")
@Slf4j
//@ApiIgnore
public class DemoController extends BaseController {

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private DemoBusiness demoBusiness;
    @Resource
    private DemoClient demoClient;
    @Resource
    private PayClientFeignClient payClientFeignClient;
    @Resource
    private TestUserService testUserService;
    @Resource
    private LocaleMessageSourceService localeMessageSourceService;


    @ApiOperation("Redis=====Test===get======")
    @PostMapping(value = "/pc/v1/getExpire")
    @AllowAccess
    public ResultInfo getExpire() {
        long time = RedisUtil.getExpire("seven_test");
        return ResultInfo.ok(time);
    }

    @ApiOperation("Redis=====Test===set======")
    @PostMapping(value = "/pc/v1/setExpire")
    @AllowAccess
    public ResultInfo setExpire() {
        RedisUtil.set("seven_test", "sssss");
        return ResultInfo.ok();
    }

    @ApiOperation("测试内部调用feign头部传参")
    @PostMapping(value = "/pc/v1/testFeignHeaderParameter")
    @AllowAccess
    public ResultInfo testFeignHeaderParameter() {
        ResultInfo resultInfo = payClientFeignClient.testFeign("====this is feign header parameter=====");
        return ResultInfo.ok();
    }


    @ApiOperation("多语言国际化")
    @PostMapping(value = "/pc/v1/languageDemo")
    @AllowAccess
    @AopEncryption(paramType = {TestUserReq.class})
    public ResultInfo langDemo(@RequestBody(required = false) TestUserReq user) {
        log.info("user---: " + JSON.toJSONString(user));
        // return ResultInfo.ok(localeMessageSourceService.getMessage("test"));
        // return ResultInfo.getInstance(StatusCode.BUSSINESS_ERROR);
        //return ResultInfo.ok(localeMessageSourceService.getMessage("test"));

//       try {
//           Integer pp = null;
//           pp.toString();
//       }catch (Exception e){
//           throw new BusinessException(StatusCode.IP_ERROR);
//       }
//       return null;
        return ResultInfo.ok(localeMessageSourceService.getMessage("test"));


        // return JSON.toJSONString(ResultInfo.ok(localeMessageSourceService.getMessage("test")));
    }


    @ApiOperation("pc端调用")
    @GetMapping(value = "/pc/v1/info")
    @SupperAccess
    public ResultInfo getPcDemo() {
        //业务层面判断参数
        //..............
//            demoBusiness.getDemoPageList(null);
        return ResultInfo.ok("pc");

    }

    @ApiOperation("app端调用")
    @GetMapping(value = "/app/v1/info")
    public ResultInfo getAppDemo() {
        //业务层面判断参数
        //..............
        return ResultInfo.ok("app");
    }

    @ApiOperation("pc、app两端共同调用")
    @RequestMapping(value = {"/pc/v2/info", "/app/v2/info"}, method = RequestMethod.GET)
    public ResultInfo getAllDemo() {

        //业务层面判断参数
        //..............
        return ResultInfo.ok("all");

    }


    @ApiOperation(value = "异步执行demo")
    @GetMapping(value = "/async")
    public ResultInfo<String> async() {
        ResultInfo<String> resultData = new ResultInfo<>();
        taskExecutor.execute(() -> {
            try {
//                Thread t = Thread.currentThread();
//                String name = t.getName();
                Thread.sleep(3000);
//                System.err.println("--------已执行----------"+name);
            } catch (Exception e) {
                log.error("{}.async,出错:{}", getClass().getName(), e.getMessage(), e);
            }

        });
        return resultData;
    }


    @ApiOperation(value = "操作redis实体demo")
    @GetMapping(value = "/redis")
    public ResultInfo<String> redis() {
        //业务层面判断参数
        demoBusiness.redisToEntity();
        return ResultInfo.ok();
    }

    @ApiOperation(value = "feign的get调用")
    @GetMapping(value = "/feignGet")
    @SupperAccess
    public ResultInfo<String> feignGet() {
        ResultInfo<TestUserVO> dd = demoClient.getDemo("123", 2);
        return ResultInfo.ok();
    }

    @ApiOperation(value = "feign的post调用")
    @GetMapping(value = "/feignPost")
    @SupperAccess
    public ResultInfo<String> feignPost() {
        TestUserReq req = new TestUserReq();
        req.setAge(12333);
        ResultInfo<String> dd = demoClient.insertDemo(req);
        return ResultInfo.ok();
    }


    @ApiOperation("测试多数据源-读库slave1-查询数据")
    @GetMapping(value = "/getSlave")
    @AllowAccess
    public ResultInfo<PageInfo<TestUser>> getSlave(@ApiParam("条数") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                   @ApiParam("页数") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        return ResultInfo.ok(demoBusiness.getSlave(pageNum, pageSize));
    }

    @ApiOperation("测试多数据源-读库master-新增数据")
    @PostMapping(value = "/insert_master")
    @AllowAccess
    public ResultInfo insert_master(@ApiParam("测试新增 master主库 test_user 表的数据") @RequestBody TestUser user) {
        demoBusiness.insert_master(user);
        return ResultInfo.ok();
    }


    @ApiOperation("事务控制测试")
    @GetMapping("/testTransaction")
    @SupperAccess
    public ResultInfo testTransaction() {
        demoBusiness.testTransaction();
        return ResultInfo.ok();
    }

    @ApiOperation("测试批量插入或者更新方法")
    @GetMapping("/testBatch")
    @SupperAccess
    public ResultInfo testBatch() {
        //因多数据源下，service方法的批量更新和插入会调用异常，所以选择自己封装批量插入和更新
        List<TestUser> list = new ArrayList<>();

        Integer flag = 1;

        //批量插入
        if (flag == 0) {
            TestUser kk = new TestUser();
            kk.setName("1");
            kk.setAge(12);
            list.add(kk);
            TestUser aa = new TestUser();
            aa.setName("测试21");
            aa.setAge(21);
            list.add(aa);
            MysqlMethod.batchInsert(TestUser.class, list);
        }

        //批量更新
        if (flag == 1) {
            TestUser kk = new TestUser();
            kk.setName("测试123");
            kk.setAge(66);
            kk.setId(248);
            list.add(kk);
            TestUser aa = new TestUser();
            aa.setName("测试21");
            aa.setAge(77);
            aa.setId(249);
            list.add(aa);
            MysqlMethod.batchUpdate(TestUser.class, list);
        }

        //动态指定批量处理的方法
        if (flag == 2) {
            MysqlMethod.batchStatment(list, TestUsersMapper.class, (item, mapper) -> mapper.insert(item));
        }


        return ResultInfo.ok();
    }


    public static void main(String[] args) {
//        TestUserReq req = new TestUserReq();
//        req.setAge(32);
//        req.setUserId("123");
//       String before = AesUtil.aesEncrypt("1637056509493||8405||||getColumn","c1kgVioySoUVimtw");
//       System.out.println(before);
//
        String after2 = AesUtil.aesDecrypt("RsfbCvIvXXnNhZEtgg49SxiCRCICheigoH5TXhzWjl+ZtwbIsAsL2LKeeuh54yNb9+sxIPPXiYyA/wGSYQ6i/gwIUgebDrdRGdTCchOoINs5BpOL57L9cn2bHql1Plyry6llJcjJHVIj9RPIbQQ0OzYu+u2jec6FKEzrO+Pq7H94cSIeqKvnW0kafCS4TgXPYaORWhii/tx4WqSJNwKtf0MverZBhcYSIvQnxvpCpqa8OpZTIYaB8byQFAtakVDyl9m/JsTo887ZK7+mY4jDtHKWsPK9fEN4BFFNv4+ihn/oRbeMBeztgq2B5XTjqLuWQxSQBuFKCBdijdWjz/4CJGsu3kfv47WOzWlEygpAnrgGDIVbPru2mrYGhLkmSOqw", "f2kgVioykoURWmtg");
        System.out.println(after2);

//        StringBuilder beforeSign = new StringBuilder();
//        beforeSign.append("1636975100193");
//        beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
//        beforeSign.append("123213");
//        beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
//        beforeSign.append("");
//        beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
//        beforeSign.append("languageDemo");
//        String sign = AesUtil.aesEncrypt(beforeSign.toString(), "e3kgGioydoURWmtf");
//        System.out.println("----sign----:"+sign);

        //+8D1cRhEFaHNYsSmhCh/bw==
//                String after = AesUtil.aesDecrypt("+8D1cRhEFaHNYsSmhCh/bw==","c1kgVioySoUVimtw");
//        System.out.println(after);

//        String msg = "123";
//        Map<String,Object> map = new HashMap<>();
//        map.put("msg",msg);

//        ActAuditReq user = new ActAuditReq();
//        user.setAuditAttach("asd");
//        //user.setAuditType(1);
//        String after = AesUtil.aesEncrypt(JSON.toJSONString(user),"c1kgVioySoUVimtw");
//        System.out.println(after);
//
//
//        String after2 = AesUtil.aesDecrypt("YK7aRVY2aTqoblycMQ5qsQ==","c1kgVioySoUVimtw");
//        System.out.println(after2);

//        // IP V4
//        String ip = "117.20.112.122";
//        // IP V6 也是可以的
//        DatabaseReader reader = null;
//        CityResponse response = null;
//        //CountryResponse response2 = null;
//        try {
//            File database = new File("D:\\soft\\GeoLite2-City_20211005\\GeoLite2-City.mmdb");
//            // 读取数据库内容
//            reader = new DatabaseReader.Builder(database).build();
//            InetAddress ipAddress = InetAddress.getByName(ip);
//            // 获取查询结果
//            response = reader.city(ipAddress);
//
//           // response2 = reader.country(ipAddress);
//            System.out.println(response.getCountry().getNames().get("en"));
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//            }
//
//        }

    }
}
