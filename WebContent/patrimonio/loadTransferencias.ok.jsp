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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fieldset>
	<legend class="lbTituloLegend">Transferências</legend> 
 	<display:table id="transferencia" class="grid" name="transferencias" pagesize="10" requestURI="patrimonio.loadTransferencias.logic">
    <display:column title="Id" property="id"/>
    <display:column title="Setor de origem">
    	<c:choose>
    		<c:when test="${transferencia.setorOrigem == null}">SETOR DE PATRIMÔNIO</c:when>
    		<c:otherwise>${transferencia.setorOrigem.nome}</c:otherwise>
    	</c:choose>
    </display:column>
    <display:column title="Setor de destino" property="setorDestino.nome"/>
    <display:column title="Data"><fmt:formatDate value="${transferencia.dataSaida}" pattern="dd-MM-yyyy HH:mm" /></display:column>
    <display:column>
    	<a href="<c:url value="geraRelatorio"/>?relatorio=TransferenciaRecibo&transferencia_id=${transferencia.id}"><button>Emitir comprovante</button></a>
    </display:column>
  </display:table>
 </fieldset>