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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>SIAPNET - Almoxarifado e Patrimônio</title>
    <style type="text/css">
        @import url("/almoxarifado/css/layout.css");
        @import url("/almoxarifado/js/jscookmenu/ThemePanel/theme.css");
        @import url("/almoxarifado/css/grid.css");
        @import url("/almoxarifado/css/modal-window.css");
        @import url("/almoxarifado/css/datepicker/ui.datepicker.css");
    </style>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/estado.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/relatorio.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/validacao.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/modal-window.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jquery.meiomask.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/ui.datepicker.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/ui.datepicker-pt-BR.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jscookmenu/JSCookMenu.js"></script>
    <script type="text/javascript" charset="utf-8" src="/almoxarifado/js/jscookmenu/ThemePanel/theme.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("input:text").setMask();

            $('input:text').keypress(function(e) {

                if(e.keyCode == 13) {
                    //alert('Enter key was pressed.');
                    var inputs = $(this).parents("form").eq(0).find(":input");
                    var idx = inputs.index(this);
                    if (idx == inputs.length - 1) {
                        inputs[0].select()
                    } else {
                        inputs[idx + 1].focus(); // handles submit buttons
                        inputs[idx + 1].select();
                    }
                    return false;
                }
            });

            $('select').keypress(function(e) {

                if(e.keyCode == 13) {
                    //alert('Enter key was pressed.');
                    var inputs = $(this).parents("form").eq(0).find(":input");
                    var idx = inputs.index(this);
                    if (idx == inputs.length - 1) {
                        inputs[0].select()
                    } else {
                        inputs[idx + 1].focus(); // handles submit buttons
                        inputs[idx + 1].select();
                    }
                    return false;
                }
            });
        });
        
        function confirmDelete(delUrl) {
            if (confirm("Deseja realmente apagar este registro?")) {
                document.location = delUrl;
            }
        }

        var openMyModal = function(source,height,width)
        {
            modalWindow.windowId = "myModal";
            modalWindow.width = width;
            modalWindow.height = height;
            modalWindow.content = "<iframe width='"+width+"' height='"+height+"' frameborder='0' scrolling='auto' allowtransparency='true' src='" + source + "'>&lt/iframe>";
            modalWindow.open();
        };

        function fechar(){
            parent.document.location.reload();
            window.close();
        }

        function fechar(div,action){
            //parent.document.getById(div).load(action);
            parent.document.location.reload();
            window.close();
        }
    </script>
</head>
<body>
<!--END top-->
<jsp:include page="/mensagensErro.jsp" flush="true" />