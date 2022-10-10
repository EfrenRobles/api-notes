package api.notes.auth.infrastructure.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import api.shared.infrastructure.config.JwtAuthorizationConfig;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().addFilterAfter(JwtAuthorizationConfig.build(), UsernamePasswordAuthenticationFilter.class);
        
        // Add your endpoints that need to be free from Authorization.
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/").permitAll();
        
        http.authorizeRequests().anyRequest().authenticated();

        return http.build();
    }

}
