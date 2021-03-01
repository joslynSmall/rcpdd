package cc.mrbird.febs.common.enums;

/**
 * @author Joslyn
 * @Title: GoodsTimesTypeEnum
 * @ProjectName
 * @Description: 审核状态
 * @date 2019-12-21 14:44
 */
public enum GoodsTimesTypeEnum implements EnumMessage {

    week(1, "周卡"),
    month(2, "月卡"),
    year(3, "年卡"),
    cmonth(11, "超级影视月卡"),
    cyear(13, "超级影视年卡"),
    mtwmmonth(14,"美团外卖月卡");

    private Integer code;

    private String message;

    GoodsTimesTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
