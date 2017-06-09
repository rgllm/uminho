# getCampoStandard( campo ) -> devolve a apenas a informação útil de uma string com um campo ( incluindo tags ) 
# usada para obter as informações delimitadas por > e <
function getCampoStandard( campo ){
    split( campo , arr , /[><]/ );
    return arr[3];
}
# getMonetario( campo ) -> devolve a apenas a informação útil de uma string com o campo 8 ou 9 ( incluindo tags ) 
# usada para obter valores numericos que vão ser usados para cálculos
function getMonetario( campo ){
    split(campo,arr,/[><]/);
    sub(",",".",arr[3]);
    return arr[3];
}
# getDia( campo ) -> devolve a apenas a informação útil de uma string com o campo 2 de uma transacao ( incluindo tags ) 
# usada para obter uma string com a data no formato DD-MM 
function getDia( campo2 ){
    split(campo2,arr,">");
    split(arr[2],arr,"<");
    split(arr[1],arr,"-");
    if(arr[1]!="null")
    #          dia - mes
        dia=arr[1]"-"arr[2];
    else dia="nao especificado";
    return dia;
}
function getDataSaida( campo5 ){return getCampoStandard( campo5 );}
function getSaida( campo7 ){return getCampoStandard( campo7 );}
# getTipo -> Portagens ou Parques de estacionamento
function getTipo( campo12 ){return getCampoStandard( campo12 );}
function getCartao( campo14 ){return getCampoStandard( campo14 );}
# getOperador -> "nome" do parque de estacionamento
function getOperador( campo11 ){return getCampoStandard( campo11 );}
function getImportancia( campo8 ){return getMonetario( campo8 );}
function getDesconto( campo9 ){return getMonetario( campo9 );}