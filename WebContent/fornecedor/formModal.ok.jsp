<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/headerModal.jsp" flush="true" />
<script>
    function salvar(){
        document.forms[0].submit();
        window.opener.atualizarFornecedores();
    }
    
    function fecha(){
        window.opener.atualizarFornecedores();
        fechar();
    }
</script>
        <div id="div_conteudo">
<jsp:include page="/fornecedor/form.jsp" flush="true" />
</div>
        <p><input type="button" class="button"  value="Fechar" onclick="fecha()"/></p>
</div>