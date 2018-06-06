package br.edu.ulbra.gestaovinhos.service;

import br.edu.ulbra.gestaovinhos.model.User;
import br.edu.ulbra.gestaovinhos.repository.RoleRepository;
import br.edu.ulbra.gestaovinhos.repository.UserRepository;
import br.edu.ulbra.gestaovinhos.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user){
		if (user.getUsername().length() != 0) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}


}
