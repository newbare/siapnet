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
<!-- Tratamento de erros -->
<center>
    <c:if test="${(fn:length(errors.iterator) > 0 ||msgErro!=null)}">
        <div id="errorswrapper"><br/>
            <div id="errors">
                <ul>
                    <c:forEach var="error" items="${errors.iterator}">
                        <li>${error.key}</li>
                    </c:forEach>
                    <c:if test="${msgErro!=null}"><li>${msgErro}</li></c:if>
                </ul>
            </div>
        </div>
    </c:if>
    <c:if test="${message!=null}"><div id="errorswrapper"><br/><div id="messages"><p>&nbsp;${message}</p></div></div></c:if>
    <c:if test="${alert!=null}"><div id="errorswrapper"><br/><div id="alerts"><p>&nbsp;${alert}</p></div></div></c:if>
</center>
<!-- fim do tratamento de erros -->
