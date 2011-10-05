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
    	$("#itens").autocomplete({
            source: "item.loadItens.logic?almoxarifado.id=${requisicao.almoxarifado.id}&",
            minLength: 3,
            select: function(event, ui) {
           		$('#codItem').val(ui.item.id);
            	$('#itens').val(ui.item.label);
            }
        });

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

    function validar(){
        var dtIni = document.getElementById("dataIni").value;
        var dtFim = document.getElementById("dataFim").value;
        if (dtIni=="" || dtFim==""){
            alert('As datas do período devem ser informadas.');
            return false;

        } else {
            return checarDataInicialMaior(dtIni, dtFim);
        }
        return true;
    }
</script>

<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Relatório de Consumo Material de um Item específico</label> |</b></legend>
        <form method="post" action="geraRelatorio" accept-charset="utf-8" onsubmit="return validar();"  target="_blank">
            <input type="hidden" name="relatorio" value="ConsumoItem" />
            <input type="hidden" name="ALMOXARIFADO" value="${almoxarifadoId}" />
            <table>
                <tr>
                    <td><label>Almoxarifado: </label></td>
                    <td>
                        <select id="ALMOXARIFADO" name="ALMOXARIFADO" style="width: 300px;">
                            <c:forEach var="almoxarifado" items="${almoxarifados}">
                                <option value="${almoxarifado.id}">${almoxarifado.nome}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Cod. Item: </label></td>
                    <td><input alt="numeric" type="text" name="ITEM" id="codItem" size="6" onblur="loadItemById(this.value, 'itens')"/></td>
                </tr>
                <tr>
                    <td><label>Item: </label>
                    </td><td><input type="text" id="itens" name="itens" size="50"/></td>
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