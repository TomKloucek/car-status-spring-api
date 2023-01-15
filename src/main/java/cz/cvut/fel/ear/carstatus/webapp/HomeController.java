package cz.cvut.fel.ear.carstatus.webapp;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
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
    private static final String DIV = "</div>";
    private static final String DIV_FLEX = "<div class='flex-button'>";
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
                "  <meta http-equiv=\"refresh\" content=\"30\">"+
                "</head>\n" +
                "<body>";
        resultString += "<div class='parent'>";
        resultString += " <div class='child1'>";
        resultString += "<h3>INFORMATION ABOUT APPLICATION:</h3>";
        resultString += "<p>Current user: " + username + "</p>";
        List<Tyre> tyres = carStateService.getTyres();
        StringBuilder bld = new StringBuilder();
        for (Tyre tyre :  tyres){
            if(tyre.getPosition() == 1){
                bld.append("<p>Left front tyre current inflation: ").append(tyre.getPressure()).append(" kPa").append("</p>");
                bld.append("<p>Left front tyre current condition: ").append(tyre.getCondition()).append(" %").append("</p>");
            }
            else if(tyre.getPosition() == 3){
                bld.append("<p>Right front  tyre current inflation: ").append(tyre.getPressure()).append(" kPa").append("</p>");
                bld.append("<p>Right front tyre current condition: ").append(tyre.getCondition()).append(" %").append("</p>");
            }
            else if(tyre.getPosition() == 2){
                bld.append("<p>Left back tyre current inflation: ").append(tyre.getPressure()).append(" kPa").append("</p>");
                bld.append("<p>Left back tyre current condition: ").append(tyre.getCondition()).append(" %").append("</p>");
            }
            else if(tyre.getPosition() == 4){
                bld.append("<p>Right back tyre current inflation: ").append(tyre.getPressure()).append(" kPa").append("</p>");
                bld.append("<p>Right back tyre current condition: ").append(tyre.getCondition()).append(" %").append("</p>");
            }
        }
        resultString += bld.toString();

        resultString += "<p>Current battery capacity: " + carStateService.getBattery().getCapacity() + " %" + "</p>";
        resultString += "<p>Current battery condition: " + carStateService.getBattery().getCondition() + " %"  +"</p>";
        if (carStateService.getDriversSeat() != null) {
            resultString += "<p>Current seat position capacity: [ vertical=" + carStateService.getDriversSeat().getVerticalPosition() + " | horizontal=" + carStateService.getDriversSeat().getHorizontalPosition() + " ]" + "</p>";
        }
        List <Liquid> liquids = carStateService.getLiquids();
        bld.setLength(0);
        for(Liquid liquid : liquids){
            if(liquid.getType().equals( "cooling")){
                bld.append("<p>Current level of cooling liquid: ").append(liquid.getLevel()).append(" %").append("</p>");
            }
            if(liquid.getType().equals("braking")){
                bld.append("<p>Current level of braking liquid: ").append(liquid.getLevel()).append(" %").append("</p>");
            }
        }
        resultString += bld.toString();
        resultString += DIV;
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

        resultString += DIV;
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
            resultString += DIV_FLEX;
            resultString += "<button onclick=\"changePosition('up')\">↑</button>";
            resultString += DIV;
            resultString += DIV_FLEX;
            resultString += "<button onclick=\"changePosition('left')\">←</button>";
            resultString += DIV;
            resultString += DIV_FLEX;
            resultString += "<button onclick=\"changePosition('right')\">→</button>";
            resultString += DIV;
            resultString += DIV_FLEX;
            resultString += "<button onclick=\"changePosition('down')\">↓</button>";
            resultString += DIV;
            resultString += DIV;
        }
        resultString += "<p><button onclick=\"genRoadTrip()\">Generate roadtrip</button></p>";
        resultString += DIV;
        resultString += DIV;
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
