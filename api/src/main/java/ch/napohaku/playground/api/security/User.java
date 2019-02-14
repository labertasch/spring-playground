package ch.napohaku.playground.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.core.Authentication;

public class User extends ResourceSupport {
    private List<String> roles = new ArrayList<String>();
    private String userName;

    public User(Authentication authentication) {
        authentication.getAuthorities().forEach(auth -> roles.add(auth.getAuthority()));        
        this.userName = authentication.getName();
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }
}