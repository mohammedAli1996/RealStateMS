$(document).ready(function () {
    document.getElementById("resultBody").innerHTML = "";
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/contract/all/res",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            for(var i = 0 ; i < data.length ; i ++){
                if(data[i].status == "Rented"){
                    document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].city+"</th>  <th>"+data[i].district+"</th>  <th>"+removeTime(data[i].contractStartDate)+"</th>  <th>"+removeTime(data[i].contractEndDate)+"</th> <th>"+data[i].status+"</th> <th><a href='/cnclContract/res/"+data[i].id+"'>Cancel</a></th> <th><a href='/contract/editres/"+data[i].id+"'>View</a></th></tr>"
                }else{
                    document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].city+"</th>  <th>"+data[i].district+"</th>  <th>"+removeTime(data[i].contractStartDate)+"</th>  <th>"+removeTime(data[i].contractEndDate)+"</th> <th>"+data[i].status+"</th><th></th> <th><a href='/contract/editres/"+data[i].id+"'>View</a></th></tr>"
                }
            }
        },
        error: function (e) {
        }
    });
});






function removeTime(date) {
    var start = new Date(date);
    console.log( start.getFullYear());
    console.log( start.getMonth());
    console.log( start.getDate());
    var month = start.getMonth() + 1 ; 
    return start.getDate() + "/" + month + "/" +  start.getFullYear() ; 
  }