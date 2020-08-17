package tmall.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Miracle- on 2020/8/16 14:02
 */
public class DateUtil {
    public static Timestamp d2t(Date date) {
        if (null==date) {
            return null;
        }
        return new Timestamp(date.getTime());
    }
    public static Date t2d(Timestamp timestamp) {
        if (null==timestamp) {
            return null;
        }
        return new Date(timestamp.getTime());
    }
}
