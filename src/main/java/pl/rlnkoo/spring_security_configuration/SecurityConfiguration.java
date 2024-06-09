package pl.rlnkoo.spring_security_configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(reqistry ->{
                    reqistry.requestMatchers("/home").permitAll();
                    reqistry.requestMatchers("/admin/**").hasRole("ADMIN");
                    reqistry.requestMatchers("/user/**").hasRole("USER");
                    reqistry.anyRequest().authenticated();
        })
                .build();
    }
}
