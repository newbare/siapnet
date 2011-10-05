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
    $(document).ready(function() {
        $("#servidor").load("patrimonio.loadServidor.logic", {minChars:3,formatItem: function(row){ return row[1] +" - " + row[0]; }});
        $("#bem").result(
        function(event, data, formatted) {
            servId=data[1];
            document.getElementById("servidorId").value = servId;
        }
    );
        $("input:text").setMask();
    });


    var servId = null;


    function addTombo() {
        tombo = document.getElementById("tombo").value;

        $("#tombos").load("patrimonio.addTombo.logic", {"patrimonio.numero": tombo});
    }

    function remTombo(pId) {
        $("#tombos").load("patrimonio.remTombo.logic", {"patrimonio.id": pId});
    }
</script>

<div id="conteudo">
    <form action="/almoxarifado/patrimonio.autorizarSaida.logic" method="post">
        <fieldset><legend><label>Transferencia de bens patrimoniais</label></legend>

            <table>
                <tr><td><label for=autorizacaoSaida.setorResponsavel.nome">Setor Responsável pela Autorização : </label></td><td>
                        <input type="hidden" name="autorizacaoSaida.setorResponsavel.id" value="${autorizacaoSaida.setorResponsavel.id}" />
                        <input name="autorizacaoSaida.setorResponsavel.nome" id="autorizacaoSaida.setorResponsavel.nome" value="${autorizacaoSaida.setorResponsavel.nome}" size="50" readonly="readonly" />
                </td></tr>
                <tr><td><label for=serv">Funcionário autorizado à sair com os Bens : </label></td><td>
                        <select name="autorizacaoSaida.servidorAutorizado.id" id="serv" >
                            <option value=""></option>
                            <c:forEach var="servidor" items="${servidores}" >
                                <option value="${servidor.id}">${servidor.numeroMatricula} - ${servidor.nome}</option>
                            </c:forEach>
                        </select>
                        <%-- Matrícula: <input type="text" size="4" maxlength="6" alt="numeric" id="servidor" >
            <input type="hidden" name="autorizacaoSaida.servidorAutorizado.id" id="servidorId" />
                        <input name="autorizacaoSaida.servidorAutorizado.nome" id="autorizacaoSaida.servidorAutorizado.nome" value="${autorizacaoSaida.setorResponsavel.nome}" /> --%>
                </td></tr>
                <tr><td><label for=autorizacaoSaida.movitoSaida">Motivo da Saída : </label></td><td>
                        <input type="text" size="30" maxlength="80" name="autorizacaoSaida.motivoSaida" value="${autorizacaoSaida.motivoSaida}" >
                </td></tr>
            </table>
        </fieldset>
        <fieldset><legend><label>Bens</label></legend>
            <table><tr><td><label>Número Tombamento:</label></td><td><input type="text" name="tombo" id="tombo" size="5" maxlength="6" alt="numeric" /></td><td><input type="button" class="button" value="Adiciona Tombo" onclick="addTombo()" /></td></tr></table>

            <fieldset>
                <div id="tombos"></div>
            </fieldset>
        </fieldset>
        <p align="center"><input type="submit" class="button" value="Emitir"></'p>
    </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
