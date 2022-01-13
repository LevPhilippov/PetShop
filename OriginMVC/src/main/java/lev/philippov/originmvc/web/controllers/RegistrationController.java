package lev.philippov.originmvc.web.controllers;

import lev.philippov.originmvc.exceptions.PasswordOrUsernameException;
import lev.philippov.originmvc.exceptions.UserAlreadyExistException;
import lev.philippov.originmvc.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping(path = "/loginpage")
    public String showLoginPage(){
        System.out.println("Hello from controller!");
        return "login_page";
    }


    @GetMapping(path = "/registration")
    public String showRegForm(HttpServletRequest request) {
        return "registration/reg_form";
    }

    @PostMapping(path = "/registration")
    public String showDetailsForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        if(request.getParameter("roboCheck") == null) {
            throw new RuntimeException("You are fucking robot!");
        }
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        if(phone.length()<1 || password.length()<1) {
            try{
                throw new PasswordOrUsernameException("Username or password are too short!");
            } catch (PasswordOrUsernameException e) {
                model.addAttribute("nullError",e);
                return "registration/reg_form";
            }
        }
        try {
            userService.saveNewUser(phone, password);
        } catch (UserAlreadyExistException e) {
            model.addAttribute("phone",phone);
            model.addAttribute("existError",e);
            return "registration/reg_form";
        }
        try {
            response.sendRedirect(request.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
