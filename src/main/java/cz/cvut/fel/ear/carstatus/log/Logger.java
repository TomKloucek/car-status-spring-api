package cz.cvut.fel.ear.carstatus.log;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.EarException;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger {
    private final FileAccessPool fileAccessPool;

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Logger.class);

    public Logger() {
        this.fileAccessPool = new FileAccessPool(Constants.NUMBER_OF_FILEACCESSES_IN_POOL);
    }

    public void log(String message, ELoggerLevel level) {
        DataClass.getInstance().incrementNumberOfLoggerCalls();
        switch (level) {
            case DEBUG:
                LOG.debug(message);
                break;
            case ERROR:
                LOG.error(message);
                break;
            case INFO:
                LOG.info(message);
                break;
            case TRACE:
                LOG.trace(message);
                break;
            case WARN:
                LOG.warn(message);
                break;
            default:
                break;
        }
        try {
            FileAccess fileAccess = fileAccessPool.getFileAccess();
            fileAccess.openFile("log.txt");
            fileAccess.writeToFile(level.name() + " - " + message);
            fileAccess.closeFile();
            fileAccessPool.releaseFileAccess(fileAccess);
        }catch (InterruptedException e) {
            LOG.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            throw new EarException("Could not access file.");
        }
    }
}