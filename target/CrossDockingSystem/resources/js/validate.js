var errNameD, errLengthNameD, errCapD, errTypeCapD;
var errV, errLengthV, errCompany;
var errVehicle, errDate, errArrival, errVol, errTypeVol, errDemand, errTypeDemand;
if(getCurrentLanguage() == "en"){
	errNameD="Please enter name door";
	errLengthNameD="Please enter at least 5 characters";
	errCapD="Please enter capacity door";
	errTypeCapD="Please just enter number";
	errTransitionActive="You need to setup the cost of the door before transition active state";
	
	errV="Please enter code vehicle";
	errLengthV="Please enter at least 5 characters";
	errCompany="Please enter name company";
	
	errVehicle="Please select a vehicle";
	errDate="Please picker date vehicle will come";
	errArrival="Please picker time vehicle will come";
	errVol="Please enter volumn vehicle";
	errTypeVol="Please just enter number";
	errDemand="Please enter demand vehicle";
	errTypeDemand="Please just enter number";
}else{
	errNameD="Bạn cần nhập tên cửa";
	errLengthNameD="Bạn cần nhập ít nhất 5 kí tự";
	errCapD="Bạn cần nhập sức chứa của cửa";
	errTypeCapD="Bạn chỉ có thể nhập kí tự là số";
	errTransitionActive="Bạn cần thiêt lập chi phí cho cửa trước khi chuyển sang trạng thái hoạt động";
	
	errV="Bạn cần nhập biển số xe";
	errLengthV="Bạn cần nhập ít nhất 5 kí tự";
	errCompany="Bạn cần nhập tên công ty vận chuyển";
	
	errVehicle="Bạn cần chọn một xe";
	errDate="Bạn cần chọn ngày xe đến";
	errArrival="Bạn cần chọn thời gian xe đến";
	errVol="Bạn cần nhập sức chứa của xe";
	errTypeVol="Bạn chỉ có thế nhập kí tự là số";
	errDemand="Bạn cần nhập nhu cầu của xe";
	errTypeDemand="Bạn chỉ có thể nhập số";
}
$(document).ready(function(){
	
	$('#addID').validate({
		rules: {
			nameD: {
				required: true, 
				minlength: 5,
			},
			capD: {
				required: true,
				number: true,
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
			}
		}
	});
	$('#addOD').validate({
		rules: {
			nameD: {
				required: true, 
				minlength: 5,
			},
			capD: {
				required: true,
				number: true,
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
			}
		}
	});	
	$('#addV').validate({
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
	});
	$('#addIV').validate({
		rules: {
			idVehicle: {
				required: true,
			},
			date: {
				required: true,
			},
			arrivalTime: {
				required: true,
			},
			volumn: {
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
			},
			arrivalTime: {
				required: errArrival,
			},
			volumn: {
				required: errVol,
				number: errTypeVol,
			}
		},
	});
	$('#addOV').validate({
		rules: {
			idVehicle: {
				required: true,
			},
			date: {
				required: true,
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
			},
			arrivalTime: {
				required: errArrival,
			},
			demand: {
				required: errDemand,
				number: errTypeDemand,
			}
		},
	});
	checkNumber();
});
function getCurrentLanguage(){
	var lang=$('.lang').attr('data-bind');
	return lang;
}
function checkNumber(str){
	var regex = /^[0-9]*$/gm;
	return regex.test(str);
}
function validateDoor(trC, nameD, capD){
	var isValid=false;
	var validN, validC;
	if(nameD == ''){
		trC.find('.nameD').css('border', '1px solid red');
		trC.parents('.adv-table').find('.msg-error.nameD').remove();
		trC.parents('table').before("<section class='msg-error nameD'>"+errNameD+"</section>");
		validN=false;
	}else if(nameD.length < 5){
		trC.find('.nameD').css('border', '1px solid red');
		trC.parents('.adv-table').find('.msg-error.nameD').remove();
		trC.parents('table').before("<section class='msg-error nameD'>"+errLengthNameD+"</section>");
		validN=false;
	}else{
		trC.parents('.adv-table').find('.msg-error.nameD').remove();
		trC.find('.nameD').css('border', '');
		validN=true;
	}		
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
	if(validN && validC) isValid=true;
	return isValid;
}
$(document).ready(function(){
	var str="<p>test</p>";
	console.log(removeHTMLTag(str));
});
function removeHTMLTag(str){
	var regex = /(<([^>]+)>)/ig;
	var result = str.replace(regex, '');
	return result; 
}