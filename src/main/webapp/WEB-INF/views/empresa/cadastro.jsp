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
	<c:import url="../menu.jsp"/>
	<c:url var="save" value="/empresa/save"/>
	<c:if test="${empresa.id != null}">
      <c:url var="save" value="/empresa/update/${empresa.id}"/>
    </c:if>
    
   	<div class="container conteudo base">
   	  <form:form modelAttribute="empresa" action="${save}" method="post" enctype="multipart/form-data">
		<form:hidden path="id"/>	
		<div class="container">
			<div class="row">
			  <div class="col-md-5">
		           <div class="control-group">
				       <label for="quantidade">Empresa:</label>
				       <form:input path="descricao" type="text" required="true" class="form-control"/>
				   </div>
			  </div>	
			  <div class="col-md-3">
		           <div class="control-group">
				       <label for="quantidade">Sigla:</label>
				       <form:input path="sigla" type="text" required="true" class="form-control"/>
				   </div>
			  </div>	
			</div>
		</div>												 
		<div class="col-md-12" style="margin-top: 10px">
			<input type="submit" value="Salvar">
		</div>
	</form:form>
   	</div>
	
</body>
</html>