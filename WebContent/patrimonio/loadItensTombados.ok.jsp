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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fieldset><legend>Bens</legend>
<form method="post" action='<c:url value="patrimonio.preparaTransferencia.logic" />'>
<display:table id="patrimonio" class="grid" name="itensTombados" pagesize="10" requestURI="patrimonio.listTombados.logic">
	<c:if test="${departamento != null && departamento.id != 0}">
	<display:column title="Selecionar">
		<input type="checkbox" value="${patrimonio.id}" name="patrimoniosId[]">
	</display:column>
	</c:if>
	<display:column title="Tombo">
		<a href="patrimonio.recupera.logic?patrimonio.id=${patrimonio.id}">${patrimonio.numero}</a>
	</display:column>
	<display:column title="nome" property="itemEntrada.item.nome"
		maxWords="4" sortable="true" />
	<display:column property="modelo" maxWords="4" sortable="true" />
	<display:column title="marca" property="marca.nome" maxWords="4"
		sortable="true" />
	<display:column title="estado" property="estadoDepreciacao.nome"
		maxWords="4" sortable="true" />
	<display:column title="departamento">
		<c:choose>
			<c:when test="${patrimonio.departamento != null}">${patrimonio.departamento.nome}</c:when>
			<c:otherwise>SETOR DE PATRIMÔNIO</c:otherwise>
		</c:choose>
	</display:column>
	<display:column>
		<a
			href="<c:url value="patrimonio.preparaTransferencia.logic" />?patrimonio.id=${patrimonio.id}">
		<img src='<c:url value="/images/arrow_right.png" />'
			title="Transfere item" /> </a>
		<a
			href="javascript:confirmDelete('patrimonio.remove.logic?patrimonio.id=${patrimonio.id}')"><img
			src="images/trash.png" title="Apagar este tombamento" /></a>
	</display:column>
</display:table>
<c:if test="${fn:length(itensTombados) > 0 && departamento != null && departamento.id != 0}">
	<input type="submit" value="Transferir em masa" style="margin: 10px;" />
</c:if>
</form>
</fieldset>
