package br.edu.ulbra.gestaovinhos.controller;

import br.edu.ulbra.gestaovinhos.config.StringConstants;
import br.edu.ulbra.gestaovinhos.config.RedirectConstants;
import br.edu.ulbra.gestaovinhos.input.UserInput;
import br.edu.ulbra.gestaovinhos.model.Role;
import br.edu.ulbra.gestaovinhos.model.User;
import br.edu.ulbra.gestaovinhos.repository.RoleRepository;
import br.edu.ulbra.gestaovinhos.repository.UserRepository;
import br.edu.ulbra.gestaovinhos.service.interfaces.SecurityService;
import br.edu.ulbra.gestaovinhos.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/usuario")
public class AdminUserController {
	@Autowired
	SecurityService securityService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping()
	public ModelAndView listaUsuarios() {
		ModelAndView mv = new ModelAndView("admin/usuario/lista");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals(StringConstants.ROLE_ADMIN)) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		List<User> usuarios = (List<User>) userRepository.findAll();
		mv.addObject("users", usuarios);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novoUsuarioForm(@ModelAttribute("user") UserInput user){
		List<Role> roles = (List<Role>)roleRepository.findAll();
		ModelAndView mv = new ModelAndView("admin/usuario/novo");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals(StringConstants.ROLE_ADMIN)) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		mv.addObject("roles", roles);
		mv.addObject("user", user);
		return mv;
	}

	@PostMapping("/novo")
	public String novoUsuario(UserInput userInput, RedirectAttributes redirectAttrs){
		User usuario = userRepository.findByUsername(userInput.getUsername());

		if (usuario != null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Um usuário com esse email já está cadastrado.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_ADMIN_USUARIO_NOVO;
		}

		if (userInput.getPassword().length() == 0) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Uma senha deve ser informada.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_ADMIN_USUARIO_NOVO;
		}

		if (!userInput.getPassword().equals(userInput.getPasswordConfirm())) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Senha e confirmação de senha não são iguais.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_ADMIN_USUARIO_NOVO;
		}

		Role role = roleRepository.findById(userInput.getIdRole()).get();
		User user = mapper.map(userInput, User.class);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userService.save(user);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Usuário cadastrado com sucesso.");
		return RedirectConstants.REDIRECT_ADMIN_USUARIO;
	}

	@GetMapping("/{id}")
	public ModelAndView detalheUsuario(@PathVariable("id") Long idUsuario, RedirectAttributes redirectAttrs){
		User usuario = userRepository.findById(idUsuario).get();

		if (usuario == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "O usuário solicitado não existe.");
			return new ModelAndView(RedirectConstants.REDIRECT_ADMIN_USUARIO);
		}

		UserInput userInput = mapper.map(usuario, UserInput.class);

		Set<Role> userRoles = usuario.getRoles();
		if (!userRoles.isEmpty()){
			userInput.setIdRole(userRoles.iterator().next().getId());
		}

		List<Role> roles = (List<Role>)roleRepository.findAll();

		ModelAndView mv = new ModelAndView("admin/usuario/detalhe");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals(StringConstants.ROLE_ADMIN)) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		mv.addObject("roles", roles);
		mv.addObject("user", userInput);
		return mv;
	}

	@PostMapping("/{id}")
	public String salvarUsuario(@PathVariable("id") Long idUsuario, UserInput userInput, RedirectAttributes redirectAttrs) {
		User usuario = userRepository.findById(idUsuario).get();

		if (usuario == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Esse usuário não existe.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return "redirect:/admin/usuario/" + idUsuario;
		}

		User usuarioTest = userRepository.findByUsername(userInput.getUsername());
		if (usuarioTest != null && !usuario.getUsername().equals(usuarioTest.getUsername())) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Um usuário com esse email já está cadastrado.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_ADMIN_USUARIO + idUsuario;
		}

		if (userInput.getPassword().length() != 0 && !userInput.getPassword().equals(userInput.getPasswordConfirm())) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Senha e confirmação de senha não são iguais.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_ADMIN_USUARIO + idUsuario;
		}

		usuario.setName(userInput.getName());
		usuario.setUsername(userInput.getUsername());
		if (userInput.getPassword().length() != 0) {
			usuario.setPassword(userInput.getPassword());
		}

		userService.save(usuario);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Usuário alterado com sucesso.");
		redirectAttrs.addFlashAttribute("user", userInput);
		return RedirectConstants.REDIRECT_ADMIN_USUARIO + idUsuario;
	}

	@PostMapping("/{id}/resetSenha")
	public String resetarSenhaUsuario(@PathVariable("id") Long idUsuario){
		return RedirectConstants.REDIRECT_ADMIN_USUARIO + idUsuario;
	}

	@RequestMapping("/{id}/delete")
	public String deletarUsuario(@PathVariable("id") Long idUsuario, RedirectAttributes redirectAttrs) {
		User usuario = userRepository.findById(idUsuario).get();
		if (usuario == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Não existe uma usuário com essa identificação.");
		} else {
			userRepository.delete(usuario);
			redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Usuário deletado com sucesso.");
		}

		return RedirectConstants.REDIRECT_ADMIN_USUARIO;
	}
}
