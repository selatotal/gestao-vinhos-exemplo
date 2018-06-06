package br.edu.ulbra.gestaovinhos.repository;

import br.edu.ulbra.gestaovinhos.model.Avaliacao;
import br.edu.ulbra.gestaovinhos.model.User;
import br.edu.ulbra.gestaovinhos.model.Vinho;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvaliacaoRepository extends CrudRepository<Avaliacao, Long> {
	List<Avaliacao> findByUser(User user);
	Avaliacao findByUserAndVinho(User username, Vinho vinho);
}
