package br.com.devmedia.blog.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.blog.entity.Avatar;
import br.com.devmedia.blog.entity.Usuario;
import br.com.devmedia.blog.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Transactional(readOnly = false)
	public void updateSenha(Usuario usuario) {

		repository.updateSenha(usuario.getSenha(), usuario.getId());		
	}
	
	@Transactional(readOnly = false)
	public void updateNomeAndEmail(Usuario usuario) {
		
		repository.updateNomeAndEmail(usuario.getNome(), usuario.getEmail(), usuario.getId());
	}
	
	@Transactional(readOnly = false)
	public void save(Usuario usuario) {
		
		if (usuario.getDataCadastro() == null) {
			usuario.setDataCadastro(LocalDate.now());
		}
		
		String hash = new BCryptPasswordEncoder().encode(usuario.getSenha());
		
		usuario.setSenha(hash);
		
		repository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		
		repository.delete(id);
	}
	
	public Usuario findById(Long id) {
		
		return repository.findOne(id);
	}
	
	public Usuario findByAvatar(Avatar avatar) {
		
		return repository.findByAvatar(avatar);
	}
	
	public Usuario findByEmail(String email) {
		
		return repository.findByEmail(email);
	}
	
	public List<Usuario> findAll() {
		
		return repository.findAll();
	}
}
