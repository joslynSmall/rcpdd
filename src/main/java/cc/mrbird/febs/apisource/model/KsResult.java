/**
 * Copyright 2021 json.cn
 */
package cc.mrbird.febs.apisource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Auto-generated: 2021-02-21 22:25:41
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KsResult<T> {

    private String code;
    private String message;
    private T data;

    public static KsResult error(String code,String message){
        return new KsResult(code,message,null);
    }

}
