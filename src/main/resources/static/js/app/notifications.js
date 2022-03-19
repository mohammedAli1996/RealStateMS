$(document).ready(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/Notifications/all",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var table = document.getElementById("notificationsPanel");
            table.innerHTML = "" ; 
            for(var i = 0 ; i < data.length ; i ++){
                if(data[i].priority == "H"){
                    table.innerHTML += "<a href='"+data[i].path+"' class='dropdown-item preview-item'><div class='preview-thumbnail'><div class='preview-icon bg-danger'></div></div><div class='preview-item-content'><h6 class='preview-subject font-weight-normal'>"+data[i].msg+"</h6></div></a>";
                }
                else if(data[i].priority == "M"){
                    table.innerHTML += "<a href='"+data[i].path+"' class='dropdown-item preview-item'><div class='preview-thumbnail'><div class='preview-icon bg-warning'></div></div><div class='preview-item-content'><h6 class='preview-subject font-weight-normal'>"+data[i].msg+"</h6></div></a>";
                }
                else if(data[i].priority == "L"){
                    table.innerHTML += "<a href='"+data[i].path+"' class='dropdown-item preview-item'><div class='preview-thumbnail'><div class='preview-icon bg-info'></div></div><div class='preview-item-content'><h6 class='preview-subject font-weight-normal'>"+data[i].msg+"</h6></div></a>";
                }
            }
        },
        error: function (e) {
        }
    });
});




