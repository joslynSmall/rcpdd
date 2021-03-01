package cc.mrbird.febs.pdd.service;

import cc.mrbird.febs.apisource.model.KsResult;
import cc.mrbird.febs.apisource.model.TxmSubmitOrderParams;
import cc.mrbird.febs.common.domain.Result;
import cc.mrbird.febs.common.exception.RedisConnectException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Joslyn
 * @Title: IkashangService
 * @ProjectName backend
 * @Description: 卡商api
 * @date 2/8/21 11:54 AM
 */
public interface IkashangService {

    KsResult getProduct() throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException;

    KsResult summitPddOrder(TxmSubmitOrderParams params) throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException;

    KsResult getOrderStatusByOrderId(String ksOrderId,String sign) throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException;
}
