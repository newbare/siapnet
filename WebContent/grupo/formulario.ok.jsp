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

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/header.jsp" flush="true" />
<div id="div_conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Grupo</label> |</b></legend>
    <c:choose>
      <c:when test="${grupo.id==null}">
        <h3>Preencha os dados do formulário para cadastrar um novo grupo:</h3>
      </c:when>
      <c:otherwise>
        <h3>Edite os dados do formulário que deseja atualizar o cadastro do grupo:</h3>
      </c:otherwise>
    </c:choose>
    <form method="post" action="grupo.gravar.logic" accept-charset="utf-8">
      <input type="hidden" name="grupo.id" value="${grupo.id}" />
      <table>
        <tr>
          <td>Nome:</td>
          <td><input type="text" name="grupo.nome" value="${grupo.nome}"  onblur="this.value=this.value.toUpperCase()" 
                     size="50" maxlength="100" /></td>
        </tr>
         <tr>
          <td>Código SIAFEM:</td>
          <td><input type="text" name="grupo.codigoSIAFEM" value="${grupo.codigoSIAFEM}"
                     size="10" maxlength="15" /></td>
        </tr>
        <tr>
          <td>Descritivo:</td>
          <td><textarea name="grupo.descritivo" onblur="this.value=this.value.toUpperCase()" 
                     cols="80" rows="4" >${grupo.descritivo}</textarea></td>
        </tr>
        <tr>
          <td colspan="2">
            <fieldset><legend>Grupo de Bens Permanentes</legend>
              <table>
                <tr>
                  <td>Grupo de Materiais Permanentes ?</td>
                  <td>
                    <c:choose>
                      <c:when test="${grupo.materialPermanente == true}">
                        <input type="checkbox" name="grupo.materialPermanente" id="bem" value="true" checked="checked"/>
                      </c:when>
                      <c:otherwise>
                        <input type="checkbox" class="check" name="grupo.materialPermanente" id="bem" value="true"/>
                      </c:otherwise>
                  </c:choose></td>
                </tr>
                <tr>
                  <td>Taxa de Depreciação Anual:</td>
                  <td><input type="text" name="grupo.taxaDepreciacao" alt="numeric"
                             value="${grupo.taxaDepreciacao}" size="4" maxlength="6" /> % </td>
                </tr>
                <tr>
                  <td>Tempo de Vida Útil do Bem:</td>
                  <td><input type="text" name="grupo.vidaUtil" alt="numeric"
                             value="${grupo.vidaUtil}" size="4" maxlength="6" /> anos </td>
                </tr>
              </table>
            </fieldset>
          </td>
        </tr>
      </table>
      <p align="center"><input type="submit" class="button" value="Salvar informações" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="voltar" class="button" name="voltar" value="Voltar" onclick="history.back(-1)" /></p>
    </form>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />