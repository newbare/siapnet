<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<c:choose>
     	<c:when test="${fn:length(unidades) == 0}">
			<select style="width: 400px;" id="unidadeAlmoxarifado.id">
				<option value="0">-- Unidades não disponíveis --</option>
			</select>
		</c:when>
		<c:otherwise>
			<select style="width: 400px;" id="unidadeAlmoxarifado.id" onchange="loadAlmoxarifados(this.value)">
				<option value="0">-- Escolha uma unidade --</option>
				<c:forEach var="u" items="${unidades}">
					<option value="${u.id}">${u.nome}</option>
				</c:forEach>
			</select>
		</c:otherwise>
</c:choose> 