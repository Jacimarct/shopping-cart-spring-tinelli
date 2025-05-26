package com.ecom.controller;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.OrderItem;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.UserService;
import com.ecom.service.PedidoService;

import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

//      ******************** inserido pelo assistente	
	
//     ******************** inserido pelo assistente	*/
	

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
		Cart saveCart = cartService.saveCart(pid, uid);

		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Falha na Inserção do Produto no Carrinho");
		} else {
			session.setAttribute("succMsg", "Produto Inserido no Carrinho");
		}
		return "redirect:/product/" + pid;
	}

	@GetMapping("/cart")
	public String loadCartPage(Principal p, Model m) {
		try {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "user/cart";
		
	} catch (Exception e) {
		System.err.println("Erro ao carregar o carrinho: " + e.getMessage());
		e.printStackTrace();
		return "redirect:/user/cart";
	}
		}
	
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		cartService.updateQuantity(sy, cid);
		return "redirect:/user/cart";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

	@GetMapping("/orders")
	public String orderPage(Principal p, Model m) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("orderPrice", orderPrice);
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "user/order";
	}
	
	// Método modificado para incluir a impressão
	
	@PostMapping("/save-order") 
	public String saveOrder(@ModelAttribute OrderRequest request, Principal p, HttpSession session) { 
	    try {
	        // Obtém o usuário logado 
	        UserDtls user = getLoggedInUserDetails(p);
	  
	        // Salva o pedido 
	        orderService.saveOrder(user.getId(), request);
	  
	        // Imprime os detalhes do pedido 
	        printOrderDetails(request, user);
	  
	        session.setAttribute("succMsg", "Pedido realizado com sucesso!");
	        return "redirect:/user/success"; 
	    } catch (Exception e) {
	        session.setAttribute("errorMsg", "Erro ao processar pedido: " + e.getMessage());
	        return "redirect:/user/cart";
	    }
	}
	
	// Novo método para imprimir os detalhes do pedido 
	private void printOrderDetails(OrderRequest orderRequest, UserDtls user) {
		
		try { 
			// Formata os dados do pedido para impressão 
			String content = "=== Detalhes do Pedido ===\n" + 
						"Usuário: " + user.getName() + "\n" +
						"Endereço: " + orderRequest.getAddress() + "\n" + 
						"Itens do Pedido:\n";
// **********************************************************************	  
			// Verifica se há itens no pedido
	        if (orderRequest.getItems() != null) {
	            for (Cart item : orderRequest.getItems()) {
	                content += "- Produto: " + item.getProduct().getTitle() + 
	                          ", Quantidade: " + item.getQuantity() + 
	                          ", Preço: " + item.getProduct().getDiscountPrice() + "\n"; 
	            }
	        }	
	        
	        content += "Total: " + calculateTotal(orderRequest.getItems()) + "\n";
	        content += "==========================";	        
			
//**********************************************************************			
	  // Converte o conteúdo para bytes 
				byte[] data = content.getBytes();
	  
	  // Cria um fluxo de entrada com os dados 
				ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
	  
	  // Define o tipo de documento (texto simples) 
				DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	  
	  // Obtém a impressora padrão PrintService defaultPrintService =
	 // PrintServiceLookup.lookupDefaultPrintService();
		
				PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();		
	  
	  if (defaultPrintService != null) { 
		  // Cria o documento a ser impresso 
		  Doc doc = new SimpleDoc(inputStream, flavor, null);
	  
	  // Cria um trabalho de impressão 
		  DocPrintJob printJob = defaultPrintService.createPrintJob();
	  
	  // Define atributos de impressão (opcional) 
		  PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
	  
	  // Envia o documento para a impressora 
		  printJob.print(doc, attributes);
	  
	  System.out.println("Impressão enviada com sucesso para a impressora: " + defaultPrintService.getName());
	  
	  } else {
		  System.out.println("Nenhuma impressora padrão encontrada."); 
		  } 
	  }	catch (Exception e) { 
		  System.err.println("Erro ao imprimir: " + e.getMessage());
	  e.printStackTrace(); 
	  }
}
	// Método auxiliar para calcular o total
	private double calculateTotal(List<Cart> items) {
	    if (items == null) return 0.0;
	    
	    double total = 0.0;
	    for (Cart item : items) {
	        if (item.getProduct() != null && item.getQuantity() != null) {
	            total += item.getProduct().getDiscountPrice() * item.getQuantity();
	        }
	    }
	    return total;
	}
	 	
	/*		************************************************/
	
	@GetMapping("/success")
	public String loadSuccess() {
		return "user/success";
	}

	@GetMapping("/user-orders")
	public String myOrder(Model m, Principal p) {
		UserDtls loginUser = getLoggedInUserDetails(p);
		List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
		m.addAttribute("orders", orders);
		return "user/my_orders";
	}

	@GetMapping("/update-status")
	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
		
		try {
			commonUtil.sendMailForProductOrder(updateOrder, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!ObjectUtils.isEmpty(updateOrder)) {
			session.setAttribute("succMsg", "Situação Atualizada");
		} else {
			session.setAttribute("errorMsg", "Situação não Atualizada");
		}
		return "redirect:/user/user-orders";
	}

	@GetMapping("/profile")
	public String profile() {
		return "user/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Perfil não Atualizado");
		} else {
			session.setAttribute("succMsg", "Perfil Atualizado");
		}
		return "redirect:/user/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);

		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());

		if (matches) {
			String encodePassword = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encodePassword);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if (ObjectUtils.isEmpty(updateUser)) {
				session.setAttribute("errorMsg", "Senha não Atualizada !! Erro no Servidor");
			} else {
				session.setAttribute("succMsg", "Senha Atualizada com Sucesso");
			}
		} else {
			session.setAttribute("errorMsg", "Senha Atual Inválida");
		}

		return "redirect:/user/profile";
	}
	
// *****************************inserido pelo assistente *****************************************	
// *****************************inserido pelo assistente *****************************************	

}
