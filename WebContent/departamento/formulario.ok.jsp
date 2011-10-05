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
<script type="text/javascript">
    function loadUnidades(id, unidadesElement) {
        if(unidadesElement == "unidades")
    		$("#"+ unidadesElement).load("departamento.loadUnidades.logic", {"orgao.id": id});
        else        
        	$("#"+ unidadesElement).load("departamento.loadUnidadesAlmoxarifado.logic", {"orgao.id": id});
    }

    function loadAlmoxarifados(id) {        
        $("#almoxarifados").load("departamento.loadAlmoxarifados.logic", {"unidade.id": id});
    }

    function addAlmoxarifado() {
        id = document.getElementById("almoxarifado.id").value;
        $("#almoxarifadosList").load("departamento.addAlmoxarifado.logic", {"almoxarifado.id": id});
    }

    function remAlmoxarifado(id) {
    	$("#almoxarifadosList").load("departamento.remAlmoxarifado.logic", {"almoxarifado.id": id});
    }    
</script>

<div id="conteudo">
<c:choose>
  <c:when test="${departamento.id==null}">
    <h3>Preencha os dados do formulário para cadastrar um novo departamento:</h3>
  </c:when>
  <c:otherwise>
    <h3>Edite os dados do formulário que deseja atualizar o cadastro do departamento:</h3>
  </c:otherwise>
</c:choose>
    
<fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Departamento</label> |</b></legend>
<form method="post" action="departamento.gravar.logic">
	<input type="hidden" name="departamento.id" value="${departamento.id}"/>
    <table>
		<c:choose>
			<c:when test="${fn:length(orgaos) == 1}">
				<tr>
			  		<td>Orgãos: </td>
			  		<td>
			  			<input type="hidden" id="orgao.id" value="${orgaos[0].id}"/>
			  			${orgaos[0].nome}
			  		</td>
			  	</tr>	
			</c:when>
			<c:otherwise>
				<tr>
	  				<td>Orgãos: </td>
	  				<td>
	  					<select style="width: 400px;" id="orgao.id" onchange="loadUnidades(this.value, 'unidades')">
	  						<option value="0">-- Nenhum orgão escolhido --</option>
	  						<c:forEach var="o" items="${orgaos}">
	  							<c:choose>
	  								<c:when test="${o.id == departamento.unidade.orgao.id}">
	  									<option value="${o.id}" selected="selected">${o.nome}</option>
	  								</c:when>
	  								<c:otherwise>
	  									<option value="${o.id}">${o.nome}</option>
	  								</c:otherwise>
	  							</c:choose>
	  							
	  						</c:forEach>
	  					</select>
	  				</td>
	  			</tr>
	  		</c:otherwise>
		</c:choose>
  		<tr>
  			<td>Unidades: </td>
  			<td><div id="unidades"><jsp:include page="loadUnidades.ok.jsp" flush="true" /></div></td>
  		</tr>	         			
        <tr><td>Nome:</td><td><input type="text" name="departamento.nome"  onblur="this.value=this.value.toUpperCase()" value="${departamento.nome}" size="90" maxlength="200" /></td></tr>
        <tr><td>Sigla:</td><td><input type="text" name="departamento.sigla"  onblur="this.value=this.value.toUpperCase()" value="${departamento.sigla}" size="10" maxlength="10"/></td></tr>
        <tr><td>Responsável:</td><td><input type="text" name="departamento.responsavel"  onblur="this.value=this.value.toUpperCase()" value="${departamento.responsavel}" size="90" maxlength="100" /></td></tr>
        <tr><td>Telefone:</td><td><input type="text" name="departamento.telefone" alt="phone" value="${departamento.telefone}" size="14" maxlength="14" /></td></tr>
        <tr>
        	<td colspan="2">
          		<fieldset>
          			<legend>Adicionar almoxarifado</legend>
          			<table>
						<c:choose>					
					  		<c:when test="${fn:length(orgaos) == 1}">
					  			<tr>
					  				<td>Orgãos: </td>
					  				<td>
					  					<input type="hidden" name="orgao.id" id="orgao.id" value="${orgaos[0].id}"/>
					  					${orgaos[0].nome}
					  				</td>
					  			</tr>	
					  		</c:when>
					  		<c:otherwise>
					  			<tr>
					  				<td>Orgãos: </td>
					  				<td>
					  					<select style="width: 400px;" id="orgao.id" onchange="loadUnidades(this.value, 'unidadesAlmoxarifado')">
					  						<option value="0">-- Nenhum orgão escolhido --</option>
					  						<c:forEach var="o" items="${orgaos}">
					  							<c:choose>
					  								<c:when test="${o.id == departamento.unidade.orgao.id}">
					  									<option value="${o.id}" selected="selected">${o.nome}</option>
					  								</c:when>
					  								<c:otherwise>
					  									<option value="${o.id}">${o.nome}</option>
					  								</c:otherwise>
					  							</c:choose>					  							
					  						</c:forEach>
					  					</select>
					  				</td>
					  			</tr>
					  		</c:otherwise>
		  			</c:choose>  			          							
  				<tr>
	  				<td>Unidades: </td>
	  				<td><div id="unidadesAlmoxarifado"><jsp:include page="loadUnidades.ok.jsp" flush="true" /></div></td>
	  			</tr>
	  			<tr>
	  				<td>Almoxarifados: </td>
	  				<td><div id="almoxarifados"><jsp:include page="loadAlmoxarifados.ok.jsp" flush="true" /></div></td>
	  			</tr>
	  			<tr>
	  				<td></td><td style="text-align: right;"><input type="button" value="Adicionar" onclick="addAlmoxarifado()"/></td>
	  			</tr>
  			</table>	         			          			
          		</fieldset>
          	</td>          			          	
          </tr>
          <tr>
          	<td colspan="2">
          		<fieldset>
          			<legend>Almoxarifados</legend>
          			<div id="almoxarifadosList"><jsp:include page="addAlmoxarifado.ok.jsp" flush="true" /></div>
          		</fieldset>
          	</td>
          </tr>
          <tr><td colspan="2">
        	<p align="center">
        		<input type="button" class="button" value="Salvar informações" onclick="forms[0].submit()" />
        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" />
    		</p>
    	</td></tr>
    	</table>
 </form>    
 </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />