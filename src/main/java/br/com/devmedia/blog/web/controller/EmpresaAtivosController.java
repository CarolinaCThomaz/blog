package br.com.devmedia.blog.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import br.com.devmedia.blog.service.EmpresaAtivoCotacaoService;
import br.com.devmedia.blog.service.EmpresaAtivosService;
import br.com.devmedia.blog.service.EmpresaService;
import br.com.devmedia.blog.web.editor.TipoAtivoEditorSupport;

@Controller
@RequestMapping("empresa/ativos")
public class EmpresaAtivosController {

	@Autowired
	private EmpresaAtivosService empresaAtivosService;

	@Autowired
	private EmpresaAtivoCotacaoService ativoCotacaoService;

	@Autowired
	private EmpresaService empresaService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		binder.registerCustomEditor(TipoAtivo.class, new TipoAtivoEditorSupport());
	}

	@RequestMapping(method = { RequestMethod.GET })
	public ModelAndView listDiaria() {

		ModelAndView view = new ModelAndView();
		List<EmpresaAtivo> empresaAtivos = empresaAtivosService.findAtivos();
		List<EmpresaAtivo> listaDiaria = new ArrayList<EmpresaAtivo>();
		for (EmpresaAtivo empresaAtivo : empresaAtivos) {
			AtivoCotacao lst = ativoCotacaoService.findByIdAtivoAnDataCotacao(empresaAtivo.getId(), new Date());
			if (lst == null) {
				listaDiaria.add(empresaAtivo);
			}

		}
		SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd/MM/yyyy");
		view.addObject("data", simpleFormatter.format(new Date()));
		view.addObject("ativos", listaDiaria);
		view.setViewName("empresa/ativos/lista");
		return view;
	}

	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
	public ModelAndView list(@PathVariable("id") Long empresaId,
			@RequestParam(name = "compra", required = false, defaultValue = "true") boolean compra,
			@RequestParam(name = "venda", required = false) boolean venda) {

		ModelAndView view = new ModelAndView();
		// List<EmpresaAtivo> entity = empresaAtivosService.findAll(new
		// EmpresaAtivosRepository.EmpresaAtivoSpecification(
		// empresaId, compra, venda));
		List<EmpresaAtivo> entity = null;
		if (compra && !venda) {
			entity = empresaAtivosService.findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompra(empresaId, TipoOpcao.COMPRA,
					TipoAtivo.A플O);
		} else if (venda && !compra) {
			entity = empresaAtivosService.findByIdEmpresaAndTipoAtivoAndTipoOpcaoVenda(empresaId, TipoOpcao.VENDA,
					TipoAtivo.A플O);
		} else if (venda && compra) {
			entity = empresaAtivosService.findByIdEmpresaAndTipoAtivoAndTipoOpcaoCompraVenda(empresaId, TipoAtivo.A플O);
		} else {
			entity = empresaAtivosService.findByIdEmpresa(empresaId);
		}

		view.addObject("ativos", entity);
		Empresa empresa = empresaService.findById(empresaId);
		view.addObject("empresa", empresa);
		view.addObject("empresaId", empresaId);
		view.addObject("compra", compra);
		view.addObject("venda", venda);
		List<Empresa> empresas = empresaService.findAll();
		view.addObject("empresas", empresas);
		view.setViewName("empresa/ativos/list");
		return view;
	}

	@RequestMapping(value = { "edit/{id}" }, method = { RequestMethod.GET })
	public ModelAndView show(@PathVariable("id") Optional<Long> id) {

		ModelAndView view = new ModelAndView();
		EmpresaAtivo entity = empresaAtivosService.findById(id.get());
		List<EmpresaAtivo> acaoPrincipal = empresaAtivosService.findAtivosByTipoAcao(TipoAtivo.A플O);
		view.addObject("acaoPrincipal", acaoPrincipal);
		view.addObject("empresas", empresaService.findAll());
		view.addObject("empresaAtivo", entity);
		view.setViewName("empresa/ativos/cadastro");
		return view;
	}

	@RequestMapping(value = { "/update/{id}" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView update(@PathVariable("id") Optional<Long> id,
			@ModelAttribute("empresaAtivo") EmpresaAtivo empresaAtivo) {

		ModelAndView view = new ModelAndView();

		EmpresaAtivo entity = empresaAtivosService.findById(id.get());
		entity.setDescricao(empresaAtivo.getDescricao());
		entity.setStrike(empresaAtivo.getStrike());
		entity.setDataVencimento(empresaAtivo.getDataVencimento());
		entity.setTipoAtivo(empresaAtivo.getTipoAtivo());
		entity.setTipoOpcao(empresaAtivo.getTipoOpcao());
		entity.setEmpresa(empresaService.findOne(empresaAtivo.getEmpresa().getId()));
		empresaAtivosService.update(entity);

		view.setViewName("redirect:/empresa/ativos/" + entity.getEmpresa().getId());

		return view;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String create(@ModelAttribute("empresaAtivo") EmpresaAtivo ativo) {

		if (ativo.getId() != null) {
			EmpresaAtivo entity = empresaAtivosService.findOne(ativo.getId());
			entity.setDescricao(ativo.getDescricao());

			ativo = entity;
		}
		if (ativo.getDataCadastro() == null) {
			ativo.setDataCadastro(new Date());
		}
		Empresa empresa = empresaService.findOne(ativo.getEmpresa().getId());
		ativo.setEmpresa(empresa);
		empresaAtivosService.save(ativo);

		return "redirect:/empresa/ativos/" + ativo.getEmpresa().getId();
	}

	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public ModelAndView getNew(@PathVariable("id") Optional<Long> idEmpresa,
			@ModelAttribute("empresaAtivo") EmpresaAtivo ativo) {
		ModelAndView view = new ModelAndView("empresa/ativos/cadastro");

		view.addObject("empresas", empresaService.findAll());
		Empresa empresa = empresaService.findOne(idEmpresa.get());
		EmpresaAtivo empresaAtivo = new EmpresaAtivo();
		empresaAtivo.setEmpresa(empresa);
		List<EmpresaAtivo> acaoPrincipal = empresaAtivosService.findAtivosByTipoAcao(TipoAtivo.A플O);
		view.addObject("acaoPrincipal", acaoPrincipal);
		view.addObject("empresaAtivo", empresaAtivo);
		view.addObject("tipos", TipoAtivo.values());
		view.addObject("tiposOpcoes", TipoOpcao.values());
		return view;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long idAtivo, @RequestParam("idEmpresa") Long idEmpresa,
			@RequestParam(name = "compra", required = false, defaultValue = "true") boolean compra,
			@RequestParam(name = "venda", required = false) boolean venda) {

		empresaAtivosService.delete(idAtivo);
		return "redirect:/empresa/ativos/" + idEmpresa + "?compra=" + compra + "&venda=" + venda;
	}
}
