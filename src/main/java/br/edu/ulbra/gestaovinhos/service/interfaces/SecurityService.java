package br.edu.ulbra.gestaovinhos.service.interfaces;

import br.edu.ulbra.gestaovinhos.model.User;

public interface SecurityService {

	String findLoggedInUsername();

	User findLoggedInUser();

	void autologin(String username, String password);
}
