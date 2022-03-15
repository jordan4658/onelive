package com.onelive.api.common.config;

import com.onelive.common.constants.other.HeaderConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author aguang
 * @Description 请放在controller包父级（不能超出启动类范围）
 * @Date 18:42 2021/3/13
 * @Param
 * @return
 **/
@Configuration
@EnableSwagger2
@Profile({"default", "dev", "sit"}) //设置只在 dev和test配置文件启动下生效
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    public Docket createRestApi() {
        Boolean isEnable = false;
        if (enableEnv().contains(env)) {
            isEnable = true;
        }
        //token值
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder ticketPar1 = new ParameterBuilder();
        ParameterBuilder ticketPar2 = new ParameterBuilder();
        ticketPar1.name(HeaderConstants.AUTHORIZATION).description("登录令牌（登录后所有接口必需这个token）")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的Authorization参数非必填，传空也可以
        ticketPar2.name(HeaderConstants.ONELIVESOURCE).description("对应的来源 ios、android、pc")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("ios")
                .required(true).build();
        pars.add(ticketPar1.build());
        pars.add(ticketPar2.build());

        ParameterBuilder ticketPar3 = new ParameterBuilder();
        ParameterBuilder ticketPar4 = new ParameterBuilder();
        ParameterBuilder ticketPar5 = new ParameterBuilder();
        ParameterBuilder ticketPar6 = new ParameterBuilder();
        ParameterBuilder ticketPar7 = new ParameterBuilder();
        ParameterBuilder ticketPar8 = new ParameterBuilder();
        ParameterBuilder ticketPar9 = new ParameterBuilder();
        ParameterBuilder ticketPar10 = new ParameterBuilder();
        ParameterBuilder ticketPar11 = new ParameterBuilder();

        ticketPar3.name(HeaderConstants.ONELIVERANDOM).description("请求随机数，每次请求都不一样")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();
        ticketPar4.name(HeaderConstants.ONELIVETIMESTAMP).description("获取当前时间戳（精确到毫秒）")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();
        ticketPar5.name(HeaderConstants.ONELIVEURL).description("请求url最后一个斜杆后面的字符串（例如：请求url=http:localhost:8080/api/game/app/v1/queryBalance?name=sdfs&id=232，那么获取的值就是：queryBalance?name=sdfs&id=232）")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();
        ticketPar6.name(HeaderConstants.ONELIVESIGNATURE).description("签名字符串（根据签名规则 onelive-timestamp||onelive-random||Authorization||onelive-url 进行匹配，一定按照这个顺序拼接进行加密")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();

        ticketPar7.name(HeaderConstants.ONELIVEDEVICES).description("获取手机设备型号")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();

        ticketPar8.name(HeaderConstants.ONELIVEDEVICEID).description("手机设备唯一标识")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();

        ticketPar9.name(HeaderConstants.ONELIVEAPPLETYPE).description("苹果加密类型（秘钥顺序的号 填写1-5数字，参考 参数onelive-signature的说明 ）")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(true).build();

        ticketPar10.name(HeaderConstants.Lang).description("语言标识,vi_VN,en_US")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("zh_CN")
                .required(true).build();

        ticketPar11.name(HeaderConstants.isTest).description("调试，不走加密")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
                .required(false).build();



        pars.add(ticketPar3.build());
        pars.add(ticketPar4.build());
        pars.add(ticketPar5.build());
        pars.add(ticketPar6.build());
        pars.add(ticketPar7.build());
        pars.add(ticketPar8.build());
        pars.add(ticketPar9.build());
        pars.add(ticketPar10.build());
        pars.add(ticketPar11.build());

//        //如果是开发环境，则不需要显示，方便测试接口
//        if (!env.equals(EnvEnum.dev.name())) {
//            //验签值
//
//            ParameterBuilder ticketPar3 = new ParameterBuilder();
//            ParameterBuilder ticketPar4 = new ParameterBuilder();
//            ParameterBuilder ticketPar5 = new ParameterBuilder();
//            ParameterBuilder ticketPar6 = new ParameterBuilder();
//
//            ticketPar3.name(HeaderConstants.ONELIVERANDOM).description("请求随机数，每次请求都不一样")
//                    .modelRef(new ModelRef("string")).parameterType("header")
//                    .required(true).build();
//            ticketPar4.name(HeaderConstants.ONELIVETIMESTAMP).description("获取当前时间戳（精确到毫秒）")
//                    .modelRef(new ModelRef("string")).parameterType("header")
//                    .required(true).build();
//            ticketPar5.name(HeaderConstants.ONELIVEURL).description("请求url的最后字符串")
//                    .modelRef(new ModelRef("string")).parameterType("header")
//                    .required(true).build();
//            ticketPar6.name(HeaderConstants.ONELIVESIGNATURE).description("签名字符串")
//                    .modelRef(new ModelRef("string")).parameterType("header")
//                    .required(true).build();
//            pars.add(ticketPar3.build());
//            pars.add(ticketPar4.build());
//            pars.add(ticketPar5.build());
//            pars.add(ticketPar6.build());
//        }

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.onelive"))  //设置扫描 swagger的注解包路径 所有的需要用swagger显示的
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars).enable(isEnable);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ONELIVE-移动端")
                .description("RESTful APIs")
                .version("1.0")
                .build();
    }

    private List<String> enableEnv() {
        List<String> envList = new ArrayList<>();
        envList.add("dev");
        envList.add("sit");
        envList.add("uat");
        return envList;
    }
}

