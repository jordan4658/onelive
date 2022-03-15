package com.onelive.common.utils.pay;

import lombok.Data;

/**
 * @ClassName Result
 * @Description TODO
 * @Author wk
 * @Date 2021/6/7 15:26
 **/
@Data
public class Result {
    private String code;
    private String msg;

    public Result(){

    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success(String msg) {
        return new Result("0",msg);
    }

    public static Result fail(String msg) {
        return new Result("1", msg);
    }

}
