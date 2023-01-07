package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.TyreDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.rest.UserController;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.scanner.Constant;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TyreService {

    private final Logger logger = new Logger();
    private final TyreDao dao;

    public TyreService(TyreDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Tyre find(Integer id) {
        return dao.find(id);
    }

    public List<Tyre> getCurrentTyres() {
        List<Tyre> result = new ArrayList<>();
        for (Tyre b : dao.findAll()) {
            if (b.isInUsage()) {
                result.add(b);
            }
        }
        logger.log("Application provided information about current tyres.", ELoggerLevel.INFO);
        return result;
    }

    @Transactional
    public List<Tyre> findAll() {
        logger.log("Application found all tyres in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }

    public void inflateTyreAtPosition(Integer position) {
        List<Tyre> tyres = dao.findAll();
        for(Tyre tyre : tyres){
            if(tyre.isInUsage() && tyre.getPosition() == position){
                Tyre tyreToInflate = tyre;
                tyreToInflate.setPressure(33);
                DataClass.getInstance().incrementNumberOfTyresInflated();
                DataClass.getInstance().incrementNumberOfTyresInflated();
                dao.update(tyreToInflate);
                return;
            }
        }
        logger.log("Application could not find tyre in usage with given position.", ELoggerLevel.ERROR);
        throw new NotFoundException("Unable to find tyre in usage with given position.");
    }

    public void inflateTyre(Integer id) {
        final Tyre tyre = dao.find(id);
        tyre.setPressure(33);
        DataClass.getInstance().incrementNumberOfTyresInflated();
        dao.update(tyre);
        logger.log("Tyre with id "+ id.toString()+" was inflated.", ELoggerLevel.INFO);
    }

    public void inflateTyres() {
        for (Tyre tyre : getCurrentTyres()) {
            tyre.setPressure(33);
            DataClass.getInstance().incrementNumberOfTyresInflated();
            dao.update(tyre);
        }
        logger.log("All tyres were inflated.", ELoggerLevel.INFO);
    }

    public void deleteTyre(Tyre tyre) {
        dao.remove(tyre);
        logger.log("Tyre with ID: "+tyre.getId() +" was deleted.", ELoggerLevel.INFO);
    }
    @Transactional
    public void updateTyre(Tyre tyre) {
        dao.update(tyre);
        logger.log("Tyre with ID: "+tyre.getId() +" was updated.", ELoggerLevel.INFO);
    }

    public void createNewTyre(Tyre tyre) {
        dao.persist(tyre);
        DataClass.getInstance().incrementNumberOfTyresAdded();
        logger.log("New tyre was created.", ELoggerLevel.INFO);
    }

    public boolean changeCurrentTyre(Tyre tyre) {
        if (tyre.getPressure() < Constants.MINIMAL_TYRE_PRESSURE) {
            return false;
        }
        for (Tyre el : getCurrentTyres()) {
            if (Objects.equals(el.getPosition(), tyre.getPosition())) {
                el.setInUsage(false);
                dao.update(el);
                tyre.setInUsage(true);
                dao.update(tyre);
                DataClass.getInstance().incrementNumberOfTyresChanged();
                logger.log("Current tyre with ID: " + tyre.getId()+ " was changed.", ELoggerLevel.DEBUG);
                return true;
            }
        }
        return false;
    }

    public boolean tyresAreFunctional() {
        for (Tyre tyre : getCurrentTyres()) {
            if (tyre.getPressure() < 30) {
                return false;
            }
        }
        return true;
    }
}
