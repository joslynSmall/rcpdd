package cc.mrbird.febs.common.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/***
 * @author Joslyn
 * @date 2019/3/29 20:40
 * @description 通用封装结果
 * @version v1.0.0
 */
@Slf4j
@Data
public class Result<T> implements Serializable {

    /**
     *
     */
    @NotNull
    private int code;
    /**
     *
     */
    @NotNull
    private String message = "success";
    /**
     *
     */
    @NotNull
    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     */

    public static <T> Result<T> error(int code, String msg) {
        return new Result(code, msg);
    }

    protected Result(T data) {
        this.data = data;
    }

    protected Result(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    protected Result(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
    }

}
