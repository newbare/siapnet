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
<%@ taglib  prefix="display" uri="http://displaytag.sf.net" %>
<jsp:include page="/header.jsp" flush="true"/>
<script type="text/javascript">
	function validaBusca() {
		if (document.getElementById('criteria').value.length >= 3) {
			return true;
		} else {
			alert("Para realizar a busca informe um valor que contenha pelo menos 3 letras!");
			return false;
		}
	}
</script>
<c:if test="${criteria == null}">
	<c:set var="paginas" value="10" />
</c:if>
<c:if test="${criteria != null}">
	<c:set var="paginas" value="0" />
</c:if>
<div id="conteudo">
	<fieldset>
		<legend>
			<label class="lbTituloLegend"> Busca de Departamentos: </label>
		</legend>
		<form action="departamento.buscar.logic" method="post" id="frmBuscar" onsubmit="return validaBusca()">
			<label for="criteria" title="Nome do departameno ou orgão ou a SIGLA dos mesmos">
				Digite os critérios de busca:
			</label>
			<input type="text" id="criteria" name="criteria" size="50" maxlength="50" />
			<input type="submit" value="Buscar" /> 
		</form>
	</fieldset>
    <fieldset><legend><b>| <label class="lbTituloLegend">Listagem de Departamentos(Setores)</label> |</b></legend>
       	<display:table id="departamento" class="grid" name="departamentos" pagesize="${paginas}" requestURI="departamento.listAll.logic">
           	<display:column title="Id"><a href="departamento.get.logic?departamento.id=${departamento.id}">${departamento.id}</a></display:column>
           	<display:column property="nome" sortable="true"/>
           	<display:column  title="Orgão/Unidade">
               	${departamento.unidade.orgao.sigla} / ${departamento.unidade.nome}
           	</display:column>

            <display:column property="responsavel" sortable="true"/>
            <display:column property="telefone"/>
            <display:column  title="Almoxarifados">
        <c:forEach var="a" items="${departamento.almoxarifados}" > - ${a.nome}<br/></c:forEach>
            </display:column>
            <display:column  title="Ação">
                <a href="departamento.get.logic?departamento.id=${departamento.id}"><img src="images/edit.png" alt="Editar departamento" /></a>
                &nbsp;
                <a href="javascript:confirmDelete('departamento.remove.logic?departamento.id=${departamento.id}')"><img src="images/trash.png" title="Apagar departamento"/></a>
            </display:column>
        </display:table>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true"/>
