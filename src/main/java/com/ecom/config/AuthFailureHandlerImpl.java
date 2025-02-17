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
import com.ecom.service.impl.UserServiceImpl;
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
			
			if (userDtls.getIsEnable()) {

				if (userDtls.getAccountNonLocked()) {

					if (userDtls.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
						userService.increaseFailedAttempt(userDtls);
					} else {
						userService.userAccountLock(userDtls);
						exception = new LockedException("Sua conta está bloqueada !! 3 tentativas fracassadas");
					}
				} else {

					if (userService.unlockAccountTimeExpired(userDtls)) {
						exception = new LockedException("Sua conta está desbloqueada !! Por favor, tente fazer o login");
					} else {
						exception = new LockedException("sua conta está bloqueada !! Por favor, tente mais tarde");
					}
				}

			} else {
				exception = new LockedException("Sua conta está inativa");
			}
			
// **************************************************************************************************			
			// Mensagem para credenciais inválidas (email existe, mas senha incorreta)
			exception = new LockedException("Credenciais inválidas. Por favor, tente novamente.");
			super.setDefaultFailureUrl("/signin?error");
			super.onAuthenticationFailure(request, response, exception);

		   } else {
		        // Redireciona para a página de cadastro com uma mensagem (email não existe)
		        request.getSession().setAttribute("errorMsg", "Email não cadastrado. Por favor, crie uma conta.");
		        response.sendRedirect("/register"); // Altere "/signup" para a rota da sua página de cadastro
		   }
	}
}	