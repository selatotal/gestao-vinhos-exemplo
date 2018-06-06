package br.edu.ulbra.gestaovinhos.service.interfaces;

import br.edu.ulbra.gestaovinhos.model.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);
}
