<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Usuário</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
</head>
<body>
	<c:import url="../menu.jsp"/>
		
	<fieldset>
		<legend>Editar dados da Empresa</legend>
		<c:url var="save" value="/empresa/update/${empresa.id}"/>
		<form:form modelAttribute="empresa" action="${save}" method="post">
			<form:hidden path="id"/>
			<fieldset class="grupo">
				<legend>Editar Empresa</legend>
				<div class="campo">
					<form:label path="descricao">Descrição</form:label><br>
					<form:input path="descricao" type="text" required="true"/>
				</div>
				<div>
					<input type="submit" value="Salvar">
					<input type="reset" value="Limpar">
				</div>
			</fieldset>
		</form:form>
	
	</fieldset>
</body>
</html>