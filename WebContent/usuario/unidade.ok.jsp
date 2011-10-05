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
<!--content principal-->
<div id="conteudo">
    <br />
    <form method="post" action="usuario.cadastrarUnidade.logic" >
        <input type="hidden" id="msie" name="msie" value="" />
        <fieldset style="margin: 10px; auto" ><legend>Ativar Orgão no Sistema</legend>
            <table >
                <tr>
                    <td><label for="orgao.id">Orgão: </label></td>
                    <td>
                        <select name="orgao.id" style="width:350px;">
                            <c:forEach var="orgao" items="${orgaos}">
                                <option value="${orgao.id}">${orgao.sigla} - ${orgao.nome}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="unidade">Unidade: </label></td>
                    <td>
                        <input type="text" name="unidade" id="unidade" value="${unidade.id}"/>
                    </td>
                </tr>
                <tr><td colspan="2" align="right"><input type="submit" class="button" value="Salvar"/></td></tr>
            </table>
        </fieldset>
        <br/>

    </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />