package com.example.demo.common;

public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }
    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode,String message) {
        return new BaseResponse<>(errorCode.getCode(),null,message);
    }

    public static <T> BaseResponse<T> error(int errorCode,String message) {
        return new BaseResponse<>(errorCode,null,message);
    }

}

