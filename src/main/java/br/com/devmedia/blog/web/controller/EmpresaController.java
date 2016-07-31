package br.com.devmedia.blog.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.devmedia.blog.entity.Empresa;
import br.com.devmedia.blog.service.EmpresaService;

@Controller
@RequestMapping("empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list(ModelMap model) {

		List<Empresa> empresas = empresaService.findAll();

		model.addAttribute("empresas", empresas);

		return new ModelAndView("empresa/list", model);
	}

	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
	public ModelAndView show(@PathVariable("id") Optional<Long> id) {

		ModelAndView view = new ModelAndView();
		Empresa entity = empresaService.findById(id.get());
		view.addObject("empresa", entity);
		view.setViewName("empresa/cadastro");
		return view;
	}

	@RequestMapping(value = { "/update/{id}" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView update(@PathVariable("id") Optional<Long> id, @ModelAttribute("empresa") Empresa empresa) {

		ModelAndView view = new ModelAndView();

		Empresa entity = empresaService.findById(id.get());
		entity.setDescricao(empresa.getDescricao());
		entity.setSigla(empresa.getSigla());
		empresaService.update(entity);

		view.setViewName("redirect:/empresa/list");

		return view;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String create(@ModelAttribute("empresa") Empresa empresa) {

		if (empresa.getId() != null) {
			Empresa entity = empresaService.findOne(empresa.getId());
			entity.setDescricao(empresa.getDescricao());
			entity.setSigla(empresa.getSigla());
			empresa = entity;
		}
		empresaService.save(empresa);

		return "redirect:/empresa/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView getNew(@ModelAttribute("empresa") Empresa empresa) {
		return new ModelAndView("empresa/cadastro");
	}
}
