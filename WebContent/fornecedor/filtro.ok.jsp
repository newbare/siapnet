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
        $("input:text").setMask();
        buscarPaginacao();
    });
    function buscarPaginacao(){
        var url= window.location.href ;
        url = url.replace("fornecedor.filtro.logic","fornecedor.buscar.logic");
        var k=url.search('-p=');
        if( k!=-1)
            $("#fornecedores").load(url);
    }
    function buscar() {
        var nome = document.getElementById("fornecedor.nome").value;
        var pj = document.getElementById("fornecedor.pessoaJuridica").value;
        var cpf = document.getElementById("fornecedor.cpfCnpj").value;
        var ie = document.getElementById("fornecedor.inscricaoEstadual").value;
        var razao = document.getElementById("fornecedor.razaoSocial").value;
        $("#fornecedores").load("fornecedor.buscar.logic", {
            "fornecedor.nome" : nome ,
            "fornecedor.pessoaJuridica": pj ,
            "fornecedor.cpfCnpj" : cpf ,
            "fornecedor.inscricaoEstadual" :ie ,
            "fornecedor.razaoSocial":razao});
    }
</script>  
<div id="conteudo">
  <form method="post" action="fornecedor.buscar.logic">
    <fieldset><legend><b>| <label class="lbTituloLegend">Busca de Fornecedor</label> |</b></legend>
      <h3>Preencha os dados do formulário para definir o critério da busca de fornecedor:</h3>
      <table>
        <tr>
          <td><label for="fornecedor.nome">Nome:</label></td>
          <td><input type="text" name="fornecedor.nome" id="fornecedor.nome"
                     value="${fornecedor.nome}" size="50" /></td>
        </tr>
        <tr><td></td>
          <td>
            <input type="radio" name="fornecedor.pessoaJuridica" id="fornecedor.pessoaJuridica"  value="true"
                   <c:if test="${fornecedor.pessoaJuridica==true}">checked</c:if> />
            <label for="fornecedor.pessoaJuridica">Pessoa Jurídica</label>
            <input type="radio" name="fornecedor.pessoaJuridica"  id="fornecedor.pessoaFisica" value="false"
                   <c:if test="${fornecedor.pessoaJuridica==false}">checked</c:if> />
            <label for="fornecedor.pessoaFisica">Pessoa Física</label>
          </td>
        </tr>
        <tr>
          <td><label for="fornecedor.cpfCnpj">CPF/CNPJ:</label></td>
          <td><input type="text" alt="cnpj" name="fornecedor.cpfCnpj" id="fornecedor.cpfCnpj"
                     value="${fornecedor.cpfCnpj}"  size="20" /></td>
        </tr>
        <tr>
          <td><label for="fornecedor.inscricaoEstadual">Inscrição Estadual:</label></td>
          <td><input type="text" name="fornecedor.inscricaoEstadual" id="fornecedor.inscricaoEstadual"
                     value="${fornecedor.inscricaoEstadual}"  size="20" /></td>
        </tr>
        <tr>
          <td><label for="fornecedor.razaoSocial">Razao Social:</label></td>
          <td><input type="text" name="fornecedor.razaoSocial" id="fornecedor.razaoSocial"
                     value="${fornecedor.razaoSocial}" size="50" /></td>
        </tr>
        <tr>
          <td colspan="2" align="center">
          <input type="button" class="button" onclick="buscar()"  value="Procurar"/></td>
        </tr>
      </table>
    </fieldset>
    <fieldset>
      <div id="fornecedores"></div>
    </fieldset>
  </form>
</div>
<jsp:include page="/footer.jsp" flush="true" />