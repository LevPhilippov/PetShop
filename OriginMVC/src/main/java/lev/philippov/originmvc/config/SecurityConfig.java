package lev.philippov.originmvc.config;

import com.nimbusds.jose.shaded.json.JSONArray;
import lev.philippov.originmvc.filter.SessionPopulationAfterAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/order").authenticated()
                .antMatchers("/error").denyAll()
                .antMatchers("/ws/**","/ws").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .and()
                .oauth2Login(new Customizer<OAuth2LoginConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(OAuth2LoginConfigurer<HttpSecurity> httpSecurityOAuth2LoginConfigurer) {
                        httpSecurityOAuth2LoginConfigurer.userInfoEndpoint(new Customizer<OAuth2LoginConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.UserInfoEndpointConfig>() {
                            @Override
                            public void customize(OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig userInfoEndpointConfig) {
                                userInfoEndpointConfig.oidcUserService(oidcUserService());
                            }
                        });
                    }
                })
                .csrf().disable().addFilterAfter(new SessionPopulationAfterAuthenticationFilter(),AnonymousAuthenticationFilter.class);
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return new OAuth2UserService<OidcUserRequest, OidcUser>() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                OidcUser oidcUser = delegate.loadUser(userRequest);

                final Map<String, Object> claims = oidcUser.getClaims();
                final JSONArray roles = (JSONArray) claims.get("roles");

                final Set<GrantedAuthority> mappedAuthorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(("ROLE_" + role)))
                        .collect(Collectors.toSet());

                return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }
        };
    }

}