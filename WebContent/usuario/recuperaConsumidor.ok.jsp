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
    function loadDepartamentos(id) {
        loadAlmoxarifadosPorPerfil(3, id);
        $("#departamentos").load("usuario.loadDepartamentos.logic", {"unidade.id": id});
        $("#perfis").load("usuario.loadPerfis.logic");
    }

    function loadAlmoxarifadosPorPerfil(id, unidade) {
        $("#almoxarifados").load("usuario.loadAlmoxarifados.logic", {"unidade.id": unidade, "perfil.id": id});
    }


    function setaDepto(dp){
        document.getElementById("deptoID").value = dp.value;
    }

    function validaForm(){
        var cpfUser = document.getElementById("cpf");
        return validarCPF(cpfUser);
    }
</script>
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Consumidor</label> |</b></legend>
        <c:choose>
            <c:when test="${consumidor.id==null}">
                <h3>Preencha os dados do formulário para cadastrar um novo usuário:</h3>
            </c:when>
            <c:otherwise>
                <h3>Edite os dados do formulário que deseja atualizar o cadastro do usuário:</h3>
            </c:otherwise>
        </c:choose>
        <form method="post" action="usuario.gravarConsumidor.logic" onsubmit="validaForm();">
            <input type="hidden" name="consumidor.id" value="${consumidor.id}" />
            <input type="hidden" name="consumidor.perfil.id" value="13" /><%--Perfil consumidor setor = 13 --%>
            <input type="hidden" id="deptoID" name="deptoId" value="${deptoId}" />
            <table>
                <tr><td><label for="consumidor.nome">Nome:</label></td><td><input type="text" id="consumidor.nome"  onblur="this.value=this.value.toUpperCase()"  name="consumidor.nome" value="${consumidor.nome}" maxlength="50" /></td></tr>
                <tr><td><label for="cpf">CPF:</label></td><td><input type="text" alt="cpf" id="cpf" name="consumidor.cpf" value="${consumidor.cpf}" size="15" maxlength="25" /></td></tr>
                <tr><td><label for="numeroMatricula">Matrícula:</label></td><td><input type="text" alt="numeric" id="numeroMatricula" name="consumidor.numeroMatricula" value="${consumidor.numeroMatricula}" maxlength="25" /></td></tr>
                <tr><td><label for="consumidor.funcao">Função:</label></td><td><input type="text" id="consumidor.funcao"  onblur="this.value=this.value.toUpperCase()" name="consumidor.funcao" value="${consumidor.funcao}" maxlength="50" /></td></tr>
                <tr><td><label for="consumidor.email">E-mail:</label></td><td><input type="text" id="consumidor.email" name="consumidor.email" value="${consumidor.email}" maxlength="100" /></td></tr>
                <tr><td><label for="consumidor.senha">Senha:</label></td><td><input type="password" id="consumidor.senha"  name="consumidor.senha" maxlength="20" /></td></tr>
                <c:choose>
                    <c:when test="${unidades != null}">
                        <tr><td>Unidades</td>
                            <td>
                                <select id="unidade.id" name="unidade.id" size="5" onchange="loadDepartamentos(this.value)"  onblur="loadDepartamentos(this.value)" style="width: 300px;">
                                    <c:forEach var="un" items="${unidades}">
                                        <c:choose>
                                            <c:when test="${consumidor.usuarioDepartamentos[0].departamento.unidade != null && consumidor.usuarioDepartamentos[0].departamento.unidade.id == un.id}">
                                                <option value="${un.id}" selected="selected">${un.nome}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${un.id}">${un.nome}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td></tr>
                    </c:when>
                    <c:otherwise>
                        <tr><td><label>Unidade:</label></td><td><input type="hidden" id="unidade.id" name="unidade.id" value="${unidade.id}"/>${unidade.nome}</td></tr>
                        <script>loadDepartamentos(${unidade.id});</script>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td><label for="departamento.id">Departamento:</label> </td>
                    <td>
                        <div id="departamentos">
                            <jsp:include page="loadDepartamentos.ok.jsp" flush="true" />
                        </div>
                    </td>
                </tr>
            </table>
            <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
        </form>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />