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
<script type="text/javascript">
    $(document).ready(function() {

        $("#garantia").datepicker({
            showOn: "button",
            buttonImage: "/almoxarifado/images/cal.gif",
            buttonImageOnly: true
        });
    });

    function verAssistenciaTecnica(fornecedorId){
        openMyModal('fornecedor.verModal.logic?fornecedor.id='+fornecedorId,505,501);
        return false;
    }

    function cadMarca(){
        openMyModal('marca.formModal.logic',150,501);
        return false;
    }

    function cadFornecedor(){
        abrirJanela('fornecedor.formAssistenciaTec.logic',500,651);
        return false;
    }
</script>
<div id="div_conteudo">
    <form method="post" action="patrimonio.enviar.logic" accept-charset="utf-8">
        <input type="hidden" name="patrimonio.itemEntrada.id" value="${patrimonio.itemEntrada.id}"/>
        <fieldset><legend><b>| <label class="lbTituloLegend">Bem Permanente Tombado</label> |</b></legend>
            <table>
                <tr><td><b><label>Nome: </label></b></td><td>${patrimonio.itemEntrada.item.nome}</td></tr>
                <tr><td><b><label>Aplicação: </label></b></td><td>${patrimonio.itemEntrada.item.aplicacao}</td></tr>
                <tr><td><b><label>Grupo: </label></b></td><td>${patrimonio.itemEntrada.item.grupo.nome}</td></tr>
            </table>
            <table>
                <tr>
                    <td>
                        <fieldset><legend>Patrimônio</legend>
                            <table>
                                <tr><td><label for="patrimonio.numero">Número Tombamento: </label></td>
                                    <td>
                                        <input type="text" id="patrimonio.numero" name="patrimonio.numero" alt="numeric"
                                               value="${patrimonio.numero}" size="5"/>
                                        <input type="hidden" name="patrimonio.itemEntrada.id" value="${patrimonio.itemEntrada.id}" />
                                        <input type="hidden" name="patrimonio.id" value="${patrimonio.id}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="modelo">Modelo: </label></td>
                                    <td><input type="text" id="modelo" name="patrimonio.modelo"
                                               value="${patrimonio.modelo}"/></td>
                                </tr>
                                <tr>
                                    <td><label for="marca">Marca: </label></td>
                                    <td >
                                        <span id="divMarca">
                                            <select name="patrimonio.marca.id" id="marca">
                                                <option value=""></option>
                                                <c:forEach var="marca" items="${marcas}">
                                                    <option value="${marca.id}"
                                                <c:if test="${marca.id==patrimonio.marca.id}">selected="selected"</c:if> >
                                                ${marca.nome}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </span>
                                    <a href="#" onclick="javascript:cadMarca();"><img src="images/edit.png" alt="Cadastrar nova marca" /></a>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="numeroSerie">Número de Série: </label></td>
                                <td>
                                    <input type="text" id="numeroSerie" name="patrimonio.numeroSerie"
                                           value="${patrimonio.numeroSerie}"/></td>
                            </tr>
                            <tr>
                                <td><label for="estado">Estado Depreciação: </label></td>
                                <td>
                                    <select name="patrimonio.estadoDepreciacao.id" id="estado">
                                        <c:forEach var="estado" items="${estadosDepre}">
                                            <option value="${estado.id}" <c:if test="${estado.id==patrimonio.estadoDepreciacao.id}">selected="selected"</c:if> >${estado.nome}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="observacao">Observação: </label></td>
                                <td>
                                    <input type="text" id="observacao" name="patrimonio.observacao"
                                           value="${patrimonio.observacao}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset><legend>Garantia</legend>
                        <table>
                            <tr>
                                <td><label for="garantia">Até:</label></td>
                                <td>
                                    <input type="text" id="garantia" name="patrimonio.garantia" alt="date" size="10"
                                           value="<fmt:formatDate value="${patrimonio.garantia}" pattern="dd-MM-yyyy"/>" />
                                </td>
                            </tr>
                            <tr><td><label for="garantia">Assistencias Técnicas:</label></td>
                                <td>
                                    <div class="scroll_checkboxes">
                                        <c:set var="i" value="0" />
                                        <c:forEach var="at" items="${assistenciasTecnicas}">
                                            <c:set var="checked" value="" />
                                            <c:forEach var="pat" items="${patrimonio.assistenciasTecnica}">
                                                <c:if test="${at.id == pat.id}">
                                                    <c:set var="checked" value="checked='checked'"/>
                                                </c:if>
                                            </c:forEach>
                                            <input type="checkbox" class="check" value="${at.id}" ${checked} name="assistenciasTecnicaArray[${i}]"> <a href="#" onclick="javascript:verAssistenciaTecnica(${at.id});" >${at.nome}</a> <br/>
                                            <c:set var="i" value="${i+1}"/>
                                        </c:forEach>
                                    </div>
                                    <a title="Cadastrar nova assistência técnica" href="#" onclick="javascript:cadFornecedor();"><img src="images/edit.png" alt="Cadastrar nova assistência técnica" /></a>
                                </td></tr>
                        </table>
                    </fieldset>
                    <fieldset><legend>Localização Atual do Bem</legend>
                        <table>
                        	<tr>
                        		<td style="text-align: right;" ><label for="departamento">Setor:</label></td>
                                <td>
                                	<c:if test="${patrimonio.departamento != null}">
                                    	${patrimonio.departamento.nome}
                                	</c:if>
                                	<c:if test="${patrimonio.departamento == null}">
                                    	SETOR DE PATRIMÔNIO
                                	</c:if>
                        		</td>
                        		<td>
                        			<label for="localizacao">Localizado em:</label>
                        			<input type="text" id="localizacao" name="patrimonio.localizacao" readonly="readonly" value="${patrimonio.localizacao}"/>
                    			</td>
                    		</tr>
		                    <tr>
		                    	<td><a href="patrimonio.preparaTransferencia.logic?patrimonio.id=${patrimonio.id}">Transferir para outro setor</a></td>
		                        <td><a href="patrimonio.movimentacaoBem.logic?patrimonio.id=${patrimonio.id}">Ver movimentação desse Bem</a></td>
		                    </tr>
                		</table>
            		</fieldset>
            	</td>
    </tr>
</table>
<p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
</fieldset>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />