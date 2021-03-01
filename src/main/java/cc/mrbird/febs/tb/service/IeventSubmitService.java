package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.tb.bean.EventSubmitBean;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author Joslyn
 * @Title: IeventSubmitService
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-24 17:49
 */
public interface IeventSubmitService {

    public EventSubmitBean getTbEventSubmitList() throws IOException;
}
