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
  <display:table id="itemEstoque" class="grid" name="itensEstoque" pagesize="25" requestURI="itemEstoque.filtro.logic">
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
    <display:column title="Estoque máximo" property="estoqueMaximo" />
    <display:column title="Estoque mínimo" property="estoqueMinimo" />
    <display:column  title="Ação">
      <a href="itemEstoque.get.logic?itemEstoque.id=${itemEstoque.id}"><img src="images/edit.png" title="Editar nota de entrada"/></a>
      &nbsp;
      <a href="javascript:confirmDelete('itemEstoque.remove.logic?itemEstoque.id=${itemEstoque.id}')"><img src="images/trash.png" title="Apagar nota de entrada" /></a>
    </display:column>
  </display:table>