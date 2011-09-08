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
<jsp:include page="/header.jsp" flush="true" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="pt_BR" scope="application"/>
<div id="div_conteudo">
	<h2>Relatório de Curva ABC</h2>    	
	<table style="width: 90%; text-align: left;" class="grid">					
		<thead>
			<tr>
				<th>Nº</th><th style="text-align: left;">Id</th><th>Nome</th><th>Quantidade</th><th>V. de Consumo</th><th>V. Acumulado</th><th>P. Acumulado</th><th>Classificação</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="i" value="0"/>
			<c:set var="j" value="0"/>
			<c:forEach items="${itensSaidaA}" var="is">
				<c:set var="i" value="${i + 1}"/>
				<c:set var="j" value="${j + 1}"/>
				<tr style="background-color: #e57272;">
					<td>${j}</td>
					<td>${is.item.id}</td>
					<td>${is.item.nome}</td>
					<td><fmt:formatNumber value="${is.quantidade}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td>R$ <fmt:formatNumber value="${is.valorConsumo}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td>R$ <fmt:formatNumber value="${is.valorAcumulado}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td><fmt:formatNumber value="${is.percentualAcumulado}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />%</td>
					<c:if test="${i == 1}">
						<td style="text-align: center;" rowspan="${fn:length(itensSaidaA)}">A</td>
					</c:if>
				</tr>
			</c:forEach>
			<c:set var="i" value="0"/>
			<c:forEach items="${itensSaidaB}" var="is">
				<c:set var="i" value="${i + 1}"/>
				<c:set var="j" value="${j + 1}"/>
				<tr style="background-color: #ffecab;">
					<td>${j}</td>
					<td>${is.item.id}</td>
					<td>${is.item.nome}</td>
					<td><fmt:formatNumber value="${is.quantidade}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td>R$ <fmt:formatNumber value="${is.valorConsumo}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td>R$ <fmt:formatNumber value="${is.valorAcumulado}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td><fmt:formatNumber value="${is.percentualAcumulado}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />%</td>
					<c:if test="${i == 1}">
						<td style="text-align: center;" rowspan="${fn:length(itensSaidaB)}">B</td>
					</c:if>					
				</tr>
			</c:forEach>
			<c:set var="i" value="0"/>
			<c:forEach items="${itensSaidaC}" var="is">
				<c:set var="i" value="${i + 1}"/>
				<c:set var="j" value="${j + 1}"/>
				<tr style="background-color: #48c480;">
					<td>${j}</td>
					<td>${is.item.id}</td>
					<td>${is.item.nome}</td>
					<td><fmt:formatNumber value="${is.quantidade}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td>R$ <fmt:formatNumber value="${is.valorConsumo}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td>R$ <fmt:formatNumber value="${is.valorAcumulado}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
					<td><fmt:formatNumber value="${is.percentualAcumulado}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />%</td>
					<c:if test="${i == 1}">
						<td style="text-align: center;" rowspan="${fn:length(itensSaidaC)}">C</td>
					</c:if>
				</tr>
			</c:forEach>												
		</tbody>			
	</table>	
</div>
<jsp:include page="/footer.jsp" flush="true" />