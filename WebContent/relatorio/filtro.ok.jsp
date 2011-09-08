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
    function loadParametrosRelatorio(relId){
        var relatorio = relId;
        if (relatorio!='') {
            $("#parametros").load("relatorio.loadParams.logic", {"relatorio.arquivoJasper" : relatorio});
        }
    }
</script>
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Relatórios</label> |</b></legend>
    <form method="post" action="geraRelatorio" accept-charset="utf-8">
      <label for="relatorio">Relatório : </label>
      <select id="relatorio" name="relatorio" onblur="loadParametrosRelatorio(this.value)" onchange="loadParametrosRelatorio(this.value)" >
        <option value="" ></option>
        <c:forEach var="rel" items="${relatorios}">
          <option value="${rel.arquivoJasper}" >${rel.titulo}</option>
        </c:forEach>
      </select>
    <label for="formato">Formato : </label>
    <select id="almoxarifado" name="ALMOXARIFADO">
      <option value="0">Selecione o Almoxarifado</option>
        <c:forEach var="almox" items="${almoxarifados}">
          <option value="${almox.id}" >${almox.nome}</option>
        </c:forEach>
    </select>
	<fieldset><legend>Formato:</legend>
		<table>
			<tr>
				<td><input type="radio" id="formato" name="formato" value="application/pdf" selected="selected" />
					<img src="/almoxarifado/images/pdf.gif" width="30" height="30" />Abobe	(*.PDF)</td>
				<td><input type="radio" id="formato" name="formato"	value="application/vnd.ms-excel" />
					<img src="/almoxarifado/images/excel.gif" width="30" height="30" />Excel (*.XLS)</td>
				<td><input type="radio" id="formato" name="formato" value="application/rtf" />
					<img src="/almoxarifado/images/word.gif" width="30" height="30" />Word (*.DOC)</td>
			</tr>
		</table>
	</fieldset>

      <fieldset><legend>Filtro:</legend>
        <div id="parametros">
        </div>
      </fieldset>
      <input type="submit" value="Exibir Relatório">
    </form>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />