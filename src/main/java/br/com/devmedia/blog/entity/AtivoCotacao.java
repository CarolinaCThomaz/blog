package br.com.devmedia.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "empresa_ativo_cotacao")
public class AtivoCotacao extends AbstractPersistable<Long> {

	@ManyToOne(optional = false)
	private EmpresaAtivo ativo;

	@Column(name = "data_cotacao", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	private Date dataCotacao;

	@Column(name = "data_cadastro", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "data_alteracao", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;

	@Column(name = "data_exclusao", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExclusao;

	@Column(name = "valor_acao_principal", nullable = true)
	private Double valoAcaoPrincipal;

	@Column(name = "valor_minimo", nullable = false)
	private Double valorMinimo = 0.0;

	@Column(name = "valor_medio", nullable = false)
	private Double valorMedio = 0.0;

	@Column(name = "valor_maximo", nullable = false)
	private Double valorMaximo = 0.0;

	@Column(name = "valor_fechamento", nullable = false)
	private Double valorFechamento = 0.0;

	@Column(name = "variacao", nullable = false)
	private Double variacao = 0.0;

	@Column(name = "porcentagem", nullable = false)
	private Double porcentagem = 0.0;

	@Column(name = "tendencia", nullable = false)
	private Integer tendencia;

	@Column(name = "quantidade_negocios", nullable = false)
	private Integer quantidadeNegocio = 0;

	@Column(name = "volume", nullable = false)
	private Integer volume = 0;

	@Column(name = "media_volume", nullable = false)
	private Double mediaVolume = 0.0;

	public EmpresaAtivo getAtivo() {
		return ativo;
	}

	public void setAtivo(EmpresaAtivo ativo) {
		this.ativo = ativo;
	}

	public Date getDataCotacao() {
		return dataCotacao;
	}

	public void setDataCotacao(Date dataCotacao) {
		this.dataCotacao = dataCotacao;
	}

	public Double getValoAcaoPrincipal() {
		return valoAcaoPrincipal;
	}

	public void setValoAcaoPrincipal(Double valoAcaoPrincipal) {
		this.valoAcaoPrincipal = valoAcaoPrincipal;
	}

	public Double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(Double valoMinimo) {
		this.valorMinimo = valoMinimo;
	}

	public Double getValorMedio() {
		return valorMedio;
	}

	public void setValorMedio(Double valorMedio) {
		this.valorMedio = valorMedio;
	}

	public Double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public Double getValorFechamento() {
		return valorFechamento;
	}

	public void setValorFechamento(Double valorFechamento) {
		this.valorFechamento = valorFechamento;
	}

	public Double getVariacao() {
		return variacao;
	}

	public void setVariacao(Double variacao) {
		this.variacao = variacao;
	}

	public Double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Double porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Integer getTendencia() {
		return tendencia;
	}

	public void setTendencia(Integer tendencia) {
		this.tendencia = tendencia;
	}

	public Integer getQuantidadeNegocio() {
		return quantidadeNegocio;
	}

	public void setQuantidadeNegocio(Integer quantidadeNegocio) {
		this.quantidadeNegocio = quantidadeNegocio;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Double getMediaVolume() {
		return this.mediaVolume;
	}

	public void setMediaVolume(Double mediaVolume) {
		this.mediaVolume = mediaVolume;
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

	public Double getRange() {
		Double range = 0.0;
		if (valorMaximo > valorFechamento) {
			range = valorMaximo - valorMinimo;
		} else {
			range = valorFechamento - valorMinimo;
		}
		return range;
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}
}
