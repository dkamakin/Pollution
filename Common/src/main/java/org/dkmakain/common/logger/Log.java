package org.dkmakain.common.logger;

import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private final Logger logger;

    private Log(Supplier<Logger> wrapper) {
        this.logger = wrapper.get();
    }

    public static Log create(Class<?> aClass) {
        return new Log(() -> LoggerFactory.getLogger(aClass));
    }

    public void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    public void trace(String message, Object... params) {
        logger.trace(message, params);
    }

    public void information(String message, Object... params) {
        logger.info(message, params);
    }

    public void warning(String message, Object... params) {
        logger.warn(message, params);
    }

    public void error(String message, Object... params) {
        logger.error(message, params);
    }

    public void exception(String message, Throwable throwable, Object... params) {
        exception(message.formatted(params), throwable);
    }

    public void exception(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
