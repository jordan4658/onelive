/**
 *
 */
package com.onelive.manage.utils.other;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName StringToDateConverter
 * @Desc 字符转日期格式转换类
 * @Date 2021/3/15 10:17
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final String datemFormat = "yyyy-MM-dd HH:mm";
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();
        try {
            if (source.contains("-")) {
                SimpleDateFormat formatter;
                int i = source.length() - source.replace(":", "").length();
                if (source.contains(":") && i > 1) {
                    formatter = new SimpleDateFormat(dateFormat);
                } else if (source.contains(":")) {
                    formatter = new SimpleDateFormat(datemFormat);
                } else {
                    formatter = new SimpleDateFormat(shortDateFormat);
                }
                Date dtDate = formatter.parse(source);
                return dtDate;
            } else if (source.matches("^\\d+$")) {
                Long lDate = new Long(source);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", source));
    }


}
