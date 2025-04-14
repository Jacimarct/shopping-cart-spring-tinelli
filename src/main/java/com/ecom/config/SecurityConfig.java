/*
 * package com.ecom.config;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.MessageSource; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.Lazy; import
 * org.springframework.context.support.ReloadableResourceBundleMessageSource;
 * import
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 * import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.core.userdetails.UserDetailsService;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.authentication.AuthenticationSuccessHandler;
 * 
 * @Configuration public class SecurityConfig {
 * 
 * @Autowired private AuthenticationSuccessHandler authenticationSuccessHandler;
 * 
 * @Autowired
 * 
 * @Lazy private AuthFailureHandlerImpl authenticationFailureHandler;
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Bean public UserDetailsService userDetailsService() { return new
 * UserDetailsServiceImpl(); }
 * 
 * @Bean public DaoAuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider authenticationProvider = new
 * DaoAuthenticationProvider();
 * authenticationProvider.setUserDetailsService(userDetailsService());
 * authenticationProvider.setPasswordEncoder(passwordEncoder()); return
 * authenticationProvider; }
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
 * Exception { http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
 * .authorizeHttpRequests(req->req.requestMatchers("/user/**").hasRole("USER")
 * .requestMatchers("/admin/**").hasRole("ADMIN")
 * .requestMatchers("/**").permitAll())
 * .formLogin(form->form.loginPage("/signin") .loginProcessingUrl("/login")
 * .failureHandler(authenticationFailureHandler)
 * .successHandler(authenticationSuccessHandler))
 * .logout(logout->logout.permitAll());
 * 
 * return http.build(); } }
 */

package com.ecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Lazy
    private AuthFailureHandlerImpl authenticationFailureHandler; // Gerencia falhas de autenticação

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Mantém a proteção CSRF desativada, se aplicável
            .cors(cors -> cors.disable()) // Desativa CORS, caso não utilizado
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/**").permitAll()
            )
            .formLogin(form -> form
                .loginPage("/signin") // Página personalizada de login
                .loginProcessingUrl("/login") // URL de processamento de login
                .failureHandler(authenticationFailureHandler) // Gerencia falhas de login
                .successHandler(authenticationSuccessHandler) // Gerencia sucesso de login
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL para logout
                .logoutSuccessUrl("/signin?logout=true") // Redireciona após logout
                .invalidateHttpSession(true) // Invalida a sessão atual
                .deleteCookies("JSESSIONID") // Remove cookies específicos
                .permitAll()
            )
            // Adicionado: Manipulação de exceções no nível global
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/error/401"); // Redireciona para página de erro 401
                })
            );

        return http.build();
    }
}

