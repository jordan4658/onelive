package com.onelive.pay.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.onelive.pay.common.interceptor.SystemInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.*;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author eddy
 * @version 1.0
 * @date 2020-7-2 16:15
 */
@Configuration
@EnableWebMvc
public class WebAppConfig implements WebMvcConfigurer {
    @Resource
    private SystemInterceptor systemInterceptor;
    @Value("${fl.secretKey}")
    private String secretKey;


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //converters.removeIf(converter ->converter.getClass() ==MappingJackson2HttpMessageConverter.class );
    }



    /**
     * 国际化
     *
     * @return
     */
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setBasenames("i18n/messages", "i18n/ValidationMessages");
        resourceBundleMessageSource.setCacheSeconds(3600);
        return resourceBundleMessageSource;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(systemInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        // swagger2
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ByteArrayHttpMessageConverter byteConverter = new ByteArrayHttpMessageConverter();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        ResourceHttpMessageConverter resourceHttpConverter = new ResourceHttpMessageConverter();
        ResourceRegionHttpMessageConverter resourceRegionConverter = new ResourceRegionHttpMessageConverter();
        SourceHttpMessageConverter sourceConverter = new SourceHttpMessageConverter();
        AllEncompassingFormHttpMessageConverter allEncompassingConverter = new AllEncompassingFormHttpMessageConverter();

//        MappingJackson2XmlHttpMessageConverter mappingXmlConverter = new MappingJackson2XmlHttpMessageConverter();
//        MappingJackson2CborHttpMessageConverter mappingConverter = new MappingJackson2CborHttpMessageConverter();
        TextJavascriptMessageConverter jackson2HttpMessageConverter = new TextJavascriptMessageConverter(secretKey);

//        //ObjectMapper objectMapper = new ObjectMapper();
//        // 设置Date类型字段序列化方式
//        //objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE));
//        /**
//         * 序列换成json时,将所有的long变成string
//         * 因为js中得数字类型不能包含所有的java long值
//         */
////        SimpleModule simpleModule = new SimpleModule();
////        simpleModule.addSerializer(Float.class, ToStringSerializer.instance);
////        simpleModule.addSerializer(Float.TYPE, ToStringSerializer.instance);
////        simpleModule.addSerializer(Double.class, ToStringSerializer.instance);
////        simpleModule.addSerializer(Double.TYPE, ToStringSerializer.instance);
////        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
////        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
////        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
////        objectMapper.registerModule(simpleModule);
////        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//
        //converters.add(aa);
        converters.add(byteConverter);
        converters.add(stringConverter);
        converters.add(resourceHttpConverter);
        converters.add(resourceRegionConverter);
        converters.add(sourceConverter);
        converters.add(allEncompassingConverter);
        //converters.add(mappingXmlConverter);
        //converters.add(mappingConverter);
        converters.add(jackson2HttpMessageConverter);
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFeatures(
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty);
        converters.add(converter);

    }


}
