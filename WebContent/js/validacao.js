/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// ### FUNÇÕES DE VALIDAÇÃO ###
    
function validarData(obj){
    if (!(isDate(obj.value,"dd/MM/y")))
        alert("Data inválida.");
}

// verifica se data inicial é maior que data final
function checarDataInicialMaior(data_1, data_2){

    var Compara01 = parseInt(data_1.split("/")[2].toString() + data_1.split("/")[1].toString() + data_1.split("/")[0].toString());
    var Compara02 = parseInt(data_2.split("/")[2].toString() + data_2.split("/")[1].toString() + data_2.split("/")[0].toString());

    if (Compara01 > Compara02) {
        alert("Data inicial é maior que a data final.");
        return false;
    }

    return true;
}

//valida o CPF digitado
function validarCPF(Objcpf){
    var cpf = Objcpf.value;
    var erro = "";
    exp = /\.|\-/g;
    cpf = cpf.toString().replace( exp, "" );
    
    if (cpf.length == 11) {
	    if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || 
	   		cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || 
	   		cpf == "88888888888" || cpf == "99999999999") {  
	    	erro = "Número de CPF inválido!";  
	    }
	    var a = [];  
	    var b = new Number;  
	    var c = 11;  

	    for (i=0; i<11; i++){  
	    	a[i] = cpf.charAt(i);  
	    	if (i < 9) 
	    		b += (a[i] * --c);  
	    }

	    if ((x = b % 11) < 2) { 
	    	a[9] = 0;
	    } else { 
	    	a[9] = 11-x;
	    }  

	    b = 0;  
	    c = 11;  

	    for (y=0; y<10; y++) 
	    	b += (a[y] * c--);   
	    if ((x = b % 11) < 2) { 
	    	a[10] = 0; 
	    } else { 
	    	a[10] = 11-x; 
	    }  

	    if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])) {  
	    	erro = "Número de CPF inválido.";  
	    }
    } else {
    	erro = "Número de CPF inválido.";  
	}
    if (erro.length > 0) {  
    	alert(erro);  
    	return false;  
    }
    return true;      
}

//valida o CNPJ digitado
function validarCNPJ(ObjCnpj){
    var cnpj = ObjCnpj.value;
    var valida = new Array(6,5,4,3,2,9,8,7,6,5,4,3,2);
    var dig1= new Number;
    var dig2= new Number;

    exp = /\.|\-|\//g
    cnpj = cnpj.toString().replace( exp, "" );
    var digito = new Number(eval(cnpj.charAt(12)+cnpj.charAt(13)));

    for(i = 0; i<valida.length; i++){
        dig1 += (i>0? (cnpj.charAt(i-1)*valida[i]):0);
        dig2 += cnpj.charAt(i)*valida[i];
    }
    dig1 = (((dig1%11)<2)? 0:(11-(dig1%11)));
    dig2 = (((dig2%11)<2)? 0:(11-(dig2%11)));

    if(((dig1*10)+dig2) != digito) {
        alert('CNPJ invalido');
        return false;
    }
    return true;
}