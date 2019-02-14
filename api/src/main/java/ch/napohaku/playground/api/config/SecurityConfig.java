package ch.napohaku.playground.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.http.HttpMethod;
 
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String REALM_NAME = "Admin Login";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                    .withUser("user").password("{noop}user").roles("USER").and()
                    .withUser("admin").password("{noop}admin").roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
            .and().httpBasic()
            .and().csrf().disable();

         http.authorizeRequests()
            .antMatchers("/api/documentation/**").hasRole("ADMIN")
            .antMatchers("/api/browser/**").hasRole("ADMIN")
            .and()            
            .httpBasic().realmName(REALM_NAME).authenticationEntryPoint(new BrowserAuthenticationEntryPoint());

    }
}