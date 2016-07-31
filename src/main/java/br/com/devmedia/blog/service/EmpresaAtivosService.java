package br.com.devmedia.blog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.blog.entity.EmpresaAtivo;
import br.com.devmedia.blog.entity.TipoAtivo;
import br.com.devmedia.blog.entity.TipoOpcao;
import br.com.devmedia.blog.repository.EmpresaAtivosRepository;
import br.com.devmedia.blog.repository.EmpresaAtivosRepository.EmpresaAtivoSpecification;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EmpresaAtivosService {

	@Autowired
	private EmpresaAtivosRepository repository;

	@Transactional(readOnly = false)
	public void update(EmpresaAtivo ativo) {
		ativo.setDataAlteracao(new Date());
		repository.save(ativo);
	}

	@Transactional(readOnly = false)
	public void save(EmpresaAtivo ativo) {
		if (ativo.getDataCadastro() == null) {
			ativo.setDataCadastro(new Date());
		}
		if (ativo.getDataAlteracao() == null) {
			ativo.setDataAlteracao(new Date());
		}
		repository.save(ativo);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		EmpresaAtivo empresaAtivo = repository.findOne(id);
		empresaAtivo.setDataAlteracao(new Date());
		empresaAtivo.setDataExclusao(new Date());
		repository.save(empresaAtivo);
	}

	public EmpresaAtivo findById(Long id) {

		return repository.findOne(id);
	}

	public List<EmpresaAtivo> findByIdEmpresa(Long id) {

		return repository.findByIdEmpresa(id);
	}

	public EmpresaAtivo findByNome(String nome) {

		return repository.findByDescricao(nome);
	}

	public List<EmpresaAtivo> findAll() {

		return repository.findAll();
	}

	public EmpresaAtivo findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	public List<EmpresaAtivo> findAtivos() {
		// TODO Auto-generated method stub
		return repository.findAtivos();
	}

	public List<EmpresaAtivo> findAtivosByTipoAcao(TipoAtivo acao) {
		// TODO Auto-generated method stub
		return repository.findAtivosByTipoAcao(acao);
	}

	public List<EmpresaAtivo> findAll(EmpresaAtivoSpecification empresaAtivoCotacaoSpecification) {
		// TODO Auto-generated method stub
		return repository.findAll(empresaAtivoCotacaoSpecification);
	}

	public void createOrUpdate(EmpresaAtivo ativo) {
		if (ativo.getDataCadastro() == null) {
			ativo.setDataCadastro(new Date());
		}

		if (ativo.getDataAlteracao() == null) {
			ativo.setDataAlteracao(new Date());
		}
		repository.save(ativo);

	}

	public List<EmpresaAtivo> findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompra(Long empresaId, TipoOpcao compra,
			TipoAtivo tipoAtivo) {
		// TODO Auto-generated method stub
		return repository.findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompra(empresaId, compra, tipoAtivo);
	}

	public List<EmpresaAtivo> findByIdEmpresaAndTipoAtivoAndTipoOpcaoVenda(Long empresaId, TipoOpcao venda,
			TipoAtivo tipoAtivo) {
		// TODO Auto-generated method stub
		return repository.findByIdEmpresaAndTipoAtivoAndTipoOpcaoVenda(empresaId, venda, tipoAtivo);
	}

	public List<EmpresaAtivo> findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompraVenda(Long empresaId, TipoAtivo tipoAtivo) {
		// TODO Auto-generated method stub
		return repository.findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompraVenda(empresaId, tipoAtivo);
	}
}
