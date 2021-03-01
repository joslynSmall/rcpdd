package cc.mrbird.febs.tb.service;

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.AgisoQueryBo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;
import cc.mrbird.febs.tb.bean.agiso.TradeInfo;
import cc.mrbird.febs.tb.bean.agiso.tradeClose.TradeCloseBo;

import javax.script.ScriptException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Joslyn
 * @Title: IOrderCommitService
 * @ProjectName febs_shiro_jwt
 * @Description: tb下单
 * @date 2019-11-25 20:12
 */
public interface IOrderCommitService {

    public TradeInfo getTradeInfo(AgisoQueryBo queryBo);

    public String LogisticsDummySend(AgisoQueryBo queryBo,String sellerNick);

    ReturnBackResult beforeBuyPush(AgisoPushInfo agisoPushInfo) throws RedisConnectException, IOException, NoSuchAlgorithmException, InterruptedException, ScriptException;

    ReturnBackResult beforeBuyPushNew(AgisoPushInfo agisoPushInfo) throws RedisConnectException, IOException, NoSuchAlgorithmException, InterruptedException, ScriptException;

    String closeTrade(TradeCloseBo closeBo);

}
