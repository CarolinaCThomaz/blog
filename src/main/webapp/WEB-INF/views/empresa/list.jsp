<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<body>
	<c:import url="../menu.jsp"/>
	
	<div class="container-fluid conteudo">
	  <div class="container base">
	   <c:url var="empresa" value="/empresa/add/${ativo.id}"/>
        <a href="${empresa}" title="Empresa">
	    <span class="label label-info" style="font-size: 18px;">Cadastro Empresa </span>
	    </a>
	      <div class="table-responsive">
	          <table class="table table-condensed table-striped table-bordered table-hover">
	            <thead>
					<tr>
						<th style="width:40%;text-align: center;">Nome</th>
						<th style="width:30%;text-align: center;">Alterar</th>
						<th style="width:15%;text-align: center;">Ativos</th>
					</tr>	
				</thead>
				<tbody>
			    <c:forEach var="empresa" items="${empresas}" varStatus="i">		
					<tr bgcolor='${i.count % 2 != 0 ? '#f1f1f1' : 'white'}'>
						<td style="text-align: left;"> ${empresa.descricao}</td>
						<td>					
							<c:url var="update" value="/empresa/${empresa.id}"/>
							<a href="${update}" title="Editar">
							 <span class="glyphicon glyphicon-edit"></span>
							</a>
						</td>
						<td>					
							<c:url var="empresaAtivos" value="/empresa/ativos/${empresa.id}"/>
							<a href="${empresaAtivos}" title="Ativos">
							 <span class="glyphicon glyphicon glyphicon-search"></span>
							</a>
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