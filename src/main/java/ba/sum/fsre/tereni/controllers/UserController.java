package ba.sum.fsre.tereni.controllers;
import ba.sum.fsre.tereni.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import ba.sum.fsre.tereni.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
@Controller
public class UserController {
    @Autowired
    UserRepository userRepo;

    @GetMapping("users/add")
    public String add(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "users/add";
    }
    @PostMapping("users/add")
    public String newUser(@Valid User user,BindingResult result,Model model){
        boolean errors=result.hasErrors();
        if(errors){
            model.addAttribute("user",user);
            return "users/add";
        }
        userRepo.save(user);
        return "redirect:/users/add";
    }
}
