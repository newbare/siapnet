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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<fmt:setLocale value="pt_BR" scope="application"/>

<script>
$(document).ready(function() {   
    $('#btCancelar').click(function(event){
    	var requisicaoId = $('#requisicaoCanceladaId').val();
    	window.location = 'requisicao.cancelar.logic?requisicao.id='+ requisicaoId;    	
    });    
});
</script>

<c:choose>
	<c:when test="${requisicao == null}">
		Nenhuma requisição encontrada com este código.
	</c:when>
	<c:otherwise>
		<fieldset style="width: 50%;">
			<input type="hidden" id="requisicaoCanceladaId" value="${requisicao.id}"/>
			<legend>Dados da requisição</legend>
			<table>
				<tr>
					<td><label>Status: </label></td><td><strong>${requisicao.statusAtual.tipoStatus.nome}</strong></td>
				</tr>			
				<tr>
					<td><label>Data solicitada: </label></td><td><fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/></td>
				</tr>
				<tr>
					<td><label>Requisitante: </label></td><td>${requisicao.usuario.nome}</td>
				</tr>
				<tr>
					<td><label>Almoxarifado: </label></td><td>${requisicao.almoxarifado.nome}</td>
				</tr>
				<tr>
					<td><label>Observação do almoxarife: </label></td><td>${requisicao.statusAtual.comentario}</td>
				</tr>
				<tr>
					<td colspan="2">
						<fieldset>
	                        <legend> Itens requisitados </legend>
							<c:set var="i" value="0"/>
							<table width="95%" class="grid">
								<thead>
									<tr>
										<th align="left">Código</th><th align="left">Nome</th><th align="right">Quantidade</th>
									</tr>
								</thead>
								<tbody>						
									<c:forEach var="itemRequisitado" items="${requisicao.itensRequisitados}">
										<c:set var="i" value="${i+1}"/>
										<c:if test="${i % 2 != 0}">
											<c:set var="classe" value="odd"/>
										</c:if>
										<c:if test="${i % 2 == 0}">
											<c:set var="classe" value="even"/>
										</c:if>
										<tr class="${classe}">
											<td>${itemRequisitado.item.id}</td><td>${itemRequisitado.item.nome}</td>
											<td align="right">
												<fmt:formatNumber value="${itemRequisitado.qtdRequisitada}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</fieldset>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<fieldset>
	                        <legend> Itens disponibilizados </legend>
							<c:set var="i" value="0"/>
							<table width="95%" class="grid">
								<thead>
									<tr>
										<th align="left">Código</th><th align="left">Nome</th><th align="right">Quantidade</th>
									</tr>
								</thead>
								<tbody>						
									<c:forEach var="is" items="${requisicao.itensEnviados}">
										<c:set var="i" value="${i+1}"/>
										<c:if test="${i % 2 != 0}">
											<c:set var="classe" value="odd"/>
										</c:if>
										<c:if test="${i % 2 == 0}">
											<c:set var="classe" value="even"/>
										</c:if>
										<tr class="${classe}">
											<td>${is.item.id}</td><td>${is.item.nome}</td>
											<td align="right">
												<fmt:formatNumber value="${is.quantidade}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</fieldset>
					</td>
				</tr>				
			</table>
			<input id="btCancelar" type="button" class="button" value="CANCELAR REQUISIÇÃO" />			
		</fieldset>
	</c:otherwise>
</c:choose>