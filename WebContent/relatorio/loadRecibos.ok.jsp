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
<c:if test="${fn:length(recibos) > 0}">
    <table width="95%" class="grid">
        <thead>
            <tr>
                <th >Cod.</th><th>Data Requisição</th><th >Data Entrega</th><th >Consumidor</th><th >Recibo</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="recibo" items="${recibos}">
                <tr>
                    <td>${recibo.id} </td>
                    <td><fmt:formatDate value="${recibo.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/></td>
                    <td><fmt:formatDate value="${recibo.statusAtual.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/></td>
                    <td>${recibo.usuario.nome} </td>
                    <td><input type="button" class="button" value="Emitir" onclick="emitirRecibo(${recibo.id})"/></td>
                </tr>
                <c:set var="i" value="${i+1}"/>
            </c:forEach>
        </tbody>
    </table>
</c:if>