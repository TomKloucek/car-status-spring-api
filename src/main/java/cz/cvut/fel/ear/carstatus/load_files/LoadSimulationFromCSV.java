package cz.cvut.fel.ear.carstatus.load_files;

import cz.cvut.fel.ear.carstatus.interfaces.ILoadSimulationFile;
import org.springframework.stereotype.Service;

@Service
public class LoadSimulationFromCSV implements ILoadSimulationFile {
    @Override
    public boolean readSimulationFromFile(String fileContent) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
