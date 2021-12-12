package lev.philippov.originmvc.utils;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class KeycloakRoleConverter2 implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {

        final Map<String, Object> claims = source.getClaims();
        final JSONArray roles = (JSONArray) claims.get("roles");

        final Set<GrantedAuthority> mappedAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(("ROLE_" + role)))
                .collect(Collectors.toSet());
        return mappedAuthorities;
    }
}
