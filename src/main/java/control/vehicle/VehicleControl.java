package control.vehicle;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import entity.door.InDoor;
import entity.door.OutDoor;
import entity.employee.Employee;
import entity.product.ProductTransfer;
import entity.vehicle.InVehicle;
import entity.vehicle.OutVehicle;
import entity.vehicle.Vehicle;
import services.vehicle.VehicleServices;
import tool.MyTool;

@Controller
public class VehicleControl {
	@Autowired
	private DoorDAO doorDAO;
	@Autowired
	private VehicleDAO vehicleDAO;
	
	/*
	 * Vehicle
	 * */
	@RequestMapping(value = "vehicle", method = RequestMethod.GET)
	public ModelAndView vehicle(ModelMap mm, HttpSession session, HttpServletResponse response){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int currentPage = 1;
			int sizePage = 5;
			int numPage=0;
			int total=vehicleDAO.countVehicle();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pVehicle", vehicleDAO.getPageVehicle(currentPage, sizePage));
			return new ModelAndView("vehicle.def");
		}else{
			return new ModelAndView("login.def");
		}
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
	public ModelAndView invehicle(ModelMap mm, HttpSession session){	
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
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
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public ModelAndView invehicle_assign(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
//			int status = 0;
			int currentPage = 1;
			int sizePage = 5;
			int totalI=vehicleDAO.countInVehicle_whereAssignDoor();
			int totalO=vehicleDAO.countOutVehicle_whereAssignDoor();
			int numPageI=totalI/sizePage + 1, numPageO=totalO/sizePage + 1;
			if(totalI%sizePage == 0) numPageI=totalI/sizePage;
			if(totalO%sizePage == 0) numPageO=totalO/sizePage;
			
			mm.put("numPageI", numPageI);
			mm.put("listInDoor", doorDAO.getListInDoorActive());
			mm.put("pInVehicle", vehicleDAO.getPageInVehicle_whereAssignDoor(currentPage, sizePage));
			
			mm.put("numPageO", numPageO);
			mm.put("listOutDoor", doorDAO.getListOutDoorActive());
			mm.put("pOutVehicle", vehicleDAO.getPageOutVehicle_whereAssignDoor(currentPage, sizePage));
			
			mm.put("listCost", doorDAO.getListCost());
			mm.put("listPT", vehicleDAO.getProductTransfer(new MyTool().getDateSystem()));
			return new ModelAndView("assign.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "invehicle/unload", method = RequestMethod.GET)
	public ModelAndView invehicle_uploadtime(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
//			int status = 0;
			int currentPage = 1;
			int sizePage = 5;
			int numPage=0;
			int total=vehicleDAO.countInVehicle_whereUnloadStatus();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pInVehicle", vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage));
			return new ModelAndView("invehicle.unload.def");
		}else{
			return new ModelAndView("login.def");
		}
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
	@RequestMapping(value = "getPageInVehicle_whereAssignDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<?> getPageInVehicle_whereAssignDoor(
				@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage = 5;
		ArrayList<InDoor> listID=doorDAO.getListInDoorActive();
		ArrayList<InVehicle> listIV=vehicleDAO.getPageInVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<ArrayList<?>> page=new ArrayList<ArrayList<?>>();
		page.add(listID); page.add(listIV);
		return page;
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
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "volumn") Double volumn
	){
		int sizePage=5;
		int currentPage=1;
		int status=0;
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		InVehicle iv=new InVehicle(); 
		iv.setIdVehicle(idVehicle);
		iv.setDate(date);
		iv.setArrivalTime(arrivalTime);
		iv.setVolumn(volumn);
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
	public ModelAndView outvehicle(ModelMap mm, HttpSession session){	
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
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
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "outvehicle/load", method = RequestMethod.GET)
	public ModelAndView outvehicle_loadtime(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
//			int status = 0;
			int currentPage = 1;
			int sizePage = 5;
			int numPage=0;
			int total=vehicleDAO.countOutVehicle_whereLoadStatus();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pOutVehicle", vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage));
			return new ModelAndView("outvehicle.load.def");
		}else{
			return new ModelAndView("login.def");
		}
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
	@RequestMapping(value = "getPageOutVehicle_whereAssignDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<?> getPageOutVehicle_whereAssignDoor(
				@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage = 5;
		ArrayList<OutDoor> listOD=doorDAO.getListOutDoorActive();
		ArrayList<OutVehicle> listOV=vehicleDAO.getPageOutVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<ArrayList<?>> page=new ArrayList<ArrayList<?>>();
		page.add(listOD); page.add(listOV);
		return page;
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
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "demand") Double demand
	){
		int sizePage=5;
		int currentPage=1;
		int status=0;
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		OutVehicle ov=new OutVehicle();
		ov.setIdVehicle(idVehicle);
		ov.setDate(date);
		ov.setArrivalTime(arrivalTime);
		ov.setDemand(demand);
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
	public ModelAndView mtrstatus(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int currentPage = 1;
			int sizePage = 15;
			int status=0;
			/* InVehicle
			 * */
			int totalI=vehicleDAO.countInVehicle_byStatus(0);
			int numPageI=totalI/sizePage + 1;;
			if(totalI%sizePage == 0) numPageI = totalI/sizePage;
			ArrayList<InVehicle> pageIV=vehicleDAO.getPageInVehicle_byStatus(status, currentPage, sizePage);
			mm.put("numPageI", numPageI);
			mm.put("pageIV", pageIV);
			/* OutVehicle
			 * */
			int totalO=vehicleDAO.countOutVehicle_byStatus(0);
			int numPageO=totalO/sizePage + 1;;
			if(totalI%sizePage == 0) numPageI = totalI/sizePage;
			ArrayList<OutVehicle> pageOV=vehicleDAO.getPageOutVehicle_byStatus(status, currentPage, sizePage);
			mm.put("numPageO", numPageO);
			mm.put("pageOV", pageOV);
			return new ModelAndView("mtrstatus.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "getPageMtrStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ArrayList<?>> getPageMtrStatus(
			@RequestParam(value = "status") int status
	){
		int currentPage=1; int sizePage=15;
		ArrayList<ArrayList<?>> page=new ArrayList<ArrayList<?>>();
		ArrayList<InVehicle> pageIV=vehicleDAO.getPageInVehicle_byStatus(status, currentPage, sizePage);
		ArrayList<OutVehicle> pageOV=vehicleDAO.getPageOutVehicle_byStatus(status, currentPage, sizePage);
		page.add(pageIV);
		page.add(pageOV);
		return page;
	}
	@RequestMapping(value = "getPageInVehicle_byStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> getPageInVehicle_byStatus(
			@RequestParam(value="currentPage") int currentPage,
			@RequestParam(value="status") int status
	){
		int sizePage = 15;
		ArrayList<InVehicle> pageIV=vehicleDAO.getPageInVehicle_byStatus(status, currentPage, sizePage);
		return pageIV;
	}
	@RequestMapping(value = "getPageOutVehicle_byStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(
			@RequestParam(value="currentPage") int currentPage,
			@RequestParam(value="status") int status
	){
		int sizePage = 15;
		ArrayList<OutVehicle> pageOV=vehicleDAO.getPageOutVehicle_byStatus(status, currentPage, sizePage);
		return pageOV;
	}
	@RequestMapping(value = "transfer", method = RequestMethod.GET)
	public ModelAndView transfer(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			String date=new MyTool().getDateSystem();
			ArrayList<ProductTransfer> listPT=vehicleDAO.getProductTransfer(date);
			ArrayList<InVehicle> listIV=vehicleDAO.getListInVehicle_byCurDate(date);
			ArrayList<OutVehicle> listOV=vehicleDAO.getListOutVehicle_byCurDate(date);
			mm.put("currentDate", date);
			mm.put("listIV", listIV);
			mm.put("listOV", listOV);
			mm.put("listPT", listPT);
			return new ModelAndView("transfer.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "getDataTransfer", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ArrayList<?>> getDataTransfer(
			@RequestParam(value = "date") String date
	){
		ArrayList<ProductTransfer> listPT=vehicleDAO.getProductTransfer(date);
		ArrayList<InVehicle> listIV=vehicleDAO.getListInVehicle_byCurDate(date);
		ArrayList<OutVehicle> listOV=vehicleDAO.getListOutVehicle_byCurDate(date);
		ArrayList<ArrayList<?>> list=new ArrayList<ArrayList<?>>();
		list.add(listIV); 
		list.add(listOV);
		list.add(listPT);
		return list;
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
		ArrayList<ProductTransfer> listPT=vehicleDAO.getProductTransfer(new MyTool().getDateSystem());
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
