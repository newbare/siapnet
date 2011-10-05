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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/header.jsp" flush="true" />
<script charset="utf-8" type="text/javascript">
        var paramId = null;
        var paramNome = null;
        var paramTipo = null;

        function addParam() {
            //relatorioId = document.getElementById("relatorio.id").value;
            paramNome		= document.getElementById("paramNome").value;
            paramDescricao	= document.getElementById("paramDescricao").value;
            paramTipo		= document.getElementById("paramTipo").value;

            $("#parametros").load("relatorio.addParam.logic", {"parametro.nome": paramNome, "parametro.tipo":paramTipo, "parametro.titulo" : paramDescricao});
            document.getElementById("paramNome").value='';
            document.getElementById("paramTipo").value='';
            document.getElementById("paramDescricao").value='';
        }

        function remParam(paramId) {
        	//relatorioId = document.getElementById("relatorio.id").value;
            $("#parametros").load("relatorio.remParam.logic", {"parametro.id": paramId});
        }
</script>    
<div id="conteudo">
	<form action="relatorio.gravar.logic" method="post">
		<fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Relatório</label> |</b></legend>
           	<input type="hidden" name="relatorio.id" id="relatorio.id" value="${relatorio.id}">
        	<fieldset>
          		<label for="titulo">Título:</label>&nbsp;
          		<input type="text" name="relatorio.titulo" id="titulo" size="20" value="${relatorio.titulo}">
          		<label for="arquivo">Arquivo:</label>&nbsp;
          		<input type="text" name="relatorio.arquivoJasper" id="arquivo" size="20" value="${relatorio.arquivoJasper}">
        	</fieldset>
        	<fieldset><legend>Parâmetro</legend>
          		<label for="paramNome">Nome:</label> <input type="text" id="paramNome" size="20"/>
          		<label for="paramDescricao">Descricao:</label> <input type="text" id="paramDescricao" size="20"/>
          		<label for="paramTipo">Tipo:</label>
          		<select name="paramTipo" id="paramTipo" >
            		<option value="java.lang.String">String</option>
            		<option value="java.lang.Integer">Integer</option>
            		<option value="java.lang.Long">Long</option>
            		<option value="java.util.Date">Date</option>
          		</select>
          		<input type="button" value="Adicionar" onclick="addParam()"/>
          		<fieldset>
	            	<legend>Parâmetros</legend>
	            	<div id="parametros">
	              		<c:if test="${((relatorio.id!=null) && fn:length(relatorio.parametros)>0)}">
	                		<jsp:include page="addParam.ok.jsp" flush="true"/>
	              		</c:if>
	            	</div>
          		</fieldset>
        	</fieldset>
        	<input type="submit" class="button" />    	
		</fieldset>
	</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />