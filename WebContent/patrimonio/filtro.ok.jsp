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
<script type="text/javascript">
 $(document).ready(function() {
        $("input:text").setMask();
        buscarPaginacao();
    });
    
    function buscar() {
        var nome = document.getElementById("bem").value;
        var tombo = document.getElementById("tombo").value;
        var marca = document.getElementById("marca").value;
        var modelo = document.getElementById("modelo").value;
        var estadoD = document.getElementById("estadoD").value;
        $("#itensPermanentes").load("patrimonio.buscar.logic", {
            "patrimonio.itemEntrada.item.nome" : nome ,
            "patrimonio.numero": tombo ,
            "patrimonio.marca.id" : marca ,
            "patrimonio.modelo" : modelo ,
            "patrimonio.estadoDepreciacao.id":estadoD});
    }
    function buscarPaginacao(){
        var url= window.location.href ;
        url = url.replace("patrimonio.filtro.logic","patrimonio.buscar.logic");
        var k=url.search('-p=');
        if( k!=-1)
            $("#itensPermanentes").load(url);
    }
</script>  
<div id="conteudo">
  <form method="post" action="patrimonio.buscar.logic">
    <fieldset><legend><b>| <label class="lbTituloLegend">Busca de Bens Permanentes Tombados</label> |</b></legend>
    <input type="hidden" name="patrimonio.itemEntrada.notaEntrada.almoxarifado.id" value="${patrimonio.itemEntrada.notaEntrada.almoxarifado.id}" />
    <table>
        <tr>
          <td><label for="tombo">Número Tombamento:</label></td>
          <td>
          	<input type="text" name="patrimonio.numero" id="tombo" value="${patrimonio.numero}" size="5" maxlength="6" />
          </td>
        </tr>
        <tr>
          <td><label for="bem">Nome do Bem:</label></td>
          <td>
          	<input type="text" name="patrimonio.itemEntrada.item.nome" id="bem" size="40" />
          	<input type="hidden" name="patrimonio.itemEntrada.id" id="patrimonio.itemEntrada.id"/>
          </td>
        </tr>
        <tr>
          <td><label for="marca">Marca:</label></td>
          <td>
          	<select name="patrimonio.marca.id" id="marca" >
	             <option value=""></option>
	             <c:forEach var="marca" items="${marcas}" >
	               <option value="${marca.id}">${marca.nome}</option>
	             </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <td><label for="modelo">Modelo:</label></td>
          <td>
          	<input type="text" name="patrimonio.modelo" id="modelo" value="${patrimonio.modelo}" size="15" />
          </td>
        </tr>
        <tr>
          <td><label for="estadoD">Estado de Depreciação:</label></td>
          <td>
          	<select name="patrimonio.estadoDepreciacao.id" id="estadoD" >
	            <option value=""></option>
	            <c:forEach var="estadoD" items="${estadosDepre}" >
	              <option value="${estadoD.id}">${estadoD.nome}</option>
	            </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="center">
          <input type="button" class="button" onclick="javascript:buscar();"  value="Procurar"/></td>
        </tr>
      </table>
    </fieldset>
    <fieldset>
      <div id="itensPermanentes"></div>
    </fieldset>
  </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />