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
<jsp:include page="/header.jsp" flush="true" />
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Listagem de itens permanentes não tombados</label> |</b></legend>
    <display:table id="itemEntradaPermanente" class="grid" name="itensEntradaPermanentes" pagesize="10" requestURI="patrimonio.listAll.logic">
      <display:column title="Id">
        <a href="patrimonio.get.logic?itemEntrada.id=${itemEntradaPermanente.id}">${itemEntradaPermanente.id}</a>
      </display:column>
      <display:column property="item.nome" maxWords="4" sortable="true" />
      <display:column title="Medida" property="item.tipoMedidaEntrada.nome" sortable="true" />
      <display:column title="Grupo" property="item.grupo.nome" sortable="true" />
      <display:column title="Aplicação" property="item.aplicacao" sortable="true" />
      <display:column title="Nota de Entrada" property="notaEntrada.numero" sortable="true" />
    </display:table>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />