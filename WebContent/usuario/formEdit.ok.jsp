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
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Atualização dos seus dados</label> |</b></legend>
    <c:choose>
      <c:when test="${usuario.id==null}">
        <h3>Preencha os dados do formulário para cadastrar um novo usuário:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite o e-mail e senha que deseja atualizar no cadastro do usuário:</h3>
      </c:otherwise>
    </c:choose>
    <form method="post" action="usuario.salvarSenha.logic">
      <input type="hidden" name="usuario.id" value="${usuario.id}" />
      <table>
        <tr><td>CPF:</td><td>${usuario.cpf}</td></tr>
        <tr><td>Nome:</td><td>${usuario.nome}</td></tr>
        <tr><td>Senha atual:</td><td><input type="password" name="usuario.senha"  maxlength="20" title="Informe a senha atual" /></td></tr>
        <tr><td>Nova senha:</td><td><input type="password" name="novaSenha"  maxlength="20" title="Informe a nova senha" /></td></tr>
        <tr><td>Confirmação da nova senha:</td><td><input type="password" name="confirmacaoNovaSenha"  maxlength="20" title="Confirme a nova senha" /></td></tr>
      </table>
      <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
    </form>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />