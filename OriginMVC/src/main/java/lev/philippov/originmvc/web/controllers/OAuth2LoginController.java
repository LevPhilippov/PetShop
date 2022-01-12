//package lev.philippov.originmvc.web.controllers;
//
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
////@Controller
//@RequestMapping("/login")
//@Profile("oauth2")
//@ConfigurationProperties(value = "provider.keycloak.login", ignoreUnknownFields = true)
//public class OAuth2LoginController {
//
//    @Setter
//    private String loginUri;
//
//    @GetMapping
//    public void loginPageRedirection(HttpServletResponse response) throws IOException {
//        response.sendRedirect(loginUri);
//    }
//}
