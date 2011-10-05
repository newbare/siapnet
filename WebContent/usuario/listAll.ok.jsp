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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/header.jsp" flush="true"/>
<script type="text/javascript">
    function loadUnidades(id) {
        $("#unidades").load("usuario.loadUnidades.logic", {"orgao.id": id});
    }
    
    function unidadeAction(id) {
        $("#usuarios").load("usuario.loadUsuarios.logic", {"unidade.id": id});        
    }
</script>
<div id="conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Listagem de usuarios</label> |</b></legend>
	<c:choose>
		<c:when test="${fn:length(orgaos) == 1}">
			<strong>Orgao: ${orgaos[0].nome} <br />
			Unidade: ${unidades[0].nome} </strong><br />
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td>Orgão:</td>
					<td><select id="orgao" name="orgao" onchange="loadUnidades(this.value)">
							<option value="0l"> - Nenhum Orgão selecionado - </option>
		  					<c:forEach var="o" items="${orgaos}">
		  						<option value="${o.id}"> ${o.nome} </option>
		  					</c:forEach>
		  				</select>						
					</td>
				</tr>
				<tr>
					<td>Unidade:</td>
					<td><div id="unidades"><jsp:include page="loadUnidades.ok.jsp" flush="true" /></div></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	<br />
	<div id="usuarios">
		<jsp:include page="buscar.ok.jsp" flush="true" />
    </div>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true"/>
