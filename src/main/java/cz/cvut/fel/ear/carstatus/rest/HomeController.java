package cz.cvut.fel.ear.carstatus.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "Hello World";
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver")
    public String user(){
        return "Hello Driver";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin";
    }
}
