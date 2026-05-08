package ch.hftm.db2.ticketsystem;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
class SwaggerRedirectController {

    @GetMapping("/")
    String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
