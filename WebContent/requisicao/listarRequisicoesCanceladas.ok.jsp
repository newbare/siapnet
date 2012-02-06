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

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/header.jsp" flush="true" />
<!--Script para validar a busca, onde a validação é verificar se o valor a ser pesquisado é maior que 3 letras-->
<script type="text/javascript">
	function validaBusca() {
		if (document.getElementById('criteria').value.length >= 3) {
			return true;
		} else {
			alert("Para realizar a busca o valor informado deve conter pelo menos 3 letras!");
			return false;
		}
	}
</script>
<!--Condicional para definir a quantidade de registro por paginação, 
    o resultado de uma pesquisa num deve ter paginação, caso contrário, irá exibir 30 registros por paginação -->
<c:if test="${criteria == null}">
	<c:set var="paginas" value="20" />
</c:if>
<c:if test="${criteria != null}">
	<c:set var="paginas" value="0" />
</c:if>

<div id="conteudo">
<!-- 	<fieldset>
		<legend>
			<label class="lbTituloLegend"> Busca de Requisições Canceladas </label>
		</legend>
		<form action="requisicao.buscarRequisicaoCancelada.logic" method="post" id="frmBuscarRequisicaoCancelada" onsubmit="return validaBusca()">
			<label for="criteria">Informe o Nº da Requisição:</label>
			<input type="text" id="criteria" name="criteria" size="60" maxlength="50" />
			<input type="submit" value="Buscar" /> 
		</form>
	</fieldset> 
 -->    
    <fieldset><legend><b>| <label class="lbTituloLegend">Requisições Canceladas </label> |</b></legend>
        <display:table style="width: 90%" id="requisicaoCancelada" class="grid" name="cancelamantoRequisicoes" pagesize="${paginas}" requestURI="requisicao.listarRequisicoesCanceladas.logic">
            <display:column title="Número da Requisição" property="numeroRequisicao" sortable="true"/>
            <display:column title="Justificativa do Cancelamento" property="justificativa" style="width: 50%" />  
            <display:column title="Responsável pelo Cancelamento" property="responsavelCancelamento.nome" style="width: 20%" />
            <display:column title="Data do Cancelamento"  style="width: 20%">
	        	<fmt:formatDate value="${requisicaoCancelada.dataCancelamento}"  pattern="dd/MM/yyyy"/>
	        </display:column>  
	        
	    </display:table>	      
    </fieldset>
    </div>
    <jsp:include page="/footer.jsp" flush="true" />
