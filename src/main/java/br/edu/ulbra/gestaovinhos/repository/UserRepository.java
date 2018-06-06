package br.edu.ulbra.gestaovinhos.repository;

import br.edu.ulbra.gestaovinhos.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
