package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.common.exception.RedisConnectException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: IQidianPayService
 * @ProjectName febs_shiro_jwt
 * @Description: 起点下单服务
 * @date 2020-04-06 18:05
 */
public interface IQidianPayService {

    /**
     * 起点平台下单好莱坞月卡
     * @param buyerMessage
     * @param num
     * @return
     */
    public boolean payQidianTxspMonthOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException;

    public Map<String, String> getQidianCookie(boolean boo) throws RedisConnectException, IOException;
    }
