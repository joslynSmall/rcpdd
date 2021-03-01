package cc.mrbird.febs.tb.bean.contants;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: GoodsIdInRedis
 * @ProjectName febs_shiro_jwt
 * @Description: 商品常量类
 * @date 2020-04-20 19:10
 */
@Data
public class GoodsIdNameInRedis {

    public static String goodsType = "";

    public static String goodsIdTxspWeekId = "goodsid_txsp_haodian_week";

    public static String goodsIdTxspMonthId = "goodsid_txsp_haodian_month";


    public static String goodsIdTxspQidianMonthId = "goodsid_txsp_qidian_month";


    public static String goodsIdTxspKashangWeekId = "goodsid_txsp_kashang_week";

    public static String goodsIdTxspKashangMonthId = "goodsid_txsp_kashang_month";

    public static String goodsIdTxspKashangSeasonId = "goodsid_txsp_kashang_season";

    public static String goodsIdTxspKashangYearId = "goodsid_txsp_kashang_year";


    public static String goodsIdCjysKashangMonthId = "goodsid_cjys_kashang_month";

    public static String goodsIdCjysKashangSeasonId = "goodsid_cjys_kashang_season";

    public static String goodsIdCjysKashangYearId = "goodsid_cjys_kashang_year";

    public static String tbTxspMonthGoodsIds = "tb_txsp_month_goods_ids";

    public static String tbTxspWeekGoodsIds = "tb_txsp_week_goods_ids";

    public static String goodsIdMtwm1mouthId = "goods_id_mtwm_1_mouth_id";
}

