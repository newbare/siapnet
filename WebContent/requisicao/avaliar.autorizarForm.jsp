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
<jsp:include page="/header.jsp" flush="true" />
<script>
$(document).ready(function() {
	$("input:text").setMask();
});
</script>
    <div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Avaliação da Requisição</label> |</b></legend>
	  <h3><label>Dados da requisição: </label></h3>
		<form method="post" action="requisicao.autorizar.logic">
		<input type="hidden" name="comentario" value="${comentario}" />	
		<table width="500px;">
			<tr>
				<td width="120px"><label>Código do documento: </label></td><td><strong>${requisicao.id}</strong></td>
			</tr>
			<tr>
				<td><label>Data de cadastro: </label></td><td><fmt:formatDate value="${requisicao.dataCadastro}" pattern="dd-MM-yyyy HH:mm"/></td>
			</tr>
			<tr>
				<td><label>Requisitante:</label></td><td>${requisicao.usuario.nome}</td>
			</tr>
			<tr>
				<td><label>Departamento:</label></td><td>${requisicao.departamento.nome}</td>
			</tr>			
			<tr>
				<td><label>Almoxarifado:</label></td><td>${requisicao.almoxarifado.nome}</td>
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
										<c:set var="bg" value="odd" />
									</c:if>
									<c:if test="${i % 2 == 0}">
										<c:set var="bg" value="even" />										
									</c:if>
									<tr class="${bg}">
										<td>${itemRequisitado.item.id}</td><td>${itemRequisitado.item.nome}</td><td>${itemRequisitado.item.tipoMedidaEntrada.nome}</td><td><fmt:formatNumber value="${itemRequisitado.quantidadeSaida}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" /></td>
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
						<legend>Itens disponibilizados</legend>
						<c:set var="i" value="0"/>
						<table width="95%" class="grid">
							<thead>
								<tr>
									<th>Código</th><th>Nome</th><th>Unidade</th><th>Estoque atual</th><th>Quantidade</th>
								</tr>
							</thead>
							<tbody>						
								<c:forEach var="itemRequisitado" items="${requisicao.itensRequisitados}">
									<c:if test="${i % 2 != 0}">
										<c:set var="bg" value="odd" />
									</c:if>
									<c:if test="${i % 2 == 0}">
										<c:set var="bg" value="even" />										
									</c:if>
									<tr class="${bg}">
										<td><input type="hidden" name="itensEnviados[${i}].item.id" value="${itemRequisitado.item.id}" /> ${itemRequisitado.item.id}</td>
										<td>${itemRequisitado.item.nome}</td><td>${itemRequisitado.item.tipoMedidaEntrada.nome}</td>
										<td>
											<c:forEach var="itemEstoque" items="${itensEstoque}">
												<c:if test="${itemEstoque.item.id == itemRequisitado.item.id}">
													<fmt:formatNumber value="${itemEstoque.estoque}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />
												</c:if>
											</c:forEach>
										</td>
										<td><input type="text" name="iEQtd[${i}]" alt="decimal" value="<fmt:formatNumber value="${itemRequisitado.quantidadeSaida}" type="NUMBER" maxFractionDigits="2" minFractionDigits="2" />" /></td>
									</tr>
									<c:set var="i" value="${i+1}"/>
								</c:forEach>
							</tbody>
						</table>
					</fieldset>
				</td>
			</tr>			
		</table>
		<center><input type="submit" class="button" value="avaliar" /></center>
		</form>			       
      </fielset>
    </div>
    <jsp:include page="/footer.jsp" flush="true" />