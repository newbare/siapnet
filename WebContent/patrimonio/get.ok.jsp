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
<jsp:include page="/header.jsp" flush="true" />
<script>
    $(document).ready(function() {
        $("input:text").setMask();
    });

    function cadMarca(){
        openMyModal('marca.formModal.logic',150,501);
        return false;
    }
</script>
<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Tombamento de Bens Permantentes</label> |</b></legend>
        <form method="post" action="patrimonio.gravar.logic" accept-charset="utf-8">
            <fieldset><legend>Item Permantente</legend>
                <input type="hidden" name="itemEntrada.id" value="${itemEntrada.id}"/>
                <table>
                    <tr><td colspan="2"><b><label>Nome: </label></b></td><td>${itemEntrada.item.nome}</td></tr>
                    <tr><td colspan="2"><b><label>Aplicação: </label></b></td><td>${itemEntrada.item.aplicacao}</td></tr>
                    <tr><td colspan="2"><b><label>Tipo de medida: </label></b></td><td>${itemEntrada.item.tipoMedidaEntrada.nome}</td></tr>
                    <tr><td colspan="2"><b><label>Grupo: </label></b></td><td>${itemEntrada.item.grupo.nome}</td></tr>
                    <tr><td colspan="3">
                            <fieldset><legend>Nota de Entrada</legend>
                                <table>
                                    <tr>
                                        <td><b><label>Número da Nota: </label></b></td><td>${itemEntrada.notaEntrada.numero}</td>
                                        <td><b><label>Data da Nota: </label></b></td><td>${itemEntrada.notaEntrada.data}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><b><label>Fornecedor: </label></b></td><td>${itemEntrada.notaEntrada.fornecedor.nome}</td>
                                    </tr>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <table>
                <tr>
                    <td>
                        <fieldset><legend>Patrimônio</legend>
                            <table>
                                <c:forEach var="i" begin="1" end="${itemEntrada.quantidade}" >
                                    <tr><td><label for="patrimonios[${i-1}].numero">Número Tombamento: </label>
                                            <input type="text" id="patrimonios[${i-1}].numero" name="patrimonios[${i-1}].numero"
                                                   value="${patrimonios[i-1].numero}" size="5" maxlength="6" alt="numeric" />
                                            <input type="hidden" name="patrimonios[${i-1}].id" value="${patrimonios[i-1].id}" />
                                            <input type="hidden" name="patrimonios[${i-1}].item.id" value="${item.id}" />
                                        </td>
                                        <td><label for="bem">Número série:</label>
                                        <td><input type="text" name="patrimonios[${i-1}].numeroSerie" id="numSerie"
                                                   value="${patrimonios[i-1].numeroSerie}" size="10" maxlength="25" />
                                        </td>
                  <td><label for="marca">Marca:</label> <span>
                                                <td><select name="patrimonios[${i-1}].marca.id" id="marca" >
                                                        <option value="">Selecione</option>
                                                        <c:forEach var="marca" items="${marcas}" >
                                                            <option value="${marca.id}"
                                                        <c:if test="${marca.id == patrimonios[i-1].marca.id}">selected="selected"</c:if> >
                                                        ${marca.nome}
                                                        </option>
                                                    </c:forEach>
                                                </select><a href="#" onclick="javascript:cadMarca();"><img src="images/edit.png" alt="Cadastrar nova marca" /></a> </span>
                                    </td>
                                    <td><label for="modelo">Modelo:</label>
                                    <td><input type="text" name="patrimonios[${i-1}].modelo" id="modelo"
                                               value="${patrimonios[i-1].modelo}" size="10" maxlength="25" />
                                    </td>
                                    <td><label for="estadoD">Estado de Depreciação:</label>
                                    <td><select name="patrimonios[${i-1}].estadoDepreciacao.id" id="estadoD" >
                                            <option value="">Selecione</option>
                                            <c:forEach var="estadoD" items="${estadosDepre}" >
                                                <option value="${estadoD.id}"
                                            <c:if test="${estadoD.id == patrimonios[i-1].estadoDepreciacao.id}">selected="selected"</c:if> >
                                            ${estadoD.nome}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </fieldset>
            </td>
        </tr>
        <tr><td colspan="2" align="center"><input type="submit" value="Tombar" class="button"/></td></tr>
    </table>
</form>
</fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />