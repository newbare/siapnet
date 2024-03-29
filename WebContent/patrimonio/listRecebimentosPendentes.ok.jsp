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
<jsp:include page="/header.jsp" flush="true" />
<div id="conteudo">
  <fieldset><legend><b>| <label class="lbTituloLegend">Movimentação de bens tombados pelo patrimônio</label> |</b></legend>
    <fieldset><legend>Transferência de Bens pendentes no Setor/Orgão : ${authUser.usuarioDepartamentoAtivo.departamento.nome} /${authUser.usuarioDepartamentoAtivo.departamento.unidade.orgao.sigla} </legend>
      <display:table id="transf" class="grid" name="listaTransfPatrimonio" pagesize="10" requestURI="patrimonio.listaTransferencias.logic">
        <display:column title="Setor Origem" property="setorOrigem.nome" maxWords="4" sortable="true" />
        <display:column title="Responsável Envio" property="responsavelEnvio.nome" maxWords="4" sortable="true" />
        <display:column title="Data saída" property="dataSaida" maxWords="4" sortable="true" />
        <display:column title="Estado" property="estadoDepreciacao.nome" maxWords="4" sortable="true" />
        <display:column title="Setor Destino" property="setorDestino.nome" maxWords="4" sortable="true" />
        <display:column title="Responsável recebimento" property="responsavelRecebimento.nome" maxWords="4" sortable="true" />
        <display:column title="Data recebimento" property="dataRecebimento" maxWords="4" sortable="true" />
        <display:column>
          <a	href="patrimonio.recebeNoSetor.logic?transferenciaPatrimonio.id=${transf.id}">Receber no setor</a>
        </display:column>
      </display:table>
    </fieldset>
  </fieldset>
</div>
<jsp:include page="/footer.jsp" flush="true" />