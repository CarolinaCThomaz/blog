package br.com.devmedia.blog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.blog.entity.AtivoCotacao;
import br.com.devmedia.blog.repository.EmpresaAtivoCotacaoRepository;
import br.com.devmedia.blog.repository.EmpresaAtivoCotacaoRepository.EmpresaAtivoCotacaoSpecification;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EmpresaAtivoCotacaoService {

	@Autowired
	private EmpresaAtivoCotacaoRepository repository;

	@Transactional(readOnly = false)
	public void update(AtivoCotacao cotacao) {
		cotacao.setDataAlteracao(new Date());
		repository.save(cotacao);
	}

	@Transactional(readOnly = false)
	public void save(AtivoCotacao cotacao) {
		if (cotacao.getDataCadastro() == null) {
			cotacao.setDataCadastro(new Date());
		}

		if (cotacao.getDataAlteracao() == null) {
			cotacao.setDataAlteracao(new Date());
		}

		repository.save(cotacao);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		AtivoCotacao cotacao = repository.findOne(id);
		cotacao.setDataAlteracao(new Date());
		cotacao.setDataExclusao(new Date());
		repository.save(cotacao);
	}

	public AtivoCotacao findById(Long id) {

		return repository.findOne(id);
	}

	public List<AtivoCotacao> findAll() {

		return repository.findAll();
	}

	public AtivoCotacao findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	public List<AtivoCotacao> findByIdAtivo(Long idAtivo) {
		// TODO Auto-generated method stub
		return repository.findByIdAtivo(idAtivo);
	}

	public List<AtivoCotacao> findByIdAtivoAndUltimaDataCotacao(Long idAtivo, Date dateCotacao, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByIdAtivoAnDataCotacao(idAtivo, dateCotacao, pageable);
	}

	public AtivoCotacao findByIdAtivoAnDataCotacao(Long idAtivo, Date dateCotacao) {
		// TODO Auto-generated method stub
		return repository.findByIdAtivoAnDataCotacao(idAtivo, dateCotacao);
	}

	public Double findValorAcaoPrincipal(Long idAtivo, Date dataCotacao) {
		// TODO Auto-generated method stub
		return repository.findValorAcaoPrincipal(idAtivo, dataCotacao);
	}

	public List<AtivoCotacao> findByOpcaoDependenteAcaoPrincipal(Long idAtivo, Date dataCotacao) {
		// TODO Auto-generated method stub
		return repository.findByOpcaoDependenteAcaoPrincipal(idAtivo, dataCotacao);
	}

	public AtivoCotacao findByAtivoAndData(Long id, Date dataCotacao) {
		// TODO Auto-generated method stub
		return repository.findByAtivoAndData(id, dataCotacao);
	}

	public void createOrUpdate(AtivoCotacao ativoCotacao) {
		if (ativoCotacao.getDataCadastro() == null) {
			ativoCotacao.setDataCadastro(new Date());
		}

		if (ativoCotacao.getDataAlteracao() == null) {
			ativoCotacao.setDataAlteracao(new Date());
		}
		repository.save(ativoCotacao);
	}

	public List<AtivoCotacao> findAll(EmpresaAtivoCotacaoSpecification empresaAtivoCotacaoSpecification) {
		// TODO Auto-generated method stub
		return repository.findAll(empresaAtivoCotacaoSpecification);
	}

}
