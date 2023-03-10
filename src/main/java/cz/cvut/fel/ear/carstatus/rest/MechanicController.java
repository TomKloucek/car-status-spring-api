package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.service.CarcheckService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping(value = "/make-carcheck")
    public String makeCarcheck(@CurrentSecurityContext(expression="authentication?.name")
                                 String username) {
        carcheckService.makeCarcheck(username);
        return "Car check was made successfully.";
    }
}
