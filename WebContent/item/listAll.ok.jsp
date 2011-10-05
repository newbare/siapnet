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
<jsp:include page="/header.jsp" flush="true" />
<script type="text/javascript">
	function validaBuscaCatalogo() {
		if (document.getElementById('criteria').value.length >= 3) {
			return true;
		} else {
			alert("Para realizar a busca informe um valor que contenha pelo menos 3 letras!");
			return false;
		}
	}
</script>
<c:if test="${criteria == null}">
	<c:set var="paginas" value="30" />
</c:if>
<c:if test="${criteria != null}">
	<c:set var="paginas" value="0" />
</c:if>
<div id="conteudo">
<fieldset>
	<legend>
		<label class="lbTituloLegend"> Busca de Itens no Catálogo: </label>
	</legend>
	<form action="item.buscarItens.logic" method="post" id="frmBuscarCatalogo" onsubmit="return validaBuscaCatalogo()">
		<label for="criteria">Digite o nome do item:</label>
		<input type="text" id="criteria" name="criteria" size="50" maxlength="50" />
		<input type="submit" value="Buscar" /> 
	</form>
</fieldset>
<fieldset><legend><b>| <label class="lbTituloLegend">Listagem de itens do catálogo</label> |</b></legend>
	<display:table id="item" class="grid" name="itens" pagesize="${paginas}" requestURI="item.listAll.logic">
		<display:column title="Cod. Item">				
	    	<c:choose>
         		<c:when test="${editavel}">
         			<a href="item.get.logic?item.id=${item.id}">${item.id}</a>
         		</c:when>
         		<c:otherwise>
         			${item.id}
         		</c:otherwise>
         	</c:choose>
		</display:column>
        <display:column title="Nome" property="nome" sortable="true" />
        <display:column title="Medida entrada" property="tipoMedidaEntrada.nome" />
        <display:column title="Grupo" property="grupo.nome" sortable="true" />
        <display:column title="Aplicação" property="aplicacao" sortable="true" />
		<c:if test="${editavel}">          
         <display:column  title="Ação">
             <a href="item.get.logic?item.id=${item.id}"><img src="images/edit.png" title="Editar nota de entrada"/></a>
             &nbsp;
             <a href="javascript:confirmDelete('item.remove.logic?item.id=${item.id}')"><img src="images/trash.png" title="Apagar item" /></a>
         </display:column>
        </c:if>
	</display:table>
</fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />