package com.udacity.jwdnd.course1.cloudstorage.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    /*
     * HttpSecurity object by chaining methods to express security requirements.
     * 1-define how spring receive the authorization request.
     * 2-which page it requires authorization to access.
     * 3-how to handle log outs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // Handel access /h2-console
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions().sameOrigin());
        http.authorizeRequests()
                .antMatchers("/h2-console/**", "/signup", "/css/**", "/js/**").permitAll()
                // Allows authenticated users to make any request that's not explicitly covered
                // elsewhere.
                .anyRequest().authenticated();

        // Generates a login form at /login and allows anyone to access it.
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        // Redirects successful logins to the /home page
        http.formLogin()
                .defaultSuccessUrl("/home", true);

        http.logout();
    }
}
