/**
 * Copyright 2021 json.cn
 */
package cc.mrbird.febs.pdd.model;

import lombok.Data;

/**
 * Auto-generated: 2021-03-16 11:30:40
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class PddResult<T> {

    private boolean success;
    private int errorCode;
    private String errorMsg;
    private T result;

}
