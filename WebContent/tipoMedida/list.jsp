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
<jsp:include page="../header.jsp" flush="true" />
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
			<label class="lbTituloLegend"> Busca de Unidades de Medidas </label>
		</legend>
		<form action="<c:url value="/tipoMedida/buscar" />" method="post" id="frmBuscarMedida" onsubmit="return validaBusca()">
			<label for="criteria">Informe o Nome do Grupo:</label>
			<input type="text" id="criteria" name="criteria" size="60" maxlength="50" />
			<input type="submit" value="Buscar" /> 
		</form>
	</fieldset>
  	<fieldset><legend><b>| <label class="lbTituloLegend">Listagem de medidas</label> |</b></legend>
    	<display:table id="tipoMedida" class="grid" name="tipoMedidaList" pagesize="${paginas}" requestURI="/tipoMedida">
	      <display:column title="Id">${tipoMedida.id}</display:column>
	      <display:column property="nome" sortable="true" />
	      <display:column title="Ação">
	        <a href="<c:url value="/tipoMedida" />/${tipoMedida.id}"><img src="<c:url value="/images/edit.png" />" title="Editar unidade de medida" /></a>
	        &nbsp;
	        <a href="javascript:confirmDelete('<c:url value="/tipoMedida/remove"/>/${tipoMedida.id}')"><img src="<c:url value="/images/trash.png" />" title="Apagar unidade de medida" /></a>
	      </display:column>
	    </display:table>
  	</fieldset>
</div>
<jsp:include page="../footer.jsp" flush="true" />