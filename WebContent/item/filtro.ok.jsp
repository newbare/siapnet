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
          url = url.replace("item.filtro.logic","item.buscar.logic");
          var k=url.search('-p=');
          if( k!=-1)
              $("#itens").load(url);
      }

      function buscar(){
          var codItemId = document.getElementById("codItem").value;
          var nome = document.getElementById("nome").value;
          var aplicacao = document.getElementById("aplicacao").value;
          var tipoMedida = document.getElementById("tipoMedida").value;
          var grupo = document.getElementById("grupo").value;
          var bem = false;
          if( document.getElementById("bem").checked )
              bem = true;
          
          $("#itens").load("item.buscar.logic", {
              "item.id" : codItemId ,
              "item.nome" : nome ,
              "item.aplicacao":  aplicacao ,
              "item.tipoMedidaEntrada.id" : tipoMedida ,
              "item.materialPermanente" : bem ,
              "item.grupo.id" : grupo });
      }
  </script>
</head>
<div id="conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Busca de item no catálogo</label> |</b></legend>
    <form method="post" action="item.buscar.logic" accept-charset="utf-8">
      <table>
          <tr><td><label for="nome">Cod. Item:</label></td><td><input type="text" id="codItem" name="item.id" value="${item.id}" size="6" maxlength="10"/></td></tr>
        <tr><td><label for="nome">Nome:</label></td><td><input type="text" id="nome" name="item.nome" value="${item.nome}" size="50" maxlength="50"/></td></tr>
        <tr><td><label for="aplicacao">Aplicação:</label></td><td><input type="text" id="aplicacao"  name="item.aplicacao" value="${item.aplicacao}" size="50" maxlength="50"/></td></tr>
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
        <tr>
          <td><label for="bem" >Material permantente?</label></td>
          <td>
            <c:choose>
              <c:when test="${item.materialPermanente == true}">
                <input type="checkbox" name="item.materialPermanente" id="bem" value="true" class="radio" checked="checked"/>
              </c:when>
              <c:otherwise>
                <input type="checkbox" class="radio" name="item.materialPermanente" id="bem" value="true"/>
              </c:otherwise>
            </c:choose>
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