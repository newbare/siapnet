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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="pt_BR" scope="application" />
<jsp:include page="/header.jsp" flush="true" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#itensPrevalecentes").autocomplete("entradaItens.loadItens.logic", {
			minChars : 3,
			formatItem : function(row) {
				return row[1] + " - " + row[0];
			}
		}).setOptions( {
			max : 50
		});
		$("#itensPrevalecentes").result(function(event, data, formatted) {
			itemId = data[1];
			document.getElementById("codItemPrevalecente").value = itemId;
		});
		$("#itensCondenados").autocomplete("entradaItens.loadItens.logic", {
			minChars : 3,
			formatItem : function(row) {
				return row[1] + " - " + row[0];
			}
		}).setOptions( {
			max : 50
		});
		$("#itensCondenados").result(function(event, data, formatted) {
			itemId = data[1];
			document.getElementById("codItemCondenado").value = itemId;
		});
		$("input:text").setMask();
	});
</script>
<div id="div_conteudo">
<h2>Consolidação de itens</h2>
<form method="post" action="item.consolidar.logic" accept-charset="utf-8">
	<fieldset>
		<legend><b>| <label class="lbTituloLegend">Item que vai prevalecer</label> |</b></legend>
		<table>
			<tr>
				<td>
					<label for="codItemPrevalecente">Código: </label>
					<input name="codItemPrevalecente" alt="numeric" type="text" id="codItemPrevalecente" size="5" onblur="loadItemById(this.value, 'itensPrevalecentes')" />
				</td>
				<td>
					<label for="itensPrevalecentes">Item: </label>
					<input type="text" id="itensPrevalecentes" title="Digite o nome do item que procura." size="50" />
					<span id="itemPrevalecente" style="display: none"></span>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
	<legend>
		<b>| <label class="lbTituloLegend">Item que vai ser substituído</label> |</b></legend>
		<table>
			<tr>
				<td>
					<label for="codItemCondenado">Código: </label>
					<input alt="numeric" type="text" name="codItemCondenado" id="codItemCondenado" size="5" onblur="loadItemById(this.value, 'itensCondenados')" />
				</td>
				<td>
					<label for="itensCondenados">Item: </label>
					<input type="text" id="itensCondenados" title="Digite o nome do item que procura." size="50" />
					<span id="itemCondenado" style="display: none"></span>
				</td>
			</tr>
		</table>
	</fieldset>
	<p align="center">
		<input type="submit" class="button" value="Salvar informações" />
	</p>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />