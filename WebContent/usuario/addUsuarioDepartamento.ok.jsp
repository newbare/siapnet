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
<fieldset>
    <legend>Departamentos associados ao usuário</legend>
    <center>
	    <c:if test="${(fn:length(errors.iterator) > 0 ||msgErro!=null)}">
	        <c:forEach var="error" items="${errors.iterator}">
	            <span class="erroSimples">${error.key}</span><br/>
	        </c:forEach>
	    </c:if>
	</center>
    <c:choose>
        <c:when test="${usuarioDepartamentos == null || fn:length(usuarioDepartamentos) == 0}">
            Nenhum departamento foi escolhido.
        </c:when>
        <c:otherwise>
            <table class="grid" style="min-width: 60%;">
                <thead>
                    <tr>
                        <th>Departamento</th><th>Perfil</th><th>Almoxarifado</th><th>Deletar</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="uD" items="${usuarioDepartamentos}">
                    <tr>
                        <td>${uD.departamento.nome}</td>
                        <td>${uD.perfil.nome}</td>
                        <td>${uD.almoxarifado.nome}</td>
                        <td><a href="javascript:remUsuarioDepartamento(${uD.departamento.id});"><img src="images/trash.png" title="remover" alt="remover" /></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</fieldset>