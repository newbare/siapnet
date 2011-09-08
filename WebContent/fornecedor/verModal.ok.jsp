<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <style type="text/css">
    @import url("/almoxarifado/css/layout.css");
    @import url("/almoxarifado/js/jscookmenu/ThemePanel/theme.css");
    @import url("/almoxarifado/css/grid.css");
    @import url("/almoxarifado/css/clickmenu.css");
    @import url("/almoxarifado/css/modal-window.css");
    @import url("/almoxarifado/css/datepicker/ui.datepicker.css");
  </style>
</head>
<body>
  <div id="div_conteudo" style="width:490px">
    <fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Fornecedor</label> |</b></legend>
      <h3>Informações do cadastro do fornecedor:</h3>
      <fieldset>
        <legend>Dados do fornecedor</legend>
        <table>
          <tr>
            <td class="label" ><label for="fornecedor.nome">Nome:</label></td>
            <td>${fornecedor.nome}</td>
          </tr>
          <tr>
            <td class="label" ><label for="fornecedor.tipo">Tipo:</label></td>
            <td>${fornecedor.tipoFornecedor.nome}</td>
          </tr>
          <tr><td></td>
            <td>
              <input readonly="readonly" type="radio" name="fornecedor.pessoaJuridica" id="fornecedor.pessoaJuridica"  value="true" <c:if test="${fornecedor.pessoaJuridica==true}">checked</c:if> />
              <label for="fornecedor.pessoaJuridica">Pessoa Jurídica</label>
              <input readonly="readonly" type="radio" name="fornecedor.pessoaJuridica"  id="fornecedor.pessoaFisica" value="false" <c:if test="${fornecedor.pessoaJuridica==false}">checked</c:if> />
              <label for="fornecedor.pessoaFisica">Pessoa Física</label>
            </td>
          </tr>
          <tr>
            <td  class="label" ><label for="cpfCnpj">CPF/CNPJ:</label></td>
            <td>${fornecedor.cpfCnpj}
            </td>
          </tr>
          <tr>
            <td class="label" ><label for="inscricaoEstadual">Inscrição Estadual:</label></td>
            <td>${fornecedor.inscricaoEstadual}</td>
          </tr>
          <tr>
            <td class="label" ><label for="fornecedor.razaoSocial">Razao Social:</label></td>
            <td>${fornecedor.razaoSocial}</td>
          </tr>
          <tr>
            <td class="label" ><label for="telefone">Telefone:</label></td>
            <td>${fornecedor.telefone}</td>
          </tr>
          <tr>
            <td class="label" ><label for="fornecedor.observacao"  style="font-size: 10pt;" >Observação:</label></td>
            <td>${fornecedor.observacao}</td>
          </tr>
          <tr>
            <td class="label"  ><label for="fornecedor.grupos"  style="font-size: 10pt; vertical-align:top" >Grupos:</label></td>
            <td>
              <div class="scroll_checkboxes">
                <c:set var="i" value="0" />
                <c:forEach var="grupo" items="${grupos}">
                  <c:set var="checked" value="" />
                  <c:forEach var="group" items="${fornecedor.grupos}">
                    <c:if test="${grupo.id == group.id}">
                      <c:set var="checked" value="checked='checked'"/>
                    </c:if>
                  </c:forEach>
                  <input type="checkbox" value="${grupo.id}" ${checked} id="gruposArray[${i}]" name="gruposArray[${i}]"><label for="gruposArray[${i}]" >${grupo.nome}</label> <br/>
                  <c:set var="i" value="${i+1}"/>
                </c:forEach>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">
            <fieldset><legend>Contato</legend>
              <table>
                <tr>
                  <td  class="label" ><label for="fornecedor.contato" style="font-size: 10pt;">Contato:</label></td>
                  <td>${fornecedor.contato}</td>
                </tr>
                <tr>
                  <td class="label" ><label for="foneContato" style="font-size: 10pt;">Telefone:</label></td>
                  <td>${fornecedor.telefoneContato}</td>
                </tr>
              </table>
            </fieldset>
          </tr>
          <tr>
            <td colspan="2">
              <fieldset><legend>Endereço</legend>
                <table>
                  <tr>
                    <td class="label" ><label for="endereco.logradouro" style="font-size: 10pt;">Logradouro: </label></td>
                    <td>${fornecedor.endereco.logradouro}</td>
                  </tr>
                  <tr>
                    <td class="label" ><label for="endereco.numero" style="font-size: 10pt;">Número: </label></td>
                    <td class="label"> ${fornecedor.endereco.numero}&nbsp;&nbsp;
                      <label for="endereco.bairro" style="font-size: 10pt;">Bairro: </label>
                    ${fornecedor.endereco.bairro}</td>
                  </tr>
                  <tr>
                    <td class="label" ><label for="endereco.complemento" style="font-size: 10pt;">Complemento:</label></td>
                    <td>${fornecedor.endereco.complemento}</td>
                  </tr>
                  <tr>
                    <td class="label" ><label for="cep" style="font-size: 10pt;">CEP: </label></td>
                    <td>${fornecedor.endereco.cep}&nbsp;&nbsp;
                      <label for="endereco.uf" style="font-size: 10pt;">UF: </label>
                      ${fornecedor.endereco.municipio.uf.sigla}
                      <span id="cidades">
                        <label for="endereco.municipio" style="font-size: 10pt;">Município:</label>
                        ${fornecedor.endereco.municipio.nome}
                      </span>
                    </td>
                  </tr>
                </table>
              </fieldset>
            </td>
          </tr>
        </table>
      </fieldset>
    </fieldset>
  </div>
</body>