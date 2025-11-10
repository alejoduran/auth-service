package org.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // Deshabilitar CSRF y CORS
        .csrf().disable()
        .cors().disable()

        // Configurar autorizaciones
        .authorizeRequests()
        .antMatchers("/**").permitAll() // Permitir TODOS los endpoints sin autenticación
        .anyRequest().permitAll()

        .and()

        // Configurar headers
        .headers().frameOptions().disable(); // Para H2 console
  }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
