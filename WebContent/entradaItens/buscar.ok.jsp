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

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fieldset><legend><b>| <label class="lbTituloLegend">Entradas de Itens</label> |</b></legend>
<!-- Tratamento de erros -->
<jsp:include page="/mensagensErro.jsp" flush="true" />
<!-- fim do tratamento de erros -->
    <display:table id="notaEntrada" class="grid" name="notaEntradas">
        <display:column property="id"/>
        <display:column title="Número da Nota" sortable="true">
            ${notaEntrada.numero}
        </display:column>
        <display:column title="Observação" sortable="true" maxWords="3">
            ${notaEntrada.observacao}
        </display:column>
        <display:column title="Fornecedor" sortable="true">
            ${notaEntrada.fornecedor.nome}
        </display:column>
        <display:column title="Almoxarifado" property="almoxarifado.nome" sortable="true"/>
        <display:column title="Data de entrada" sortable="true">
            <fmt:formatDate value="${notaEntrada.data}" pattern="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Ação">
            <a href="entradaItens.get.logic?notaEntrada.id=${notaEntrada.id}"><img src="images/edit.png" title="Editar nota de entrada"/></a>
            &nbsp;
            <a href="javascript:confirmDelete('entradaItens.remove.logic?notaEntrada.id=${notaEntrada.id}')"><img src="images/trash.png" title="Apagar nota de entrada"/></a>
        </display:column>
    </display:table>
</fieldset>
