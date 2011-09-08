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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/header.jsp" flush="true" />
<style type="text/css">.embed + img { position: relative; left: -21px; top: -1px; }</style>
<script charset="utf-8">
    $(document).ready(function() {
    	$("#itens").autocomplete({
            source: "item.loadItens.logic?almoxarifado.id=${requisicao.almoxarifado.id}&",
            minLength: 3,
            select: function(event, ui) {
           		$('#codItem').val(ui.item.id);
            	$('#itens').val(ui.item.label);
            }
        });        
        $("input:text").setMask();
        $("#data").datepicker({
            showOn: "button",
            buttonImage: "/almoxarifado/images/cal.gif",
            buttonImageOnly: true
        });
        $(".validade").datepicker({
            showOn: "button",
            buttonImage: "/almoxarifado/images/cal.gif",
            buttonImageOnly: true
        });
    });

    function buscar() {
    	itemId	= document.getElementById("codItem").value;
        almoxId	= document.getElementById("almoxarifado").value;
        fornecedorId = document.getElementById("notaEntrada.fornecedor.id").value;
        tipoEntradaId = document.getElementById("tipoEntrada").value;
        data = document.getElementById("dataNota").value;
        numero = document.getElementById("numero").value;
        observacao = document.getElementById("observacao").value;
        if (itemId==null)itemId="";
        $("#notasEntrada").load("entradaItens.buscar.logic", {
            "notaEntrada.almoxarifado.id": almoxId,
            "notaEntrada.data": data,
            "notaEntrada.numero":numero,
            "notaEntrada.observacao":observacao,
            "notaEntrada.fornecedor.id": fornecedorId,
            "notaEntrada.tipoEntrada.id": tipoEntradaId,
            "notaEntrada.itensEntrada[0].item.id": itemId
        });
    }
</script>
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Procurar nota de entrada</label> |</b></legend>
        <h3>Preencha os critérios que queira usar para buscar a nota de entrada de itens desejada :</h3>
        <form action="#" method="POST" >
            <fieldset><legend>Almoxarifado</legend>
                <table>
                    <tr>
                        <td>
                        <label for="almoxarifado">Nome:</label></td><td>
                            <select name="notaEntrada.almoxarifado.id" id="almoxarifado" onchange="atualizaAlmox()" >
                                <option value=""></option>
                                <c:forEach var="almoxarifado" items="${almoxarifados}">
                                    <option value="${almoxarifado.id}"
                                            <c:if test="${almoxarifado.id == notaEntrada.almoxarifado.id}">
                                            selected="selected"
                                    </c:if> >${almoxarifado.nome}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                        <label for="tipoEntrada">Tipo de Entrada:</label></td><td>
                            <select name="notaEntrada.tipoEntrada.id" id="tipoEntrada" >
                                <option value=""> </option>
                                <c:forEach var="tipo" items="${tiposEntrada}">
                                    <option value="${tipo.id}">${tipo.nome}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <fieldset><legend>Dados da Nota de Entrada</legend>
                <table>
                    <tr>
                        <td><label for="dataNota">Data: </label></td>
                        <td><input alt="date" id="dataNota" name="notaEntrada.data" size="10" /></td>
                        <td><label for="numero">Número da nota fiscal: </label></td>
                        <td><input alt="numeric" id="numero" name="notaEntrada.numero" value="${notaEntrada.numero}" size="10" maxlength="100" /></td>
                    </tr>
                    <tr>
	                    <td><label for="observacao">Observação: </label></td>
	                    <td><input id="observacao" name="notaEntrada.observacao" value="${notaEntrada.observacao}" size="30" maxlength="200" /></td>
                    </tr>
                    <tr>
                        <td><label for="notaEntrada.fornecedor.id">Fornecedor: </label></td>
                        <td>
                        	<select name="notaEntrada.fornecedor.id" id="notaEntrada.fornecedor.id" >
                                    <option value=""></option>
                                    <c:forEach var="fornecedor" items="${fornecedores}">
                                        <c:if test="${fornecedor.id ==notaEntrada.fornecedor.id}">
                                            <option value="${fornecedor.id}" selected="selected">${fornecedor.nome}</option>
                                        </c:if>
                                        <c:if test="${fornecedor.id != notaEntrada.fornecedor.id}">
                                            <option value="${fornecedor.id}">${fornecedor.nome}</option>
                                        </c:if>
                                    </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <fieldset><legend>Itens de Entrada</legend>
                <table>
                    <tr>
                        <td><label for="codItem">Cod.Item: </label></td>
                        <td><input alt="numeric" type="text" id="codItem" size="6" onblur="loadItemById(this.value, 'itens')"/></td>
                        <td><label for="itens">Item: </label></td>
                        <td><input type="text" id="itens" title="Digite o nome do item que procura." size="50"/></td>
                    </tr>
                </table>
            </fieldset>
            <p align="center"><input type="button" class="button" value="Buscar"  onclick="buscar()"/>
        </form>
    </fieldset>
    <div id="notasEntrada">
    </div>
    <br />
</div>
<jsp:include page="/footer.jsp" flush="true" />