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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<jsp:include page="/header.jsp" flush="true" />
<div id="conteudo">
<fieldset><legend><b>|<label class="lbTituloLegend">Transferência de Bem Permanente Tombado</label>|</b></legend>
<table>
	<tr>
		<td><label for="motivo">Motivo:</label></td>
		<td>${transferenciaPatrimonio.motivo}</td>
	</tr>
	<TR>
		<td><label for="setor">Setor de Origem:</label></td>
		<td>${transferenciaPatrimonio.setorOrigem.nome}</td>
	</tr>
	<tr>
		<td><label for="respSaida">Responsável pela Saída:</label></td>
		<td>${transferenciaPatrimonio.responsavelEnvio.nome}</td>
	</tr>
	<tr>
		<td><label for="dataSaida">Data Saída:</label></td>
		<td><fmt:formatDate value="${transferenciaPatrimonio.dataSaida}" pattern="dd-MM-yyyy HH:mm" /></td>
	</tr>
	<TR>
		<td><label for="setor">Setor de Destino:</label></td>
		<td>${transferenciaPatrimonio.setorDestino.nome}</td>
	</tr>
	<tr>
		<td><label for="respRecebimento">Responsável pelo
		Recebimento:</label></td>
		<td>${transferenciaPatrimonio.responsavelRecebimento.nome}</td>
	</tr>
</table>
</fieldset>
<fieldset><legend>Bens movimentados na transferência</legend>
	<display:table id="bem" class="grid" name="transferenciaPatrimonio.bens">
		<display:column property="id" />
		<display:column title="Tombo" property="numero" sortable="true" />
		<display:column title="Bem" property="itemEntrada.item.nome"
			sortable="true" />
		<display:column title="Marca" property="marca.nome" sortable="true" />
		<display:column title="Modelo" property="modelo" sortable="true" />
		<display:column title="Estado Atual" property="estadoDepreciacao.nome" sortable="true" />
	</display:table>
</fieldset>
<form method="post" action="<c:url value="geraRelatorio"/>">
	<input type="hidden" name="relatorio" value="TransferenciaRecibo"/>
	<input type="hidden" name="transferencia_id" value="${transferenciaPatrimonio.id}"/>
	<center><input type="submit" value="Emitir comprovante de transferência"/></center>
</form>
<br />
</div>
<jsp:include page="/footer.jsp" flush="true" />
