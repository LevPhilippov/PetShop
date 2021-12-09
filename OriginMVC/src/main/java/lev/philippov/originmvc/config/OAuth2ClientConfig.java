package lev.philippov.originmvc.config;

import lev.philippov.originmvc.utils.KeycloakRoleConverter;
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
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.function.Function;

@Configuration

public class OAuth2ClientConfig {

//    @Value("${keycloak.client_id}")
//    private String KEYCLOAK_CLIENT_ID;
//
//    @Value("${keycloak.client_secret}")
//    private String KEYCLOAK_CLIENT_SECRET;
//
//    private ClientRegistration clientRegistration;

//    @Autowired
//    public void setClientRegistration(ClientRegistration clientRegistration) {
//        this.clientRegistration = clientRegistration;
//    }

//    @Bean
//    public ClientRegistration clientRegistration() {
//        return ClientRegistration
//                .withRegistrationId("keycloak")
//                .clientId(KEYCLOAK_CLIENT_ID)
//                .clientSecret(KEYCLOAK_CLIENT_SECRET)
//                .scope("openid", "email", "profile", "roles")
//                .authorizationUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/auth")
//                .tokenUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/token")
//                .userInfoUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/userinfo")
//                .userNameAttributeName("id").clientName("PetShop")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("https://localhost:9443/app/login/oauth2/code/keycloak")//http://localhost:8081/login/oauth2/code/keycloak
//                .build();
//    }

//    @Bean
//    public JwtDecoderFactory<ClientRegistration> getJWTDecoder() {
//        OidcIdTokenDecoderFactory factory = new OidcIdTokenDecoderFactory();
//        factory.setJwtValidatorFactory(new Function<ClientRegistration, OAuth2TokenValidator<Jwt>>() {
//            @Override
//            public OAuth2TokenValidator<Jwt> apply(ClientRegistration clientRegistration) {
//                return new CustomTimestampIgnoringOidcTokenValidator(clientRegistration);
//            }
//        });
//    }

//    @Bean
//    public ClientRegistration clientRegistration(){
//        return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                .clientId("98a2947e1f947c6f4169")
//                .clientSecret("6b924c6e2394bd514338681119089ee2890a7fda").build();
//    }


//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(clientRegistration);
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter(){
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
//        return jwtAuthenticationConverter;
//    }


}
