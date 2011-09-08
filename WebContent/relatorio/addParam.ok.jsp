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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:set var="i" value="0"/>
<c:if test="${fn:length(relatorio.parametros) > 0}">
<table class="grid">
<thead>
  <tr>
    <th>Código</th><th>Nome</th><th>Desc.</th><th>Tipo</th><th></th>
  </tr>
</thead>
<tbody>
<c:forEach var="parametro" items="${relatorio.parametros}">
  <c:if test="${i % 2 != 0}">
    <tr class="odd">
  </c:if>
  <c:if test="${i % 2 == 0}">
    <tr class="even">
  </c:if>
  <td>${parametro.id}</td><td>${parametro.nome}</td><td>${parametro.titulo}</td><td>${parametro.tipo}</td>
  <td>
    <a href="javascript:remParam(${parametro.id});" ><img src="images/trash.png" alt="remover" /></a>
  </td>
  </tr>
  <c:set var="i" value="${i+1}"/>
</c:forEach>
</tbody>
</table>
</c:if>