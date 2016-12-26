$(document).ready(function(){
	
	
	checkNumber();
});
function checkNumber(str){
	var regex = /^[0-9]*$/gm;
	return regex.test(str);
}
function validateInsertDoor(This){
	This.validate({
		rules: {
			nameD: {
				required: true, 
				minlength: 5,
			},
			capD: {
				required: true,
				number: true,
				min: 1,
			}
		},
		messages: {
			nameD: {
				required: errNameD,
				minlength: errLengthNameD,
			},
			capD: {
				required: errCapD,
				number: errTypeCapD,
				min: "NOT < 1",
			}
		}
	}).form();
}
function validateEditDoor(trC, capD){
	var isValid=false;
	var validC;
	if(capD == ''){
		trC.find('.capD').css('border', '1px solid red');
		trC.parents('.adv-table').find('.msg-error.capD').remove();
		trC.parents('table').before("<section class='msg-error capD'>"+errCapD+"</section>");
		validC=false;
	}else if(!checkNumber(capD)){
		trC.find('.capD').css('border', '1px solid red');
		trC.parents('.adv-table').find('.msg-error.capD').remove();
		trC.parents('table').before("<section class='msg-error capD'>"+errTypeCapD+"</section>");
		validC=false;
	}else{
		trC.parents('.adv-table').find('.msg-error.capD').remove();
		trC.find('.capD').css('border', '');
		validC=true;
	}
	if(validC) isValid=true;
	return isValid;
}
function validateInsertV(This){
	This.validate({
		rules: {
			code:{
				required: true,
				minlength: 5
			},
			company:{
				required: true,
			}
		},
		messages: {
			code: {
				required: errV,
				minlength: errLengthV
			},
			company: {
				required: errCompany
			}
		
		}
	}).form();
}
function validateInsertVehicle(This){
	This.validate({
		rules: {
			idVehicle: {
				required: true,
			},
			date: {
				required: true,
				date: true,
			},
			arrivalTime: {
				required: true,
			},
			demand: {
				required: true,
				number: true,
			}
			
		},
		messages: {
			idVehicle: {
				required: errVehicle,
			},
			date: {
				required: errDate,
				date: "Error Date",
			},
			arrivalTime: {
				required: errArrival,
			},
			demand: {
				required: errDemand,
				number: errTypeDemand,
			}
		},
	}).form();
}
function validateInsertPIV(This){
	This.validate({
		rules:{
			pName: {
				required: true, 
			},
			pQuantity: {
				required: true,
				number: true, 
				min: 1,
			}
		},
		messages: {
			pName: {
				required: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errProduct,
			},
			pQuantity: {
				required: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errQuantity,
				number: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errTypeQuantity,
				min: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errMinQuantity,
			}
		},
		errorPlacement: function(error, element) {
			error.appendTo('#msgInsertTab1');
		},
	}).form();
}
function validateEditPIV(tr, quantity){
	var divEditErr=tr.parents('.adv-table').find('#msgEditTabs1');
	if(quantity == ''){
		divEditErr.html("<label class='error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errQuantity+"</label>");
		tr.find('input.quantity').css('border', '1px solid red');
		return false;
	}else if(!checkNumber(quantity)){
		divEditErr.html("<label class='error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errTypeQuantity+"</label>");
		tr.find('input.quantity').css('border', '1px solid red');
		return false;
	}else if(parseInt(quantity) < 1){
		divEditErr.html("<label class='error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errMinQuantity+"</label>");
		tr.find('input.quantity').css('border', '1px solid red');
		return false;
	}
	return true;
}

function validateInsertPOV(This){
	var maxQuantity = parseInt(This.find('.quantity-piv').html());
	This.validate({
		rules:{
			ovCode: {
				required: true,
			},
			pName: {
				required: true, 
			},
			pQuantity: {
				required: true,
				number: true, 
				min: 1,
				max: maxQuantity,
			}
		},
		messages: {
			ovCode: {
				required: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errVehicle,
			},
			pName: {
				required: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errProduct,
			},
			pQuantity: {
				required: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errQuantity,
				number: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errTypeQuantity,
				min: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errMinQuantity,
				max: "<i class='fa fa-exclamation-triangle icon-msg'></i>" + errMaxQuantity,
			}
		},
		errorPlacement: function(error, element) {
			error.appendTo('#msgInsertTab2');
		},
	}).form();
}
function validateEditPOV(tr, quantity){
	var divEditErr=tr.parents('.adv-table').find('#msgEditTabs2');
	if(quantity == ''){
		divEditErr.html("<label class='error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errQuantity+"</label>")
		tr.find('input.quantity').css('border', '1px solid red');
		return false;
	}else if(!checkNumber(quantity)){
		divEditErr.html("<label class='error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errTypeQuantity+"</label>")
		tr.find('input.quantity').css('border', '1px solid red');
		return false;
	}else if(parseInt(quantity) < 1){
		divEditErr.html("<label class='error'><i class='fa fa-exclamation-triangle icon-msg'></i>"+errMinQuantity+"</label>")
		tr.find('input.quantity').css('border', '1px solid red');
		return false;
	}
	return true;
}

function removeHTMLTag(str){
	var regex = /(<([^>]+)>)/ig;
	var result = str.replace(regex, '');
	return result; 
}
function standardizeStr(str){
	if(str == null) return '';
	return str;
}
function confirmDialog(This, e){
	e.preventDefault();
	var defer = $.Deferred();
    var dynamicDialog = $('<div id="conformBox">'+
		'<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;">'+
    	'</span>Are you sure to delete the item?</div>');
    dynamicDialog.dialog({
        title : "Are you sure?",
        closeOnEscape: true,
        modal : true,        
        buttons : 
			[{
				text : "Yes",
				click : function() {
					$(this).dialog("close");
					defer.resolve("true");
				}
			},{
				text : "No",
				click : function() {
					$(this).dialog("close");
					defer.resolve("false");
				}
			}]
    });
    return defer.promise();
}