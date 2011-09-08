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
<script>
    $(document).ready(function() {
        $("#dataIni").datepicker({
            showOn: "button",
            buttonImage: "/almoxarifado/images/cal.gif",
            buttonImageOnly: true
        });
        $("#dataFim").datepicker({
            showOn: "button",
            buttonImage: "/almoxarifado/images/cal.gif",
            buttonImageOnly: true
        });
    });

    function setaDepto(dpto){
        document.getElementById("deptoId").value=dpto;
    }

    function validar(){
        var dtIni = document.getElementById("dataIni").value;
        var dtFim = document.getElementById("dataFim").value;
        if (dtIni=="" || dtFim==""){
            alert('As datas do período devem ser informadas.');
            return false;
        } else {
            return checarDataInicialMaior(dtIni, dtFim);
        }
    }
</script>
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Relatório de Consumo de Material de um Setor</label> |</b></legend>
    <form method="post" action="geraRelatorio" accept-charset="utf-8" onsubmit="return validar();"  target="_blank">
      <input type="hidden" name="relatorio" value="ConsumoSetor" />
      <c:if test="${almoxarifadoId != null}">
      	<input type="hidden" name="ALMOXARIFADO" value="${almoxarifadoId}" />
      </c:if>
	  <table>      
      <c:if test="${almoxarifadoId == null}">
      	<tr>
      		<td><label>Selecione o almoxarifado: </label></td>
      		<td>
		      	<select name="ALMOXARIFADO">
		      		<c:forEach var="a" items="${almoxarifados}">
		      			<option value="${a.id}">${a.nome}</option>
		      		</c:forEach>      		
		      	</select>
		    </td>
      	</tr>
      </c:if>	  
        <tr ><td id="departamentos">        <label>Setor: </label></td>
          <td>
            <select name="DEPTO" id="depto" onchange="setaDepto(this.value)" onblur="setaDepto(this.value)" >
              <c:forEach var="d" items="${departamentos}">
                <option value="${d.id}">${d.nome}</option>
              </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td><label>Período Início: </label></td>
          <td><input type="text" id="dataIni" name="DT_INI" alt="date" size="10" /></td>
        </tr>
        <tr>
          <td><label>Período Fim: </label></td>
          <td><input type="text" id="dataFim" name="DT_FIM" alt="date" size="10" /></td>
        </tr>
      </table>
		<jsp:include page="/relatorio/formato.jsp" flush="true" />
      <input type="submit" value="Exibir Relatório">
    </form>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />