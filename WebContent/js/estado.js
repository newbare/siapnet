function loadMunicipios(id) {
	$("#cidades").load("endereco.loadMunicipios.logic", {"uf.id": id});
}

function loadAlmoxarifados(id) {
	$("#almoxarifados").load("departamento.loadAlmoxarifados.logic", {"unidade.id": id});
}