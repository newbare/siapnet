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
<jsp:include page="/headerModal.jsp" flush="true" />
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de item para o catálogo</label> |</b></legend>
      <input type="hidden" name="item.id" value="${item.id}"/>
      <table>
        <tr><td><label >Cod. Item:</label></td><td>${item.id}</td></tr>
        <tr><td><label for="nome">Nome:</label></td><td>${item.nome}</td></tr>
        <tr><td><label for="aplicacao">Aplicação:</label></td><td>${item.aplicacao}</td></tr>
        <tr><td><label for="tipoMedidaEntrada">Tipo de medida de entrada:</label></td>
          <td>
            <c:forEach var="tipo" items="${tiposMedida}">
              <c:choose>
                <c:when test="${tipo.id == item.tipoMedidaEntrada.id}">
                    ${tipo.nome}
                </c:when>
              </c:choose>
            </c:forEach>
          </td>
        </tr>
        <tr><td><label for="grupo">Grupo:</label></td>
          <td>
            <c:forEach var="grupo" items="${grupos}">
              <c:choose>
                <c:when test="${grupo.id == item.grupo.id}">
                  ${grupo.nome}
                </c:when>
              </c:choose>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td><label for="bem" >Material permantente?</label></td>
          <td>
            <c:choose>
              <c:when test="${item.materialPermanente == true}">
                <input type="checkbox" name="item.materialPermanente" id="bem" value="true" checked="checked"/>
              </c:when>
              <c:otherwise>
                <input type="checkbox" name="item.materialPermanente" id="bem" value="true"/>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr><td><input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></td></tr>
      </table>
  </fieldset>
</div>