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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/header.jsp" flush="true" />
<div id="div_conteudo">
    <div class="div_principal">
        <form action="/almoxarifado/ajuda.suporteEnviar.logic" method="post" name="addform">
            <fieldset class="fieldset">
                <legend>
                    <b>| <label class="lbTituloLegend">Suporte Técnico</label> |</b>
                </legend>
                <table cellpadding="2" cellspacing="0" border="0" class="tbFrm">
                    <tr>
                        <td colspan="2" align="left" class="tdLinhaFrm">
                            <img src="/img/bg_barra_topo.jpg" height="1px" width="1px" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="left">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Órgão:
                        </td>
                        <td class="tbFieldFrm">
                            <select name="orgName" id="MensagemSuporteOrgaoId" class="comboBox textFieldWidth240">
                                <option value="">Selecione</option>
                                <option value="UNCISAL"  >UNCISAL</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Unidade:
                        </td>
                        <td class="tbFieldFrm">
                            <select name="unitName" id="unidade" class="comboBox textFieldWidth240">
                                <option value="">Selecione</option>
                                <option value="UNCISAL-SEDE"  >UNCISAL-SEDE</option>
                                <option value="UNCISAL-CDIM"  >UNCISAL-CDIM</option>
                                <option value="UNCISAL-CPML"  >UNCISAL-CPML</option>
                                <option value="UNCISAL-HEHA"  >UNCISAL-HEHA</option>
                                <option value="UNCISAL-HEPR"  >UNCISAL-HEPR</option>
                                <option value="UNCISAL-MESM"  >UNCISAL-MESM</option>
                                <option value="UNCISAL-SVO"   >UNCISAL-SVO</option>
                            </select>
                        </td>
                    </tr>
                    <tr>

                        <td class="tbTituloFrm">
                            Setor:
                        </td>
                        <td class="tbFieldFrm">
                            <input name="setor" type="text" class="textField textFieldWidth240" maxlength="100" value="" id="setor" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Nome:
                        </td>
                        <td class="tbFieldFrm">
                            <div class="input text"><input name="fromName" type="text" class="textField textFieldWidth240" maxlength="100" value="" id="fromName" /></div>                   </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Telefone:
                        </td>
                        <td class="tbFieldFrm">
                            <div class="input text"><input name="fromPhone" type="text" class="textField textFieldWidth120" maxlength="15" value="" id="MensagemSuporteTelefone" /></div>                    </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            E-mail:
                        </td>
                        <td class="tbFieldFrm">
                            <div class="input text"><input name="fromEmail" type="text" class="textField textFieldWidth120" maxlength="100" value="" id="fromEmail" /></div>                    </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Tipo da Mensagem:
                        </td>

                        <td class="tbFieldFrm">
                            <select name="typeMessage" id="MensagemSuporteTipoMensagemId" class="comboBox textFieldWidth120">
                                <option value="">Selecione</option>
                                <option value="DUVIDAS"  >
                                    Dúvidas                                </option>
                                <option value="RECLAMACOES"  >
                                    Reclamações                                </option>
                                <option value="SUGESTOES"  >
                                    Sugestões                                </option>
                                <option value="SOLICITACOES"  >
                                    Solicitações                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Assunto:
                        </td>

                        <td class="tbFieldFrm">
                            <select name="subject" id="assunto" class="comboBox textFieldWidth240">
                                <option value="">Selecione</option>
                                <option value="Login"  > Login             </option>
                                <option value="Cadastro de material"  >  Cadastro de material  </option>
                                <option value="Requisicao"  >    Requisição        </option>
                                <option value="Insercao de novo item no catalogo"  >   Inserção de novo item no catálogo  </option>
                                <option value="Entrada de Itens"  >  Entrada de Itens </option>
                                <option value="Outros assuntos"  > Outros assuntos </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            Mensagem:
                        </td>
                        <td class="tbFieldFrm">
                            <textarea name="content" label="" class="textArea textFieldWidth240" rows="5" id="conteudo" ></textarea>                   </td>
                    </tr>
                    <tr>
                        <td  colspan="2" class="tbFieldFrm">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="tbTituloFrm">
                            &nbsp;
                        </td>
                        <td class="tbFieldFrm">

                            <input type="submit" id="btnEnviar" name="btnEnviar" value="Enviar Mensagem" />
                            <input type="button" id="btnVoltar" name="btnVoltar" value="Voltar" onclick="history.back(-1)" />
                        </td>
                    </tr>
                    <tr>
                        <td  colspan="2" class="tbFieldFrm">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Contato:</b>
                        </td>
                    </tr>

                    <tr>
                        <td class="tbTituloFrm">
                            GTIN (CETIS) - UNCISAL:
                        </td>
                        <td class="tbFieldFrm">
                            3315-6775
                        </td>
                    </tr>

                    <tr>
                        <td class="tbTituloFrm">
                            ITEC:
                        </td>
                        <td class="tbFieldFrm">
                            almoxarifado@itec.al.gov.br
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
    <br />
</div>
<jsp:include page="/footer.jsp" flush="true" />