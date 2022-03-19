var contractId;

var propertyUnitId ; 

var units;

$(document).ready(function() {
    document.getElementById("tabTenants").click() ;  
    document.getElementById("tabSerachUnits").click() ;  
    document.getElementById("tabTenants").hidden = true;
    $(document).on('submit', '#fileUploadForm', function () {
        submitForm("fileUploadForm",contractId,"serviceCharges","serviceChargesTableBody");
        return false;
    });
    $(document).on('submit', '#maintainanceFileUploadForm', function () {
        submitForm("maintainanceFileUploadForm",contractId,"Maintainance" ,"maintananceTableBody");
        return false;
    });
    $(document).on('submit', '#attachmentsTypeFileUploadForm', function () {
        submitForm("attachmentsTypeFileUploadForm",contractId,"AttachmentsType","attTableBody");
        return false;
    });
});

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
        url: "/contract/attachments/"+conId+"/"+attType+"/comm",
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

function saveCommContract() {    

    var request = {}
    request["id"] = -1 ; 
    request["propertyId"] = propertyUnitId;
    request["licenseNumber"] = document.getElementById("licenseNumber").value;
    request["applicationNumber"] = document.getElementById("applicationNumber").value;
    request["contractStartDate"] = document.getElementById("contractStartDate").value;
    request["contractEndDate"] = document.getElementById("contractEndDate").value;
    
    $("#btnSaveCommContract").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/contract/add/comm",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            contractId = data.holderID;
            document.getElementById("tabServiceFees").hidden = false;
            document.getElementById("tabMaintainanceCharges").hidden = false;
            document.getElementById("tabAttachments").hidden = false;
            document.getElementById("resultMsg").innerHTML = "Saved"; 
            document.getElementById("resultMsg").style.color = "green"; 
        },
        error: function (e) {
            $("#btnSaveResContract").prop("disabled", false);
            document.getElementById("resultMsg").innerHTML = "Error please try again later"; 
            document.getElementById("resultMsg").style.color = "red"; 
        }
    });

}



function searchProperty() {

    var request = {}
    request["ttt"] = document.getElementById("pType").value;
    request["leaseUnit"] = document.getElementById("leaseUnit").value;
    request["ownerName"] = document.getElementById("ownerName").value;
    request["sewegNum"] = document.getElementById("sewegNum").value;
    request["waterElec"] = document.getElementById("waterElec").value;
    document.getElementById("resultBody").innerHTML = "";
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/property/find",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            units = data ; 
            for(var i = 0 ; i < data.length ; i ++){
                if(data[i].status == "rented"){
                    document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].leaseUnit+"</th>  <th>"+data[i].area+"</th>  <th>"+data[i].rooms+"</th>  <th>"+data[i].parking+"</th>  <th>"+data[i].premisesNum+"</th> <th>" + data[i].status + "</th> </tr>"
                }else {
                    document.getElementById("resultBody").innerHTML += "<tr><th>"+data[i].type+"</th><th>"+data[i].nameDesc+"</th>   <th>"+data[i].leaseUnit+"</th>  <th>"+data[i].area+"</th>  <th>"+data[i].rooms+"</th>  <th>"+data[i].parking+"</th>  <th>"+data[i].premisesNum+"</th> <th>" + data[i].status + "</th> <th><button value="+data[i].id+" onclick='setUnitId(this.value)' >Select</button></th></tr>"
                }
                
            }
        },
        error: function (e) {
        }
    });

}


function setUnitId(id){
    propertyUnitId = id ; 
    document.getElementById("tabTenants").hidden = false;
    document.getElementById("tabContract").hidden = false;
    document.getElementById("tabSerachUnits").hidden = true;
    document.getElementById("tabTenants").click() ;  

    document.getElementById("tabTenants").value = "" ;  
    var element ; 
    for(var i = 0 ; i < units.length ; i ++){
        if(units[i].id == id ){
            element = units[i];
            break ; 
        }
    }

    document.getElementById("propertyType").value = element.type;
    document.getElementById("nameDescription").value = element.nameDesc;
    document.getElementById("contractleaseUnit").value = element.leaseUnit;
    document.getElementById("area").value = element.area;
    document.getElementById("rooms").value = element.rooms;
    document.getElementById("numWaterElec").value = element.waterNum;
    document.getElementById("sewegNumber").value = element.sewerageNum;
    document.getElementById("hasPremises").checked  = element.premisesNum;
    document.getElementById("parking").checked  = element.parking;


}






var actionName = "", actionDate = "";

function setActionName(name) {
    actionName = name;
    setFormAction();
}
function setActionDate(date) {
    actionDate = date;
    setFormAction();
}

function setFormAction() {
    document.getElementById("fileUploadForm").action = "/upload/" + contractId + "/" + actionName + "/" + actionDate + "/serviceCharges/comm";
}

//

var maintainanceActionName = "", maintainanceActionDate = "";

function setMaintainanceActionName(name) {
    maintainanceActionName = name;
    setMaintainanceFormAction();
}
function setMaintainanceActionDate(date) {
    maintainanceActionDate = date;
    setMaintainanceFormAction();
}

function setMaintainanceFormAction() {
    document.getElementById("maintainanceFileUploadForm").action = "/upload/" + contractId + "/" + maintainanceActionName + "/" + maintainanceActionDate + "/Maintainance/comm";
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
    document.getElementById("attachmentsTypeFileUploadForm").action = "/upload/" + contractId + "/" + attachmentsTypeActionName + "/" + attachmentsTypeActionDate + "/AttachmentsType/comm";
}



/*Refresh attachments tables */
function getAttachments(attType , conId , tableId){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/contract/attachments/"+conId+"/"+attType+"/comm",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var table = document.getElementById(tableId);
            table.innerHTML = "";
            for(var i = 0 ; i < data.length ; i ++ ){
                table += "<tr><td style='width: 50%;'>"+data[i].name+"</td><td style='width: 25%;'>"+data[i].date+"</td><td style='width: 5%;'><a href='"+data[i].path+"' targer='_blank' class='btn btn-outline-primary btn-icon-text'><i class='ti-file btn-icon-prepend'></i>View</a></td></tr>";
            }
        },
        error: function (e) {
        }
    });

}


