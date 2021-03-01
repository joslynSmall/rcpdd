package cc.mrbird.febs.pdd.service.impl;

import cc.mrbird.febs.agiso.constants.AgisoContans;
import cc.mrbird.febs.pdd.service.IPddOrderService;
import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;
import cc.mrbird.febs.tb.utils.AgisoUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: PddOrderServiceImpl
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-10-18 22:36
 */
@Service
public class PddOrderServiceImpl implements IPddOrderService {
    @Override
    public ReturnBackResult beforeBuyPushNew(AgisoPushInfo agisoPushInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        this.validateSign(agisoPushInfo);

        return null;
    }

    @Override
    public boolean validateSign(AgisoPushInfo pushInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Map<String, String> map = new HashMap();
        map.put("json", pushInfo.getJson());
        map.put("timestamp", String.valueOf(pushInfo.getTimestamp()));
        //参考签名算法
        String checkSign = AgisoUtil.Sign(map, AgisoContans.APP_SECRET);
        if (!checkSign.equals(pushInfo.getSign())) {
            return false;
        }

        return true;
    }
}
