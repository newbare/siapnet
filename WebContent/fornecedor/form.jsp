
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    function alternaPessoa(tPessoa) {
        var pessoa = document.getElementById("pessoa");
        pessoa.innerHTML = "<input type='text' name='fornecedor.cpfCnpj' id='cpfCnpj' alt='"+ tPessoa +"' enabled='enabled' value='' size='20' />";
    
        $("#cpfCnpj").setMask();
    }
</script>

<c:choose>
	<c:when test="${fornecedor.id==null}">
		<c:set var="msg_cabecalho_formulario" value="Preencha os dados do formulário para cadastrar um novo fornecedor:" />
		<c:set var="alt_cpfCnpj" value="cnpj" />
	</c:when>
	<c:otherwise>
		<c:set var="msg_cabecalho_formulario" value="Edite os dados do formulário que deseja atualizar o cadastro do fornecedor:" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${fornecedor.pessoaJuridica==true}"><c:set var="alt_cpfCnpj" value="cnpj" /></c:when>
	<c:when test="${fornecedor.pessoaJuridica==false}"><c:set var="alt_cpfCnpj" value="cpf" /></c:when>
</c:choose>
<c:choose>
	<c:when test="${fornecedor.pessoaJuridica == null}">
		<c:set var="pJuridicaAtivo" value="disabled='disabled'"/>
	</c:when>
	<c:otherwise>
		<c:set var="pJuridicaAtivo" value=""/>
	</c:otherwise>
</c:choose>	

<fieldset><legend><b>| <label class="lbTituloLegend">Cadastro de Fornecedor</label> |</b></legend>
    <form method="post" action="fornecedor.gravar.logic">
        <input type="hidden" name="modal" value="${modal}" />
        <input type="hidden" name="fornecedor.id" value="${fornecedor.id}" />
		<h3>${msg_cabecalho_formulario}</h3>
        <fieldset>
            <legend>Dados do fornecedor</legend>
            <table>
                <tr>
                    <td><label for="fornecedor.nome">Nome:</label></td>
                    <td><input type="text" name="fornecedor.nome" id="fornecedor" onblur="this.value=this.value.toUpperCase()"  value="${fornecedor.nome}" size="50" maxlength="100"/></td>
                </tr>
                <tr><td></td>
                    <td>
                <input onclick="alternaPessoa('cnpj')" type="radio" class="radio" name="fornecedor.pessoaJuridica" id="fornecedor.pessoaJuridica"  value="true" <c:if test="${fornecedor.pessoaJuridica==true}">checked</c:if> />
                <label for="fornecedor.pessoaJuridica">Pessoa Jurídica</label>
                <input onclick="alternaPessoa('cpf')" type="radio" class="radio" name="fornecedor.pessoaJuridica"  id="fornecedor.pessoaFisica" value="false" <c:if test="${fornecedor.pessoaJuridica==false}">checked</c:if> />
                <label for="fornecedor.pessoaFisica">Pessoa Física</label>
                </td>
                </tr>
                <tr>
                    <td><label for="cpfCnpj">CPF/CNPJ:</label></td>
                    <td>
                        <div id="pessoa">
	                        <input type="text" name="fornecedor.cpfCnpj" id="cpfCnpj" alt="${alt_cpfCnpj}" value="${fornecedor.cpfCnpj}" onblur="validarCNPJ(this)" size="20" maxlength="20" ${pJuridicaAtivo}/>
                            <a href="http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao.asp" target="_BLANK">Consulta CNPJ</a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><label for="inscricaoEstadual">Inscrição Estadual:</label></td>
                    <td><input type="text" name="fornecedor.inscricaoEstadual" id="inscricaoEstadual"
                               value="${fornecedor.inscricaoEstadual}" size="20" maxlength="18" alt="ie"/>
                    <a href="http://sintegra.sefaz.al.gov.br/consultapublica/consulta_empresa_pesquisa.asp" target="_BLANK">Consulta IE em Alagoas</a>
                    </td>
                </tr>
                <tr>
                    <td><label for="fornecedor.razaoSocial">Razão Social:</label></td>
                    <td><input type="text" name="fornecedor.razaoSocial" id="fornecedor.razaoSocial" onblur="this.value=this.value.toUpperCase()" 
                               value="${fornecedor.razaoSocial}" size="50" maxlength="100" /></td>
                </tr>
                <tr>
                    <td><label for="telefone">Telefone:</label></td>
                    <td><input type="text" name="fornecedor.telefone" id="telefone"  alt="phone"
                               value="${fornecedor.telefone}" size="15" maxlength="14"/></td>
                </tr>
                <tr>
                    <td><label for="fornecedor.observacao"  style="font-size: 10pt;" >Observação:</label></td>
                    <td><input type="text" name="fornecedor.observacao" id="fornecedor.observacao" value="${fornecedor.observacao}" onblur="this.value=this.value.toUpperCase()"  size="50" maxlength="255" /></td>
                </tr>
                <tr>
                    <td class="label"><label for="fornecedor.tipoFornecedor"  style="font-size: 10pt; vertical-align:top" >Tipo de Fornecedor :</label></td>
                    <td>
                        <select name="fornecedor.tipoFornecedor.id" id="fornecedor.tipoFornecedor" size="6" style="width: 250px;" >
                            <option value="1" <c:if test="${fornecedor.tipoFornecedor.id==1}" >selected</c:if> >ASSISTENCIA TÉCNICA</option>
                            <option value="2" <c:if test="${fornecedor.tipoFornecedor.id==2}" >selected</c:if> >SERVIÇOS</option>
                            <option value="3" <c:if test="${fornecedor.tipoFornecedor.id==3}" >selected</c:if> >AQUISIÇÂO</option>
                            <option value="4" <c:if test="${fornecedor.tipoFornecedor.id==4}" >selected</c:if> >AQUISIÇÂO E SERVIÇOS</option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td class="label"><label for="fornecedor.grupos"  style="font-size: 10pt; vertical-align:top" >Grupos de Fornecimento:</label></td>
                    <td>
                        <select name="gruposArray" id="fornecedor.grupos" size="6" style="width: 450px;" multiple="multiple">
                            <c:forEach var="grupo" items="${grupos}">
                                <c:set var="selected" value="" />
                                <c:forEach var="group" items="${fornecedor.grupos}">
                                    <c:if test="${grupo.id == group.id}">
                                        <c:set var="selected" value="selected='selected'"/>
                                    </c:if>
                                </c:forEach>
                                <option value="${grupo.id}" ${selected}>${grupo.nome}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <fieldset><legend>Contato</legend>
                            <table>
                                <tr>
                                    <td class="label"><label for="fornecedor.contato" style="font-size: 10pt;">Contato:</label></td>
                                    <td>
                                        <input type="text" name="fornecedor.contato"  id="fornecedor.contato" onblur="this.value=this.value.toUpperCase()" value="${fornecedor.contato}" style="font-size: 10pt;"  size="50" maxlength="50"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><label for="foneContato" style="font-size: 10pt;">Telefone:</label></td>
                                    <td>
                                        <input type="text" name="fornecedor.telefoneContato" id="foneContato"  alt="phone" value="${fornecedor.telefoneContato}" style="font-size: 10pt;" size="15" maxlength="14" />
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                </tr>
                <tr>
                    <td colspan="2">
                        <fieldset><legend>Endereço</legend>
                            <table>
                                <tr>
                                    <td class="label"><label for="endereco.logradouro" style="font-size: 10pt;">Logradouro:</label></td>
                                    <td><input type="text" name="endereco.logradouro" id="endereco.logradouro" value="${fornecedor.endereco.logradouro}"  onblur="this.value=this.value.toUpperCase()"  size="54" maxlength="150" /></td>
                                </tr>
                                <tr>
                                    <td class="label"><label for="endereco.numero" style="font-size: 10pt;">Número:</label></td>
                                    <td>
                                        <input type="text" name="endereco.numero" id="endereco.numero" value="${fornecedor.endereco.numero}"  onblur="this.value=this.value.toUpperCase()"  size="4" maxlength="5" />
                                        <label for="endereco.bairro" style="font-size: 10pt;">Bairro:</label>
                                        <input type="text" name="endereco.bairro" id="endereco.bairro"  value="${fornecedor.endereco.bairro}" onblur="this.value=this.value.toUpperCase()"  size="38" maxlength="50" /></td>
                                </tr>
                                <tr>
                                    <td class="label"><label for="endereco.complemento" style="font-size: 10pt;">Complemento:</label></td>
                                    <td>
                                        <input type="text" name="endereco.complemento" id="endereco.complemento" value="${fornecedor.endereco.complemento}"  onblur="this.value=this.value.toUpperCase()"  size="50" maxlength="150" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><label for="cep" style="font-size: 10pt;">CEP:</label></td>
                                    <td><input type="text" name="endereco.cep" id="cep" alt="cep" value="${fornecedor.endereco.cep}" size="10" maxlength="10" />
                                        <label for="endereco.uf" style="font-size: 10pt;">UF:</label>
                                        <select
                                            name="uf.id" id="endereco.uf" onchange="loadMunicipios(this.value)"
                                            style="width: 50px;">
                                            <c:forEach var="uf" items="${ufs}">
                                                <c:choose>
                                                    <c:when test="${uf.id == fornecedor.endereco.municipio.uf.id}">
                                                        <option value="${uf.id}" selected="selected">${uf.sigla}</option>
                                                    </c:when>
                                                    <c:when test="${uf.id == 27 && fornecedor.endereco.municipio == null}">
                                                        <option value="${uf.id}" selected="selected">${uf.sigla}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${uf.id}">${uf.sigla}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <span id="cidades">
                                            <label for="endereco.municipio" style="font-size: 10pt;">Município:</label>
                                            <select id="endereco.municipio" name="endereco.municipio.id">
                                                <c:forEach var="municipio" items="${municipios}">
                                                    <c:choose>
                                                        <c:when test="${municipio.id == fornecedor.endereco.municipio.id}">
                                                            <option value="${municipio.id}" selected="selected">${municipio.nome}</option></c:when>
                                                        <c:otherwise><option value="${municipio.id}">${municipio.nome}</option></c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </td>
                </tr>
            </table>
        </fieldset>
        <p align="center"><input type="button" class="button" value="Salvar informações" onclick="salvar()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           </p>
    </form>
</fieldset>
