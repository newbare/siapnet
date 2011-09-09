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
<%@page import="br.edu.uncisal.almoxarifado.model.TipoStatus"%>
    <jsp:include page="/header.jsp" flush="true" />
    <div id="div_conteudo">
      <fieldset><legend><b>| <label class="lbTituloLegend">Detalhes da Requisição</label> |</b></legend>
	  <h3>Dados da requisição: </h3>
		<table width="500px;">
			<tr>
				<td width="120px"><label>Código do documento: </label></td><td><strong>${requisicao.id}</strong></td>
			</tr>
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
			<c:if test="${requisicao.almoxarifadoRequisitante != null}">
				<tr>
					<td colspan="2">
						<input type="button" onclick='aceitar(${requisicao.id});' value="Aceitar recebimento"/>				
					</td>
				</tr>
			</c:if>						
		</table>
      </fieldset>
    </div>
    <jsp:include page="/footer.jsp" flush="true" />