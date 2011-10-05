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
  <div id="errors">
    <ul>
      <c:forEach var="error" items="${errors.iterator}">
        <li>${error.key}</li>
      </c:forEach>
    </ul>
  </div>
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de tipo de fornecedor</label> |</b></legend>
    <c:choose>
      <c:when test="${tipoFornecedor.id==null}">
        <h3>Preencha os dados do formulário para cadastrar um novo tipo de fornecedor:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite os dados do formulário que deseja atualizar o cadastro do tipo de fornecedor:</h3>
      </c:otherwise>
    </c:choose>
    <form method="post" action="tipoFornecedor.gravar.logic" accept-charset="utf-8">
      <input type="hidden" name="tipoFornecedor.id" value="${item.id}"/>
      <table>
        <tr><td>Nome:</td><td><input type="text" name="tipoFornecedor.nome" value="${tipoFornecedor.nome}" size="50" maxlength="50"/></td></tr>
      </table>
      <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
    </form>
    
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />