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
    function concatena(){
        var nome = document.getElementById("nome").value;
        var nomeModif = document.getElementById("nomeModificador").value;
        var nomeCarac = document.getElementById("caractFisicas").value;
        var nomeIdAux = document.getElementById("identificAux").value;
        var nomeRef = document.getElementById("refComercial").value;
        var nomeAplic = document.getElementById("aplicacao").value;
        var nomeDesc = nome + ', ' +nomeModif + ', ' +nomeCarac + ', ' +nomeIdAux + ', ' +nomeRef + ', ' +nomeAplic + '.';
        document.getElementById("nomeDescritivo").value = nomeDesc;
</script>

<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de item para o catálogo</label> |</b></legend>
        <c:choose>
            <c:when test="${departamento.id==null}">
                <h3>Preencha os dados do formulário para cadastrar um novo item para o catálogo:</h3>
            </c:when>
            <c:otherwise>
                <h3>Edite os dados do formulário que deseja atualizar o cadastro de item para o catálogo:</h3>
            </c:otherwise>
        </c:choose>
        <form method="post" action="item.gravar.logic" accept-charset="utf-8">
            <input type="hidden" name="item.id" value="${item.id}"/>
            <table>
                <tr><td colspan="2">
                            <dl>
                                <dt><label for="nome">Nome:</label></dt><dd><input type="text" id="nome" onblur="this.value=this.value.toUpperCase()" name="item.nome" value="${item.nome}" onchange="concatena()" size="100" maxlength="100"/></dd>
                                <dt><label for="aplicacao">Aplicação:</label></dt><dd><input type="text" id="aplicacao"  onblur="this.value=this.value.toUpperCase()"  name="item.aplicacao" value="${item.aplicacao}" size="50" maxlength="50"/></dd>
                            </dl>
                    </td>
                </tr>
                <tr>
                    <td><label for="nomeDescritivo">Nome Descritivo:</label></td><td><textarea id="nomeDescritivo"  onblur="this.value=this.value.toUpperCase()" name="item.nomeDescritivo" rows="3" cols="50 ">${item.nomeDescritivo}</textarea></td></tr>
                <tr>
                    <td><label for="tipoMedidaEntrada">Tipo de medida:</label></td>
                    <td>
                        <select name="item.tipoMedidaEntrada.id" id="tipoMedidaEntrada">
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
                        <input type="checkbox" name="item.materialPermanente" id="bem" value="true" checked="checked"/>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" class="check" name="item.materialPermanente" id="bem" value="true"/>
                    </c:otherwise>
                </c:choose>
                </td>
                </tr>
            </table>
            <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
        </form>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />