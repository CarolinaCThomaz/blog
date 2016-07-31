<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuários</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.min.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/bootstrap/jquery-ui-1.9.2.custom.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/select2.css" />">
<script type="text/javascript" src="<c:url value="/js/jquery.js" />"></script> 
<script type="text/javascript" src="<c:url value="/js/bootstrap/bootstrap.js" />"></script> 
<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.9.2.custom.js" />"></script> 
<script type="text/javascript" src="<c:url value="/js/select2.js" />"></script> 


   <style type="text/css">

body {
background-color: #F7F7F7;
}

.conteudo {
 margin-top: 30px;
}

.base {
background-color: white;
padding-top: 16px;
}



  #custom-bootstrap-menu .navbar-collapse.collapse {
    display: none !important;
  }
  #custom-bootstrap-menu .navbar-collapse.collapse.in {
    display: block !important;
  }
  #custom-bootstrap-menu .navbar-toggle {
    display: block !important;
  }
  #custom-bootstrap-menu .navbar-collapse {
    text-align: center;
    display: block !important;
  }
  #custom-bootstrap-menu .navbar-header {
    float: none;
    display: block !important;
  }
  #custom-bootstrap-menu .navbar-nav {
    display: inline-block;
    float: left;
    vertical-align: top;
  }
  #custom-bootstrap-menu .navbar-nav > li {
    float: left;
  }
/*   #custom-bootstrap-menu .navbar-nav{ */
/*         float: left; */
/*   } */
  #custom-bootstrap-menu .navbar-nav> li > a {
    padding-top: 10px;
    padding-bottom: 10px;
  }
  
 
.mtop {margin-top:20px;}
.menu-ico-collapse {
    font-size: 8px;
    margin-left: 2px;
    float: none;
}

    
/* MAIN MENU */
    #mainmenu {font-size: 12px;}  

    #mainmenu {
        background: #ECECEC;
        height: 50px;
    }

    #mainmenu .pos-absolute {
        position: absolute;
        float: left;
        margin-left: -15px;
        z-index: 999;
    }
    
    .menu-ico-collapse {
        font-size: 8px;
        margin-left: 2px;
    }

    #mainmenu .list-group {
      margin-bottom: 20px;
      padding-left: 0;
      float: left;
      display: inline;
    }
    #mainmenu .list-group-item-base {
      display: block;
      width:170px;
      height: 50px;
      font-size: 23px;
      color: #736F6F;
      margin-bottom: -1px;
      background-color: #ECECEC;
      border: 0;
      line-height: 27px;
    }
    
    #mainmenu .list-group-item {
      display: block;
      width:185px;
      height: 50px;
      margin-bottom: -1px;
      background-color: #ECECEC;
      border: 0;
      line-height: 27px;
    }
    #mainmenu .list-group-item:first-child {
      border-top-right-radius: 0;
      border-top-left-radius: 0;
    }
    #mainmenu .list-group-item:last-child {
      margin-bottom: 0;
      border-bottom-right-radius: 0;
      border-bottom-left-radius: 0;
    }
    #mainmenu .list-group-item > .badge {
      float: right;
    }
    #mainmenu .list-group-item > .badge + .badge {
      margin-right: 5px;
    }
    #mainmenu a.list-group-item {
      color: #333;
      font-weight: normal;
      border: 0;
    }
    #mainmenu a.list-group-item .list-group-item-heading {
      color: #ECECEC;
    }
    #mainmenu a.list-group-item:hover,
    #mainmenu a.list-group-item:focus {
      text-decoration: none;
      background-color: #FFF;
    }
    #mainmenu a.list-group-item.active,
    #mainmenu a.list-group-item.active:hover,
    #mainmenu a.list-group-item.active:focus {
      z-index: 2;
      color: #333;
      background-color: #7FAEEA;
      border: 0;
    }

    #mainmenu .panel {
      margin-bottom: 20px;
      background-color: #333;
    border: none;
      }
    /* MAIN MENU end */



</style>
</head>
<div class="container-fluid fundo_cabecalho">
  <div class="container">
     <div class="row" >
        <div class="col-xs-6">
		  <div id="mainmenu" class="col-xs-2">
	         <div class="list-group panel">
	            <a href="#menupos1" class="list-group-item-base btn-default btn-sm" data-toggle="collapse" data-parent="#mainmenu">Cotações <span class="glyphicon glyphicon glyphicon-list"></span></a>
	            <div class="collapse pos-absolute" id="menupos1">
	              <a href="#submenu1" class="list-group-item sub-item" data-toggle="collapse" data-parent="#submenu1">Empresas <span class=" menu-ico-collapse"><i class="glyphicon glyphicon-chevron-down"></i></span></a>
	                  <div class="collapse list-group-submenu" id="submenu1">
	                  <a href="${pageContext.request.contextPath}/empresa/list" class="list-group-item sub-sub-item" data-parent="#submenu1">Listar</a>
	                  </div>
	               <a href="${pageContext.request.contextPath}/empresa/ativos" class="list-group-item sub-item">Ativos</a>
	               <a href="${pageContext.request.contextPath}/importar" class="list-group-item sub-item">Importar Cotações</a>
	               <a href="${pageContext.request.contextPath}/importarativos" class="list-group-item sub-item">Importar Ativos</a>
<!-- 	               <a href="#submenu2" class="list-group-item sub-item" data-toggle="collapse" data-parent="#submenu2">Importar Cotações <span class=" menu-ico-collapse"><i class="glyphicon glyphicon-chevron-down"></i></span></a> -->
<!-- 	                  <div class="collapse list-group-submenu" id="submenu2"> -->
<!-- 	                    <a href="lojaListarInstalacao" class="list-group-item sub-sub-item" data-parent="#submenu2">Importar</a> -->
<!-- 	                    <a href="lojaListarProgramacao" class="list-group-item sub-sub-item" data-parent="#submenu2">Listar Programação</a> -->
<!-- 	                  </div>  -->
				
	             </div>
	            
	            </div> 
	        </div>
	      </div>
		</div>
     </div>
   </div>
  </div>

