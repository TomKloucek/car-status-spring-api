package cz.cvut.fel.ear.carstatus.webapp;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    private final CarStateService carStateService;
    public HomeController(CarStateService carStateService) {
        this.carStateService = carStateService;
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC', 'ADMIN')")
    @GetMapping
    public String home(@CurrentSecurityContext(expression="authentication?.name")
                           String username){
        carStateService.updateMalfunctionality();
        String resultString = "";
        resultString += "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <link rel=\"stylesheet\" href=\"style.css\">\n" +
                " <script src=\"library.js\"></script>\n" +
                "    <script src=\"app.js\"></script>" +
                "  <meta http-equiv=\"refresh\" content=\"50\">"+
                "</head>\n" +
                "<body>";
        resultString += "<div class='parent'>";
        resultString += " <div class='child1'>";
        resultString += "<h3>INFORMATION ABOUT APPLICATION:</h3>";
        resultString += "<p>Current user: " + username + "</p>";
        List<Tyre> tyres = carStateService.getTyres();
        for (Tyre tyre :  tyres){
            if(tyre.getPosition() == 1){
                resultString += "<p>Left front tyre current inflation: " + tyre.getPressure() + " kPa" + "</p>";
                resultString += "<p>Left front tyre current condition: " + tyre.getCondition() + " %" + "</p>";
            }
            else if(tyre.getPosition() == 3){
                resultString += "<p>Right front  tyre current inflation: " + tyre.getPressure() + " kPa" + "</p>";
                resultString += "<p>Right front tyre current condition: " + tyre.getCondition() + " %" + "</p>";
            }
            else if(tyre.getPosition() == 2){
                resultString += "<p>Left back tyre current inflation: " + tyre.getPressure() + " kPa" + "</p>";
                resultString += "<p>Left back tyre current condition: " + tyre.getCondition() + " %" + "</p>";
            }
            else if(tyre.getPosition() == 4){
                resultString += "<p>Right back tyre current inflation: " + tyre.getPressure() + " kPa" + "</p>";
                resultString += "<p>Right back tyre current condition: " + tyre.getCondition() + " %" + "</p>";
            }
        }


        resultString += "<p>Current battery capacity: " + carStateService.getBattery().getCapacity() + " %" + "</p>";
        resultString += "<p>Current battery condition: " + carStateService.getBattery().getCondition() + " %"  +"</p>";
        if (carStateService.getDriversSeat() != null) {
            resultString += "<p>Current seat position capacity: [ vertical=" + carStateService.getDriversSeat().getVerticalPosition() + " | horizontal=" + carStateService.getDriversSeat().getHorizontalPosition() + " ]" + "</p>";
        }
        List <Liquid> liquids = carStateService.getLiquids();
        for(Liquid liquid : liquids){
            if(liquid.getType().equals( "cooling")){
                resultString += "<p>Current level of cooling liquid: " + liquid.getLevel() + " %" + "</p>";
            }
            if(liquid.getType().equals("braking")){
                resultString += "<p>Current level of braking liquid: " + liquid.getLevel() + " %" + "</p>";
            }
        }
        resultString += "</div>";
        resultString += " <div class='child2'>";

        String statistics = "";
        statistics += "<h3>STATISTICS:</h3>";
        statistics += "<p> Number of logger calls: " + DataClass.getInstance().getNumberOfLoggerCalls();
        statistics += "<p> Number of users registered: " + DataClass.getInstance().getNumberOfUsersRegistered();
        statistics += "<p> Number of simulation method calls: " + DataClass.getInstance().getNumberOfSimulationMethodCalls();
        statistics += "<p> Number of drivers generated: " + DataClass.getInstance().getNumberOfDriversGenerated();
        statistics += "<p> Number of drivers added: " + DataClass.getInstance().getNumberOfDriversAdded();
        statistics += "<p> Number of roads generated: " + DataClass.getInstance().getNumberOfRoadsGenerated();
        statistics += "<p> Number of roads added: " + DataClass.getInstance().getNumberOfRoadsAdded();
        statistics += "<p> Number of batteries added: " + DataClass.getInstance().getNumberOfBatteriesAdded();
        statistics += "<p> Number of tyres added: " + DataClass.getInstance().getNumberOfTyresAdded();
        statistics += "<p> Number of batteries charged: " + DataClass.getInstance().getNumberOfChargingBatteries();
        statistics += "<p> Number of batteries changed: " + DataClass.getInstance().getNumberOfBatteriesChanged();
        statistics += "<p> Number of car checks made: " + DataClass.getInstance().getNumberOfCarchecksMade();
        statistics += "<p> Number of statistics generated: " + DataClass.getInstance().getNumberOfStatisticsGenerated();
        statistics += "<p> Number of tyres inflated: " + DataClass.getInstance().getNumberOfTyresInflated();
        statistics += "<p> Number of cooling liquid refills: " + DataClass.getInstance().getNumberOfCoolingLiquidRefills();
        statistics += "<p> Number of braking liquid refills: " + DataClass.getInstance().getNumberOfBrakingLiquidReffils();
        statistics += "<p> Number of CSV files loaded: " + DataClass.getInstance().getNumberOfCSVFilesLoaded();
        statistics += "<p> Number of JSON files loaded: " + DataClass.getInstance().getNumberOfJSONFilesLoaded();

        resultString += statistics;

        resultString += "</div>";
        resultString += " <div class='child-malfunction'>";
        resultString += carStateService.getNotifyMalfunctions().sendMessage("");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        if (roles.contains("ROLE_MECHANIC")) {
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
            builder.scheme("http");
            URI newUri = builder.build().toUri();
            resultString += "<form id=\"form\" method=\"post\" action="+newUri+"rest/mechanic/make-carcheck"+">\n" +
                    "  <button value='submit' type=\"submit\" id=\"button\">Do a carcheck</button>\n" +
                    "</form>\n";
            resultString += "<p><button onclick=\"inflateTyres()\">Inflate tyres</button></p>";
            resultString += "<p><button onclick=\"rechargeBattery()\">Recharge battery</button></p>";
            resultString += "<p><button onclick=\"refillBrakingLiquid()\">Refill braking liquid</button></p>";
            resultString += "<p><button onclick=\"refillCoolingLiquid()\">Refill cooling liquid</button></p>";
        }
        else if (roles.contains("ROLE_DRIVER")) {
            resultString += "<p><button onclick=\"inflateTyres()\">Inflate tyres</button></p>";
            resultString += "<p><button onclick=\"rechargeBattery()\">Recharge battery</button></p>";
            resultString += "<p><button onclick=\"refillBrakingLiquid()\">Refill braking liquid</button></p>";
            resultString += "<p><button onclick=\"refillCoolingLiquid()\">Refill cooling liquid</button></p>";
        }
        if (roles.contains("ROLE_DRIVER")) {
            resultString += "<h2 style='color: black;'>Change driver seat position:</h2>";
            resultString += "<div class='flexbox'>";
            resultString += "<div class='flex-button'>";
            resultString += "<button onclick=\"changePosition('up')\">↑</button>";
            resultString += "</div>";
            resultString += "<div class='flex-button'>";
            resultString += "<button onclick=\"changePosition('left')\">←</button>";
            resultString += "</div>";
            resultString += "<div class='flex-button'>";
            resultString += "<button onclick=\"changePosition('right')\">→</button>";
            resultString += "</div>";
            resultString += "<div class='flex-button'>";
            resultString += "<button onclick=\"changePosition('down')\">↓</button>";
            resultString += "</div>";
            resultString += "</div>";
        }
        resultString += "<p><button onclick=\"genRoadTrip()\">Generate roadtrip</button></p>";
        resultString += "</div>";
        resultString += "</div>";
        resultString +=  "</body>\n" + "</html>";
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
