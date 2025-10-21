package com.example.demo.config;

import com.example.demo.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔹 Desactivar CSRF solo para las rutas de API
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**"))

                .authorizeHttpRequests(auth -> auth
                        // Recursos públicos
                        .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/scss/**", "/vendor/**").permitAll()

                        // 🔹 Permitir acceso a la API solo a ADMIN
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                        // Rutas de vistas protegidas
                        .requestMatchers("/home/**").hasRole("ADMIN")
                        .requestMatchers("/perfil/**").hasRole("USER")

                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll())

                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
