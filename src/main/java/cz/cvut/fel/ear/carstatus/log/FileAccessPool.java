package cz.cvut.fel.ear.carstatus.log;

import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileAccessPool {
    // The pool of FileAccess objects
    private final BlockingQueue<FileAccess> pool;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FileAccessPool.class);

    // The maximum number of FileAccess objects that can be created
    private final int maxSize;

    // Constructor
    public FileAccessPool(int maxSize) {
        this.maxSize = maxSize;
        this.pool = new LinkedBlockingQueue<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            if (pool.offer(new FileAccess())) {
                LOG.info("Offering from file-access pool was successful");
            } else {
                LOG.error("Offering from file-access pool was not successful");
                break;
            }
        }
    }

    // Method to get a FileAccess object from the pool
    public FileAccess getFileAccess() throws InterruptedException {
        return pool.take();
    }

    // Method to return a FileAccess object to the pool
    public void releaseFileAccess(FileAccess fileAccess) {
        // Return the FileAccess object to the pool only if it is not at the maximum size
        if (pool.size() < maxSize) {
            if (pool.offer(fileAccess)) {
                LOG.info("Offering from file-access pool was successful");
            } else {
                LOG.error("Offering from file-access pool was not successful");
            }
        }
    }
}
