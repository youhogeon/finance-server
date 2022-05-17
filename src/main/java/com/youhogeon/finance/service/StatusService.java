package com.youhogeon.finance.service;

import com.youhogeon.finance.helper.Buffer;

public class StatusService {
    public static Buffer ping() {
        Buffer buf = new Buffer(4);

        buf.add(0, (int)(System.currentTimeMillis() / 1000));

        return buf;
    }

    public static boolean marketOpen() {
        //SELECT COUNT(*) FROM min_datas WHERE `date` = '2022-04-26' AND `code`='999001' AND `time` < 1001;
        return true;
    }
}
