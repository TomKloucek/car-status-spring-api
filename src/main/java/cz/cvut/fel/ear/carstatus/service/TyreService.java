package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.TyreDao;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.rest.UserController;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TyreService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserController.class);
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
        return result;
    }

    @Transactional
    public List<Tyre> findAll() {
        return dao.findAll();
    }

    public void inflateTyre(Integer id) {
        final Tyre tyre = dao.find(id);
        tyre.setPressure(33);
        dao.persist(tyre);
    }

    public void inflateTyres() {
        for (Tyre tyre : getCurrentTyres()) {
            tyre.setPressure(33);
            dao.persist(tyre);
        }
    }

    public void deleteTyre(Tyre tyre) {
        dao.remove(tyre);
    }

    public void updateTyre(Tyre tyre) {
        dao.update(tyre);
    }

    public void createNewTyre(Tyre tyre) {
        dao.persist(tyre);
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
