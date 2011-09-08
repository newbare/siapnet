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
<select name="requisicao.usuario.id">
<c:choose>
	<c:when test="${consumidores != null && fn:length(consumidores) > 0}">		
		<option value="0">-- Selecione um consumidor --</option>
		    <c:forEach var="c" items="${consumidores}">
			<c:choose>
				<c:when test="${requisicao.usuario.id == c.id}">
					<option value="${c.id}" selected="selected">${c.nome}</option>
				</c:when>
				<c:otherwise>
					<option value="${c.id}">${c.nome}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<option value="0">-- Nenhum consumidor disponível--</option>
	</c:otherwise>
</c:choose>
</select>  