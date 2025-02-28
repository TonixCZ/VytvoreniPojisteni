package tonixcare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

    @GetMapping("/contact") // Kontakt
    public String renderContact() {
        return "pages/home/contact";
    }

    @GetMapping("/error") // Chyba
    public String error() {
        return "error";
    }
}
