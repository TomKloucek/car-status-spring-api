package cz.cvut.fel.ear.carstatus.log;

import java.io.File;

public class FileAccess {
    private File file;

    // Constructor
    public FileAccess() {
        this.file = null;
    }

    // Method to open a file
    public void openFile(String fileName) {
        this.file = new File(fileName);
    }

    // Method to close a file
    public void closeFile() {
        this.file = null;
    }

    public void writeToFile(String message) {
    }
}
