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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.devmedia.blog.entity.AtivoCotacao;
import br.com.devmedia.blog.entity.EmpresaAtivo;
import br.com.devmedia.blog.entity.TipoAtivo;
import br.com.devmedia.blog.service.EmpresaAtivoCotacaoService;
import br.com.devmedia.blog.service.EmpresaAtivosService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("importar")
public class ImportarController {

	@Autowired
	private EmpresaAtivoCotacaoService cotacaoCotacaoService;

	@Autowired
	private EmpresaAtivosService empresaAtivosService;

	@Autowired
	private EmpresaAtivoCotacaoService ativoCotacaoService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView view = new ModelAndView("importar/cadastro");
		return view;

	}

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
				String ativoNome = record.get(0);
				String dataUltimaNegociacao = record.get(1);
				String quantidadeNegocio = record.get(2);
				String volume = record.get(3);
				String fechamento = record.get(4);
				String valorMinimo = record.get(5);
				String valorMaximo = record.get(6);

				if (ativoNome != null && !ativoNome.trim().isEmpty()) {
					EmpresaAtivo ativo = empresaAtivosService.findByNome(ativoNome.trim());
					AtivoCotacao ativoCotacao = new AtivoCotacao();
					if (ativo != null) {
						System.out.println("Ativo " + ativo.getDescricao());
						System.out.println(" ativoNome " + ativoNome);
						System.out.println(" dataUltimaNegociacao " + dataUltimaNegociacao);
						System.out.println(" quantidadeNegocio " + quantidadeNegocio);
						System.out.println(" volume " + volume);
						System.out.println(" fechamento " + fechamento);
						System.out.println(" valorMinimo " + valorMinimo);
						System.out.println(" valorMaximo " + valorMaximo);
						ativoCotacao.setAtivo(ativo);
					} else {
						erroLinha.add(ativoNome + " não está cadastrado");
					}

					if (dataUltimaNegociacao.equals("")) {
						erroLinha.add("Campo Data não está cadastrado");
					} else {
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						Date data = new Date(format.parse(dataUltimaNegociacao).getTime());
						ativoCotacao.setDataCotacao(data);
					}

					if (!quantidadeNegocio.equals("")) {
						ativoCotacao.setQuantidadeNegocio((new Integer(quantidadeNegocio)));
					}

					if (!volume.equals("")) {
						ativoCotacao.setVolume((new Integer(volume)));
					}

					if (!fechamento.equals("")) {
						ativoCotacao.setValorFechamento((new Double(fechamento.replace(",", "."))));
					}

					if (!valorMinimo.equals("")) {
						ativoCotacao.setValorMinimo(((new Double(valorMinimo.replace(",", ".")))));
					}

					if (!valorMaximo.equals("")) {
						ativoCotacao.setValorMaximo(((new Double(valorMaximo.replace(",", ".")))));
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
						if (ativoCotacao.getValorFechamento() <= 0d) {
							// buscar cotacao dia anterior
							Pageable cotacaoDiaAnterior = new PageRequest(0, 1);
							List<AtivoCotacao> existe = ativoCotacaoService.findByIdAtivoAndUltimaDataCotacao(
									ativoCotacao.getAtivo().getId(), ativoCotacao.getDataCotacao(), cotacaoDiaAnterior);
							if (existe.size() > 0) {
								AtivoCotacao ativoDiaAnterior = new AtivoCotacao();
								for (AtivoCotacao teste : existe) {
									ativoDiaAnterior = teste;
									ativoCotacao.setValorFechamento(teste.getValorFechamento());
								}
							}
						}

						AtivoCotacao ativoCotacaocalculado = this.calculadoraCotacao(ativoCotacao);
						AtivoCotacao entity = cotacaoCotacaoService.findByAtivoAndData(ativoCotacaocalculado.getAtivo()
								.getId(), ativoCotacao.getDataCotacao());
						if (entity != null) {
							entity.setDataCotacao(ativoCotacaocalculado.getDataCotacao());
							entity.setQuantidadeNegocio(ativoCotacaocalculado.getQuantidadeNegocio());
							entity.setVolume(ativoCotacaocalculado.getVolume());
							entity.setValoAcaoPrincipal(ativoCotacaocalculado.getValoAcaoPrincipal());
							entity.setValorFechamento(ativoCotacaocalculado.getValorFechamento());
							entity.setValorMinimo(ativoCotacaocalculado.getValorMinimo());
							entity.setValorMaximo(ativoCotacaocalculado.getValorMaximo());
							entity.setValorMedio(ativoCotacaocalculado.getValorMedio());
							entity.setVariacao(ativoCotacaocalculado.getVariacao());
							entity.setPorcentagem(ativoCotacaocalculado.getPorcentagem());
							entity.setTendencia(ativoCotacaocalculado.getTendencia());
							entity.setMediaVolume(ativoCotacaocalculado.getMediaVolume());

							ativoCotacao = entity;
						}
						cotacaoCotacaoService.createOrUpdate(ativoCotacao);
					}

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

			if (ativoDiaAnterior.getValorFechamento() <= 0d) {
				ativo.setPorcentagem(0d);
			} else {
				Double porcentagem = (variacao * 100) / ativoDiaAnterior.getValorFechamento();
				porcentagem = this.formatarDoisDecimais(porcentagem);
				ativo.setPorcentagem(porcentagem);
			}

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

		if (ativo.getVolume() > 0 && ativo.getQuantidadeNegocio() > 0) {
			Double mediaVolume = this.formatarDoisDecimais((double) ativo.getVolume() / ativo.getQuantidadeNegocio());
			ativo.setMediaVolume(mediaVolume);
		} else {
			ativo.setMediaVolume(0d);
		}

		if (ativo.getAtivo().getTipoAtivo() != TipoAtivo.AÇÃO) {
			Double valoAcaoPrincipal = ativoCotacaoService.findValorAcaoPrincipal(ativo.getAtivo().getAcaoPrincipal(),
					ativo.getDataCotacao());
			if (valoAcaoPrincipal != null) {
				ativo.setValoAcaoPrincipal(valoAcaoPrincipal);
			}

		} else {
			// buscar em cotacoes se tem ativos como acao principal e atualizar
			// valor acao principal
			List<AtivoCotacao> listOpcoes = ativoCotacaoService.findByOpcaoDependenteAcaoPrincipal(ativo.getAtivo()
					.getId(), ativo.getDataCotacao());
			for (AtivoCotacao ativoCotacao : listOpcoes) {
				System.out.println(" Ativo dependente " + ativoCotacao.getAtivo().getDescricao());
				ativoCotacao.setValoAcaoPrincipal(ativo.getValorFechamento());
				ativoCotacaoService.save(ativoCotacao);
			}
		}

		return ativo;
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
