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
<jsp:include page="/header.jsp" flush="true" />
<script type="text/javascript">
function loadBens(id) {
    $("#bens").load("patrimonio.loadItensTombados.logic", {"departamento.id": id});
}
</script>
<div id="conteudo">
	<h2>Listagem de itens permanentes tombados pelo patrimônio</h2>
	<label for="departamento.id">Selecione um departamento:</label>
	<select name="departamento.id" id="departamento.id" onchange="loadBens(this.value);">
		<option value="0">Todos os departamentos</option>
		<c:forEach var="d" items="${departamentos}">
			<option value="${d.id}">${d.nome}</option>
		</c:forEach>
	</select>
	<div id="bens">
		<jsp:include page="loadItensTombados.ok.jsp" flush="true"/>
	</div>
</div>
<jsp:include page="/footer.jsp" flush="true" />