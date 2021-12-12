package lev.philippov.originmvc.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Value("${keycloak.login.uri}")
    private String KEYCLOAK_LOGIN_URI;


    @GetMapping
    public void loginPageRedirection(HttpServletResponse response) throws IOException {
        response.sendRedirect(KEYCLOAK_LOGIN_URI);
    }
}
