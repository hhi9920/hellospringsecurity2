package kr.ac.hansung.cse.hellospringsecurity2.controller;

import kr.ac.hansung.cse.hellospringsecurity2.entity.Role;
import kr.ac.hansung.cse.hellospringsecurity2.entity.User;
import kr.ac.hansung.cse.hellospringsecurity2.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user, Model model) {

        if (registrationService.checkEmailExists(user.getEmail())) {//기존에 사용자가 있는지
            model.addAttribute("emailExists", true);
            return "signup";
        }
        else {
            Role role = registrationService.findByRolename("ROLE_USER");    //회원가입 받는 유저는 role 역할로 가정함

            List<Role> userRoles = new ArrayList<>();
            userRoles.add(role);

            registrationService.createUser(user, userRoles);

            return "redirect:/";
        }
    }
}