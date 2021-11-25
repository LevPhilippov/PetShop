package lev.philippov.originmvc.controllers;

import lev.philippov.originmvc.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    MailService mailService;

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("title", "Index page");
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
