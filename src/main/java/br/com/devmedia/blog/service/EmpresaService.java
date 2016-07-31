package br.com.devmedia.blog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.blog.entity.Empresa;
import br.com.devmedia.blog.repository.EmpresaRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;

	@Transactional(readOnly = false)
	public void update(Empresa empresa) {
		empresa.setDataAlteracao(new Date());
		repository.save(empresa);
	}

	@Transactional(readOnly = false)
	public void save(Empresa empresa) {
		if (empresa.getDataCadastro() == null) {
			empresa.setDataCadastro(new Date());
		}
		if (empresa.getDataAlteracao() == null) {
			empresa.setDataAlteracao(new Date());
		}
		repository.save(empresa);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		Empresa empresa = repository.findOne(id);
		empresa.setDataAlteracao(new Date());
		empresa.setDataExclusao(new Date());
		repository.save(empresa);
	}

	public Empresa findById(Long id) {

		return repository.findOne(id);
	}

	public Empresa findByNome(String nome) {

		return repository.findByDescricao(nome);
	}

	public List<Empresa> findAll() {

		return repository.findAll();
	}

	public Empresa findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
}
