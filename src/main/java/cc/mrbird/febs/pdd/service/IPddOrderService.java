package cc.mrbird.febs.pdd.service;

import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Joslyn
 * @Title: IPddOrderService
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-10-18 22:36
 */
public interface IPddOrderService {

    ReturnBackResult beforeBuyPushNew(AgisoPushInfo agisoPushInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    boolean validateSign(AgisoPushInfo pushInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
