//package lev.philippov.originmvc.config;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
////import org.springframework.security.oauth2.core.OAuth2TokenValidator;
//import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
////import org.springframework.security.oauth2.jwt.Jwt;
////import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
////import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//
//
//@Configuration
//@Profile("oauth2")
//@ConfigurationProperties(value = "provider.keycloak", ignoreUnknownFields = true)
//public class OAuth2ClientConfig {
//
//    @Setter
//    private String clientId;
//    @Setter
//    private String clientSecret;
//
//    @Bean
//    @Profile("GITHUB")
//    public ClientRegistrationRepository githubClientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(gitHubClientRegistration());
//    }
//
//    @Bean
//    @Profile("KEYCLOAK")
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(keycloackCustomClientRegistration());
//    }
//
//    @Profile("KEYCLOAK")
//    public ClientRegistration keycloackCustomClientRegistration() {
//        return ClientRegistration
//                .withRegistrationId("keycloak")
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                .scope("openid", "profile", "email") //при установке scope вручную получаем ErrorResponse (почему-то keycloak не принимает scope в таком виде)
//                .authorizationUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/auth")
//                .tokenUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/token")
//                .userInfoUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/userinfo")
//                .jwkSetUri("http://localhost:8080/auth/realms/petshopdev/protocol/openid-connect/certs")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .clientName("PetShop")
//                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
//                .build();
//    }
//    @Profile("GITHUB")
//    public ClientRegistration gitHubClientRegistration(){
//        return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                .clientId("98a2947e1f947c6f4169")
//                .clientSecret("6b924c6e2394bd514338681119089ee2890a7fda").build();
//    }
//
////    @Bean
////    @Profile("converter1")
////    public JwtAuthenticationConverter jwtAuthenticationConverter1(){
////        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
////        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
////        return jwtAuthenticationConverter;
////    }
////
////    @Bean
////    @Profile("converter2")
////    public JwtAuthenticationConverter jwtAuthenticationConverter2(){
////        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
////        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter2());
////        return jwtAuthenticationConverter;
////    }
//
//
//}
