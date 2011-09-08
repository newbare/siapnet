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
<c:set var="i" value="0"/>
<c:if test="${fn:length(autorizacaoSaida.bens) > 0}">
<table width="95%" class="grid">
<thead>
  <tr>
    <th>Tombo</th><th>Modelo</th><th>Marca</th><th>Núm. de Série</th><th>Observação</th><th></th>
  </tr>
</thead>
<tbody>
<c:forEach var="p" items="${autorizacaoSaida.bens}">
  <c:set var="i" value="${i+1}"/>
  <c:if test="${i % 2 != 0}">
    <tr class="odd">
  </c:if>
  <c:if test="${i % 2 == 0}">
    <tr class="even">
  </c:if>
  <td>${p.numero}</td><td>${p.modelo}<td>${p.marca.nome}</td><td>${p.numeroSerie}</td><td>${p.observacao}</td><td><input type="button" onclick="remItem(${p.id})" class="button" value="remover bem"/></td>
  </tr>
</c:forEach>
</tbody>
</table>
</c:if>