package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.common.exception.RedisConnectException;

import java.io.IOException;

/**
 * @author Joslyn
 * @Title: IYihuiPayService
 * @ProjectName febs_shiro_jwt
 * @Description: 易惠网下单服务
 * @date 2020-04-06 18:05
 */
public interface IYihuiPayService {

    /**
     * 易惠网下单好莱坞月卡
     *
     * @param buyerMessage
     * @param num
     * @return
     */
    public boolean payYihuiTxspMonthOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException;

    /**
     * 易惠网下载好莱坞年卡
     *
     * @param buyerMessage
     * @param num
     * @return
     */
    public boolean payYihuiTxspYearOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException;

    /**
     * 易惠网下单腾讯云视听月卡
     * @param buyerMessage
     * @param num
     * @return
     * @throws InterruptedException
     * @throws RedisConnectException
     * @throws IOException
     */
    public boolean payYihuiYstMonthOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException;
}
