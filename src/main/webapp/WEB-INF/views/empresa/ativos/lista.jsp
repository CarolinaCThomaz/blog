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
	   
	    <span class="label label-warning" style="font-size: 31px;">${data}</span>
	   
	       <div class="table-responsive">
	          <table class="table table-condensed table-striped table-bordered table-hover">
	            <thead>
					<tr>
						<th style="width:40%;text-align: center;">Ativo</th>
						<th style="width:20%;text-align: center;">Tipo</th>
						<th style="width:20%;text-align: center;">Histórico</th>
						<th style="width:20%;text-align: center;">Cotação</th>
					</tr>	
				</thead>
				<tbody>
			    <c:forEach var="ativos" items="${ativos}" varStatus="i">		
				<tr bgcolor='${i.count % 2 != 0 ? '#f1f1f1' : 'white'}'>
					<td>${ativos.descricao}</td>
					<td>${ativos.tipoAtivo}</td>
					<td>					
						<c:url var="empresaAtivos" value="/empresa/ativos/cotacao/${ativos.id}"/>
						<a href="${empresaAtivos}" title="Ativos"><span class="glyphicon glyphicon glyphicon-stats"></span></a>
					</td>
					<td>					
						<c:url var="empresaAtivos" value="/empresa/ativos/cotacao/add/${ativos.id}"/>
						<a href="${empresaAtivos}" title="Ativos"><span class="glyphicon glyphicon glyphicon-pencil"></span></a>
					</td>
				</tr>
				</c:forEach>
			   </tbody>
		     </table>
	      </div>
	  </div>
	</div>  
</body>
</html>