package cz.cvut.fel.ear.carstatus.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig{

    private final JpaUserDetailsService jpaUserDetailsService;

    public WebSecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests(auth -> auth
//                        .mvcMatchers("/rest/battery").permitAll() //exception from authentication rule
                        .antMatchers(HttpMethod.GET, "/**").authenticated()
                        .antMatchers(HttpMethod.PUT, "/**").authenticated()
                        .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN", "DRIVER","MECHANIC")
                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailsService)
                .cors().and().csrf().disable()
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}