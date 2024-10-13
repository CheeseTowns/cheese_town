package org.mascord.main.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // ANSI 코드로 콘솔에서 출력될 색상을 지정 (색상 지원)
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[91m";
    private static final String GREEN = "\u001B[92m";
    private static final String YELLOW = "\u001B[93m";
    private static final String BLUE = "\u001B[94m";
    private static final String CYAN = "\u001B[96m";

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        // 로그 레벨에 따른 색상 적용
        String color;
        if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
            color = RED;
        } else if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
            color = YELLOW;
        } else if (record.getLevel().intValue() >= Level.INFO.intValue()) {
            color = GREEN;
        } else if (record.getLevel().intValue() >= Level.FINE.intValue()) {
            color = CYAN;
        } else {
            color = BLUE;  // 기본 색상
        }

        // 시간, 레벨, 메시지에 대한 색상 적용
        builder.append(color)
                .append("[")
                .append(record.getLevel().getLocalizedName())
                .append("]")
                .append(RESET)  // 색상 리셋
                .append(" ")
                .append(formatMessage(record))
                .append("\n");

        return builder.toString();
    }
}
