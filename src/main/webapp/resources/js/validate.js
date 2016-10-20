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
//	var str="<p>test</p>";
//	console.log(removeHTMLTag(str));
});
function removeHTMLTag(str){
	var regex = /(<([^>]+)>)/ig;
	var result = str.replace(regex, '');
	return result; 
}
function standardizeStr(str){
	if(str == null) return '';
	return str;
}