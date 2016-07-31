package br.com.devmedia.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.devmedia.blog.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	Empresa findByDescricao(String descricao);

	@Modifying
	@Query("update Empresa e set e.descricao = ?1 where e.id = ?2")
	void update(String descricao, Long id);

}
