package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.model.Battery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatteryService {
    private final BatteryDao dao;

    public BatteryService(BatteryDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Battery find(Integer id) {
        return dao.find(id);
    }

    public Battery getCurrentBattery() {
        Battery result = null;
         for (Battery b : dao.findAll()) {
             if (b.isInUsage()) {
                 result = b;
             }
         }
         return result;
    }
}
