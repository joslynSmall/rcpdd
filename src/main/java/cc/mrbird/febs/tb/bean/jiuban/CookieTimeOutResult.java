package cc.mrbird.febs.tb.bean.jiuban;

import lombok.Data;
import lombok.ToString;

/**
 * @author Joslyn
 * @Title: CookieTimeOutResult
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-29 14:38
 */
@Data
@ToString
public class CookieTimeOutResult {

    private String info;

    private int status;

    private String url;
}
