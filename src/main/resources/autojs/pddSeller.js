// var utilMd5 = require('./md5.js');
// var m = utilMd5.hexMD5("123");
// console.log(m)
var hexcase = 0;
var b64pad = "";
var chrsz = 8;
var md5 = {
    /*
    参数:
    1.欲加密字符串
    2.布尔值为真则为32位否则16位
    3.布尔值为真则大写否则为小写
    */
    hex_md5: function (s, digit, capitalize) {
        let str = "";
        if (digit == true && capitalize == true) {
            str = binl2hex(core_md5(str2binl(s), s.length * chrsz)).toUpperCase();
        } else if (digit == false && capitalize == true) {
            str = binl2hex(core_md5(str2binl(s), s.length * chrsz)).substring(8, 24).toUpperCase();
        } else if (digit == true && capitalize == false) {
            str = binl2hex(core_md5(str2binl(s), s.length * chrsz));
        } else {
            str = binl2hex(core_md5(str2binl(s), s.length * chrsz)).substring(8, 24);
        }
        return str;
    },
    b64_md5: function (s) {
        return binl2b64(core_md5(str2binl(s), s.length * chrsz));
    },
    hex_hmac_md5: function (key, data) {
        return binl2hex(core_hmac_md5(key, data));
    },
    b64_hmac_md5: function (key, data) {
        return binl2b64(core_hmac_md5(key, data));
    },
    calcMD5: function (s) {
        return binl2hex(core_md5(str2binl(s), s.length * chrsz));
    }
}

function binl2hex(binarray) {
    var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    var str = "";
    for (var i = 0; i < binarray.length * 4; i++) {
        str += hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8 + 4)) & 0xF) +
            hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8)) & 0xF);
    }
    return str;
}

function str2binl(str) {
    var bin = Array();
    var mask = (1 << chrsz) - 1;
    for (var i = 0; i < str.length * chrsz; i += chrsz)
        bin[i >> 5] |= (str.charCodeAt(i / chrsz) & mask) << (i % 32);
    return bin;
}

function md5_ff(a, b, c, d, x, s, t) {
    return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}

function md5_cmn(q, a, b, x, s, t) {
    return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s), b);
}

function md5_gg(a, b, c, d, x, s, t) {
    return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}

function md5_hh(a, b, c, d, x, s, t) {
    return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}

function md5_ii(a, b, c, d, x, s, t) {
    return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

function safe_add(x, y) {
    var lsw = (x & 0xFFFF) + (y & 0xFFFF);
    var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    return (msw << 16) | (lsw & 0xFFFF);
}

function bit_rol(num, cnt) {
    return (num << cnt) | (num >>> (32 - cnt));
}

function core_md5(x, len) {

    x[len >> 5] |= 0x80 << ((len) % 32);
    x[(((len + 64) >>> 9) << 4) + 14] = len;
    var a = 1732584193;
    var b = -271733879;
    var c = -1732584194;
    var d = 271733878;
    for (var i = 0; i < x.length; i += 16) {
        var olda = a;
        var oldb = b;
        var oldc = c;
        var oldd = d;

        a = md5_ff(a, b, c, d, x[i + 0], 7, -680876936);
        d = md5_ff(d, a, b, c, x[i + 1], 12, -389564586);
        c = md5_ff(c, d, a, b, x[i + 2], 17, 606105819);
        b = md5_ff(b, c, d, a, x[i + 3], 22, -1044525330);
        a = md5_ff(a, b, c, d, x[i + 4], 7, -176418897);
        d = md5_ff(d, a, b, c, x[i + 5], 12, 1200080426);
        c = md5_ff(c, d, a, b, x[i + 6], 17, -1473231341);
        b = md5_ff(b, c, d, a, x[i + 7], 22, -45705983);
        a = md5_ff(a, b, c, d, x[i + 8], 7, 1770035416);
        d = md5_ff(d, a, b, c, x[i + 9], 12, -1958414417);
        c = md5_ff(c, d, a, b, x[i + 10], 17, -42063);
        b = md5_ff(b, c, d, a, x[i + 11], 22, -1990404162);
        a = md5_ff(a, b, c, d, x[i + 12], 7, 1804603682);
        d = md5_ff(d, a, b, c, x[i + 13], 12, -40341101);
        c = md5_ff(c, d, a, b, x[i + 14], 17, -1502002290);
        b = md5_ff(b, c, d, a, x[i + 15], 22, 1236535329);
        a = md5_gg(a, b, c, d, x[i + 1], 5, -165796510);
        d = md5_gg(d, a, b, c, x[i + 6], 9, -1069501632);
        c = md5_gg(c, d, a, b, x[i + 11], 14, 643717713);
        b = md5_gg(b, c, d, a, x[i + 0], 20, -373897302);
        a = md5_gg(a, b, c, d, x[i + 5], 5, -701558691);
        d = md5_gg(d, a, b, c, x[i + 10], 9, 38016083);
        c = md5_gg(c, d, a, b, x[i + 15], 14, -660478335);
        b = md5_gg(b, c, d, a, x[i + 4], 20, -405537848);
        a = md5_gg(a, b, c, d, x[i + 9], 5, 568446438);
        d = md5_gg(d, a, b, c, x[i + 14], 9, -1019803690);
        c = md5_gg(c, d, a, b, x[i + 3], 14, -187363961);
        b = md5_gg(b, c, d, a, x[i + 8], 20, 1163531501);
        a = md5_gg(a, b, c, d, x[i + 13], 5, -1444681467);
        d = md5_gg(d, a, b, c, x[i + 2], 9, -51403784);
        c = md5_gg(c, d, a, b, x[i + 7], 14, 1735328473);
        b = md5_gg(b, c, d, a, x[i + 12], 20, -1926607734);
        a = md5_hh(a, b, c, d, x[i + 5], 4, -378558);
        d = md5_hh(d, a, b, c, x[i + 8], 11, -2022574463);
        c = md5_hh(c, d, a, b, x[i + 11], 16, 1839030562);
        b = md5_hh(b, c, d, a, x[i + 14], 23, -35309556);
        a = md5_hh(a, b, c, d, x[i + 1], 4, -1530992060);
        d = md5_hh(d, a, b, c, x[i + 4], 11, 1272893353);
        c = md5_hh(c, d, a, b, x[i + 7], 16, -155497632);
        b = md5_hh(b, c, d, a, x[i + 10], 23, -1094730640);
        a = md5_hh(a, b, c, d, x[i + 13], 4, 681279174);
        d = md5_hh(d, a, b, c, x[i + 0], 11, -358537222);
        c = md5_hh(c, d, a, b, x[i + 3], 16, -722521979);
        b = md5_hh(b, c, d, a, x[i + 6], 23, 76029189);
        a = md5_hh(a, b, c, d, x[i + 9], 4, -640364487);
        d = md5_hh(d, a, b, c, x[i + 12], 11, -421815835);
        c = md5_hh(c, d, a, b, x[i + 15], 16, 530742520);
        b = md5_hh(b, c, d, a, x[i + 2], 23, -995338651);
        a = md5_ii(a, b, c, d, x[i + 0], 6, -198630844);
        d = md5_ii(d, a, b, c, x[i + 7], 10, 1126891415);
        c = md5_ii(c, d, a, b, x[i + 14], 15, -1416354905);
        b = md5_ii(b, c, d, a, x[i + 5], 21, -57434055);
        a = md5_ii(a, b, c, d, x[i + 12], 6, 1700485571);
        d = md5_ii(d, a, b, c, x[i + 3], 10, -1894986606);
        c = md5_ii(c, d, a, b, x[i + 10], 15, -1051523);
        b = md5_ii(b, c, d, a, x[i + 1], 21, -2054922799);
        a = md5_ii(a, b, c, d, x[i + 8], 6, 1873313359);
        d = md5_ii(d, a, b, c, x[i + 15], 10, -30611744);
        c = md5_ii(c, d, a, b, x[i + 6], 15, -1560198380);
        b = md5_ii(b, c, d, a, x[i + 13], 21, 1309151649);
        a = md5_ii(a, b, c, d, x[i + 4], 6, -145523070);
        d = md5_ii(d, a, b, c, x[i + 11], 10, -1120210379);
        c = md5_ii(c, d, a, b, x[i + 2], 15, 718787259);
        b = md5_ii(b, c, d, a, x[i + 9], 21, -343485551);

        a = safe_add(a, olda);
        b = safe_add(b, oldb);
        c = safe_add(c, oldc);
        d = safe_add(d, oldd);
    }
    return Array(a, b, c, d);

}

auto.waitFor();

var orderArr = new Array();
var orderDealArr = new Array();
var storage = storages.create("orderInfoStorage");

//请求截图
if (!requestScreenCapture(false)) {
    toastLog("请求截图失败");
    exit();
}

float = 1.25;
speed = 1;

getAndSaveShippingOrderSn();
sleep(5 * 1000);
lanchMyApp();

function lanchMyApp() {
    var appName = "浏览器";
    launchApp(appName);
    // waitTime(10,"正在打开"+appName);
    var urlInput = id("url").findOne();
    urlInput.click();
    urlInput.setText("https://mms.pinduoduo.com");
    KeyCode("KEYCODE_ENTER");
    // sleep(5 * 1000);
    toastLog("进入商家后台主页,等待登录判断");

    if (
        false
    ) {
        // if (!className("android.view.View").desc("囧卡官方旗舰店").exists()) {
        toastLog("还么有登录,即将登录");
        className("android.view.View").desc("账户登录").waitFor();
        className("android.view.View").desc("账户登录").findOne().click();
        var username = className("android.widget.EditText")
            .text("请输入账户名/手机号")
            .findOne();
        username.click();
        username.setText("16638111185");
        sleep(2 * 1000);
        KeyCode("39");
        KeyCode("KEYCODE_TAB");

        sleep(5 * 1000);

        Text("Y");
        sleep(500);
        KeyCode("KEYCODE_S");
        sleep(500);
        KeyCode("KEYCODE_1");
        sleep(500);
        KeyCode("KEYCODE_9");
        sleep(500);
        KeyCode("KEYCODE_9");
        sleep(500);
        KeyCode("KEYCODE_2");
        sleep(500);
        KeyCode("KEYCODE_S");
        sleep(500);
        KeyCode("KEYCODE_P");
        sleep(500);
        KeyCode("KEYCODE_D");
        sleep(500);
        KeyCode("KEYCODE_D");

        // password.setText("Ys1992spdd");
        sleep(1000);
        className("android.widget.Button").findOne().click();
        faHuoStart();
    } else {
        toastLog("进入订单详情发货");
        faHuoStart();
    }
}

function faHuoStart() {
    var urlInput = id("url").findOne();
    var lastLen = orderArr.length;
    var copyarr = copy(orderArr);
    toastLog("可发货订单数量:" + lastLen);
    for (var i = 0; i < lastLen; i++) {
        orderSn = copyarr[i];
        toastLog("数组取订单号:" + orderSn + "第" + (i + 1) + "个,一共:" + lastLen);
        urlInput.click();
        sleep(1500);
        urlInput.setText(
            "https://mms.pinduoduo.com/orders/detail?type=4399&sn=" + orderSn
        );
        KeyCode("KEYCODE_ENTER");

        // 等待页面加载完成后截图
        className("android.view.View").desc("查看充值信息").waitFor();

        // 点击显示充值信息
        className("android.view.View").desc("查看充值信息").findOne().click();
        sleep(2000);

        // 截取主图
        var img = images.captureScreen();
        sleep(1000);
        images.save(img, "/storage/emulated/0/$MuMu共享文件夹/bg/11.jpeg");


        // 发货按钮截图
        var fahuo_btn = images.clip(img, 1399, 460, 83, 36);
        sleep(2000);
        images.save(fahuo_btn, "/storage/emulated/0/$MuMu共享文件夹/bg/fahuo_btn.jpeg");
        fahuo_btn.recycle();

        var postNum = images.clip(img, 318, 797, 112, 17);
        sleep(1000);
        images.save(postNum, "/storage/emulated/0/$MuMu共享文件夹/bg/postNum.jpeg");
        var postnum = BaiDu_ocr(postNum, false);
        postnum = postnum['words_result'][0].words;
        toastLog('ocr识别号码:' + postnum);

        // 调用充值
        toastLog('存储对象' + storage.get(orderSn))
        var chargeResultBoo = postOrderRecharge(storage.get(orderSn), postnum);
        postNum.recycle();
        if (!chargeResultBoo) {
            toastLog('调用充值反馈失败,失败订单号' + orderSn);
        }else{
          var b = fahuo_click(img);
          if (b) {
              toastLog("已发货订单号:" + orderSn);
              remove(orderArr, orderSn);
              storage.remove(orderSn);
              orderDealArr.push(orderSn);
          }
        }
        
        
    }
}

function fahuo_click(img) {
    var fahuo_btn = images.read("/storage/emulated/0/$MuMu共享文件夹/bg/fahuo_btn.jpeg");
    // 寻找发货按钮
    var isFindFahuoBtn = images.findImageInRegion(
        img,
        fahuo_btn,
        1399,
        460,
        83,
        36
    );

    if (isFindFahuoBtn) {
        toastLog("找到啦:" + isFindFahuoBtn);
        Tap(isFindFahuoBtn.x + 30, isFindFahuoBtn.y + 20);
        sleep(500);

        className("android.view.View").desc("自己联系物流").findOne().click();
        sleep(500);
        className("android.widget.RadioButton")
            .desc("   无物流发货")
            .findOne()
            .click();
        sleep(500);
        className("android.widget.RadioButton")
            .desc("   其他")
            .findOne()
            .click();
        sleep(500);
        className("android.widget.Button").desc("确认").findOne().click();

        fahuo_btn.recycle();
        // isFindFahuoBtn.recycle();

        return true;
    } else {
        toastLog("没找到[发货按钮]");
        // isFindFahuoBtn.recycle();
        return false;
    }
    toastLog("发货成功");
    // 发货完成再去过滤一遍防止处理过程中漏单
    if (getAndSaveShippingOrderSn()) {
      lanchMyApp();
    }
    var thisTh = threads.currentThread();
    log("当前线程(子线程):" + thisTh);
    thisTh.interrupt();

}

/**
 * pddOrderNumer={订单编号}&pddGoodsId={商品ID}&buyNum={购买数量}&rechargeAccount={充值号码}&sign={md5校验}
 *
 * md5
 * {订单编号}{商品ID}{购买数量}joslyn
 */
function postOrderRecharge(orderItem, rechargeAccount) {
    var posturl = "http://192.168.6.120:9527/agiso/manual/payById";
    var res = http.post(posturl, {
        pddOrderNumer: orderItem.orderSn,
        pddGoodsId: orderItem.goodsId,
        buyNum: orderItem.goodsNumber,
        rechargeAccount: rechargeAccount,
        sign: ksAgisoMd5(orderItem, rechargeAccount),
    });
    jres = res.body.json();
    if ("ok" == jres.code) {
        return true;
    } else {
        return false;
    }
}

function ksAgisoMd5(orderItem, rechargeAccount) {
    toastLog(orderItem.orderSn.replace(/\s+/g, ""));
    toastLog(orderItem.goodsId.replace(/\s+/g, ""));
    toastLog(orderItem.goodsNumber);
    var signString =
        md5.hex_md5(orderItem.orderSn.replace(/\s+/g, "") + orderItem.goodsId.replace(/\s+/g, "") + orderItem.goodsNumber + "joslyn", true, false);
    return signString;
}

function getMd5(string) {
    return java.math
        .BigInteger(
            1,
            java.security.MessageDigest.getInstance("MD5").digest(
                java.lang.String(string).getBytes()
            )
        )
        .toString(16);
}

var orderObj = {
    'orderSn': '',
    'goodsId': '',
    'goodsNumber': ''
};

function getAndSaveShippingOrderSn() {
    var url = "http://192.168.6.120:9527/agiso/push/pdd/orderList/noshipment";
    var res = http.get(url);
    jres = res.body.json();
    if (!jres.success) {
        toastLog(jres.errorMsg);
        return false;
    }
    var totalNum = jres.result.totalItemNum;
    toastLog(totalNum);
    var isAddedThisTime = false;
    for (var i = 0; i < jres.result.pageItems.length; i++) {
        var orderObj = {
            'orderSn': '',
            'goodsId': '',
            'goodsNumber': ''
        };
        var orderSn = jres.result.pageItems[i].orderSn;
        toastLog("预备新添加订单号" + orderSn);
        if (!_contains(orderArr, orderSn) && !_contains(orderDealArr, orderSn)) {
            orderObj.orderSn = orderSn;
            orderObj['goodsId'] = jres.result.pageItems[i].goodsId;
            orderObj['goodsNumber'] = jres.result.pageItems[i].goodsNumber;
            storage.put(orderSn, orderObj);
            orderArr.push(orderSn);
            toastLog("新添加订单号:" + orderSn);
            isAddedThisTime = true;
        }
    }
    return isAddedThisTime;
}

function BaiDu_ocr(img, is位置) {
    var imag64 = images.toBase64(img, "png", 100);
    //本代码。key值，属于，大维万，所有。每天可用1000次。
    var API_Key = "IMi7uTlPbISgrYCkBnUZxREn";
    var Secret_Key = "NRE9cT0SA9qeEyadk7e0wzHH2LHiQTeS";

    var getTokenUrl = "https://aip.baidubce.com/oauth/2.0/token";
    //token获取地址。
    var token_Res = http.post(getTokenUrl, {
        grant_type: "client_credentials",
        client_id: API_Key,
        client_secret: Secret_Key,
    });

    var token = token_Res.body.json().access_token;
    //log(token);
    var ocrUrl1 = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //每天可用5000次。
    //文字识别。
    var ocrUrl2 = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //每天可用500次。
    //含位置信息。
    var ocrUrl = ocrUrl1;
    if (is位置) {
        ocrUrl = ocrUrl2;
    }
    var ocr_Res = http.post(ocrUrl, {
        headers: {
            "Content - Type": "application/x-www-form-urlencoded",
        },
        access_token: token,
        image: imag64,
    });

    var json = ocr_Res.body.json();
    log(json);
    toastLog(json);
    return json;
}

toastLog("Hello, Auto.js noonononon");
auto();
events.observeNotification();
events.onNotification(function (notification) {
    printNotification(notification);
});
toastLog("监听中，请在日志中查看记录的通知及其内容");

function printNotification(notification) {
    toastLog("应用包名: " + notification.getPackageName());
    toastLog("通知文本: " + notification.getText());
    // toastLog("通知优先级: " + notification.priority);
    // toastLog("通知目录: " + notification.category);
    // toastLog("通知时间: " + new Date(notification.when));
    // toastLog("通知数: " + notification.number);
    // toastLog("通知摘要: " + notification.tickerText);
    // if(notification.getPackageName()=='com.xunmeng.merchant'){
    //     task();
    // }
    // if (notification.getText() != null && (notification.getText().includes("请尽快发货") || notification.getText().includes("测试发货"))) {
    if (
        notification.getPackageName() != null &&
        (notification.getPackageName().includes("com.xunmeng.merchant") ||
            notification.getText().includes("测试发货"))
    ) {
        task();
    }
}

function task() {
    if (!getAndSaveShippingOrderSn()) {
        return;
    }
    var thread = threads.start(function () {
        lanchMyApp();
    });
}

/**
 * 通过文字内容模拟点击按钮
 * @param content 按钮文字内容
 * @param type 点击类型，默认为text点击
 * @param sleepTime 等待时间，默认1000毫秒
 */
function clickContent(content, type, sleepTime) {
    var type = type || "text";
    var sleepTime = sleepTime || 1000;
    sleep(sleepTime * float * speed);
    if (type == "text") {
        var button = text(content).findOne();
    } else {
        var button = desc(content).findOne();
    }
    clickButton(button);
    return button;
}

/**
 * 根据控件的坐标范围随机模拟点击
 * @param button
 */
function clickButton(button) {
    var bounds = button.bounds();
    press(
        random(bounds.left, bounds.right),
        random(bounds.top, bounds.bottom),
        random(50, 100)
    );
}

/**
 * 根据控件的坐标范围随机模拟点击
 * @param button
 */
function clickButton(bounds) {
    press(
        random(bounds.left, bounds.right),
        random(bounds.top, bounds.bottom),
        random(50, 100)
    );
}

function remove(arr, item) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == item) {
            //从i出开始删除1个元素
            arr.splice(i, 1);
            i--;
        }
    }
    return arr;
}

function _contains(arr, item) {
    for (var j = 0; j < arr.length; j++) {
        if (arr[j] == item) {
            return true;
        }
    }
    return false;
}

function copy(obj) {
    var newobj = obj.constructor === Array ? [] : {};
    if (typeof obj !== "object") {
        return;
    }
    for (var i in obj) {
        newobj[i] = typeof obj[i] === "object" ? copy(obj[i]) : obj[i];
    }
    return newobj;
}
