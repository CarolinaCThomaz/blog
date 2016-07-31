<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuário</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/jquery-ui.css" />">
<style type="text/css">
.ui-dialog-osx {
    -moz-border-radius: 0 0 8px 8px;
    -webkit-border-radius: 0 0 8px 8px;
    border-radius: 0 0 8px 8px; border-width: 0 8px 8px 8px;
}
</style>
</head>
<body>
	<c:import url="../menu.jsp"/>		
	
	<div class="container conteudo base">
   <c:url var="save" value="/importar"/>
   <div class="container">IMPORTAR ATIVOS
			<div class="row" id="error">
			</div>
	</div>		
	<form id="form-files" action="${save}" method="post" 
														enctype="multipart/form-data">
  		<div class="container">
			<div class="row">
			  <div class="col-md-12">
			  <a  href="css/modelo.csv" style="margin-left: 490px;" download> Modelo</a>
		           <div class="control-group">
				       <label for="quantidade">Importar:</label>
				       <input type="file" name="file" required="true" class="form-control" style="width: 400px;"/>
				   </div>
			  </div>	
			</div>
		</div>												 
		<div class="col-md-12" style="margin-top: 10px">
			<input type="button" id="button-save" value="Salvar">
		</div>		
		
	</form>	
	
	<div id="dialog-message" title="Important information" style="display: none">
	    <span class="ui-state-default"><span class="ui-icon ui-icon-info" style="float:left; margin:0 7px 0 0;"></span></span>
	    <div style="margin-left: 23px;">
          <p>Importação Ok  </p>
        </div>
    </div>
   
   	</div>
	<script type="text/javascript">
	 $('#button-save').on(
             'click',
             function(event) {

                var $form = $("#form-files");
                var fd = new FormData($("#form-files")[0]);
                
                var url = "${pageContext.request.contextPath}/importarativos/create";
                
                $.ajax({
                    type : 'POST',
                    url : url,
                    data : fd,
                    cache : false,
                    processData : false,
                    contentType : false,
                    success : function(response) {
                      console.log("OK");  
                      $("#dialog-message").dialog({
                    	    modal: true,
                    	    draggable: false,
                    	    resizable: false,
                    	    position: ['center', 'top'],
                    	    show: 'blind',
                    	    hide: 'blind',
                    	    width: 400,
                    	    dialogClass: 'ui-dialog-osx',
                    	    buttons: {
                    	        "OK": function() {
                    	            $(this).dialog("close");
                    	        }
                    	    }
                    	});
                    },
                    error : function(data, status, error) {
                             $("#error").show();
                             $("#import-file").hide();
                             $('.modal-footer').hide();
                             
                             var newcontentError = $("#error");
                             var content = '<p style="border-right: 1px solid #FFF; height: 40px; padding-left: 10px; font-size: 13px;color: red;font-weight: bold;">';
                             content += 'Erros de importação: ';
                             content += '</p>';
                              
                             var response = data['responseText'];
                             var obj = JSON.parse(response);
                             
                             $.each(obj, function( index, node ) {
                                var linha = node.Linha;
                                var erros = node.Erros;
                                    content += '<p style="border-right: 1px solid #FFF; height: 40px; padding-left: 10px; font-size: 12px;">';
                                    content += 'Linha: ' +linha+ ' Erros: ' +erros;
                                    content += '</p>';
                             });
                             
                             newcontentError.append(content);
                                                            
                    }
                    
                });
             });
	</script>	
</body>
</html>