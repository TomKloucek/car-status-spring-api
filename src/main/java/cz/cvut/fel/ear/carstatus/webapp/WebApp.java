package cz.cvut.fel.ear.carstatus.webapp;

import cz.cvut.fel.ear.carstatus.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;

@RestController
@RequestMapping("/")
public class WebApp {

    private final Logger logger;

    @Autowired
    public WebApp(Logger logger) {
        this.logger = logger;
    }

    @GetMapping(value = "/load_simulation",produces = MediaType.TEXT_HTML_VALUE)
    public String simForm() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/cz/cvut/fel/ear/carstatus/webapp/load_simulation.html"))) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[10];
            while (reader.read(buffer) != -1) {
                stringBuilder.append(new String(buffer));
                buffer = new char[10];
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            logger.log("Nedokazal se precist soubor ve webapp", null);
            return "Something wrong happened";
        }
    }
}
