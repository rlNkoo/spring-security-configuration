package pl.rlnkoo.spring_security_configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    // Creating users in-memory
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails normalUser = User.builder()
                .username("normaluser")
                .password("$2a$12$82x7b/B5M/of8HBT4cYGQ.Hr/zIU6KOWMnQS/8XXpiOJzgeUFsRei")
                .roles("USER")
                .build();

        UserDetails adminUser = User.builder()
                .username("adminuser")
                .password("$2a$12$q9p/Mkh9Dlg0LjaQcd2sRuvW2pvu9r8KMTDaYPHFy/2XZY1anN3y6")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
