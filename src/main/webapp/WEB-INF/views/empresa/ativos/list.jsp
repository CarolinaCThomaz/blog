<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuários</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
</head>
<body>
   <c:import url="../../menu.jsp"/>
	
	<div class="container-fluid conteudo">
	  <div class="container base">
	   <div class="row">
		<div class="col-md-4">
		    <c:url var="addEmpresaAtivos" value="/empresa/ativos/add/${empresa.id}"/>
		    <a href="${addEmpresaAtivos}" title="Ativos">
		    <span class="label label-warning" style="font-size: 18px;">Cadastro Ativos ${empresa.descricao} </span>
		    </a>
	    </div>
	    <div class="col-md-2" style="margin-bottom: 10px; ">
	      <input type="checkbox" class="tipo" id="compra" value="COMPRA" ${compra == 'true' ? 'checked' : ''}> <label for="cbox2">Compra</label>
		</div>
		<div class="col-md-2" style="margin-bottom: 10px;">
		  <input type="checkbox" class="tipo"  id="venda" value="VENDA" ${venda == 'true' ? 'checked' : ''}> <label for="cbox2">Venda</label>
		</div>
	    <div class="col-md-4" style="margin-bottom: 10px;">
		     <select id="empresa">
		            <option value="">Trocar Empresa</option>
		            <c:forEach var="item" items="${empresas}">
				       <option value="${item.id}" ${item.id == empresaId ? 'selected="selected"' : ''}>${item.descricao}</option>
		            </c:forEach>
			</select>
		</div>
	  </div>
	    
	     <div class="table-responsive">
	          <table class="table table-condensed table-striped table-bordered table-hover">
	            <thead>
					<tr>
						<th style="width:20%;text-align: center;">Descrição</th>
						<th style="width:10%;text-align: center;">Strike</th>
						<th style="width:10%;text-align: center;">Vencimento</th>
						<th style="width:10%;text-align: center;">Tipo</th>
						<th style="width:10%;text-align: center;">Tipo</th>
						<th style="width:10%;text-align: center;">Alterar</th>
						<th style="width:10%;text-align: center;">Excluir</th>
						<th style="width:20%;text-align: center;">Cotação</th>
					</tr>	
				</thead>
				<tbody>
			    <c:forEach var="ativos" items="${ativos}" varStatus="i">		
				<tr bgcolor='${i.count % 2 != 0 ? '#f1f1f1' : 'white'}'>
					<td style="text-align: left;">${ativos.descricao}</td>
					<td>${ativos.strike}</td>
					<td>${ativos.dataVencimento}</td>
					<td>
					${ativos.tipoAtivo}
					</td>
					<td>
					${ativos.tipoOpcao}
					</td>
					<td>					
						<c:url var="update" value="edit/${ativos.id}"/>
						<a href="${update}" title="Editar"><span class="glyphicon glyphicon glyphicon glyphicon-pencil"></span></a>
					</td>
					<td>					
						<c:url var="update" value="delete/${ativos.id}?idEmpresa=${ativos.empresa.id}&compra=${compra}&venda=${venda}"/>
						<a href="${update}" title="Editar"><span class="glyphicon glyphicon glyphicon glyphicon-remove"></span></a>
					</td>
					<td>					
						<c:url var="empresaAtivos" value="/empresa/ativos/cotacao/${ativos.id}?compra=${compra}&venda=${venda}"/>
						<a href="${empresaAtivos}" title="Ativos"><span class="glyphicon glyphicon glyphicon-search"></span></a>
					</td>
				</tr>
				</c:forEach>
			   </tbody>
		     </table>
	      </div>
	  </div>
	</div> 
	
	<script type="text/javascript">
	   $('#empresa').select2({ width: '300px' });
	   
	   $("#empresa").change(function(){
		   var compra = $("#compra").is(':checked') ? true : false;
		   var venda = $("#venda").is(':checked') ? true : false;
		   var id = $(this).val();
		   var url = "${pageContext.request.contextPath}/empresa/ativos/"+id+"?&compra="+compra+"&venda="+venda;
		   console.log(url);
		   window.location = url;
		});
	   
	   $('.tipo').change(function() {
		   $("#empresa").trigger('change');        
	    });
   </script> 
</body>
</html>