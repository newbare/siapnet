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
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Marca</label> |</b></legend>
    <c:choose>
      <c:when test="${marca.id==null}">
        <h3>Preencha os dados do formulário para cadastrar uma nova marca:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite os dados do formulário que deseja atualizar o cadastro da marca:</h3>
      </c:otherwise>
    </c:choose>
    <form method="post" action="marca.gravar.logic" accept-charset="utf-8">
      <input type="hidden" name="marca.id" value="${marca.id}"/>
      <table>
        <tr><td>Nome:</td><td><input type="text" name="marca.nome" onblur="this.value=this.value.toUpperCase()" value="${marca.nome}" size="30" maxlength="25"/></td></tr>
      </table>
      <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
    </form>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />