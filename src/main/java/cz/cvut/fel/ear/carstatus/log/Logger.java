package cz.cvut.fel.ear.carstatus.log;

import cz.cvut.fel.ear.carstatus.util.Constants;
import org.springframework.stereotype.Component;

@Component
public class Logger {
    private final FileAccessPool fileAccessPool;


    public Logger() {
        this.fileAccessPool = new FileAccessPool(Constants.NUMBER_OF_FILEACCESSES_IN_POOL);
    }

    public void log(String message) {
        try {
            FileAccess fileAccess = fileAccessPool.getFileAccess();
            fileAccess.openFile("log.txt");
            fileAccess.writeToFile(message);
            fileAccess.closeFile();
            fileAccessPool.releaseFileAccess(fileAccess);
        } catch (Exception e) {
            // TODO
        }
    }
}