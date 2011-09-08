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
<jsp:include page="/header.jsp" flush="true" />
<script>
    $(document).ready(function() {
        $("input:text").setMask();
    });
</script>
<div id="div_conteudo">
  <form action="/almoxarifado/patrimonio.termoResponsabilidade.logic" method="post">
    <fieldset><legend>Emitir Termo de Responsabilidade</legend>
      <table>
        <tr><td><label for=termoResponsabilidade.departamentoOrigem">Do : </label></td><td>
            <select  name="termoResponsabilidade.departamentoOrigem.id" id="termoResponsabilidade.departamentoOrigem"         >
              <c:forEach var="depto" items="${departamentos}">
                <c:choose>
                  <c:when test="${depto.id==11}">
                    <option value="${depto.id}" selected="selected">${depto.nome}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${depto.id}" >${depto.nome}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
        </td></tr>
        <tr><td><label for=termoResponsabilidade.departamentoDestino">Para : </label></td><td>
            <select  name="termoResponsabilidade.departamentoDestino.id" id="termoResponsabilidade.departamentoDestino"         >
              <c:forEach var="deptoD" items="${departamentos}">
                <option value="${deptoD.id}" >${deptoD.nome}</option>
              </c:forEach>
            </select>
        </td></tr>
        <tr><td><label for="termoResponsabilidade.numero">Termo Num. : </label></td><td><input type="text" alt="numeric" maxlength="4" size="3" name="termoResponsabilidade.numero" > / <select name="termoResponsabilidade.ano"><c:forEach var="a" begin="2006" end="2014"><option value="${a}"<c:if test="${a==2008}">selected="selected"</c:if>>${a}</option></c:forEach></select></td></tr>
        <tr><td><input type="submit" value="Emitir"></td></tr>
      </table>
    </fieldset>
  </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
