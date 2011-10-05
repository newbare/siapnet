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
<jsp:include page="/header.jsp" flush="true" />
<div id="conteudo">
  <form method="post" action="geraRelatorio" accept-charset="utf-8"   target="_blank">
  <fieldset><legend><b>| <label class="lbTituloLegend">Relatório do Estoque Baixo (Itens em falta)</label> |</b></legend>
     <input type="hidden" name="relatorio" id="relatorio" value="EstoqueBaixo" />
                <div id="almox">
                <label>Almoxarifado: </label>
                <select id="ALMOXARIFADO" name="ALMOXARIFADO" style="width: 300px;">
                    <c:forEach var="almoxarifado" items="${almoxarifados}">
                        <option value="${almoxarifado.id}">${almoxarifado.nome}</option>
                    </c:forEach>
                </select>
            </div>
	<jsp:include page="/relatorio/formato.jsp" flush="true" />
	<input type="submit" value="Exibir Relatório">
	</fieldset>
  </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />