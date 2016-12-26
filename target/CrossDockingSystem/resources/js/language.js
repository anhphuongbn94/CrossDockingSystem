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
	
	activeStatus = "Active";
	inactiveStatus = "InActive";
	
	errNameD="Please enter name door";
	errLengthNameD="Please enter at least 5 characters";
	errCapD="Please enter capacity door";
	errTypeCapD="Please just enter number";
	errUpdateDoor="Please not update the door is being used";
	errDeleteDoor="Please not delete the door is being used";
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
	
	errProduct="Please select a product";
	errQuantity="Please enter quantity product";
	errTypeQuantity="Please just enter number";
	errMinQuantity="Quantity of product should be greater than 0";
	errMaxQuantity="Please not entered ";
	
	errEditPIV="You can not update the quantity of product is less than quantity of product is used";
	errDelPIV="You can not delete the product is used";
	
	successInsertV="Vehicle information is add successfully!";
	successInsertProduct = "Vehicle product information is add successfully!";
	successChangePass = "Your password has been changed";
	successChangeProfile = "Your personal information has been updated";
}else{
	noRecord="Không có bản ghi nào";
	
	activeStatus = "Active";
	inactiveStatus = "InActive";
	
	waitAssStatus="Đang chờ gán cửa";
	waitUnloadStatus="Đang chờ dỡ hàng";
	startUnloadStatus="Bắt đầu dỡ hàng";
	finishUnloadStatus="Kết thúc dỡ hàng";
	waitLoadStatus="Đang đợi lấy hàng";
	startLoadStatus="Bắt đầu lấy hàng";
	finishLoadStatus="Kết thúc lấy hàng";
	
	errNameD="Vui lòng nhập tên cửa";
	errLengthNameD="Vui lòng nhập ít nhất 5 kí tự";
	errCapD="Vui lòng nhập sức chứa của cửa";
	errTypeCapD="Vui lòng chỉ nhập kí tự là số";
	errUpdate="Vui lòng không cập nhật các cửa đang được sử dụng";
	errDeleteDoor="Vui lòng không xóa các cửa đang được sử dụng";
	errTransitionActive="Vui lòng thiêt lập chi phí cho cửa trước khi chuyển sang trạng thái hoạt động";
	
	errV="Vui lòng nhập biển số xe";
	errLengthV="Vui lòng nhập ít nhất 5 kí tự";
	errCompany="Vui lòng nhập tên công ty vận chuyển";
	
	errVehicle="Vui lòng chọn một xe";
	errDate="Vui lòng chọn ngày xe đến";
	errArrival="Vui lòng chọn thời gian xe đến";
	errVol="Vui lòng nhập sức chứa của xe";
	errTypeVol="Bạn chỉ có thế nhập kí tự là số";
	errDemand="Vui lòng nhập nhu cầu của xe";
	errTypeDemand="Bạn chỉ có thể nhập số";
	errExistsVehicle="Thông tin xe nhập đã tồn tại";
	
	errProduct="Vui lòng chọn một mặt hàng";
	errQuantity="Vui lòng nhập số lượng mặt hàng";
	errTypeQuantity="Vui lòng chỉ nhập kí tự là số";
	errMinQuantity="Số lượng mặt hàng cần lớn hơn 0";
	errMaxQuantity="Vui lòng không nhập quá số lượng mặt hàng xe cung cấp";
	
	errEditPIV="Bạn không thể cập nhật số lượng mặt hàng ít hơn số lượng đã sử dụng";
	errDelPIV="Bạn không thể xóa mặt hàng đã được sử dụng";
	
	successInsertV="Thông tin xe nhập đã được thêm thành công!";
	successInsertProduct = "Thông tin sản phẩm của xe được thêm thành công!";
	successChangePass = "Mật khẩu của bạn đã được thay đổi";
	successChangeProfile = "Thông tin cá nhân của bạn được cập nhật thành công"
}

function getCurrentLanguage(){
	var lang=$('.lang').attr('data-bind');
	return lang;
}