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
<c:choose>
	<c:when test="${usuarios == null || fn:length(usuarios) > 0}">
		<fieldset><legend><b>| <label class="lbTituloLegend">Listagem de usuarios</label> |</b></legend>
		    <display:table id="usuario" class="grid" name="usuarios">
		        <display:column sortable="true" title="Id"><a href="usuario.get.logic?usuario.id=${usuario.id}">${usuario.id}</a></display:column>
		        <display:column sortable="true" title="Nome"><a href="usuario.get.logic?usuario.id=${usuario.id}">${usuario.nome}</a></display:column>
		        <display:column sortable="true" title="Orgão / Unidade / Setor"><c:forEach var="ud" items="${usuario.usuarioDepartamentos}" >${ud.departamento.unidade.orgao.sigla} - ${ud.departamento.unidade.nome} - ${ud.departamento.nome}<br/></c:forEach></display:column>
		        <display:column property="funcao" title="Função" sortable="true" style="width: 10%;" />
		        <display:column  sortable="true" title="E-mail" property="email"/>
		        <display:column  sortable="true" title="CPF" property="cpf"/>
		        <display:column style="width: 50px;">
		        	<c:choose>
		        		<c:when test="${desativado}">
		        			<a href="usuario.reabilitar.logic?usuario.id=${usuario.id}"><img src="images/check.gif" title="Re-ativar usuário" /></a>
		        		</c:when> 
		        		<c:otherwise>
							<a href="usuario.get.logic?usuario.id=${usuario.id}"><img src="images/edit.png" title="Editar usuário" /></a>
							    &nbsp;
							<a href="javascript:confirmDelete('usuario.remove.logic?usuario.id=${usuario.id}')"><img src="images/trash.png" title="Apagar usuário" /></a>
		        		</c:otherwise>
		        	</c:choose>		            
		        </display:column>
		    </display:table>
		</fieldset>		
	</c:when>
	<c:otherwise>
		<div align="center"> 
			<b> <label class="lbTituloLegend" style="color: red;">Usuário não encontrado!</label></b>
		</div>
	</c:otherwise>
</c:choose>
<div align="center"> 
	<span id="indBuscaUsuario"></span>
</div>


