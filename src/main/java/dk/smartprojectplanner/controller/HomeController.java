package dk.smartprojectplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     *
     * @return returns HTML index
     */
    @GetMapping("/")
    public String showHomePage(){
        return"index";
    }
}
