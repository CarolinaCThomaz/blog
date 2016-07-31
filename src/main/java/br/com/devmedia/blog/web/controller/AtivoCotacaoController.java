package br.com.devmedia.blog.web.controller;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.devmedia.blog.entity.AtivoCotacao;
import br.com.devmedia.blog.entity.Empresa;
import br.com.devmedia.blog.entity.EmpresaAtivo;
import br.com.devmedia.blog.entity.TipoAtivo;
import br.com.devmedia.blog.entity.TipoOpcao;
import br.com.devmedia.blog.repository.EmpresaAtivoCotacaoRepository;
import br.com.devmedia.blog.service.EmpresaAtivoCotacaoService;
import br.com.devmedia.blog.service.EmpresaAtivosService;
import br.com.devmedia.blog.service.EmpresaService;

@Controller
@RequestMapping("empresa/ativos/cotacao")
public class AtivoCotacaoController {

	@Autowired
	private EmpresaAtivoCotacaoService cotacaoCotacaoService;

	@Autowired
	private EmpresaAtivosService empresaAtivosService;

	@Autowired
	private EmpresaAtivoCotacaoService ativoCotacaoService;

	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
	public ModelAndView list(@PathVariable("id") Long empresaAtivoId,
			@RequestParam(name = "compra", required = false, defaultValue = "true") boolean compra,
			@RequestParam(name = "venda", required = false) boolean venda) {

		ModelAndView view = new ModelAndView();
		List<AtivoCotacao> entity = cotacaoCotacaoService
				.findAll(new EmpresaAtivoCotacaoRepository.EmpresaAtivoCotacaoSpecification(empresaAtivoId));
		view.addObject("cotacoes", entity);
		EmpresaAtivo ativo = empresaAtivosService.findOne(empresaAtivoId);
		view.addObject("ativo", ativo);
		List<EmpresaAtivo> empresaAtivos = null;
		if (compra && !venda) {
			empresaAtivos = empresaAtivosService.findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompra(ativo.getEmpresa()
					.getId(), TipoOpcao.COMPRA, TipoAtivo.A플O);
		} else if (venda && !compra) {
			empresaAtivos = empresaAtivosService.findByIdEmpresaAndTipoAtivoAndTipoOpcaoVenda(ativo.getEmpresa()
					.getId(), TipoOpcao.VENDA, TipoAtivo.A플O);
		} else if (venda && compra) {
			empresaAtivos = empresaAtivosService.findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompraVenda(ativo.getEmpresa()
					.getId(), TipoAtivo.A플O);
		} else {
			empresaAtivos = empresaAtivosService.findByIdEmpresa(ativo.getEmpresa().getId());
		}
		Collections.sort(entity, new Comparator<AtivoCotacao>() {
			public int compare(AtivoCotacao r1, AtivoCotacao r2) {
				return r1.getDataCotacao().compareTo(r2.getDataCotacao()) * -1;
			}
		});
		view.addObject("empresaAtivos", empresaAtivos);
		view.addObject("ativoId", empresaAtivoId);
		view.addObject("compra", compra);
		view.addObject("venda", venda);
		view.addObject("ativoCotacao", new AtivoCotacao());
		List<Empresa> empresas = empresaService.findAll();
		view.addObject("empresas", empresas);
		view.setViewName("empresa/ativos/cotacao/list");
		return view;
	}

	@RequestMapping(value = { "edit/{id}" }, method = { RequestMethod.GET })
	public ModelAndView show(@PathVariable("id") Optional<Long> id) {

		ModelAndView view = new ModelAndView();
		AtivoCotacao entity = cotacaoCotacaoService.findById(id.get());
		view.addObject("cotacao", entity);
		view.setViewName("empresa/ativos/cotacao/cadastro");
		return view;
	}

	@RequestMapping(value = { "/update/{id}" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView update(@PathVariable("id") Optional<Long> id, @ModelAttribute("cotacao") AtivoCotacao cotacao) {

		ModelAndView view = new ModelAndView();

		AtivoCotacao entity = cotacaoCotacaoService.findById(id.get());
		entity.setDataCotacao(cotacao.getDataCotacao());
		entity.setValorMinimo(cotacao.getValorMinimo());
		entity.setValorMaximo(cotacao.getValorMaximo());
		entity.setQuantidadeNegocio(cotacao.getQuantidadeNegocio());
		entity.setValorFechamento(cotacao.getValorFechamento());
		entity.setVolume(cotacao.getVolume());
		entity.setMediaVolume(cotacao.getMediaVolume());
		entity = this.calculadoraCotacao(entity);

		if (cotacao.getVolume() > 0 && cotacao.getQuantidadeNegocio() > 0) {
			Double mediaVolume = this.formatarDoisDecimais((double) cotacao.getVolume()
					/ cotacao.getQuantidadeNegocio());
			entity.setMediaVolume(mediaVolume);
		} else {
			entity.setMediaVolume(0d);
		}

		EmpresaAtivo empresaAtivo = empresaAtivosService.findOne(entity.getAtivo().getId());
		if (empresaAtivo.getTipoAtivo() != TipoAtivo.A플O) {
			Double valoAcaoPrincipal = ativoCotacaoService.findValorAcaoPrincipal(entity.getAtivo().getAcaoPrincipal(),
					entity.getDataCotacao());
			if (valoAcaoPrincipal != null) {
				entity.setValoAcaoPrincipal(valoAcaoPrincipal);
			}

		} else {
			// buscar em cotacoes se tem ativos como acao principal e atualizar
			// valor acao principal
			List<AtivoCotacao> listOpcoes = ativoCotacaoService.findByOpcaoDependenteAcaoPrincipal(cotacao.getAtivo()
					.getId(), cotacao.getDataCotacao());
			for (AtivoCotacao ativoCotacao : listOpcoes) {
				ativoCotacao.setValoAcaoPrincipal(cotacao.getValorFechamento());
				ativoCotacaoService.save(ativoCotacao);
			}
		}

		cotacaoCotacaoService.update(entity);

		view.setViewName("redirect:/empresa/ativos/cotacao/" + entity.getAtivo().getId());

		return view;
	}

	public Double formatarDoisDecimais(double d) {
		System.out.println(" dois decimais >>>>> " + d);
		if (d > 0) {
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			Double valor = Double.valueOf(twoDForm.format(d).replace(",", "."));
			return valor;
		}
		return d;

	}

	private AtivoCotacao calculadoraCotacao(AtivoCotacao ativo) {
		// buscar cotacao dia anterior
		Pageable cotacaoDiaAnterior = new PageRequest(0, 1);
		List<AtivoCotacao> existe = ativoCotacaoService.findByIdAtivoAndUltimaDataCotacao(ativo.getAtivo().getId(),
				ativo.getDataCotacao(), cotacaoDiaAnterior);
		if (existe.size() > 0) {
			AtivoCotacao ativoDiaAnterior = new AtivoCotacao();
			for (AtivoCotacao ativoCotacao : existe) {
				ativoDiaAnterior = ativoCotacao;
			}

			Double variacao = (ativo.getValorFechamento() < ativoDiaAnterior.getValorFechamento()) ? ativo
					.getValorFechamento() - ativoDiaAnterior.getValorFechamento() : ativo.getValorFechamento()
					- ativoDiaAnterior.getValorFechamento();
			variacao = this.formatarDoisDecimais(variacao);
			ativo.setVariacao(variacao);

			Double porcentagem = (variacao * 100) / ativoDiaAnterior.getValorFechamento();
			porcentagem = this.formatarDoisDecimais(porcentagem);
			ativo.setPorcentagem(porcentagem);

			Integer tendencia = null;
			if (ativoDiaAnterior.getValorFechamento() < ativo.getValorFechamento()) {
				tendencia = 1;
			} else if (ativoDiaAnterior.getValorFechamento() > ativo.getValorFechamento()) {
				tendencia = 0;
			} else {
				tendencia = 3;
			}

			ativo.setTendencia(tendencia);
		} else {
			ativo.setVariacao(0d);
			ativo.setPorcentagem(0d);
			ativo.setTendencia(0);
		}

		Double valorMedio = ativo.getValorMaximo() > ativo.getValorMinimo() ? ativo.getValorMaximo()
				/ ativo.getValorMinimo() : (ativo.getValorMinimo() == 0d && ativo.getValorMaximo() == 0d) ? 0d : ativo
				.getValorMinimo() / ativo.getValorMaximo();
		valorMedio = this.formatarDoisDecimais(valorMedio);
		ativo.setValorMedio(valorMedio);

		return ativo;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String create(@ModelAttribute("cotacao") AtivoCotacao cotacao) {

		AtivoCotacao verify = ativoCotacaoService.findByIdAtivoAnDataCotacao(cotacao.getAtivo().getId(),
				cotacao.getDataCotacao());
		if (verify != null) {
			return "redirect:/empresa/ativos/cotacao/add/" + cotacao.getAtivo().getId();
		}

		this.calculadoraCotacao(cotacao);
		EmpresaAtivo empresaAtivo = empresaAtivosService.findOne(cotacao.getAtivo().getId());
		if (empresaAtivo.getTipoAtivo() != TipoAtivo.A플O) {
			Double valoAcaoPrincipal = ativoCotacaoService.findValorAcaoPrincipal(empresaAtivo.getAcaoPrincipal(),
					cotacao.getDataCotacao());
			if (valoAcaoPrincipal != null) {
				cotacao.setValoAcaoPrincipal(valoAcaoPrincipal);
			}

		} else {
			// buscar em cotacoes se tem ativos como acao principal e atualizar
			// valor acao principal
			List<AtivoCotacao> listOpcoes = ativoCotacaoService.findByOpcaoDependenteAcaoPrincipal(cotacao.getAtivo()
					.getId(), cotacao.getDataCotacao());
			for (AtivoCotacao ativoCotacao : listOpcoes) {
				System.out.println(" Ativo dependente " + ativoCotacao.getAtivo().getDescricao());
				ativoCotacao.setValoAcaoPrincipal(cotacao.getValorFechamento());
				ativoCotacaoService.save(ativoCotacao);
			}
		}
		Double mediaVolume = this.formatarDoisDecimais((double) cotacao.getVolume() / cotacao.getQuantidadeNegocio());
		cotacao.setMediaVolume(mediaVolume);
		cotacaoCotacaoService.save(cotacao);
		return "redirect:/empresa/ativos";
	}

	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public ModelAndView getNew(@PathVariable("id") Optional<Long> ativoId) {
		ModelAndView view = new ModelAndView("empresa/ativos/cotacao/cadastro");
		AtivoCotacao ativoCotacao = new AtivoCotacao();
		EmpresaAtivo empresaAtivo = empresaAtivosService.findOne(ativoId.get());
		ativoCotacao.setAtivo(empresaAtivo);
		ativoCotacao.setDataCotacao(new Date());
		view.addObject("cotacao", ativoCotacao);

		return view;
	}
}
