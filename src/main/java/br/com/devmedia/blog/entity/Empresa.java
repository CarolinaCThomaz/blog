package br.com.devmedia.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "empresa")
public class Empresa extends AbstractPersistable<Long> {

	@Column(nullable = false, unique = true)
	private String descricao;

	@Column(nullable = false, unique = true)
	private String sigla;

	@Column(name = "data_cadastro", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataCadastro;

	@Column(name = "data_alteracao", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataAlteracao;

	@Column(name = "data_exclusao")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataExclusao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

}
