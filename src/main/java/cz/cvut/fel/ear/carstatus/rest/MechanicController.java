package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.service.CarcheckService;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/mechanic")
public class MechanicController {

    private final CarcheckService carcheckService;

    public MechanicController(CarcheckService carcheckService) {

        this.carcheckService = carcheckService;
    }
    @PreAuthorize("hasRole('MECHANIC')")
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void makeCarcheck(@CurrentSecurityContext(expression="authentication?.name")
                                 String username) {
        carcheckService.makeCarcheck(username);
    }
}
