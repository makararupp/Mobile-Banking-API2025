package com.mk.mbanking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //injection bean
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    //1. Define bean: security FilterChain . // update spring version3. like arrow function.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Disable CSRF (for APIs)
        http.csrf(token -> token.disable());
        http.cors(Customizer.withDefaults());

        //Configure Http mapping URL.
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasRole("SYSTEM")
                .requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasRole("SYSTEM")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasRole("SYSTEM")
                .requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyRole("SYSTEM","ADMIN","CUSTOMER")
                .anyRequest().authenticated()
        );

        //Configure security mechanism
        http.httpBasic(httpBasic->{});

        // Make session stateless
        http.sessionManagement(session
                ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

     // 2. Define bean: AuthenticationManager.[User InMemory]
     // custom from DB
     @Bean
     public DaoAuthenticationProvider daoAuthenticationProvider() {

         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userDetailsService);
         authProvider.setPasswordEncoder(passwordEncoder);

         return authProvider;
     }



/*    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder()
                .username("sam")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("makara")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails editor = User.builder()
                .username("dara")
                .password(passwordEncoder.encode("123"))
                .roles("EDITOR")
                .build();

        return new InMemoryUserDetailsManager(user, admin, editor);
    }*/

}
