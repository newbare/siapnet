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
<c:if test="${fn:length(parametros) > 0}">
  <table width="95%" class="grid">
    <thead>
      <tr>
        <th colspan="2">Parâmetros</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="parametro" items="${parametros}">
        <tr>
          <td>${parametros[i].titulo}: </td>
          <td>
            <input type="text" name="${parametros[i].nome}"
                   <c:if test="${('java.util.Date'==parametros[i].tipo)}" >alt="data"</c:if>
            <c:if test="${('java.lang.Long' == parametros[i].tipo) || 'java.lang.Integer' == (parametros[i].tipo))}" > alt="numeric"</c:if>
            />
            <%-- <input type="hidden" value="parametros[${i}].tipo" value="${parametros[i].tipo}" /> --%>
          </td>
        </tr>
        <c:set var="i" value="${i+1}"/>
      </c:forEach>
    </tbody>
  </table>
</c:if>