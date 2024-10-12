package org.mascord.main.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MascordLogger {
    // 싱글톤 패턴 인스턴스
    private static MascordLogger instance;
    private static Logger logger;

    // ANSI 코드로 콘솔에서 출력될 색상을 지정 (색상 지원)
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[91m";
    private static final String GREEN = "\u001B[92m";
    private static final String YELLOW = "\u001B[93m";
    private static final String BLUE = "\u001B[94m";

    // 싱글톤 패턴으로 인스턴스 생성 (private로 외부에서 호출할 수 없음)
    private MascordLogger(Logger logger) {
        MascordLogger.logger = logger;  // Logger 초기화

        // 기본 로그 레벨을 FINE 이하로 설정
        logger.setLevel(Level.FINE);

        // 기본 핸들러 제거
        Logger parentLogger = Logger.getLogger("");

        for (var handler : parentLogger.getHandlers()) {
            parentLogger.removeHandler(handler);
        }

        // 핸들러가 이미 추가되었는지 체크해 불필요한 핸들러를 추가하지 않도록 함
        if (logger.getHandlers().length == 0) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new LogFormatter());  // 커스텀 포맷터 사용
            logger.addHandler(handler);
        }
    }

    // 싱글톤 인스턴스를 초기화 및 반환하는 메서드
    public static void init(Logger logger) {
        if (instance == null) {
            instance = new MascordLogger(logger);  // Logger 초기화
        }
    }

    // 로그 메시지 출력 메서드
    public static void info(String message) {
        if (logger != null) {
            logger.info(message);
        }
    }

    public static void warning(String message) {
        if (logger != null) {
            logger.warning(message);
        }
    }

    public static void severe(String message) {
        if (logger != null) {
            logger.severe(message);
        }
    }

    public static void fine(String message) {
        if (logger != null) {
            logger.fine(message);
        }
    }
}
