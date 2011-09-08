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
	function loadUsuarios(id) {
	    $("#servidores").load("patrimonio.loadUsuarios.logic", {"departamento.id": id});
	}
</script>
<div id="div_conteudo">
<form method="post" action="patrimonio.transfere.logic">
<fieldset><legend><b>| <label
	class="lbTituloLegend">Transferencia de Bem Permanente Tombado</label>
|</b></legend>
<table>
	<tr>
		<td><label for="marca">Setor para onde se destina o bem:</label></td>
		<td><select name="transferenciaPatrimonio.setorDestino.id" onchange="loadUsuarios(this.value);">
			<option value="0">Selecione um departamento</option>
			<c:forEach var="depto" items="${departamentos}">
				<option value="${depto.id}">${depto.nome}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td><label for="transferenciaPatrimonio.responsavelRecebimento.id">Responsável pelo recebimento:</label></td>
		<td><div id="servidores"><jsp:include page="loadUsuarios.ok.jsp" flush="true"/></div></td>
	</tr>
	<tr>
		<td><label for="localizacao">Localização:</label></td>
		<td><input type="text" name="localizacao" id="localizacao"
			size="50" maxlength="80" /></td>
	</tr>
	<tr>
		<td><label for="motivo">Motivo:</label></td>
		<td><input type="text" name="transferenciaPatrimonio.motivo"
			id="motivo" size="50" maxlength="80" /></td>
	</tr>
	<tr>
		<td>Bens:</td>
		<td>
			<c:set var="i" value="0"/>
			<c:forEach var="bem" items="${bens}">
				<input type="hidden" name="patrimoniosId[]" value="${bem.id}"/>
				<c:set var="i" value="${i + 1}"/>
			</c:forEach>
			<display:table id="patrimonio" class="grid" name="bens">
			<display:column title="Tombo">
				${patrimonio.numero}
			</display:column>
			<display:column title="nome" property="itemEntrada.item.nome"/>
			<display:column property="modelo" maxWords="4" sortable="true" />
			<display:column title="marca" property="marca.nome"/>
			<display:column title="estado" property="estadoDepreciacao.nome"/>
			<display:column title="departamento">
				<c:choose>
					<c:when test="${patrimonio.departamento != null}">${patrimonio.departamento.nome}</c:when>
					<c:otherwise>SETOR DE PATRIMÔNIO</c:otherwise>
				</c:choose>
			</display:column>
		</display:table></td>
	</tr>
	<tr>
		<td align="center"><input type="button" id="voltar"
			class="button" name="voltar" value="Voltar"
			onclick="history.back(-1)" /></td>
		<td align="center"><input type="submit" class="button"
			value="Transferir" /></td>
	</tr>
</table>
</fieldset>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />