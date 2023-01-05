package cz.cvut.fel.ear.carstatus.webapp;

import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    private final CarStateService carStateService;
    public HomeController(CarStateService carStateService) {
        this.carStateService = carStateService;
    }

    @GetMapping
    public String home(@CurrentSecurityContext(expression="authentication?.name")
                           String username){
        String resultString = "";
        resultString += "<p>Current user>: " + username + "</p>";
        List<Tyre> tyres = carStateService.getTyres();
        for (Tyre tyre :  tyres){
            if(tyre.getPosition() == 1){
                resultString += "<p>Left front tyre current inflation: " + tyre.getPressure() + "</p>";
                resultString += "<p>Left front tyre current condition: " + tyre.getCondition() + "</p>";
            }
            if(tyre.getPosition() == 2){
                resultString += "<p>Left back tyre current inflation:" + tyre.getPressure() + "</p>";
                resultString += "<p>Left back tyre current condition:" + tyre.getCondition() + "</p>";
            }
            if(tyre.getPosition() == 3){
                resultString += "<p>Right front  tyre current inflation: " + tyre.getPressure() + "</p>";
                resultString += "<p>Right front tyre current condition: " + tyre.getCondition() + "</p>";
            }
            if(tyre.getPosition() == 4){
                resultString += "<p>Right back tyre current inflation: " + tyre.getPressure() + "</p>";
                resultString += "<p>Right back tyre current condition: " + tyre.getCondition() + "</p>";
            }
        }

        resultString += "<p>Current capacity condition: " + carStateService.getBattery().getCapacity() +"</p>";
        resultString += "<p>Current battery condition: " + carStateService.getBattery().getCondition() +"</p>";
        List <Liquid> liquids = carStateService.getLiquids();
        for(Liquid liquid : liquids){
            if(liquid.getType() == "cooling"){
                resultString += "<p>Current level of cooling liquid: " + liquid.getLevel() + "</p>";
            }
            if(liquid.getType() == "braking"){
                resultString += "<p>Current level of braking liquid: " + liquid.getLevel() + "</p>";
            }
        }
        resultString += carStateService.getNotifyMalfunctions().sendMessage("");
        return resultString;
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver")
    public String user(){
        return "Hello Driver";
    }

    @PreAuthorize("hasRole('MECHANIC')")
    @GetMapping("/mechanic")
    public String mechanic(){
        return "Hello Mechanic";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin";
    }
}
