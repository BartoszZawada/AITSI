package com.AITSI.user;

import com.AITSI.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final VehicleService vehicleService;

    @GetMapping("/")
    String homePage(){
        return "home";
    }

    @GetMapping("/info")
    String info(){
        return "info";
    }

    @GetMapping("/userInfo")
    String userInfo(Model model){
        model.addAttribute("dtos", vehicleService.findAllByUser());
        return "userInfo";
    }

    @GetMapping("/login")
    public String login(User user) {
        ModelAndView model = new ModelAndView();
        model.addObject("loginOrRegister", "login");
        model.addObject("user", user);
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(Model model, User user) {
        model.addAttribute("loginOrRegister", "register");
        userService.createUser(user);
        return "home";
    }
}
