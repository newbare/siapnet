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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="pt_BR" scope="application"/>
<jsp:include page="/header.jsp" flush="true" />
<script type="text/javascript">
    $(document).ready(function() {
    	$("#itens").autocomplete({
            source: "item.loadItens.logic?almoxarifado.id=${requisicao.almoxarifado.id}&",
            minLength: 3,
            select: function(event, ui) {
           		$('#codItem').val(ui.item.id);
            	$('#itens').val(ui.item.label);
            }
        });
        document.getElementById("codItem").focus();
    });
</script>
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Requerimento de material - passo 2</label> |</b></legend>
        <form method="post" action="requisicao.gravar.logic"><input type="hidden" name="requisicao.id" value="${requisicao.id}" />
            <h3>Escolha os materiais, digitando o nome do item e selecionando-o do catálogo existente. E informe a quantidade pretendida.</h3>
            <label>Almoxarifado: </label> ${requisicao.almoxarifado.nome}<br/><br/>
            <label>Tipo de saída: </label>
            <select name="requisicao.tipoSaida.id">
            	<c:forEach var="tipoSaida" items="${tiposSaida}">
            		<c:choose>
            		    <c:when test="${requisicao.tipoSaida == null && tipoSaida.id == 1}">
            				<option value="${tipoSaida.id}" selected="selected">${tipoSaida.nome}</option>
            			</c:when>
            			<c:when test="${requisicao.tipoSaida.id == tipoSaida.id}">
            				<option value="${tipoSaida.id}" selected="selected">${tipoSaida.nome}</option>
            			</c:when>
            			<c:otherwise>
            				<option value="${tipoSaida.id}">${tipoSaida.nome}</option>
            			</c:otherwise>
            		</c:choose>
            	</c:forEach>
            </select><br>            
            <table class="grid">
                <thead>
                    <tr>
                        <th>Cód. item</th><th>Nome do item</th><th>Quantidade</th><th>Adicionar</th>
                    </tr>
                </thead>
                <tbody>
                    <tr style="height: 50px;">
                        <td><input alt="numeric" type="text" id="codItem" size="6" onblur="loadItemById(this.value, 'itens')"/></td>
                        <td><input type="text" id="itens" name="itens" size="50"/></td>
                        <td><input type="text" id="qtdRequisitada" alt="decimal" name="qtdRequisitada" maxlength="5" size="5"/></td>
                        <td><input type="button" class="button" value="Adicionar item" onclick="addItem()"/></td>
                    </tr>
                </tbody>
            </table>
            <table width="50%">
                <tr>
                    <td>
                        <fieldset>
                            <legend>Itens</legend>
                            <div id="itensRequisicao"></div>
                        </fieldset>
                    </td>
                </tr>
                <tr>
                    <td align="center"><input type="submit" class="button" value="Requerer" /></td>
                </tr>
            </table>
        </form>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />