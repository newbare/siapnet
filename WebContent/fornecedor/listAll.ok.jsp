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
	<!--Script para validar a busca, onde a validação é verificar se o valor a ser pesquisado é maior que 3 letras-->
	<script type="text/javascript">
		function validaBusca() {
			if (document.getElementById('criteria').value.length >= 3) {
				return true;
			} else {
				alert("Para realizar a busca o valor informado deve conter pelo menos 3 letras!");
				return false;
			}
		}
	</script>
	<!--Condicional para definir a quantidade de registro por paginação, 
	    o resultado de uma pesquisa num deve ter paginação, caso contrário, irá exibir 30 registros por paginação -->
	<c:if test="${criteria == null}">
		<c:set var="paginas" value="20" />
	</c:if>
	<c:if test="${criteria != null}">
		<c:set var="paginas" value="0" />
	</c:if>
	<div id="div_conteudo">
		<fieldset>
			<legend>
				<label class="lbTituloLegend"> Busca de Fornecedores </label>
			</legend>
			<form action="fornecedor.buscarFornecedor.logic" method="post" id="frmBuscarFornecedor" onsubmit="return validaBusca()">
				<label for="criteria">Informe o Nome do Fornecedor:</label>
				<input type="text" id="criteria" name="criteria" size="60" maxlength="50" />
				<input type="submit" value="Buscar" /> 
			</form>
		</fieldset>
	  <fieldset>
	  <legend><b>| <label class="lbTituloLegend">Listagem de fornecedores</label> |</b></legend>
	  <display:table id="fornecedor" class="grid" name="fornecedores" pagesize="${paginas}" requestURI="fornecedor.listAll.logic">
	    <display:column title="Id">
	      <a href="fornecedor.ver.logic?fornecedor.id=${fornecedor.id}">${fornecedor.id}</a>
	    </display:column>
	    <display:column title="Nome" sortable="true" >
	      <a href="fornecedor.ver.logic?fornecedor.id=${fornecedor.id}">${fornecedor.nome}</a>
	    </display:column>
	    <display:column title="CNPJ/CPF" property="cpfCnpj" sortable="true" />
	    <display:column title="IE" property="inscricaoEstadual" sortable="true" />
	    <display:column title="Telefone" property="telefone" />
	    <display:column title="Tipo fornecedor" property="tipoFornecedor.nome" sortable="true" />
	
	    <display:column title="Contato" property="contato" sortable="true" />
	    <display:column title="Ação">
	            <a href="fornecedor.get.logic?fornecedor.id=${fornecedor.id}">
	      <img	src="images/edit.png" /></a>&nbsp;
	      <a href="javascript:confirmDelete('fornecedor.remove.logic?fornecedor.id=${fornecedor.id}')">
	      <img	src="images/trash.png" /></a>
	
	    </display:column>
	  </display:table>
	  </fieldset>
	</div>
	<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>
