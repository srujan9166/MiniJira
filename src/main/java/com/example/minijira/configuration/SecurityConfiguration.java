package com.example.minijira.configuration;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 

import com.example.minijira.filter.JwtFilter;
import com.example.minijira.repository.UserRepository;
import com.example.minijira.service.CustomUserDetailsService;



@Configuration
@EnableAutoConfiguration
public class SecurityConfiguration {

     private final JwtFilter jwtFilter;

    public SecurityConfiguration(@Lazy JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(crsf -> crsf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
    )
            .authorizeHttpRequests(auth -> auth
                                            .requestMatchers("/api/auth/**").permitAll()
                                            .requestMatchers("/ai/**").permitAll()
                                             
                                            
                                            .requestMatchers(
    "/swagger-ui/**",
    "/v3/api-docs/**"
).permitAll()
                                            
                                                 .anyRequest()
                                                .authenticated()
                                             )    
            .httpBasic(httpBasic -> httpBasic.disable())
.formLogin(form -> form.disable())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();   
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService , PasswordEncoder passwordEncoder){
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder);
            return new ProviderManager(authProvider);
    }



}
