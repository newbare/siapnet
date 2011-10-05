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
<script>
    function loadRecibos(){
        var deptoId = document.getElementById("depto").value;
        var almoxId = document.getElementById("almoxarifadoId").value;
        $("#parametros").load("relatorio.loadRecibos.logic", { "departamento.id": deptoId, "recibo.almoxarifado.id": almoxId});
    }

    function setaDepto(dpto){
        document.getElementById("depto").value=dpto;
    }
</script>
<div id="conteudo">
    <fieldset><legend><b>| <label
            class="lbTituloLegend">Buscar Recibo de retirada de itens</label> |</b></legend>
        <h3>Selecione o setor do qual pertence o consumidor em que a requisição foi entregue.</h3>
        <form method="post" action="geraRelatorio" accept-charset="utf-8">
           <input  type="hidden" name="ALMOXARIFADO" value="${almoxarifadoId}" id="almoxarifadoId" />
           <input type="hidden" name="formato" id="formato" value="application/pdf" />
            <table>
                <tr>
                    <td id="departamentos"><label>Setor: </label></td>
                    <td><select name="DEPTO" id="depto"
                                    onchange="setaDepto(this.value)" onblur="setaDepto(this.value)">
                            <c:forEach var="d" items="${departamentos}">
                                <option value="${d.id}">${d.nome}</option>
                            </c:forEach>
                    </select></td>
                    <td><input type="button" class="button" onclick="loadRecibos()"	value="Procurar"></td>
                </tr>
            </table>
        </form>
    </fieldset>
    <div id="parametros"></div>
</div>
<jsp:include page="/footer.jsp" flush="true" />