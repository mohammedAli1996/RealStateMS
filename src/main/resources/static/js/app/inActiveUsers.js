$(document).ready(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/users/allNonActive",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var table = document.getElementById("usersTable");
            table.innerHTML = "" ; 
            for(var i = 0 ; i < data.length ; i ++){
                table.innerHTML += "<tr><td>"+data[i].userName+"</td> <td><label class='badge badge-danger'>Inactive</label></td><td><a href='/users/active/"+data[i].id+"'>Activate</a> </td></tr>";                
            }
        },
        error: function (e) {
        }
    });
});

