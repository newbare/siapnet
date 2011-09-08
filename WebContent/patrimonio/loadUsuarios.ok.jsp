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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<select name="transferenciaPatrimonio.responsavelRecebimento.id" id="transferenciaPatrimonio.responsavelRecebimento.id">
    <c:choose>
        <c:when test="${servidores == null || fn:length(servidores) == 0}">
            <option value="0">-- Nenhum usuário disponível --</option>
        </c:when>
        <c:otherwise>
            <option value="0">-- Selecione um usuário --</option>
            <c:forEach var="s" items="${servidores}">
                <option value="${s.id}">${s.nome}</option>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</select>
