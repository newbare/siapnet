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
    function loadTransferencias(id) {
        $("#transferencias").load("patrimonio.loadTransferencias.logic", {"departamento.id": id});
    }
</script>
<div id="div_conteudo">
	<h2>Emissão de comprovante de transferência</h2>
	<label for="departamento.id">Escolha o departamento de destino cujo a transferência foi efetuada: </label><br/>
	<select name="departamento.id" onchange="loadTransferencias(this.value)">
		<option value="0">-- Escolha um departamento --</option>
		<c:forEach var="departamento" items="${departamentos}">
			<option value="${departamento.id}">${departamento.nome}</option> 
		</c:forEach>
	</select>
	<div id="transferencias"><jsp:include page="loadTransferencias.ok.jsp" flush="true"/></div>
</div>
<jsp:include page="/footer.jsp" flush="true" />