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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="pt_BR" scope="application"/>
<c:if test="${msgErro!=null}">
	<script type="text/javascript">
		window.alert('${msgErro}');
	</script>
</c:if>
<c:set var="i" value="0"/>
<c:set var="somatorio" value="0"/>
<c:if test="${fn:length(notaEntrada.itensEntrada) > 0}">
<table width="95%" class="grid">
<thead>
  <tr>
    <th>Código</th><th>Nome</th><th>Medida</th><th>Grupo</th><th>Quantidade</th><th>Preço Unitário</th><th>Lote</th><th>Validade</th><th>Ação</th>
  </tr>
</thead>
<tbody>
<c:forEach var="itemEntrada" items="${notaEntrada.itensEntrada}">
	<!-- Calcular o valor total dos itens -->
  <c:set var="somatorio" value="${somatorio + (itemEntrada.quantidade * itemEntrada.valorUnitario)}" />  
  <c:set var="classe" value="odd"/>  
  <c:if test="${i % 2 == 0}">
    <c:set var="classe" value="even"/>
  </c:if>
  <tr class="${classe}">
  	<td>${itemEntrada.item.id}</td>
	<td>${itemEntrada.item.nome}</td>
	<td>${itemEntrada.item.tipoMedidaEntrada.nome}</td>
	<td>${itemEntrada.item.grupo.codigoSIAFEM}</td>
	<td><fmt:formatNumber value="${itemEntrada.quantidade}" type="NUMBER"  maxFractionDigits="2" minFractionDigits="2" /></td>
  	<td><fmt:formatNumber value="${itemEntrada.valorUnitario}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
	<td>${itemEntrada.lote}</td>
	<td><fmt:formatDate value="${itemEntrada.validade}" pattern="dd/MM/yyyy" /></td>
  	<td><a href="javascript:remItem(${i});"><img src="images/trash.png" title="remover item"/></a></td>
  </tr>
  <c:set var="i" value="${i+1}"/>
</c:forEach>
</tbody>
</table>
<div style="width: 95%; font-weight: bold; text-align: right;">Valor total da Nota de Entrada: R$ <fmt:formatNumber value="${somatorio}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /> </div>
</c:if>