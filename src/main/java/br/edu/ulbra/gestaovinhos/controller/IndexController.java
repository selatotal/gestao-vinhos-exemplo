package br.edu.ulbra.gestaovinhos.controller;

import br.edu.ulbra.gestaovinhos.config.StringConstants;
import br.edu.ulbra.gestaovinhos.model.Role;
import br.edu.ulbra.gestaovinhos.model.Vinho;
import br.edu.ulbra.gestaovinhos.repository.VinhoRepository;
import br.edu.ulbra.gestaovinhos.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Paths;
import java.util.List;

@Controller
public class IndexController {
	@Value("${gestao-vinhos.uploadFilePath}")
	private String uploadFilePath;
	@Autowired
	SecurityService securityService;
	@Autowired
	VinhoRepository vinhoRepository;

	@RequestMapping("/")
	public String index(){
		return "redirect:/inicio";
	}

	@RequestMapping("/inicio")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("userLogged", securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals("ROLE_ADMIN")) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		List<Vinho> vinhos = (List<Vinho>) vinhoRepository.findAll();
		mv.addObject("wines", vinhos);
		return mv;
	}

	@RequestMapping("/minhalista")
	public ModelAndView minhalista() {
		ModelAndView mv = new ModelAndView("lista");
		mv.addObject("userLogged", securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals("ROLE_ADMIN")) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		mv.addObject("avaliations", securityService.findLoggedInUser().getAvaliacoes());
		return mv;
	}

	@GetMapping("/images/{fileName:.+}")
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("fileName") String fileName){
		return new FileSystemResource(Paths.get(uploadFilePath, fileName).toString());
	}

	@GetMapping("/login")
	public ModelAndView loginForm(){
		return new ModelAndView("login/login");
	}

	@GetMapping("/denied")
	public ModelAndView denied(){
		return new ModelAndView("denied");
	}
}
