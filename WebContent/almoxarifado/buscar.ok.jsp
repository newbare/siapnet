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
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<fieldset><legend><b>| <label class="lbTituloLegend">Listagem de almoxarifados</label> |</b></legend>
    <display:table id="almoxarifado" class="grid" name="almoxarifados" pagesize="10" requestURI="almoxarifado.listAll.logic">
        <display:column title="id"><a href="almoxarifado.ver.logic?almoxarifado.id=${almoxarifado.id}">${almoxarifado.id}</a></display:column>
        <display:column title="nome" sortable="true">
            <a href="almoxarifado.ver.logic?almoxarifado.id=${almoxarifado.id}">${almoxarifado.nome}</a></display:column>
        <display:column property="responsavel" sortable="true"/>
        <display:column property="telefone"/>
        <display:column title="Ação">
            <a href="almoxarifado.get.logic?almoxarifado.id=${almoxarifado.id}"><img src="images/edit.png" alt="Editar almoxarifado" /></a>
            &nbsp;
            <a href="javascript:confirmDelete('almoxarifado.remove.logic?almoxarifado.id=${almoxarifado.id}')"><img src="images/trash.png" alt="Excluir almoxarifado" /></a>
        </display:column>
    </display:table>
</fieldset>