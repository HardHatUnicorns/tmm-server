package pl.aogiri.tmm.server.controller.implementation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aogiri.tmm.server.controller.GenericController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HealthController extends GenericController {
    @GetMapping("status")
    public void status(HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
