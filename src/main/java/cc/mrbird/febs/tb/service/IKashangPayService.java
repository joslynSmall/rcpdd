package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.common.exception.RedisConnectException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: IKashangPayService
 * @ProjectName backend
 * @Description: http://www.kashangwl.com/user
 * 卡商网充值
 * @date 2020-04-26 15:10
 */
public interface IKashangPayService {

    public String setKashangCookiesMap(String cookiesStr) throws RedisConnectException;

    public Map<String, String> getKashangCookiesMap() throws RedisConnectException, IOException, InterruptedException;

    public boolean payTxspWeek(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    public boolean payTxspMonth(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    public boolean payTxspSeason(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    public boolean payTxspYear(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    public boolean payCjysMonth(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    public boolean payCjysYear(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    public boolean payMtwmMonth(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException;

    boolean payKsById(String goodsId, String rechargeAccount, int num) throws InterruptedException, RedisConnectException, IOException;
}
