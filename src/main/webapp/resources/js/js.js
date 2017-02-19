function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}
var contextPath=getContextPath();
var url = window.location.pathname;
var currentPage = 1;
$(document).ready(function(e){
	$('#ac-wrapper').css('display', 'none');
	$('#addID').submit(function(e){
		var addID=$(this);
		confirmDialog(addID, e).then(function(choose){
			if(choose == 'true'){
				validateInsertDoor(addID);
				var isValid=addID.valid();
				if(isValid){
					insertInDoor(); e.preventDefault();
				}
			}
		});
	});
	$('#addOD').submit(function(e){
		var addOD=$(this);	
		confirmDialog(addOD, e).then(function(choose){
			if(choose == 'true'){
				validateInsertDoor(addOD);
				var isValid=addOD.valid();
				if(isValid){
					insertOutDoor(); e.preventDefault();
				}
			}
		});
	});
	$('.edit-indoor').click(function(e){
		var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
		var trC=$(this).parents('tr');
		eventEditIndoor(e, trC_old, trC);
	});	
	$('.edit-outdoor').click(function(e){
		var trC_old=$('.rowO_data').find('.save-outdoor').parents('tr');
		var trC=$(this).parents('tr');
		eventEditOutdoor(e, trC_old, trC);
	});
	$('.del-indoor').click(function(e){
		eventDelInDoor($(this), e);
	});
	$('.del-outdoor').click(function(e){
		eventDelOutDoor($(this), e);
	});
	$('.setup-cost-indoor').click(function(){
		var idDoor=$(this).parents('tr').find('.idInDoor').html();
		getInforCostByIdInDoor(idDoor)
		dialogSC.dialog('open');
	});
	$('.setup-cost-outdoor').click(function(){
		var idDoor=$(this).parents('tr').find('.idOutDoor').html();
		getInforCostByIdOutDoor(idDoor)
		dialogSC.dialog('open');
	});
	var dialogSC=$( '.dialog-setup-cost' ).dialog({
		title: "Setup Cost Door",
		autoOpen: false,
		modal : true,     
     	buttons: {
     		Close: function() {
     			$(this).dialog('close');
     		},
     	},
     	width: 550,
    });
});
function eventEditCost(This){
	var trC = This.parents('tr');
	var idCost = This.attr('data-bind');
	var cost = trC.find('.cost').html();
	trC.find('.cost').parent().html(
		"<input type='text' class='form-control cost' value='"+cost+"'>"
	);
	trC.find('.edit-cost').parents('td').html(
		"<span class='save-cost option' data-bind='"+idCost+"'>"
			+ "<i class='fa fa-floppy-o'></i>"
		+ "</span>"
	);	
	trC.find('.cancel-cost').parents('td').html(
		"<span class='cancel-cost option'>"
			+ "<i class='fa fa-times'></i>"
		+ "</span>"
	);	
	$('.save-cost').click(function(){
		editCost($(this));
	});
	$('.cancel-cost').click(function(){
		trC.find('.cost').parents('td').html("<span class='cost'>"+cost+"</span>")
		trC.find('.save-cost').parents('td').html(
			"<span class='edit-cost option' data-bind='"+idCost+"'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
		);
		trC.find('.cancel-cost').html('');
		$('.edit-cost').click(function(){
    		eventEditCost($(this));
    	});
	});
}
function editCost(This){
	trC = This.parents('tr');
	var idCost = This.attr('data-bind');
	var cost = trC.find('.cost').val();
	$.ajax({
        type: "POST",
        data: "idCost=" + idCost + "&cost=" + cost,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editCost',
        success: function (response) {
        	if(response == true){
        		trC.find('.cost').parent().html("<span class='cost'>"+cost+"</span>");
            	trC.find('.save-cost').parents('td').html(
            			"<span class='edit-cost option' data-bind='"+idCost+"'>"
            				+ "<i class='fa fa-pencil-square-o'></i>"
        				+ "</span>"
            	);
            	trC.find('.cancel-cost').html('');
            	$('.edit-cost').click(function(){
            		eventEditCost($(this));
            	});	
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function showContentInforCostByIdDoor(response){
	var arr = response;
	var content="";
	for(var i=0; i<arr.length; i++){
		content += "<tr>"
						+ "<td>"+arr[i].inDoor.nameDoor+"</td>"
						+ "<td>"+arr[i].outDoor.nameDoor+"</td>";
		if(arr[i].cost > 0){
			content += "<td><span class='cost'>"+arr[i].cost+"</span></td>"
					+ "<td><span class='edit-cost option' data-bind='"+arr[i].idCost+"'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</td>"
					+ "<td><span class='cancel-cost'></span></td>";
		}else{
			content += "<td><input type='text' class='form-control cost' style='padding: 1px 8px'></td>"
					+ "<td><span class='save-cost option' data-bind='"+arr[i].idCost+"'>"
						+ "<i class='fa fa-floppy-o'></i>"
					+ "</td>"
					+ "<td><span class='cancel-cost'></span></td>";
		}
		content += "</tr>";
	}
	$('.rowSetCost').html(content);
	$('.edit-cost').click(function(){
		eventEditCost($(this));
	});
	$('.save-cost').click(function(){
		editCost($(this));
	});
	
	
}
function getInforCostByIdInDoor(idDoor){
	$.ajax({
        type: "POST",
        data: 'idDoor=' + idDoor,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getInforCostByIdIndoor',
        success: function (response) {
        	showContentInforCostByIdDoor(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function getInforCostByIdOutDoor(idDoor){
	$.ajax({
        type: "POST",
        data: 'idDoor=' + idDoor,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getInforCostByIdOutDoor',
        success: function (response) {
        	showContentInforCostByIdDoor(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function eventDelInDoor(This, e){
	var delID=This;
	confirmDialog(delID, e).then(function(choose){
		if(choose == 'true'){
			delInDoor(delID);
		}
	});
}
function eventEditIndoor(e, trC_old, trC){
	var idInDoor_old = trC_old.find('.idInDoor').html();
	var capD_old = trC_old.find('.capD').val();
	var statusD_old = trC_old.find('.statusD').val();
	 
	if(statusD_old == 0) statusD_old = inactiveStatus;
	else if(statusD_old == 1) statusD_old = activeStatus;
	
	/*TrNew*/
	var idInDoor = trC.find('.idInDoor').html();
	var nameD = trC.find('.nameD').html();
	var capD = trC.find('.capD').html();
	var statusD = trC.find('.statusD').html();
	var content="";
	content += "<td style='display: none'><span class='idInDoor'>"+idInDoor+"</span></td>" 
			+ "<td><span class='nameD'>"+nameD+"</span></td>" 
			+ "<td><input type='text' value='"+capD+"' size='5' class='form-control capD' style='padding: 1px 8px'></td>"
			+ "<td>" 
				+ "<select name='statusD' class='form-control statusD' style='padding: 1px 8px' data-bind='"+statusD+"'>"
					+ "<option value='0'>"+inactiveStatus+"</option>"
					+ "<option value='1'>"+activeStatus+"</option>"
				+ "</select>" 
			+ "</td>"
			+ "<td><span class='save-indoor option-vehicle'>"
					+ "<i class='fa fa-floppy-o'></i>"
				+ "</span>"
			+ "</td>"
			+ "<td><span class='cancel-indoor option-vehicle'>"
					+ "<i class='fa fa-times'></i>"
				+ "</span>"
			+ "</td>"
			+ "<td class='setup-cost-indoor'></td>"
			;
	trC.html(content);
	$('.save-indoor').click(function(e){
		var saveID=$(this);
		confirmDialog(saveID, e).then(function(choose){
			if(choose == 'true'){
				trC=saveID.parents('tr');
				capD = trC.find('.capD').val(); 
				statusD = trC.find('.statusD').val();
				var isValid = validateEditDoor(trC, capD);
				if(isValid){
					editInDoor(trC, idInDoor, capD, statusD);
				}
				e.preventDefault();
			}
		});
	});
	$('.cancel-indoor').click(function(){
		var statusD = $(this).parents('tr').find('.statusD').attr('data-bind'); 
		trC.find('.capD').parent().html("<span class='capD'>"+capD+"</span>");
		var status = inactiveStatus;
		if(statusD == 1){
			status = activeStatus;
		}
		trC.find('.statusD').parent().html("<span class='statusD'>"+status+"</span>");
		trC.find('.save-indoor').parent().html(
			"<span class='edit-indoor option'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
		);
		trC.find('.cancel-indoor').parent().html(
			"<span class='del-indoor option'>"
				+ "<i class='fa fa-trash-o'></i>"
			+ "</span>"
		);
		trC.find('.setup-cost-indoor').html(
			"<span class='setup-cost-indoor option'>"
				+ "<i class='fa fa-cog'></i>"
			+ "</span>"
		)
		$('.edit-indoor').click(function(e){
			var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
			var trC=$(this).parents('tr');
			eventEditIndoor(e, trC_old, trC);
		});
		$('.del-indoor').click(function(e){
			eventDelInDoor($(this), e);
		});
		$('.setup-cost-indoor').click(function(){
			var idDoor=$(this).parents('tr').find('.idInDoor').html();
			getInforCostByIdInDoor(idDoor)
			$('.dialog-setup-cost').dialog('open');
		});
	});
	e.preventDefault();
}
function eventEditOutdoor(e, trC_old, trC){
	/*TrOld*/
	var idOutDoor_old = trC_old.find('.idOutDoor').html();
	var capD_old = trC_old.find('.capD').val();
	var statusD_old = trC_old.find('.statusD').val();
	 
	if(statusD_old == 0) statusD_old = inactiveStatus;
	else if(statusD_old == 1) statusD_old = activeStatus;
	
	/*TrNew*/
	var idOutDoor = trC.find('.idOutDoor').html();
	var nameD = trC.find('.nameD').html();
	var capD = trC.find('.capD').html();
	var statusD = trC.find('.statusD').html();
	var content="";
	content += "<td style='display: none'><span class='idOutDoor'>"+idOutDoor+"</span></td>" 
			+ "<td><span class='nameD'>"+nameD+"</span></td>" 
			+ "<td><input type='text' value='"+capD+"' size='5' class='form-control capD' style='padding: 1px 8px'></td>"
			+ "<td>" 
				+ "<select name='statusD' class='form-control statusD' style='padding: 1px 8px' data-bind='"+statusD+"'>"
					+ "<option value='0'>"+inactiveStatus+"</option>"
					+ "<option value='1'>"+activeStatus+"</option>"
				+ "</select>" 
			+ "</td>"
			+ "<td><span class='save-outdoor option-vehicle'>"
					+ "<i class='fa fa-floppy-o'></i>"
				+ "</span>"
			+ "</td>"
			+ "<td><span class='cancel-outdoor option-vehicle'>"
					+ "<i class='fa fa-times'></i>"
				+ "</span>"
			+ "</td>"
			+ "<td class='setup-cost-outdoor'></td>";
	trC.html(content);
	$('.save-outdoor').click(function(e){
		var saveOD=$(this);
		confirmDialog(saveOD, e).then(function(choose){
			if(choose == 'true'){
				trC=saveOD.parents('tr');
				capD = trC.find('.capD').val(); 
				statusD = trC.find('.statusD').val();
				var isValid = validateEditDoor(trC, capD);
				if(isValid){
					editOutDoor(trC, idOutDoor, capD, statusD);
				}
				e.preventDefault();
			}
		});
	});
	$('.cancel-outdoor').click(function(){
		statusD = $(this).parents('tr').find('.statusD').attr('.data-bind');
		trC.find('.capD').parent().html("<span class='capD'>"+capD+"</span>");
		var status = inactiveStatus;
		if(statusD == 1){
			status = activeStatus;
		}
		trC.find('.statusD').parent().html("<span class='statusD'>"+status+"</span>");
		trC.find('.save-outdoor').parent().html(
			"<span class='edit-outdoor option'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
		);
		trC.find('.cancel-outdoor').parent().html(
			"<span class='del-outdoor option'>"
				+ "<i class='fa fa-trash-o'></i>"
			+ "</span>"
		);
		trC.find('.setup-cost-outdoor').html(
			"<span class='setup-cost-outdoor option'>"
				+ "<i class='fa fa-cog'></i>"
			+ "</span>"
		)
		$('.edit-outdoor').click(function(e){
			var trC_old=$('.rowO_data').find('.save-outdoor').parents('tr');
			var trC=$(this).parents('tr');
			eventEditOutdoor(e, trC_old, trC);
		});
		$('.del-outdoor').click(function(e){
			eventDelOutDoor($(this), e);
		});
		$('.setup-cost-outdoor').click(function(){
			var idDoor=$(this).parents('tr').find('.idOutDoor').html();
			getInforCostByIdOutDoor(idDoor)
			$('.dialog-setup-cost').dialog('open');
		});
	});
	e.preventDefault();
}
function eventDelOutDoor(This, e){
	var delOD=This;
	confirmDialog(delOD, e).then(function(choose){
		if(choose == 'true'){
			delOutDoor(delOD);
		}
	});
}
function delInDoor(This){
	var idDoor=This.parents('tr').find('.idInDoor').html();
	$.ajax({
        type: "POST",
        data: 'idDoor=' + idDoor,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delInDoor',
        success: function (response) {
        	if(response == true){
        		This.parents('tr').remove();
        	}else{
        		This.parents('table').before("<section class='msg-error'>"+errDeleteDoor+"</section>");
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
		if(response[i].status == 0) status = inactiveStatus;
		else if(response[i].status == 1) status = activeStatus;
		
		content += "<tr>"
					+ "<td style='display: none'><span class='idInDoor'>"+response[i].idDoor+"</span>"
					+ "<td><span class='nameD'>"+response[i].nameDoor+"</span></td>"
					+ "<td><span class='capD'>"+response[i].capacity+"</span></td>"
					+ "<td><span class='statusD'>"+status+"</span></td>"
					+ "<td><span class='edit-indoor option'>"
							+ "<i class='fa fa-pencil-square-o'></i>"
						+ "</span>"
					+ "</td>"
					+ "<td><span class='del-indoor option'>"
							+ "<i class='fa fa-trash-o'></i>"
						+ "</span>"
					+ "</td>"
					+ "<td><span class='setup-cost-indoor option'>"
							+ "<i class='fa fa-cog'></i>"
						+ "</span>"
					+ "</td>";
				+ "</tr>";
	}
	$('.rowI_data').html(content);
	$('.edit-indoor').click(function(e){
		var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
		var trC=$(this).parents('tr');
		eventEditIndoor(e, trC_old, trC);
	});
	$('.del-indoor').click(function(e){
		eventDelInDoor($(this), e);
	});
	$('.setup-cost-indoor').click(function(){
		var idDoor=$(this).parents('tr').find('.idInDoor').html();
		getInforCostByIdInDoor(idDoor)
		$('.dialog-setup-cost').dialog('open');
	});
}
function showContentOutDoor(response){
	var content="";
	for(var i=0; i<response.length; i++){
		if(response[i].status == 0) status = inactiveStatus;
		else if(response[i].status == 1) status = activeStatus;
		content += "<tr>"
						+ "<td style='display: none'><span class='idOutDoor'>"+response[i].idDoor+"</span>"
						+ "<td><span class='nameD'>"+response[i].nameDoor+"</span></td>"
						+ "<td><span class='capD'>"+response[i].capacity+"</span></td>"
						+ "<td><span class='statusD'>"+status+"</span></td>"
						+ "<td><span class='edit-outdoor option-vehicle'>"
								+ "<i class='fa fa-pencil-square-o'></i>"
							+ "</span>"
						+ "</td>"
						+ "<td><span class='del-outdoor option-vehicle'>"
								+ "<i class='fa fa-trash-o'></i>"
							+ "</span>"
						+ "</td>"
						+ "<td><span class='setup-cost-outdoor option'>"
								+ "<i class='fa fa-cog'></i>"
							+ "</span>"
						+ "</td>"
					+ "</tr>";
	}
	$('.rowO_data').html(content);
	$('.edit-outdoor').click(function(e){
		var trC_old=$('.rowO_data').find('.save-outdoor').parents('tr');
		var trC=$(this).parents('tr');
		eventEditOutdoor(e, trC_old, trC);
	});
	$('.del-outdoor').click(function(e){
		eventDelOutDoor($(this), e);
	});
	$('.setup-cost-outdoor').click(function(){
		var idDoor=$(this).parents('tr').find('.idOutDoor').html();
		getInforCostByIdOutDoor(idDoor)
		$('.dialog-setup-cost').dialog('open');
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
        	if(getCurrentLanguage() == "en"){
        		msgErr='Name door entered already exists';
        	}else{
        		msgErr='Tên cửa nhập đã tồn tại';
        	}
        	if(response == ''){
        		$('.pn-form-indoor .msg-error').remove();
        		$('.pn-form-indoor .cmxform').before("<section class='msg-error'>"+msgErr+"</section>");
        	}else{
        		showContentInDoor(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function editInDoor(trC, idInDoor, capD, statusD){
	$.ajax({
        type: "POST",
        data: 'idInDoor=' + idInDoor + '&capD=' + capD + '&statusD=' + statusD,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editInDoor',
        success: function (response) {
        	if(response == 'success'){
        		trC.find('.capD').parent().html("<span class='capD'>"+capD+"</span>");
        		var status = inactiveStatus;
        		if(statusD == 1){
        			status = activeStatus;
        		}
        		trC.find('.statusD').parent().html("<span class='statusD'>"+status+"</span>");
        		trC.find('.save-indoor').parent().html(
    				"<span class='edit-indoor option'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</span>"
        		);
        		trC.find('.cancel-indoor').parent().html(
    				"<span class='del-indoor option'>"
						+ "<i class='fa fa-trash-o'></i>"
					+ "</span>"
        		);
        		trC.find('.setup-cost-indoor').html(
    				"<span class='setup-cost-indoor option'>"
						+ "<i class='fa fa-cog'></i>"
					+ "</span>"
        		)
        		$('.edit-indoor').click(function(e){
        			var trC_old=$('.rowI_data').find('.save-indoor').parents('tr');
        			var trC=$(this).parents('tr');
        			eventEditIndoor(e, trC_old, trC);
        		});
        		$('.del-indoor').click(function(e){
        			eventDelInDoor($(this), e);
        		});
        		$('.setup-cost-indoor').click(function(){
        			var idDoor=$(this).parents('tr').find('.idInDoor').html();
        			getInforCostByIdInDoor(idDoor)
        			$('.dialog-setup-cost').dialog('open');
        		});
        	}else if(response == 'errIsUpdate'){
        		trC.parents('table').before("<section class='msg-error'>"+errUpdate+"</section>");
        	}else if(response == 'errTransitionStatus'){
        		trC.parents('table').before("<section class='msg-error capD'>"+errTransitionActive+"</section>");
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
        	if(getCurrentLanguage() == "en"){
        		msgErr='Name door entered already exists';
        	}else{
        		msgErr='Tên cửa nhập đã tồn tại';
        	}
        	if(response == ''){
        		$('.pn-form-outdoor .cmxform').before("<section class='msg-error'>"+msgErr+"</section>")
        	}else{
        		showContentOutDoor(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function editOutDoor(trC, idOutDoor, capD, statusD){
	$.ajax({
        type: "POST",
        data: 'idOutDoor=' + idOutDoor + '&capD=' + capD + '&statusD=' + statusD,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editOutDoor',
        success: function (response) {
        	if(response == 'success'){
        		trC.find('.capD').parent().html("<span class='capD'>"+capD+"</span>");
        		var status = inactiveStatus;
        		if(statusD == 1){
        			status = activeStatus;
        		}
        		trC.find('.statusD').parent().html("<span class='statusD'>"+status+"</span>");
        		trC.find('.save-outdoor').parent().html(
    				"<span class='edit-outdoor option'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</span>"
        		);
        		trC.find('.cancel-outdoor').parent().html(
    				"<span class='del-outdoor option'>"
						+ "<i class='fa fa-trash-o'></i>"
					+ "</span>"
        		);
        		trC.find('.setup-cost-outdoor').html(
    				"<span class='setup-cost-outdoor option'>"
						+ "<i class='fa fa-cog'></i>"
					+ "</span>"
        		)
        		$('.edit-outdoor').click(function(e){
        			var trC_old=$('.rowO_data').find('.save-outdoor').parents('tr');
        			var trC=$(this).parents('tr');
        			eventEditOutdoor(e, trC_old, trC);
        		});
        		$('.del-outdoor').click(function(e){
        			eventDelOutDoor($(this), e);
        		});
        		$('.setup-cost-outdoor').click(function(){
        			var idDoor=$(this).parents('tr').find('.idOutDoor').html();
        			getInforCostByIdOutDoor(idDoor)
        			$('.dialog-setup-cost').dialog('open');
        		});
        	}else if(response == 'errIsUpdate'){
        		trC.parents('table').before("<section class='msg-error'>"+errUpdate+"</section>");
        	}else if(response == 'errTransitionStatus'){
        		trC.parents('table').before("<section class='msg-error capD'>"+errTransitionActive+"</section>");
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function delOutDoor(This){
	var idDoor=This.parents('tr').find('.idOutDoor').html();
	$.ajax({
        type: "POST",
        data: 'idDoor=' + idDoor,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delOutDoor',
        success: function (response) {
        	if(response == true){
        		This.parents('tr').remove();
        	}else{
        		This.parents('table').before("<section class='msg-error'>"+errDeleteDoor+"</section>");
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
		var addV=$(this);
		confirmDialog(addV, e).then(function(choose){
			if(choose == 'true'){
				validateInsertV(addV);
				var isValid=addV.valid();
				if(isValid){
					insertVehicle();
					e.preventDefault();
				}
			}
		});
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
				+ "<td>"+ response[i].type +"</td>"
				+ "<td>"+ response[i].company +"</td>"
				+ "<td>"+ response[i].description +"</td>"
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
        	var msgErr='';
        	if(getCurrentLanguage() == "en"){
        		msgErr='Vehicle code entered already exists';
        	}else{
        		msgErr='Biển số xe nhập đã tồn tại';
        	}
        	if(response == ''){
        		$('.form-insert-v .msg-error').remove();
        		$('.form-insert-v .cmxform').before("<section class='msg-error'>"+msgErr+"</section>");
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
	
	$('.save-unload').click(function(){
		upDataUnloadTime($(this));
	});
	$('.edit-unload').click(function(){
		eventEditUnloadTime($(this));
	});
	$('.finish-unload').click(function(){
		upFinishUnloadTime($(this));
	});
	var record=15;
	$('.recordI-unload').change(function(){
		record = $(this).val();
		getPageInVehicle_whereUnloadStatus(currentPage, record);
	});
	$('.pageI_unload .page_index').click( function() {
		var curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			currentPage = curPage;
			getPageInVehicle_whereUnloadStatus(currentPage, record);
		}
	});
	$('.pageI_unload .next').click(function(){
		var numPage=$('.pageI_unload .num-page').attr('data-bind');
		if(currentPage < numPage){
			currentPage=parseInt(currentPage)+1;
			getPageInVehicle_whereUnloadStatus(currentPage, record);
			$('.pageI_unload').find('li.active').removeClass('active');
			$('.pageI_unload').find('[data-index='+currentPage+']').addClass('active');
		}
	});
});
/* 
 * InVehicle
 * */
function showContentIV_whereUnloadStatus(tr, idVehicle, startTime, endTime){
	tr.find('.td-start-time').html(startTime);
	tr.find('.td-start-time').addClass('label-time');
	tr.find('.td-end-time').html(endTime);
	tr.find('.td-end-time').addClass('label-time');
	tr.find('.save-unload').parent().html(
		"<span class='edit-unload option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-pencil-square-o'></i>"
		+ "</span>"
	);
	tr.find('.td-finish-unload').html(
		"<span class='finish-unload option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-check'></i>"
		+ "</span>"
	);
	$('.edit-unload').click(function(){
		eventEditUnloadTime($(this));
	});
	$('.finish-unload').click(function(){
		upFinishUnloadTime($(this));
	});
}
function showContentInVehicle_whereUnloadStatus(response){
	var pager = response[0];
	var pageIV = response[1];
	var content="";
	for(var i=0; i<pageIV.length; i++){
		content += "<tr>"
		content += "<td>"+pageIV[i].vehicleCode+"</td>"
				+ "<td>"+pageIV[i].company+"</td>"
				+ "<td>"+pageIV[i].date+"</td>"
				+ "<td>"+pageIV[i].arrivalTime+"</td>"
				+ "<td>"+pageIV[i].door.nameDoor+"</td>";
		if(pageIV[i].status == 1){
			content +="<td class='td-start-time'>"
						+ "<input type='text' class='timepicker-24 start-time' />"
						+ "<i class='fa fa-clock-o'></i>"
					+ "</td>"
					+ "<td class='td-end-time'>"
						+ "<input type='text' class='timepicker-24 end-time' />"
						+ "<i class='fa fa-clock-o'></i>"
					+ "</td>"
		}else{
			content += "<td class='td-start-time label-time'>"+pageIV[i].startUnloadTime+"</td>"
					+ "<td class='td-end-time label-time'>"+pageIV[i].finishUnloadTime+"</td>";
		}
		content += "<td>";
		if(pageIV[i].status == 1){
			content += "<span class='save-unload option-vehicle' data-bind='"+pageIV[i].idInVehicle+"'>"
						+ "<i class='fa fa-floppy-o'></i>"
					+ "</span>"
		}
		if(pageIV[i].status == 2){
			content += "<span class='edit-unload option-vehicle' data-bind='"+pageIV[i].idInVehicle+"'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</span>"
		}
		content += "</td>";
		content += "<td class='td-finish-unload'>";
		if(pageIV[i].status == 2){
			content += "<span class='finish-unload option-vehicle' data-bind='"+pageIV[i].idInVehicle+"'>"
						+ "<i class='fa fa-check'></i>"
					+ "</span>"
		}
		content += "</td>";
		content += "</tr>";
	}
	$('.row_data').html(content);
	
	var contentPage="";
	contentPage += "<li class='prev'><a>←</a></li>";
	var arrPage = pager.listPage;
	for(var i=0; i<arrPage.length; i++){
		if(arrPage[i] == currentPage){
			contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}else{
			contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}
	}
	contentPage += "<li class='next'><a>→ </a></li>"
	$('.pageI_unload').html(contentPage);
	
	var record = $('.recordI-unload').val();
	$('.pageI_unload .page_index').click( function() {
		var curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			currentPage = curPage;
			getPageInVehicle_whereUnloadStatus(currentPage, record);
		}
	});
	$('.pageI_unload .next').click(function(){
		var sizePage=$('.pageI_unload .size-page').attr('data-bind');
		if(currentPage < pager.numPage){
			currentPage=parseInt(currentPage)+1;
			getPageInVehicle_whereUnloadStatus(currentPage, record);
			$('.pageI_unload').find('li.active').removeClass('active');
			$('.pageI_unload').find('[data-index='+currentPage+']').addClass('active');
		}
	});
	$('.pageI_unload .prev').click(function(){
		if(currentPage > 1){
			currentPage=currentPage-1;
			getPageInVehicle_whereUnloadStatus(currentPage, record);
			$('.pageI_unload').find('li.active').removeClass('active');
			$('.pageI_unload').find('[data-index='+currentPage+']').addClass('active');
		}
	});
	
	$('.timepicker-24').timepicker({
	    minuteStep: 1,
	    showMeridian: false
	});
	$('.save-unload').click(function(){
		upDataUnloadTime($(this));
	});
	$('.edit-unload').click(function(){
		eventEditUnloadTime($(this));
	});
	$('.finish-unload').click(function(){
		upFinishUnloadTime($(this));
	});
}
/* Start getPageInVehicle_whereUnloadStatus */
function getPageInVehicle_whereUnloadStatus(currentPage, record){
	$.ajax({
        type: "POST",
        data: {
        	currentPage: currentPage,
        	record: record,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle_whereUnloadStatus',
        success: function (response) {
    		showContentInVehicle_whereUnloadStatus(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function eventEditUnloadTime(This){
	var tr = This.parents('tr');
	var idVehicle = This.attr('data-bind');
	var sTime = tr.find('.td-start-time').html();
	var eTime = tr.find('.td-end-time').html();
	tr.find('.td-start-time').html(
		+ "<input type='text' class='timepicker-24 start-time'/>"
		+ "<i class='fa fa-clock-o'></i>"
	);
	tr.find('.td-end-time').html(
		+ "<input type='text' class='timepicker-24 end-time'/>"
		+ "<i class='fa fa-clock-o'></i>"
	);
	$('.timepicker-24').timepicker({
	    minuteStep: 1,
	    showMeridian: false
	});
	tr.find('.start-time').val(sTime);
	tr.find('.end-time').val(eTime);
	tr.find('.td-start-time').removeClass('label-time');
	tr.find('.td-end-time').removeClass('label-time');
	tr.find('.edit-unload').parent().html(
		"<span class='save-unload option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-floppy-o'></i>"
		+ "</span>"
	);
	tr.find('.finish-unload').parent().html(
		"<span class='cancel-unload option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-times'></i>"
		+ "</span>"
	);
	$('.save-unload').click(function(){
		upDataUnloadTime($(this));
	});
	$('.cancel-unload').click(function(){
		tr.find('.td-start-time').html(sTime);
		tr.find('.td-end-time').html(eTime);
		tr.find('.td-start-time').addClass('label-time');
		tr.find('.td-end-time').addClass('label-time');
		tr.find('.save-unload').parent().html(
			"<span class='edit-unload option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
		);
		tr.find('.cancel-unload').parent().html(
			"<span class='finish-unload option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-check'></i>"
			+ "</span>"
		);
		$('.edit-unload').click(function(){
			eventEditUnloadTime($(this));
		});
		$('.finish-unload').click(function(){
			upFinishUnloadTime($(this));
		});
	});
}
function upDataUnloadTime(This){
	var tr = This.parents('tr');
	var idVehicle = This.attr('data-bind'); 
	var startTime = tr.find('.start-time').val();
	var endTime = tr.find('.end-time').val();
	$.ajax({
        type: "POST",
        data: {
        	idInVehicle: idVehicle,
        	startTime: startTime,
        	endTime: endTime,
        	currentPage: currentPage
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upDataUnloadTime',
        success: function (response) {
        	if(response == true){
        		showContentIV_whereUnloadStatus(tr, idVehicle, startTime, endTime);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* Start upFinishUnloadTime */
function upFinishUnloadTime(This){
	var tr = This.parents('tr'); 
	var idInVehicle = This.attr('data-bind');
	$.ajax({
        type: "POST",
        data: 'idInVehicle=' + idInVehicle,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upFinishUnloadTime',
        success: function (response) {
        	if(response==true){
        		tr.find('.edit-unload').remove();
        		tr.find('.finish-unload').remove();
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
	$('.save-load').click(function(){
		upDataLoadTime($(this));
	});
	$('.finish-load').click(function(){
		upFinishLoadTime($(this));
	});
	$('.edit-load').click(function(){
		eventEditLoadTime($(this));
	});
	var record=15;
	$('.recordO-load').change(function(){
		record = $(this).val();
		getPageOutVehicle_whereLoadStatus(currentPage, record);
	});
	$('.pageO_load .page_index').click( function() {
		var curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			currentPage = curPage;
			getPageOutVehicle_whereLoadStatus(currentPage, record);
			$('.pageO_load').find('li.active').removeClass('active').addClass('page_index');
			$(this).removeClass('page_index').addClass('active');
		}
	});
	$('.pageO_load .next').click(function(){
		var numPage=$('.pageO_load .num-page').attr('data-bind');
		if(currentPage < numPage){
			currentPage=parseInt(currentPage)+1;
			getPageOutVehicle_whereLoadStatus(currentPage, record);
		}
	});
});
function showContentOV_whereLoadStatus(tr, idVehicle, startTime, endTime){
	tr.find('.td-start-time').html(startTime);
	tr.find('.td-start-time').addClass('label-time');
	tr.find('.td-end-time').html(endTime);
	tr.find('.td-end-time').addClass('label-time');
	tr.find('.save-load').parent().html(
		"<span class='edit-load option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-pencil-square-o'></i>"
		+ "</span>"
	);
	tr.find('.td-finish-load').html(
		"<span class='finish-load option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-check'></i>"
		+ "</span>"
	);
	$('.edit-load').click(function(){
		eventEditLoadTime($(this));
	});
	$('.finish-load').click(function(){
		upFinishLoadTime($(this));
	});
}
function showContentOutVehicle_whereLoadStatus(response){
	var pager = response[0];
	var pageOV = response[1];
	var content="";
	for(var i=0; i<pageOV.length; i++){
		content += "<tr>"
		content += "<td>"+pageOV[i].vehicleCode+"</td>"
				+ "<td>"+pageOV[i].company+"</td>"
				+ "<td>"+pageOV[i].date+"</td>"
				+ "<td>"+pageOV[i].arrivalTime+"</td>"
				+ "<td>"+pageOV[i].door.nameDoor+"</td>";
		if(pageOV[i].status == 1){
			content +="<td class='td-start-time'>"
						+ "<input type='text' class='timepicker-24 start-time' />"
						+ "<i class='fa fa-clock-o'></i>"
					+ "</td>"
					+ "<td class='td-end-time'>"
						+ "<input type='text' class='timepicker-24 end-time' />"
						+ "<i class='fa fa-clock-o'></i>"
					+ "</td>"
		}else{
			content += "<td class='td-start-time label-time'>"+pageOV[i].startLoadTime+"</td>"
					+ "<td class='td-end-time label-time'>"+pageOV[i].finishLoadTime+"</td>";
		}
		content += "<td>";
		if(pageOV[i].status == 1){
			content += "<span class='save-load option-vehicle' data-bind='"+pageOV[i].idOutVehicle+"'>"
						+ "<i class='fa fa-floppy-o'></i>"
					+ "</span>"
		}
		if(pageOV[i].status == 2){
			content += "<span class='edit-load option-vehicle' data-bind='"+pageOV[i].idOutVehicle+"'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</span>"
		}
		content += "</td>";
		content += "<td class='td-finish-load'>";
		if(pageOV[i].status == 2){
			content += "<span class='finish-load option-vehicle' data-bind='"+pageOV[i].idOutVehicle+"'>"
						+ "<i class='fa fa-check'></i>"
					+ "</span>"
		}
		content += "</td>";
		content += "</tr>";
	}
	$('.row_data').html(content);
	
	var contentPage="";
	contentPage += "<li class='prev'><a>←</a></li>";
	var arrPage = pager.listPage;
	for(var i=0; i<arrPage.length; i++){
		if(arrPage[i] == currentPage){
			contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}else{
			contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}
	}
	contentPage += "<li class='next'><a>→ </a></li>";
	$('.pageO_load').html(contentPage);
	
	var record = $('.recordO-load').val();
	$('.pageO_load .page_index').click( function() {
		var curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			currentPage = curPage;
			getPageOutVehicle_whereLoadStatus(currentPage, record);
			$('.pageO_load').find('li.active').removeClass('active').addClass('page_index');
			$(this).removeClass('page_index').addClass('active');
		}
	});
	$('.pageO_load .next').click(function(){
		if(currentPage < pager.numPage){
			currentPage=parseInt(currentPage)+1;
			getPageOutVehicle_whereLoadStatus(currentPage, record);
		}
	});
	$('.pageO_load .prev').click(function(){
		if(currentPage > 1){
			currentPage=parseInt(currentPage)-1;
			getPageOutVehicle_whereLoadStatus(currentPage, record);
		}
	});
	
	$('.timepicker-24').timepicker({
	    minuteStep: 1,
	    showMeridian: false
	});
	$('.save-load').click(function(){
		upDataLoadTime($(this));
	});
	$('.edit-load').click(function(){
		eventEditLoadTime($(this));
	});
	$('.finish-load').click(function(){
		upFinishLoadTime($(this));
	});
}
function getPageOutVehicle_whereLoadStatus(currentPage, record){
	$.ajax({
        type: "POST",
        data: {
        	currentPage: currentPage,
        	record: record,
        },
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
function upDataLoadTime(This){
	var tr = This.parents('tr');
	var idVehicle = This.attr('data-bind');
	var startTime = tr.find('.start-time').val();
	var endTime = tr.find('.end-time').val();
	$.ajax({
        type: "POST",
        data: {
        	idOutVehicle: idVehicle,
        	startTime: startTime, 
        	endTime: endTime,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upDataLoadTime',
        success: function (response) {
        	if(response == true){
        		showContentOV_whereLoadStatus(tr, idVehicle, startTime, endTime);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function upFinishLoadTime(This){
	var idOutVehicle = This.attr('data-bind');
	var tr = This.parents('tr');
	$.ajax({
        type: "POST",
        data: 'idOutVehicle=' + idOutVehicle,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/upFinishLoadTime',
        success: function (response) {
        	if(response == true){
        		tr.find('.edit-load').remove();
        		tr.find('.finish-load').remove();
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function eventEditLoadTime(This){
	var tr = This.parents('tr');
	var idVehicle = This.attr('data-bind');
	var sTime = tr.find('.td-start-time').html();
	var eTime = tr.find('.td-end-time').html();
	tr.find('.td-start-time').html(
		"<label>"
			+ "<input type='text' class='timepicker-24 start-time'/>"
			+ "<i class='fa fa-clock-o'></i>"
		+ "</label>"
	);
	tr.find('.td-end-time').html(
		"<label>"
			+ "<input type='text' class='timepicker-24 end-time'/>"
			+ "<i class='fa fa-clock-o'></i>"
		+ "</label>"
	);
	$('.timepicker-24').timepicker({
	    minuteStep: 1,
	    showMeridian: false
	});
	tr.find('.start-time').val(sTime);
	tr.find('.end-time').val(eTime);
	tr.find('.td-start-time').removeClass('label-time');
	tr.find('.td-end-time').removeClass('label-time');
	tr.find('.edit-load').parent().html(
		"<span class='save-load option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-floppy-o'></i>"
		+ "</span>"
	);
	tr.find('.finish-load').parent().html(
		"<span class='cancel-load option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-times'></i>"
		+ "</span>"
	);
	$('.save-load').click(function(){
		upDataLoadTime($(this));
	});
	$('.cancel-load').click(function(){
		tr.find('.td-start-time').html(sTime);
		tr.find('.td-end-time').html(eTime);
		tr.find('.td-start-time').addClass('label-time');
		tr.find('.td-end-time').addClass('label-time');
		tr.find('.save-load').parent().html(
			"<span class='edit-load option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
		);
		tr.find('.cancel-load').parent().html(
			"<span class='finish-load option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-check'></i>"
			+ "</span>"
		);
		$('.edit-load').click(function(){
			eventEditLoadTime($(this));
		});
		$('.finish-load').click(function(){
			upFinishLoadTime($(this));
		});
	});
}
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
//		if($(this).attr('checked') != 'checked'){
			eventAssignInDoor($(this));
//		}
	});
	$('.page-iv-assign .page_index').click(function(){
		var curPage=$(this).attr('data-index');
		for(var i=0; i<sizeIDoor; i++){
			arrID[i] = 'iDoor-' + i;
			uIDoor[i] = $('.uIDoor-' + i).html();
			cIDoor[i] = $('.cIDoor-' + i).html();
		}
//		if(curPage != currentPage){
			currentPage = curPage;
			getPageInVehicle_whereAssignDoor(currentPage);
//		}
	});
	$('.page-iv-assign .prev').click(function(){
		if(currentPage > 1){
			currentPage = currentPage-1;
			getPageInVehicle_whereAssignDoor(currentPage);
		}
	});
	var numPageI = $('.page-iv-assign .num-pageI').attr('data-bind');
	$('.page-iv-assign .next').click(function(){
		if(currentPage < numPageI){
			currentPage = parseInt(currentPage)+1;
			getPageInVehicle_whereAssignDoor(currentPage);
		}
	});
	/*--------------------------------------------------------------------------------------
	 * OutVehicle*/
	$(".gODoor input[type='radio']").click(function (){
		eventAssignOutDoor($(this));
	});
	$('.page-ov-assign .page_index').click(function(){
		var curPage=$(this).attr('data-index');
		for(var i=0; i<sizeODoor; i++){
			arrOD[i] = 'oDoor-' + i;
			uODoor[i] = $('.uODoor-' + i).html();
			cODoor[i] = $('.cODoor-' + i).html();
		}
//		if(curPage != currentPage){
			currentPage = curPage;
			getPageOutVehicle_whereAssignDoor(currentPage);
//		}
	});
	$('.page-ov-assign .prev').click(function(){
		if(currentPage > 1){
			currentPage = currentPage-1;
			getPageOutVehicle_whereAssignDoor(currentPage);
		}
	});
	var numPageO = $('.page-ov-assign .num-pageO').attr('data-bind');
	$('.page-ov-assign .next').click(function(){
		if(currentPage < numPageO){
			currentPage = parseInt(currentPage)+1;
			getPageOutVehicle_whereAssignDoor(currentPage);
		}
	});
//	$('#btnAssDoor').click(function(){
//		var arrIDV = $(".gIDoor [data-flag = choose]").map( function() {
//		    return $(this).attr('data-idInVehicle');
//		}).get();
//		var arrIDD = $(".gIDoor [data-flag = choose]").map( function() {
//		    return $(this).attr('data-idDoor');
//		}).get();
//		var arrODV = $(".gODoor [data-flag = choose]").map( function() {
//		    return $(this).attr('data-idOutVehicle');
//		}).get();
//		var arrODD = $(".gODoor [data-flag = choose]").map( function() {
//		    return $(this).attr('data-idDoor');
//		}).get();
//		for(var i=0; i<sizeODoor; i++){
//			cODoor[i] = cODoor[i] - uODoor[i];
//		}
//		assignDoorInVehicle(arrIDV, arrIDD, cIDoor);
//		assignDoorOutVehicle(arrODV, arrODD, cODoor);
//	});
	$('#btnAssAI').click(function (){
		$(this).parent().html("<i class='fa fa-spinner fa-pulse fa-3x fa-fw fa-load'></i>")
		assignDoorAI();
	});
	$('#btnUseAssAI').click(function(){
		$(this).parent().html("<i class='fa fa-spinner fa-pulse fa-3x fa-fw fa-load'></i>")
		useAssignDoorAI();		
		getTotalCost();
	});
});
function getDataTransferNumber(curPage){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + curPage,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getDataTransferNumber',
        success: function (response) {
        	appendDataTranferNumber(response)
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function appendDataTranferNumber(response){
	var pageTF = response;
	for(var i=0; i<pageTF.length; i++){
		$('.data-transfer').append(
			"<span class='trip-"+pageTF[i].iv.idInVehicle
				+ "-" +pageTF[i].ov.idOutVehicle+ "'"
				+ "data-bind='" +pageTF[i].transfer+ "'"
			+ ">"
			+ pageTF[i].transfer
			+ "</span>"
		)
	}
}
function eventAssignInDoor(This){
	flagI=true;
	var volumn = This.parents('.gIDoor').find('.volumn').html();
	var doorChoose = This.parents('.gIDoor').find("input[data-flag='choose']");
	var iDoor = doorChoose.attr('data-index');
	var nGroup = doorChoose.attr('name');
	// Ban dau chua ton tai thuoc tinh flag -> lan chon dau tien se gan bien flag 
	if(doorChoose.attr('class') == null){
		var iDoor = This.attr('data-index');
		uIDoor[iDoor] = parseInt(uIDoor[iDoor]) + parseInt(volumn);
		This.attr('data-flag', 'choose');
	// Lan tiep theo da ton tai bien flag se xoa bien flag cua radio cu va tao them bien flag vao radio moi
	}else{
		if(This.attr('data-index') != iDoor || This.attr('name') == nGroup){
			var iDoor = doorChoose.attr('data-index');
			uIDoor[iDoor] = parseInt(uIDoor[iDoor]) - parseInt(volumn);
			doorChoose.removeAttr('data-flag');
			
			iDoor = This.attr('data-index');
			uIDoor[iDoor] = parseInt(uIDoor[iDoor]) + parseInt(volumn);
			This.attr('data-flag', 'choose');
		}else{// Cung chon 1 cua
			This.attr('data-flag', 'choose');
			iDoor = This.attr('data-index');
			uIDoor[iDoor] = parseInt(uIDoor[iDoor]) + parseInt(volumn);
		}
	}
	for(var i=0; i<sizeIDoor; i++){
		$('.' + arrID[i]).parent().find('.remain').html(cIDoor[i] - uIDoor[i]);
	}
	var idInVehicle = This.attr('data-idInVehicle');
	var idInDoor = This.attr('data-idDoor');
	assignDoorIV(idInVehicle, idInDoor);
}
function assignDoorIV(idInVehicle, idInDoor){
	$.ajax({
        type: "POST",
        data: {
        	idInVehicle: idInVehicle,
        	idInDoor: idInDoor
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/assignDoorIV',
        success: function (response) {
        	var ttCost = response;
        	$('.total-cost').html(ttCost);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function getTotalCost(){
	$.ajax({
        type: "POST",
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getTotalCost',
        success: function (response) {
        	var ttCost = response;
        	$('.total-cost').html(ttCost);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function assignDoorOV(idOutVehicle, idOutDoor){
	$.ajax({
        type: "POST",
        data: {
        	idOutVehicle: idOutVehicle,
        	idOutDoor: idOutDoor
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/assignDoorOV',
        success: function (response) {
        	ttCost = response;
        	$('.total-cost').html(ttCost);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function eventAssignOutDoor(This){
	var demand = This.parents('.gODoor').find('.demand').html();
	var doorChoose = This.parents('.gODoor').find("input[data-flag = 'choose']");
	var oDoor = doorChoose.attr('data-index');
	var nGroup = doorChoose.attr('name');
	if(doorChoose.attr('class') == null){
		var oDoor = This.attr('data-index');
		uODoor[oDoor] = parseInt(uODoor[oDoor]) + parseInt(demand);
		This.attr('data-flag', 'choose');
	}else{
		if(This.attr('data-index') != oDoor || This.attr('name') == nGroup){
			var oDoor = doorChoose.attr('data-index');
			uODoor[oDoor] = parseInt(uODoor[oDoor]) - parseInt(demand);
			doorChoose.removeAttr('data-flag');
			
			oDoor = This.attr('data-index');
			uODoor[oDoor] = parseInt(uODoor[oDoor]) + parseInt(demand);
			This.attr('data-flag', 'choose');
		}else{
			This.attr('data-flag', 'choose');
			oDoor = This.attr('data-index');
			uODoor[oDoor] = parseInt(uODoor[oDoor]) + parseInt(demand);
		}
	}
	for(var i=0; i<sizeODoor; i++){
		$('.' + arrOD[i]).parent().find('.remain').html(cODoor[i] - uODoor[i]);
	}
	var idOutVehicle = This.attr('data-idOutVehicle');
	var idOutDoor = This.attr('data-idDoor');
	assignDoorOV(idOutVehicle, idOutDoor);
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
			console.log('Trip=' + trip);
			console.log('Cost=' + cost)
			console.log('Obj=' + trip*cost);
			objective += trip*costs[arrInDoorChoose[i]][arrOutDoorChoose[j]];
		}
	}
	return objective;
}
function assignDoorAI(){
	var arrIdInDoor = [];
	var arrIdOutDoor = [];
	$('.gIDoor input[name=groupI-0]').each(function(index){
		arrIdInDoor[index] = $(this).attr('data-idDoor');
	});
	$('.gODoor input[name=groupO-0]').each(function(index){
		arrIdOutDoor[index] = $(this).attr('data-idDoor');
	});
	$.ajax({
        type: "POST",
        data: {
        	sizeIDoor: sizeIDoor,
        	sizeODoor: sizeODoor,
        	arrCid: cIDoor,
        	arrCod: cODoor,
        	arrIdInDoor: arrIdInDoor,
        	arrIdOutDoor: arrIdOutDoor,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/assignDoorAI',
        success: function (response) {
        	currentPage = 1;
        	getPageInVehicle_whereAssignDoor(currentPage);
        	getPageOutVehicle_whereAssignDoor(currentPage);
        	
        	var o = jQuery.parseJSON(response);
    		var objective = o.objective;
			var sol_invehicles = o.inVehicleAssignments;
			var sol_outvehicles = o.outVehicleAssignments;
			for(var i=0; i<sol_invehicles.length; i++){
				var doorChoose = $(".gIDoor [name = groupI-"+i+"][data-index="+sol_invehicles[i]+"]");
				doorChoose.parents('tr').find('.door-assign-ai').removeClass('door-assign-ai');
				doorChoose.parent().find('.remain').addClass('door-assign-ai');
			}
			for(var i=0; i<sol_outvehicles.length; i++){
				var doorChoose = $(".gODoor [name = groupO-"+i+"][data-index="+sol_outvehicles[i]+"]");
				doorChoose.parents('tr').find('.door-assign-ai').removeClass('door-assign-ai');
				doorChoose.parent().find('.remain').addClass('door-assign-ai');
			}
			$('.total-cost-ai').html(objective);
			$('.div-btn-assign-ai').html("<button class='btnDefault btn-primary' id='btnUseAssAI'>Use Assign AI</button>");
			
			$('#btnUseAssAI').click(function(){
				$(this).parent().html("<i class='fa fa-spinner fa-pulse fa-3x fa-fw fa-load'></i>")
				useAssignDoorAI();		
			});
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function useAssignDoorAI(){
	$.ajax({
        type: "POST",
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/useAssignDoorAI',
        success: function (response) {
        	currentPage = 1;
        	getPageInVehicle_whereAssignDoor(currentPage);
        	getPageOutVehicle_whereAssignDoor(currentPage);
        	$('.div-btn-assign-ai').html('');
        	getTotalCost();
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
/*function assignDoorAI(){
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
}*/
function showContentInVehicle_whereAssignDoor(response){
	flagI=false;
	var pager = response[0];
	var arrIDoor=response[1];
	var arrIV=response[2];
	var content="";	
	content+="<tr style='display: none'><td><span class='size-in-vehicle' data-bind='"+arrIV.length+"'></span></td></tr>";
	for(var i=0; i<arrIV.length; i++){
		content+="<tr class='gIDoor'>";
		content+="<td>"+arrIV[i].vehicleCode+"</td>"
			+ "<td><span class='volumn' data-bind='"+arrIV[i].volumn+"'>"+arrIV[i].volumn+"</span>"
				+ "<span class='volumn-"+i+"' data-bind='"+arrIV[i].volumn+"'></span>"
			+ "</td>"
			+ "<td>"+arrIV[i].arrivalTime+"</td>";
		for(var j=0; j<arrIDoor.length; j++){
			var remain=arrIDoor[j].capacity - arrIDoor[j].capuse;
			content+="<td style='text-align: center;'>";
			content+="<label>";
			if(arrIV[i].door.idDoor == arrIDoor[j].idDoor){
				content+="<input type='radio' "
						+ "name='groupI-"+i+"' " 
						+ "class='iDoor-"+j+"' "
						+ "data-idInVehicle='"+arrIV[i].idInVehicle+"' "
						+ "data-index='"+j+"' " 
						+ "data-idDoor='"+arrIDoor[j].idDoor+"' " 
						+ "checked='checked' " 
						+ "data-flag='choose'/>";
			}else{
				content+="<input type='radio' " 
						+ "name='groupI-"+i+"' " 
						+ "class='iDoor-"+j+"' " 
						+ "data-idInVehicle='"+arrIV[i].idInVehicle+"' " 
						+ "data-index='"+j+"' " 
						+ "data-idDoor='"+arrIDoor[j].idDoor+"'/>";
			}
			var cssDoorAI='';
			if(arrIV[i].idDoorAI == arrIDoor[j].idDoor){
				cssDoorAI = 'door-assign-ai';
			}
			content+="<span class='remain "+cssDoorAI+"'>"+remain+"</span>";
			
			content+="</label>";
			content+"</td>";
		}
		content+="</tr>"
	}
	$('.rowI_data').html(content);
	
	var contentPage="";
	contentPage += "<li class='prev'><a>←</a></li>";
	var arrPage = pager.listPage;
	for(var i=0; i<arrPage.length; i++){
		if(arrPage[i] == currentPage){
			contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}else{
			contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}
	}
	contentPage += "<li class='next'><a>→ </a></li>"
	$('.page-iv-assign').html(contentPage);
	
	$('.page-iv-assign .page_index').click(function(){
		var curPage=$(this).attr('data-index');
		for(var i=0; i<sizeIDoor; i++){
			arrID[i] = 'iDoor-' + i;
			uIDoor[i] = $('.uIDoor-' + i).html();
			cIDoor[i] = $('.cIDoor-' + i).html();
		}
//		if(curPage != currentPage){
			currentPage = curPage;
			getPageInVehicle_whereAssignDoor(currentPage);
//		}
	});
	$('.page-iv-assign .prev').click(function(){
		if(currentPage > 1){
			currentPage = currentPage-1;
			getPageInVehicle_whereAssignDoor(currentPage);
		}
	});
	$('.page-iv-assign .next').click(function(){
		if(currentPage < pager.numPage){
			currentPage = parseInt(currentPage)+1;
			getPageInVehicle_whereAssignDoor(currentPage);
		}
	});
	
	$(".gIDoor input[type='radio']").click(function (){
//		if($(this).attr('checked') != 'checked'){
			eventAssignInDoor($(this));
//		}
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
	var pager = response[0];
	var arrODoor=response[1];
	var arrOV=response[2];
	var content="";	
	content+="<tr style='display: none'><td><span class='size-out-vehicle' data-bind='"+arrOV.length+"'></span></td></tr>";
	for(var i=0; i<arrOV.length; i++){
		content+="<tr class='gODoor'>";
		content+="<td>"+arrOV[i].vehicleCode+"</td>"
			+ "<td><span class='demand' data-bind='"+arrOV[i].demand+"'>"+arrOV[i].demand+"</span>"
				+ "<span class='demand-"+i+"' data-bind='"+arrOV[i].demand+"'></span>"
			+ "</td>"
			+ "<td>"+arrOV[i].arrivalTime+"</td>";
		for(var j=0; j<arrODoor.length; j++){
			var remain=arrODoor[j].capacity - arrODoor[j].capuse;
			content+="<td style='text-align: center;'>";
			content+="<label>";
			if(arrOV[i].door.idDoor == arrODoor[j].idDoor){
				content+="<input type='radio' " 
						+ "name='groupO-"+i+"' " 
						+ "class='oDoor-"+j+"' "
						+ "data-idOutVehicle='"+arrOV[i].idOutVehicle+"' " 
						+ "data-index='"+j+"' " 
						+ "data-idDoor='"+arrODoor[j].idDoor+"' " 
						+ "checked='checked' " 
						+ "data-flag='choose'/>";
			}else{
				content+="<input type='radio' " 
						+ "name='groupO-"+i+"' " 
						+ "class='oDoor-"+j+"' " 
						+ "data-idOutVehicle='"+arrOV[i].idOutVehicle+"' " 
						+ "data-index='"+j+"' " 
						+ "data-idDoor='"+arrODoor[j].idDoor+"'/>";
			}
			var cssDoorAI = '';
			if(arrOV[i].idDoorAI == arrODoor[j].idDoor){
				cssDoorAI = 'door-assign-ai';
			}
			content+="<span class='remain + "+cssDoorAI+"'>"+remain+"</span>";
			content+="</label>";
			content+"</td>";
		}
		content+="</tr>"
	}
	$('.rowO_data').html(content);
	
	var contentPage="";
	contentPage += "<li class='prev'><a>←</a></li>";
	var arrPage = pager.listPage;
	for(var i=0; i<arrPage.length; i++){
		if(arrPage[i] == currentPage){
			contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}else{
			contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
		}
	}
	contentPage += "<li class='next'><a>→ </a></li>"
	$('.page-ov-assign').html(contentPage);
	
	$('.page-ov-assign .page_index').click(function(){
		var curPage=$(this).attr('data-index');
		for(var i=0; i<sizeODoor; i++){
			arrOD[i] = 'oDoor-' + i;
			uODoor[i] = $('.uODoor-' + i).html();
			cODoor[i] = $('.cODoor-' + i).html();
		}
//		if(curPage != currentPage){
			currentPage = curPage;
			getPageOutVehicle_whereAssignDoor(currentPage);
//		}
	});
	$('.page-ov-assign .prev').click(function(){
		if(currentPage > 1){
			currentPage = currentPage-1;
			getPageOutVehicle_whereAssignDoor(currentPage);
		}
	});
	$('.page-ov-assign .next').click(function(){
		if(currentPage < pager.numPage){
			currentPage = parseInt(currentPage) + 1;
			getPageOutVehicle_whereAssignDoor(currentPage);
		}
	});
	
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
	var status="";
	var recordI = 15;
	var keyIV="";
	$('.status-vehicle').select2({
		 minimumResultsForSearch: -1,
		 placeholder: "All",
		 allowClear: true,
	});
	$('.recordI').change(function(){
		recordI = $(this).val();
		getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);
	});
	$('.s-product').select2({
		placeholder: "Please select Product",
		allowClear: true,
	});
	$('.s-unit').select2({
		minimumResultsForSearch: -1,
		allowClear: true,
	});
	var dialogPIV=$( '.dialog-piv' ).dialog({
		autoOpen: false,
		modal : true,     
     	buttons: {
     		Close: function() {
     			var volumn=0;
     			$('.rowPIV_data td.quantity').each(function(){
     				volumn=volumn + parseInt($(this).attr('data-bind'));
     			});
     			var idIV = $('#frm-add-piv #btn-submit-piv').attr('data-bind');
     			trThis = $('.row_data').find("tr[data-bind='"+idIV+"']");
     			trThis.find('.volumn').html(volumn);
     			$(this).dialog('close');
     		},
     	},
     	width: 700,
    });
	dialogPIV.dialog("widget").find('.ui-dialog-titlebar').css('display', 'none');
	$('#tabs').tabs();
	$('.detail-invehicle').click(function(){
		var idVehicle = $(this).attr('data-bind');
		var tr = $(this).parents('tr');
		var vehicleCode = tr.find('.code').attr('data-bind');
		var company = tr.find('.company').attr('data-bind');
		var status = tr.find('.status').attr('data-bind');
		var dialog = $('.dialog-piv');
		dialog.find('.code').val(vehicleCode);
		dialog.find('.company').val(company);
		dialog.find('#btn-submit-piv').attr('data-bind', idVehicle);
		dialog.find('#btn-submit-pov').attr('data-bind', idVehicle);
		dialog.find('.status').attr('data-bind', status);
		displayProductIV(idVehicle);
		$('.dialog-piv').dialog('open');
	});
	$('#btn-submit-piv').click(function(){
		var frmSubmit = $(this).parents('#frm-add-piv');
		validateInsertPIV(frmSubmit);
		var isValid =  frmSubmit.valid();
		if(isValid){
			var idVehicle = $(this).attr('data-bind');
			var company = frmSubmit.find('.company').val();
			var idProduct = frmSubmit.find('select.product').val();
			var quantity = frmSubmit.find('.quantity').val();
			var idUnit = frmSubmit.find('select.unit').val();
			insertProductIV(idVehicle, idProduct, quantity, idUnit);
		}
	});
	$('#btn-submit-pov').click(function(){
		var frmSubmit = $(this).parents('#frm-add-pov');
		validateInsertPOV(frmSubmit);
		var isValid = frmSubmit.valid();
		if(isValid){
			var idInVehicle = $(this).attr('data-bind');
			var idOutVehicle = $('select.sl-ov').val().split('-')[0];
			var idProduct = $('select.sl-piv').val().split('-')[0];
			var quantity = $('.quantity-ov').val();
			var idUnit = $('select.unit-ov').val();
			insertProductOV(idInVehicle, idOutVehicle, idProduct, quantity, idUnit);
		}
	});
	$('#btn-search-mtriv').click(function(){
		keyIV = $('.key-invehicle-search').val();
		status = $('select.status-vehicle').val();
		currentPage=1;
		getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);
	});
	$('.pageI-status .page_index').click(function(){
		var curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			currentPage = curPage;
			getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);		
		}
	});
	$('.pageI-status .prev').click(function(){
		if(currentPage > 1){
			currentPage=currentPage - 1;
			getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);
		}
	});
	$('.pageI-status .next').click(function(){
		var numPage = $('.pageI-status .num-page').attr('data-bind');
		if(currentPage < numPage){
			currentPage=currentPage - (-1);
			getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);
		}
	});
});
function showContentProductIV(response){
	var listPIV=response[0];
	var listPT=response[1];
	if(listPIV.length < 1){
		$('.rowPIV_data').html('');
		$('.sl-piv').html('');
		$('.no-record-piv').remove();
		$('.rowPIV_data').parents('table').after("<span class='no-record-piv'>" +noRecord+ "</span>");
	}else{
		$('.no-record-piv').remove();
		var content="";
		var dataProduct="<select class='sl-piv' name='pName'>"
						+ "<option value=''></option>";
		for(var i=0; i<listPIV.length; i++){
			content += "<tr>";
			content +="<td class='vehicle' data-bind='"+listPIV[i].iv.idInVehicle+"'>"
						+listPIV[i].iv.vehicleCode
					+ "</td>"
					+ "<td>"+listPIV[i].iv.company+"</td>"
					+ "<td class='product' data-bind='"+listPIV[i].p.id+"'>"
						+listPIV[i].p.name
					+ "</td>"
					+ "<td class='quantity' data-bind='"+listPIV[i].quantity+"'>"
						+listPIV[i].quantity
					+ "</td>"
					+ "<td class='unit' data-bind='"+listPIV[i].u.id+"'>"
						+listPIV[i].u.name
					+ "</td>"
					+ "<td class='col-option'><span class='edit-piv option-vehicle' data-bind='"+listPIV[i].id+"'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</td>"
					+ "<td class='col-option'><span class='del-piv option-vehicle' data-bind='"+listPIV[i].id+"'>"
						+ "<i class='fa fa-trash-o'></i>"
					+ "</td>";
			content += "</tr>";
			dataProduct += "<option value='"+listPIV[i].p.id+"-"+listPIV[i].quantity+"'>"+listPIV[i].p.name+"</option>"
		}
		$('.rowPIV_data').html(content);
		dataProduct += "</select>";
		var status = $('.dialog-piv .status').attr('data-bind');
		if(status > 1){
			$('.dialog-piv #btn-submit-piv').css('display', 'none');
			$('.dialog-piv #tbl-piv .col-option').css('display', 'none');
		}
		$('.sl-piv').parent().html(dataProduct);
		$('.sl-piv').select2({
			placeholder: "Please select product",
		});
		$('.sl-ov').change(function(){
			var val = $(this).val()
			var idOutVehicle = val.split('-')[0];
			var arrivalTime = val.split('-')[1];
			$('.arrival-time-ov').html(arrivalTime);
		});
		$('.sl-piv').change(function(){
			var idProduct=$(this).val().split('-')[0];
			var totalQ=$(this).val().split('-')[1];
			var volumn = 0;
			for(var i=0; i<listPT.length; i++){
				if(listPT[i].pov.p.id == idProduct){
					volumn += listPT[i].pov.quantity;
				}
			}
			$('.quantity-piv').html(totalQ - volumn);
			$('.quantity-piv').attr('data-bind', totalQ);
//			$('.unit-piv').html(listPIV[i].u.name);
		});
		$('.del-piv').click(function(){
			var idPIV=$(this).attr('data-bind');
			var idVehicle = $(this).parents('tr').find('.vehicle').attr('data-bind');
			delProductIV(idPIV, idVehicle);
		});
		$('.edit-piv').click(function(){
			eventEditPIV($(this));
		});
	}

	var dataPT="";
	if(listPT.length < 1){
		$('.rowPOV_data').html('');
		$('.no-record-pov').remove();
		$('.rowPOV_data').parents('table').after("<span class='no-record-pov'>" +noRecord+ "</span>");
	}else{
		$('.no-record-pov').remove();
		for(var i=0; i<listPT.length; i++){
			dataPT +="<tr>";
			dataPT +=
				"<td class='idInVehicle' data-bind='"+listPT[i].piv.iv.idInVehicle+"'>" 
					+ listPT[i].piv.iv.vehicleCode
				+ "</td>"
				+ "<td>" +listPT[i].pov.ov.vehicleCode+ "</td>"
				+ "<td>" +listPT[i].pov.p.name+ "</td>"
				+ "<td class='quantity' data-bind='"+listPT[i].pov.quantity+"'>" 
					+ listPT[i].pov.quantity
				+ "</td>"
				+ "<td>" +listPT[i].pov.u.name+ "</td>"
					+ "<td class='col-option'><span class='edit-pov option-vehicle' "
					+ "data-idProduct='"+listPT[i].pov.p.id+"' data-idOutVehicle='"+listPT[i].pov.ov.idOutVehicle+"'>"
					+ "<i class='fa fa-pencil-square-o'></i>"
				+ "</td>"
				+ "<td class='col-option'><span class='del-pov option-vehicle' "
				+ "data-idProduct='"+listPT[i].pov.p.id+"' data-idOutVehicle='"+listPT[i].pov.ov.idOutVehicle+"'>"
						+ "<i class='fa fa-trash-o'></i>"
				+ "</td>";
			dataPT +="</tr>";
		}
		$('.rowPOV_data').html(dataPT);
		$('.edit-pov').click(function(){
			eventEditPOV($(this));
		});
		$('.del-pov').click(function(){
			var idVehicle = $(this).parents('tr').find('.idInVehicle').attr('data-bind');
			var idProduct = $(this).attr('data-idProduct');
			var idOutVehicle = $(this).attr('data-idOutVehicle');
			delProductOV(idProduct, idOutVehicle, idVehicle);
		});
	}
	
}
function eventEditPIV(This){
	trPIV = This.parents('tr');
	var quantity = trPIV.find('.quantity').attr('data-bind');
	var idPIV=This.attr('data-bind');
	trPIV.find('.quantity').html
		("<input type='text' class='form-control quantity' size='10' name='quantity'"
			+ "style='padding: 0 6px' value='"+quantity+"'/>");
	trPIV.find('.edit-piv').parent().html
		("<span class='save-piv option' data-bind='"+idPIV+"'><i class='fa fa-floppy-o'></i></span>");
	trPIV.find('.del-piv').parent().html
		("<span class='cancel-piv option'><i class='fa fa-times'></i></span>");
	trPIV.find('input.quantity').focus();
	
	$('.save-piv').click(function(){
		var tr = $(this).parents('tr');
		var quantity = tr.find('input[name=quantity]').val();
		var isValid = validateEditPIV(tr, quantity);
		var idPIV = $(this).attr('data-bind');
		var idVehicle = $(this).parents('tr').find('.vehicle').attr('data-bind');
		if(isValid){
			editProductIV(idPIV, quantity, idVehicle);
		}
	});
	$('.cancel-piv').click(function(){
		var tr = $(this).parents('tr');
		var divEditErr=tr.parents('.adv-table').find('#msgEditTabs1');
		divEditErr.html('');
		var quantity=tr.find('.quantity').attr('data-bind');
		tr.find('.quantity').html(quantity);
		tr.find('.save-piv').parent().html
			("<span class='edit-piv option' data-bind='"+idPIV+"'><i class='fa fa-pencil-square-o'></i></span>");
		$(this).parent().html
			("<span class='del-piv option' data-bind='"+idPIV+"'><i class='fa fa-trash-o'></i></span>");
		
		$('.edit-piv').click(function(){
			eventEditPIV($(this));
		});
		$('.del-piv').click(function(){
			var idPIV=$(this).attr('data-bind');
			var idVehicle = $(this).parents('tr').find('.vehicle').attr('data-bind');
			delProductIV(idPIV, idVehicle);
		});
	});
}
function eventEditPOV(This){
	trPOV = This.parents('tr');
	var quantity = trPOV.find('.quantity').attr('data-bind');
	var idProduct = This.attr('data-idProduct');
	var idOutVehicle = This.attr('data-idOutVehicle');
	trPOV.find('.quantity').html
		("<input type='text' class='form-control quantity' size='10' name='quantity'"
			+ "style='padding: 0 6px' value='"+quantity+"'/>");
	trPOV.find('.edit-pov').parent().html("<span class='save-pov option'><i class='fa fa-floppy-o'></i></span>");
	trPOV.find('.del-pov').parent().html("<span class='cancel-pov option'><i class='fa fa-times'></i></span>");
	trPOV.find('input.quantity').focus();
	
	$('.save-pov').click(function(){
		tr = $(this).parents('tr');
		var quantity = tr.find('input[name=quantity]').val();
		var isValid = validateEditPOV(tr, quantity);
		var idVehicle = $(this).parents('tr').find('.idInVehicle').attr('data-bind');
		if(isValid){
			editProductOV(idProduct, idOutVehicle, quantity, idVehicle);
		}
	});
	$('.cancel-pov').click(function(){
		var tr = $(this).parents('tr');
		var divEditErr=tr.parents('.adv-table').find('#msgEditTabs2');
		divEditErr.html('');
		var quantity=tr.find('.quantity').attr('data-bind');
		tr.find('.quantity').html(quantity);
		tr.find('.save-pov').parent().html("<span class='edit-pov option' data-idProduct='"+idProduct+"' data-idOutVehicle='"+idOutVehicle+"'><i class='fa fa-pencil-square-o'></i></span>");
		$(this).parent().html("<span class='del-pov option' data-idProduct='"+idProduct+"' data-idOutVehicle='"+idOutVehicle+"'><i class='fa fa-trash-o'></i></span>");
		
		$('.edit-pov').click(function(){
			eventEditPOV($(this));
		});
		$('.del-pov').click(function(){
			var idVehicle = $(this).parents('tr').find('.idInVehicle').attr('data-bind');
			var idProduct = $(this).attr('data-idProduct');
			var idOutVehicle = $(this).attr('data-idOutVehicle');
			delProductOV(idProduct, idOutVehicle, idVehicle);
		});
	});
}
function showContentTransferProduct(response, idProduct){
	var listPT=response;
	if(listPT.length < 1){
		$('.rowPOV_data').html('');
		$('.no-record-pov').remove();
		$('.rowPOV_data').parents('table').after("<span class='no-record-pov'>" +noRecord+ "</span>");
	}else{
		var uQuantity=0;
		$('.no-record-pov').remove();
		content="";
		for(var i=0; i<listPT.length; i++){
			content +="<tr>";
			content +="<td class='idInVehicle' data-bind='"+listPT[i].piv.iv.idInVehicle+"'>" 
						+ listPT[i].piv.iv.vehicleCode
					+ "</td>"
					+ "<td>" +listPT[i].pov.ov.vehicleCode+ "</td>"
					+ "<td>" +listPT[i].pov.p.name+ "</td>"
					+ "<td class='quantity' data-bind='"+listPT[i].pov.quantity+"'>" 
						+ listPT[i].pov.quantity
					+ "</td>"
					+ "<td>" +listPT[i].pov.u.name+ "</td>"
					+ "<td class='col-option'><span class='edit-pov option-vehicle' "
							+ "data-idProduct='"+listPT[i].pov.p.id+"' data-idOutVehicle='"+listPT[i].pov.ov.idOutVehicle+"'>"
						+ "<i class='fa fa-pencil-square-o'></i>"
					+ "</td>"
					+ "<td class='col-option'><span class='del-pov option-vehicle' "
							+ "data-idProduct='"+listPT[i].pov.p.id+"' data-idOutVehicle='"+listPT[i].pov.ov.idOutVehicle+"'>"
						+ "<i class='fa fa-trash-o'></i>"
					+ "</td>";
			content +="</tr>";
			if(listPT[i].pov.p.id == idProduct){
				uQuantity += parseInt(listPT[i].pov.quantity);
			}
		}
		$('.rowPOV_data').html(content);
		
		$('.sl-piv').change(function(){
			var idProduct=$(this).val().split('-')[0];
			var totalQ=$(this).val().split('-')[1];
			$('.quantity-piv').html(totalQ - uQuantity);
			$('.quantity-piv').attr('data-bind', totalQ);
//			$('.unit-piv').html(listPIV[i].u.name);
		});
		
		var reQuantity=$('.quantity-piv').attr('data-bind') - uQuantity;
		$('.quantity-piv').html(reQuantity);
		
		$('.edit-pov').click(function(){
			eventEditPOV($(this));
		});
		$('.del-pov').click(function(){
			var idVehicle = $(this).parents('tr').find('.idInVehicle').attr('data-bind');
			var idProduct = $(this).attr('data-idProduct');
			var idOutVehicle = $(this).attr('data-idOutVehicle');
			delProductOV(idProduct, idOutVehicle, idVehicle);
		});
	}
}
function displayProductIV(idVehicle){
	$.ajax({
        type: "POST",
        data: {
        	idVehicle: idVehicle,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/displayProductIV',
        success: function (response) {
        	showContentProductIV(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function insertProductOV(idInVehicle, idOutVehicle, idProduct, quantity, idUnit){
	$.ajax({
        type: "POST",
        data: {
        	idInVehicle: idInVehicle,
        	idOutVehicle: idOutVehicle, 
        	idProduct: idProduct,
        	quantity: quantity, 
        	idUnit: idUnit,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertProductOV',
        success: function (response) {
        	$('#msgInsertTab2').html("<label class='msg-success'><i class='fa fa-check icon-msg'></i>"+successInsertProduct+"</label>");
        	showContentTransferProduct(response, idProduct);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function insertProductIV(idVehicle, idProduct, quantity, idUnit){
	$.ajax({
        type: "POST",
        data: {
        	idVehicle: idVehicle,
        	idProduct: idProduct,
        	quantity: quantity, 
        	idUnit: idUnit,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertProductIV',
        success: function (response) {
        	$('#msgInsertTab1').html("<label class='msg-success'><i class='fa fa-check icon-msg'></i>"+successInsertProduct+"</label>");
        	showContentProductIV(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function editProductOV(idProduct, idOutVehicle, quantity, idVehicle){
	$.ajax({
        type: "POST",
        data: {
        	idProduct: idProduct,
        	idOutVehicle: idOutVehicle,
        	quantity: quantity,
        	idVehicle: idVehicle,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editProductOV',
        success: function (response) {
        	if(response == ''){
        		divEditErr = $('#msgEditTabs2');
        		divEditErr.html("<label class='error'>"+errMaxQuantity+"</label>");
        		divEditErr.parents('.adv-table').find('input.quantity').css('border', '1px solid red');
        	}else{
        		showContentTransferProduct(response, idProduct);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function editProductIV(idPIV, quantity, idVehicle){
	$.ajax({
        type: "POST",
        data: {
        	idPIV: idPIV,
        	quantity: quantity,
        	idVehicle: idVehicle,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/editProductIV',
        success: function (response) {
        	if(response == ''){
        		var divEditErr = $('#msgEditTabs1');
        		divEditErr.html("<label class='error'>"+errEditPIV+"</label>")
        		divEditErr.parents('.adv-table').find('input.quantity').css('border', '1px solid red');
        	}else{
        		showContentProductIV(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function delProductOV(idProduct, idOutVehicle, idVehicle){
	$.ajax({
        type: "POST",
        data: {
        	idProduct: idProduct,
        	idOutVehicle: idOutVehicle,
        	idVehicle: idVehicle,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delProductOV',
        success: function (response) {
        	showContentTransferProduct(response, idProduct);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function delProductIV(idPIV, idVehicle){
	$.ajax({
        type: "POST",
        data: {
        	idPIV: idPIV,
        	idVehicle: idVehicle,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delProductIV',
        success: function (response) {
        	if(response == ''){
        		var divEditErr = $('#msgEditTabs1');
        		divEditErr.html("<label class='error'>"+errDelPIV+"</label>")
        	}else{
        		showContentProductIV(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
$(document).ready(function(){
	var dialogPOV=$( '.dialog-pov' ).dialog({
		autoOpen: false,
		modal : true,     
     	buttons: {
     		Close: function() {
     			$(this).dialog('close');
     		},
     	},
     	width: 700,
    });
	dialogPOV.dialog("widget").find('.ui-dialog-titlebar').css('display', 'none');
	$('#tabs').tabs();
	$('.detail-outvehicle').click(function(){
		var idVehicle = $(this).attr('data-bind');
		displayProductOV(idVehicle);
		$('.dialog-pov').dialog('open');
	});
});
function displayProductOV(idVehicle){
	$.ajax({
        type: "POST",
        data: {
        	idVehicle: idVehicle,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/displayProductOV',
        success: function (response) {
        	showContentProductOV(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
function showContentProductOV(response){
	var listPOV=response[0];
	var listPT=response[1];
	if(listPOV < 1){
		$('.rowPOV_data').html('');
		$('.no-record-pov').remove();
		$('.rowPOV_data').parents('table').after("<span class='no-record-pov'>" +noRecord+ "</span>");
	}else{
		$('.no-record-pov').remove();
		var content="";
		for(var i=0; i<listPOV.length; i++){
			content += "<tr>";
			content +="<td class='vehicle'>" +listPOV[i].ov.vehicleCode+ "</td>"
					+ "<td>"+listPOV[i].ov.company+"</td>"
					+ "<td class='product' data-bind='"+listPOV[i].p.id+"'>"
						+listPOV[i].p.name
					+ "</td>"
					+ "<td class='quantity' data-bind='"+listPOV[i].quantity+"'>"
						+listPOV[i].quantity
					+ "</td>"
					+ "<td class='unit' data-bind='"+listPOV[i].u.id+"'>"
						+listPOV[i].u.name
					+ "</td>"
			content += "</tr>";
		}
		$('.rowPOV_data').html(content);
	}
	if(listPT.length < 1){
		$('.rowPT_data').html('');
		$('.no-record-pt').remove();
		$('.rowPT_data').parents('table').after("<span class='no-record-pt'>" +noRecord+ "</span>");
	}else{
		$('.no-record-pt').remove();
		var dataPT="";
		for(var i=0; i<listPT.length; i++){
			dataPT +="<tr>";
			dataPT +="<td>" +listPT[i].piv.iv.vehicleCode+ "</td>"
					+ "<td>" +listPT[i].pov.ov.vehicleCode+ "</td>"
					+ "<td>" +listPT[i].pov.p.name+ "</td>"
					+ "<td class='quantity' data-bind='"+listPT[i].pov.quantity+"'>" 
						+ listPT[i].pov.quantity
					+ "</td>"
					+ "<td>" +listPT[i].pov.u.name+ "</td>"
			dataPT +="</tr>";
		}
	}	
	$('.rowPT_data').html(dataPT);
}
function showContentInVehicle_byStatusAndKey(response){
	var content="";
	var pager=response[0];
	var pageV=response[1];
	if(pager.numPage > 0){
		$('.no-record').remove();
		for(var i=0; i<pageV.length; i++){
			if(pageV[i].status ==0) status=waitAssStatus;
			else if(pageV[i].status ==1) status=waitUnloadStatus;
			else if(pageV[i].status ==2) status=startUnloadStatus;
			else status=finishUnloadStatus;
			var nameDoor=pageV[i].door.nameDoor;
			if(pageV[i].door.nameDoor == null) nameDoor="";
			content+=
				"<tr>"
					+ "<td>"+pageV[i].vehicleCode+"</td>"
					+ "<td>"+standardizeStr(pageV[i].company)+"</td>"
					+ "<td>"+pageV[i].arrivalTime+"</td>"
					+ "<td>"+pageV[i].volumn+"</td>"
					+ "<td>"+standardizeStr(pageV[i].startUnloadTime)+"</td>"
					+ "<td>"+standardizeStr(pageV[i].finishUnloadTime)+"</td>"
					+ "<td>"+nameDoor+"</td>"
					+ "<td>"+status+"</td>"
					+ "<td><span class='detail-invehicle option' data-bind='"+pageV[i].idInVehicle+"'>" 
							+ "<i class='fa fa-list-alt'></i></span>"
					+ "</td>"
				+ "</tr>"
		}
		$('.rowI_data').html(content);
		var contentPage="";
		contentPage += "<li class='prev'><a>←</a></li>";
		var arrPage = pager.listPage;
		for(var i=0; i<arrPage.length; i++){
			if(arrPage[i] == currentPage){
				contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}else{
				contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}
		}
		contentPage += "<li class='next'><a>→ </a></li>"
		$('.pageI-status').html(contentPage);
		
		status = $('.status-vehicle').val();
		recordI = $('.recordI').val();
		keyIV = $('.key-invehicle-search').val();
		$('.pageI-status .page_index').click(function(){
			currentPage = $(this).attr('data-index');
			getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);		
		});
		$('.pageI-status .prev').click(function(){
			if(currentPage > 1){
				currentPage=currentPage - 1;
				getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);
			}
		});
		$('.pageI-status .next').click(function(){
			if(currentPage < pager.numPage){
				currentPage=parseInt(currentPage) + 1;
				getPageInVehicle_byStatusAndKey(status, recordI, keyIV, currentPage);
			}
		});
		$('.detail-invehicle').click(function(){
			var idVehicle = $(this).attr('data-bind');
			tr = $(this).parents('tr');
			var vehicleCode = tr.find('.code').attr('data-bind');
			var company = tr.find('.company').attr('data-bind');
			var status = tr.find('.status').attr('data-bind');
			var dialog = $('.dialog-piv');
			dialog.find('.code').val(vehicleCode);
			dialog.find('.company').val(company);
			dialog.find('#btn-submit-piv').attr('data-bind', idVehicle);
			dialog.find('.status').attr('data-bind', status);
			displayProductIV(idVehicle);
			$('.dialog-piv').dialog('open');
		});
	}else{
		$('.rowI_data').html('');
		$('.no-record').remove();
		$('table').after("<span class='no-record'>" +noRecord+ "</span>");
		$('.pagination').html('');
	}
}
function getPageInVehicle_byStatusAndKey(status, record, key, currentPage){
	$.ajax({
        type: "POST",
        data: {
        	status: status,
        	record: record,
        	key: key, 
        	currentPage: currentPage,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicle_byStatusAndKey',
        success: function (response) {
        	showContentInVehicle_byStatusAndKey(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
$(document).ready(function(){
	var status="";
	var recordO = 15;
	var keyOV="";
	$('.recordO').change(function(){
		recordO = $(this).val();
		getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);
	});
	$('#btn-search-mtrov').click(function(){
		keyOV = $('.key-outvehicle-search').val();
		status = $('select.status-vehicle').val();
		currentPage=1;
		getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);
	});
	$('.pageO-status .page_index').click(function(){
		var curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			currentPage = curPage;
			getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);		
		}
	});
	$('.pageO-status .prev').click(function(){
		if(currentPage > 1){
			currentPage=currentPage - 1;
			getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);
		}
	});
	$('.pageO-status .next').click(function(){
		var numPage = $('.pageO-status .numPage').attr('data-bind');
		if(currentPage < numPage){
			currentPage=currentPage - (-1);
			getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);
		}
	});
});
function showContentOutVehicle_byStatusAndKey(response){
	var content="";
	var pager=response[0];
	var pageV=response[1];
	if(pager.numPage > 0){
		for(var i=0; i<pageV.length; i++){
			if(pageV[i].status ==0) status=waitAssStatus;
			else if(pageV[i].status ==1) status=waitLoadStatus;
			else if(pageV[i].status ==2) status=startLoadStatus
			else status=finishLoadStatus;
			var nameDoor=pageV[i].door.nameDoor;
			if(pageV[i].door.nameDoor == null) nameDoor="";
			content+=
				"<tr>"
					+ "<td>"+pageV[i].vehicleCode+"</td>"
					+ "<td>"+standardizeStr(pageV[i].company)+"</td>"
					+ "<td>"+pageV[i].arrivalTime+"</td>"
					+ "<td>"+pageV[i].demand+"</td>"
					+ "<td>"+standardizeStr(pageV[i].startUnloadTime)+"</td>"
					+ "<td>"+standardizeStr(pageV[i].finishUnloadTime)+"</td>"
					+ "<td>"+nameDoor+"</td>"
					+ "<td>"+status+"</td>"
					+ "<td><span class='detail-outvehicle option' data-bind='"+pageV[i].idOutVehicle+"'>"
						+ "<i class='fa fa-list-alt'></i></span>"
					+ "</td>"
				+ "</tr>"
		}
		$('.rowO_data').html(content);
		var contentPage="";
		contentPage += "<li class='prev'><a>←</a></li>";
		var arrPage = pager.listPage;
		for(var i=0; i<arrPage.length; i++){
			if(arrPage[i] == currentPage){
				contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}else{
				contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}
		}
		contentPage += "<li class='next'><a>→ </a></li>"
		$('.pageO-status').html(contentPage);
		
		status = $('.status-vehicle').val();
		recordO = $('.recordO').val();
		keyOV = $('.key-outvehicle-search').val();
		$('.detail-outvehicle').click(function(){
			var idVehicle = $(this).attr('data-bind');
			displayProductOV(idVehicle);
			$('.dialog-pov').dialog('open');
		});
		$('.pageO-status .page_index').click(function(){
			currentPage = $(this).attr('data-index');
			getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);		
		});
		$('.pageO-status .prev').click(function(){
			if(currentPage > 1){
				currentPage=currentPage - 1;
				getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);
			}
		});
		$('.pageO-status .next').click(function(){
			if(currentPage < pager.numPage){
				currentPage=currentPage - (-1);
				getPageOutVehicle_byStatusAndKey(status, recordO, keyOV, currentPage);
			}
		});
	}else{
		$('.rowO_data').html('');
		$('.no-record').remove();
		$('table').after("<span class='no-record'>" +noRecord+ "</span>");
		$('.pagination').html('');
	}
}
function getPageOutVehicle_byStatusAndKey(status, record, key, currentPage){
	$.ajax({
        type: "POST",
        data: {
        	status: status,
        	record: record,
        	key: key, 
        	currentPage: currentPage,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicle_byStatusAndKey',
        success: function (response) {
        	showContentOutVehicle_byStatusAndKey(response);
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
	});
}
$(document).ready(function(){
	$('.recordI').change( function (e){
		var key=$('.key-invehicle-search').val();
		var record = $(this).find(':selected').val();
		searchInVehicleCurrentDay_byKeySearch(currentPage, key, record, e);
	});
	$('#addIV').submit( function(e) {
		eventInsertIV($(this), e);
	});
	$('.edit-invehicle').click(function(e){
		eventEditIV($(this), e);
	});
	$('.del-invehicle').click(function(e){
		eventDelIV($(this), e);
	});
	$('.page-iv .page_index').click( function(e) {
		eventChangePageIV($(this), e);
	});
	$('.page-iv .prev').click(function(e){
		eventPrePageIV(e);
	});
	$('.page-iv .next').click(function(e){
		var numPage = $('.num-page').attr('data-bind');
		eventNextPageIV(numPage, e);
	});
	$('#btn-search-invehicle').submit(function(e){
		var record = $('select.recordI').val();
		var keySearch=$('.key-invehicle-search').val();
		var currentPage=1;
		searchInVehicleCurrentDay_byKeySearch(currentPage, keySearch, record, e);
		e.preventDefault();
	});
	$('#btnSearch').click(function(){
		var infSearch=$(this).parents('.infor-search');
		var vehicleCode=infSearch.find('input[name=vehicleCode]').val();
		var strDate=infSearch.find('input[name=strDate]').val();
		var endDate=infSearch.find('input[name=endDate]').val();
		getPageVehiclebyInforSearch(vehicleCode, strDate, endDate);
	});
});
function showDataInVehicleCurrentDay_byKeySearch(currentPage, pager, arrIV, e){
	var content="";
	contentPage="";
	if(arrIV.length < 1){
		$('.adv-table table').after("<span class='no-record'>" + noRecord + "</span>");
	}else{
		$('.adv-table .no-record').remove();
		for(var i=0; i<arrIV.length; i++){
			var status="";
			if(arrIV[i].status ==0) status=waitAssStatus;
			else if(arrIV[i].status ==1) status=waitUnloadStatus;
			else if(arrIV[i].status ==2) status=startUnloadStatus;
			else status=finishUnloadStatus;
			content+=
				"<tr>"
					+ "<td class='code' data-bind='"+arrIV[i].vehicleCode+"'>"
						+ arrIV[i].vehicleCode
					+ "</td>"
					+ "<td class='company' data-bind='"+arrIV[i].company+"'>"
						+ arrIV[i].company
					+ "</td>"
					+ "<td class='date label-date'>"+arrIV[i].date+"</td>"
					+ "<td class='arrivalTime label-time' data-bind='"+arrIV[i].arrivalTime+"'>"
						+ arrIV[i].arrivalTime
					+ "</td>"
					+ "<td>"+arrIV[i].volumn+"</td>"
					+ "<td class='status' data-bind='"+arrIV[i].status+"'>"+status+"</td>"
					+ "<td>"+standardizeStr(arrIV[i].door.nameDoor)+"</td>"
					+ "<td>";
			if(arrIV[i].status < 1){
				content +="<span class='edit-invehicle option-vehicle' data-bind='"+arrIV[i].idInVehicle+"'>"
							+ "<i class='fa fa-pencil-square-o'></i>"
						+ "</span>"
			}
			content += "</td>"
					+ "<td>"; 
			if(arrIV[i].status < 1){
				content += "<span class='del-invehicle option-vehicle' "
						+ "data-bind='"+arrIV[i].idInVehicle+"'>"
							+ "<i class='fa fa-trash-o'></i>"
						+ "</span>";
			}
			content+= "</td>"
					+ "<td>";
			content += "<span class='detail-invehicle option-vehicle' data-bind='"+arrIV[i].idInVehicle+"'>"
						+ "<i class='fa fa-list-alt'></i>"
					+ "</span>";
			content+= "</td>"
				+ "</tr>";
		}
		var arrPage = pager.listPage;
		contentPage += "<li class='prev'><a>←</a></li>";
		for(var i=0; i<arrPage.length; i++){
			if(arrPage[i] == currentPage){
				contentPage +="<li class='page_index active' data-index="+arrPage[i]+"><a>"+arrPage[i]+"</a></li>";
			}else{
				contentPage +="<li class='page_index' data-index="+arrPage[i]+"><a>"+arrPage[i]+"</a></li>";
			}
		}
		contentPage += "<li class='next'><a>→ </a></li>";
		
		$('.row_data').html(content);
		$('.page-iv').html(contentPage);
		$('.page-iv .page_index').click( function(e) {
			eventChangePageIV($(this), e);
		});
		$('.del-invehicle').click(function(e){
			eventDelIV($(this), e)
		});
		$('.edit-invehicle').click(function(e){
			eventEditIV($(this), e);
		});
		$('.page-iv .prev').click(function(e){
			eventPrePageIV(e);
		});
		$('.page-iv .next').click(function(e){
			eventNextPageIV(pager.numPage, e);
		});
		$('.detail-invehicle').click(function(){
			var idVehicle = $(this).attr('data-bind');
			tr = $(this).parents('tr');
			var vehicleCode = tr.find('.code').attr('data-bind');
			var company = tr.find('.company').attr('data-bind');
			var status = tr.find('.status').attr('data-bind');
			var dialog = $('.dialog-piv');
			dialog.find('.code').val(vehicleCode);
			dialog.find('.company').val(company);
			dialog.find('#btn-submit-piv').attr('data-bind', idVehicle);
			dialog.find('.status').attr('data-bind', status);
			displayProductIV(idVehicle);
			$('.dialog-piv').dialog('open');
		});
	}
}
/* Start insertInVehicle */
function insertInVehicle(record, e){
	$.ajax({
        type: "POST",
        data: $("#addIV").serialize() + '&record=' + record,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertInVehicle',
        success: function (response) {
        	if(response == ''){
        		$('.msg-insert-iv').html("<label class='msg-error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errExistsVehicle+"</label>");
        	}else{
        		$('.msg-insert-iv').html("<label class='msg-success'><i class='fa fa-check icon-msg'></i>"+successInsertV+"</label>");
        		pager = response[0];
        		arrIV = response[1];
        		currentPage = 1;
        		showDataInVehicleCurrentDay_byKeySearch(currentPage, pager, arrIV, e);        		
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
/* End insertInVehicle */
function delInVehicleCurrentDay(idVehicle, currentPage, keySearch, record, e){
	$.ajax({
        type: "POST",
        data: 'idVehicle=' + idVehicle + '&currentPage=' + currentPage + '&keySearch=' + keySearch + "&record=" + record,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delInVehicleCurrentDay',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		pager = response[0];
        		arrIV = response[1];
        		showDataInVehicleCurrentDay_byKeySearch(currentPage, pager, arrIV, e);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function eventInsertIV(This, e){
	var addIV=This;
	confirmDialog(addIV, e).then(function(choose){
		if(choose == 'true'){
			validateInsertVehicle(addIV);
			var isValid=addIV.valid();
			if(isValid){
				var record=$('.recordI').find(':selected').val();
				insertInVehicle(record, e);
				e.preventDefault();
			}
		}
	});
}
function eventEditIV(This, e){
	/*Tr-Old*/
	var trcOld=This.parents('.row_data').find('.save-invehicle').parents('tr');
	if(trcOld.html() != null){
		var dateOld=trcOld.find('input[name=date]').val();
		var arrivalOld=trcOld.find('input[name=arrivalTime]').val();
		eventCancelIV(trcOld.find('.cancel-invehicle'), dateOld, arrivalOld, e);
	}
	/*Tr-New*/
	var trC=This.parents('tr');
	var idVehicle=This.attr('data-bind');
	var date=trC.find('.date').html();
	var arrival=trC.find('.arrivalTime').attr('data-bind');
	trC.find('.date').html(
			"<input type='text' value='"+date+"' size='10' name='date' " 
			+ "class='default-date-picker form-control date-invehicle'"
			+ "style='padding: 2px 6px; float: left'>"
			+ "<i class='fa fa-calendar' style='float: left; margin-top: 5px'></i>"
	);
	trC.find('.date').removeClass('label-date');
	trC.find('.arrivalTime').html(
			"<input type='text' size='10' name='arrivalTime' " 
			+ "class='timepicker-24 form-control time-invehicle' "
			+ "style='padding: 2px 6px; float: left'>"
			+ "<i class='fa fa-clock-o' style='float: left; margin-top: 5px;'></i>"
	);
	trC.find('.arrivalTime').removeClass('label-time');
	trC.find('.edit-invehicle').parent().html(
		"<span class='save-invehicle option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-floppy-o'></i>"
		+ "</span>"
	);
	trC.find('.del-invehicle').parent().html(
		"<span class='cancel-invehicle option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-times'></i>"
		+ "</span>"
	);
	$('.default-date-picker').datepicker({
        format: 'yyyy-mm-dd',
    });
    $('.default-date-picker').datepicker().on('changeDate', function(e) {
    	$(this).datepicker('hide');
    });
    $('.timepicker-24').timepicker({
        minuteStep: 1,
        showMeridian: false, 
    });	
    $('.timepicker-24').val(arrival);
    
    $('.save-invehicle').click(function(e){
    	eventSaveIV($(this), e);	
    });
    $('.cancel-invehicle').click(function(e){
    	eventCancelIV($(this), date, arrival, e);
    });
}
function eventSaveIV(This, e){
	var saveIV=This;
	confirmDialog(saveIV, e).then(function(choose){
		if(choose == 'true'){
			var idVehicle=saveIV.attr('data-bind');
			var key=$('.key-invehicle-search').val();
			var record = $('.recordI').find(':selected').val();
			var date=saveIV.parents('tr').find('input[name=date]').val();
			var arrivalTime=saveIV.parents('tr').find('input[name=arrivalTime]').val();
			saveIVCurrentDay(saveIV, idVehicle, date, arrivalTime, currentPage, key, record, e);
		}
	});
}
function eventCancelIV(This, date, arrival, e){
	var idVehicle=This.attr('data-bind');
	This.parents('tr').find('.date').html(date);
	This.parents('tr').find('.arrivalTime').html(arrival);
	This.parents('tr').find('.save-invehicle').parent().
		html("<span class='edit-invehicle option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
	);
	This.parents('tr').find('.cancel-invehicle').parent().
		html("<span class='del-invehicle option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-trash-o'></i>"
			+ "</span>"
	);
//	$('.row_data').find('.edit-invehicle').removeClass('edit-invehicle').addClass('edit-invehicle-new');
//	$('.edit-invehicle').click(function(e){
//		eventEditIV($(this), e);
//	});
	$('.del-invehicle').click(function(e){
		eventDelIV($(this), e);
	});
}
function eventDelIV(This, e){
	var delIV=This;
	confirmDialog(delIV, e).then(function(choose){
		if(choose == 'true'){
			var idVehicle=delIV.attr('data-bind');
			var key=$('.key-invehicle-search').val();
			var record = $('.recordI').find(':selected').val();
			delInVehicleCurrentDay(idVehicle, currentPage, key, record, e);
		}
	});
}
function eventChangePageIV(This){
	curPage = This.attr('data-index');
	if(currentPage != curPage){
		var key=$('.key-invehicle-search').val();
		var record = $('.recordI').find(':selected').val();
		searchInVehicleCurrentDay_byKeySearch(curPage, key, record);
		currentPage=curPage;
	}
}
function eventPrePageIV(e){
	if(currentPage > 1){
		currentPage = currentPage -1
		var key=$('.key-invehicle-search').val();
		var record = $('.recordI').find(':selected').val();
		searchInVehicleCurrentDay_byKeySearch(currentPage, key, record, e);
	}
}
function eventNextPageIV(numPage, e){
	if(currentPage < numPage){
		currentPage = currentPage - (-1);
		var key=$('.key-invehicle-search').val();
		var record = $('.recordI').find(':selected').val();
		searchInVehicleCurrentDay_byKeySearch(currentPage, key, record, e);
	}
}
function saveIVCurrentDay(This, idVehicle, date, arrivalTime, currentPage, key, record, e){
	$.ajax({
        type: "POST",
        data: {
        	idVehicle: idVehicle,
        	date: date,
        	arrivalTime: arrivalTime, 
        	currentPage: currentPage,
        	keySearch: key,
        	record: record,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/saveIVCurrentDay',
        success: function (response) {
        	if(response==true){
        		tr = This.parents('tr');
        		tr.find('.date').html(date);
        		tr.find('.arrivalTime').html(arrivalTime);
        		tr.find('.save-invehicle').parent().html(
        				"<span class='edit-invehicle option-vehicle' data-bind='"+idVehicle+"'>"
        					+ "<i class='fa fa-pencil-square-o'></i>"
						+ "</span>"
        		);
        		tr.find('.cancel-invehicle').parent().html(
        				"<span class='del-invehicle option-vehicle' data-bind='"+idVehicle+"'>"
        					+ "<i class='fa fa-trash-o'></i>"
						+ "</span>"
        		);
        		$('.edit-invehicle').click(function(e){
        			eventEditIV($(this), e);
        		});
        		$('.del-invehicle').click(function(e){
        			eventDelIV($(this), e);
        		});
        		tr.addClass('change-vehicle');
//        		numPage = response[0];
//        		arrIV = response[1];
//        		showDataInVehicleCurrentDay_byKeySearch(numPage, arrIV, e);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function searchInVehicleCurrentDay_byKeySearch(currentPage, keySearch, record, e){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage + '&keySearch=' + keySearch + "&record=" + record,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageInVehicleCurrentDay_byKeySearch',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		pager = response[0];
        		arrIV = response[1];
        		showDataInVehicleCurrentDay_byKeySearch(currentPage, pager, arrIV, e);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
$(document).ready(function(){
	$('.recordO').change( function (e){
		var key=$('.key-outvehicle-search').val();
		var record = $(this).find(':selected').val();
		searchOutVehicleCurrentDay_byKeySearch(currentPage, key, record, e);
	});
	$('#addOV').submit( function(e) {
		eventInsertOV($(this), e);
	});
	$('.del-outvehicle').click(function(e){
		eventDelOV($(this), e);
	});
	$('.edit-outvehicle').click(function(e){
		eventEditOV($(this), e);
	});
	$('.page-ov .page_index').click(function(e) {
		eventChangePageOV($(this), e);
	});
	$('.page-ov .prev').click(function(e){
		eventPrePageOV(e);
	});
	$('.page-ov .next').click(function(e){
		var numPage = $('.num-page').attr('data-bind');
		eventNextPageOV(numPage, e);
	});
	$('#btn-search-outvehicle').submit(function(e){
		var record = $('select.recordO').val();
		var keySearch=$('.key-outvehicle-search').val();
		var currentPage=1;
		searchOutVehicleCurrentDay_byKeySearch(currentPage, keySearch, record, e);
		e.preventDefault();
	});
});
function eventEditOV(This, e){
	var trcOld=This.parents('.row_data').find('.save-outvehicle').parents('tr');
	if(trcOld.html() != null){
		var dateOld=trcOld.find('input[name=date]').val();
		var arrivalOld=trcOld.find('input[name=arrivalTime]').val();
		eventCancelOV(trcOld.find('.cancel-outvehicle'), dateOld, arrivalOld, e);
	}
	/*Tr-New*/
	var trC=This.parents('tr');
	var idVehicle=This.attr('data-bind');
	var date=trC.find('.date').html();
	var arrival=trC.find('.arrivalTime').attr('data-bind');
	trC.find('.date').html(
			"<input type='text' value='"+date+"' size='10' name='date' " 
			+ "class='default-date-picker form-control date-outvehicle'"
			+ "style='padding: 2px 6px; float: left'>"
			+ "<i class='fa fa-calendar' style='float: left; margin-top: 5px'></i>"
	);
	trC.find('.date').removeClass('label-date');
	trC.find('.arrivalTime').html(
			"<input type='text' size='10' name='arrivalTime' " 
			+ "class='timepicker-24 form-control time-outvehicle' "
			+ "style='padding: 2px 6px; float: left'>"
			+ "<i class='fa fa-clock-o' style='float: left; margin-top: 5px;'></i>"
	);
	trC.find('.arrivalTime').removeClass('label-time');
	trC.find('.edit-outvehicle').parent().html(
		"<span class='save-outvehicle option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-floppy-o'></i>"
		+ "</span>"
	);
	trC.find('.del-outvehicle').parent().html(
		"<span class='cancel-outvehicle option-vehicle' data-bind='"+idVehicle+"'>"
			+ "<i class='fa fa-times'></i>"
		+ "</span>"
	);
	$('.default-date-picker').datepicker({
        format: 'yyyy-mm-dd',
    });
    $('.default-date-picker').datepicker().on('changeDate', function(e) {
    	$(this).datepicker('hide');
    });
    $('.timepicker-24').timepicker({
        minuteStep: 1,
        showMeridian: false, 
    });	
    $('.timepicker-24').val(arrival);
    
    $('.save-outvehicle').click(function(e){
    	eventSaveOV($(this), e);	
    });
    $('.cancel-outvehicle').click(function(e){
    	eventCancelOV($(this), date, arrival, e);
    });
}
function eventSaveOV(This, e){
	var saveOV=This;
	confirmDialog(saveOV, e).then(function(choose){
		if(choose == 'true'){
			var idVehicle=saveOV.attr('data-bind');
			var key=$('.key-outvehicle-search').val();
			var record = $('.recordO').find(':selected').val();
			var date=saveOV.parents('tr').find('input[name=date]').val();
			var arrivalTime=saveOV.parents('tr').find('input[name=arrivalTime]').val();
			saveOVCurrentDay(saveOV, idVehicle, date, arrivalTime, currentPage, key, record, e);
		}
	});
}
function eventCancelOV(This, date, arrival, e){
	var idVehicle=This.attr('data-bind');
	This.parents('tr').find('.date').html(date);
	This.parents('tr').find('.arrivalTime').html(arrival);
	This.parents('tr').find('.save-outvehicle').parent().
		html("<span class='edit-outvehicle option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-pencil-square-o'></i>"
			+ "</span>"
	);
	This.parents('tr').find('.cancel-outvehicle').parent().
		html("<span class='del-outvehicle option-vehicle' data-bind='"+idVehicle+"'>"
				+ "<i class='fa fa-trash-o'></i>"
			+ "</span>"
	);
	$('.edit-outvehicle').click(function(e){
		eventEditOV($(this), e);
	});
	$('.del-outvehicle').click(function(e){
		eventDelIV($(this), e);
	});
}
function saveOVCurrentDay(This, idVehicle, date, arrivalTime, currentPage, key, record, e){
	$.ajax({
        type: "POST",
        data: {
        	idVehicle: idVehicle,
        	date: date,
        	arrivalTime: arrivalTime, 
        	currentPage: currentPage,
        	keySearch: key,
        	record: record,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/saveOVCurrentDay',
        success: function (response) {
        	if(response==true){
        		tr = This.parents('tr');
        		tr.find('.date').html(date);
        		tr.find('.arrivalTime').html(arrivalTime);
        		tr.find('.save-outvehicle').parent().html(
        				"<span class='edit-outvehicle option-vehicle' data-bind='"+idVehicle+"'>"
        					+ "<i class='fa fa-pencil-square-o'></i>"
						+ "</span>"
        		);
        		tr.find('.cancel-outvehicle').parent().html(
        				"<span class='del-outvehicle option-vehicle' data-bind='"+idVehicle+"'>"
        					+ "<i class='fa fa-trash-o'></i>"
						+ "</span>"
        		);
        		$('.edit-outvehicle').click(function(e){
        			eventEditOV($(this), e);
        		});
        		$('.del-outvehicle').click(function(e){
        			eventDelOV($(this), e);
        		});
        		tr.addClass('change-vehicle');
//        		numPage = response[0];
//        		arrOV = response[1];
//        		showDataOutVehicleCurrentDay_byKeySearch(numPage, arrOV, e);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function showDataOutVehicleCurrentDay_byKeySearch(currentPage, pager, arrOV, e){
	var content="";
	contentPage="";
	if(arrOV.length < 1){
		$('.adv-table table').after("<span class='no-record'>" + noRecord + "</span>");
	}else{
		$('.adv-table .no-record').remove();
		for(var i=0; i<arrOV.length; i++){
			var status="";
			if(arrOV[i].status ==0) status="Waitting Assign Door";
			else if(arrOV[i].status ==1) status="Watting Unload";
			else if(arrOV[i].status ==2) status="Is Unloading";
			else status="UnLoading is Finish";
			content+=
				"<tr>"
					+ "<td>"+arrOV[i].vehicleCode+"</td>"
					+ "<td>"+standardizeStr(arrOV[i].company)+"</td>"
					+ "<td class='date'>"+arrOV[i].date+"</td>"
					+ "<td class='arrivalTime' data-bind='"+arrOV[i].arrivalTime+"'>"
						+ arrOV[i].arrivalTime
					+ "</td>"
					+ "<td>"+arrOV[i].demand+"</td>"
					+ "<td>"+status+"</td>"
					+ "<td>"+standardizeStr(arrOV[i].door.nameDoor)+"</td>"
					+ "<td><span class='edit-outvehicle option-vehicle' data-bind='"+arrOV[i].idOutVehicle+"'>" 
						+ "<i class='fa fa-pencil-square-o'></i>" 
					+ "</span></td>"
					+ "<td><span class='del-outvehicle option-vehicle' data-bind='"+arrOV[i].idOutVehicle+"'>"
						+ "<i class='fa fa-trash-o'></i>"
					+ "</span></td>"
					+ "<td><span class='detail-outvehicle option-vehicle' data-bind='"+arrOV[i].idOutVehicle+"'>"
						+ "<i class='fa fa-list-alt'></i>"
					+ "</span></td>";
				+ "</tr>";
		}
		contentPage += "<li class='prev'><a>←</a></li>";
		var arrPage = pager.listPage;
		for(var i=0; i<arrPage.length; i++){
			if(arrPage[i] == currentPage){
				contentPage +="<li class='page_index active' data-index="+arrPage[i]+"><a>"+arrPage[i]+"</a></li>";
			}else{
				contentPage +="<li class='page_index' data-index="+arrPage[i]+"><a>"+arrPage[i]+"</a></li>";
			}
		}
		contentPage += "<li class='next'><a>→ </a></li>";
	}
	$('.row_data').html(content);
	$('.page-ov').html(contentPage);
	$('.edit-outvehicle').click(function(e){
		eventEditOV($(this), e);
	});
	$('.del-outvehicle').click(function(e){
		eventDelOV($(this), e);
	});
	$('.page-ov .page_index').click(function() {
		eventChangePageOV($(this), e);
	});
	$('.page-ov .prev').click(function(){
		eventPrePageOV(e);
	});
	$('.page-ov .next').click(function(){
		eventNextPageOV(pager.numPage, e);
	});
	$('.detail-outvehicle').click(function(){
		var idVehicle = $(this).attr('data-bind');
		displayProductOV(idVehicle);
		$('.dialog-pov').dialog('open');
	});
}
function insertOutVehicle(record, e){
	$.ajax({
        type: "POST",
        data: $("#addOV").serialize() + '&record=' + record,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/insertOutVehicle',
        success: function (response) {
        	if(response == ''){
        		$('.msg-insert-ov').html("<label class='msg-error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errExistsVehicle+"</label>");
        	}else{
        		$('.msg-insert-ov').html("<label class='msg-success'><i class='fa fa-check icon-msg'></i>"+successInsertV+"</label>");
        		pager = response[0];
        		arrOV = response[1];
        		currentPage = 1;
        		showDataOutVehicleCurrentDay_byKeySearch(currentPage, pager, arrOV, e);        		
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function delOutVehicleCurrentDay(idVehicle, currentPage, keySearch, record, e){
	$.ajax({
        type: "POST",
        data: 'idVehicle=' + idVehicle + '&currentPage=' + currentPage + '&keySearch=' + keySearch + "&record=" + record,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/delOutVehicleCurrentDay',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		pager = response[0];
        		arrOV = response[1];
        		showDataOutVehicleCurrentDay_byKeySearch(currentPage, pager, arrOV, e);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function eventInsertOV(This, e){
	var addOV=This;
	confirmDialog(addOV, e).then(function(choose){
		if(choose == 'true'){
			validateInsertVehicle(addOV);
			var isValid=addOV.valid();
			if(isValid){
				var record=$('.recordO').find(':selected').val();
				insertOutVehicle(record, e);
				e.preventDefault();
			}
		}
	});
}
function eventDelOV(This, e){
	var delOV=This;
	confirmDialog(delOV, e).then(function(choose){
		if(choose == 'true'){
			var idVehicle=delOV.attr('data-bind');
			var key=$('.key-outvehicle-search').val();
			var record = $('.recordO').find(':selected').val();
			delOutVehicleCurrentDay(idVehicle, currentPage, key, record, e);
		}
	});
}
function eventChangePageOV(This, e){
	curPage = This.attr('data-index');
	if(currentPage != curPage){
		var key=$('.key-outvehicle-search').val();
		var record = $('.recordO').find(':selected').val();
		searchOutVehicleCurrentDay_byKeySearch(curPage, key, record, e);
		currentPage=curPage;
	}
}
function eventPrePageOV(e){
	if(currentPage > 1){
		currentPage = currentPage -1
		var key=$('.key-outvehicle-search').val();
		var record = $('.recordO').find(':selected').val();
		searchOutVehicleCurrentDay_byKeySearch(currentPage, key, record, e);
	}
}
function eventNextPageOV(numPage, e){
	if(currentPage < numPage){
		currentPage = currentPage - (-1);
		var key=$('.key-outvehicle-search').val();
		var record = $('.recordO').find(':selected').val();
		searchOutVehicleCurrentDay_byKeySearch(currentPage, key, record, e);
	}
}
function searchOutVehicleCurrentDay_byKeySearch(currentPage, keySearch, record, e){
	$.ajax({
        type: "POST",
        data: 'currentPage=' + currentPage + '&keySearch=' + keySearch + "&record=" + record,
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPageOutVehicleCurrentDay_byKeySearch',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		pager = response[0];
        		arrOV = response[1];
        		showDataOutVehicleCurrentDay_byKeySearch(currentPage, pager, arrOV, e);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
$(document).ready(function(){
	var code='';
	var company='';
	var date='';
	var record=15;
	
	var curDate = new Date(), y = curDate.getFullYear(), m = curDate.getMonth();
	var fSDate = new Date(y, m, 1);
	var fEDate = new Date(y, m + 1, 0);
	
	$('.rp-piv-date').datepicker({
		format: 'yyyy-mm-dd',
		minDate: fSDate,
		maxDate: fEDate,
	});
	$('.rp-piv-date .ui-datepicker-prev').css('display', 'none');
	$('.record-rp-inv').change(function(){
		record = $(this).val();
		getPagePIVReportCurMonth(code, company, date, currentPage, record);
	});
	$('.page-rpinv .page_index').click(function(){
		curPage = $(this).attr('data-index');
//		if(curPage != currentPage){
			getPagePIVReportCurMonth(code, company, date, curPage, record);
			currentPage = curPage;
//		}
	});
	$('.page-rpinv .next').click(function(){
		var numPage = $(this).parent().find('.num-page').attr('data-bind');
		if(currentPage < numPage){
			currentPage = parseInt(currentPage) + 1;
			getPagePIVReportCurMonth(code, company, date, currentPage, record);
		}
	});
	$('#btn-search-rpiv').click(function(){
		code = $('.key-invehicle-search').val();
		company = $(this).parents('.cmxform').find('.company').val();
		date = $('.rp-piv-date').val();
		currentPage = 1;
		getPagePIVReportCurMonth(code, company, date, currentPage, record);
	});
});
function getPagePIVReportCurMonth(code, company, date, currentPage, record){
	$.ajax({
        type: "POST",
        data: {
        	code: code,
        	company: company, 
        	date: date,
        	record: record,
        	currentPage: currentPage,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPagePIVReportCurMonth',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showDataReportPIVCurMonth(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function showDataReportPIVCurMonth(response){
	var pager=response[0];
	var arrPIV=response[1];
	var content="";
	contentPage="";
	if(arrPIV.length < 1){
		$('.rowPIV_data').html('');
		$('.page-rpinv').html('');
		$('.rowPIV_data').parents('.adv-table').find('.no-record').remove();
		$('.rowPIV_data').parents('.adv-table').find('table').after("<span class='no-record'>" + noRecord + "</span>");
	}else{
		$('.rowPIV_data').parents('.adv-table').find('.no-record').remove();
		for(var i=0; i<arrPIV.length; i++){
			content+=
				"<tr>"
					+ "<td>" +arrPIV[i].iv.vehicleCode+ "</td>"
					+ "<td>" +arrPIV[i].iv.company+ "</td>"
					+ "<td>" +arrPIV[i].iv.date+"</td>"
					+ "<td>" +arrPIV[i].iv.arrivalTime+ "</td>"
					+ "<td>" +arrPIV[i].p.name+ "</td>"
					+ "<td>" +arrPIV[i].quantity+ "</td>"
					+ "<td>" +arrPIV[i].u.name+ "</td>"
				+ "</tr>";
		}
		contentPage += "<li class='prev'><a>←</a></li>";
		var arrPage = pager.listPage;
		for(var i=0; i<arrPage.length; i++){
			if(arrPage[i] == currentPage){
				contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}else{
				contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}
		}
		contentPage += "<li class='next'><a>→ </a></li>";
		$('.rowPIV_data').html(content);
		$('.page-rpinv').html(contentPage);
		
		$('.page-rpinv .page_index').click(function(){
			var code = $('.key-invehicle-search').val();
			var company = $('.company').val();
			var date = $('.date').val();
			var record=$('.record-rp-inv').val();
			var curPage = $(this).attr('data-index');
			
			if(curPage != currentPage){
				getPagePIVReportCurMonth(code, company, date, curPage, record);
				currentPage = curPage;
			}
		});
		$('.page-rpinv .prev').click(function(){
			var code = $('.key-invehicle-search').val();
			var company = $('.company').val();
			var date = $('.date').val();
			var record=$('.record-rp-inv').val();
			var curPage = $(this).attr('data-index');
			
			if(currentPage > 1){
				currentPage = parseInt(currentPage) - 1;
				getPagePIVReportCurMonth(code, company, date, currentPage, record);
			}
		})
		$('.page-rpinv .next').click(function(){
			var code = $('.key-invehicle-search').val();
			var company = $('.company').val();
			var date = $('.date').val();
			var record=$('.record-rp-inv').val();
			var curPage = $(this).attr('data-index');
			
			if(currentPage < pager.numPage){
				currentPage = parseInt(currentPage) + 1;
				getPagePIVReportCurMonth(code, company, date, currentPage, record);
			}
		});
	}
}

$(document).ready(function(){
	var code='';
	var company='';
	var datePOV='';
	var record=15;
	
	var curDate = new Date(), y = curDate.getFullYear(), m = curDate.getMonth();
	var fSDate = new Date(y, m, 1);
	var fEDate = new Date(y, m + 1, 0);
	
//	$('.picker-month').MonthPicker();
//	$('.picker-month').datepicker({
//		dateFormat: 'yy-mm',
//	    changeMonth: true,
//	    changeYear: false,
//	    onChangeMonthYear: function (year, month, day) {
//	        $(this).datepicker('setDate', new Date(year, month - 1, day.selectedDay));
//	    }
//    });
	$('.rp-pov-date').datepicker({
		format: 'yyyy-mm-dd',
		minDate: fSDate,
		maxDate: fEDate,
	});
	$('.record-rp-outv').change(function(){
		record = $(this).val();
		getPagePOVReportCurMonth(code, company, datePOV, currentPage, record);
	});
	$('.page-rpoutv .page_index').click(function(){
		curPage = $(this).attr('data-index');
		if(curPage != currentPage){
			getPagePOVReportCurMonth(code, company, datePOV, curPage, record);
			currentPage = curPage;
		}
	});
	$('.page-rpoutv .next').click(function(){
		var numPage = $(this).parent().find('.num-page').attr('data-bind');
		if(currentPage < numPage){
			currentPage = parseInt(currentPage) + 1;
			getPagePOVReportCurMonth(code, company, datePOV, currentPage, record);
		}
	});
	$('#btn-search-rpov').click(function(){
		code = $('.key-outvehicle-search').val();
		company = $(this).parents('.cmxform').find('.company').val();
		datePOV = $('.rp-pov-date').val();
		currentPage = 1;
		getPagePOVReportCurMonth(code, company, datePOV, currentPage, record);
	});
});
function getPagePOVReportCurMonth(code, company, date, currentPage, record){
	$.ajax({
        type: "POST",
        data: {
        	code: code,
        	company: company, 
        	date: date,
        	record: record,
        	currentPage: currentPage,
        },
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/getPagePOVReportCurMonth',
        success: function (response) {
        	if(response == null){
        		alert("Error");
        	}else{
        		showDataReportPOVCurMonth(response);
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function showDataReportPOVCurMonth(response){
	var pager=response[0];
	var arrPOV=response[1];
	var content="";
	contentPage="";
	if(arrPOV.length < 1){
		$('.rowPOV_data').html('');
		$('.page-rpoutv').html('');
		$('.rowPOV_data').parents('.adv-table').find('.no-record').remove();
		$('.rowPOV_data').parents('.adv-table').find('table').after("<span class='no-record'>" + noRecord + "</span>");
	}else{
		$('.rowPOV_data').parents('.adv-table').find('.no-record').remove();
		for(var i=0; i<arrPOV.length; i++){
			content+=
				"<tr>"
					+ "<td>" +arrPOV[i].ov.vehicleCode+ "</td>"
					+ "<td>" +arrPOV[i].ov.company+ "</td>"
					+ "<td>" +arrPOV[i].ov.date+"</td>"
					+ "<td>" +arrPOV[i].ov.arrivalTime+ "</td>"
					+ "<td>" +arrPOV[i].p.name+ "</td>"
					+ "<td>" +arrPOV[i].quantity+ "</td>"
					+ "<td>" +arrPOV[i].u.name+ "</td>"
				+ "</tr>";
		}
		contentPage += "<li class='prev'><a>←</a></li>";
		var arrPage = pager.listPage;
		for(var i=0; i<arrPage.length; i++){
			if(arrPage[i] == currentPage){
				contentPage += "<li class='page_index active' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}else{
				contentPage += "<li class='page_index' data-index='"+arrPage[i]+"'><a>"+arrPage[i]+"</a></li>"
			}
		}
		contentPage += "<li class='next'><a>→ </a></li>";
		
		$('.rowPOV_data').html(content);
		$('.page-rpoutv').html(contentPage);
		
		
		$('.page-rpoutv .page_index').click(function(){
			var code = $('.key-outvehicle-search').val();
			var company = $('.company').val();
			var date = $('.date').val();
			var record=$('.record-rp-outv').val();
			var curPage = $(this).attr('data-index');
			
			if(curPage != currentPage){
				getPagePOVReportCurMonth(code, company, date, curPage, record);
				currentPage = curPage;
			}
		});
		$('.page-rpoutv .prev').click(function(){
			var code = $('.key-outvehicle-search').val();
			var company = $('.company').val();
			var date = $('.date').val();
			var record=$('.record-rp-outv').val();
			var curPage = $(this).attr('data-index');
			
			if(currentPage > 1){
				currentPage = parseInt(currentPage) - 1;
				getPagePOVReportCurMonth(code, company, date, currentPage, record);
			}
		})
		$('.page-rpoutv .next').click(function(){
			var code = $('.key-outvehicle-search').val();
			var company = $('.company').val();
			var date = $('.date').val();
			var record=$('.record-rp-outv').val();
			var curPage = $(this).attr('data-index');
			
			if(currentPage < pager.numPage){
				currentPage = parseInt(currentPage) + 1;
				getPagePOVReportCurMonth(code, company, date, currentPage, record);
			}
		});
	}
}
$(document).ready(function(){
	$('.dialog-change-pass').dialog({
		title: 'Change password',
		autoOpen: false,
		modal : true,     
     	width: 450,
	});
	$('.dialog-change-profile').dialog({
		title: 'Change profile',
		autoOpen: false,
		modal : true,     
     	width: 450,
	});
	$('.emp-profile').click(function(){
		$('.dialog-change-profile').dialog('open');
	});
	$('.emp-password').click(function(){
		$('.dialog-change-pass').dialog('open');
	});
	$('#changePass').submit(function(e){
		changePassword();
		e.preventDefault();
	});
	$('#changeProfile').submit(function(e){
		changeProfile();
		e.preventDefault();
	});
});
function changePassword(){
	$.ajax({
        type: "POST",
        data: $('#changePass').serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/changePassword',
        success: function (response) {
        	if(response == "success"){
        		$('#changePass .msg').html("<span class='msg-success'>" +successChangePass+ "</span>");
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
function changeProfile(){
	$.ajax({
        type: "POST",
        data: $('#changeProfile').serialize(),
        header: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
		url: contextPath + '/changeProfile',
        success: function (response) {
        	if(response == "success"){
        		$('#changeProfile .msg').html("<span class='msg-success'>" +successChangeProfile+ "</span>");
        	}
        },
        error: function (xhr, status, error) {
        	alert(error + status + xhr);
        }
    });
}
