var contractId;

var propertyUnitId;

var units;

function formatDate(date){
    date = new Date(date);
    const day = `${date.getDate() < 10 ? '0' : ''}${date.getDate()}`;
    const month = `${date.getMonth() + 1 < 10 ? '0' : ''}${date.getMonth() + 1}`;
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
}


$(document).ready(function () {
    document.getElementById("tabContract").click();
    document.getElementById("tabTenants").click();
    contractId = document.getElementById("cId").value ; 
    getAttachments("serviceCharges" , contractId , "serviceChargesTableBody");
    getAttachments("Maintainance" , contractId , "maintananceTableBody");
    getAttachments("AttachmentsType" , contractId , "attTableBody");
    getContractData();
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


function getContractData(){
    var id = document.getElementById("cId").value;
    contractId = id ; 
    $.ajax({
        type: "GET",
        contentType: "application/json",
        async: false,
        url: "/contract/getById/"+id,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            document.getElementById("emiratesId").value = data.emiratesId;
            document.getElementById("passportNum").value = data.passportNum;
            document.getElementById("nameContract").value = data.nameContract;
            document.getElementById("nationality").value = data.nationality;
            document.getElementById("mobileNum").value = data.mobileNum;
            document.getElementById("emailContract").value = data.emailContract;
            propertyUnitId = data.propertyUnitId;
            document.getElementById("contractStartDate").value = formatDate(data.contractStartDate);
            document.getElementById("contractEndDate").value = formatDate(data.contractEndDate);
        },
        error: function (e) {
        }
    });

    $.ajax({
        type: "GET",
        contentType: "application/json",
        async: false,
        url: "/property/get/"+propertyUnitId,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (element) {
            document.getElementById("propertyType").value = element.type;
            document.getElementById("nameDescription").value = element.nameDesc;
            document.getElementById("contractleaseUnit").value = element.leaseUnit;
            document.getElementById("area").value = element.area;
            document.getElementById("rooms").value = element.rooms;
            document.getElementById("numWaterElec").value = element.waterNum;
            document.getElementById("sewegNumber").value = element.sewerageNum;
            document.getElementById("hasPremises").checked = element.premisesNum;
            document.getElementById("parking").checked = element.parking;
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
        url: "/contract/attachments/"+conId+"/"+attType+"/res",
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

function saveResContract() {

    var request = {}
    request["id"] = contractId ; 
    request["emiratesId"] = document.getElementById("emiratesId").value;
    request["passportNum"] = document.getElementById("passportNum").value;
    request["nameContract"] = document.getElementById("nameContract").value;
    request["nationality"] = document.getElementById("nationality").value;
    request["mobileNum"] = document.getElementById("mobileNum").value;
    request["emailContract"] = document.getElementById("emailContract").value;
    request["propertyUnitId"] = propertyUnitId;
    request["contractStartDate"] = document.getElementById("contractStartDate").value;
    request["contractEndDate"] = document.getElementById("contractEndDate").value;


    $("#btnSaveResContract").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/contract/add/res",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            contractId = data.holderID;
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



/*Uploader Script*/


//service Charges 

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
    document.getElementById("fileUploadForm").action = "/upload/" + contractId + "/" + actionName + "/" + actionDate + "/serviceCharges/res";
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
    document.getElementById("maintainanceFileUploadForm").action = "/upload/" + contractId + "/" + maintainanceActionName + "/" + maintainanceActionDate + "/Maintainance/res";
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
    document.getElementById("attachmentsTypeFileUploadForm").action = "/upload/" + contractId + "/" + attachmentsTypeActionName + "/" + attachmentsTypeActionDate + "/AttachmentsType/res";
}



/*Refresh attachments tables */
function getAttachments(attType , conId , tableId){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/contract/attachments/"+conId+"/"+attType+"/res",
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


