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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/header.jsp" flush="true" />
<!--content principal-->
<div id="div_conteudo">
 	<!-- <c:if test="${fn:length(itensEstoqueBaixo) > 0}">
		<fieldset class="aviso">
			<legend>Itens que estão com o estoque abaixo do estoque mínimo.</legend>	
			<display:table id="ie" class="grid" name="itensEstoqueBaixo" requestURI="login.almoxarifado.logic" pagesize="10">
					<display:column title="Id">${ie.id}</display:column>
					<display:column property="item.nome" sortable="true" />
					<display:column property="estoque" sortable="true" />
					<display:column property="estoqueMinimo" sortable="true" />
			</display:table>
		</fieldset>
	</c:if> -->
 	<c:if test="${fn:length(requisicoesPendentes) > 0}">
		<fieldset class="aviso">
			<legend>Requisições que ainda não foram avaliadas.</legend>	
				<display:table id="requisicao" class="grid" name="requisicoesPendentes" requestURI="requisicao.listAguardando.logic" pagesize="10">
			    	<display:column property="id"/>
			        <display:column title="requisitante" property="usuario.nome" sortable="true"/>
			        <display:column title="data de requisição" sortable="true">
			        	<fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/>
			        </display:column>
			        <display:column>
			        	<a href="requisicao.avaliacaoForm.logic?requisicao.id=${requisicao.id}"><img src="images/edit.png" title="Avaliar requisição"/></a>
			         </display:column>
			    </display:table>
		</fieldset>
	</c:if>	
 	<c:if test="${fn:length(requisicoesNaoEntregues) > 0}">
		<fieldset class="aviso">
			<legend>Requisições que ainda não foram entregues ao requisitante.</legend>	
				<display:table id="requisicao" class="grid" name="requisicoesNaoEntregues" requestURI="requisicao.listAprovadas.logic" pagesize="10">
			    	<display:column property="id"/>
			        <display:column title="requisitante" property="usuario.nome" sortable="true"/>
			        <display:column title="data de requisição" sortable="true">
			        	<fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/>
			        </display:column>
			        <display:column>
			        	<a href="requisicao.entregarForm.logic?requisicao.id=${requisicao.id}"><img src="images/edit.png" title="Entregar requisição"/></a>
			         </display:column>
			    </display:table>
		</fieldset>
	</c:if>
</div>
<jsp:include page="/footer.jsp" flush="true" />
