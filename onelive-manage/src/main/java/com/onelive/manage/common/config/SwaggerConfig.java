package com.onelive.manage.common.config;

import com.onelive.common.constants.other.HeaderConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
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
@Profile({"default", "dev", "sit","local"}) //设置只在 dev和test配置文件启动下生效
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String env;
    @Autowired
    private ServletContext servletContext;


    @Bean
    public Docket createRestApi() {
        Boolean isEnable = false;
        if (enableEnv().contains(env)) {
            isEnable = true;
        }

        ParameterBuilder ticketPar1 = new ParameterBuilder();
        ParameterBuilder ticketPar2 = new ParameterBuilder();
        ParameterBuilder ticketPar3 = new ParameterBuilder();
        ParameterBuilder ticketPar4 = new ParameterBuilder();
        ParameterBuilder ticketPar5 = new ParameterBuilder();
        ParameterBuilder ticketPar6 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar1.name(HeaderConstants.AUTHORIZATION).description("登录令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的Authorization参数非必填，传空也可以
        pars.add(ticketPar1.build());

        if ("dev".equals(env)) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.onelive"))  //设置扫描 swagger的注解包路径 所有的需要用swagger显示的
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                    .build().globalOperationParameters(pars);
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.onelive"))  //设置扫描 swagger的注解包路径 所有的需要用swagger显示的
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                    .build().globalOperationParameters(pars)
                    .pathProvider(new RelativePathProvider(servletContext) {
                        @Override
                        public String getApplicationBasePath() {
                            return "/manage";
                        }
                    }).enable(isEnable);
        }


    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("onelive-后台管理")
                .description("RESTful APIs")
                .version("1.0")
                .build();
    }

    private List<String> enableEnv() {
        List<String> envList = new ArrayList<>();
        envList.add("dev");
        envList.add("sit");
        envList.add("local");
        envList.add("uat");
        return envList;
    }
}

