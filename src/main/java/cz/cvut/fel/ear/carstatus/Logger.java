package cz.cvut.fel.ear.carstatus;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.File;

public class Logger {
    // The object pool of FileAccess objects
    private FileAccessPool fileAccessPool;

    // The queue of log messages
    private BlockingQueue<String> logQueue;

    // The thread that writes log messages to a file
    private Thread logWriterThread;

    // Constructor
    public Logger(int maxFileAccessObjects) {
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
}