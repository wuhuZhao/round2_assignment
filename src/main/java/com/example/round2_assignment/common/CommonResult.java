package com.example.round2_assignment.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;

@Data
@Accessors(chain = true)
public class CommonResult<T>{
    private Integer code;
    private T data;
    private String msg;

    public static CommonResult SuccessResult(Object data) {
        return new CommonResult()
                .setMsg(Strings.EMPTY)
                .setData(data)
                .setCode(200);
    }

    public static CommonResult ExceptionResult(Exception e) {
        return new CommonResult()
                .setCode(-1)
                .setMsg(e.getMessage())
                .setData(null);
    }
}
