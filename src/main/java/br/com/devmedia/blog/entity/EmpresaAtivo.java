package br.com.devmedia.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "empresa_ativo")
public class EmpresaAtivo extends AbstractPersistable<Long> {

	@Column(nullable = false, unique = true)
	private String descricao;

	@ManyToOne(optional = false)
	private Empresa empresa;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAtivo tipoAtivo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private TipoOpcao tipoOpcao;

	@Column(nullable = true)
	private Double strike;

	@Column(name = "data_cadastro", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "data_vencimento", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;

	@Column(name = "data_alteracao", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataAlteracao;

	@Column(name = "data_exclusao", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataExclusao;

	@Column(name = "acao_principal", nullable = true)
	private Long acaoPrincipal;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoAndStrike() {
		return descricao + ((strike != null) ? " - " + strike.toString() : "");
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoAtivo getTipoAtivo() {
		return tipoAtivo;
	}

	public void setTipoAtivo(TipoAtivo tipoAtivo) {
		this.tipoAtivo = tipoAtivo;
	}

	public TipoOpcao getTipoOpcao() {
		return tipoOpcao;
	}

	public void setTipoOpcao(TipoOpcao tipoOpcao) {
		this.tipoOpcao = tipoOpcao;
	}

	public Double getStrike() {
		return strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
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

	public Long getAcaoPrincipal() {
		return acaoPrincipal;
	}

	public void setAcaoPrincipal(Long acaoPrincipal) {
		this.acaoPrincipal = acaoPrincipal;
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

}
