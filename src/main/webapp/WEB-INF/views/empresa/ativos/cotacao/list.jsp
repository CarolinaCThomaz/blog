<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuários</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
</head>
<body>

   <c:import url="../../../menu.jsp"/>
	
	<div class="container-fluid conteudo">
	  <div class="container base">
	  <div class="row">
		<div class="col-md-5">
		    <c:url var="empresaAtivosCotacao" value="/empresa/ativos/cotacao/add/${ativo.id}"/>
			<a href="${empresaAtivosCotacao}" title="Cotacao">
		    <span class="label label-danger" style="font-size: 18px;">Cadastro ${ativo.descricao}</span>
		    </a>
	     
		    <c:if test="${not empty ativo.dataVencimento}">
		      <span class="label label-warning" style="font-size: 18px;">Vencimento <fmt:formatDate value="${ativo.dataVencimento}" pattern="dd/MM/yyyy"/></span>
			</c:if>
			
			<a href="${pageContext.request.contextPath}/empresa/ativos/${ativo.empresa.id}" title="Empresa Ativos" style="margin-left: 15px;">
		    <span class="glyphicon glyphicon-flag"></span>
		    </a>
		</div>
		<div class="col-md-1" style="margin-bottom: 10px;    width: 10%;">
	      <input type="checkbox" class="tipo" id="compra" value="COMPRA" ${compra == 'true' ? 'checked' : ''}> <label for="cbox2">Compra</label>
		</div>
		<div class="col-md-1" style="margin-bottom: 10px;">
		  <input type="checkbox" class="tipo" id="venda" value="VENDA" ${venda == 'true' ? 'checked' : ''}> <label for="cbox2">Venda</label>
		</div>
		<div class="col-md-3" style="margin-bottom: 10px;    width: 20%;">
		    <select id="empresa-ativos">
		        <option value="">Pesquisar</option>
		        <c:forEach var="item" items="${empresaAtivos}">
				       <option value="${item.id}" ${item.id == ativoId ? 'selected="selected"' : ''}>${item.descricaoAndStrike}</option>
		        </c:forEach>
		    </select>
		 </div>
	    <div class="col-md-3" style="margin-bottom: 10px;    width: 20%;">
		     <select id="empresa">
		             <option value="">Trocar Empresa</option>
		            <c:forEach var="item" items="${empresas}">
				       <option value="${item.id}" ${item.id == ativo.empresa.id ? 'selected="selected"' : ''}>${item.descricao}</option>
		            </c:forEach>
			</select>
		</div>
	  </div> 
	  <div class="table-responsive">
	          <table class="table table-condensed table-striped table-bordered table-hover">
	            <thead>
					<tr>
						<th style="width:10%;text-align: center;">Data Cotação</th>
						<th style="width:10%;text-align: center;">Mínimo</th>
						<th style="width:10%;text-align: center;">Máximo</th>
						<th style="width:10%;text-align: center;">Fechamento</th>
						<th style="width:10%;text-align: center;">Strike</th>
						<th style="width:5%;text-align: center;">Ação</th>
						<th style="width:5%;text-align: center;">Range</th>
						<th style="width:10%;text-align: center;">Variação</th>
						<th style="width:5%;text-align: center;">%</th>
						<th style="width:5%;text-align: center;">T</th>
						<th style="width:10%;text-align: center;">Qtd. Neg</th>
						<th style="width:15%;text-align: center;">Média.V</th>
						<th style="width:5%;text-align: center;">Volume</th>
						<th style="width:10%;text-align: center;">Alterar</th>
					</tr>	
				</thead>
				<tbody>
			    <c:forEach var="cotacao" items="${cotacoes}" varStatus="i">		
					<tr bgcolor='${i.count % 2 != 0 ? '#f1f1f1' : 'white'}'>
					    <td>${cotacao.dataCotacao}</td>
						<td>${cotacao.valorMinimo}</td>
						<td>${cotacao.valorMaximo}</td>
						<td>${cotacao.valorFechamento}</td>
						<td>${cotacao.ativo.strike}</td>
						<td>${cotacao.valoAcaoPrincipal}</td>
						<td>
						<fmt:formatNumber type="number" maxIntegerDigits="2" value="${cotacao.range}" />
						</td>
						<td>
						<fmt:formatNumber type="number" maxIntegerDigits="2" value="${cotacao.variacao}" />
						</td>
						<td>
						<fmt:formatNumber type="number" maxIntegerDigits="2" value="${cotacao.porcentagem}" />
						</td>
						<td>
						<c:choose>
						   <c:when test="${cotacao.valorMinimo == 0 && cotacao.valorMaximo == 0}">
						    <span class="glyphicon glyphicon glyphicon-asterisk" title="Não teve negociação" style="color: #FFC125;"></span>
						  </c:when>
						  <c:when test="${cotacao.tendencia == 0}">
						    <span class="glyphicon glyphicon-arrow-down" style="color: red;"></span>
						  </c:when>
						  <c:when test="${cotacao.tendencia == 3}">
						    <span class="glyphicon glyphicon-arrow-right" style="color: blue;"></span>
						  </c:when>
						  <c:otherwise>
						    <span class="glyphicon glyphicon-arrow-up" style="color: green;"></span>
						  </c:otherwise>
                        </c:choose>
						</td>
						<td>${cotacao.quantidadeNegocio}</td>
						<td>${cotacao.mediaVolume}</td>
						<td>${cotacao.volume}</td>
						<td>					
							<c:url var="update" value="edit/${cotacao.id}"/>
							<a href="${update}" title="Editar">&#9445</a>
						</td>
					</tr>
				</c:forEach>
			   </tbody>
		     </table>
	      </div>
	  </div>
	</div> 
	<script type="text/javascript">
	   $('#empresa-ativos').select2({ width: '200px' });
	   
	   $("#empresa-ativos").change(function(){
		   var compra = $("#compra").is(':checked') ? true : false;
		   var venda = $("#venda").is(':checked') ? true : false;
		   
		   var id = $("#empresa-ativos").val();
		   if(id == ""){
			   $("#empresa-ativos").css("color", "yellow");
			   return false;
		   }
		   var url = "${pageContext.request.contextPath}/empresa/ativos/cotacao/"+id+"?&compra="+compra+"&venda="+venda;
		   console.log(url);
		   window.location = url;
		});
	   
      $('#empresa').select2({ width: '200px' });
	   
	   $("#empresa").change(function(){
		   var compra = $("#compra").is(':checked') ? true : false;
		   var venda = $("#venda").is(':checked') ? true : false;
		   
		   var id = $(this).val();
		   var url = "${pageContext.request.contextPath}/empresa/ativos/"+id+"?&compra="+compra+"&venda="+venda;
		   console.log(url);
		   window.location = url;
		});
	   
	   $('.tipo').change(function() {
		   $("#empresa-ativos").trigger('change');        
	    });
	   
   </script>	
</body>
</html>