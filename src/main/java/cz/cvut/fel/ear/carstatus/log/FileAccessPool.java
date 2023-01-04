package cz.cvut.fel.ear.carstatus.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileAccessPool {
    // The pool of FileAccess objects
    private BlockingQueue<FileAccess> pool;

    // The maximum number of FileAccess objects that can be created
    private int maxSize;

    // Constructor
    public FileAccessPool(int maxSize) {
        this.maxSize = maxSize;
        this.pool = new LinkedBlockingQueue<>(maxSize);

        // Populate the pool with initial FileAccess objects
        for (int i = 0; i < maxSize; i++) {
            pool.offer(new FileAccess());
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
            pool.offer(fileAccess);
        }
    }
}
