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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<jsp:include page="/header.jsp" flush="true" />
<div id="conteudo">
	<fieldset><legend><b>| <label class="lbTituloLegend">Histórico de requisições</label> |</b></legend>
	<display:table id="requisicao" class="grid" name="requisicoes" requestURI="requisicao.listAprovadas.logic" pagesize="20">
	    <display:column title="Id">
	      <a href="requisicao.acompanharDetalhes.logic?requisicao.id=${requisicao.id}">${requisicao.id}</a>
	    </display:column>
		<display:column title="Requisitante" property="usuario.nome" sortable="true"/>
		<display:column title="Estado" property="statusAtual.tipoStatus.nome" sortable="true"/>
		<display:column title="Data de requisição" sortable="true">
			<fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/>
		</display:column>
		<display:column title="Comprovante">
			<div align="center">
				 <c:if test="${requisicao.statusAtual.tipoStatus.id == 4 || requisicao.statusAtual.tipoStatus.id == 5}" >
				 	<input type="button" class="button" onclick="emitirRecibo(${requisicao.id})" value="EMITIR">
				 </c:if>
			</div>
		</display:column>
	</display:table>	      
	</fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />