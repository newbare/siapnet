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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/header.jsp" flush="true"/>
<script type="text/javascript">
    function loadUnidades(id) {
    	uId = document.getElementById("almoxarifado.unidade.id").value;
    	
        $("#unidades").load("almoxarifado.loadUnidades.logic", {"orgao.id": id});
    }  
</script>
<div id="div_conteudo">
    <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Almoxarifado</label> |</b></legend>
        <c:choose>
            <c:when test="${almoxarifado.id==null}">
                <h3>Preencha os dados do formulário para cadastrar um novo almoxarifado:</h3>
            </c:when>
            <c:otherwise>
                <h3>Edite os dados do formulário que deseja atualizar o cadastro do almoxarifado:</h3>
            </c:otherwise>
        </c:choose>
        <form method="post" action="almoxarifado.gravar.logic">
            <input type="hidden" name="almoxarifado.id" value="${almoxarifado.id}"/>
            <fieldset>
                <legend>Dados do Almoxarifado</legend>
                <table>
                    <c:choose>
		  		<c:when test="${fn:length(orgaos) == 1}">
		  			<tr>
		  				<td>Orgãos: </td>
		  				<td>
		  					<input type="hidden" name="orgao.id" value="${orgaos[0].id}"/>
		  					${orgaos[0].nome}
		  				</td>
		  			</tr>	
		  		</c:when>
		  		<c:otherwise>
		  			<tr>
		  				<td>Orgãos: </td>
		  				<td>
		  					<select style="width: 400px;" name="orgao.id" onchange="loadUnidades(this.value)">
		  						<option value="0">-- Nenhum orgão escolhido --</option>
		  						<c:forEach var="o" items="${orgaos}">
		  							<c:choose>
		  								<c:when test="${o.id == almoxarifado.unidade.orgao.id}">
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
                    <tr><td><label for="nome">Nome:</label></td><td><input type="text" id="nome" onblur="this.value=this.value.toUpperCase()"  name="almoxarifado.nome" value="${almoxarifado.nome}" size="50" maxlength="50" /></td></tr>
                    <tr><td><label for="responsavel">Responsável:</label></td><td><input type="text" onblur="this.value=this.value.toUpperCase()"  id="responsavel" name="almoxarifado.responsavel" value="${almoxarifado.responsavel}" size="50" maxlength="50" /></td></tr>
                    <tr><td><label for="telefone">Telefone:</label></td><td><input type="text" id="telefone" name="almoxarifado.telefone" alt="phone" value="${almoxarifado.telefone}" size="15" maxlength="14" /></td></tr>                     
                </table>
            </fieldset>
            <p align="center"><input type="button" class="button" value="Salvar informações" onclick="forms[0].submit()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
        </form>
    </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />
