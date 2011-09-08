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
<jsp:include page="/header.jsp" flush="true"/>
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Listagem de perfis de acesso</label> |</b></legend>
     <display:table id="perfil" class="grid" name="perfis" pagesize="10" requestURI="perfil.listAll.logic">
         <display:column title="Id"><a href="perfil.get.logic?perfil.id=${perfil.id}">${perfil.id}</a></display:column>
         <display:column property="nome" sortable="true"/>
         <display:column>
             <a href="perfil.get.logic?perfil.id=${perfil.id}"><img src="images/edit.png" title="Editar este perfil" /></a>
             &nbsp;
             <a href="javascript:confirmDelete('perfil.remove.logic?perfil.id=${perfil.id}')"><img src="images/trash.png" title="Apagar este perfil" /></a>
         </display:column>
     </display:table>
     </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true"/>
