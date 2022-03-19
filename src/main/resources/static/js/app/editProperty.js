var propertyId;


$(document).ready(function () {
    document.getElementById("locationTab").click() ;  
    document.getElementById("ownerD").click() ;  
    propertyId = document.getElementById("pId").value;
    getAttachments("PropertyAttachment" , propertyId , "attTableBody");
    getPropertyData();
    $(document).on('submit', '#attachmentsTypeFileUploadForm', function () {
        submitForm("attachmentsTypeFileUploadForm",propertyId,"PropertyAttachment","attTableBody");
        return false;
    });
});


function getPropertyData(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        async: false,
        url: "/property/get/"+propertyId,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            document.getElementById("licensesNumber").value = data.licensesNumber;
            document.getElementById("companyName").value = data.companyName;
            document.getElementById("mobileNumber").value = data.mobileNumber;
            document.getElementById("certNumber").value = data.certNumber;
            document.getElementById("city").value = data.city;
            document.getElementById("district").value = data.district;
            document.getElementById("type").value = data.type;
            document.getElementById("nameDesc").value = data.nameDesc;
            document.getElementById("leaseUnit").value = data.leaseUnit;
            document.getElementById("area").value = data.area;
            document.getElementById("rooms").value = data.rooms;
            document.getElementById("waterNum").value = data.waterNum;
            document.getElementById("sewerageNum").value = data.sewerageNum;
            if (data.premisesNum  ) {
                document.getElementById("premisesNum").checked = true;
            } else {
                document.getElementById("premisesNum").checked = false;
            }
            if (data.parking) {
                document.getElementById("parking").checked = true ; 
            } else {
                document.getElementById("parking").checked = false ; 
            }
        
        },
        error: function (e) {
        }
    });
}

async function submitForm(formId , conId , attType , tableId){
    var actionPath = document.getElementById(formId).action ;
    var data = new FormData(document.getElementById(formId));
    var xhr = new XMLHttpRequest();
    xhr.open("POST", actionPath ,false);
    xhr.send(data);
    document.getElementById(formId).reset();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        async: false,
        url: "/contract/attachments/"+conId+"/"+attType+"/prop",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var table = document.getElementById(tableId);
            table.innerHTML = "";
            for(var i = 0 ; i < data.length ; i ++ ){
                table.innerHTML += "<tr><td style='width: 50%;'>"+data[i].name+"</td><td style='width: 25%;'>"+data[i].date+"</td><td style='width: 5%;'><a href='/files/"+data[i].path+"' target='_blank' class='btn btn-outline-primary btn-icon-text'><i class='ti-file btn-icon-prepend'></i>View</a></td></tr>";
            }
        },
        error: function (e) {
        }
    });
}


function saveProperty() {

    var request = {}
    request["id"] = propertyId ; 
    request["licensesNumber"] = document.getElementById("licensesNumber").value;
    request["companyName"] = document.getElementById("companyName").value;
    request["mobileNumber"] = document.getElementById("mobileNumber").value;
    request["certNumber"] = document.getElementById("certNumber").value;
    request["city"] = document.getElementById("city").value;
    request["district"] = document.getElementById("district").value;
    request["type"] = document.getElementById("type").value;
    request["nameDesc"] = document.getElementById("nameDesc").value;
    request["leaseUnit"] = document.getElementById("leaseUnit").value;
    request["area"] = document.getElementById("area").value;
    request["rooms"] = document.getElementById("rooms").value;
    request["waterNum"] = document.getElementById("waterNum").value;
    request["sewerageNum"] = document.getElementById("sewerageNum").value;
    if (document.getElementById("premisesNum").checked) {
        request["premisesNum"] = true;
    } else {
        request["premisesNum"] = false;
    }
    if (document.getElementById("parking").checked) {
        request["parking"] = true;
    } else {
        request["parking"] = false;
    }

    $("#submitBtn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/property/add",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            propertyId = data.holderID;
            document.getElementById("attachA").hidden = false;
            document.getElementById("msgHolder").innerHTML = "Saved";
            document.getElementById("msgHolder").style.color = "green";
            document.getElementById("formContainer").innerHTML = "<form method='POST' action='/upload/" + propertyId + "'  enctype='multipart/form-data' id='fileUploadForm'><div class='form-group'><label>File upload</label><input type='file' class='form-control file-upload-info' id='files' placeholder='Upload Multiple Files'name='files' multiple></input></div><button type='submit' class='file-upload-browse btn btn-primary'>Upload</button></form>";
        },
        error: function (e) {
            $("#submitBtn").prop("disabled", false);
            document.getElementById("msgHolder").innerHTML = "Error please try again later";
            document.getElementById("msgHolder").style.color = "red";

        }
    });

}


//Attachments type 

var attachmentsTypeActionName = "", attachmentsTypeActionDate = "";

function setAttachmentsTypeActionName(name) {
    attachmentsTypeActionName = name;
    setAttachmentsTypeFormAction();
}
function setAttachmentsTypeActionDate(date) {
    attachmentsTypeActionDate = date;
    setAttachmentsTypeFormAction();
}
function setAttachmentsTypeFormAction() {
    document.getElementById("attachmentsTypeFileUploadForm").action = "/upload/" + propertyId + "/" + attachmentsTypeActionName + "/" + attachmentsTypeActionDate + "/PropertyAttachment/prop";
}



/*Refresh attachments tables */
function getAttachments(attType , conId , tableId){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/contract/attachments/"+conId+"/"+attType+"/prop",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var table = document.getElementById(tableId);
            table.innerHTML = "";
            for(var i = 0 ; i < data.length ; i ++ ){
                table.innerHTML += "<tr><td style='width: 50%;'>"+data[i].name+"</td><td style='width: 25%;'>"+data[i].date+"</td><td style='width: 5%;'><a href='/files/"+data[i].path+"' target='_blank' class='btn btn-outline-primary btn-icon-text'><i class='ti-file btn-icon-prepend'></i>View</a></td></tr>";
            }
        },
        error: function (e) {
        }
    });

}
