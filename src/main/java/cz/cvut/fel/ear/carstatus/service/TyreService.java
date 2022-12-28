package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.TyreDao;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TyreService {

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

    @javax.transaction.Transactional
    public List<Tyre> findAll() {
        return dao.findAll();
    }
}
