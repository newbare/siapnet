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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<jsp:include page="/header.jsp" flush="true"/>
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
<div id="conteudo">
	<fieldset>
		<legend>
			<label class="lbTituloLegend"> Busca de Almoxarifado </label>
		</legend>
		<form action="almoxarifado.buscarAlmoxarifado.logic" method="post" id="frmBuscarAlmoxarifado" onsubmit="return validaBusca()">
			<label for="criteria">Informe o Nome do Almoxarifado:</label>
			<input type="text" id="criteria" name="criteria" size="60" maxlength="50" />
			<input type="submit" value="Buscar" /> 
		</form>
	</fieldset>
  <fieldset><legend><b>| <label class="lbTituloLegend">Listagem de almoxarifados</label> |</b></legend>     
    <display:table id="almoxarifado" class="grid" name="almoxarifados" pagesize="${paginas}" requestURI="almoxarifado.listAll.logic">
      <display:column title="id">${almoxarifado.id}</display:column>
      <display:column title="nome" sortable="true">${almoxarifado.nome}</display:column>
      <display:column property="responsavel" sortable="true"/>
      <display:column property="telefone"/>
      <display:column title="Ação">
        <a href="almoxarifado.get.logic?almoxarifado.id=${almoxarifado.id}"><img src="images/edit.png" alt="Editar almoxarifado" /></a>
        &nbsp;
        <a href="javascript:confirmDelete('almoxarifado.remove.logic?almoxarifado.id=${almoxarifado.id}')"><img src="images/trash.png" alt="Excluir almoxarifado" /></a>
      </display:column>
    </display:table>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true"/>
