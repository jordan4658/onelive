package com.onelive.manage.utils.other;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.OutputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class JsonUtil {

    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // new Object() 对象，转换时报异常，设置FAIL_ON_EMPTY_BEANS 将会直接转成成{}
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 允许出现特殊字符和转义符
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        // SimpleModule stringModule = new SimpleModule("StringModule");
        // stringModule.addSerializer(new StringSerializer()); // 将字符串中的html进行转义
        // stringModule.addDeserializer(String.class, new StringDeserialize());// 将json中html转义字符还原
        // objectMapper.registerModule(stringModule);

        // 禁用不存在字段报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private CharacterIterator it;
    private char c;
    private int col;

    /**
     * java 对象转换为json 存入流中
     *
     * @param obj
     */
    public static String toJson(Object obj) {
        String s = "";
        try {
            s = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * java 对象转换为json 存入流中
     *
     * @param obj
     * @param out
     */
    public static void toJson(Object obj, OutputStream out) {
        try {
            objectMapper.writeValue(out, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * json 转为java对象
     *
     * @param json
     * @param valueType
     */
    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json 转为java对象
     *
     * @param json
     * @param valueTypeRef
     * @param valueTypeRef 注意泛型类型不能是接口
     */
    @SuppressWarnings("hiding")
    public static <Object> Object fromJson(String json, TypeReference<Object> valueTypeRef) {
        try {
            return objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证一个字符串是否是合法的JSON串
     *
     * @param input 要验证的字符串
     * @return true-合法 ，false-非法
     */
    public boolean validate(String input) {
        input = input.trim();
        boolean ret = valid(input);
        return ret;
    }

    private boolean valid(String input) {
        if ("".equals(input)) {
            return true;
        }

        boolean ret = true;
        it = new StringCharacterIterator(input);
        c = it.first();
        col = 1;
        if (!value()) {
            ret = error("value", 1);
        } else {
            skipWhiteSpace();
            if (c != CharacterIterator.DONE) {
                ret = error("end", col);
            }
        }

        return ret;
    }

    private boolean value() {
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }

    private boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (c != t) {
            return false;
        }

        int start = col;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        if (!ret) {
            error("literal " + text, start);
        }
        return ret;
    }

    private boolean array() {
        return aggregate('[', ']', false);
    }

    private boolean object() {
        return aggregate('{', '}', true);
    }

    private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (c != entryCharacter) {
            return false;
        }
        nextCharacter();
        skipWhiteSpace();
        if (c == exitCharacter) {
            nextCharacter();
            return true;
        }

        for (; ; ) {
            if (prefix) {
                int start = col;
                if (!string()) {
                    return error("string", start);
                }
                skipWhiteSpace();
                if (c != ':') {
                    return error("colon", col);
                }
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (c == ',') {
                    nextCharacter();
                } else if (c == exitCharacter) {
                    break;
                } else {
                    return error("comma or " + exitCharacter, col);
                }
            } else {
                return error("value", col);
            }
            skipWhiteSpace();
        }

        nextCharacter();
        return true;
    }

    private boolean number() {
        if (!Character.isDigit(c) && c != '-') {
            return false;
        }
        int start = col;
        if (c == '-') {
            nextCharacter();
        }
        if (c == '0') {
            nextCharacter();
        } else if (Character.isDigit(c)) {
            while (Character.isDigit(c)) {
                nextCharacter();
            }
        } else {
            return error("number", start);
        }
        if (c == '.') {
            nextCharacter();
            if (Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
            } else {
                return error("number", start);
            }
        }
        if (c == 'e' || c == 'E') {
            nextCharacter();
            if (c == '+' || c == '-') {
                nextCharacter();
            }
            if (Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
            } else {
                return error("number", start);
            }
        }
        return true;
    }

    private boolean string() {
        if (c != '"') {
            return false;
        }

        int start = col;
        boolean escaped = false;
        for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && c == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (c == '"') {
                nextCharacter();
                return true;
            }
        }
        return error("quoted string", start);
    }

    private boolean escape() {
        int start = col - 1;
        if (" \\\"/bfnrtu".indexOf(c) < 0) {
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
        }
        if (c == 'u') {
            if (!ishex(nextCharacter())) {
                return error("unicode escape sequence  \\uxxxx ", start);
            }
        }
        return true;
    }

    private boolean ishex(char d) {
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }

    private char nextCharacter() {
        c = it.next();
        ++col;
        return c;
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(c)) {
            nextCharacter();
        }
    }

    private boolean error(String type, int col) {
        System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
        return false;
    }

    public static JSONObject toJSONObject(Object object) {
        return null == object ? null : toJSONObject(JSONObject.toJSONString(object));
    }

    public static JSONObject toJSONObject(String json) {
        return JSONObject.parseObject(json);
    }

    /**
     * 删除对象指定字段
     *
     * @param object        转json的对象
     * @param excludes      要排除的字段，逗号分隔
     * @param returnDefault 是否返回默认值
     * @return
     */
    public static JSONObject excludeFields(Object object, String excludes, boolean returnDefault) {
        JSONObject jsonObject = null;
        if (returnDefault) {
            jsonObject = new JSONObject();
        }
        if (null == excludes || excludes.trim().length() == 0) {
            return jsonObject;
        }
        return excludeFields(object, excludes.split(","), returnDefault);
    }

    /**
     * 删除对象指定字段
     *
     * @param object        转json的对象
     * @param excludes      要排除的字段数组
     * @param returnDefault 是否返回默认值
     * @return
     */
    public static JSONObject excludeFields(Object object, String[] excludes, boolean returnDefault) {
        JSONObject jsonObject = null;
        if (returnDefault) {
            jsonObject = new JSONObject();
        }
        if (null == excludes || excludes.length == 0) {
            return jsonObject;
        }
        jsonObject = toJSONObject(object);
        for (String field : excludes) {
            jsonObject.remove(field);
        }
        return jsonObject;
    }
}
