/*
 * package com.ecom.config;
 * 
 * import java.io.IOException;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.authentication.BadCredentialsException; import
 * org.springframework.security.authentication.LockedException; import
 * org.springframework.security.core.AuthenticationException; import
 * org.springframework.security.web.authentication.
 * SimpleUrlAuthenticationFailureHandler; import
 * org.springframework.stereotype.Component;
 * 
 * import com.ecom.model.UserDtls; import com.ecom.repository.UserRepository;
 * import com.ecom.service.UserService; import com.ecom.util.AppConstant;
 * 
 * import jakarta.servlet.ServletException; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse;
 * 
 * @Component public class AuthFailureHandlerImpl extends
 * SimpleUrlAuthenticationFailureHandler {
 * 
 * @Autowired private UserRepository userRepository;
 * 
 * @Autowired private UserService userService;
 * 
 * @Override public void onAuthenticationFailure(HttpServletRequest request,
 * HttpServletResponse response, AuthenticationException exception) throws
 * IOException, ServletException {
 * 
 * String email = request.getParameter("username");
 * 
 * UserDtls userDtls = userRepository.findByEmail(email);
 * 
 * if (userDtls != null) { // Verifica se a conta está ativa if
 * (!userDtls.getIsEnable()) { exception = new
 * LockedException("Sua conta está inativa"); } // Verifica se a conta está
 * bloqueada else if (!userDtls.getAccountNonLocked()) { if
 * (userService.unlockAccountTimeExpired(userDtls)) { exception = new
 * LockedException("Sua conta está desbloqueada! Por favor, tente fazer o login."
 * ); } else { exception = new
 * LockedException("Sua conta está bloqueada! Por favor, tente mais tarde."); }
 * } // Verifica tentativas de login falhas else if (userDtls.getFailedAttempt()
 * < AppConstant.ATTEMPT_TIME) { userService.increaseFailedAttempt(userDtls); }
 * else { userService.userAccountLock(userDtls); exception = new
 * LockedException("Sua conta está bloqueada! 3 tentativas fracassadas."); }
 * 
 * // Mensagem para credenciais inválidas (email existe, mas senha incorreta)
 * exception = new
 * BadCredentialsException("Credenciais inválidas. Por favor, tente novamente."
 * ); super.setDefaultFailureUrl("/signin?error");
 * super.onAuthenticationFailure(request, response, exception);
 * 
 * } else { // Redireciona para a página de cadastro com uma mensagem (email não
 * existe) request.getSession().setAttribute("errorMsg",
 * "Email não cadastrado. Por favor, crie uma conta.");
 * response.sendRedirect("/register"); } } }
 */


package com.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");

        UserDtls userDtls = userRepository.findByEmail(email);

        if (userDtls != null) {
            // Modificação 1: Verificação explícita de conta inativa
            if (!userDtls.getIsEnable()) {
                exception = new LockedException("Sua conta está inativa"); // Mantém o estado correto
                super.setDefaultFailureUrl("/signin?error=inactive"); // Adiciona parâmetro na URL
            } 
            // Modificação 2: Verificação e persistência do bloqueio
            else if (!userDtls.getAccountNonLocked()) {
                if (userService.unlockAccountTimeExpired(userDtls)) {
                    exception = new LockedException("Sua conta está desbloqueada! Por favor, tente fazer o login.");
                    userDtls.setAccountNonLocked(true); // Atualiza status da conta
                    userRepository.save(userDtls); // Persistência no banco
                } else {
                    exception = new LockedException("Sua conta está bloqueada! Por favor, tente mais tarde.");
                }
                super.setDefaultFailureUrl("/signin?error=locked");
            } 
            // Modificação 3: Controle de tentativas falhas
            else if (userDtls.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
                userService.increaseFailedAttempt(userDtls); // Incrementa falhas
                userRepository.save(userDtls); // Persistência no banco
            } else {
                userService.userAccountLock(userDtls); // Bloqueia conta após tentativas excedidas
                userRepository.save(userDtls); // Persistência no banco
                exception = new LockedException("Sua conta está bloqueada! Tentativas excedidas.");
                super.setDefaultFailureUrl("/signin?error=failed");
            }

            // Modificação 4: Mensagens consistentes de erro
            if (exception instanceof BadCredentialsException) {
                exception = new BadCredentialsException("Credenciais inválidas. Por favor, tente novamente.");
            }

            super.onAuthenticationFailure(request, response, exception);

        } else {
            // Modificação 5: Redirecionamento aprimorado para email não cadastrado
            request.getSession().setAttribute("errorMsg", "Email não cadastrado. Por favor, crie uma conta.");
            response.sendRedirect("/register?error=emailNotFound");
        }
    }
}

