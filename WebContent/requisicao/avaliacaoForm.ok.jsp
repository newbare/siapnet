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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="br.edu.uncisal.almoxarifado.model.TipoStatus"%><html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Almoxarifado</title>
    <style type="text/css">
      @import url("/almoxarifado/css/layout.css");
      @import url("/almoxarifado/css/grid.css");
    </style>
  </head>
  <body>
    <jsp:include page="/header.jsp" flush="true" />
    <div id="conteudo">
      <fieldset><legend><b>| <label class="lbTituloLegend">Avaliação da Requisição</label> |</b></legend>
	  <h3>Dados da requisição: </h3>
		<form method="post" action="requisicao.avaliar.logic">	
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
				<td><label>Avaliação:</label></td>
				<td>
					<input type="radio" class="radio" name="aval" value="<%= TipoStatus.APROVADO %>" checked="checked"/>Aprovar<br />
					<input type="radio" class="radio" name="aval" value="<%= TipoStatus.BLOQUEADO %>" /> Bloquear
				</td>
			</tr>
			<tr>
				<td><label>Justificativa:</label></td>
				<td>
					<textarea rows="5" cols="50" name="comentario"></textarea>
				</td>
			</tr>
		</table>
		<center><input type="submit" class="button" value="avaliar" /></center>
		</form>
      </fieldset>
    </div>
    <jsp:include page="/footer.jsp" flush="true" />
  </body>
</html>