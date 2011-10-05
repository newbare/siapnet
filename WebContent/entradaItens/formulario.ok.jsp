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
            source: "item.loadItens.logic",
            minLength: 3,
            select: function(event, ui) {
           		$('#codItem').val(ui.item.id);
            	$('#itens').val(ui.item.label);
            }
        });

    	$(function() {
    	    $("#data").datepicker({
                showOn: "button",
                buttonImage: "/almoxarifado/images/cal.gif",
                buttonImageOnly: true
            });

            $("#iValidade").datepicker({
                showOn: "button",
                buttonImage: "/almoxarifado/images/cal.gif",
                buttonImageOnly: true
	        });
    	});    	       
    });

    function addItem() {
    	var iId 		= document.getElementById("codItem").value;
		var iQtd 		= document.getElementById("iQtd").value;
		var iValor 		= document.getElementById("iValor").value;
		var iValidade 	= document.getElementById("iValidade").value;
		var iLote 		= document.getElementById("iLote").value;
		var iValorTotal = document.getElementById("iValorTotal").checked;

		iValor = iValor.replace(".","");
		iValor = iValor.replace(",",".");
		iQtd = iQtd.replace(".","");
		iQtd = iQtd.replace(",",".");		
		
		$("#itensEntrada").load(
				"entradaItens.addItem.logic", {
					 "itemEntrada.item.id": iId,
					 "qtd": iQtd,
					 "vl": iValor,
					 "itemEntrada.validade": iValidade,
					 "itemEntrada.lote": iLote,
					 "valorTotal": iValorTotal
				}
		);

		document.getElementById("codItem").value = "";
		document.getElementById("itens").value = "";
		document.getElementById("iQtd").value = "0,00";
		document.getElementById("iValor").value = "0,00";
		document.getElementById("iValidade").value = "";
		document.getElementById("iLote").value = "";
		document.getElementById("iValorTotal").checked = false;
		document.cadastro.codItem.focus();
    }

    function remItem(i) {
        $("#itensEntrada").load("entradaItens.remItem.logic", {"index": i});
    }

    function validar(){
        if ((quantItemNota >0) && (document.getElementById("data").value!='')
            && (document.getElementById("numero").value!='')
            && (document.getElementById("notaEntrada.fornecedor.id").value!='')) {

            return true;
        } else {
            alert("Favor preencher os dados da nota de entrada no formulário.");
            return false;
        }
    }  
</script>
<div id="conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Entrada de itens</label> |</b></legend>
    <c:choose>
      <c:when test="${notaEntrada.id==null}">
        <h3>Preencha os dados do formulário para cadastrar uma nova nota de entrada de itens:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite os dados do formulário que deseja atualizar a nota de entrada de itens:</h3>
      </c:otherwise>
    </c:choose>
    <form name="cadastro" action="entradaItens.gravar.logic" method="POST" >
      <input type="hidden" name="notaEntrada.id" id="notaEntrada.id" value="${notaEntrada.id}">
      <input type="hidden" name="temItensNaNota" id="temItensNaNota" value="0">
      <fieldset><legend>Almoxarifado</legend>
        <table>
          <tr>
            <td>
            	<label for="almoxarifado">Nome:</label></td><td>
                  <c:choose>
                  <c:when test="${fn:length(almoxarifados) == 1}">
                  	${almoxarifados[0].nome} <input type="hidden" name="notaEntrada.almoxarifado.id" value="${almoxarifados[0].id}" />
                  </c:when>
                  <c:otherwise>
	              	<select name="notaEntrada.almoxarifado.id" id="almoxarifado" >
                  		<option value="0L">-- Selecione um almoxarifado --</option>
	                    <c:forEach var="almoxarifado" items="${almoxarifados}">
	                      <option value="${almoxarifado.id}"
	                              <c:if test="${almoxarifado.id == notaEntrada.almoxarifado.id}">
	                          selected="selected"
	                      </c:if> >${almoxarifado.nome}</option>
	                    </c:forEach>
                  	</select>
                  </c:otherwise>
                  </c:choose>
            </td>
            <td>
            <label for="tipoEntrada">Tipo de Entrada:</label></td><td>
              <select name="notaEntrada.tipoEntrada.id" id="tipoEntrada" >
                <c:forEach var="tipo" items="${tiposEntrada}">
                  <option value="${tipo.id}"
                          <c:if test="${tipo.id == notaEntrada.tipoEntrada.id}">
                      selected="selected"
                  </c:if> >${tipo.nome}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
        </table>
      </fieldset>
      <fieldset><legend>Dados da Nota de Entrada</legend>
        <table>
          <tr>
            <td>
              <label for="data">Data: </label>
            <input alt="date" id="data" name="notaEntrada.data" value="<fmt:formatDate value="${notaEntrada.data}" pattern="dd-MM-yyyy HH:mm"/>" size="10" /></td>
            <td>
	            <label for="numero">Número da nota fiscal: </label>
	            <input id="numero" name="notaEntrada.numero" value="${notaEntrada.numero}" size="10" maxlength="100" />
            </td>
          </tr>
          <tr>
	          <td>
	            <label for="observacao">Observação: </label>
	            <input id="observacao" name="notaEntrada.observacao" onblur="this.value=this.value.toUpperCase()"  value="${notaEntrada.observacao}" size="30" maxlength="200"/>
	          </td>
          </tr>
          <tr>
            <td colspan="2">
              <label for="notaEntrada.fornecedor.id">Fornecedor: </label><span id="fornecedor">
              <select name="notaEntrada.fornecedor.id" id="notaEntrada.fornecedor.id" >
                <c:forEach var="fornecedor" items="${fornecedores}">
                  <c:if test="${fornecedor.id == notaEntrada.fornecedor.id}">
                    <option value="${fornecedor.id}" selected="selected">${fornecedor.nome}</option>
                  </c:if>
                  <c:if test="${fornecedor.id != notaEntrada.fornecedor.id}">
                    <option value="${fornecedor.id}">${fornecedor.nome}</option>
                  </c:if>
                </c:forEach>
            </select></span>
            </td>
          </tr>
        </table>
      </fieldset>
      <fieldset><legend>Itens de Entrada</legend>
        <table>
          <tr>
           <td><label for="codItem">Cod.Item: </label><input alt="numeric" type="text" id="codItem" size="5" onblur="loadItemById(this.value, 'itens')"/></td>
            <td><label for="itens">Item: </label><input type="text" id="itens" title="Digite o nome do item que procura." size="20"/></td>
            <td><label for="iQtd">Quantidade: </label> <input alt="decimal" type="text" id="iQtd" size="5" value="0,00" /></td>
            <td><label for="iValor">Valor: </label> <input alt="decimal" type="text" id="iValor" size="5" value="0,00" /></td>
            <td><label for="iValorTotal">Valor total?</label> <input type="checkbox" id="iValorTotal" value="true" title="Utilize este valor caso não possua o valor unitário do item." /></td>
            <td><label for="iValidade">Validade: </label> <input alt="date" type="text" id="iValidade" value="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy HH:mm"/>" size="8" /></td>
            <td><label for="iLote">Lote: </label> <input type="text" id="iLote" size="6"/></td>
            <td>
              <input type="button" value="Adicionar" onclick="addItem()"/>              
            </td>
          </tr>
        </table>
        <fieldset>
          <legend>Itens</legend>
          <div id="itensEntrada">
            <jsp:include page="addItem.ok.jsp" flush="true" />
          </div>
        </fieldset>
      </fieldset>
      <p align="center"><input type="button" class="button" value="Salvar informações" onclick="forms[0].submit()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
    </form>
  </fieldset>
  <br />
</div>
<jsp:include page="/footer.jsp" flush="true" />