package lev.philippov.originmvc.config;

import lev.philippov.originmvc.utils.KeycloakRoleConverter;
import lev.philippov.originmvc.utils.KeycloakRoleConverter2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

@Configuration

public class OAuth2ClientConfig {

    @Value("${keycloak.client_id}")
    private String KEYCLOAK_CLIENT_ID;

    @Value("${keycloak.client_secret}")
    private String KEYCLOAK_CLIENT_SECRET;



    @Bean
    @Profile("GITHUB")
    public ClientRegistrationRepository githubClientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(gitHubClientRegistration());
    }

    @Bean
    @Profile("KEYCLOAK")
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloackCustomClientRegistration());
    }


    public ClientRegistration keycloackCustomClientRegistration() {
        return ClientRegistration
                .withRegistrationId("keycloak")
                .clientId(KEYCLOAK_CLIENT_ID)
                .clientSecret(KEYCLOAK_CLIENT_SECRET)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("openid", "profile", "email") //при установке scope вручную получаем ErrorResponse (почему-то keycloak не принимает scope в таком виде)
                .authorizationUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/userinfo")
                .jwkSetUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/certs")
                .userNameAttributeName(IdTokenClaimNames.SUB).clientName("PetShop")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .build();
    }

    public ClientRegistration gitHubClientRegistration(){
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("98a2947e1f947c6f4169")
                .clientSecret("6b924c6e2394bd514338681119089ee2890a7fda").build();
    }

    @Bean
    @Profile("converter1")
    public JwtAuthenticationConverter jwtAuthenticationConverter1(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    @Profile("converter2")
    public JwtAuthenticationConverter jwtAuthenticationConverter2(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter2());
        return jwtAuthenticationConverter;
    }


}
