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
<jsp:include page="/header.jsp" flush="true"/>
<script>
    $(document).ready(function() {
        $("input:text").setMask();
        buscarPaginacao();
    });

    function buscarPaginacao(){
        var url= window.location.href ;
        url = url.replace("almoxarifado.filtro.logic","almoxarifado.buscar.logic");
        var k=url.search('-p=');
        if( k!=-1)
            $("#listaAlmoxarifados").load(url);
    }

    function buscar() {
        var nome = document.getElementById("nome").value;
        var unidade = document.getElementById("unidade.id").value;
        var resp = document.getElementById("responsavel").value;
        var phone = document.getElementById("telefone").value;
        $("#listaAlmoxarifados").load("almoxarifado.buscar.logic", {
            "almoxarifado.nome" : nome ,
            "unidade.id": unidade ,
            "almoxarifado.responsavel" :resp ,
            "almoxarifado.telefone":phone});
    }
</script>
<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Busca de de Almoxarifado</label> |</b></legend>
        <h3>Preencha os critérios de busca para procurar os almoxarifados desejados:</h3>
        <form method="post" action="almoxarifado.gravar.logic">
            <input type="hidden" name="almoxarifado.id" value="${almoxarifado.id}"/>
            <fieldset>
                <legend>Dados do Almoxarifado</legend>
                <table>
                    <c:choose>
                        <c:when test="${unidades != null}">
                            <tr><td><label>Unidades</label></td><td><select name="unidade.id"  id="unidade.id">
                                        <c:forEach var="un" items="${unidades}">
                                            <c:choose>
                                                <c:when test="${almoxarifado.unidade != null && almoxarifado.unidade.id == un.id}">
                                                    <option value="${un.id}" selected="selected">${un.nome}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${un.id}">${un.nome}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select></td></tr>
                        </c:when>
                        <c:otherwise>
                            <tr><td><label>Unidade:</label></td><td><input type="hidden" name="almoxarifado.unidade.id" value="${unidade.id}"/><input type="text" readonly="readonly" value="${unidade.nome}" size="50" maxlength="50" /></td></tr>
                        </c:otherwise>
                    </c:choose>
                    <tr><td><label for="nome">Nome:</label></td><td><input type="text" id="nome" name="almoxarifado.nome" value="${almoxarifado.nome}" size="50" maxlength="50" /></td></tr>
                    <tr><td><label for="responsavel">Responsável:</label></td><td><input type="text" id="responsavel" name="almoxarifado.responsavel" value="${almoxarifado.responsavel}" size="50" maxlength="50" /></td></tr>
                    <tr><td><label for="telefone">Telefone:</label></td><td><input type="text" id="telefone" name="almoxarifado.telefone" alt="phone" value="${almoxarifado.telefone}" size="15" maxlength="14" /></td></tr>
                </table>
            </fieldset>
            <p align="center"><input type="button" class="button" value="Procurar" onclick="buscar()" /></p>
    </fieldset>
</form>
</fieldset>
<div id="listaAlmoxarifados"></div>
</div>
<jsp:include page="/footer.jsp" flush="true" />