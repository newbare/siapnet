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
<jsp:include page="/header.jsp" flush="true" />
<div id="conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Catálogo Geral de Itens</label> |</b></legend>
        <form method="post" action="geraRelatorio" accept-charset="utf-8" target="_blank">
        	<input type="hidden" name="relatorio" value="ItensPorGrupo" />
        	<jsp:include page="/relatorio/formato.jsp" flush="true" />
            <input type="submit" value="Exibir Relatório">
		</form>
	</fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />