package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.common.exception.RedisConnectException;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * @author Joslyn
 * @Title: IHaodianPayService
 * @ProjectName febs_shiro_jwt
 * @Description: http://www.haodiankm.com/?cid=59&tid=738
 * @date 2020-04-13 15:56
 */
public interface IHaodianPayService {

    public boolean haodianOrderCreate(String buyerMessage, int num, Integer goodsTimeType) throws InterruptedException, RedisConnectException, ScriptException, IOException;

    public boolean haodianTxspMonthCreate(String buyerMessage) throws RedisConnectException, InterruptedException, ScriptException, IOException;

    public boolean haodianTxspWeekCreate(String buyerMessage) throws InterruptedException, RedisConnectException, ScriptException, IOException;
}
