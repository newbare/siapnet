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
<script type="text/javascript">
    function loadUnidades(id) {
    	$("#departamentos").load("usuario.loadDepartamentos.logic", {"unidade.id": 0});
        $("#almoxarifados").load("usuario.loadAlmoxarifados.logic", {"unidade.id": 0, "perfil.id": 0});
        $("#unidades").load("usuario.loadUnidades.logic", {"orgao.id": id});
    }

    function unidadeAction(id) {
    	$("#almoxarifados").load("usuario.loadAlmoxarifados.logic", {"unidade.id": 0, "perfil.id": 0});
        $("#departamentos").load("usuario.loadDepartamentos.logic", {"unidade.id": id});
    }

    function loadAlmoxarifados() {
        pId = document.getElementById("perfil.id").value;
        uId = document.getElementById("unidade.id").value;
        $("#almoxarifados").load("usuario.loadAlmoxarifados.logic", {"unidade.id": uId, "perfil.id": pId});
    }

    function addUsuarioDepartamento() {
        dId = document.getElementById("departamento.id").value;
        pId = document.getElementById("perfil.id").value;
        aId = document.getElementById("almoxarifado.id").value;
        $("#usuarioDepartamentos").load("usuario.addUsuarioDepartamento.logic", {"departamento.id": dId, "perfil.id": pId, "almoxarifado.id": aId});
        $("#orgaos").load("usuario.loadOrgaos.logic");
        $("#unidades").load("usuario.loadUnidades.logic", {"orgao.id": 0});
        $("#departamentos").load("usuario.loadDepartamentos.logic", {"unidade.id": 0});
        $("#almoxarifados").load("usuario.loadAlmoxarifados.logic", {"unidade.id": 0, "perfil.id": 0});
        
        windows.open("aqui");
    }


    function validaForm(){
        var cpfUser = document.getElementById("cpf");
        return validarCPF(cpfUser);
    }

    function remUsuarioDepartamento(dId) {
        $("#usuarioDepartamentos").load("usuario.remUsuarioDepartamento.logic", {"departamento.id": dId});
    }
</script>
<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Usuário</label> |</b></legend>
        <c:choose>
            <c:when test="${usuario.id==null}">
                <h3>Preencha os dados do formulário para cadastrar um novo usuário:</h3>
            </c:when>
            <c:otherwise>
                <h3>Edite os dados do formulário que deseja atualizar o cadastro do usuário:</h3>
            </c:otherwise>
        </c:choose>
        <form method="post" action="usuario.gravar.logic" onsubmit="validaForm();">
            <input type="hidden" name="usuario.id" value="${usuario.id}" />
            <input type="hidden" id="deptoID" name="deptoId" value="${deptoId}" />
            <table>
                <tr><td>Nome:</td><td><input type="text" name="usuario.nome" onblur="this.value=this.value.toUpperCase()"  value="${usuario.nome}" maxlength="50" size="30" /></td></tr>
                <tr><td>CPF:</td><td><input type="text" alt="cpf" id="cpf" name="usuario.cpf" value="${usuario.cpf}"  onblur="validarCPF(this)" size="15" maxlength="25" /></td></tr>
                <tr><td>Matrícula:</td><td><input type="text" name="usuario.numeroMatricula" value="${usuario.numeroMatricula}" size="10" maxlength="25" /></td></tr>
                <tr><td>Função:</td><td><input type="text" name="usuario.funcao"  onblur="this.value=this.value.toUpperCase()" value="${usuario.funcao}" maxlength="50" size="30"/></td></tr>
                <tr><td>E-mail:</td><td><input type="text" name="usuario.email" value="${usuario.email}" maxlength="100" size="30"/></td></tr>
                <tr><td>Senha:</td><td><input type="password" name="usuario.senha" maxlength="20" /></td></tr>
                <tr>
                    <td>Perfil: </td>
                    <td>
                        <div id="perfis">
                            <jsp:include page="loadPerfis.ok.jsp" flush="true" />
                        </div>
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${orgaos != null}">
                        <tr><td>Orgãos</td>
                            <td>
                                <div id="orgaos">
                                    <jsp:include page="loadOrgaos.ok.jsp" flush="true" />
                                </div>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr><td>Orgão:</td><td><input type="hidden" id="orgao.id" name="orgao.id" value="${orgao.id}"/><input type="text" readonly="readonly" value="${orgao.nome}" size="50" maxlength="50" /></td></tr>
                    </c:otherwise>
                </c:choose>
                <tr><td>Unidades</td>
                    <td>
                        <div id="unidades">
                            <jsp:include page="loadUnidades.ok.jsp" flush="true" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Departamento: </td>
                    <td>
                        <div id="departamentos">
                            <jsp:include page="loadDepartamentos.ok.jsp" flush="true" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Almoxarifado:</td>
                    <td>
                        <div id="almoxarifados">
                            <jsp:include page="loadAlmoxarifados.ok.jsp" flush="true" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><button type="button" title="Adicionar departamento" onclick="addUsuarioDepartamento()">Adicionar departamento</button></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="usuarioDepartamentos" style="width: 95%; text-align: center;">
                            <jsp:include page="addUsuarioDepartamento.ok.jsp" flush="true" />
                        </div>
                    </td>
                </tr>
            </table>
            <p align="center"><input type="submit" class="button" value="Salvar informações" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
        </form>
    </fieldset>
</div>                            
<jsp:include page="/footer.jsp" flush="true" />