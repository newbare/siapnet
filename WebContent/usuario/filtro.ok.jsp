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
    var unidade = null;
    var depto = null;
    var perfil = null;

    $(document).ready(function() {
        $("input:text").setMask();
        buscarPaginacao();

    });

    function buscarPaginacao(){
        var url= window.location.href ;
        url = url.replace("usuario.filtro.logic","usuario.buscar.logic");
        var k=url.search('-p=');
        if( k!=-1)
            $("#listUsuarios").load(url);
    }


    function buscar() {
    	$("#indBuscaUsuario").html('<img src="<c:url value="/images/indicator.gif"/>"/>');
        var nome = document.getElementById("usuario.nome").value;
        var cpf = document.getElementById("usuario.cpf").value;
        var matricula = document.getElementById("usuario.numeroMatricula").value;
        var resp = document.getElementById("usuario.funcao").value;
        var email = document.getElementById("usuario.email").value;
        var desativado = document.getElementById("desativado").checked;

        $("#listUsuarios").load("usuario.buscar.logic", {
            "usuario.nome" : nome ,
            "usuario.cpf" : cpf ,
            "usuario.numeroMatricula": matricula ,
            "usuario.funcao" :resp,
            "usuario.email":email,            
            "desativado": desativado
        });
    }

    function loadDepartamentos(id) {
        loadAlmoxarifadosPorPerfil(3, id);
        $("#departamentos").load("usuario.loadDepartamentos.logic", {"unidade.id": id});
        unidade = id;
    }

    function loadAlmoxarifadosPorPerfil(id, unidade) {
        $("#almoxarifados").load("usuario.loadAlmoxarifados.logic", {"unidade.id": unidade, "perfil.id": id});
        perfil = id;
        depto = document.getElementById("usuario.departamento.id").value;
    }

    
</script>
<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Busca de Usuário</label> |</b></legend>
        <h3>Preencha os dados para os critérios da busca de usuários:</h3>
        <form method="post" name="filtroForm">
            <input type="hidden" name="usuario.id" value="${usuario.id}" />
            <table>
                <tr><td>Nome:</td><td><input type="text" id="usuario.nome" name="usuario.nome" value="${usuario.nome}" maxlength="50" /></td></tr></label>
                <tr><td>CPF:</td><td><input type="text" alt="cpf" id="usuario.cpf" name="usuario.cpf" value="${usuario.cpf}" maxlength="16" /></td></tr>
                <tr><td>Matrícula:</td><td><input type="text" alt="numeric" id="usuario.numeroMatricula" name="usuario.numeroMatricula" value="${usuario.numeroMatricula}" maxlength="25" /></td></tr>
                <tr><td>Função:</td><td><input type="text" id="usuario.funcao" name="usuario.funcao" value="${usuario.funcao}" maxlength="50" /></td></tr>
                <tr><td>E-mail:</td><td><input type="text" id="usuario.email" name="usuario.email" value="${usuario.email}" maxlength="100" /></td></tr>
                <tr><td>Desativado:</td><td><input type="checkbox" id="desativado" name="desativado"/></td></tr>
            </table>
            <p align="center"><input type="button" onclick="buscar()" class="button" value="Procurar" /></p>
        </form>
        <div id="listUsuarios"></div>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />