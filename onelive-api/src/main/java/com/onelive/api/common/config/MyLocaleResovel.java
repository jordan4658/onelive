package com.onelive.api.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@Configuration
public class MyLocaleResovel implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {

        String l = httpServletRequest.getParameter("lang");
        String header = httpServletRequest.getHeader("lang");
        Locale locale = new Locale("zh", "CN");
        if (!StringUtils.isEmpty(l)) {
            String[] split = l.split("_");
            locale = new Locale(split[0], split[1]);
        } else if (!StringUtils.isEmpty(header)) {
            header = header.replaceAll("\"", "");
            String[] split = header.split("_");
            if (split.length > 1) {
                locale = new Locale(split[0], split[1]);
            }
        }
        return locale;
    }


    @Override
    public void setLocale(HttpServletRequest httpServletRequest, @Nullable HttpServletResponse
            httpServletResponse, @Nullable Locale locale) {
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResovel();
    }

}

 
