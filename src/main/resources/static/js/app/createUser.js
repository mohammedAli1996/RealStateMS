


function createUser(){

    request = {} ;

    request["username"] = document.getElementById("username").value ; 
    request["password"] = document.getElementById("password").value ; 

    $.ajax({
        type: "POST",
        contentType: "application/json",
        async: false,
        url: "/users/addUser",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            document.getElementById("redirect").click();
        },
        error: function (e) {
        }
    });
}