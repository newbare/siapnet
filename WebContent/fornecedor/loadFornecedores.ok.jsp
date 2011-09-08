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
<select name="notaEntrada.fornecedor.id" id="notaEntrada.fornecedor.id" onkeypress="passaCampo(event,'notaEntrada.tipoEntrada.id')">
<c:forEach var="fornecedor" items="${fornecedores}">
  <c:if test="${fornecedor.id ==notaEntrada.fornecedor.id}">
    <option value="${fornecedor.id}" selected="selected">${fornecedor.nome}</option>
  </c:if>
  <c:if test="${fornecedor.id != notaEntrada.fornecedor.id}">
    <option value="${fornecedor.id}">${fornecedor.nome}</option>
  </c:if>
</c:forEach>