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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="i" value="0"/>
<c:if test="${fn:length(requisicao.itensRequisitados) > 0}">
	<table width="95%" class="grid">
		<thead>
			<tr>
				<th>Código</th><th>Nome</th><th>Unidade</th><th>Quantidade Solicitada</th><th>Remover</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="itemRequisitado" items="${requisicao.itensRequisitados}">
				<c:set var="i" value="${i+1}"/>
				<c:if test="${i % 2 != 0}">
				 <tr class="odd">
				</c:if>
				<c:if test="${i % 2 == 0}">
				 <tr class="even">
				</c:if>
					<td>${itemRequisitado.item.id}</td>
					<td>${itemRequisitado.item.nome}</td>
					<td>${itemRequisitado.item.tipoMedidaEntrada.nome}</td>
					<td><fmt:formatNumber value="${itemRequisitado.quantidadeSaida}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td><input type="button" onclick="remItem(${itemRequisitado.item.id})" value="remover item"/></td>
				 </tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>