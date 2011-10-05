<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
/**
 * Copyright 2010 UNCISAL Universidade de Ciências em Saúde do Estado de Alagoas.
 * 
 * This file is part of SIAPNET.
 *
 * SIAPNET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * SIAPNET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIAPNET.  If not, see <http://www.gnu.org/licenses/>.
 */
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Almoxarifado</title>
    <style type="text/css">
      @import url("/almoxarifado/css/layout.css");
      @import url("/almoxarifado/css/grid.css");
    </style>
  </head>
  <body>
    <jsp:include page="/header.jsp" flush="true" />
    <div id="conteudo">
      <fieldset><legend><b>| <label class="lbTituloLegend">Requisições que estão aguardando aprovação</label> |</b></legend>
		<display:table id="requisicao" class="grid" name="requisicoes" requestURI="requisicao.listAguardando.logic">
	    	<display:column property="id"/>
	        <display:column title="requisitante" property="usuario.nome" sortable="true"/>
	        <display:column title="data de requisição" sortable="true">
	        	<fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/>
	        </display:column>
	        <display:column>
	        	<a href="requisicao.avaliacaoForm.logic?requisicao.id=${requisicao.id}"><img src="images/edit.png" title="Avaliar requisição"/></a>
	         </display:column>
	    </display:table>
      </fieldset>
    </div>
    <jsp:include page="/footer.jsp" flush="true" />
  </body>
</html>
