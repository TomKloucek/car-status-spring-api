package cz.cvut.fel.ear.carstatus.log;

import cz.cvut.fel.ear.carstatus.CarState;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Logger {
    private static Logger _instance;
    // The object pool of FileAccess objects
    private FileAccessPool fileAccessPool;

    // The queue of log messages
    private BlockingQueue<String> logQueue;

    // The thread that writes log messages to a file
    private Thread logWriterThread;

    // Constructor
    private Logger(int maxFileAccessObjects) {
        this.fileAccessPool = new FileAccessPool(maxFileAccessObjects);
        this.logQueue = new LinkedBlockingQueue<>();
        this.logWriterThread = new Thread(new LogWriter());
        this.logWriterThread.start();
    }

    // Method to log a message
    public void log(String message) {
        logQueue.offer(message);
    }

    // Inner class representing the log writer thread
    private class LogWriter implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Get a FileAccess object from the pool
                    FileAccess fileAccess = fileAccessPool.getFileAccess();

                    // Open the log file
                    fileAccess.openFile("log.txt");

                    // Write the log message to the file
                    String message = logQueue.take();
                    fileAccess.writeToFile(message);

                    // Close the log file
                    fileAccess.closeFile();

                    // Return the FileAccess object to the pool
                    fileAccessPool.releaseFileAccess(fileAccess);
                } catch (InterruptedException e) {
                    // Handle the interrupted exception
                }
            }
        }
    }

    public static Logger getInstance() {
        if (_instance == null) {
            synchronized (CarState.class) {
                if (_instance == null) {
                    _instance = new Logger(3);
                }
            }
        }
        return _instance;
    }
}