package testControllers;

import com.mvcFramework.annotations.controller.Controller;
import com.mvcFramework.annotations.request.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home/{1}/edit")
    public String getHomePage(){
        return "home";
    }
}
