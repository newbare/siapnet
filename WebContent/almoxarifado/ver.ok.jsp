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
<jsp:include page="/header.jsp" flush="true"/>
<div id="conteudo">
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
        <tr><td><label for="nome">Nome:</label></td><td>${almoxarifado.nome}</td></tr>
        <tr><td><label for="responsavel">Responsável:</label></td><td>${almoxarifado.responsavel}</td></tr>
        <tr><td><label for="telefone">Telefone:</label></td><td>${almoxarifado.telefone}</td></tr>
        <tr><td colspan="2">
            <fieldset><legend>Endereço</legend>
              <table>
                <tr>
                  <td><label for="endereco.logradouro" style="font-size: 10pt;">Logradouro: </label></td>
                  <td>${almoxarifado.endereco.logradouro}</td>
                </tr>
                <tr>
                  <td><label for="endereco.numero" style="font-size: 10pt;">Número: </label></td>
                  <td>${almoxarifado.endereco.numero}&nbsp;&nbsp;
                    <label for="endereco.bairro" style="font-size: 10pt;">Bairro: </label>
                  ${almoxarifado.endereco.bairro}</td>
                </tr>
                <tr>
                  <td><label for="endereco.complemento" style="font-size: 10pt;">Complemento:</label></td>
                  <td>${almoxarifado.endereco.complemento}</td>
                </tr>
                <tr>
                  <td><label for="cep" style="font-size: 10pt;">CEP: </label></td>
                  <td>${almoxarifado.endereco.cep}&nbsp;&nbsp;
                    <label for="endereco.uf" style="font-size: 10pt;">UF: </label>
                    ${almoxarifado.endereco.municipio.uf.sigla}
                    <span id="cidades">
                      <label for="endereco.municipio" style="font-size: 10pt;">Município:</label>
                      ${almoxarifado.endereco.municipio.nome}
                    </span>
                  </td>
                </tr>
              </table>
            </fieldset>
        </td></tr>
        <tr><td align="center"><input type="submit" class="button" value="Salvar informações"/></td><td><br/><input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></td></tr>
      </table>
    </fieldset>
  </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
