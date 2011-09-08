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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib  prefix="display" uri="http://displaytag.sf.net" %>
<jsp:include page="/header.jsp" flush="true" />
<head>
  <script>
      $(document).ready(function() {          
          buscarPaginacao();
      });
      function buscarPaginacao(){
          var url= window.location.href ;
          url = url.replace("itemEstoque.filtro.logic","itemEstoque.buscar.logic");
          var k=url.search('-p=');
          if( k!=-1)
              $("#itens").load(url);
      }

      function buscar(){
          var codItemId = document.getElementById("codItem").value;
          var nome = document.getElementById("nome").value;
          var tipoMedida = document.getElementById("tipoMedida").value;
          var grupo = document.getElementById("grupo").value;
          $("#itens").load("itemEstoque.buscar.logic", {
              "itemEstoque.item.id" : codItemId ,
              "itemEstoque.item.nome" : nome ,
              "itemEstoque.item.tipoMedidaEntrada.id" : tipoMedida ,
              "itemEstoque.item.grupo.id" : grupo });
      }
  </script>
</head>
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Busca de item em estoque</label> |</b></legend>
    <form method="post" action="itemEstoque.buscar.logic" accept-charset="utf-8">
      <table>
          <tr><td><label for="nome">Cod. Item:</label></td><td><input type="text" id="codItem" name="itemEstoque.item.id" value="${itemEntrada.item.id}" size="6" maxlength="10"/></td></tr>
        <tr><td><label for="nome">Nome:</label></td><td><input type="text" id="nome" name="itemEstoque.item.nome" value="${itemEntrada.item.nome}" size="50" maxlength="50"/></td></tr>
        <tr><td><label for="tipoMedida">Unidade de medida de entrada:</label></td>
          <td>
            <select name="item.tipoMedidaEntrada.id" id="tipoMedida">
              <option value="" >Selecione </option>
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
              <option value="" >Selecione </option>
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
        <tr><td colspan="2" align="right"><input type="button" class="button" onclick="javascript:buscar();"  value="Procurar"/></td></tr>
      </table>
    </form>
  </fieldset>
  <fieldset>
    <div id="itens">  </div>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />