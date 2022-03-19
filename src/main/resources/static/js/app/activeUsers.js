$(document).ready(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/users/allActive",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var table = document.getElementById("usersTable");
            table.innerHTML = "" ; 
            for(var i = 0 ; i < data.length ; i ++){
                table.innerHTML += "<tr><td>"+data[i].userName+"</td> <td><label class='badge badge-success'>Active</label></td><td><a href='/users/deactive/"+data[i].id+"'>Deactivate</a></td></tr>";                
            }
        },
        error: function (e) {
        }
    });
});

