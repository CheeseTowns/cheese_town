package org.mascord.main.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        // 시간 추가
        builder.append(dateformat.format(new Date(record.getMillis())));
        // 로그레벨 추가
        builder.append(" [").append(record.getLevel().getName()).append(" ]");
        // 로그 내용 추가
        builder.append(" ").append(formatMessage(record)).append("\n");

        return builder.toString();
    }
}
