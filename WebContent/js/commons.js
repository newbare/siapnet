function abrirJanela(source,height,width){
    var janela = window.open(source, "UNCISAL",  "width="+width+"px, height="+height+"px, left=150px, top=150px, toolbar=no, resizable=yes, location=no, menubar=no, scrollbars=yes");
}

function loadItemById(itemId, elementId) {
	if (itemId != null && itemId != "") {
		$("#" + elementId).load("item.verItem.logic", {
			"item.id" : itemId,
			"elemento" : elementId
		});
	}
}

function emitirRecibo(reciboId){
    var formato = "application/pdf";
    abrirJanela("/almoxarifado/geraRelatorio?relatorio=NotaSaidaRecibo&formato="+formato+"&SAIDA_ID="+reciboId, 600,800);
}

function aceitar(id) {
	if(confirm("Deseja adicionar os valores da requisição no estoque do almoxarifado?"))
		location = "/almoxarifado/requisicao.transferir.logic?requisicao.id=" + id;
}

function addItem() {
    if (document.getElementById("itens").value=="" || document.getElementById("codItem").value == "") {
        alert("Por favor digite o nome do matérial pretendido e escolha-o entre as opções listadas.");
        document.getElementById("itens").focus();
    } else if (document.getElementById("qtdRequisitada").value=="") {
        alert("Por favor informe a quantidade de item necessária.");
        document.getElementById("qtdRequisitada").focus();
    } else {
        itemId =  document.getElementById("codItem").value;
        qtdRequisitada = document.getElementById("qtdRequisitada").value;
        qtdRequisitada = qtdRequisitada.replace(".","");
        qtdRequisitada = qtdRequisitada.replace(",",".");
        

        $("#itensRequisicao").load("requisicao.addItem.logic", {"item.id": itemId, "qtdRequisitada": qtdRequisitada});
        document.getElementById("itens").value = "";
        document.getElementById("codItem").value = "";
        document.getElementById("qtdRequisitada").value="";
        document.getElementById("codItem").focus();
    }
}

function remItem(itemId) {
    $("#itensRequisicao").load("requisicao.remItem.logic", {"item.id": itemId});
}