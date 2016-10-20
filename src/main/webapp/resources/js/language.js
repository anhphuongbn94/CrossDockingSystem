var errNameD;
if(getCurrentLanguage() == "en"){
	noRecord="No records";
	
	waitAssStatus="Waitting Assign Door";
	waitUnloadStatus="Waiting Unload";
	startUnloadStatus="Start Unload";
	finishUnloadStatus="Finish Unload";
	waitLoadStatus="Waiting Load";
	startLoadStatus="Start Load";
	finishLoadStatus="Finish Load";
	
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
	errExistsVehicle="Vehicle information entered already exists";
}else{
	noRecord="Không có bản ghi nào";
	
	waitAssStatus="Đang chờ gán cửa";
	waitUnloadStatus="Đang chờ dỡ hàng";
	startUnloadStatus="Bắt đầu dỡ hàng";
	finishUnloadStatus="Kết thúc dỡ hàng";
	waitLoadStatus="Đang đợi lấy hàng";
	startLoadStatus="Bắt đầu lấy hàng";
	finishLoadStatus="Kết thúc lấy hàng";
	
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
	errExistsVehicle="Thông tin xe nhập đã tồn tại";
}

function getCurrentLanguage(){
	var lang=$('.lang').attr('data-bind');
	return lang;
}