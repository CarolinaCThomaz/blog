package br.com.devmedia.blog.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.devmedia.blog.entity.EmpresaAtivo;
import br.com.devmedia.blog.entity.TipoAtivo;
import br.com.devmedia.blog.entity.TipoOpcao;

public interface EmpresaAtivosRepository extends JpaRepository<EmpresaAtivo, Long>,
		JpaSpecificationExecutor<EmpresaAtivo> {

	@Query("SELECT i from EmpresaAtivo i where i.descricao = :descricao")
	EmpresaAtivo findByDescricao(@Param("descricao") String descricao);

	@Modifying
	@Query("update EmpresaAtivo e set e.descricao = ?1 where e.id = ?2")
	void update(String descricao, Long id);

	@Query("SELECT i from EmpresaAtivo i where i.empresa.id = :idEmpresa AND i.dataExclusao is null ORDER BY i.dataVencimento ASC, i.strike ASC")
	List<EmpresaAtivo> findByIdEmpresa(@Param("idEmpresa") Long idEmpresa);

	@Query("SELECT i from EmpresaAtivo i where i.empresa.id = :idEmpresa AND (i.tipoOpcao = :compra OR i.tipoAtivo = :tipoAtivo) AND  i.dataExclusao is null ORDER BY i.dataVencimento ASC, i.strike ASC")
	List<EmpresaAtivo> findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompra(@Param("idEmpresa") Long empresaId,
			@Param("compra") TipoOpcao compra, @Param("tipoAtivo") TipoAtivo tipoAtivo);

	@Query("SELECT i from EmpresaAtivo i where i.empresa.id = :idEmpresa AND (i.tipoOpcao = :venda OR i.tipoAtivo = :tipoAtivo) AND  i.dataExclusao is null ORDER BY i.dataVencimento ASC,  i.strike ASC")
	List<EmpresaAtivo> findByIdEmpresaAndTipoAtivoAndTipoOpcaoVenda(@Param("idEmpresa") Long empresaId,
			@Param("venda") TipoOpcao venda, @Param("tipoAtivo") TipoAtivo tipoAtivo);

	@Query("SELECT i from EmpresaAtivo i where i.empresa.id = :idEmpresa AND (i.tipoOpcao IS NOT NULL OR i.tipoAtivo = :tipoAtivo) AND  i.dataExclusao is null ORDER BY i.dataVencimento ASC, i.strike ASC")
	List<EmpresaAtivo> findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompraVenda(@Param("idEmpresa") Long empresaId,
			@Param("tipoAtivo") TipoAtivo tipoAtivo);

	@Query("SELECT i from EmpresaAtivo i where i.dataExclusao is null")
	List<EmpresaAtivo> findAtivos();

	@Query("SELECT i from EmpresaAtivo i where i.tipoAtivo = :acao  AND i.dataExclusao is null")
	List<EmpresaAtivo> findAtivosByTipoAcao(@Param("acao") TipoAtivo acao);

	public class EmpresaAtivoSpecification implements Specification<EmpresaAtivo> {
		private Long empresaId;
		private boolean compra;
		private boolean venda;

		public EmpresaAtivoSpecification(Long empresaId, boolean compra, boolean venda) {
			this.empresaId = empresaId;
			this.compra = compra;
			this.venda = venda;
		}

		public Predicate toPredicate(Root<EmpresaAtivo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(cb.equal(root.get("empresa"), this.empresaId));

			if (compra && !venda) {
				predicates.add(cb.or(cb.equal(root.get("tipoOpcao"), TipoOpcao.COMPRA),
						cb.equal(root.get("tipoAtivo"), TipoAtivo.AÇÃO)));
			}
			if (venda && !compra) {
				predicates.add(cb.or(cb.equal(root.get("tipoOpcao"), TipoOpcao.VENDA),
						cb.equal(root.get("tipoAtivo"), TipoAtivo.AÇÃO)));
			}

			if (venda && compra) {
				predicates.add(cb.or(cb.equal(root.get("tipoOpcao"), TipoOpcao.COMPRA),
						cb.equal(root.get("tipoOpcao"), TipoOpcao.VENDA),
						cb.equal(root.get("tipoAtivo"), TipoAtivo.AÇÃO)));
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}
	}

}
