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
<jsp:include page="/header.jsp" flush="true" />
<div id="div_conteudo">
	<fieldset><legend><b>| <label class="lbTituloLegend">Requisição bloqueada</label> |</b></legend>
	<h3>Dados da requisição: </h3>
	<table width="500px;">
		<tr>
			<td width="120px">Código do documento: </td><td><strong>${requisicao.id}</strong></td>
		</tr>
		<tr>
			<td>Data de cadastro: </td><td><fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/></td>
		</tr>
		<tr>
			<td>Requisitante:</td><td>${requisicao.usuario.nome}</td>
		</tr>
		<tr>
			<td>Almoxarifado:</td><td>${requisicao.almoxarifado.nome}</td>
		</tr>
		<tr>
			<td colspan="2">
				<fieldset>
					<legend>Itens requisitados</legend>
					<c:set var="i" value="0"/>
					<table width="95%" class="grid">
						<thead>
							<tr>
								<th>Código</th><th>Nome</th><th>Unidade</th><th>Quantidade</th>
							</tr>
						</thead>
						<tbody>						
							<c:forEach var="itemRequisitado" items="${requisicao.itensRequisitados}">
								<c:set var="i" value="${i+1}"/>
								<c:if test="${i % 2 != 0}">
									<tr class="odd">
								</c:if>
								<c:if test="${i % 2 == 0}">
									<tr class="even">
								</c:if>
									<td>${itemRequisitado.item.id}</td><td>${itemRequisitado.item.nome}</td><td>${itemRequisitado.item.tipoMedidaEntrada.nome}</td><td>${itemRequisitado.quantidadeSaida}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td>Status: </td><td>Bloqueado</td>
		</tr>
		<tr>
			<td>Justificativa: </td><td>${requisicao.statusAtual.comentario}</td>
		</tr>
	</table>		      
        </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />
