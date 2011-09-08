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
<jsp:include page="/header.jsp" flush="true" />
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Listagem de tipo de fornecedores</label> |</b></legend>
        <display:table id="tipoFornecedor" class="grid" name="tipoFornecedores" pagesize="10" requestURI="tipoFornecedor.listAll.logic">
            <display:column title="Id">
                <a href="tipoFornecedor.get.logic?tipoFornecedor.id=${tipoFornecedor.id}">${tipoFornecedor.id}</a>
            </display:column>
            <display:column property="nome" sortable="true" />
            <display:column>
                <a href="tipoFornecedor.get.logic?tipoFornecedor.id=${tipoFornecedor.id}"><img src="images/edit.png" title="Editar tipo de fornecedor" /></a>
                &nbsp;
                <a href="javascript:confirmDelete('tipoFornecedor.remove.logic?tipoFornecedor.id=${tipoFornecedor.id}')"><img src="images/trash.png" title="Apagar tipo de fornecedor" /></a>
            </display:column>
        </display:table>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />
