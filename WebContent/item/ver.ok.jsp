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
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de item para o catálogo</label> |</b></legend>
    <c:choose>
      <c:when test="${departamento.id==null}">
        <h3>Preencha os dados do formulário para cadastrar um novo item para o catálogo:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite os dados do formulário que deseja atualizar o cadastro de item para o catálogo:</h3>
      </c:otherwise>
    </c:choose>
  <form method="post" action="item.gravar.logic" accept-charset="utf-8">
    <input type="hidden" name="item.id" value="${item.id}"/>
    <table>
      <tr><td><label for="nome">Cod. Item:</label></td><td>${item.id}</td></tr>
      <tr><td><label for="nome">Nome:</label></td><td><input type="text" id="nome" name="item.nome" value="${item.nome}" size="50" maxlength="100"/></td></tr>
      <tr><td><label for="aplicacao">Aplicação:</label></td><td><input type="text" id="aplicacao"  name="item.aplicacao" value="${item.aplicacao}" size="50" maxlength="50"/></td></tr>
        <tr><td><label for="tipoMedidaEntrada">Tipo de medida de entrada:</label></td>
          <td>
            <select name="item.tipoMedidaEntrada.id" id="tipoMedidaEntrada">
              <c:forEach var="tipo" items="${tiposMedida}">
                <c:choose>
                  <c:when test="${tipo.id == item.tipoMedidaEntrada.id}">
                    <option value="${tipo.id}" selected="selected">${tipo.nome}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${tipo.id}">${tipo.nome}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </td>
        </tr>
      <tr><td><label for="grupo">Grupo:</label></td>
        <td>
          <select name="item.grupo.id" id="grupo">
            <c:forEach var="grupo" items="${grupos}">
              <c:choose>
                <c:when test="${grupo.id == item.grupo.id}">
                  <option value="${grupo.id}" selected="selected">${grupo.nome}</option>
                </c:when>
                <c:otherwise>
                  <option value="${grupo.id}">${grupo.nome}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
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
              <input type="checkbox" class="check" name="item.materialPermanente" id="bem" value="true"/>
            </c:otherwise>
          </c:choose>
        </td>
      </tr>
      <tr><td colspan="2" align="right"><input type="submit" class="button"/></td></tr>
    </table>
  </form>
</fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />