package lev.philippov.originmvc.config;

import lev.philippov.originmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private CustomAuthenticationSuccessHandler handler;

    @Autowired
    public void setHandler(CustomAuthenticationSuccessHandler handler) {
        this.handler = handler;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/user/**").authenticated()
                .antMatchers("/order").authenticated()
                .antMatchers("/error").denyAll()
                .antMatchers("/ws/**","/ws").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
//                .defaultSuccessUrl("/")
                .successHandler(handler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll().and()
                //отключено для проверки работы SOAP-сервиса
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}