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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Almoxarifado</title>
    <style type="text/css">
      @import url("/almoxarifado/css/layout.css");
      @import url("/almoxarifado/css/grid.css");
    </style>
  </head>
  <body>
    <jsp:include page="/header.jsp" flush="true" />
    <div id="div_conteudo">
      <h2>Listagem de assistencias técnicas</h2>
      <display:table id="fornecedor" class="grid" name="fornecedores" pagesize="10" requestURI="fornecedor.listAll.logic">
        <display:column title="Id">
          <a href="fornecedor.get.logic?fornecedor.id=${fornecedor.id}">${fornecedor.id}</a>
        </display:column>
        <display:column property="nome" sortable="true" />
        <display:column property="razaoSocial" sortable="true" />
        <display:column property="telefone" />
        <display:column property="cpfCnpj" sortable="true" />
        <display:column property="inscricaoEstadual" sortable="true" />
        <display:column property="contato" sortable="true" />
        <display:column property="telefoneContato" sortable="true" />
    </display:table></div>
    <jsp:include page="/footer.jsp" flush="true" />
  </body>
</html>
