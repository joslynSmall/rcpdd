package cc.mrbird.febs.common.exception;

/***
 * @author Royce
 * @date 2019/1/31 14:55
 * @description
 * @version v1.0.0
 * 错误码
 */
public class CodeMsg {
    // 通用的错误码 < 2000
    // SQL
    public static final CodeMsg MYBATIS_PLUS_EXCEPTION = new CodeMsg(100, "数据库执行异常");
    public static final CodeMsg UPDATE_SQL_EFFECT_COUNT_IS_ZERO = new CodeMsg(110, "sql影响记录数为0");
    public static final CodeMsg SQL_RESULT_IS_FALSE = new CodeMsg(111, "SQL执行返回结果为失败");
    // common
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg LOGIN = new CodeMsg(-1, "login");
    public static final CodeMsg LOGIN_FAIL = new CodeMsg(-2, "登录失败");
    public static final CodeMsg METHOD_ARGUMENT_NOT_VALID = new CodeMsg(-3, "服务异常：%s");
    public static final CodeMsg BIND_ERROR = new CodeMsg(-5, "参数校验异常：%s");
    public static final CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(-6, "访问太频繁！");
    public static final CodeMsg VALIDATION_CODE_ERROR = new CodeMsg(-7, "验证码错误");
    public static final CodeMsg UPLOAD_FAIL = new CodeMsg(-8, "文件上传失败");
    public static final CodeMsg MOBILE_SENDLIMIT_FAIL = new CodeMsg(-10, "今天验证码请求次数太多");
    public static final CodeMsg UPLOAD_IMAGE_TOO_LARGER = new CodeMsg(-11, "上传图片大小大于2MB");
    public static final CodeMsg OPERATION_NEED_MOBILE = new CodeMsg(-12, "该操作需绑定手机号");
    public static final CodeMsg VALIDATION_CODE_EXPIRE = new CodeMsg(-13, "验证码已失效");
    public static final CodeMsg NO_PERMISSION = new CodeMsg(-14, "没有权限");

    public static final CodeMsg HTTP_REQUEST_ERROR = new CodeMsg(101, "执行出错 post url:%s reason: %s");
    public static final CodeMsg TOKEN_EXPIRE = new CodeMsg(401, "Token失效");
    public static final CodeMsg WX_MINI_PROGRAM_SIGNATURE_ERROR = new CodeMsg(402, "签名错误");
    public static final CodeMsg METHOD_ARGUMENT_NOT_VALID_ERROR = new CodeMsg(-13, "请求参数校验错误,REASON=%s(%s)");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(-14, "服务异常，请重试");
    public static final CodeMsg SYS_PARAM_NULL = new CodeMsg(-15, "系统参数%s不存在");
    public static final CodeMsg SYS_PARAM_ILLEGAL = new CodeMsg(-15, "非法系统参数值%s");
    public static final CodeMsg REQUEST_PARAM_IS_NULL = new CodeMsg(-16, "非法请求参数");
    public static final CodeMsg REDIS_TEMPLATE_IS_NULL = new CodeMsg(-17, "获取不到与 %s 相对的 RedisTemplate");
    public static final CodeMsg PLEASE_DO_NOT_INPUT_EMOJI = new CodeMsg(-18, "请勿输入表情符号");

    // 账户模块 2XXX
    public static final CodeMsg UPDATE_PASSWORD_AS_OLD = new CodeMsg(2003, "密码与原密码相同");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(2004, "手机号不能为空");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(2005, "密码错误");
    public static final CodeMsg OLD_PASSWORD_ERROR = new CodeMsg(2005, "旧密码错误");
    public static final CodeMsg MOBILE_ALREADY_EXISTS = new CodeMsg(2007, "手机号已存在");
    public static final CodeMsg OLD_CARER_EXISTS = new CodeMsg(2008, "老司机已上车");
    public static final CodeMsg REGISTER_FAIL = new CodeMsg(2009, "注册失败");
    public static final CodeMsg WEIXIN_NOT_EXIST = new CodeMsg(2010, "微信号不存在");
    public static final CodeMsg ACCOUNT_NOT_EXIST = new CodeMsg(2011, "账号不存在");
    public static final CodeMsg DEVICE_TYPE_ERROR = new CodeMsg(2014, "设备类型错误");
    public static final CodeMsg ACCOUNT_EXIST = new CodeMsg(2015, "账号已存在");
    public static final CodeMsg WECHAT_ALREADY_EXISTS = new CodeMsg(2016, "该微信号已经绑定了其它账号");
    public static final CodeMsg USERNAME_NOT_EXISTS = new CodeMsg(2017, "用户不存在");
    public static final CodeMsg MOBILE_NUM_NOT_EXIST = new CodeMsg(2018, "%s 手机号码不存在");
    public static final CodeMsg GET_WX_APP_USER_ERROR = new CodeMsg(2019, "获取微信用户信息异常");
    public static final CodeMsg ACCOUNT_BIND_WX_IS_AS_OLD = new CodeMsg(2020, "已绑定该微信账号");
    public static final CodeMsg USER_DELETE_OR_NOT_EXIST = new CodeMsg(2021, "用户不存在或已删除");
    public static final CodeMsg USER_NEW_PASSWORD_NOT_SAME = new CodeMsg(2022, "两次输入新密码不一致");
    public static final CodeMsg PASSWORD_NEW_AS_OLD = new CodeMsg(2023, "新旧密码不能相同");
    public static final CodeMsg ACTION_ID_IS_NULL = new CodeMsg(2025, "权限编号不允许为空");
    public static final CodeMsg DEVICE_TYPE_NOT_NULL = new CodeMsg(2027, "deviceType不能为空");
    public static final CodeMsg MOBILE_HAS_BIND = new CodeMsg(2028, "该手机号已绑定其他微信,请更换");

    public static final CodeMsg SHARE_MUST_LOGIN = new CodeMsg(2028, "登录后才可以使用分享功能");
    public static final CodeMsg USER_DISPLAY_ID_DUPLICATE = new CodeMsg(2029, "用户显示编号已存在");
    public static final CodeMsg USER_INFO_DUPLICATE = new CodeMsg(2030, "%s与现有用户重复");
    public static final CodeMsg WECHAT_ACCOUNT_HAS_BIND_MOBILE = new CodeMsg(2031, "该微信号已绑定其他手机号");
    public static final CodeMsg ACCOUNT_EXCEPTION = new CodeMsg(2032, "账户异常: %s");
    public static final CodeMsg NULL_WECHAT_MINI_PROGRAM_OPEN_ID = new CodeMsg(2032, "未绑定微信小程序");
    public static final CodeMsg MOBILE_HAS_REGISTER = new CodeMsg(2033, "该手机号已注册，自动升级为V2");


    public static final CodeMsg WECHAT_AUTH_ERROR = new CodeMsg(2033, "微信认证异常，请重试");
    public static final CodeMsg MEMBER_NOT_EXIST = new CodeMsg(2034, "用户不存在");
    public static final CodeMsg RECOMMENDIDHASBIND = new CodeMsg(2035, "您已绑定推荐人");
    public static final CodeMsg ACCOUNT_LCOKED = new CodeMsg(2037, "账户已锁定");
    public static final CodeMsg UNKNOW_ACCOUNT = new CodeMsg(2038, "账户不存在");
    public static final CodeMsg ACCOUNT_DELETE = new CodeMsg(2039, "账号已被删除");
    public static final CodeMsg SUPPLIER_ACCOUNT_DELETE = new CodeMsg(2039, "供应商账号已被删除");
    public static final CodeMsg RECOMMENDIDBINDSELF = new CodeMsg(2040, "推荐人不能绑定自己");
    public static final CodeMsg MEMBER_ADDRESS_NOT_FOUND = new CodeMsg(2041, "未查找到收货地址");
    public static final CodeMsg MEMBER_ADDRESS_NOT_COMPLETE = new CodeMsg(2042, "收货地址信息不完整");
    public static final CodeMsg GET_WX_MOBILE_ERROR = new CodeMsg(2043, "获取微信手机号错误，请重试");
    public static final CodeMsg ACCOUNT_MOBILE_NOT_EQUAL_REQUEST_MOBILE = new CodeMsg(2044, "账户手机号与请求手机号不一致，请修改后重试");
    public static final CodeMsg ADDRESS_SOMANY = new CodeMsg(2046, "最多新增%s个地址");
    public static final CodeMsg PARAM_VALIDATEION_ERROR = new CodeMsg(2048, "参数校验异常:%s");
    public static final CodeMsg RECOMMEND_INFO_ERROR = new CodeMsg(2049, "推荐人信息异常，请联系客服处理");
    public static final CodeMsg RECOMMEND_FANS_ERROR = new CodeMsg(2050, "推荐人不能绑定自己的粉丝");
    public static final CodeMsg SYS_USER_INVITATION_CODE_INVALID = new CodeMsg(2051, "无效/异常邀请码[%s]");
    public static final CodeMsg INVITATION_CODE_PATTERN_WRONG = new CodeMsg(2052, "邀请码格式错误");

    // 订单模块 3XXX
    public static final CodeMsg ORDER_NOT_EXIST = new CodeMsg(3001, "订单不存在");
    public static final CodeMsg ORDER_REDPAPER_INVALID = new CodeMsg(3002, "红包已失效");
    public static final CodeMsg ORDER_REDPAPER_EXP_INVALID = new CodeMsg(3003, "红包已过期");
    public static final CodeMsg ORDER_GOODS_NUM_NOT_ENOUGH_WITH_STOCKNUM = new CodeMsg(3004, "%s剩余库存为%s请修改购物车数量重新提交");
    public static final CodeMsg TOO_MANY_ORDER_UPPAID = new CodeMsg(3006, "您有太多订单未完成支付，请先支付或者取消未支付的订单");
    public static final CodeMsg JSAPI_TICKET_FAIL = new CodeMsg(3008, "获取 JsapiTicket 失败");
    public static final CodeMsg WXPAYREFUND_FAIL = new CodeMsg(3010, "微信退款调用-退款失败");
    public static final CodeMsg ORDER_REDPAPER_LIMIT_TIME_ERROR = new CodeMsg(3014, "该红包仅限 %s ~ %s 时间段内使用");
    public static final CodeMsg ORDER_NULL = new CodeMsg(3016, "未查询到该订单");
    public static final CodeMsg ORDER_INVALID = new CodeMsg(3017, "订单已失效");
    public static final CodeMsg SECKILL_GOODS_CAN_NOT_USE_THIS_REDPAPER = new CodeMsg(3018, "活动商品不允许使用当前红包");
    public static final CodeMsg GOODS_NOT_IN_CART = new CodeMsg(3019, "商品'%s'不在购物车");
    public static final CodeMsg GOODS_ORDER_LIMIT = new CodeMsg(3020, "商品%s每单限购 %s 件");
    public static final CodeMsg GOODS_DAY_LIMIT = new CodeMsg(3021, "商品%s每日限购 %s 件");
    public static final CodeMsg ORDER_STATUS_ERROR = new CodeMsg(3022, "订单状态异常");
    public static final CodeMsg REFUND_ORDER_NOT_EXIST = new CodeMsg(3022, "退款订单不存在");
    public static final CodeMsg GOODS_ALL_ORDER_LIMIT = new CodeMsg(3023, "商品%s每个账号限购 %s 件");

    public static final CodeMsg REDPAPER_CONSUMED = new CodeMsg(3050, "红包已被使用");
    public static final CodeMsg REDPAPER_LOCKED = new CodeMsg(3051, "红包冻结中");
    public static final CodeMsg REDPAPER_LIMIT_TYPE = new CodeMsg(3052, "红包仅限%s使用");
    public static final CodeMsg REDPAPER_NOT_YOURS = new CodeMsg(3053, "红包不存在");
    public static final CodeMsg UNSUPPORT_EXPRESS_TYPE = new CodeMsg(3054, "不支持的订单配送类型");
    public static final CodeMsg THIRD_PAY_AMOUNT_CAN_BE_ZERO = new CodeMsg(3055, "支付金额不允许小于1分");
    public static final CodeMsg CANNOT_OPERATE_NOT_YOUR_ORDERS = new CodeMsg(3056, "不允许操作非本人的订单");
    public static final CodeMsg ORDER_STATUS_MUST_BE_UNPAID = new CodeMsg(3057, "只能取消未支付订单");
    public static final CodeMsg ORDER_STATUS_IS_NOT_COMPLETE = new CodeMsg(3058, "订单未完成");
    public static final CodeMsg HEAD_INFO_INVALID = new CodeMsg(3059, "站点参数不完整");
    public static final CodeMsg HEAD_NOT_EXIST_OR_HEAD_IS_NOT_SELF = new CodeMsg(3060, "无效团长/不是自营团长");
    public static final CodeMsg ORDER_SHARE_EXPIRE = new CodeMsg(3061, "订单分享链接已过期");

    // 会员
    // 会员地址
    public static final CodeMsg MEMBER_ADDRESS_NOT_VALID_FOR_CURRENT_GOODS = new CodeMsg(3501, "该收货地址不支持当前商品");
    public static final CodeMsg AVA_BALANCE_IS_ZERO = new CodeMsg(3502, "用户可用余额为0");
    public static final CodeMsg ADCODE_IS_NULL = new CodeMsg(3503, "行政区编码不允许为空");
    public static final CodeMsg CURRENT_ADDRESS_NOT_SUPPORT_SEND = new CodeMsg(3504, "当前地址不支持配送");
    public static final CodeMsg ILLEGAL_STOREHOUSE_CODES = new CodeMsg(3505, "非法仓库参数：%s");
    public static final CodeMsg ILLEGAL_ADCODE = new CodeMsg(3505, "非法区域编码：%s");
    public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(3506, "手机号未注册会员");
    // 商品 40XX - 44XX
    public static final CodeMsg STOREHOUSE_HAS_STOCK = new CodeMsg(4001, "仓库:%s 还有库存未下架,请先下架");
    public static final CodeMsg STOREHOUSE_GOODS_PRICE_NULL = new CodeMsg(4002, "仓库商品价格未设置,请先设置价格");
    public static final CodeMsg GOODS_AUDITED = new CodeMsg(4003, "商品:%s已审核，请勿重复审核");
    public static final CodeMsg GOODS_UNAUDITED = new CodeMsg(4004, "商品:%s已是未审核状态");
    public static final CodeMsg GOODS_FIELD_IS_NULL = new CodeMsg(4005, "商品:%s的字段:%s为空");
    public static final CodeMsg GOODS_DISPLAY_ID_EXIST = new CodeMsg(4006, "商品显示编码:%s已存在");
    public static final CodeMsg GOODS_NOT_EXIST = new CodeMsg(4007, "商品:%s不存在");
    public static final CodeMsg GOODS_IS_DELETE = new CodeMsg(4012, "商品已删除或商品不存在");
    public static final CodeMsg ORDER_DAY_LIMIT_UNDER_MINUS_ONE = new CodeMsg(4013, "每单限购及每日限购不允许小于-1");
    public static final CodeMsg GOODS_IS_DELETE_CAN_NOT_UPDATE = new CodeMsg(4014, "已删除商品不允许修改");
    public static final CodeMsg BIG_TYPE_CODE_DUPLICATE = new CodeMsg(4015, "商品大类代码%s已存在");
    public static final CodeMsg MIDDLE_TYPE_CODE_DUPLICATE = new CodeMsg(4016, "商品中类代码%s已存在");
    public static final CodeMsg BIG_TYPE_DELETE = new CodeMsg(4017, "商品大类%s已删除");
    public static final CodeMsg INVLID_BIG_TYPE = new CodeMsg(4017, "无效商品类别%s");
    public static final CodeMsg MIDDLE_TYPE_DELETE = new CodeMsg(4018, "商品中类%s已删除");
    public static final CodeMsg GROUP_END_DATE_AS_PICK_DATE = new CodeMsg(4019, "团购结束时间和配送时间不能为同一天");
    public static final CodeMsg GROUP_PICK_TIME_EARLY_THAN_NOW = new CodeMsg(4020, "团购配送时间必须晚于现在");
    public static final CodeMsg UNIT_CODE_EXIST = new CodeMsg(4022, "计量单位代码已存在");
    public static final CodeMsg PARAM_ID_EXIST = new CodeMsg(4023, "系统参数代码已存在");
    public static final CodeMsg GOODS_ID_DISPLAY_ID_NAME_ALL_NULL = new CodeMsg(4025, "商品编号、显示编号、名称查询条件不能同时为空");
    public static final CodeMsg SHORTAGE = new CodeMsg(4027, "商品库存不足");
    public static final CodeMsg GOODS_IS_SOLD_OUT = new CodeMsg(4027, "商品已售罄");
    public static final CodeMsg GOODS_IS_OUT_OF_STOCK = new CodeMsg(4028, "商品%s库存不足");
    public static final CodeMsg RECOMMEND_STOREHOUSE_TYPE_EXIST = new CodeMsg(4029, "本仓库已存在此推荐类型");
    public static final CodeMsg SALE_PRICE_IS_EMPTY = new CodeMsg(4030, "商品价格为空");
    public static final CodeMsg DUPLICATE_GOODS_GROUP = new CodeMsg(4031, "优选购商品时间重合");
    public static final CodeMsg GOODS_NUM_LESS_THAN = new CodeMsg(4032, "商品库存不足%s件");
    public static final CodeMsg GOODS_ORDER_LIMIT_NUM_IS = new CodeMsg(4033, "商品每单限购%s件");
    public static final CodeMsg GOODS_DAY_LIMIT_NUM_IS = new CodeMsg(4034, "商品每天限购%s件");
    public static final CodeMsg GOODS_ALL_LIMIT_NUM_IS = new CodeMsg(4035, "商品每个用户限购%s件");
    public static final CodeMsg GOODS_IS_LIMIT = new CodeMsg(4021, "商品当前不可售");
    public static final CodeMsg COMMISSION_RATION_WRONGFUL = new CodeMsg(4099, "商品佣金比例不合法");
    public static final CodeMsg MUST_ONLY_COLER_OR_PICTURE = new CodeMsg(4100, "标签颜色和图片有且只能选择其中一个");
    public static final CodeMsg MIDDLE_TYPE_EXIST = new CodeMsg(4101, "存在未删除的中类");
    public static final CodeMsg GOODS_EXIST = new CodeMsg(4102, "存在已关联的商品");
    public static final CodeMsg INFO_NOT_NULL = new CodeMsg(4103, "%s信息必填");
    public static final CodeMsg CREATE_GOODS_POSTER_ERROR = new CodeMsg(4104, "生成商品海报异常，请重试");
    public static final CodeMsg SECOND_KILL_TIME_NOT_EXIST = new CodeMsg(4105, "秒杀时间不存在");
    public static final CodeMsg OFFSALE_GOODS_CANNOT_DISPLAY = new CodeMsg(4105, "未上架的商品不可设置显示");
    public static final CodeMsg PHOTO_TOO_MUCH = new CodeMsg(4106, "商品图片太多了");
    public static final CodeMsg DETAIL_PHOTO_TOO_MUCH = new CodeMsg(4105, "详情图片太多了");
    public static final CodeMsg CREATE_WX_A_CODE_ERROR = new CodeMsg(4106, "创建微信小程序码错误");
    public static final CodeMsg ORDER_LIMIT_VALUE_IS_INVALID = new CodeMsg(4107, "无效每单限购量 %s");
    public static final CodeMsg DAY_LIMIT_VALUE_IS_INVALID = new CodeMsg(4108, "无效每日限购量 %s");
    public static final CodeMsg SALE_PRICE_VALUE_IS_INVALID = new CodeMsg(4109, "无效售价值 %s");
    public static final CodeMsg HAS_NOT_ONSHELF = new CodeMsg(4110, "商品未上架");
    public static final CodeMsg CACHE_ERROR = new CodeMsg(4109, "缓存异常%s");
    public static final CodeMsg GOODS_NOT_DISPLAY = new CodeMsg(4110, "编号为%s的商品未设置显示");
    public static final CodeMsg STOCK_NUM_EMPTY = new CodeMsg(4111, "编号为%s的商品未无库存");
    public static final CodeMsg GOODS_OFF_SALE = new CodeMsg(4112, "商品 %s 已下架");
    public static final CodeMsg NOT_GOODS_RECOMMEND_ONLY_ONE = new CodeMsg(4113, "非商品详情的推荐只能有一个");
    public static final CodeMsg NO_GOODS_INFO_CHANGE = new CodeMsg(4114, "未修改任何商品信息");

    // 购物车
    public static final CodeMsg NOT_FIND_PROVINCE_BY_ADCODE = new CodeMsg(4400, "不存在匹配 %s 的省份信息");
    public static final CodeMsg CART_ALL_AD_CODE_PROVINCE_CODE_CAN_NOT_BOTH_NULL = new CodeMsg(4401, "adcode和provinceCode不允许同时为空");
    public static final CodeMsg SINGLE_STOREHOUSE_CART_GOODS_NUM_OVER_LIMIT = new CodeMsg(4402, "单个仓库购物车商品数量不允许超过%s个");
    public static final CodeMsg CREATE_ORDER_ERROR = new CodeMsg(4406, "生成订单失败，请重试");

    //团长+小区+仓库 5XXX
    public static final CodeMsg COMMUNITY_IS_DELETE = new CodeMsg(5004, "该小区已删除");
    public static final CodeMsg CommunityInfoExist = new CodeMsg(5006, "存在已关联的小区");
    public static final CodeMsg CommunityHeadInfoExist = new CodeMsg(5006, "存在已关联的团长");
    public static final CodeMsg ONE_MEMBER_ID_CANT_BIND_MORE_THAN_ONE_COMMUNITY_HEAD = new CodeMsg(5007, "一个会员只允许绑定一个团长，请联系客服处理");
    public static final CodeMsg COMMUNITY_INFO_ERROR = new CodeMsg(5008, "团长信息异常");
    public static final CodeMsg APPLY_REPEAT = new CodeMsg(5009, "您已经申请过了，无需再次申请");

    public static final CodeMsg BANK_CARD_NO_ERROR = new CodeMsg(5014, "不支持的银行卡号");
    public static final CodeMsg STOREHOUSE_STOP_SALE = new CodeMsg(5016, "当前仓库停售中");
    public static final CodeMsg MEMBER_HAS_BEEN_BIND = new CodeMsg(5017, "会员已被团长：%s绑定");
    public static final CodeMsg HAS_REMIT = new CodeMsg(5018, "存在已打款申请记录");
    public static final CodeMsg COMMUNITY_HEAD_NUM_FULL = new CodeMsg(5019, "团上数量已达上限");

    public static final CodeMsg ILLEGAL_STOREHOUSE_CODE = new CodeMsg(5050, "无效仓库编码%s");
    public static final CodeMsg QUERY_CALL_CENTER_GROUP_ID = new CodeMsg(5051, "获取客服组异常");
    //支付+退款 6XXX
    public static final CodeMsg REPEATPAY = new CodeMsg(6001, "订单已经支付");
    public static final CodeMsg WX_PAY_ERROR = new CodeMsg(6002, "微信支付异常，请联系客服处理(原因:%s)");
    public static final CodeMsg ALIPAY_PAY_ERROR = new CodeMsg(6003, "支付宝支付异常");
    public static final CodeMsg ALIPAY_PAY_NOTIFY_RSA_CHECK_ERROR = new CodeMsg(6004, "支付宝支付回调通知验签失败");
    public static final CodeMsg UNSUPPORTED_PAY_TYPE = new CodeMsg(6006, "不支持的支付方式");
    public static final CodeMsg ALIPAY_QUERY_ERROR = new CodeMsg(6013, "支付宝查询订单信息异常");
    public static final CodeMsg WECHAT_PAY_QUERY_ERROR = new CodeMsg(6014, "微信查询订单信息异常");
    public static final CodeMsg TRANSACTION_ID_AND_OUT_TRADE_NO_AND_OUT_REQUEST_NO_AND_REFUND_ID_ALL_NULL = new CodeMsg(6016, "微信订单号、商户订单号、商户退款单号和微信退款单号四选一");
    public static final CodeMsg QUERY_ERROR = new CodeMsg(6017, "查询失败: %s");
    public static final CodeMsg PLEASE_GIVE_ME_REQUEST_PARAM_COMMUNITY_HEAD_ID = new CodeMsg(6018, "团长编号为空");
    public static final CodeMsg HAVE_SELF_GOODS_MUST_POST_SELF_COMMUNITY_HEAD_ID = new CodeMsg(6019, "购买自营商品必须带有自营团长信息");
    public static final CodeMsg NOT_SELF_COMMUNITY_HEAD = new CodeMsg(6020, "非自营团长");
    public static final CodeMsg NOT_FIND_VITUAL_COMMUNITY_HEAD = new CodeMsg(6021, "未查找到全国仓虚拟站点信息");
    public static final CodeMsg MUST_ONE_TIME_CODITION_NEED = new CodeMsg(6022, "至少有一个时间查询条件");
    public static final CodeMsg MAX_TIME_BETWEEN_OVER = new CodeMsg(6023, "时间区间最大不超过3个月");

    public static final CodeMsg CANNOTREFUND = new CodeMsg(6057, "无法申请退款");
    public static final CodeMsg WX_QUERY_ERROR = new CodeMsg(6058, "微信支付错误:%s");
    public static final CodeMsg CANNOT_CANCEL_OTHERS_ORDER = new CodeMsg(6059, "不允许取消他人订单");
    public static final CodeMsg AUDIT_AMOUNT_BIG_THAN_GOODS_PRICE = new CodeMsg(6060, "审核金额大于商品金额");
    public static final CodeMsg RETURN_MONEY_MUST_BIG_THAN_ZERO = new CodeMsg(6061, "退款金额必须大于0");
    public static final CodeMsg RETURN_ASK_HAS_NOT_AUDIT = new CodeMsg(6062, "退款申请未审核");
    public static final CodeMsg RETURN_ASK_HAS_AUDIT = new CodeMsg(6063, "退款申请已审核");
    public static final CodeMsg RETURN_ASK_NOT_AGREE = new CodeMsg(6064, "退款申请未同意");
    public static final CodeMsg RETURN_ASK_IS_NOT_CUSTOMER_APPLY = new CodeMsg(6065, "退款申请类型不是顾客申请退款");
    public static final CodeMsg NOT_NEED_SUPPLIER_AUDIT = new CodeMsg(6065, "无需供应商审核");
    public static final CodeMsg AUDIT_MONEY_IS_NULL = new CodeMsg(6066, "退款金额不能为空");
    public static final CodeMsg OVERDUE_REFUND_TIME = new CodeMsg(6067, "订单已超过 %s 分钟，无法直接退款");
    public static final CodeMsg ADDRESS_PHONE_TAKE_NAME_CAN_NOT_ALL_NULL = new CodeMsg(6067, "收货地址与取货人/手机号不允许同时为空");
    public static final CodeMsg UNSUPPORTED_BUY_TYPE = new CodeMsg(6068, "不支持的购买渠道");
    public static final CodeMsg NOT_SUPPORT_THIS_BUY_TYPE_REFUND = new CodeMsg(6069, "暂时不支持此种购买方式的退款");

    // 运营活动（砍价、秒杀） 7XXX
    public static final CodeMsg SECKILL_IS_OVER = new CodeMsg(7003, "秒杀已结束");
    public static final CodeMsg SECKILL_GOOD_EXIST = new CodeMsg(7006, "秒杀商品已存在");
    public static final CodeMsg SECKILL_GOODS_QUERY_CONDITION_IS_NULL = new CodeMsg(7007, "请输入秒杀起止时间和仓库");
    public static final CodeMsg SECKILL_TIME_INTERSECT = new CodeMsg(7010, "秒杀时间与已添加秒杀的时间冲突");
    public static final CodeMsg SECKILL_OUT_OF_STOCK = new CodeMsg(7011, "秒杀商品 %s 库存不足");
    public static final CodeMsg SECKILL_STOCK_LESS_THAN = new CodeMsg(7012, "秒杀商品 %s 库存不足 %s 件");
    // 查询统计 8XXX
    public static final CodeMsg START_TIME_NOT_LATER_THAN_END_TIME = new CodeMsg(8002, "起始时间必须小于终止时间");
    public static final CodeMsg EXPORT_ERROR = new CodeMsg(8003, "导出失败");
    public static final CodeMsg EXPORT_DATA_IS_NULL = new CodeMsg(8004, "导出数据为空");
    public static final CodeMsg QUERY_DATE_NOT_EXCEED_ONE_MONTH = new CodeMsg(8001, "不能查询超过一个月间距的数据");
    public static final CodeMsg MUST_ONLY_PICK_OR_OPEARTION_TIME = new CodeMsg(4100, "配送时间与下单时间条件有且只能选择其中一个");
    public static final CodeMsg TIME_MUST_LESS_THAN_SOME_DAYS = new CodeMsg(4101, "时间间隔不能超过%s天");

    // 配送 9XXX
    public static final CodeMsg ROUTE_HEADER_NOT_CHANGE = new CodeMsg(9006, "团长已处于不可修改路线状态");
    public static final CodeMsg ROUTE_DRIVER_NOT_CHANGE = new CodeMsg(9007, "该线路已处于不可修改状态");
    public static final CodeMsg ZONE_OVERLAPPING = new CodeMsg(9008, "所画区域与其他区域有重叠");
    public static final CodeMsg VEHICHLE_EXIT = new CodeMsg(9008, "该车辆已有司机使用中");


    //团长端
    public static final CodeMsg RETURN_ASK_NOT_FOUND = new CodeMsg(700501, "退款申请信息未找到");
    public static final CodeMsg REFUND_IS_HELPED = new CodeMsg(6055, "退款申请已协助，请勿重复提交");
    public static final CodeMsg REFUND_IS_OVER = new CodeMsg(700507, "退款申请已审核，已无法进行协助");
    public static final CodeMsg HEADER_INFO_NOT_COMPLETE = new CodeMsg(800213, "团长信息不完整，请联系客服处理");
    public static final CodeMsg PASSWORD_NEW_IS_OLD = new CodeMsg(800202, "新密码与原密码相同");
    public static final CodeMsg HEADER_COUNT_IS_DELETED = new CodeMsg(5015, "团长账号已删除，无法进行此操作");
    public static final CodeMsg WITHDRAW_AMOUNT_MORE_THAN_COMMISSION = new CodeMsg(800207, "提现申请金额大于账户余额");
    public static final CodeMsg WITHDRAW_AMOUNT_MORE_THAN_HIGH_LIMIT = new CodeMsg(800208, "提现申请金额大于单次最大可提现金额");
    public static final CodeMsg WITHDRAW_AMOUNT_MORE_THAN_LOW_LIMIT = new CodeMsg(800209, "提现申请金额小于单次提现最小金额");

    public static final CodeMsg EXIST_UNDONE_WITHDRAW_APPLY = new CodeMsg(800210, "存在未完成提现申请");
    public static final CodeMsg PAY_PASSWORD_ERROR = new CodeMsg(800211, "支付密码错误");
    public static final CodeMsg WITHDRAW_APPLY_SQL_ERROR = new CodeMsg(800212, "插入提现申请记录失败");
    public static final CodeMsg NO_EXPRESS_INFO_MATCH_CONDITION = new CodeMsg(800305, "不存在符合条件的配送信息");
    public static final CodeMsg INSERT_EXPRESS_ARRIVE_INFO_ERROR = new CodeMsg(800306, "插入配送到货信息失败");
    public static final CodeMsg ORDER_INFO_NOT_FIND = new CodeMsg(800406, "未查找到订单信息");
    public static final CodeMsg ORDER_INFO_NOT_FIND_OR_NOT_TODAY = new CodeMsg(800406, "未查找到订单信息或非今日取货订单-%s");
    public static final CodeMsg HEAD_ID_NOT_NULL = new CodeMsg(800406, "团长编号不允许为空");
    public static final CodeMsg DATE_FORMAT_ERROR = new CodeMsg(800001, "时间解析失败");
    public static final CodeMsg COMMISSION_NOT_ENOUGH = new CodeMsg(800002, "佣金不足");
    public static final CodeMsg AMOUNT_LESS_THAN_ZERO = new CodeMsg(800003, "提现金额小于零");
    public static final CodeMsg RATIO_OUT_OF_RANGE = new CodeMsg(800004, "佣金比例范围必须为0.001-0.999");
    public static final CodeMsg SUPPLIER_LINKMAN_EXIT = new CodeMsg(900001, "联系人号码已被存在");


    // 提现汇款
    public static final CodeMsg NO_TRANSFER_INFO = new CodeMsg(1000001, "没有需要汇款的提现记录");
    public static final CodeMsg WITHDRAW_TIME_MORE_THAN_LIMIT = new CodeMsg(1000002, "提现次数超出单月最大提现次数%s次");
    public static final CodeMsg WITHDRAW_AMOUNT_MORE_THAN_LIMIT = new CodeMsg(1000003, "申请提现金额大于本月剩余可提现金额%s");
    public static final CodeMsg WITHDRAW_AMOUNT_MORE_THAN_REMAIN = new CodeMsg(1000004, "提现申请金额大于账户余额");
    public static final CodeMsg WITHDRAW_AMOUNT_CAN_NOT_BE_NULL = new CodeMsg(1000005, "提现金额不允许为空");
    public static final CodeMsg ILLEGAL_WITHDRAW_AMOUNT = new CodeMsg(1000006, "非法提现金额数值");

    //public static final CodeMsg TRANSFER_INFO_NOT_CONTINUE = new CodeMsg(1000002, "需要汇款的提现记录编号不连续");


    public static final CodeMsg QQ_REP = new CodeMsg(200000, "qq30天内已经提交过,不能再次提交");


    private int code;
    private String msg;

    public CodeMsg() {
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}
