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
<%@ taglib uri ="/tags/uncisal" prefix ="u"%>
<jsp:include page="/header.jsp" flush="true" />
<script>
    function loadRecursos() {
        var nome = document.getElementById("recurso.nome").value;
        var uri = document.getElementById("recurso.uri").value;
        var pai = document.getElementById("recurso.pai.id").value;

        $("#recursos").load("recurso.gravar.logic", {"recurso.nome" : nome , "recurso.uri": uri , "recurso.pai.id" : pai});
    }
</script>
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Perfil</label> |</b></legend>
    <c:choose>
      <c:when test="${perfil.id==null}">
        <h3>Preencha os dados do formulário para cadastrar um novo perfil:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite os dados do formulário que deseja atualizar o cadastro do perfil:</h3>
      </c:otherwise>
    </c:choose>
    <form method="post" action="perfil.gravar.logic">
      <input type="hidden" name="perfil.id" value="${perfil.id}" />
      <table>
        <tr><td>Perfil:</td><td><input type="text" id="perfil.nome" name="perfil.nome" value="${perfil.nome}" maxlength="25" /></td></tr>
        <tr>
          <td colspan="2">
            <fieldset>
              <legend>Recursos disponíveis</legend>
              <u:treePrintList treeables="${recursos}" checkeable="true" existentes="${perfil.recursos}" />
            </fieldset>
          </td>
        </tr>
      </table>
      <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
    </form>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />