function loadParamsRelatorio(id) {
	$("#parametrosRelatorio").load("relatorio.loadParamsRelatorio.logic", {"relatorio.id": id});
}