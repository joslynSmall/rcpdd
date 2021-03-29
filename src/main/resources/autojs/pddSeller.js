// var utilMd5 = require('./md5.js');
// var m = utilMd5.hexMD5("123");
// console.log(m)

auto.waitFor();

var orderArr = new Array();
var orderDealArr = new Array();

//请求截图
if (!requestScreenCapture(false)) {
  toastLog("请求截图失败");
  exit();
}

float = 1.25;
speed = 1;

// getAndSaveShippingOrderSn();
sleep(5 * 1000);
// lanchMyApp();

function lanchMyApp() {
  var appName = "浏览器";
  launchApp(appName);
  // waitTime(10,"正在打开"+appName);
  var urlInput = id("url").findOne();
  urlInput.click();
  urlInput.setText("https://mms.pinduoduo.com");
  KeyCode("KEYCODE_ENTER");
  sleep(9 * 1000);
  toastLog("进入商家后台主页,等待登录判断");

  if (
    !className("android.view.View").desc("后台首页").exists() ||
    !className("android.view.View").desc("待签收").exists()
  ) {
    // if (!className("android.view.View").desc("囧卡官方旗舰店").exists()) {
    toastLog("还么有登录,即将登录");
    // Swipe(596, 857, 240, 809, 500);
    className("android.view.View").desc("账户登录").waitFor();
    className("android.view.View").desc("账户登录").findOne().click();
    var username = className("android.widget.EditText")
      .text("请输入账户名/手机号")
      .findOne();
    // var username = id("usernameId").findOne();
    // username.focus();
    username.click();
    username.setText("16638111185");
    sleep(2 * 1000);
    KeyCode("39");
    KeyCode("KEYCODE_TAB");

    sleep(5 * 1000);

    // KeyCode("KEYCODE_SYM");
    // KeyCode("KEYCODE_Y");
    Text("Y");
    // KeyCode("KEYCODE_SYM");
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
    urlInput.setText(
      "https://mms.pinduoduo.com/orders/detail?type=4399&sn=" + orderSn
    );
    KeyCode("KEYCODE_ENTER");

    // 等待页面加载完成后截图
    className("android.view.View").desc("查看充值信息").waitFor();

    // 读取主图
    // var img = images.read("/storage/emulated/0/autojs/11.jpeg");

    // 截取主图
    var img = images.captureScreen();
    sleep(1000);
    images.save(img, "/storage/emulated/0/autojs/11.jpeg");

    // 点击显示充值信息
    className("android.view.View").desc("查看充值信息").findOne().click();
    sleep(2000);

    // 发货按钮截图
    var fahuo_btn = images.clip(img, 1399, 460, 83, 36);
    sleep(2000);
    images.save(fahuo_btn, "/storage/emulated/0/autojs/fahuo_btn.jpeg");

    img = images.captureScreen();

    var postNum = images.clip(img, 318, 797, 112, 17);
    sleep(1000);
    images.save(postNum, "/storage/emulated/0/autojs/postNum.jpeg");
    // BaiDu_ocr(postNum, false);
    postNum.recycle();
    var b = fahuo_click(img);
    if (b) {
      toastLog("已发货订单号:" + orderSn);
      remove(orderArr, orderSn);
      orderDealArr.push(orderSn);
    }
  }
}

function fahuo_click(img) {
  var fahuo_btn = images.read("/storage/emulated/0/autojs/fahuo_btn.jpeg");
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
}

/**
 * pddOrderNumer={订单编号}&pddGoodsId={商品ID}&buyNum={购买数量}&rechargeAccount={充值号码}&sign={md5校验}
 *
 * md5
 * {订单编号}{商品ID}{购买数量}joslyn
 */
function postOrderRecharge(orderItem, rechargeAccount) {
  var posturl = "http://192.168.0.8:9527/agiso/manual/payById";
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
  var signString =
    orderItem.orderSn + orderItem.goodsId + orderItem.goodsNumber + "joslyn";
  return getMd5(signString);
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

function getAndSaveShippingOrderSn() {
  var url = "http://192.168.0.8:9527/agiso/push/pdd/orderList/noshipment";
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
    var orderSn = jres.result.pageItems[i].orderSn;
    toastLog("预备新添加订单号" + orderSn);
    if (!_contains(orderArr, orderSn) && !_contains(orderDealArr, orderSn)) {
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
