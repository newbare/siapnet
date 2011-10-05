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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<fieldset>
		<legend>
			<label class="lbTituloLegend"> Busca de Itens em Estoque </label>
		</legend>
		<form action="itemEstoque.buscarItem.logic" method="post" id="frmBuscarItem" onsubmit="return validaBusca()">
			<label for="criteria">Informe o Nome do Item ou seu Cód:</label>
			<input type="text" id="criteria" name="criteria" size="60" maxlength="50" />
			<input type="submit" value="Buscar" /> 
		</form>
	</fieldset>
<fieldset><legend><b>| <label class="lbTituloLegend">Listagem de itens em estoque</label> |</b></legend>
  <display:table id="itemEstoque" class="grid" name="itensEstoque" pagesize="25" requestURI="itemEstoque.listAll.logic">
    <display:column title="Cod. Item">
      <a href="itemEstoque.get.logic?itemEstoque.id=${itemEstoque.id}&itemEstoque.item.id=${itemEstoque.item.id}">${itemEstoque.item.id}</a>
    </display:column>
    <display:column title="Nome" sortable="true" >
      <a href="itemEstoque.get.logic?itemEstoque.id=${itemEstoque.id}">${itemEstoque.item.nome}</a>
    </display:column>
    <display:column title="Unidade" property="item.tipoMedidaEntrada.nome" />
    <display:column title="Estoque">
    	<fmt:formatNumber value="${itemEstoque.estoque}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />
    </display:column>
    <display:column title="Custo médio">
    	<fmt:formatNumber value="${itemEstoque.custoMedio}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />
    </display:column>    
    <display:column title="Estoque máximo" property="estoqueMaximo" />
    <display:column title="Estoque mínimo" property="estoqueMinimo" />
    <display:column  title="Ação">
      <a href="itemEstoque.get.logic?itemEstoque.id=${itemEstoque.id}"><img src="images/edit.png" title="Editar"/></a>
    </display:column>
  </display:table>
</fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />