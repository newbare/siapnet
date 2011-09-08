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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="pt_BR" scope="application"/>
<c:if test="${departamento.almoxarifados != null && fn:length(departamento.almoxarifados) > 0}">
	<table width="95%" class="grid">
	<thead>
	  <tr>
	    <th>Código</th><th>Nome</th><th>Orgao</th><th>Ação</th>
	  </tr>
	</thead>
	<tbody>
	<c:forEach var="a" items="${departamento.almoxarifados}">
	  <c:set var="i" value="${i+1}"/>
	  <c:set var="classe" value="odd"/>  
	  <c:if test="${i % 2 == 0}">
	    <c:set var="classe" value="even"/>
	  </c:if>
	  <tr class="${classe}">
	  	<td>${a.id}</td>
		<td>${a.nome}</td>
		<td>${a.unidade.orgao.nome}</td>	
	  	<td><a href="javascript:remAlmoxarifado(${a.id});"><img src="images/trash.png" title="remover almoxarifado"/></a></td>
	  </tr>
	</c:forEach>
	</tbody>
	</table>
</c:if>