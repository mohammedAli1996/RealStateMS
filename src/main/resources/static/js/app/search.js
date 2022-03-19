function saveProperty() {

    var filter = {}
    filter["ttt"] = document.getElementById("propertyTypeField").value;
    filter["leaseUnit"] = document.getElementById("leaseUnit").value;
    filter["ownerName"] = document.getElementById("ownerName").value;
    filter["sewegNum"] = document.getElementById("sewegNum").value;
    filter["waterElec"] = document.getElementById("waterElec").value;
    document.getElementById("resultBody").innerHTML = "";
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/property/find",
        data: JSON.stringify(filter),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            for(var i = 0 ; i < data.length ; i ++){
                document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].leaseUnit+"</th>  <th>"+data[i].area+"</th>  <th>"+data[i].rooms+"</th>  <th>"+data[i].parking+"</th>  <th>"+data[i].premisesNum+"</th> <th>"+data[i].status+"</th>   <th><a href='/property/edit/"+data[i].id+"'>View</a></th> <th><a href='/property/delete/"+data[i].id+"'>Archive</a></th> </tr>"
            }
        },
        error: function (e) {
        }
    });

}