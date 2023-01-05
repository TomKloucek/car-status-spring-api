package cz.cvut.fel.ear.carstatus.commands;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.interfaces.ICommand;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class GenerateDriverCommand implements ICommand {
    private final Faker faker;
    private final Date maxDate;
    private final Date minDate;

    private final DriverService driverService;
    private final Logger logger;

    @Autowired
    public GenerateDriverCommand(DriverService driverService, Logger logger) {
        this.driverService = driverService;
        this.logger = logger;
        this.faker = new Faker();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -13);
        this.maxDate = calendar.getTime();

        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.set(Calendar.YEAR, 1970);
        firstCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        firstCalendar.set(Calendar.DAY_OF_MONTH, 1);
        this.minDate = firstCalendar.getTime();
    }

    @Override
    public void execute() {
        Driver driver = new Driver();
        Name name = faker.name();
        driver.setFirstName(name.firstName());
        driver.setLastName(name.lastName());
        driver.setUsername(name.username());
        driver.setPassword(faker.team()+faker.address().buildingNumber());
        driver.setBirthDate(faker.date().between(minDate,maxDate));
        driverService.persist(driver);
        DataClass.getInstance().incrementNumberOfDriversGenerated();
        logger.log("created driver with id:"+driver.getId(), ELoggerLevel.DEBUG);
    }
}
