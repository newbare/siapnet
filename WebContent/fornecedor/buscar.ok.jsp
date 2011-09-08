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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:if test="${fornecedores!=null && fn:length(fornecedores)>0}" >
<display:table id="fornecedor" class="grid" name="fornecedores" pagesize="10" requestURI="fornecedor.filtro.logic">
	<display:column title="Id">
		<a href="fornecedor.ver.logic?fornecedor.id=${fornecedor.id}">${fornecedor.id}</a>
	</display:column>
	<display:column title="Nome" property="nome" sortable="true" >
        <a href="fornecedor.ver.logic?fornecedor.id=${fornecedor.id}">${fornecedor.nome}</a>
	</display:column>
	<display:column title="Razão Social" property="razaoSocial" sortable="true" />
	<display:column title="Telefone" property="telefone" />
	<display:column title="CPF/CNPJ" property="cpfCnpj" sortable="true" />
	<display:column title="IE" property="inscricaoEstadual" sortable="true" />
	<display:column title="Contato" property="contato" sortable="true" />
	<display:column title="Fone Contato" property="telefoneContato" sortable="true" />
	<display:column>
		<a href="fornecedor.get.logic?fornecedor.id=${fornecedor.id}"><img src="images/edit.png"></a>
	</display:column>
</display:table>
</c:if>
<c:if test="${fn:length(fornecedores)==0}">
  Nenhum Fornecedor encontrado para esse critério de busca.
</c:if>