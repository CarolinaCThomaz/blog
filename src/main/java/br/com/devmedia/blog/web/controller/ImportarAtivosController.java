package br.com.devmedia.blog.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.devmedia.blog.entity.Empresa;
import br.com.devmedia.blog.entity.EmpresaAtivo;
import br.com.devmedia.blog.entity.TipoAtivo;
import br.com.devmedia.blog.entity.TipoOpcao;
import br.com.devmedia.blog.service.EmpresaAtivoCotacaoService;
import br.com.devmedia.blog.service.EmpresaAtivosService;
import br.com.devmedia.blog.service.EmpresaService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("importarativos")
public class ImportarAtivosController {

	@Autowired
	private EmpresaAtivoCotacaoService cotacaoCotacaoService;

	@Autowired
	private EmpresaAtivosService empresaAtivosService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private EmpresaAtivoCotacaoService ativoCotacaoService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView view = new ModelAndView("importarativos/cadastro");
		return view;

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/create", headers = "content-type=multipart/*", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public ResponseEntity<String> create(@RequestParam("file") MultipartFile file) throws Exception {
		ResponseEntity<String> responseEntity = null;

		List<Map<String, Object>> errorMap = new LinkedList<Map<String, Object>>();
		int linha = 0;
		boolean temErro = false;

		if (!file.isEmpty()) {

			Reader reader = new InputStreamReader(new ByteArrayInputStream(file.getBytes()), "ISO-8859-1");
			CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(';'));

			for (CSVRecord record : parser) {

				List<String> erroLinha = new ArrayList<String>();
				linha++;
				String empresaId = record.get(0);
				String tipoAtivo = record.get(1);
				String tipoOpcao = record.get(2);
				String vencimento = record.get(3);
				String acaoPrincipal = record.get(4);
				String ativoNome = record.get(5);
				String strike = record.get(6);

				if (!empresaId.equals("")) {
					Empresa empresa = empresaService.findById(new Long(empresaId));
					EmpresaAtivo ativo = new EmpresaAtivo();
					if (empresa != null) {

						ativo.setEmpresa(empresa);
					} else {
						erroLinha.add("Empresa não está cadastrada ID " + empresaId);
					}

					if (!tipoAtivo.equals(TipoAtivo.AÇÃO.toString()) && !tipoAtivo.equals(TipoAtivo.OPÇÃO.toString())) {
						erroLinha.add("Tipo Ativo Não Existe");
					} else {
						if (tipoAtivo.equals(TipoAtivo.AÇÃO.toString())) {
							ativo.setTipoAtivo(TipoAtivo.AÇÃO);
						} else {
							ativo.setTipoAtivo(TipoAtivo.OPÇÃO);
						}
					}

					if (!tipoOpcao.equals(TipoOpcao.COMPRA.toString()) && !tipoOpcao.equals(TipoOpcao.VENDA.toString())) {
						erroLinha.add("Tipo Opção Não Existe");
					} else {
						if (tipoOpcao.equals(TipoOpcao.COMPRA.toString())) {
							ativo.setTipoOpcao(TipoOpcao.COMPRA);
						} else {
							ativo.setTipoOpcao(TipoOpcao.VENDA);
						}
					}

					if (ativoNome.equals("")) {
						erroLinha.add("Descrição do ativo campo Vazio");
					} else {
						ativo.setDescricao(ativoNome);
					}

					if (strike.equals("")) {
						erroLinha.add("Strike campo Vazio");
					} else {
						ativo.setStrike((new Double(strike.replace(",", "."))));
					}

					if (vencimento.equals("")) {
						erroLinha.add("Vencimento campo Vazio");
					} else {
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						Date data = new Date(format.parse(vencimento).getTime());
						ativo.setDataVencimento(data);
					}

					if (acaoPrincipal.equals("")) {
						erroLinha.add("Ação Principal campo Vazio");
					} else {
						ativo.setAcaoPrincipal(((new Long(acaoPrincipal.replace(",", ".")))));
					}

					if (!erroLinha.isEmpty()) {
						temErro = true;
						StringBuilder errors = new StringBuilder();
						boolean first = true;
						for (String erro : erroLinha) {
							if (!first) {
								errors.append(";");
							}
							errors.append(erro);
							first = false;
						}

						Map<String, Object> map = new HashMap<String, Object>();
						map.put("Linha", linha);
						map.put("Erros", errors.toString());
						errorMap.add(map);
					} else {
						System.out.println("Ativo ID" + ativo.getId());
						EmpresaAtivo entity = empresaAtivosService.findByNome(ativoNome);

						if (entity != null) {
							entity.setEmpresa(ativo.getEmpresa());
							entity.setTipoAtivo(ativo.getTipoAtivo());
							entity.setTipoOpcao(ativo.getTipoOpcao());
							entity.setStrike(ativo.getStrike());
							entity.setDataVencimento(ativo.getDataVencimento());
							entity.setAcaoPrincipal(ativo.getAcaoPrincipal());

							ativo = entity;
						}
						empresaAtivosService.createOrUpdate(ativo);
					}

				} else {
					erroLinha.add("Empresa Campo Vazio ");
				}

			}
			if (temErro) {
				responseEntity = new ResponseEntity<String>(toJson(errorMap), new HttpHeaders(),
						HttpStatus.PRECONDITION_FAILED);
			}

			parser.close();
		}

		return responseEntity;

	}

	public Double formatarDoisDecimais(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d).replace(",", "."));
	}

	protected String toJson(Object model) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(model);
	}
}
