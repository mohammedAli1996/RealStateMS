$(document).ready(function () {
    document.getElementById("resultBody").innerHTML = "";
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/contract/all/comm",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            for(var i = 0 ; i < data.length ; i ++){
                if(data[i].status == "Rented"){
                    document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].city+"</th>  <th>"+data[i].district+"</th>  <th>"+data[i].contractStartDate+"</th>  <th>"+data[i].contractEndDate+"</th> <th>"+data[i].status+"</th> <th><a href='/cnclContract/comm/"+data[i].id+"'>Cancel</a></th> <th><a href='/contract/editcomm/"+data[i].id+"'>View</a></th></tr>"
                }else{
                    document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].city+"</th>  <th>"+data[i].district+"</th>  <th>"+data[i].contractStartDate+"</th>  <th>"+data[i].contractEndDate+"</th> <th>"+data[i].status+"</th><th></th> <th><a href='/contract/editcomm/"+data[i].id+"'>View</a></th></tr>"
                }
                
            }
        },
        error: function (e) {
        }
    });
});



