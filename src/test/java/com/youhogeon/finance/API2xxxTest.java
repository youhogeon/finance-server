package com.youhogeon.finance;
    
import static org.assertj.core.api.Assertions.*;

import com.youhogeon.finance.helper.Buffer;
import com.youhogeon.finance.repository.MinDataRepository;
import com.youhogeon.finance.repository.integrated.MinDataRepositoryImpl;
import com.youhogeon.finance.service.AppService;
import com.youhogeon.finance.service.StockService;

import org.junit.BeforeClass;
import org.junit.Test;

public class API2xxxTest {

    @BeforeClass
    public static void 초기화() throws Exception {
        new AppService();
    }

    @Test
    public void API2000_전체종목코드() {
        byte[] b = StockService.available(new byte[0]);

        assertThat(b.length)
            .isGreaterThan(0);

        assertThat(b.length % 6)
            .isEqualTo(0);
    }

    @Test
    public void API2000_부분종목코드() {
        byte[] req = "005930999001111111069500".getBytes();

        byte[] b = StockService.available(req);

        assertThat(new String(b))
            .matches("(999001069500|069500999001)");
    }

    @Test
    public void API2000_전종목현재가() {
        byte[] b = StockService.currentPrice(new byte[0]);

        assertThat(b.length)
            .isGreaterThan(0);

        assertThat(b.length % 4)
            .isEqualTo(0);
    }

    @Test
    public void API2000_지수현재가() {
        byte[] req = "999001111111999002".getBytes();
        byte[] b = StockService.currentPrice(req);

        Buffer buf = new Buffer(b);

        assertThat(buf.getSize())
            .isEqualTo(12);

        assertThat(buf.getInt(0))
            .isGreaterThan(buf.getInt(8));

        assertThat(buf.getInt(4))
            .isEqualTo(0);

        req = "999002999001".getBytes();
        b = StockService.currentPrice(req);

        buf = new Buffer(b);

        assertThat(buf.getInt(4))
            .isGreaterThan(buf.getInt(0));
    }
}
