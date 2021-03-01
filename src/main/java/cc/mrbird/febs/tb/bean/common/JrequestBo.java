package cc.mrbird.febs.tb.bean.common;

import lombok.*;
import org.jsoup.Connection;

import java.util.Map;

/**
 * @author Joslyn
 * @Title: JrequestBo
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-04-26 15:33
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JrequestBo {

    private Connection.Method method = Connection.Method.POST;

    private Map<String, String> payOrderRequestMap;

    private String payurl;

    private String host;

    private String referrer;

    private String cookieStr;

    private String xcsrtoken;
}
