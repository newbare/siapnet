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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
    $(document).ready(function() {
        $("input:text").setMask();
        if (/msie/i.test(navigator.userAgent) && !/opera/i.test(navigator.userAgent)) {
            document.getElementById("msie").value = "IE";
        }
    });
</script>
</head>
<jsp:include page="/header.jsp" flush="true" />
<!--content principal-->
<div id="div_conteudo">
    <form method="post" action="login.migrar.logic">
        <fieldset style="width: 40%; margin-left: 30%"><legend>Digite seu e-mail, cpf e senha para fazer as alterações.</legend>
            <table align="center">
                <tr><td><label for="authUser.email">E-mail:</label></td><td  align="left"><input type="text" id="authUser.email" name="authUser.email" value="${authUser.email}" maxlength="100" size="25"/></td></tr>
                <tr><td><label for="authUser.cpf">CPF:</label></td><td  align="left"><input type="text" id="authUser.cpf" name="authUser.cpf" value="${authUser.cpf}" alt="cpf" maxlength="15" size="25"/></td></tr>
                <tr><td><label for="authUser.senha">Senha:</label></td><td align="left"><input type="password" id="authUser.senha" name="authUser.senha" maxlength="25" size="25"/></td></tr>
                <tr><td colspan="2" align="right"><input type="submit" class="button" value="Entrar"/></td></tr>
            </table>
        </fieldset>
        <br/>
        <a href="/almoxarifado/ajuda.info.logic">Ajuda</a> | <a href="/almoxarifado/ajuda.suporte.logic">Suporte Técnico</a>
    </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />