package com.cds.control.vehicle;

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

import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.employee.Employee;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.entity.vehicle.Vehicle;
import com.cds.service.door.DoorService;
import com.cds.service.vehicle.VehicleService;
import com.cds.services.vehicle.VehicleServices;
import com.cds.tool.MyTool;

@Controller
public class VehicleControl {
	@Autowired
	private DoorService doorService;
	@Autowired
	private VehicleService vehicleService;
	
	private MyTool myTool=new MyTool();
	/*
	 * Vehicle
	 * */
	@RequestMapping(value = "vehicle", method = RequestMethod.GET)
	public ModelAndView vehicle(ModelMap mm, HttpSession session, HttpServletResponse response){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			int currentPage = 1;
			int sizePage = 10;
			int numPage=0;
//			int total=baseDAO.countAll(Vehicle_.TABLE);
			int total=0;
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pVehicle", vehicleService.getPageVehicle(currentPage, sizePage));
			return new ModelAndView("vehicle.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "getPageVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Vehicle> getPageVehicle(
			@RequestParam(value = "currentPage") int currentPage 
	){
		int sizePage=5;
		ArrayList<Vehicle> listV=vehicleService.getPageVehicle(currentPage, sizePage);
		return listV;
	}
	@RequestMapping(value = "insertVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Vehicle> insertVehicle(
		@RequestParam(value = "code") String code,
		@RequestParam(value = "type") String type, 
		@RequestParam(value = "company") String company,
		@RequestParam(value = "description") String description
	){
		int currentPage=1, sizePage=5;
		Vehicle v=new Vehicle();
		v.setVehicleCode(code);
		v.setType(type);
		v.setCompany(company);
		v.setDescription(description);
		if(vehicleService.checkInsertVehicle(v)){
			vehicleService.insertVehicle(v);
			ArrayList<Vehicle> listV=vehicleService.getPageVehicle(currentPage, sizePage);
			return listV;
		}
		return null;
	}
	/*
	 * InVehicle
	 * */
	@RequestMapping(value = "invehicle", method = RequestMethod.GET)
	public ModelAndView invehicle(ModelMap mm, HttpSession session){	
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			int currentPage = 1;
			int sizePage = 15;
			int numPage=0;
			int total=vehicleService.countInVehicle();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("listV", vehicleService.getListVehicle());
			mm.put("pInVehicle", vehicleService.getPageInVehicle(currentPage, sizePage));
			return new ModelAndView("invehicle.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public ModelAndView invehicle_assign(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
//			int status = 0;
			int currentPage = 1;
			int sizePage = 5;
			int totalI=vehicleService.countInVehicle_whereAssignDoor();
			int totalO=vehicleService.countOutVehicle_whereAssignDoor();
			int numPageI=totalI/sizePage + 1, numPageO=totalO/sizePage + 1;
			if(totalI%sizePage == 0) numPageI=totalI/sizePage;
			if(totalO%sizePage == 0) numPageO=totalO/sizePage;
			
			mm.put("numPageI", numPageI);
			mm.put("listInDoor", doorService.getListInDoorActive());
			mm.put("pInVehicle", vehicleService.getPageInVehicle_whereAssignDoor(currentPage, sizePage));
			
			mm.put("numPageO", numPageO);
			mm.put("listOutDoor", doorService.getListOutDoorActive());
			mm.put("pOutVehicle", vehicleService.getPageOutVehicle_whereAssignDoor(currentPage, sizePage));
			
			mm.put("listCost", doorService.getListCost());
			mm.put("listPT", vehicleService.getProductTransfer(new MyTool().getDateSystem()));
			return new ModelAndView("assign.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "unload", method = RequestMethod.GET)
	public ModelAndView invehicle_uploadtime(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
//			int status = 0;
			int currentPage = 1;
			int sizePage = 5;
			int numPage=0;
			int total=vehicleService.countInVehicle_whereUnloadStatus();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pInVehicle", vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage));
			return new ModelAndView("invehicle.unload.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
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
			if(vehicleService.assignDoorInVehicle(arrIDV[i], arrIDD[i]) 
					&& vehicleService.upStatusInVehicle(arrIDV[i], status)){
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
			if(vehicleService.assignDoorOutVehicle(arrIDV[i], arrIDD[i]) 
					&& vehicleService.upStatusOutVehicle(arrIDV[i], status)){
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
		ArrayList<InDoor> listID=doorService.getListInDoorActive();
		ArrayList<InVehicle> listIV=vehicleService.getPageInVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<ArrayList<?>> page=new ArrayList<ArrayList<?>>();
		page.add(listID); page.add(listIV);
		return page;
	}
	@RequestMapping(value = "getPageInVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InVehicle> getPageInVehicle(
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=5;
		try {
			ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle(currentPage, sizePage);
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
			ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
			return pInVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "insertInVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Object insertInVehicle(
			@RequestParam(value = "idVehicle") Long idVehicle,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "volumn") Double volumn,
			@RequestParam(value = "record") int record
	){
		int sizePage=record;
		int currentPage=1;
		int status=0;
		ArrayList<Object> list=new ArrayList<Object>();
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		InVehicle iv=new InVehicle(); 
		iv.setIdVehicle(idVehicle);
		iv.setDate(date);
		iv.setArrivalTime(arrivalTime);
		iv.setVolumn(volumn);
		iv.setStatus(status);
		iv.setcDS(cDS);
		try {
			if(vehicleService.checkInsertIV(iv)){
				int count=vehicleService.countInVehicle();
				int numPage=myTool.getNumPage(count, sizePage);
				vehicleService.insertInVehicle(iv);
				ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle(currentPage, sizePage);
				list.add(numPage);
				list.add(pInVehicle);				
				return list;
			}
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
		if(vehicleService.upStartUnloadTime(idInVehicle) && vehicleService.upStatusInVehicle(idInVehicle, status)) {
			int sizePage = 5;
			ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
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
		if(vehicleService.upFinishUnloadTime(idInVehicle)) {
			int sizePage = 5;
			ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
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
		if(vehicleService.upStatusInVehicle(idInVehicle, status)) {
			int sizePage = 5;
			ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
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
			ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicle(currentPage, sizePage);
			mm.put("numPage", 5);
			return pOutVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "outvehicle", method = RequestMethod.GET)
	public ModelAndView outvehicle(ModelMap mm, HttpSession session){	
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			int currentPage = 1;
			int sizePage = 15;
			int numPage=0;
			int total=vehicleService.countOutVehicle();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("listV", vehicleService.getListVehicle());
			mm.put("pOutVehicle", vehicleService.getPageOutVehicle(currentPage, sizePage));
			return new ModelAndView("outvehicle.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "load", method = RequestMethod.GET)
	public ModelAndView outvehicle_loadtime(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
//			int status = 0;
			int currentPage = 1;
			int sizePage = 5;
			int numPage=0;
			int total=vehicleService.countOutVehicle_whereLoadStatus();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pOutVehicle", vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage));
			return new ModelAndView("outvehicle.load.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "getPageOutVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> getPageOutVehicle(
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=5;
		try {
			ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicle(currentPage, sizePage);
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
		ArrayList<OutDoor> listOD=doorService.getListOutDoorActive();
		ArrayList<OutVehicle> listOV=vehicleService.getPageOutVehicle_whereAssignDoor(currentPage, sizePage);
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
			ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
			return pOutVehicle;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "insertOutVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Object insertOutVehicle(
			@RequestParam(value = "idVehicle") Long idVehicle,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "demand") Double demand,
			@RequestParam(value = "record") int record
	){
		ArrayList<Object> list=new ArrayList<Object>();
		int sizePage=record;
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
		try {
			if(vehicleService.checkInsertOV(ov)){
				vehicleService.insertOutVehicle(ov);
				int count = vehicleService.countOutVehicle();
				int numPage = myTool.getNumPage(count, sizePage);
				list.add(numPage);
				ArrayList<OutVehicle> pOutVehicle=
						vehicleService.getPageOutVehicle(currentPage, sizePage);
				list.add(pOutVehicle);
				return list;
			}
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
		if(vehicleService.upStartLoadTime(idOutVehicle) && vehicleService.upStatusOutVehicle(idOutVehicle, status)) {
			int sizePage = 5;
			ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
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
		if(vehicleService.upFinishLoadTime(idOutVehicle)) {
			int sizePage = 5;
			ArrayList<OutVehicle> pVehicle=vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
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
		if(vehicleService.upStatusOutVehicle(idOutVehicle, status)) {
			int sizePage = 5;
			ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
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
//		if(em != null){
			int currentPage = 1;
			int sizePage = 15;
			int status=0;
			/* InVehicle
			 * */
			int totalI=vehicleService.countInVehicle_byStatus(0);
			int numPageI=totalI/sizePage + 1;;
			if(totalI%sizePage == 0) numPageI = totalI/sizePage;
			ArrayList<InVehicle> pageIV=vehicleService.getPageInVehicle_byStatus(status, currentPage, sizePage);
			mm.put("numPageI", numPageI);
			mm.put("pageIV", pageIV);
			/* OutVehicle
			 * */
			int totalO=vehicleService.countOutVehicle_byStatus(0);
			int numPageO=totalO/sizePage + 1;;
			if(totalI%sizePage == 0) numPageI = totalI/sizePage;
			ArrayList<OutVehicle> pageOV=vehicleService.getPageOutVehicle_byStatus(status, currentPage, sizePage);
			mm.put("numPageO", numPageO);
			mm.put("pageOV", pageOV);
			return new ModelAndView("mtrstatus.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "getPageMtrStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ArrayList<?>> getPageMtrStatus(
			@RequestParam(value = "status") int status
	){
		int currentPage=1; int sizePage=15;
		ArrayList<ArrayList<?>> page=new ArrayList<ArrayList<?>>();
		ArrayList<InVehicle> pageIV=vehicleService.getPageInVehicle_byStatus(status, currentPage, sizePage);
		ArrayList<OutVehicle> pageOV=vehicleService.getPageOutVehicle_byStatus(status, currentPage, sizePage);
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
		ArrayList<InVehicle> pageIV=vehicleService.getPageInVehicle_byStatus(status, currentPage, sizePage);
		return pageIV;
	}
	@RequestMapping(value = "getPageOutVehicle_byStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(
			@RequestParam(value="currentPage") int currentPage,
			@RequestParam(value="status") int status
	){
		int sizePage = 15;
		ArrayList<OutVehicle> pageOV=vehicleService.getPageOutVehicle_byStatus(status, currentPage, sizePage);
		return pageOV;
	}
	@RequestMapping(value = "transfer", method = RequestMethod.GET)
	public ModelAndView transfer(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			String date=new MyTool().getDateSystem();
			ArrayList<ProductTransfer> listPT=vehicleService.getProductTransfer(date);
			ArrayList<InVehicle> listIV=vehicleService.getListInVehicle_byCurDate(date);
			ArrayList<OutVehicle> listOV=vehicleService.getListOutVehicle_byCurDate(date);
			mm.put("currentDate", date);
			mm.put("listIV", listIV);
			mm.put("listOV", listOV);
			mm.put("listPT", listPT);
			return new ModelAndView("transfer.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "getDataTransfer", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ArrayList<?>> getDataTransfer(
			@RequestParam(value = "date") String date
	){
		ArrayList<ProductTransfer> listPT=vehicleService.getProductTransfer(date);
		ArrayList<InVehicle> listIV=vehicleService.getListInVehicle_byCurDate(date);
		ArrayList<OutVehicle> listOV=vehicleService.getListOutVehicle_byCurDate(date);
		ArrayList<ArrayList<?>> list=new ArrayList<ArrayList<?>>();
		list.add(listIV); 
		list.add(listOV);
		list.add(listPT);
		return list;
	}
	@RequestMapping(value = "insertTransferTime", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ProductTransfer> insertTransferTime(
			@RequestParam(value = "idInVehicle") Long idInVehicle, 
			@RequestParam(value = "idOutVehicle") Long idOutVehicle,
			@RequestParam(value = "tranferTime") int transferTime
	){
		InVehicle i=new InVehicle(); i.setIdInVehicle(idInVehicle);
		OutVehicle o=new OutVehicle(); o.setIdOutVehicle(idOutVehicle);
		ProductTransfer pt=new ProductTransfer(i, o, transferTime);
		vehicleService.insertTransfer(pt);
		ArrayList<ProductTransfer> listPT=vehicleService.getProductTransfer(new MyTool().getDateSystem());
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
	@RequestMapping(value = "rpvehicle", method = RequestMethod.GET)
	public ModelAndView rpvehicle(ModelMap mm){
		int currentPage=1, sizePage=10;
		String strartDate=myTool.getStartDateSystem(), endDate=myTool.getEndDateSystem();
		int totalI=vehicleService.countInVehicle_byDateAndCodeVehicle(strartDate, endDate, "");
		mm.put("startDate", strartDate);
		mm.put("endDate", endDate);
		mm.put("numPageI", myTool.getNumPage(totalI, sizePage));
		mm.put("pageIV", vehicleService.getPageInVehicle_byDateAndCodeVehicle
							(strartDate, endDate, "", currentPage, sizePage));
		int totalO=vehicleService.countOutVehicle_byDateAndCodeVehicle(strartDate, endDate, "");
		mm.put("numPageO", myTool.getNumPage(totalO, sizePage));
		mm.put("pageOV", vehicleService.getPageOutVehicle_byDateAndCodeVehicle
							(strartDate, endDate, "", currentPage, sizePage));
		return new ModelAndView("rpvehicle.def");
	}
	@RequestMapping(value = "getPageVehiclebyInforSearch", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ArrayList<?>> getPageInVehicleReport(
			@RequestParam(value = "vehicleCode") String vehicleCode, 
			@RequestParam(value = "startDate") String startDate, 
			@RequestParam(value = "endDate") String endDate
	){
		int currentPage=1, sizePage=10;
		ArrayList<ArrayList<?>> pageV=new ArrayList<ArrayList<?>>();
		ArrayList<InVehicle> pageIV=vehicleService.getPageInVehicle_byDateAndCodeVehicle
				(startDate, endDate, vehicleCode, currentPage, sizePage);
		ArrayList<OutVehicle> pageOV=vehicleService.getPageOutVehicle_byDateAndCodeVehicle
				(startDate, endDate, vehicleCode, currentPage, sizePage);
		pageV.add(pageIV);
		pageV.add(pageOV);
		return pageV;
	}
	@RequestMapping(value = "getPageInVehicleCurrentDay_byKeySearch", method = RequestMethod.POST)
	@ResponseBody
	public Object getPageInVehicleCurrentDay_byKeySearch(
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "keySearch") String keySearch, 
			@RequestParam(value = "record") int record
	){
		ArrayList<Object> list=new ArrayList<Object>();
		int count = vehicleService.countAllInVehicleCurDate_byKeySearch(keySearch);
		int numPage=myTool.getNumPage(count, record);
		list.add(numPage);
		ArrayList<InVehicle> listIV=vehicleService.
				getPageInVehicleCurDate_byKeySearch(keySearch, currentPage, record);
		list.add(listIV);
		return list;
	}
	@RequestMapping(value = "getPageOutVehicleCurrentDay_byKeySearch", method = RequestMethod.POST)
	@ResponseBody
	public Object getPageOutVehicleCurrentDay_byKeySearch(
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "keySearch") String keySearch, 
			@RequestParam(value = "record") int record
	){
		ArrayList<Object> list=new ArrayList<Object>();
		int count = vehicleService.countAllOutVehicleCurDate_byKeySearch(keySearch);
		int numPage=myTool.getNumPage(count, record);
		list.add(numPage);
		ArrayList<OutVehicle> listOV=vehicleService.
				getPageOutVehicleCurDate_byKeySearch(keySearch, currentPage, record);
		list.add(listOV);
		return list;
	}
}
