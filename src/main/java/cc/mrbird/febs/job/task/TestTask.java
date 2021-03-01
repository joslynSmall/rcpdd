package cc.mrbird.febs.job.task;

import cc.mrbird.febs.tb.service.IOrderCommitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {

    @Autowired
    private IOrderCommitService orderCommitService;

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}", params);
    }

    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
        try {
//            AmountQueryRootBean cardInfoAfterPay = this.orderCommitService.getCardInfoAfterPay();
//            log.info(cardInfoAfterPay.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
