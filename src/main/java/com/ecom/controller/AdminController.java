package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

	@GetMapping("/")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/loadAddProduct")
	public String loadAddProduct(Model m) {
		List<Category> categories = categoryService.getAllCategory();
		m.addAttribute("categories", categories);
		return "admin/add_product";
	}

	@GetMapping("/category")
	public String category(Model m, 
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize,
	        HttpSession session) {

	    // Validação dos parâmetros de paginação
	    if (pageSize <= 0) {
	        session.setAttribute("errorMsg", "Tamanho da página inválido. Deve ser maior que zero.");
	        return "redirect:/admin/category";
	    }
	    if (pageNo < 0) {
	        session.setAttribute("errorMsg", "Número da página inválido. Deve ser maior ou igual a zero.");
	        return "redirect:/admin/category";
	    }

	    // Obtenção da página de categorias
	    Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
	    List<Category> categorys = page.getContent();

	    // Verificação de lista vazia
	    if (categorys == null || categorys.isEmpty()) {
	        m.addAttribute("emptyMessage", "Nenhuma categoria encontrada.");
	    }

	    // Adicionando atributos ao modelo
	    m.addAttribute("categorys", categorys);
	    m.addAttribute("pageNo", page.getNumber());
	    m.addAttribute("pageSize", pageSize);
	    m.addAttribute("totalElements", page.getTotalElements());
	    m.addAttribute("totalPages", page.getTotalPages());
	    m.addAttribute("isFirst", page.isFirst());
	    m.addAttribute("isLast", page.isLast());

	    return "admin/category";
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, 
	        @RequestParam(required = false) MultipartFile file, // Alteração: Removido "value = 'file'"
	        HttpSession session) throws IOException {
	    String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
	    category.setImageName(imageName);

	    Boolean existCategory = categoryService.existCategory(category.getName());
	    if (existCategory) {
	        session.setAttribute("errorMsg", "O Nome da Categoria já existe");
	    } else {
	        Category saveCategory = categoryService.saveCategory(category);
	        if (Optional.ofNullable(saveCategory).isEmpty()) {
	            session.setAttribute("errorMsg", "Não foi Salvo! Erro interno do servidor");
	        } else {
	            File saveFile = new ClassPathResource("static/img").getFile();
	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
	                    + file.getOriginalFilename());
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            session.setAttribute("succMsg", "Salvo com Sucesso");
	        }
	    }
	    return "redirect:/admin/category";
	}	
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);

		if (deleteCategory) {
			session.setAttribute("succMsg", "Categoria Excluida com Sucesso");
		} else {
			session.setAttribute("errorMsg", "Algo Errado no Servidor");
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/edit_category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {

			if (!file.isEmpty()) {
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			session.setAttribute("succMsg", "Categoria Atualizada com Sucesso");
		} else {
			session.setAttribute("errorMsg", "Algo Errado no Servidor");
		}

		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session) throws IOException {

		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

		product.setImage(imageName);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());
		Product saveProduct = productService.saveProduct(product);

		if (!ObjectUtils.isEmpty(saveProduct)) {

			File saveFile = new ClassPathResource("static/img").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
					+ image.getOriginalFilename());

			// System.out.println(path);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			session.setAttribute("succMsg", "Produto Salvo com Sucesso");
		} else {
			session.setAttribute("errorMsg", "Algo Errado no Servidor");
		}

		return "redirect:/admin/loadAddProduct";
	}

	@GetMapping("/products")
	public String loadViewProduct(Model m, @RequestParam(defaultValue = "") String ch,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		Page<Product> page = null;
		if (ch != null && ch.length() > 0) {
			page = productService.searchProductPagination(pageNo, pageSize, ch);
		} else {
			page = productService.getAllProductsPagination(pageNo, pageSize);
		}
		m.addAttribute("products", page.getContent());

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/products";
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpSession session) {
		Boolean deleteProduct = productService.deleteProduct(id);
		if (deleteProduct) {
			session.setAttribute("succMsg", "Produto Excluido com Sucesso");
		} else {
			session.setAttribute("errorMsg", "Algo Errado no Servidor");
		}
		return "redirect:/admin/products";
	}

	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model m) {
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/edit_product";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session, Model m) {

		if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			session.setAttribute("errorMsg", "Desconto Inválido");
		} else {
			Product updateProduct = productService.updateProduct(product, image);
			if (!ObjectUtils.isEmpty(updateProduct)) {
				session.setAttribute("succMsg", "Produto Atualizado com Sucesso");
			} else {
				session.setAttribute("errorMsg", "Algo Errado no Servidor");
			}
		}
		return "redirect:/admin/editProduct/" + product.getId();
	}

	@GetMapping("/users")
	public String getAllUsers(Model m, @RequestParam Integer type) {
		List<UserDtls> users = null;
		if (type == 1) {
			users = userService.getUsers("ROLE_USER");
		} else {
			users = userService.getUsers("ROLE_ADMIN");
		}
		m.addAttribute("userType",type);
		m.addAttribute("users", users);
		return "admin/users";
	}

	@GetMapping("/updateSts")
	public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Integer id,@RequestParam Integer type, HttpSession session) {
		Boolean f = userService.updateAccountStatus(id, status);
		if (f) {
			session.setAttribute("succMsg", "Status da Conta Atualizado");
		} else {
			session.setAttribute("errorMsg", "Algo Errado no Servidor");
		}
		return "redirect:/admin/users?type="+type;
	}

	@GetMapping("/orders")
	public String getAllOrders(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
		m.addAttribute("orders", page.getContent());
		m.addAttribute("srch", false);
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/orders";
	}

	@PostMapping("/update-order-status")
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
			session.setAttribute("succMsg", "Status Atualizado");
		} else {
			session.setAttribute("errorMsg", "Status não foi Atualizado");
		}
		return "redirect:/admin/orders";
	}

	@GetMapping("/search-order")
	public String searchProduct(@RequestParam String orderId, Model m, HttpSession session,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		if (orderId != null && orderId.length() > 0) {

			ProductOrder order = orderService.getOrdersByOrderId(orderId.trim());

			if (ObjectUtils.isEmpty(order)) {
				session.setAttribute("errorMsg", "Identificação do Pedido inválido");
				m.addAttribute("orderDtls", null);
			} else {
				m.addAttribute("orderDtls", order);
			}

			m.addAttribute("srch", true);
		} else {
			Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
			m.addAttribute("orders", page);
			m.addAttribute("srch", false);

			m.addAttribute("pageNo", page.getNumber());
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("totalElements", page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			m.addAttribute("isFirst", page.isFirst());
			m.addAttribute("isLast", page.isLast());

		}
		return "admin/orders";

	}


	@GetMapping("/add-admin")
	public String loadAdminAdd() {
		return "admin/add_admin";
	}


	
	
	@PostMapping("/save-admin") 
	public String saveAdmin(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
	  
	  
	  // Imprimindo informações para depuração
	  System.out.println("Iniciando método updateProfile...");
	  System.out.println("Usuário: " + user.getEmail());
	  System.out.println("Arquivo recebido: " + (file != null ?
	  file.getOriginalFilename() : "Nenhum arquivo recebido"));
	  
	  String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename(); user.setProfileImage(imageName); 
	  UserDtls saveUser = userService.saveAdmin(user);
	  
	  if (!ObjectUtils.isEmpty(saveUser)) 
	  { 
		  if (!file.isEmpty()) 
		  { 
			  File saveFile = new ClassPathResource("static/img").getFile(); 
			  Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator + file.getOriginalFilename());
	  
	  //System.out.println(path); 
			  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); 
			  } 
		   
		  	String firstName = user.getName().split(" ")[0];
	        session.setAttribute("succMsg","" + firstName + " - Registrado com Sucesso!" ); 
		  } else { 
			  session.setAttribute("errorMsg", "Algo Errado no Servidor"); 
			  }
	  			System.out.println("Finalizando método saveAdmin..."); 
	  			return "redirect:/admin/add-admin"; 
	  	}	 	

	
	@GetMapping("/profile")
	public String profile() {
		return "admin/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
		
	    // Imprimindo informações para depuração
	    System.out.println("Iniciando método updateProfile...");
	    System.out.println("Usuário: " + user.getEmail());
	    System.out.println("Arquivo recebido: " + (img != null ? img.getOriginalFilename() : "Nenhum arquivo recebido"));

	 		
		
		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Perfil não Atualizado");
		} else {
			session.setAttribute("succMsg", "Perfil Atualizado com sucesso");
		}
		 System.out.println("Finalizando método updateProfile...");
		return "redirect:/admin/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = commonUtil.getLoggedInUserDetails(p);

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
			session.setAttribute("errorMsg", "Senha Atual Incorreta");
		}
		return "redirect:/admin/profile";
	}
}