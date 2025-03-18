package com.ecom.util;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public Boolean sendMail(String url, String recipientEmail) throws UnsupportedEncodingException, MessagingException {

        System.out.println("Iniciando o método sendMail, linha 31 commonUtil");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            System.out.println("Configurando o remetente e o destinatário, linha 37 commonUtil");

            helper.setFrom("jcarlostinelli@gmail.com", "Shooping Cart");
            helper.setTo(recipientEmail);  // Corrigido nome do parâmetro

            String content = "<p>Olá,</p>" +
                             "<p>Você solicitou a redefinição de sua senha.</p>" +
                             "<p>Clique no link abaixo para alterar sua senha:</p>" +
                             "<p><a href=\"" + url + "\">Alterar minha senha</a></p>";
            helper.setSubject("Redefinição de Senha");
            helper.setText(content, true);

            System.out.println("Linha 49 enviando: " + recipientEmail + " " + content);

            mailSender.send(message);

            System.out.println("E-mail enviado com sucesso, linha 53 commonUtil");

            return true;

        } catch (MailAuthenticationException e) { 
            System.out.println("Erro ao enviar mensagem de e-mail, linha 58 commonUtil: " + e.getMessage());

      	  if (e.getCause() != null) {
      		  System.out.println("Causa raiz: " + e.getCause()); } 
      		  for (StackTraceElement element : e.getStackTrace()) {
      		  System.out.println("em " + element); 
      		  } 
      		  System.out.println("E-mail utilizado: jcarlostinelli@gmail.com");
      		  
      		  System.out.println("Propriedades JavaMail:");
      		  System.out.println("mail.smtp.host: " + System.getProperty("mail.smtp.host")); 
      		  System.out.println("mail.smtp.port: " + System.getProperty("mail.smtp.port"));
      		  System.out.println("mail.smtp.auth: " + System.getProperty("mail.smtp.auth"));
      		  System.out.println("mail.smtp.starttls.enable: " + System.getProperty("mail.smtp.starttls.enable")); 
            
            e.printStackTrace();
        	return false;            
            
        } catch (MessagingException e) {
            return false;

        } catch (Exception e) {
            System.out.println("Erro geral de e-mail, linha 80 commonUtil: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
        
    public static String generateUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURI().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    String msg = null;

    public Boolean sendMailForProductOrder(ProductOrder order, String status) throws Exception {

        msg = "<p>Olá [[name]],</p>" +
              "<p>Obrigado pelo seu pedido. A situação do seu pedido é <b>[[orderStatus]]</b>.</p>" +
              "<p><b>Detalhes do Produto:</b></p>" +
              "<p>Nome : [[productName]]</p>" +
              "<p>Categoria : [[category]]</p>" +
              "<p>Quantidade : [[quantity]]</p>" +
              "<p>Preço : [[price]]</p>" +
              "<p>Forma de Pagamento : [[paymentType]]</p>";

        System.out.println("Iniciando o método sendMailForProductOrder, linha 104 commonUtil");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        System.out.println("Configurando o remetente e o destinatário, linha 109 commonUtil");

        helper.setFrom("jcarlostinelli@gmail.com", "Loja Virtual do MAZINHO");
        helper.setTo(order.getOrderAddress().getEmail());

        msg = msg.replace("[[name]]", order.getOrderAddress().getFirstName());
        msg = msg.replace("[[orderStatus]]", status);
        msg = msg.replace("[[productName]]", order.getProduct().getTitle());
        msg = msg.replace("[[category]]", order.getProduct().getCategory());
        msg = msg.replace("[[quantity]]", order.getQuantity().toString());
        msg = msg.replace("[[price]]", order.getPrice().toString());
        msg = msg.replace("[[paymentType]]", order.getPaymentType());

        helper.setSubject("Situação do Pedido do Produto");
        helper.setText(msg, true);

        System.out.println("Enviando e-mail, linha 125 commonUtil");

        mailSender.send(message);

        System.out.println("E-mail enviado com sucesso, linha 129 commonUtil");

        return true;
    }

    public UserDtls getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        UserDtls userDtls = userService.getUserByEmail(email);
        return userDtls;
    }
}