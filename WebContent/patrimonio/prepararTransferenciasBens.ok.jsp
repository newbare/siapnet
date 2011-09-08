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
    var servId = null;
    
    $(document).ready(function() {
        $("#servidor").load("patrimonio.loadServidor.logic", {minChars:3,ormatItem: function(row){ return row[1] +" - " + row[0]; }});
        $("#bem").result(
        function(event, data, formatted) {
            servId=data[1];
            document.getElementById("servidorId").value = servId;
        }
    );
        $("input:text").setMask();
    });

    function addTombo() {
        tombo = document.getElementById("tombo").value;

        $("#tombos").load("patrimonio.addTomboTransferencia.logic", {"patrimonio.numero": tombo});
    }

    function remTombo(pId) {
        $("#tombos").load("patrimonio.remTomboTransferencia.logic", {"patrimonio.id": pId});
    }
</script>
<div id="div_conteudo">
    <form action="/almoxarifado/patrimonio.autorizarSaida.logic" method="post">
        <fieldset><legend>Movimentação - Transferência de Bens</legend>
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
                    </td></tr>
                <tr><td><label for=autorizacaoSaida.movitoSaida">Motivo da Saída : </label></td><td>
                        <input type="text" size="30" maxlength="80" name="autorizacaoSaida.motivoSaida" value="${autorizacaoSaida.motivoSaida}" >
                    </td></tr>
            </table>
        </fieldset>
        <fieldset><legend>Bens</legend>
            <table><tr><td>Número Tombamento:</td><td><input type="text" name="tombo" id="tombo" size="5" maxlength="6" alt="numeric" /></td><td><input type="button" value="Adiciona Tombo" onclick="addTombo()" /></td></tr></table>
            <fieldset>
                <div id="tombos"></div>
            </fieldset>
        </fieldset>
        <br/><input type="submit" value="Emitir">
    </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
