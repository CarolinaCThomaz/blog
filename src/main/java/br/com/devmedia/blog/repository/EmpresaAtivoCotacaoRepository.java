package br.com.devmedia.blog.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.devmedia.blog.entity.AtivoCotacao;
import br.com.devmedia.blog.entity.EmpresaAtivo;

public interface EmpresaAtivoCotacaoRepository extends JpaRepository<AtivoCotacao, Long>,
		JpaSpecificationExecutor<AtivoCotacao> {

	@Query("SELECT i from AtivoCotacao i where i.ativo.id = :idAtivo ORDER BY i.dataCotacao DESC")
	List<AtivoCotacao> findByIdAtivo(@Param("idAtivo") Long idAtivo);

	@Query("SELECT i from AtivoCotacao i where i.ativo.id = :idAtivo AND date(i.dataCotacao) < :dataCotacao ORDER BY i.dataCotacao DESC")
	List<AtivoCotacao> findByIdAtivoAnDataCotacao(@Param("idAtivo") Long idAtivo,
			@Param("dataCotacao") Date dateCotacao, Pageable pageable);

	@Query("SELECT i from AtivoCotacao i where i.ativo.id = :idAtivo AND date(i.dataCotacao) = :hoje")
	AtivoCotacao findByIdAtivoAnDataCotacao(@Param("idAtivo") Long idAtivo, @Param("hoje") Date hoje);

	@Query("SELECT i.valorFechamento from AtivoCotacao i where i.ativo.id = :idAtivo AND date(i.dataCotacao) = :dataCotacao")
	Double findValorAcaoPrincipal(@Param("idAtivo") Long idAtivo, @Param("dataCotacao") Date dataCotacao);

	@Query("SELECT i from AtivoCotacao i where i.ativo.acaoPrincipal = :idAtivo AND date(i.dataCotacao) = :dataCotacao")
	List<AtivoCotacao> findByOpcaoDependenteAcaoPrincipal(@Param("idAtivo") Long idAtivo,
			@Param("dataCotacao") Date dataCotacao);

	@Query("SELECT i from AtivoCotacao i where i.ativo.id= :idAtivo AND date(i.dataCotacao) = :dataCotacao")
	AtivoCotacao findByAtivoAndData(@Param("idAtivo") Long idAtivo, @Param("dataCotacao") Date dataCotacao);

	public class EmpresaAtivoCotacaoSpecification implements Specification<AtivoCotacao> {
		private Long id;

		public EmpresaAtivoCotacaoSpecification(Long id2) {
			this.id = id2;
		}

		public Predicate toPredicate(Root<AtivoCotacao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			List<Predicate> predicates = new ArrayList<Predicate>();

			javax.persistence.criteria.Path<EmpresaAtivo> ativo = root.join("ativo");

			predicates.add(cb.equal(ativo.get("id"), this.id));
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}

	}

}
