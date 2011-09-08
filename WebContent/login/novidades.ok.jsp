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
<center>
	<div id="errorswrapper"><div id="alerts">
		<ul>
			<li>
				Melhorias no cálculo do custo médio e nos relatórios financeiros.
			</li>	
		</ul>
		<ul>	
			<li>
				Transferência de bens entre almoxarifados agora pode ser feita automaticamente.<br/>
				Entre em contato com o suporte para saber como.
			</li>	
		</ul>
		<ul>
			<li>
				Agora pode-se digitar textos com acento para carregar os itens nas notas de entrada e nas requisições.
			</li>	
		</ul>
		<ul>
			<li>
				Com o objetivo de manter a conscistência dos dados os fornecedores agora serão cadastrado pelos administradores do sistema.<br/>
				Para cadastrar um fornecedor mande um e-mail para almoxarifado@itec.al.gov.br. Em caso de dúvidas ligue para 33155739.
			</li>	
		</ul>
	</div></div>
</center>
</div>
<jsp:include page="/footer.jsp" flush="true" />