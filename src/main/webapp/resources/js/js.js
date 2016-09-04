function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}
var contextPath=getContextPath();
var url = window.location.pathname;
var currentPage = 1;
$(document).ready(function() {
	$('#addV').submit( function (e){
		insertVehicle();
		$('#btnV').attr('disabled', 'disabled');
		e.preventDefault();
	});
	$('.pageV .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPageVehicle(currentPage);
		$('.pageV').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
});
function showContentVehicle(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ response[i].vehicleType +"</td>"
				+ "<td>"+ response[i].vehicleYear +"</td>"
				+ "<td>"+ response[i].vehicleMake +"</td>"
				+ "<td>"+ response[i].vehicleWeight +"</td>"
				+ "<td>"+ response[i].vehicleTrailerNum +"</td>"
				+ "<td>"+ response[i].vehicleDes +"</td>"
			"</tr>";
	}        		
	$('.row_data').html(content);
}
function getPageVehicle(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function insertVehicle(){
	$.ajax({
        type: "POST",
        data: $("#addV").serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentVehicle(response);
        		currentPage = 1;
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
$(document).ready(function() {
	$('.nav .active').removeClass('active');
	$('.nav a[href="'+url+'"]').parent().addClass('active');
	$('.nav a[href="'+url+'"]').parents('.menu-list').addClass('nav-active');
	
	$('.form-control').keyup( function (){
		$('#btnAddIV').removeAttr('disabled');
		$('#btnTF').removeAttr('disabled');
		$('#btnV').removeAttr('disabled');
	});
	$('.recordI').change( function (){
		var recordI = $(this).find(':selected').val();
		getPageInVehicle_byRecords(recordI);
	});
	$('#addIV').submit( function(e) {
		insertInVehicle();
		$('#btnAddIV').attr('disabled', 'disabled');
		e.preventDefault();
	});
	$('#addTF').submit( function (e){
		insertTransferTime();
		$('#btnTF').attr('disabled', 'disabled');
		e.preventDefault();
	});
	$('.pI .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPInVehicle(currentPage);
		$('.pI').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
	$('.pageI .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPageInVehicle(currentPage);
		$('.pageI').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
	$('.btnStartUnload').click( function (){
		var idInVehicle = $(this).attr('data-bind');
		$(this).slideUp(300);
		upStartUnloadTime(idInVehicle);
	});
	$('.btnFinishUnload').click( function (){
		var idInVehicle = $(this).attr('data-bind');
		$('.row_data').find(this).slideUp(300 );
		upFinishUnloadTime(idInVehicle);
		setTimeout(function(){upStatusFinishUnloadTime(idInVehicle);}, 2000);
		
//		var curDate = new Date($.now() );
//		var time = curDate.toTimeString().split(' ')[0];
//		var time = curDate.getHours() + ":" + curDate.getMinutes() + ":" + curDate.getSeconds();
//		$(this).parent().html(time);
	});
	$('.pageI_unload .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPageInVehicle_whereUnloadStatus(currentPage);
		$('.pageI_unload').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
});
function assignDoorInVehicle(arrIDV, arrIDD, cDoor){
	$.ajax({
        type: "POST",
        data: {
        	arrIDV: arrIDV,
        	arrIDD: arrIDD,
        	cIDoor: cDoor,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/assignDoorInVehicle',
        success: function (response) {
        	
        	if(response == null){
        		alert("Error");
        	}else{
//        		alert(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function insertTransferTime(){
	$.ajax({
        type: "POST",
        data: $("#addTF").serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertTransferTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		var content="";
        		for(var i=0; i<response.length; i++){
        			content+=
        				"<tr>" 
        					+ "<td>"+ response[i].iVehicle.vehicleCode +"</td>" 
        					+ "<td>"+ response[i].oVehicle.vehicleCode +"</td>"
        					+ "<td>"+ response[i].transfer +"</td>"
        				"</tr>";
        		}
        		$('.row_data').html(content);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* 
 * InVehicle
 * */
/* Start showContentInVehicle */
function showContentInVehicle(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		var startUnloadTime = "", finishUnloadTime = "", nameDoor = "";
		if(response[i].startUnloadTime != null){
			startUnloadTime = response[i].startUnloadTime;        				
		}if(response[i].finishUnloadTime != null){
			finishUnloadTime = response[i].finishUnloadTime;
		}if(response[i].door.nameDoor != null){
			nameDoor = response[i].door.nameDoor; 
		}
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ response[i].date +"</td>"
				+ "<td>"+ response[i].arrivalTime +"</td>"
				+ "<td>"+ startUnloadTime +"</td>"
				+ "<td>"+ finishUnloadTime +"</td>"
				+ "<td>"+ response[i].status +"</td>"
				+ "<td>"+ nameDoor +"</td>"
				/*+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"*/
			"</tr>";
	}        		
	$('.row_data').html(content);
}
/* End showContentInVehicle */
/* Start showContentInPVehicle */
function showContentPInVehicle(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		if(response[i].door.nameDoor != null){
			nameDoor = response[i].door.nameDoor; 
		}
		var status="";
		if(response[i].status == 0) status = "Waitting Assign Door";
		else if(response[i].status == 1) status = "Waitting UnLoad";
		else if(response[i].status == 2) status = "Is UnLoading";
		else if(response[i].status == 3) status = "UnLoading is Finish";
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ nameDoor +"</td>"
				+ "<td>"+ status +"</td>"
			"</tr>";
	}        		
	$('.rowI_data').html(content);
}
/* End showContentInPVehicle */
/* Start getPageInVehicle_byRecords */
function getPageInVehicle_byRecords(recordI){
	$.ajax({
        type: "POST",
        data: 'record=' + recordI + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle_byRecords',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPageInVehicle_byRecords */
/* Start getPInVehicle */
function getPInVehicle(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentPInVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPInVehicle */
/* Start getPageInVehicle */
function getPageInVehicle(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPageInVehicle */
/* Start showContentInVehicle_whereUnloadStatus */
function showContentInVehicle_whereUnloadStatus(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		var startUnloadTime = "<button class='btnStartUnload btn-primary' " +
				"data-bind='"+response[i].idVehicle+"'>Start</button>";
		var finishUnloadTime = "<button class='btnFinishUnload btn-primary' " +
				"data-bind='"+response[i].idVehicle+"' disabled='disabled'>Finish</button>";
		if(response[i].startUnloadTime != null){
			startUnloadTime = response[i].startUnloadTime;
			finishUnloadTime = "<button class='btnFinishUnload btn-primary' " +
			"data-bind='"+response[i].idVehicle+"'>Finish</button>";
		}if(response[i].finishUnloadTime != null){
			finishUnloadTime = response[i].finishUnloadTime;
		}
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ response[i].date +"</td>"
				+ "<td>"+ response[i].arrivalTime +"</td>"
				+ "<td>"+ response[i].door.nameDoor +"</td>"
				+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"
				+ "<td>"+ startUnloadTime +"</td>"
				+ "<td>"+ finishUnloadTime +"</td>"
			"</tr>";
	}
	$('.row_data').html(content);
	$('.row_data').find('.btnStartUnload').click(function(){
		var idInVehicle = $(this).attr('data-bind');
		$('.row_data').find(this).slideUp(300 );
		upStartUnloadTime(idInVehicle);
	});
	$('.row_data').find('.btnFinishUnload').click(function(){
		var idInVehicle = $(this).attr('data-bind');
		$('.row_data').find(this).slideUp(300 );
		upFinishUnloadTime(idInVehicle);
		setTimeout(function(){upStatusFinishUnloadTime(idInVehicle)}, 2000);
	});
}
/* End showContentInVehicle_whereUnloadStatus */
/* Start getPageInVehicle_whereUnloadStatus */
function getPageInVehicle_whereUnloadStatus(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle_whereUnloadStatus',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInVehicle_whereUnloadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPageInVehicle_whereUnloadStatus */
/* Start insertInVehicle */
function insertInVehicle(){
	$.ajax({
        type: "POST",
        data: $("#addIV").serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertInVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		content="";        		
        		for(var i=0; i<response.length; i++){
        			var startUnloadTime = "", finishUnloadTime = "", nameDoor = "";
        			if(response[i].startUnloadTime != null){
        				startUnloadTime = response[i].startUnloadTime;        				
        			}if(response[i].finishUnloadTime != null){
        				finishUnloadTime = response[i].finishUnloadTime;
        			}if(response[i].door.nameDoor != null){
        				nameDoor = response[i].door.nameDoor; 
        			}
        			content+=
	        			"<tr>" 
	    					+ "<td>"+ response[i].vehicleCode +"</td>" 
	    					+ "<td>"+ response[i].date +"</td>"
	    					+ "<td>"+ response[i].arrivalTime +"</td>"
	    					+ "<td>"+ startUnloadTime +"</td>"
	    					+ "<td>"+ finishUnloadTime +"</td>"
	    					+ "<td>"+ response[i].status +"</td>"
	    					+ "<td>"+ nameDoor +"</td>"
	    					/*+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"*/
	    				"</tr>";
        		}        		
        		$('.row_data').html(content);
        		currentPage = 1;
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End insertInVehicle */
/* Start upStartUnloadTime */
function upStartUnloadTime(idInVehicle){
	$.ajax({
        type: "POST",
        data: 'idInVehicle=' + idInVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upStartUnloadTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInVehicle_whereUnloadStatus(response)
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End upStartUnloadTime */
/* Start upStatusFinishUnloadTime */
function upStatusFinishUnloadTime(idInVehicle){
	$.ajax({
        type: "POST",
        data: 'idInVehicle=' + idInVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upStatusFinishUnloadTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInVehicle_whereUnloadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End upStatusFinishUnloadTime */
/* Start upFinishUnloadTime */
function upFinishUnloadTime(idInVehicle){
	$.ajax({
        type: "POST",
        data: 'idInVehicle=' + idInVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upFinishUnloadTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInVehicle_whereUnloadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End upFinishUnloadTime */
/* Start delInVehicle */
function delInVehicle(idInVehicle){
	$.ajax({
        type: "POST",
        data: 'idInVehicle=' + idInVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delInVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		content="";
        		for(var i=0; i<response.length; i++){
        			content+=
	        			"<tr>" 
	    					+ "<td>"+ response[i].vehicleCode +"</td>" 
	    					+ "<td>"+ response[i].date +"</td>"
	    					+ "<td>"+ response[i].arrivalTime +"</td>"
	    					+ "<td>"+ response[i].startUploadTime +"</td>"
	    					+ "<td>"+ response[i].finishUploadTime +"</td>"
	    					+ "<td>"+ response[i].status +"</td>"
	    					+ "<td>"+ response[i].door.nameDoor +"</td>"
	    					+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"
	    					+ "<td><a class='delIV' data-bind='"+response[i].idVehicle+"'>Del</td>" +
	    				"</tr>"
        		}        		
        		$('.row_data').html(content);
        		$('.row_data').find('.delIV').click( function(e){
        			var idInVehicle = $(this).attr('data-bind');
        			delInVehicle(idInVehicle);
        		});
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End delInVehicle*/
/* ******************************************************************************
 * ******************************************************************************
 * ******************************************************************************
 * ******************************************************************************
/* 
 * OutVehicle	
 * */
$(document).ready(function() {
	$('.form-control').keyup( function (){
		$('#btnAddOV').removeAttr('disabled');
	});
	$('.recordO').change( function (){
		var recordO = $(this).find(':selected').val();
		getPageOutVehicle_byRecords(recordO);
	});
	$('.pageO .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPageOutVehicle(currentPage);
		$('.pageO').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
	$('.pageO_load .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPageOutVehicle_whereLoadStatus(currentPage);
		$('.pageO_unload').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
	$('.pO .page_index').click( function() {
		currentPage = $(this).attr('data-index');
		getPOutVehicle(currentPage);
		$('.pO').find('li.active').removeClass('active').addClass('page_index');
		$(this).removeClass('page_index').addClass('active');
	});
	$('#addOV').submit( function(e) {
		insertOutVehicle();
		$('#btnAddOV').attr('disabled', 'disabled');
		e.preventDefault();
	});
	$('.btnStartLoad').click( function (){
		var idOutVehicle = $(this).attr('data-bind');
		$(this).slideUp(300);
		upStartLoadTime(idOutVehicle);
	});
	$('.btnFinishLoad').click( function (){
		var idOutVehicle = $(this).attr('data-bind');
		$('.row_data').find(this).slideUp(300);
		upFinishLoadTime(idOutVehicle);
		setTimeout(function(){upStatusFinishLoadTime(idOutVehicle);}, 2000);
	});
});
/* Start getPageOutVehicle_byRecords */
function getPageOutVehicle_byRecords(recordO){
	$.ajax({
        type: "POST",
        data: 'record=' + recordO + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle_byRecords',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPageInVehicle_byRecords */
/* Start showContentOutVehicle */
function showContentOutVehicle(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		var startLoadTime = "", finishLoadTime = "", nameDoor = "";
		if(response[i].startLoadTime != null){
			startLoadTime = response[i].startLoadTime;        				
		}if(response[i].finishLoadTime != null){
			finishLoadTime = response[i].finishLoadTime;
		}if(response[i].door.nameDoor != null){
			nameDoor = response[i].door.nameDoor; 
		}
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ response[i].date +"</td>"
				+ "<td>"+ response[i].arrivalTime +"</td>"
				+ "<td>"+ startLoadTime +"</td>"
				+ "<td>"+ finishLoadTime +"</td>"
				+ "<td>"+ response[i].status +"</td>"
				+ "<td>"+ nameDoor +"</td>"
				/*+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"*/
			"</tr>";
	}        		
	$('.row_data').html(content);
}
/* End showContentOutVehicle*/
/* Start showContentPOutVehicle*/
function showContentPOutVehicle(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		var nameDoor="";
		if(response[i].door.nameDoor != null){
			nameDoor = response[i].door.nameDoor; 
		}
		var status="";
		if(response[i].status == 0) status = "Waitting Assign Door";
		else if(response[i].status == 1) status = "Waitting Load";
		else if(response[i].status == 2) status = "Is Loading";
		else if(response[i].status == 3) status = "Loading is Finish";
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ nameDoor +"</td>"
				+ "<td>"+ status +"</td>"
			"</tr>";
	}        	
	$('.rowO_data').html(content);
}
/* End showContentPoutVehicle */
/* Start getPOutVehicle */
function getPOutVehicle(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentPOutVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPOutVehicle */
/* Start getPageOutVehicle */
function getPageOutVehicle(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutVehicle(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPageOutVehicle */
/* Start showContentOutVehicle_whereLoadStatus */
function showContentOutVehicle_whereLoadStatus(response){
	var content="";        		
	for(var i=0; i<response.length; i++){
		var startLoadTime = "<button class='btnStartLoad btn-primary' " +
				"data-bind='"+response[i].idVehicle+"'>Start</button>";
		var finishLoadTime = "<button class='btnFinishLoad btn-primary' " +
				"data-bind='"+response[i].idVehicle+"' disabled='disabled'>Finish</button>";
		if(response[i].startLoadTime != null){
			startLoadTime = response[i].startLoadTime;
			finishLoadTime = "<button class='btnFinishLoad btn-primary' " +
					"data-bind='"+response[i].idVehicle+"'>Finish</button>"
		}if(response[i].finishLoadTime != null){
			finishLoadTime = response[i].finishLoadTime;
		}
		content+=
			"<tr>" 
				+ "<td>"+ response[i].vehicleCode +"</td>" 
				+ "<td>"+ response[i].date +"</td>"
				+ "<td>"+ response[i].arrivalTime +"</td>"
				+ "<td>"+ response[i].door.nameDoor +"</td>"
				+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"
				+ "<td>"+ startLoadTime +"</td>"
				+ "<td>"+ finishLoadTime +"</td>"
			"</tr>";
	}
	$('.row_data').html(content);
	$('.row_data').find('.btnStartLoad').click(function(){
		var idOutVehicle = $(this).attr('data-bind');
		$('.row_data').find(this).slideUp(300 );
		upStartLoadTime(idOutVehicle);
	});
	$('.row_data').find('.btnFinishLoad').click(function(){
		var idOutVehicle = $(this).attr('data-bind');
		$('.row_data').find(this).slideUp(300 );
		upFinishLoadTime(idOutVehicle);
		setTimeout(function(){upStatusFinishLoadTime(idOutVehicle)}, 2000);
	});
}
/* End showContentOutVehicle_whereLoadStatus */
/* Start getPageOutVehicle_whereLoadStatus */
function getPageOutVehicle_whereLoadStatus(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle_whereLoadStatus',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutVehicle_whereLoadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End getPageOutVehicle_whereLoadStatus */
/* Start insertOutVehicle */
function insertOutVehicle(){
	$.ajax({
        type: "POST",
        data: $("#addOV").serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertOutVehicle',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		content="";        		
        		for(var i=0; i<response.length; i++){
        			var startLoadTime = "", finishLoadTime = "", nameDoor = "";
        			if(response[i].startLoadTime != null){
        				startLoadTime = response[i].startLoadTime;        				
        			}if(response[i].finishLoadTime != null){
        				finishLoadTime = response[i].finishLoadTime;
        			}if(response[i].door.nameDoor != null){
        				nameDoor = response[i].door.nameDoor; 
        			}
        			content+=
	        			"<tr>" 
	    					+ "<td>"+ response[i].vehicleCode +"</td>" 
	    					+ "<td>"+ response[i].date +"</td>"
	    					+ "<td>"+ response[i].arrivalTime +"</td>"
	    					+ "<td>"+ startLoadTime +"</td>"
	    					+ "<td>"+ finishLoadTime +"</td>"
	    					+ "<td>"+ response[i].status +"</td>"
	    					+ "<td>"+ nameDoor +"</td>"
	    					/*+ "<td>"+ response[i].cDS.nameCrossDockingSystem +"</td>"*/
	    				"</tr>";
        		}        		
        		$('.row_data').html(content);
        		currentPage = 1;
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End insertInVehicle */
/* Start assignDoorOutVehicle*/
function assignDoorOutVehicle(arrODV, arrODD, cODoor){
	$.ajax({
        type: "POST",
        data: {
        	arrODV: arrODV,
        	arrODD: arrODD,
        	cODoor: cODoor,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/assignDoorOutVehicle',
        success: function (response) {
        	
        	if(response == null){
        		alert("Error");
        	}else{
        		alert(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/**/
/* Start upStatusFinishUnloadTime */
function upStatusFinishLoadTime(idOutVehicle){
	$.ajax({
        type: "POST",
        data: 'idOutVehicle=' + idOutVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upStatusFinishLoadTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutVehicle_whereLoadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/* End upStatusFinishUnloadTime */
/* Start upStartUnloadTime */
function upStartLoadTime(idOutVehicle){
	$.ajax({
        type: "POST",
        data: 'idOutVehicle=' + idOutVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upStartLoadTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutVehicle_whereLoadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End upStartUnloadTime */
/* Start upFinishUnloadTime */
function upFinishLoadTime(idOutVehicle){
	$.ajax({
        type: "POST",
        data: 'idOutVehicle=' + idOutVehicle + '&currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upFinishLoadTime',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutVehicle_whereLoadStatus(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End upFinishUnloadTime */
var sizeIDoor = $('.sizeIDoor').html();
var arrID = []; var cIDoor = []; var uIDoor = [];	
for(var i=0; i<sizeIDoor; i++){
	arrID[i] = 'iDoor-' + i;
	uIDoor[i] = $('.uIDoor-' + i).html();
	cIDoor[i] = $('.cIDoor-' + i).html();
}
var sizeODoor = $('.sizeODoor').html();
var arrOD = []; var cODoor = []; var uODoor = [];	
for(var i=0; i<sizeODoor; i++){
	arrOD[i] = 'oDoor-' + i;
	uODoor[i] = $('.uODoor-' + i).html();
	cODoor[i] = $('.cODoor-' + i).html();
}
var costs = [];
var k=0;
for(var i=0; i<sizeIDoor; i++){
	costs[i] = [];
	for(var j=0; j<sizeODoor; j++){
		costs[i][j] = $('.cost-' + k).attr('data-bind');
		k++;
	}
}
var sizeInVehicle = $('.size-in-vehicle').attr('data-bind');
var sizeOutVehicle = $('.size-out-vehicle').attr('data-bind');
var vol = [];
var dem = [];
var trips = [];
var k=0;
for(var i=0; i<sizeInVehicle; i++){
	trips[i] = [];
	vol[i] = $('.volumn-' + i).attr('data-bind');
	for(var j=0; j<sizeOutVehicle; j++){
		dem[j] = $('.demand-' + j).attr('data-bind');
		trips[i][j] = $('.trip-' + k).attr('data-bind');
		k++;
	}
}
var idInVehiclesChoose = [], idOutVehiclesChoose = [];
var arrInDoorChoose = []; arrOutDoorChoose = []; 
var objective;
$(document).ready(function(){
	/*--------------------------------------------------------------------------------------
	 * InVehicle */
	$(".gIDoor input[type='radio']").click(function (){
		var volumn = $(this).parents('.gIDoor').find('.volumn').html();
		var doorChoose = $(this).parents('.gIDoor').find("[data-flag = 'choose']");
		var iDoor = doorChoose.attr('data-index');
		var nGroup = doorChoose.attr('name');
		// Ban dau chua ton tai thuoc tinh flag -> lan chon dau tien se gan bien flag 
		if(doorChoose.attr('class') == null){
			var iDoor = $(this).attr('data-index');
			uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
			$(this).attr('data-flag', 'choose');
		// Lan tiep theo da ton tai bien flag se xoa bien flag cua radio cu va tao them bien flag vao radio moi
		}else{
			if($(this).attr('data-index') != iDoor || $(this).attr('name') == nGroup){
				var iDoor = doorChoose.attr('data-index');
				uIDoor[iDoor] = uIDoor[iDoor] - volumn;
				doorChoose.removeAttr('data-flag');
				
				iDoor = $(this).attr('data-index');
				uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
				$(this).attr('data-flag', 'choose');
			}else{// Cung chon 1 cua
				$(this).attr('data-flag', 'choose');
				iDoor = $(this).attr('data-index');
				uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
			}
		}
		for(var i=0; i<sizeIDoor; i++){
			$('.' + arrID[i]).parent().find('.remain').html(cIDoor[i] - uIDoor[i]);
		}
		objective = getObjective();
		$('.total-cost').html(objective);
	});
	/*$('#btnAssID').click(function(){
		var arrIDV = $(".gIDoor [data-flag = choose]").map( function() {
		    return $(this).attr('data-idInVehicle');
		}).get();
		var arrIDD = $(".gIDoor [data-flag = choose]").map( function() {
		    return $(this).attr('data-idDoor');
		}).get();
		assignDoorInVehicle(arrIDV, arrIDD, cIDoor);
	});*/
	/*--------------------------------------------------------------------------------------
	 * OutVehicle*/
	$(".gODoor input[type='radio']").click(function (){
		var demand = $(this).parents('.gODoor').find('.demand').html();
		var doorChoose = $(this).parents('.gODoor').find("[data-flag = 'choose']");
		var oDoor = doorChoose.attr('data-index');
		var nGroup = doorChoose.attr('name');
		if(doorChoose.attr('class') == null){
			var oDoor = $(this).attr('data-index');
			uODoor[oDoor] = uODoor[oDoor] - (-demand);
			$(this).attr('data-flag', 'choose');
		}else{
			if($(this).attr('data-index') != oDoor || $(this).attr('name') == nGroup){
				var oDoor = doorChoose.attr('data-index');
				uODoor[oDoor] = uODoor[oDoor] - demand;
				doorChoose.removeAttr('data-flag');
				
				oDoor = $(this).attr('data-index');
				uODoor[oDoor] = uODoor[oDoor] - (-demand);
				$(this).attr('data-flag', 'choose');
			}else{
				$(this).attr('data-flag', 'choose');
				oDoor = $(this).attr('data-index');
				uODoor[oDoor] = uODoor[oDoor] - (-demand);
			}
		}
		for(var i=0; i<sizeODoor; i++){
			$('.' + arrOD[i]).parent().find('.remain').html(cODoor[i] - uODoor[i]);
		}
		objective = getObjective();
		$('.total-cost').html(objective);
	});
	$('#btnAssDoor').click(function(){
		var arrIDV = $(".gIDoor [data-flag = choose]").map( function() {
		    return $(this).attr('data-idInVehicle');
		}).get();
		var arrIDD = $(".gIDoor [data-flag = choose]").map( function() {
		    return $(this).attr('data-idDoor');
		}).get();
		var arrODV = $(".gODoor [data-flag = choose]").map( function() {
		    return $(this).attr('data-idOutVehicle');
		}).get();
		var arrODD = $(".gODoor [data-flag = choose]").map( function() {
		    return $(this).attr('data-idDoor');
		}).get();
		for(var i=0; i<sizeODoor; i++){
			cODoor[i] = cODoor[i] - uODoor[i];
		}
		assignDoorInVehicle(arrIDV, arrIDD, cIDoor);
		assignDoorOutVehicle(arrODV, arrODD, cODoor);
	});
	$('#btnAssAI').click(function (){
		assignDoorAI();
	});
});
function getObjective(){
	var i=0;
	var arrIDV = $(".gIDoor [data-flag = choose]").map( function() {
		idInVehiclesChoose[i] = $(this).attr('data-idInVehicle');
		arrInDoorChoose[i] = $(this).attr('data-index');
		i++;
	}).get();
	var i=0;
	var arrODV = $(".gODoor [data-flag = choose]").map( function() {
		idOutVehiclesChoose[i] = $(this).attr('data-idOutVehicle');
		arrOutDoorChoose[i] = $(this).attr('data-index');
		i++;
	}).get();
	objective=0;
	for(var i=0; i<idInVehiclesChoose.length; i++){
		for(var j=0; j<idOutVehiclesChoose.length; j++){
			var trip = $('.trip-' + idInVehiclesChoose[i] + '-' + idOutVehiclesChoose[j]).attr('data-bind');
			var cost = costs[arrInDoorChoose[i]][arrOutDoorChoose[j]];
			console.log('Trip=' + trip);
			console.log('Cost=' + cost)
			console.log('Obj=' + trip*cost);
			objective += trip*costs[arrInDoorChoose[i]][arrOutDoorChoose[j]];
		}
	}
	return objective;
}
function assignDoorAI(){
	$.ajax({
        type: "POST",
        data: 'json=' + getJSON_AssDoorAI(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/assignDoorAI',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		for(var i=0; i<sizeIDoor; i++){
        			arrID[i] = 'iDoor-' + i;
        			uIDoor[i] = $('.uIDoor-' + i).html();
        			cIDoor[i] = $('.cIDoor-' + i).html();
        		}
        		for(var i=0; i<sizeODoor; i++){
        			arrOD[i] = 'oDoor-' + i;
        			uODoor[i] = $('.uODoor-' + i).html();
        			cODoor[i] = $('.cODoor-' + i).html();
        		}
        		var o = jQuery.parseJSON(response);
        		var objective = o.objective;
    			var sol_invehicles = o.inVehicleAssignments;
    			var sol_outvehicles = o.outVehicleAssignments;
    			for(var i=0; i<sol_invehicles.length; i++){
    				var doorChoose = $(".gIDoor [name = groupI-"+i+"][data-index="+sol_invehicles[i]+"]");
    				doorChoose.parent().find('.remain').css('background', '#B0E0E6');
//    				doorChoose.attr('checked','checked');
//    				doorChoose.attr('data-flag','choose');
//    				volumn = doorChoose.parents('.gIDoor').find('.volumn').html();
//    				var iDoor = sol_invehicles[i];
//    				uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
    			}
    			for(var i=0; i<sizeIDoor; i++){
    				$('.' + arrID[i]).parent().find('.remain').html(cIDoor[i] - uIDoor[i]);
    			}
    			for(var i=0; i<sol_outvehicles.length; i++){
    				var doorChoose = $(".gODoor [name = groupO-"+i+"][data-index="+sol_outvehicles[i]+"]");
    				doorChoose.parent().find('.remain').css('background', '#B0E0E6');
//    				doorChoose.attr('checked','checked');
//    				doorChoose.attr('data-flag','choose');
//    				demand = doorChoose.parents('.gODoor').find('.demand').html();
//    				var oDoor = sol_outvehicles[i];
//    				uODoor[oDoor] = uODoor[oDoor] - (-demand);
    			}
    			for(var i=0; i<sizeODoor; i++){
    				$('.' + arrOD[i]).parent().find('.remain').html(cODoor[i] - uODoor[i]);
    			}
    			$('.total-cost-ai').html(objective);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function getJSON_AssDoorAI(){
	var json = "{"
		+ "\"inDoorNum\":" + sizeIDoor
		+ ",\"outDoorNum\":" + sizeODoor
		+ ",\"inVehicleNum\":" + sizeInVehicle
		+ ",\"outVehicleNum\":" + sizeOutVehicle
		+ ",\"costs\":[";
		for(var i=0; i<sizeIDoor; i++){
			for(var j=0; j<sizeODoor; j++){
				json +="{\"fromDoor\": " + i + ", \"toDoor\": " + j + ", \"costs\": " + costs[i][j] + "}";
				if (i != sizeIDoor - 1 || j != sizeODoor -1)
					json += ",";
			}
		}
		json += "]";	
	
	json += ",\"trips\":[";
	for(var i=0; i<sizeInVehicle; i++){
		for(var j=0; j<sizeOutVehicle; j++){
			json +="{\"fromVehicle\": " + i + ", \"toVehicle\": " + j + ", \"time\": " + trips[i][j] + "}";
			if (i != sizeInVehicle - 1 || j != sizeOutVehicle -1)
				json += ",";
		}
	}
	json += "]";
	json +=",\"inDoorCapacities\": [" ;
	for(var i=0; i<cIDoor.length; i++){
		json += cIDoor[i];
		if (i < cIDoor.length - 1)
			json += ",";
	}
	json +="],";
	json +="\"outDoorCapacities\": [" ;
	for(var i=0; i<cODoor.length; i++){
		json += cODoor[i];
		if (i < cODoor.length - 1)
			json += ",";
	}
	json +="],";
	json +="\"inVehicleDemands\": [" ;
	for(var i=0; i<vol.length; i++){
		json += vol[i];
		if (i < vol.length - 1)
			json += ",";
	}
	json +="],";
	json +="\"outVehicleDemands\": [" ;
	for(var i=0; i<dem.length; i++){
		json += dem[i];
		if (i < dem.length - 1)
			json += ",";
	}
	json +="]";
	json +="}";
	return json;
}
