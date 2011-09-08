<%@page contentType="text/html" pageEncoding="UTF-8" %>
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

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="shortcut icon" href="favicon.ico" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="expires" content="-1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>SIAPNET - Almoxarifado & Patrimônio</title>
        <style type="text/css">
            @import url("/almoxarifado/css/layout.css");
            @import url("/almoxarifado/css/menu.css");
            @import url("/almoxarifado/css/grid.css");
            @import url("/almoxarifado/css/redmond/jquery-ui-1.8.9.custom.css");
        </style>       
        <!--[if lte IE 6]><style>img, div, input { behavior: url("/almoxarifado/css/iepngfix.htc") }</style><![endif]-->        
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery-1.4.4.min.js"></script>        
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery-ui-1.8.9.custom.min.js"></script>
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery.ui.datepicker-pt-BR.js"></script>        
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery.meiomask.js"></script>
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/commons.js"></script>
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/validacao.js"></script>
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/estado.js"></script>
        <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/relatorio.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("input:text").setMask();
                
            	$('input[type="submit"]').click(
            			function(event){
            				$('form').submit();
            				$('input[type="submit"]').attr("disabled", true);
            			}		
            		);                
            });

            function confirmDelete(delUrl) {
                if (confirm("Deseja realmente apagar este registro?")) {
                    document.location = delUrl;
                }
            }
        </script>        
    </head>
    <body>
        <!--top-->
        <div id="topo">
            <div class="logo">
                <img src="images/brasao_al.gif" height="55px" alt="Brasão do Estado de Alagoas" style="vertical-align:middle" />
                <br/>
                <strong style="font-size:16pt">Governo do Estado de Alagoas</strong>
            </div>
            <div>
            	<h1>SIAPNET - Almoxarifado e Patrimônio<br/>
                    <span class="tituloSistema">
                        Sistema de Informação de Almoxarifado e Patrimônio&nbsp;
            		</span>
            	</h1>
           	</div>
        </div>
    <c:choose>
        <c:when test="${authUser!=null && authUser.usuarioDepartamentoAtivo != null}">
            <jsp:include page="menu.jsp" flush="true" />
            <div class="div_ola_usuario">
                Olá&nbsp;${authUser.nome}&nbsp;|&nbsp;Orgão/Setor: ${authUser.usuarioDepartamentoAtivo.departamento.unidade.orgao.sigla} / ${authUser.usuarioDepartamentoAtivo.departamento.sigla}&nbsp;
            </div>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
    <div style="background-color:#edf4fc">
        <jsp:include page="/mensagensErro.jsp" flush="true" />
    </div>
    <!--END top-->