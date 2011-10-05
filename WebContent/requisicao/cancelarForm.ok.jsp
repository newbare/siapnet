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
<jsp:include page="/header.jsp" flush="true" />
<script>
$(document).ready(function() {
    $('#btCarregar').click(function(event){
    	var requisicaoId = $('#requisicaoId').val();
		$('#load').load('requisicao.cancelarLoad.logic?requisicao.id='+ requisicaoId);
    });    
});
</script>

<div id="conteudo">
    <fieldset style="width: 50%;"><legend><b>| <label class="lbTituloLegend">Cancelar requisição</label> |</b></legend>
        <h3>Digite o código da requisição para que a mesma seja cancelada.</h3>
        Código: 
        <input type="text" id="requisicaoId" name="requisicao.id"/>
        <input id="btCarregar" type="button" class="button" value="Carregar" />
    </fieldset>
    <div id="load">
    </div>    
</div>
<jsp:include page="/footer.jsp" flush="true" />