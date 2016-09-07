package control.vehicle;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.door.DoorDAO;
import dao.vehicle.VehicleDAO;
import entity.door.CrossDockingSystem;
import entity.product.ProductTransfer;
import entity.vehicle.InVehicle;
import entity.vehicle.OutVehicle;
import entity.vehicle.Vehicle;
import services.vehicle.VehicleServices;

@Controller
public class VehicleControl {
	@Autowired
	private DoorDAO doorDAO;
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@RequestMapping(value = {"", "home"}, method = RequestMethod.GET)
	public ModelAndView m_home(){
//		return new ModelAndView("views/index");
		return new ModelAndView("home.def");
	}
	/*
	 * Vehicle
	 * */
	@RequestMapping(value = "vehicle", method = RequestMethod.GET)
	public ModelAndView vehicle(ModelMap mm){
		int currentPage = 1;
		int sizePage = 5;
		int numPage=0;
		int total=vehicleDAO.countVehicle();
		if(total%sizePage == 0) numPage=total/sizePage;
		else numPage=total/sizePage + 1;
		mm.put("numPage", numPage);
		mm.put("pVehicle", vehicleDAO.getPageVehicle(currentPage, sizePage));
		return new ModelAndView("vehicle.def");
	}
	@RequestMapping(value = "getPageVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Vehicle> getPageVehicle(
			@RequestParam(value = "currentPage") int currentPage 
	){
		int sizePage=5;
		ArrayList<Vehicle> listV=vehicleDAO.getPageVehicle(currentPage, sizePage);
		return listV;
	}
	@RequestMapping(value = "insertVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Vehicle> insertVehicle(
		@RequestParam(value = "vehicleCode") String vehicleCode,
		@RequestParam(value = "vehicleType") String vehicleType,
		@RequestParam(value = "vehicleYear") int vehicleYear,
		@RequestParam(value = "vehicleMake") String vehicleMake,
		@RequestParam(value = "vehicleWeight") int vehicleWeight,
		@RequestParam(value = "vehicleTrailerNum") int vehicleTrailerNum,
		@RequestParam(value = "vehicleDes") String vehicleDes
	){
		int currentPage=1, sizePage=5;
		Vehicle v=new Vehicle(vehicleCode, vehicleType, vehicleYear, 
				vehicleMake, vehicleWeight, vehicleTrailerNum, vehicleDes);
		if(vehicleDAO.insertVehicle(v)){
			ArrayList<Vehicle> listV=vehicleDAO.getPageVehicle(currentPage, sizePage);
			return listV;
		}
		return null;
	}
	/*
	 * InVehicle
	 * */
	@RequestMapping(value = "invehicle/data", method = RequestMethod.GET)
	public ModelAndView invehicle(ModelMap mm){	
		int currentPage = 1;
		int sizePage = 5;
		int numPage=0;
		int total=vehicleDAO.countInVehicle();
		if(total%sizePage == 0) numPage=total/sizePage;
		else numPage=total/sizePage + 1;
		mm.put("numPage", numPage);
		mm.put("listV", vehicleDAO.getListVehicle());
		mm.put("pInVehicle", vehicleDAO.getPageInVehicle(currentPage, sizePage));
		return new ModelAndView("invehicle.data.def");
	}
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public ModelAndView invehicle_assign(ModelMap mm){
//		int status = 0;
		int currentPage = 1;
		int sizePage = 5;
		mm.put("listInDoor", doorDAO.getListInDoor());
		mm.put("pInVehicle", vehicleDAO.getPageInVehicle_whereAssignDoor(currentPage, sizePage));
		mm.put("listOutDoor", doorDAO.getListOutDoor());
		mm.put("pOutVehicle", vehicleDAO.getPageOutVehicle_whereAssignDoor(currentPage, sizePage));
//		mm.put("listCost", doorDAO.getListCost());
		mm.put("listPT", vehicleDAO.getProductTransfer());
		return new ModelAndView("assign.def");
	}
	@RequestMapping(value = "invehicle/unload", method = RequestMethod.GET)
	public ModelAndView invehicle_uploadtime(ModelMap mm){
//		int status = 0;
		int currentPage = 1;
		int sizePage = 5;
		int numPage=0;
		int total=vehicleDAO.countInVehicle_whereUnloadStatus();
		if(total%sizePage == 0) numPage=total/sizePage;
		else numPage=total/sizePage + 1;
		mm.put("numPage", numPage);
		mm.put("pInVehicle", vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage));
		return new ModelAndView("invehicle.unload.def");
	}
	@RequestMapping(value = "assignDoorInVehicle", method = RequestMethod.POST)
	@ResponseBody
	public String assignDoorInVehicle(
			@RequestParam(value = "arrIDV[]") Integer[] arrIDV,
			@RequestParam(value = "arrIDD[]") Integer[] arrIDD,
			@RequestParam(value = "cIDoor[]") Integer [] cIDoor
	){
		int status = 1;
		boolean flag = false;
		for(int i=0; i<arrIDV.length; i++){
			if(vehicleDAO.assignDoorInVehicle(arrIDV[i], arrIDD[i]) 
					&& vehicleDAO.upStatusInVehicle(arrIDV[i], status)){
				flag = true;
			}else{
				flag = false;
				break;
			}
		}
		if(flag) return "success";
		return null;
	}
	@RequestMapping(value = "assignDoorOutVehicle", method = RequestMethod.POST)
	@ResponseBody
	public String assignDoorOutVehicle(
			@RequestParam(value = "arrODV[]") Integer[] arrIDV,
			@RequestParam(value = "arrODD[]") Integer[] arrIDD,
			@RequestParam(value = "cODoor[]") Integer [] cODoor
	){
		int status = 1;
		boolean flag = false;
		for(int i=0; i<arrIDV.length; i++){
			if(vehicleDAO.assignDoorOutVehicle(arrIDV[i], arrIDD[i]) 
					&& vehicleDAO.upStatusOutVehicle(arrIDV[i], status)){
				flag = true;
			}else{
				flag = false;
				break;
			}
		}
		if(flag) return "success";
		return null;
	}
	@RequestMapping(value = "getPageInVehicle_byRecords", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> getPageInVehicle_byRecords(
			ModelMap mm,
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "record") int record
	){
		int sizePage=record;
		try {
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle(currentPage, sizePage);
			mm.put("numPage", 5);
			return pInVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "getPageInVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> getPageInVehicle(
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=5;
		try {
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle(currentPage, sizePage);
			return pInVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "getPageInVehicle_whereUnloadStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=5;
		try {
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
			return pInVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "insertInVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> insertInVehicle(
			@RequestParam(value = "idVehicle") int idVehicle,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "arrivalTime") String arrivalTime
	){
		int sizePage=5;
		int currentPage=1;
		int status=0;
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		InVehicle iv=new InVehicle(); 
		iv.setIdVehicle(idVehicle);
		iv.setDate(date);
		iv.setArrivalTime(arrivalTime);
		iv.setStatus(status);
		iv.setcDS(cDS);
		vehicleDAO.insertInVehicle(iv);
		try {
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle(currentPage, sizePage);
			return pInVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "upStartUnloadTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> upStartUnloadTime(
			@RequestParam(value = "idInVehicle") int idInVehicle,
			@RequestParam(value = "currentPage") int currentPage
	){
		int status=2;
		if(vehicleDAO.upStartUnloadTime(idInVehicle) && vehicleDAO.upStatusInVehicle(idInVehicle, status)) {
			int sizePage = 5;
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
			return pInVehicle;
		}
		return null;
	}
	@RequestMapping(value = "upFinishUnloadTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> upFinishUnloadTime(
			@RequestParam(value = "idInVehicle") int idInVehicle,
			@RequestParam(value = "currentPage") int currentPage
	){
//		int status=3;
		if(vehicleDAO.upFinishUnloadTime(idInVehicle)) {
			int sizePage = 5;
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
			return pInVehicle;
		}
		return null;
	}
	@RequestMapping(value = "upStatusFinishUnloadTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> upStatusFinishUnloadTime(
			@RequestParam(value = "idInVehicle") int idInVehicle,
			@RequestParam(value = "currentPage") int currentPage
	){
		int status=3;
		if(vehicleDAO.upStatusInVehicle(idInVehicle, status)) {
			int sizePage = 5;
			ArrayList<InVehicle> pInVehicle=vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
			return pInVehicle;
		}
		return null;
	}
	/* *****************************************************************************************
	 * *****************************************************************************************
	 * *****************************************************************************************
	 * 
	 * OutVehicle
	 * */
	@RequestMapping(value = "getPageOutVehicle_byRecords", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> getPageOutVehicle_byRecords(
			ModelMap mm,
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "record") int record
	){
		int sizePage=record;
		try {
			ArrayList<OutVehicle> pOutVehicle=vehicleDAO.getPageOutVehicle(currentPage, sizePage);
			mm.put("numPage", 5);
			return pOutVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "outvehicle/data", method = RequestMethod.GET)
	public ModelAndView outvehicle(ModelMap mm){	
		int currentPage = 1;
		int sizePage = 5;
		int numPage=0;
		int total=vehicleDAO.countOutVehicle();
		if(total%sizePage == 0) numPage=total/sizePage;
		else numPage=total/sizePage + 1;
		mm.put("numPage", numPage);
		mm.put("listV", vehicleDAO.getListVehicle());
		mm.put("pOutVehicle", vehicleDAO.getPageOutVehicle(currentPage, sizePage));
		return new ModelAndView("outvehicle.data.def");
	}
	@RequestMapping(value = "outvehicle/load", method = RequestMethod.GET)
	public ModelAndView outvehicle_loadtime(ModelMap mm){
//		int status = 0;
		int currentPage = 1;
		int sizePage = 5;
		int numPage=0;
		int total=vehicleDAO.countOutVehicle_whereLoadStatus();
		if(total%sizePage == 0) numPage=total/sizePage;
		else numPage=total/sizePage + 1;
		mm.put("numPage", numPage);
		mm.put("pOutVehicle", vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage));
		return new ModelAndView("outvehicle.load.def");
	}
	@RequestMapping(value = "getPageOutVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> getPageOutVehicle(
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=5;
		try {
			ArrayList<OutVehicle> pOutVehicle=vehicleDAO.getPageOutVehicle(currentPage, sizePage);
			return pOutVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "getPageOutVehicle_whereLoadStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=5;
		try {
			ArrayList<OutVehicle> pOutVehicle=vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
			return pOutVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "insertOutVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> insertOutVehicle(
			@RequestParam(value = "idVehicle") int idVehicle,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "arrivalTime") String arrivalTime
	){
		int sizePage=5;
		int currentPage=1;
		int status=0;
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		OutVehicle ov=new OutVehicle();
		ov.setIdVehicle(idVehicle);
		ov.setDate(date);
		ov.setArrivalTime(arrivalTime);
		ov.setStatus(status);
		ov.setcDS(cDS);
		vehicleDAO.insertOutVehicle(ov);
		try {
			ArrayList<OutVehicle> pOutVehicle=vehicleDAO.getPageOutVehicle(currentPage, sizePage);
			return pOutVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "upStartLoadTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> upStartLoadTime(
			@RequestParam(value = "idOutVehicle") int idOutVehicle,
			@RequestParam(value = "currentPage") int currentPage
	){
		int status=2;
		if(vehicleDAO.upStartLoadTime(idOutVehicle) && vehicleDAO.upStatusOutVehicle(idOutVehicle, status)) {
			int sizePage = 5;
			ArrayList<OutVehicle> pOutVehicle=vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
			return pOutVehicle;
		}
		return null;
	}
	@RequestMapping(value = "upFinishLoadTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> upFinishLoadTime(
			@RequestParam(value = "idOutVehicle") int idOutVehicle,
			@RequestParam(value = "currentPage") int currentPage
	){
//		int status=3;
		if(vehicleDAO.upFinishLoadTime(idOutVehicle)) {
			int sizePage = 5;
			ArrayList<OutVehicle> pVehicle=vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
			return pVehicle;
		}
		return null;
	}
	@RequestMapping(value = "upStatusFinishLoadTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> upStatusFinishLoadTime(
			@RequestParam(value = "idOutVehicle") int idOutVehicle,
			@RequestParam(value = "currentPage") int currentPage
	){
		int status=3;
		if(vehicleDAO.upStatusOutVehicle(idOutVehicle, status)) {
			int sizePage = 5;
			ArrayList<OutVehicle> pOutVehicle=vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
			return pOutVehicle;
		}
		return null;
	}
	/* *****************************************************************************************
	 * *****************************************************************************************
	 * *****************************************************************************************
	 * 
	 * */
	@RequestMapping(value = "mtrstatus", method = RequestMethod.GET)
	public ModelAndView mtrstatus(ModelMap mm){
		int currentPage = 1;
		int sizePage = 5;
		/* **********************************************************************************************
		 * InVehicle
		 * */
		int numPageI0=0, numPageI1=0, numPageI2=0, numPageI3=0;
		int totalI0=vehicleDAO.countInVehicle_byStatus(0);
		int totalI1=vehicleDAO.countInVehicle_byStatus(1);
		int totalI2=vehicleDAO.countInVehicle_byStatus(2);
		int totalI3=vehicleDAO.countInVehicle_byStatus(3);
		if(totalI0%sizePage == 0) { numPageI0 = totalI0/sizePage;
		}else {
			numPageI0 = totalI0/sizePage + 1;
		}if(totalI1%sizePage == 0) { numPageI1 = totalI1/sizePage;
		}else {
			numPageI1 = totalI1/sizePage + 1;
		}if(totalI2%sizePage == 0) { numPageI2 = totalI2/sizePage;
		}else {
			numPageI2 = totalI2/sizePage + 1;
		}if(totalI3%sizePage == 0) { numPageI3 = totalI3/sizePage;
		}else {
			numPageI3 = totalI3/sizePage + 1;
		}
		ArrayList<InVehicle> pageIV0=vehicleDAO.getPageInVehicle_byStatus(0, currentPage, sizePage);
		ArrayList<InVehicle> pageIV1=vehicleDAO.getPageInVehicle_byStatus(1, currentPage, sizePage);
		ArrayList<InVehicle> pageIV2=vehicleDAO.getPageInVehicle_byStatus(2, currentPage, sizePage);
		ArrayList<InVehicle> pageIV3=vehicleDAO.getPageInVehicle_byStatus(3, currentPage, sizePage);
		
		ArrayList<Integer> listNIV=new ArrayList<Integer>();
		listNIV.add(numPageI0); listNIV.add(numPageI1); listNIV.add(numPageI2); listNIV.add(numPageI3);
		ArrayList<ArrayList<?>> listPIV=new ArrayList<ArrayList<?>>();
		listPIV.add(pageIV0); listPIV.add(pageIV1); listPIV.add(pageIV2); listPIV.add(pageIV3);
		
		mm.put("listNIV", listNIV);
		mm.put("listPIV", listPIV);
		/* **********************************************************************************************
		 * OutVehicle
		 * */
		int numPageO0=0, numPageO1=0, numPageO2=0, numPageO3=0;
		int totalO0=vehicleDAO.countOutVehicle_byStatus(0);
		int totalO1=vehicleDAO.countOutVehicle_byStatus(1);
		int totalO2=vehicleDAO.countOutVehicle_byStatus(2);
		int totalO3=vehicleDAO.countOutVehicle_byStatus(3);
		if(totalO0%sizePage == 0) { numPageO0 = totalO0/sizePage;
		}else {
			numPageO0 = totalI0/sizePage + 1;
		}if(totalO1%sizePage == 0) { numPageO1 = totalO1/sizePage;
		}else {
			numPageO1 = totalI1/sizePage + 1;
		}if(totalO2%sizePage == 0) { numPageO2 = totalO2/sizePage;
		}else {
			numPageO2 = totalI2/sizePage + 1;
		}if(totalO3%sizePage == 0) { numPageO3 = totalO3/sizePage;
		}else {
			numPageO3 = totalI3/sizePage + 1;
		}
		ArrayList<OutVehicle> pageOV0=vehicleDAO.getPageOutVehicle_byStatus(0, currentPage, sizePage);
		ArrayList<OutVehicle> pageOV1=vehicleDAO.getPageOutVehicle_byStatus(1, currentPage, sizePage);
		ArrayList<OutVehicle> pageOV2=vehicleDAO.getPageOutVehicle_byStatus(2, currentPage, sizePage);
		ArrayList<OutVehicle> pageOV3=vehicleDAO.getPageOutVehicle_byStatus(3, currentPage, sizePage);
		
		ArrayList<Integer> listNOV=new ArrayList<Integer>();
		listNOV.add(numPageO0); listNOV.add(numPageO1); listNOV.add(numPageO2); listNOV.add(numPageO3);
		ArrayList<ArrayList<?>> listPOV=new ArrayList<ArrayList<?>>();
		listPOV.add(pageOV0); listPOV.add(pageOV1); listPOV.add(pageOV2); listPOV.add(pageOV3);
		
		mm.put("listNOV", listNOV);
		mm.put("listPOV", listPOV);
		return new ModelAndView("mtrstatus.def");
	}
	@RequestMapping(value = "transfer", method = RequestMethod.GET)
	public ModelAndView transfer(ModelMap mm){
		ArrayList<ProductTransfer> listPT=vehicleDAO.getProductTransfer();
		ArrayList<InVehicle> listIV=vehicleDAO.getListInVehicle_byCurDate();
		ArrayList<OutVehicle> listOV=vehicleDAO.getListOutVehicle_byCurDate();
		mm.put("listIV", listIV);
		mm.put("listOV", listOV);
		mm.put("listPT", listPT);
		return new ModelAndView("transfer.def");
	}
	@RequestMapping(value = "insertTransferTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ProductTransfer> insertTransferTime(
			@RequestParam(value = "idInVehicle") int idInVehicle, 
			@RequestParam(value = "idOutVehicle") int idOutVehicle,
			@RequestParam(value = "tranferTime") int transferTime
	){
		InVehicle i=new InVehicle(); i.setIdInVehicle(idInVehicle);
		OutVehicle o=new OutVehicle(); o.setIdOutVehicle(idOutVehicle);
		ProductTransfer pt=new ProductTransfer(i, o, transferTime);
		vehicleDAO.insertTransfer(pt);
		ArrayList<ProductTransfer> listPT=vehicleDAO.getProductTransfer();
		return listPT;
	}
	@RequestMapping(value = "assignDoorAI", method = RequestMethod.POST)
	@ResponseBody
	public String assignDoorAI(
			@RequestParam(value = "json") String json
	){
		VehicleServices api=new VehicleServices();
		String str = api.callAPIAssignDoor(json);
		System.out.println(str);
		return str;
	}
}
