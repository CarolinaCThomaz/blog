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
<c:import url="../../../menu.jsp"/>
<div class="container conteudo base">
<c:url var="save" value="/empresa/ativos/cotacao/save"/>
	<c:if test="${cotacao.id != null}">
      <c:url var="save" value="/empresa/ativos/cotacao/update/${cotacao.id}"/>
    </c:if>
   	
	<form:form commandName="cotacao" action="${save}" method="post">
		<form:hidden path="id"/>
		<form:hidden path="ativo.id"/>
		<div class="container">
		     <div class="row" style="margin-bottom: 20px;">	
			     <div class="col-md-4">
			       <span class="label label-warning" style="font-size: 18px;">${cotacao.ativo.empresa.descricao} - ${cotacao.ativo.descricao} </span>
			     </div>
			</div>
			<div class="row">	
			<div class="col-md-4">
			   <div class="control-group">
			       <label for="dataEntrada">Data Cotação:</label>
			        <div class="controls">
			            <div class="input-group">
			                 <form:input path="dataCotacao" id="dataCotacao" type="text" required="true" class="form-control"/>
			                 <label for="dataCotacao" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> 
				                </label>
			            </div>
			        </div>
			   </div>
			</div>  
			</div> 
			<div class="row">
				<div class="col-md-3">
		           <div class="control-group">
				       <label for="quantidade">Valor Minimo</label>
				       <form:input path="valorMinimo" type="text" required="true" class="form-control number"/>
				   </div>
				</div>
			   <div class="col-md-3">
		           <div class="control-group">
				       <label for="quantidade">Valor Máximo:</label>
				       <form:input path="valorMaximo" type="text" required="true" class="form-control number"/>
				   </div>
				</div>
				 <div class="col-md-2">
		           <div class="control-group">
				       <label for="quantidade">Valor Fechamento:</label>
				       <form:input path="valorFechamento" type="text" required="true" class="form-control number"/>
				   </div>
				</div>
				
				<div class="col-md-2">
		           <div class="control-group">
				       <label for="quantidade">Quantidade Neg.:</label>
				       <form:input path="quantidadeNegocio" type="text" class="form-control number"/>
				   </div>
				</div>	
				<div class="col-md-2">
		           <div class="control-group">
				       <label for="quantidade">Volume:</label>
				       <form:input path="volume" type="text" class="form-control number"/>
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
				$("#dataCotacao").datepicker({
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