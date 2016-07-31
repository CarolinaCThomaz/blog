<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuário</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
</head>
<body>
	<c:import url="../../menu.jsp"/>
	<c:url var="save" value="/empresa/ativos/save"/>
	<c:if test="${empresaAtivo.id != null}">
      <c:url var="save" value="/empresa/ativos/update/${empresaAtivo.id}"/>
    </c:if>
   	<div class="container conteudo base">
   	  <span class="label label-warning" style="font-size: 18px;">Cadastro Ativo ${empresaAtivo.empresa.descricao}</span>
   	
   	   <form:form commandName="empresaAtivo" action="${save}" method="post">
		<form:hidden path="id"/>
		<div class="container">
			<div class="row">
			   <div class="col-md-4">
		           <div class="control-group">
				       <label for="quantidade">Descrição:</label>
				       <form:input path="descricao" type="text" required="true" class="form-control"/>
				   </div>
				</div>
				<div class="col-md-4">
		           <div class="control-group">
			           <div class="controls">
			           <label for="quantidade">Data Vencimento:</label>
				            <div class="input-group">
				                 <fmt:formatDate value="${empresaAtivo.dataVencimento}" pattern="dd/MM/yyyy" var="dateOfRecieptVar" />
                                 <form:input path="dataVencimento" id="dataVencimento" value="${dateOfRecieptVar}" class="form-control"/>
				                 <label for="dataVencimento" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> 
					                </label>
				            </div>
				        </div>
				      
				       
				   </div>
				</div>
				<div class="col-md-4">
		           <div class="control-group">
				       <label for="quantidade">Strike:</label>
				       <fmt:formatDate value="${empresaAtivo.dataVencimento}" pattern="dd/MM/yyyy" var="dateOfRecieptVar" />
                       <form:input path="strike" type="text" class="form-control number"/>
				   </div>
				</div>	
		    </div>
		    <div class="row">
		      <div class="col-md-3">
		           <div class="control-group">
		               <label for="quantidade">Empresa:</label>
				       <form:select id="customCollection" path="empresa.id" >
						  <form:options items="${empresas}" itemValue="id" itemLabel="descricao"/>
						</form:select>
				   </div>
			 </div>	
			 <div class="col-md-3">
		           <div class="control-group">
		               <label for="quantidade">Tipo:</label>
				       <form:select id="customCollection" path="TipoAtivo" >
							   <form:options items="${TipoAtivo}" />
						</form:select>
				   </div>
			 </div>
			 <div class="col-md-3">
		           <div class="control-group">
		               <label for="quantidade">Tipo Opção:</label>
		               <form:select id="customCollection" path="tipoOpcao" >
				               <option value="">Selecione</option>
							   <form:options items="${tiposOpcoes}" />
						</form:select>
				   </div>
			 </div>
			 <div class="col-md-3">
		           <div class="control-group">
		               <label for="quantidade">Ação</label>
				       <form:select path="acaoPrincipal" >
				          <form:option value="0" label="Selecione"/>
						  <form:options items="${acaoPrincipal}" itemValue="id" itemLabel="descricao"/>
						</form:select>
				   </div>
			 </div>
		    </div>	
		</div>
		<div class="col-md-12" style="margin-top: 10px">
			<input type="submit" value="Salvar">
		</div>
	</form:form>
   	</div>
	<script type="text/javascript">
		 $(document).ready(function () {
				$("#dataVencimento").datepicker({
					dateFormat: 'dd/mm/yy'
				});
		 });
		 
		 $(".number").focusout(function(){
			    var value = $(this).val();
			    var valor = value.replace(",", ".");
			    $(this).val(valor);
		});
     </script> 
</body>
</html>