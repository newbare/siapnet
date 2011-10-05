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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="pt_BR" scope="application"/>
<jsp:include page="/header.jsp" flush="true" />
<script>
    var itemId = null;
    $(document).ready(function() {
        $("#itens").autocomplete("itemEstoque.loadItens.logic", {minChars:3,formatItem: function(row){ return row[1] +" - " + row[0]; }}).setOptions({max:50});
        $("#itens").result(
            function(event, data, formatted) {
                itemId=data[1];
                itemEstoqueItemId = document.getElementById("itemEstoque.item.id");
                itemEstoqueItemId.value = itemId;
            }
        );
    });
</script>
<script type="text/javascript" charset="utf-8" src="/almoxarifado/js/hints.js"></script>
<div id="conteudo">
    <fieldset>
    	<legend><b>| <label class="lbTituloLegend">Parâmetros de Item em Estoque</label> |</b></legend>
        <c:choose>
            <c:when test="${itemEstoque.id==null}">
                <h3>Preencha os dados do formulário dos parâmetros em item de estoque:</h3>
            </c:when>
            <c:otherwise>
                <h3>Edite os dados do formulário que deseja atualizar do item em estoque:</h3>
            </c:otherwise>
        </c:choose>
        <form method="post" action="itemEstoque.gravar.logic" accept-charset="utf-8">
            <input type="hidden" name="itemEstoque.id" value="${itemEstoque.id}" />
            <input type="hidden" name="itemEstoque.item.id" id="itemEstoque.item.id" value="${itemEstoque.item.id}" />
            <input type="hidden" name="estoque" id="estoque" value="<fmt:formatNumber value="${itemEstoque.estoque}" type="NUMBER"  maxFractionDigits="2" minFractionDigits="2" />" />
                <dl style="width:600px">
                <dt><label for="itemEstoque.almoxarifado.id">Almoxarifado:</label></dt>
                    <dd>
                        <select name="itemEstoque.almoxarifado.id" id="itemEstoque.almoxarifado.id">
                            <c:forEach var="almoxarifado" items="${almoxarifados}">
                                <c:choose>
                                    <c:when test="${almoxarifado.id == itemEstoque.almoxarifado.id}">
                                        <option value="${almoxarifado.id}" selected="selected">${almoxarifado.nome}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${almoxarifado.id}">${almoxarifado.nome}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <span class="hint" style="margin-top: -24px;">Selecione o almoxarifado que estoca o item que deseja parametrizar as informações de estoque.<span class="hint-pointer">&nbsp;</span></span>
                    </dd>
                </dt>
                </dl>
                <dl>
                <dt><label for="itens"> Item: </label></dt><dd><input type="text" id="itens" name="itens" size="50" maxlength="50" value="${itemEstoque.item.nome}" /><span class="hint">Digite o nome do item que deseja buscar e selecione o que deseja parametrizar as informações de estoque.<span class="hint-pointer">&nbsp;</span></span></dd>
                </dl>
                 <dl style="width: 350px;" >
                <dt><label >Estoque Atual:</label> </dt><dd><td><fmt:formatNumber value="${itemEstoque.estoque}" type="NUMBER"  maxFractionDigits="2" minFractionDigits="2" /></td></dd>
                <dt><label for="itemEstoque.estoqueMinimo">Estoque mínimo:</label></dt><dd><input type="text" id="itemEstoque.estoqueMinimo" name="itemEstoque.estoqueMinimo" value="${itemEstoque.estoqueMinimo}" size="20" maxlength="20" alt="numeric" /><span class="hint">Valor indicando a quantidade mínima deste item em Estoque. De forma que supra o período de consumo até que aquisição e entrega de item aconteça.<span class="hint-pointer">&nbsp;</span></span></dd>
                <dt><label for="itemEstoque.estoqueMaximo">Estoque máximo:</label></dt><dd><input type="text" id="itemEstoque.estoqueMaximo" name="itemEstoque.estoqueMaximo" value="${itemEstoque.estoqueMaximo}" size="20" maxlength="20" alt="numeric" /><span class="hint">Valor indicando a capacidade máxima de armazenamento deste item no estoque do Almoxarifado.<span class="hint-pointer">&nbsp;</span></span></dd>
                <dt><label for="itemEstoque.consumoMedio">Consumo médio:</label></dt><dd><input type="text" id="itemEstoque.consumoMedio" name="itemEstoque.consumoMedio" value="${itemEstoque.consumoMedio}" size="20" maxlength="20" alt="numeric" /><span class="hint">Informar o consumo em média deste item durante o período de consumo em dias.<span class="hint-pointer">&nbsp;</span></span></dd>
                </dl>
                <dl>
                <dt><label for="itemEstoque.periodoEmDiasConsumoMedio" >Período de consumo (dias):</label></dt><dd><input type="text" id="itemEstoque.periodoEmDiasConsumoMedio" name="itemEstoque.periodoEmDiasConsumoMedio" value="${itemEstoque.periodoEmDiasConsumoMedio}" size="20" maxlength="20" alt="numeric" /><span class="hint">Parâmetro para o calculo do consumo médio e seu valor deve ser informado pelo almoxarife com base no tempo de compra e recebimento.<span class="hint-pointer">&nbsp;</span></span></dd>

                <dt><label for="itemEstoque.localizacao">Localização:</label></dt><dd><input type="text"  onblur="this.value=this.value.toUpperCase()" id="itemEstoque.localizacao"  name="itemEstoque.localizacao" value="${itemEstoque.localizacao}" size="50" maxlength="50" /><span class="hint">Localização da estocagem do item no almoxarifado, de acordo com a organização do almoxarifado. Informando por exemplo: nome do Armazém, zona de estoque, estante, nível de prateleira, escaninho.<span class="hint-pointer">&nbsp;</span></span></dd>
            <dt></dt><dd>
            <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
            <dd>
            </dl>
        </form>

    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />