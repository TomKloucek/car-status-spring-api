package cz.cvut.fel.ear.carstatus.log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class FileAccess {
    private File file;

    public FileAccess() {
        this.file = null;
    }

    public void openFile(String fileName) {
        this.file = new File(fileName);
    }

    public void closeFile() {
        this.file = null;
    }

    public void writeToFile(String message) {
        try {
            FileWriter myWriter = new FileWriter(this.file.getAbsolutePath(), true);
            myWriter.write(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new java.util.Date())+" - "+message+"\n");
            myWriter.close();
        } catch (Exception e) {
            // TODO
        }
    }
}
