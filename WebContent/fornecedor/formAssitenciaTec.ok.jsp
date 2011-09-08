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
<script>
    function alternaPessoa(tPessoa) {
        var pessoa = document.getElementById("pessoa");
        pessoa.innerHTML = "<input type='text'  name='fornecedor.cpfCnpj' id='cpfCnpj' alt='"+ tPessoa +"' enabled='enabled' value='${fornecedor.cpfCnpj}' size='20' />";
        $("input:text").setMask();
    }


</script>
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Fornecedor</label> |</b></legend>
        <form method="post" action="fornecedor.gravar.logic"><input
                type="hidden" name="fornecedor.id" value="${fornecedor.id}" />
            <input
                type="hidden" name="fornecedor.tipofornecedor.id" value="${fornecedor.tipofornecedor.id}" />
            <c:choose>
                <c:when test="${fornecedor.id==null}">
                    <h3>Preencha os dados do formulário para cadastrar um nova assistência técnica:</h3>
                </c:when>
                <c:otherwise>
                    <h3>Edite os dados do formulário que deseja atualizar a assistência técnica:</h3>
                </c:otherwise>
            </c:choose>
            <fieldset>
                <legend>Dados da assitência tecnica</legend>
                <table>
                    <tr>
                        <td><label for="fornecedor.nome">Nome:</label></td>
                        <td><input type="text" name="fornecedor.nome" id="fornecedor" onblur="this.value=this.value.toUpperCase()"  value="${fornecedor.nome}" size="50" maxlength="100"/></td>
                    </tr>
                    <tr><td></td>
                        <td>
                    <input onclick="alternaPessoa('cnpj')" type="radio" class="radio" name="fornecedor.pessoaJuridica" id="fornecedor.pessoaJuridica"  value="true" <c:if test="${fornecedor.pessoaJuridica==true}">checked</c:if> />
                    <label for="fornecedor.pessoaJuridica">Pessoa Jurídica</label>
                    <input onclick="alternaPessoa('cpf')" type="radio" class="radio" name="fornecedor.pessoaJuridica"  id="fornecedor.pessoaFisica" value="false" <c:if test="${fornecedor.pessoaJuridica==false}">checked</c:if> />
                    <label for="fornecedor.pessoaFisica">Pessoa Física</label>
                    </td>
                    </tr>
                    <tr>
                        <td><label for="cpfCnpj">CPF/CNPJ:</label></td>
                        <td>
                            <div id="pessoa">
                                <input type="text"  name="fornecedor.cpfCnpj" id="cpfCnpj" alt="cnpj" readonly="readonly" value="${fornecedor.cpfCnpj}" size="20" maxlength="20"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="inscricaoEstadual">Inscrição Estadual:</label></td>
                        <td><input type="text" name="fornecedor.inscricaoEstadual" id="inscricaoEstadual"
                                   value="${fornecedor.inscricaoEstadual}" size="20" maxlength="18" alt="ie"/></td>
                    </tr>
                    <tr>
                        <td><label for="fornecedor.razaoSocial">Razão Social:</label></td>
                        <td><input type="text" name="fornecedor.razaoSocial" id="fornecedor.razaoSocial"
                                   value="${fornecedor.razaoSocial}" size="50" maxlength="100" /></td>
                    </tr>
                    <tr>
                        <td><label for="telefone">Telefone:</label></td>
                        <td><input type="text" name="fornecedor.telefone" id="telefone"  alt="phone"
                                   value="${fornecedor.telefone}" size="15" maxlength="14"/></td>
                    </tr>
                    <tr>
                        <td><label for="fornecedor.observacao"  style="font-size: 10pt;" >Observação:</label></td>
                        <td><input type="text" name="fornecedor.observacao" id="fornecedor.observacao" value="${fornecedor.observacao}" size="50" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="fornecedor.grupos"  style="font-size: 10pt; vertical-align:top" >Grupos :</label></td>
                        <td>
                            <select name="gruposArray" id="fornecedor.grupos" size="6" style="width: 250px;" multiple="multiple">
                                <c:forEach var="grupo" items="${grupos}">
                                    <c:set var="selected" value="" />
                                    <c:forEach var="group" items="${fornecedor.grupos}">
                                        <c:if test="${grupo.id == group.id}">
                                            <c:set var="selected" value="selected='selected'"/>
                                        </c:if>
                                    </c:forEach>
                                    <option value="${grupo.id}" ${selected}>${grupo.nome}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <fieldset><legend>Contato</legend>
                                <table>
                                    <tr>
                                        <td class="label"><label for="fornecedor.contato" style="font-size: 10pt;">Contato:</label></td>
                                        <td>
                                            <input type="text" name="fornecedor.contato"  id="fornecedor.contato"  onblur="this.value=this.value.toUpperCase()"  value="${fornecedor.contato}" style="font-size: 10pt;"  size="50" maxlength="50"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label"><label for="foneContato" style="font-size: 10pt;">Telefone:</label></td>
                                        <td>
                                            <input type="text" name="fornecedor.telefoneContato" id="foneContato"  alt="phone" value="${fornecedor.telefoneContato}" style="font-size: 10pt;" size="15" maxlength="14" />
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <fieldset><legend>Endereço</legend>
                                <table>
                                    <tr>
                                        <td class="label"><label for="endereco.logradouro" style="font-size: 10pt;">Logradouro:</label></td>
                                        <td><input type="text" name="endereco.logradouro" id="endereco.logradouro" value="${fornecedor.endereco.logradouro}"  onblur="this.value=this.value.toUpperCase()"  size="50" maxlength="150" /></td>
                                    </tr>
                                    <tr>
                                        <td class="label"><label for="endereco.numero" style="font-size: 10pt;">Número:</label></td>
                                        <td>
                                            <input type="text" name="endereco.numero" id="endereco.numero" value="${fornecedor.endereco.numero}"  onblur="this.value=this.value.toUpperCase()"  size="4" maxlength="5" />
                                            <label for="endereco.bairro" style="font-size: 10pt;">Bairro:</label>
                                            <input type="text" name="endereco.bairro" id="endereco.bairro"  value="${fornecedor.endereco.bairro}"  onblur="this.value=this.value.toUpperCase()"  size="38" maxlength="50" /></td>
                                    </tr>
                                    <tr>
                                        <td class="label"><label for="endereco.complemento" style="font-size: 10pt;">Complemento:</label></td>
                                        <td>
                                            <input type="text" name="endereco.complemento" id="endereco.complemento" value="${fornecedor.endereco.complemento}"  onblur="this.value=this.value.toUpperCase()"  size="50" maxlength="150" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label"><label for="cep" style="font-size: 10pt;">CEP:</label></td>
                                        <td><input type="text" name="endereco.cep" id="cep" alt="cep" value="${fornecedor.endereco.cep}" size="10" maxlength="10" />
                                            <label for="endereco.uf" style="font-size: 10pt;">UF:</label>
                                            <select
                                                name="uf.id" id="endereco.uf" onchange="loadMunicipios(this.value)"
                                                style="width: 50px;">
                                                <c:forEach var="uf" items="${ufs}">
                                                    <c:choose>
                                                        <c:when test="${uf.id == fornecedor.endereco.municipio.uf.id}">
                                                            <option value="${uf.id}" selected="selected">${uf.sigla}</option>
                                                        </c:when>
                                                        <c:when test="${uf.id == 27 && fornecedor.endereco.municipio == null}">
                                                            <option value="${uf.id}" selected="selected">${uf.sigla}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${uf.id}">${uf.sigla}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                            <span id="cidades">
                                                <label for="endereco.municipio" style="font-size: 10pt;">Município:</label>
                                                <select id="endereco.municipio" name="endereco.municipio.id">
                                                    <c:forEach var="municipio" items="${municipios}">
                                                        <c:choose>
                                                            <c:when test="${municipio.id == fornecedor.endereco.municipio.id}">
                                                                <option value="${municipio.id}" selected="selected">${municipio.nome}</option></c:when>
                                                            <c:otherwise><option value="${municipio.id}">${municipio.nome}</option></c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </span>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <p align="center"><input type="button" class="button" value="Salvar informações" onclick="forms[0].submit()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </form>
    </fieldset>
</div>