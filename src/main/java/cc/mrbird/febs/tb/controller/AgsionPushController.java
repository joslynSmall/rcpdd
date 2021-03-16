package cc.mrbird.febs.tb.controller;

/**
 * @author Joslyn
 * @Title: AgsionPushController
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-10-18 22:24
 */

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.pdd.model.PddResult;
import cc.mrbird.febs.pdd.service.IPddOrderService;
import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

/**
 * agison 推送接收
 */
@RestController
@RequestMapping("/agiso/push")
public class AgsionPushController {

    @Autowired
    private IPddOrderService pddOrderService;

    @RequestMapping("/receive")
    public ReturnBackResult beforeBuyPush(AgisoPushInfo agisoPushInfo) throws IOException, RedisConnectException, NoSuchAlgorithmException, InterruptedException, ScriptException {
        return this.pddOrderService.beforeBuyPushNew(agisoPushInfo);

    }

    @GetMapping("/pdd/orderList")
    public PddResult getPddOrderList() throws IllegalAccessException, InterruptedException, IOException, RedisConnectException, InvocationTargetException, NoSuchMethodException {
        PddResult orderList = pddOrderService.getOrderList();
        return orderList;
    }
}
