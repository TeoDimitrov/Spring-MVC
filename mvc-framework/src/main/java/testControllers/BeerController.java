package testControllers;

import com.mvcFramework.annotations.controller.Controller;
import com.mvcFramework.annotations.parameters.PathVariable;
import com.mvcFramework.annotations.parameters.RequestParam;
import com.mvcFramework.annotations.request.GetMapping;
import com.mvcFramework.annotations.request.PostMapping;
import com.mvcFramework.models.Model;

@Controller
public class BeerController {

    @GetMapping("/beer/{id}")
    public String getBeerId(@PathVariable("id") Integer id){
        System.out.println(id);
        return "beer";
    }

    @GetMapping("/beer")
    public String getBeer(){
        return "beer";
    }

    @PostMapping("/beer")
    public String getBeerResults(@RequestParam("username") String username, @RequestParam("password") long password){
        System.out.println(username);
        System.out.println(password);
        return "redirect:/zagorka";
    }

    @GetMapping("/zagorka")
    public String getZagorka(Model model){
        model.addAttribute("beer", "I am a model beer");
        return "zagorka";
    }
}
