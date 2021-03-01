package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.common.exception.RedisConnectException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: IJiubanPayService
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2020-04-06 18:14
 */
public interface IJiubanPayService {

    public boolean payJiubanTxspMonthOrder(String buyerMessage, int num) throws RedisConnectException, IOException, InterruptedException;

    public Map<String, String> getCookie(boolean boo) throws RedisConnectException, IOException, InterruptedException;

}
