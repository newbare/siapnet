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
<jsp:include page="/mensagensErro.jsp" flush="true" />
<br/>
<display:table id="patrimonio" class="grid" name="itensTombados" export="true" pagesize="20" requestURI="patrimonio.filtro.logic" >
  <display:column media="html" title="Tombo">
    <a href="patrimonio.recupera.logic?patrimonio.id=${patrimonio.id}">${patrimonio.numero}</a>
  </display:column>
  <display:column media="excel csv xml" title="Tombo" property="numero"/>
  <display:column media="html" title="Bem">
    <a href="patrimonio.recupera.logic?patrimonio.id=${patrimonio.id}">${patrimonio.itemEntrada.item.nome}</a>
  </display:column>
  <display:column media="excel csv xml" title="Bem" property="itemEntrada.item.nome"/>
  <display:column title="Modelo" property="modelo" maxWords="4" />
  <display:column title="Marca" property="marca.nome" maxWords="4"  />
  <display:column title="Num. de série" property="numeroSerie" maxWords="2" />
  <display:column title="Observação." property="observacao" maxWords="4"  />
</display:table>

