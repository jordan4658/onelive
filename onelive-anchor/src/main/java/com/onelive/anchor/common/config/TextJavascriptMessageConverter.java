package com.onelive.anchor.common.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.onelive.common.constants.business.CommonConstants;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.AesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.TypeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TextJavascriptMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Map<String, JsonEncoding> ENCODINGS = new HashMap(JsonEncoding.values().length + 1);

    private String key;
    @Nullable
    private PrettyPrinter ssePrettyPrinter;

    public TextJavascriptMessageConverter() {
        super();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentObjectsWith(new DefaultIndenter("  ", "\ndata:"));
        this.ssePrettyPrinter = prettyPrinter;
    }

    public TextJavascriptMessageConverter(String key) {
        super();
        this.key = key;
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        JavaType javaType = this.getJavaType(type, contextClass);
        return this.readJavaType(javaType, inputMessage);
    }

    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) throws IOException {
        MediaType contentType = inputMessage.getHeaders().getContentType();
        String secretKey = getSecretKey(inputMessage.getHeaders().getFirst(HeaderConstants.ONELIVEAPPLETYPE));
//        logger.info("数据解析秘钥=====："+secretKey);
        Charset charset = getCharset(contentType);
        boolean isUnicode = ENCODINGS.containsKey(charset.name());
        try {
            if (inputMessage instanceof MappingJacksonInputMessage) {
                Class<?> deserializationView = ((MappingJacksonInputMessage) inputMessage).getDeserializationView();
                if (deserializationView != null) {
                    ObjectReader objectReader = this.objectMapper.readerWithView(deserializationView).forType(javaType);
                    if (isUnicode) {
                        if (CommonConstants.OPEN_SECRET && !LoginInfoUtil.getIsTest() && !LoginInfoUtil.getIsFeign() && StringUtils.isNotBlank(secretKey)) {
                            logger.info("===================进入数据解密流程===================");
                            return objectReader.readValue(getBody(inputMessage, charset));
                        } else {
                            logger.info("===================进入数据===不解密流程===================");
                            return objectReader.readValue(inputMessage.getBody());
                        }

                    }

                    if (CommonConstants.OPEN_SECRET && !LoginInfoUtil.getIsTest() && !LoginInfoUtil.getIsFeign() && StringUtils.isNotBlank(secretKey)) {
                        return objectReader.readValue(getBody(inputMessage, charset));
                    } else {
                        Reader reader = new InputStreamReader(inputMessage.getBody(), charset);
                        return objectReader.readValue(reader);
                    }

                }
            }

            if (CommonConstants.OPEN_SECRET && !LoginInfoUtil.getIsTest() && !LoginInfoUtil.getIsFeign()&& StringUtils.isNotBlank(secretKey)) {
                String body = getBody(inputMessage, charset);
                if (StringUtils.isBlank(body)) this.objectMapper.readValue("", javaType);
                return this.objectMapper.readValue(body.getBytes(), javaType);

            } else {
                if (isUnicode) {
                    return this.objectMapper.readValue(inputMessage.getBody(), javaType);
                } else {
                    Reader reader = new InputStreamReader(inputMessage.getBody(), charset);
                    return this.objectMapper.readValue(reader, javaType);
                }
            }

        } catch (InvalidDefinitionException var9) {
            throw new HttpMessageConversionException("Type definition error: " + var9.getType(), var9);
        } catch (JsonProcessingException var10) {
            throw new HttpMessageNotReadableException("JSON parse error: " + var10.getOriginalMessage(), var10, inputMessage);
        }
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType contentType = outputMessage.getHeaders().getContentType();
        JsonEncoding encoding = this.getJsonEncoding(contentType);
        JsonGenerator generator = this.objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);

        try {
            this.writePrefix(generator, object);
            Object value = object;
            Class<?> serializationView = null;
            FilterProvider filters = null;
            JavaType javaType = null;
            if (object instanceof MappingJacksonValue) {
                MappingJacksonValue container = (MappingJacksonValue) object;
                value = container.getValue();
                serializationView = container.getSerializationView();
                filters = container.getFilters();
            }

            if (type != null && TypeUtils.isAssignable(type, value.getClass())) {
                javaType = this.getJavaType(type, (Class) null);
            }

            ObjectWriter objectWriter = serializationView != null ? this.objectMapper.writerWithView(serializationView) : this.objectMapper.writer();
            if (filters != null) {
                objectWriter = objectWriter.with(filters);
            }

            if (javaType != null && javaType.isContainerType()) {
                objectWriter = objectWriter.forType(javaType);
            }

            SerializationConfig config = objectWriter.getConfig();
            if (contentType != null && contentType.isCompatibleWith(MediaType.TEXT_EVENT_STREAM) && config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
                objectWriter = objectWriter.with(this.ssePrettyPrinter);
            }

            String secretKey = LoginInfoUtil.getSecretKey();
            Boolean isTest = LoginInfoUtil.getIsTest();
            Boolean isFeign = LoginInfoUtil.getIsFeign();
//            logger.info("输出时候，secretKey："+secretKey+",isTest:"+isTest);
            if (CommonConstants.OPEN_SECRET && StringUtils.isNotBlank(secretKey) && !isTest && !isFeign && StringUtils.isNotBlank(secretKey)) {
                String result = AesUtil.aesEncrypt(JSON.toJSONString(object), secretKey);
                objectWriter.writeValue(generator, result);
                this.writeSuffix(generator, result);
            } else {
                objectWriter.writeValue(generator, value);
                this.writeSuffix(generator, object);
                generator.flush();
            }

            generator.flush();
        } catch (InvalidDefinitionException var13) {
            throw new HttpMessageConversionException("Type definition error: " + var13.getType(), var13);
        } catch (JsonProcessingException var14) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var14.getOriginalMessage(), var14);
        } finally {
            LoginInfoUtil.remove();
        }
    }

    private static Charset getCharset(@Nullable MediaType contentType) {
        return contentType != null && contentType.getCharset() != null ? contentType.getCharset() : StandardCharsets.UTF_8;
    }


    private String getBody(HttpInputMessage inputMessage, Charset charset) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            if (inputMessage.getBody() != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputMessage.getBody()));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                if (StringUtils.isNotBlank(sb.toString())) {
                    String secretKey = getSecretKey(inputMessage.getHeaders().getFirst(HeaderConstants.ONELIVEAPPLETYPE));
//                    logger.info("---secretKey----:"+secretKey);
                    String result = AesUtil.aesDecrypt(sb.toString(), secretKey);
                    return result;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    /**
     * 获取秘钥key值,先写死，后读取配置文件 TODO
     *
     * @param key
     * @return
     */
    private String getSecretKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        List<String> keyList = new ArrayList<>();
        keyList.add("c1kgVioySoUVimtw");
        keyList.add("f2kgVioykoURWmtg");
        keyList.add("e3kgGioydoURWmtf");
        keyList.add("b9rtFfoydoURnjtp");
        keyList.add("t6rtTfmcdoURnjth");

        if ("1".equals(key)) {
            return keyList.get(0);
        }

        if ("2".equals(key)) {
            return keyList.get(1);
        }

        if ("3".equals(key)) {
            return keyList.get(2);
        }

        if ("4".equals(key)) {
            return keyList.get(3);
        }
        return keyList.get(4);

        // return "c1kgVioySoUVimtw";
    }
}