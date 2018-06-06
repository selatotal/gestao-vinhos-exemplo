package br.edu.ulbra.gestaovinhos.repository;

import br.edu.ulbra.gestaovinhos.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
