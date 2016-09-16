function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}
var contextPath=getContextPath();
var url = window.location.pathname;
var currentPage = 1;
$(document).ready(function(e){
	$('#addID').submit(function(e){
		insertInDoor();
		e.preventDefault();
	});
	$('.edit-indoor').click(function(e){
		var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
		var trC=$(this).parents('tr');
		eventEditIndoor(e, trC_old, trC);
	});	
	$('#addOD').submit(function(e){
		insertOutDoor();
		e.preventDefault();
	});
	$('.edit-outdoor').click(function(e){
		var trC=$(this).parents('tr');
		var idOutDoor = trC.find('.idOutDoor').html();
		var nameD = trC.find('.nameD').html();
		var capD = trC.find('.capD').html();
		var statusD = trC.find('.statusD').html();
		var content="";
		content += "<td><input type='text' value='"+nameD+"' class='form-control nameD'></td>" 
				+ "<td><input type='text' value='"+capD+"' class='form-control capD' style='width: 55%'></td>"
				+ "<td>" 
					+ "<select name='statusD' class='form-control statusD' style='width: 105%'>" 
						+ "<option value='0'>Processing</option>"
						+ "<option value='1'>Active</option>"
						+ "<option value='2'>InActive</option>"
						+ "<option value='3'>Maintenance</option>"
					+ "</select>" 
				+ "</td>"
				+ "<td><a href='' class='save-outdoor'>Save</a></td>";
		trC.html(content);
		$('.save-outdoor').click(function(e){
			trC=$(this).parents('tr');
			nameD = trC.find('.nameD').val(); capD = trC.find('.capD').val(); 
			statusD = trC.find('.statusD').val();
			editOutDoor(idOutDoor, nameD, capD, statusD);
			e.preventDefault();
		});
		e.preventDefault();
	});
	$('.save-cost').click(function(e){
		var trC = $(this).parents('tr');
		var idCost = trC.find('.id-cost').html();
		var cost = trC.find('.form-control').val();
		editCost(idCost, cost);
		e.preventDefault();
	});
	$('.edit-cost').click(function(e){
		var trC = $(this).parents('tr');
		var idCost = trC.find('.id-cost').html();
		var cost = trC.find('.cost').html();
		trC.find('.cost').parent().html("" +
				"<input type='text' class='form-control small' value='"+cost+"'/>");
		$(this).parent().html("<td><a href='' class='save-cost'>Save</a></td>");
		$('.save-cost').click(function(e){
			var trC = $(this).parents('tr');
			var idCost = trC.find('.id-cost').html();
			var cost = trC.find('.form-control').val();
			editCost(idCost, cost);
			e.preventDefault();
		});
		e.preventDefault();
	});
	$('.pageCost .page_index').click(function(){
		currentPage = $(this).attr('data-index');
		getPageCost(currentPage);
	});
});
function eventEditIndoor(e, trC_old, trC){
//	var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
	var idInDoor_old = trC_old.find('.idInDoor').html();
	var nameD_old = trC_old.find('.nameD').val();
	var capD_old = trC_old.find('.capD').val();
	
	var statusD_old = trC_old.find('.statusD').val();
	if(statusD_old == 0) statusD_old = "Processing";
	else if(statusD_old == 1) statusD_old = "Active";
	else if(statusD_old == 2) statusD_old = "InActive";
	else statusD_old = "Maintenance";
	
	var content_old="";
	content_old += "<td style='display: none'><span class='idInDoor' >"+idInDoor_old+"</span></td>" 
			+ "<td><span class='nameD'>"+nameD_old+"</span></td>"
			+ "<td><span class='capD'>"+capD_old+"</span></td>"
			+ "<td><span class='statusD'>"+statusD_old+"</span></td>"
			+ "<td><a href='' class='edit-indoor'>Edit</a></td>";
	trC_old.html(content_old);
	
//	var trC=$(this).parents('tr');
	var idInDoor = trC.find('.idInDoor').html();
	var nameD = trC.find('.nameD').html();
	var capD = trC.find('.capD').html();
	var statusD = trC.find('.statusD').html();
	var content="";
	content += "<td><input type='text' value='"+nameD+"' class='form-control nameD'></td>" 
			+ "<td><input type='text' value='"+capD+"' class='form-control capD' style='width: 55%'></td>"
			+ "<td>" 
				+ "<select name='statusD' class='form-control statusD' style='width: 105%'>"
					+ "<option value='0'>Processing</option>"
					+ "<option value='1'>Active</option>"
					+ "<option value='2'>InActive</option>"
					+ "<option value='3'>Maintenance</option>"
				+ "</select>" 
			+ "</td>"
			+ "<td><a href='' class='save-indoor'>Save</a></td>";
	trC.html(content);
	$('.save-indoor').click(function(e){
		trC=$(this).parents('tr');
		nameD = trC.find('.nameD').val(); capD = trC.find('.capD').val(); 
		statusD = trC.find('.statusD').val();
		editInDoor(idInDoor, nameD, capD, statusD);
		e.preventDefault();
	});
//	$('.edit-indoor').click(function(e){
//		var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
//		var trC=$(this).parents('tr');
//		eventEditIndoor(e, trC_old, trC);
//	});
	e.preventDefault();
}
function getPageCost(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageCost',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentCost(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function showContentCost(response){
	var content="";
	for(var i=0; i<response.length; i++){
		var tdCost="<td><span class='cost'>"+response[i].cost+"</span></td>";
		var tdOp="<td><a href='' class='edit-cost'>Edit</a></td>"
		if(response[i].cost == 0){
			tdCost="<td><input type='text' class='form-control small'/></td>";
			tdOp="<td><a href='' class='save-cost'>Save</a></td>";
		}
		content += "<tr>"
				+ "<td style='display: none'><span class='id-cost'>"+response[i].idCost+"</span></td>"
				+ "<td><span>"+response[i].inDoor.nameDoor+"</span></td>"
				+ "<td><span>"+response[i].outDoor.nameDoor+"</span></td>"
				+ tdCost 
				+ tdOp
				+"</tr>"
	}
	$('.rowCost_data').html(content);
	$('.save-cost').click(function(e){
		var trC = $(this).parents('tr');
		var idCost = trC.find('.id-cost').html();
		var cost = trC.find('.form-control').val();
		editCost(idCost, cost);
		e.preventDefault();
	});
	$('.edit-cost').click(function(e){
		var trC = $(this).parents('tr');
		var idCost = trC.find('.id-cost').html();
		var cost = trC.find('.cost').html();
		trC.find('.cost').parent().html("" +
				"<input type='text' class='form-control small' value='"+cost+"'/>");
		$(this).parent().html("<td><a href='' class='save-cost'>Save</a></td>");
		$('.save-cost').click(function(e){
			var trC = $(this).parents('tr');
			var idCost = trC.find('.id-cost').html();
			var cost = trC.find('.form-control').val();
			editCost(idCost, cost);
			e.preventDefault();
		});
		e.preventDefault();
	});
}
function editCost(idCost, cost){
	$.ajax({
        type: "POST",
        data: "idCost=" + idCost + "&cost=" + cost + "&currentPage=" + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editCost',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentCost(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function showContentInDoor(response){
	var content="";
	for(var i=0; i<response.length; i++){
		if(response[i].status == 0) status = "Processing";
		else if(response[i].status == 1) status = "Active";
		else if(response[i].status == 2) status = "InActive";
		else status = "Maintenance";
		content += "<tr>"
			+ "<td style='display: none'><span class='idInDoor'>"+response[i].idDoor+"</span>"
			+ "<td><span class='nameD'>"+response[i].nameDoor+"</span></td>"
			+ "<td><span class='capD'>"+response[i].capacity+"</span></td>"
			+ "<td><span class='statusD'>"+status+"</span></td>"
			+ "<td><a href='' class='edit-indoor'>Edit</a></td>"
			+ "</tr>";
	}
	$('.rowI_data').html(content);
	$('.edit-indoor').click(function(e){
		var trC=$(this).parents('tr');
		var idInDoor = trC.find('.idInDoor').html();
		var nameD = trC.find('.nameD').html();
		var capD = trC.find('.capD').html();
		var statusD = trC.find('.statusD').html();		
		var content="";
		content += "<td><input type='text' value='"+nameD+"' class='form-control nameD'></td>" 
				+ "<td><input type='text' value='"+capD+"' class='form-control capD' style='width: 55%'></td>"
				+ "<td>" 
					+ "<select name='statusD' class='form-control statusD' style='width: 105%'>"
						+ "<option value='0'>Processing</option>"
						+ "<option value='1'>Active</option>"
						+ "<option value='2'>InActive</option>"
						+ "<option value='3'>Maintenance</option>"
					+ "</select>" 
				+ "</td>"
				+ "<td><a href='' class='save-indoor'>Save</a></td>";
		trC.html(content);
		$('.save-indoor').click(function(e){
			trC=$(this).parents('tr');
			nameD = trC.find('.nameD').val(); capD = trC.find('.capD').val(); 
			statusD = trC.find('.statusD').val();
			editInDoor(idInDoor, nameD, capD, statusD);
			e.preventDefault();
		});
		e.preventDefault();
	});
}
function showContentOutDoor(response){
	var content="";
	for(var i=0; i<response.length; i++){
		if(response[i].status == 0) status = "Processing";
		else if(response[i].status == 1) status = "Active";
		else if(response[i].status == 2) status = "InActive";
		else status = "Maintenance";
		content += "<tr>"
			+ "<td style='display: none'><span class='idInDoor'>"+response[i].idDoor+"</span>"
			+ "<td><span class='nameD'>"+response[i].nameDoor+"</span></td>"
			+ "<td><span class='capD'>"+response[i].capacity+"</span></td>"
			+ "<td><span class='statusD'>"+status+"</span></td>"
			+ "<td><a href='' class='edit-outdoor'>Edit</a></td>"
			+ "</tr>";
	}
	$('.rowO_data').html(content);
	$('.edit-outdoor').click(function(e){
		var trC=$(this).parents('tr');
		var idInDoor = trC.find('.idInDoor').html();
		var nameD = trC.find('.nameD').html();
		var capD = trC.find('.capD').html();
		var statusD = trC.find('.statusD').html();
		var content="";
		content += "<td><input type='text' value='"+nameD+"' class='form-control nameD'></td>" 
				+ "<td><input type='text' value='"+capD+"' class='form-control capD' style='width: 55%'></td>"
				+ "<td>" 
					+ "<select name='statusD' class='form-control statusD' style='width: 105%'>"
						+ "<option value='0'>Processing</option>"
						+ "<option value='1'>Active</option>"
						+ "<option value='2'>InActive</option>"
						+ "<option value='3'>Maintenance</option>"
					+ "</select>" 
				+ "</td>"
				+ "<td><a href='' class='save-outdoor'>Save</a></td>";
		trC.html(content);
		$('.save-outdoor').click(function(e){
			trC=$(this).parents('tr');
			nameD = trC.find('.nameD').val(); capD = trC.find('.capD').val(); 
			statusD = trC.find('.statusD').val();
			editOutDoor(idInDoor, nameD, capD, statusD);
			e.preventDefault();
		});
		e.preventDefault();
	});
}
function insertInDoor(){
	$.ajax({
        type: "POST",
        data: $("#addID").serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertInDoor',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentInDoor(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function editInDoor(idInDoor, nameD, capD, statusD){
	$.ajax({
        type: "POST",
        data: 'idInDoor=' + idInDoor + '&nameD=' + nameD + '&capD=' + capD + '&statusD=' + statusD,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editInDoor',
        success: function (response) {
        	if(response == ''){
        		alert("You need to setup the cost of the door before transition active state");
        	}else{
        		showContentInDoor(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function insertOutDoor(){
	$.ajax({
        type: "POST",
        data: $("#addOD").serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertOutDoor',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showContentOutDoor(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function editOutDoor(idOutDoor, nameD, capD, statusD){
	$.ajax({
        type: "POST",
        data: 'idOutDoor=' + idOutDoor + '&nameD=' + nameD + '&capD=' + capD + '&statusD=' + statusD,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editOutDoor',
        success: function (response) {
        	if(response == ''){
        		alert("You need to setup the cost of the door before transition active state");
        	}else{
        		showContentOutDoor(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/**********************************************************************************************************
 **********************************************************************************************************
 **********************************************************************************************************
 */
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
				+ "<td>"+ response[i].volumn +"</td>"
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
        		showContentInVehicle(response);
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
				+ "<td>"+ response[i].demand +"</td>"
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
        		showContentOutVehicle(response);
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
var flagI=false;
$(document).ready(function(){
	/*--------------------------------------------------------------------------------------
	 * InVehicle */
	$(".gIDoor input[type='radio']").click(function (){
		eventAssignInDoor($(this));
	});
	$('.page-invehicle-assign-door .page_index').click(function(){
		console.log(flagI);
		var currentPage=$(this).attr('data-index');
		for(var i=0; i<sizeIDoor; i++){
			arrID[i] = 'iDoor-' + i;
			uIDoor[i] = $('.uIDoor-' + i).html();
			cIDoor[i] = $('.cIDoor-' + i).html();
		}
		getPageInVehicle_whereAssignDoor(currentPage);
	});
	/*--------------------------------------------------------------------------------------
	 * OutVehicle*/
	$(".gODoor input[type='radio']").click(function (){
		eventAssignOutDoor($(this));
	});
	$('.page-outvehicle-assign-door .page_index').click(function(){
		var currentPage=$(this).attr('data-index');
		for(var i=0; i<sizeODoor; i++){
			arrOD[i] = 'oDoor-' + i;
			uODoor[i] = $('.uODoor-' + i).html();
			cODoor[i] = $('.cODoor-' + i).html();
		}
		getPageOutVehicle_whereAssignDoor(currentPage);
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
function eventAssignInDoor(This){
	flagI=true;
	var volumn = This.parents('.gIDoor').find('.volumn').html();
	var doorChoose = This.parents('.gIDoor').find("[data-flag = 'choose']");
	var iDoor = doorChoose.attr('data-index');
	var nGroup = doorChoose.attr('name');
	// Ban dau chua ton tai thuoc tinh flag -> lan chon dau tien se gan bien flag 
	if(doorChoose.attr('class') == null){
		var iDoor = This.attr('data-index');
		uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
		This.attr('data-flag', 'choose');
	// Lan tiep theo da ton tai bien flag se xoa bien flag cua radio cu va tao them bien flag vao radio moi
	}else{
		if(This.attr('data-index') != iDoor || This.attr('name') == nGroup){
			var iDoor = doorChoose.attr('data-index');
			uIDoor[iDoor] = uIDoor[iDoor] - volumn;
			doorChoose.removeAttr('data-flag');
			
			iDoor = This.attr('data-index');
			uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
			This.attr('data-flag', 'choose');
		}else{// Cung chon 1 cua
			This.attr('data-flag', 'choose');
			iDoor = This.attr('data-index');
			uIDoor[iDoor] = uIDoor[iDoor] - (-volumn);
		}
	}
	for(var i=0; i<sizeIDoor; i++){
		$('.' + arrID[i]).parent().find('.remain').html(cIDoor[i] - uIDoor[i]);
	}
	objective = getObjective();
	$('.total-cost').html(objective);
}
function eventAssignOutDoor(This){
	var demand = This.parents('.gODoor').find('.demand').html();
	var doorChoose = This.parents('.gODoor').find("[data-flag = 'choose']");
	var oDoor = doorChoose.attr('data-index');
	var nGroup = doorChoose.attr('name');
	if(doorChoose.attr('class') == null){
		var oDoor = This.attr('data-index');
		uODoor[oDoor] = uODoor[oDoor] - (-demand);
		This.attr('data-flag', 'choose');
	}else{
		if(This.attr('data-index') != oDoor || This.attr('name') == nGroup){
			var oDoor = doorChoose.attr('data-index');
			uODoor[oDoor] = uODoor[oDoor] - demand;
			doorChoose.removeAttr('data-flag');
			
			oDoor = This.attr('data-index');
			uODoor[oDoor] = uODoor[oDoor] - (-demand);
			This.attr('data-flag', 'choose');
		}else{
			This.attr('data-flag', 'choose');
			oDoor = This.attr('data-index');
			uODoor[oDoor] = uODoor[oDoor] - (-demand);
		}
	}
	for(var i=0; i<sizeODoor; i++){
		$('.' + arrOD[i]).parent().find('.remain').html(cODoor[i] - uODoor[i]);
	}
	objective = getObjective();
	$('.total-cost').html(objective);
}
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
//			console.log('Trip=' + trip);
//			console.log('Cost=' + cost)
//			console.log('Obj=' + trip*cost);
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
//        		for(var i=0; i<sizeIDoor; i++){
//        			arrID[i] = 'iDoor-' + i;
//        			uIDoor[i] = $('.uIDoor-' + i).html();
//        			cIDoor[i] = $('.cIDoor-' + i).html();
//        		}
//        		for(var i=0; i<sizeODoor; i++){
//        			arrOD[i] = 'oDoor-' + i;
//        			uODoor[i] = $('.uODoor-' + i).html();
//        			cODoor[i] = $('.cODoor-' + i).html();
//        		}
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
//    			for(var i=0; i<sizeIDoor; i++){
//    				$('.' + arrID[i]).parent().find('.remain').html(cIDoor[i] - uIDoor[i]);
//    			}
    			for(var i=0; i<sol_outvehicles.length; i++){
    				var doorChoose = $(".gODoor [name = groupO-"+i+"][data-index="+sol_outvehicles[i]+"]");
    				doorChoose.parent().find('.remain').css('background', '#B0E0E6');
//    				doorChoose.attr('checked','checked');
//    				doorChoose.attr('data-flag','choose');
//    				demand = doorChoose.parents('.gODoor').find('.demand').html();
//    				var oDoor = sol_outvehicles[i];
//    				uODoor[oDoor] = uODoor[oDoor] - (-demand);
    			}
//    			for(var i=0; i<sizeODoor; i++){
//    				$('.' + arrOD[i]).parent().find('.remain').html(cODoor[i] - uODoor[i]);
//    			}
    			$('.total-cost-ai').html(objective);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function showContentInVehicle_whereAssignDoor(response){
	flagI=false;
	arrIDoor=response[0];
	arrIV=response[1];
	var content="";	
	content+="<tr><td><span class='size-in-vehicle' data-bind='"+arrIV.length+"'></span></td></tr>";
	for(var i=0; i<arrIV.length; i++){
		content+="<tr class='gIDoor'>";
		content+="<td>"+arrIV[i].vehicleCode+"</td>"
			+ "<td><span class='volumn' data-bind='"+arrIV[i].volumn+"'>"+arrIV[i].volumn+"</span>"
				+ "<span class='volumn-"+i+"' data-bind='"+arrIV[i].volumn+"'></span>"
			+ "</td>"
			+ "<td>"+arrIV[i].arrivalTime+"</td>";
		for(var j=0; j<arrIDoor.length; j++){
			var remain=arrIDoor[j].capacity;
			for(var k=0; k<arrIV.length; k++){
				if(arrIDoor[j].idDoor == arrIV[k].door.idDoor){
					remain=remain-arrIV[k].volumn;
				}
			}
			content+="<td style='text-align: center;'>";
			if(arrIV[i].door.idDoor == arrIDoor[j].idDoor){
				content+="<input type='radio' name='groupI-"+i+"' " 
						+ "class='iDoor-"+j+"' " +
						+ "data-idInVehicle='"+arrIV[i].idInVehicle+"' " +
						+ "data-index='"+j+"' data-idDoor='"+arrIDoor[j].idDoor+"' " 
						+ "checked='checked' data-flag='choose'/>";
			}else{
				content+="<input type='radio' " 
						+ "name='groupI-"+i+"' class='iDoor-"+j+"' " 
						+ "data-idInVehicle='"+arrIV[i].idInVehicle+"' " 
						+ "data-index='"+j+"' " 
						+ "data-idDoor='"+arrIDoor[j].idDoor+"'/>";
			}
			content+="<span class='remain'>"+remain+"</span>";
			content+"</td>";
		}
		content+="</tr>"
	}
	$('.rowI_data').html(content);
	$(".gIDoor input[type='radio']").click(function (){
		eventAssignInDoor($(this));
	});
}
function getPageInVehicle_whereAssignDoor(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle_whereAssignDoor',
        success: function (response) {
        	showContentInVehicle_whereAssignDoor(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function showContentOutVehicle_whereAssignDoor(response){
	arrODoor=response[0];
	arrOV=response[1];
	var content="";	
	content+="<tr><td><span class='size-out-vehicle' data-bind='"+arrOV.length+"'></span></td></tr>";
	for(var i=0; i<arrOV.length; i++){
		content+="<tr class='gODoor'>";
		content+="<td>"+arrOV[i].vehicleCode+"</td>"
			+ "<td><span class='demand' data-bind='"+arrOV[i].demand+"'>"+arrOV[i].demand+"</span>"
				+ "<span class='demand-"+i+"' data-bind='"+arrOV[i].demand+"'></span>"
			+ "</td>"
			+ "<td>"+arrOV[i].arrivalTime+"</td>";
		for(var j=0; j<arrODoor.length; j++){
			var remain=arrODoor[j].capacity;
			for(var k=0; k<arrOV.length; k++){
				if(arrODoor[j].idDoor == arrOV[k].door.idDoor){
					remain=remain-arrOV[k].demand;
				}
			}
			content+="<td style='text-align: center;'>";
			if(arrOV[i].door.idDoor == arrODoor[j].idDoor){
				content+="<input type='radio' name='groupO-"+i+"' " 
						+ "class='oDoor-"+j+"' " +
						+ "data-idOutVehicle='"+arrOV[i].idOutVehicle+"' " +
						+ "data-index='"+j+"' data-idDoor='"+arrODoor[j].idDoor+"' " 
						+ "checked='checked' data-flag='choose'/>";
			}else{
				content+="<input type='radio' " 
						+ "name='groupO-"+i+"' class='oDoor-"+j+"' " 
						+ "data-idOutVehicle='"+arrOV[i].idOutVehicle+"' " 
						+ "data-index='"+j+"' " 
						+ "data-idDoor='"+arrODoor[j].idDoor+"'/>";
			}
			content+="<span class='remain'>"+remain+"</span>";
			content+"</td>";
		}
		content+="</tr>"
	}
	$('.rowO_data').html(content);
	$(".gODoor input[type='radio']").click(function (){
		eventAssignOutDoor($(this));
	});
}
function getPageOutVehicle_whereAssignDoor(currentPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle_whereAssignDoor',
        success: function (response) {
        	showContentOutVehicle_whereAssignDoor(response);
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
$(document).ready(function(){
	var status=0;
	$('.status-vehicle').change(function(){
		status = $(this).val();
		getPageMtrStatus(status);
	});
	$('.pageI-status .page_index').click(function(){
		var currentPage = $(this).attr('data-index');
		getPageInVehicle_byStatus(currentPage, status);		
	});
});
function showContent_MtrStatus(response){
	var contentI="";
	var pageIV=response[0];
	var pageOV=response[1];
	for(var i=0; i<pageIV.length; i++){
		if(pageIV[i].status ==0) status="Waitting Assign Door";
		else if(pageIV[i].status ==1) status="Watting Unload";
		else if(pageIV[i].status ==2) status="Is Unloading";
		else status="UnLoading is Finish";
		var nameDoor=pageIV[i].door.nameDoor;
		if(pageIV[i].door.nameDoor == null) nameDoor="";
		contentI+=
			"<tr>"
				+ "<td>"+pageIV[i].vehicleCode+"</td>"
				+ "<td>"+nameDoor+"</td>"
				+ "<td>"+status+"</td>"
			+ "</tr>"
	}
	$('.rowI_data').html(contentI);
	var contentO="";
	for(var i=0; i<pageOV.length; i++){
		if(pageOV[i].status ==0) status="Waitting Assign Door";
		else if(pageOV[i].status ==1) status="Watting load";
		else if(pageOV[i].status ==2) status="Is Loading";
		else status="Loading is Finish";
		nameDoor=pageOV[i].door.nameDoor; 
		if(pageOV[i].door.nameDoor == null) nameDoor="";
		contentO+=
			"<tr>"
				+ "<td>"+pageOV[i].vehicleCode+"</td>"
				+ "<td>"+nameDoor+"</td>"
				+ "<td>"+status+"</td>"
			+ "</tr>"
	}
	$('.rowO_data').html(contentO);
}
function getPageMtrStatus(status){
	$.ajax({
        type: "POST",
        data: 'status=' + status,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageMtrStatus',
        success: function (response) {
        	showContent_MtrStatus(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function showContentInVehicle_byStatus(response){
	var content="";
	var pageIV=response;
	for(var i=0; i<pageIV.length; i++){
		if(pageIV[i].status ==0) status="Waitting Assign Door";
		else if(pageIV[i].status ==1) status="Watting Unload";
		else if(pageIV[i].status ==2) status="Is Unloading";
		else status="UnLoading is Finish";
		var nameDoor=pageIV[i].door.nameDoor;
		if(pageIV[i].door.nameDoor == null) nameDoor="";
		content+=
			"<tr>"
				+ "<td>"+pageIV[i].vehicleCode+"</td>"
				+ "<td>"+nameDoor+"</td>"
				+ "<td>"+status+"</td>"
			+ "</tr>"
	}
	$('.rowI_data').html(content);
}
function getPageInVehicle_byStatus(currentPage, status){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage + '&status=' + status,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle_byStatus',
        success: function (response) {
        	showContentInVehicle_byStatus(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function showContentOutVehicle_byStatus(response){
	var content="";
	var pageOV=response;
	for(var i=0; i<pageOV.length; i++){
		if(pageOV[i].status ==0) status="Waitting Assign Door";
		else if(pageOV[i].status ==1) status="Watting Unload";
		else if(pageOV[i].status ==2) status="Is Unloading";
		else status="UnLoading is Finish";
		var nameDoor=pageOV[i].door.nameDoor;
		if(pageOV[i].door.nameDoor == null) nameDoor="";
		content+=
			"<tr>"
				+ "<td>"+pageOV[i].vehicleCode+"</td>"
				+ "<td>"+nameDoor+"</td>"
				+ "<td>"+status+"</td>"
			+ "</tr>"
	}
	$('.rowI_data').html(content);
}
function getPageOutVehicle_byStatus(currentPage, status){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage + '&status=' + status,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle_byStatus',
        success: function (response) {
        	showContentOutVehicle_byStatus(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
$(document).ready(function(){
	$('.s-vehicle').select2({
		placeholder: "Please select code vehicle",
		allowClear: true,
	});
	$('#addTF').submit( function (e){
		insertTransferTime();
		$('#btnTF').attr('disabled', 'disabled');
		e.preventDefault();
	});
	$('.default-date-picker').datepicker().on('changeDate', function(ev) {
		var date = $(this).val();
		getDataTransfer(date);
	});
});
function showDataTransfer(response){
	var listIV=response[0];
	var listOV=response[1];
	var listPT=response[2];
	var opIV="";
	opIV+="<select class='s-vehicle' name='idInVehicle'>";
	for(var i=0; i<listIV.length; i++){
		opIV+="<option value='"+listIV[i].idInVehicle+"'>"+listIV[i].vehicleCode+"</option>";
	}
	opIV+="<select>";
	$('.data-select-in-vehicle').html(opIV);
	
	var opOV="";
	opOV+="<select class='s-vehicle' name='idOutVehicle'>";
	for(var i=0; i<listOV.length; i++){
		opOV+="<option value='"+listOV[i].idOutVehicle+"'>"+listOV[i].vehicleCode+"</option>";
	}
	opOV+="<select>";
	$('.data-select-out-vehicle').html(opOV);
	$('.s-vehicle').select2({
		placeholder: "Please select code vehicle",
		allowClear: true,
	});
	
	var content="";
	for(var i=0; i<listPT.length; i++){
		content+=
			"<tr>"
				+ "<td>"+listPT[i].iVehicle.vehicleCode+"</td>"
				+ "<td>"+listPT[i].oVehicle.vehicleCode+"</td>"
				+ "<td>"+listPT[i].transfer+"</td>"
			+ "</tr>"
	}
	$('.row_data').html(content);
}
function getDataTransfer(date){
	$.ajax({
        type: "POST",
        data: 'date=' + date,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getDataTransfer',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showDataTransfer(response);
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