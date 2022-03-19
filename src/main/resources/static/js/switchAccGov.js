var indivisual , government , transField;
var v1,v2,v3,v4,v5,v6;
$(document).ready(function() {
    indivisual = document.getElementById('indivisual');
    government = document.getElementById('government');
    transField = document.getElementById('transField');
    v6 = document.getElementById('v6');
    v5 = document.getElementById('v5');
    v4 = document.getElementById('v4');
    v3 = document.getElementById('v3');
    v2 = document.getElementById('v2');
    v1 = document.getElementById('v1');
});

function indClick(){
    indivisual.disabled=true ; 
    government.disabled=false; 
    transField.innerHTML="Emirates ID *";
    v1.hidden=false ;
    v2.hidden=false ;
    v3.hidden=false ;
    v4.hidden=false ;
    v5.hidden=false ;
    v6.hidden=false ;
}

function govClick(){
    indivisual.disabled=false ; 
    government.disabled=true; 
    transField.innerHTML="Government Username *";
    v1.hidden=true ;
    v2.hidden=true ;
    v3.hidden=true ;
    v4.hidden=true ;
    v5.hidden=true ;
    v6.hidden=true ;
}