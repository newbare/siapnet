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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<display:table id="item" class="grid" name="itens" pagesize="100" requestURI="item.filtro.logic">
    <display:column title="Cod. Item">
        <a href="item.get.logic?item.id=${item.id}">${item.id}</a>
    </display:column>
    <display:column title="Nome" property="nome" maxWords="6" sortable="true" >
        <a href="item.get.logic?item.id=${item.id}">${item.id}</a>
    </display:column>
    <display:column title="Medida entrada" property="tipoMedidaEntrada.nome" />
    <display:column title="Grupo" property="grupo.nome" sortable="true" />
    <display:column title="Aplicação" property="aplicacao" sortable="true" />
    <display:column title="Ação">
    	<c:if test="${alteravel}">    	
	        <a href="item.get.logic?item.id=${item.id}"><img src="images/edit.png" title="Editar item do catálogo"/></a>
	        <a href="javascript:confirmDelete('item.remove.logic?item.id=${item.id}')"><img src="images/trash.png" title="Apagar item" /></a>
        </c:if>
    </display:column>
</display:table>
