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

import com.cds.entity.Pager;
import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.employee.Employee;
import com.cds.entity.product.Product;
import com.cds.entity.product.ProductInVehicle;
import com.cds.entity.product.ProductOutVehicle;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.product.ProductTransform;
import com.cds.entity.product.Unit;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.entity.vehicle.Vehicle;
import com.cds.field.Constants;
import com.cds.service.door.DoorService;
import com.cds.service.vehicle.ProductService;
import com.cds.service.vehicle.VehicleService;
import com.cds.services.vehicle.VehicleServices;
import com.cds.utils.DateUtils;

@Controller
public class VehicleControl {
	@Autowired
	private DoorService doorService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ProductService pService;
	
	private DateUtils myTool=new DateUtils();
	/*
	 * Vehicle
	 * */
	@RequestMapping(value = "vehicleManager", method = RequestMethod.GET)
	public ModelAndView vehicle(ModelMap mm, HttpSession session, HttpServletResponse response){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int sizePage = Constants.SIZE_PAGE;
			int currentPage = Constants.START_PAGE;
			int total=vehicleService.countAllVehicle();
			Pager pager = new Pager();
			pager.initPage(sizePage, total, currentPage);
			mm.put("pager", pager);
			mm.put("pVehicle", vehicleService.getPageVehicle(currentPage, sizePage));
			return new ModelAndView("vehicleManager.def");
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
	@RequestMapping(value = "invManager", method = RequestMethod.GET)
	public ModelAndView invehicle(ModelMap mm, HttpSession session){	
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int currentPage = 1;
			int sizePage = 15;
			String key="";
			int total=vehicleService.countAllInVehicleCurDate_byKeySearch(key);
			String date=new DateUtils().getDateSystem();
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			mm.put("pager", pager);
			mm.put("listV", vehicleService.getListVehicle());
			mm.put("pInVehicle", vehicleService.getPageInVehicleCurDate_byKeySearch(key, currentPage, sizePage));
			mm.put("listP", pService.getListProduct());
			mm.put("listU", pService.getListUnit());
			mm.put("listOV", vehicleService.getListOutVehicle_byCurDate(date));
			return new ModelAndView("invManager.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "getTotalCostAssignOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public double getTotalCostAssignOutDoor(
			@RequestParam(value = "idOutVehicle") Long idOutVehicle,
			@RequestParam(value = "idOutDoor") Long idOutDoor
	){
		double ttCostNotV=doorService.getTotalCostNotOutVehicle(idOutVehicle);
		double ttCostByV=doorService.getCostInVehicleAssign(idOutVehicle, idOutDoor);
		double ttCost = ttCostNotV + ttCostByV;
		return ttCost;
	}
	@RequestMapping(value = "unload", method = RequestMethod.GET)
	public ModelAndView invehicle_uploadtime(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
//			int status = 0;
			int currentPage = Constants.START_PAGE;
			int sizePage = Constants.SIZE_PAGE;
			int total=vehicleService.countInVehicle_whereUnloadStatus();
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			
			mm.put("pager", pager);
			mm.put("pInVehicle", vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage));
			return new ModelAndView("invehicle.unload.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "rpvyear", method = RequestMethod.GET)
	public ModelAndView reportInVehicle(ModelMap mm){
		int currentPage = Constants.START_PAGE;
		int sizePage = Constants.SIZE_PAGE;
		String key = Constants.EMPTY;
		String date = Constants.EMPTY;
		String company = Constants.EMPTY;
		
		ArrayList<ProductInVehicle> pagePIV=pService.getPagePIVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		int total=pService.countAllPIVCurMonth_byKeyAndDate(key, date, company);
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		mm.put("pagerI", pager);
		mm.put("pagePIV", pagePIV);
		
		ArrayList<ProductOutVehicle> pagePOV=pService.getPagePOVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		total=pService.countAllPOVCurMonth_byKeyAndDate(key, date, company);
		pager = new Pager();
		pager.initPage(sizePage, total, currentPage);
		mm.put("pagerO", pager);
		mm.put("pagePOV", pagePOV);
		return new ModelAndView("rpvyear.def");
	}
	@RequestMapping(value = "rpvmonth", method = RequestMethod.GET)
	public ModelAndView reportOutVehicle(ModelMap mm){
		int currentPage = Constants.START_PAGE;
		int sizePage = Constants.SIZE_PAGE;
		String key = Constants.EMPTY;
		String date = Constants.EMPTY;
		String company = Constants.EMPTY;
		
		ArrayList<ProductInVehicle> pagePIV=pService.getPagePIVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		int total=pService.countAllPIVCurMonth_byKeyAndDate(key, date, company);
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		mm.put("pagerI", pager);
		mm.put("pagePIV", pagePIV);
		
		ArrayList<ProductOutVehicle> pagePOV=pService.getPagePOVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		total=pService.countAllPOVCurMonth_byKeyAndDate(key, date, company);
		pager = new Pager();
		pager.initPage(sizePage, total, currentPage);
		mm.put("pagerO", pager);
		mm.put("pagePOV", pagePOV);
		return new ModelAndView("rpvmonth.def");
	}
	
	@RequestMapping(value = "getPageInVehicle_whereAssignDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<?> getPageInVehicle_whereAssignDoor(
				@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage = Constants.SIZE_PAGE;
//		int sizePage = 5;
		int total=vehicleService.countInVehicle_whereAssignDoor();
		ArrayList<InDoor> listID=doorService.getListInDoorActive();
		ArrayList<InVehicle> listIV=vehicleService.getPageInVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<Object> list=new ArrayList<Object>();
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		
		list.add(pager);
		list.add(listID); 
		list.add(listIV);
		return list;
	}
	@RequestMapping(value = "getPageInVehicle_whereUnloadStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Object> getPageInVehicle_whereUnloadStatus(
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "record") int record
	){
		int sizePage=record;
		int total=vehicleService.countInVehicle_whereUnloadStatus();
		ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		
		ArrayList<Object> list=new ArrayList<Object>();
		list.add(pager);
		list.add(pInVehicle);
		return list;
	}
	@RequestMapping(value = "insertInVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Object insertInVehicle(
			@RequestParam(value = "idVehicle") Long idVehicle,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "record") int record
	){
		int sizePage=record;
		int currentPage=1;
		int status=0;
		String key="";
		ArrayList<Object> list=new ArrayList<Object>();
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		InVehicle iv=new InVehicle(); 
		iv.setIdVehicle(idVehicle);
		iv.setDate(date);
		iv.setArrivalTime(arrivalTime);
		iv.setStatus(status);
		iv.setcDS(cDS);
		try {
			if(vehicleService.checkInsertIV(iv)){
				int count=vehicleService.countAllInVehicleCurDate_byKeySearch(key);
				vehicleService.insertInVehicle(iv);
				ArrayList<InVehicle> pInVehicle=vehicleService.getPageInVehicleCurDate_byKeySearch(key, currentPage, sizePage);
				int total = count;
				int size = record;
				Pager pager = new Pager();
				pager.initPage(size, total, currentPage);
				list.add(pager);
				list.add(pInVehicle);				
				return list;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "delInVehicleCurrentDay", method = RequestMethod.POST)
	@ResponseBody
	public Object delInVehicleCurrentDay(
			@RequestParam(value = "idVehicle") long idVehicle, 
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "keySearch") String keySearch,
			@RequestParam(value = "record") int record
	){
		ArrayList<Object> list=new ArrayList<Object>();
		vehicleService.delInVehicle_byIdVehicle(idVehicle);
		int count = vehicleService.countAllInVehicleCurDate_byKeySearch(keySearch);
		int total = count;
		int size = record;
		Pager pager = new Pager();
		pager.initPage(size, total, currentPage);
		list.add(pager);
		ArrayList<InVehicle> listIV=vehicleService.getPageInVehicleCurDate_byKeySearch(keySearch, currentPage, record);
		list.add(listIV);
		return list;
	}
	@RequestMapping(value = "upDataUnloadTime", method = RequestMethod.POST)
	@ResponseBody
	public boolean upDataUnloadTime(
			@RequestParam(value = "idInVehicle") long idInVehicle,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime
			
	){
		return vehicleService.upDataUnloadTime(idInVehicle, startTime, endTime);
	}
	@RequestMapping(value = "upFinishUnloadTime", method = RequestMethod.POST)
	@ResponseBody
	public boolean upFinishUnloadTime(
			@RequestParam(value = "idInVehicle") long idInVehicle
	){
		return vehicleService.upFinishUnloadTime(idInVehicle);
	}
	/* *****************************************************************************************
	 * *****************************************************************************************
	 * *****************************************************************************************
	 * 
	 * OutVehicle
	 * */
	@RequestMapping(value = "outvManager", method = RequestMethod.GET)
	public ModelAndView outvehicle(ModelMap mm, HttpSession session){	
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			String key="";
			int currentPage = 1;
			int sizePage = 15;
			int total = vehicleService.countAllOutVehicleCurDate_byKeySearch(key);
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			
			mm.put("pager", pager);
			mm.put("listV", vehicleService.getListVehicle());
			mm.put("pOutVehicle", vehicleService.getPageOutVehicleCurDate_byKeySearch(key, currentPage, sizePage));
			return new ModelAndView("outvManager.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "load", method = RequestMethod.GET)
	public ModelAndView outvehicle_loadtime(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
//			int status = 0;
			int currentPage = Constants.START_PAGE;
			int sizePage = 15;
			int total=vehicleService.countOutVehicle_whereLoadStatus();
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			
			mm.put("pager", pager);
			mm.put("pOutVehicle", vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage));
			return new ModelAndView("outvehicle.load.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "getPageOutVehicle_whereAssignDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<?> getPageOutVehicle_whereAssignDoor(
				@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage = Constants.SIZE_PAGE;
//		int sizePage = 5;
		int total=vehicleService.countOutVehicle_whereAssignDoor();
		ArrayList<OutDoor> listOD=doorService.getListOutDoorActive();
		ArrayList<OutVehicle> listOV=vehicleService.getPageOutVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<Object> list=new ArrayList<Object>();
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		
		list.add(pager);
		list.add(listOD); 
		list.add(listOV);
		return list;
	}
	@RequestMapping(value = "getPageOutVehicle_whereLoadStatus", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Object> getPageOutVehicle_whereLoadStatus(
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "record") int record
	){
		int sizePage=record;
		int total=vehicleService.countOutVehicle_whereLoadStatus();
		try {
			ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			
			ArrayList<Object> list=new ArrayList<Object>();
			list.add(pager);
			list.add(pOutVehicle);
			return list;
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
			@RequestParam(value = "record") int record
	){
		ArrayList<Object> list=new ArrayList<Object>();
		int sizePage=record;
		int currentPage=1;
		int status=0;
		String key="";
		CrossDockingSystem cDS = new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);		
		OutVehicle ov=new OutVehicle();
		ov.setIdVehicle(idVehicle);
		ov.setDate(date);
		ov.setArrivalTime(arrivalTime);
		ov.setStatus(status);
		ov.setcDS(cDS);
		try {
			if(vehicleService.checkInsertOV(ov)){
				vehicleService.insertOutVehicle(ov);
				int count = vehicleService.countAllOutVehicleCurDate_byKeySearch(key);
				int total = count;
				int size = record;
				Pager pager = new Pager();
				pager.initPage(size, total, currentPage);
				ArrayList<OutVehicle> pOutVehicle=vehicleService.getPageOutVehicleCurDate_byKeySearch(key, currentPage, sizePage);
				
				list.add(pager);
				list.add(pOutVehicle);
				return list;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "delOutVehicleCurrentDay", method = RequestMethod.POST)
	@ResponseBody
	public Object delOutVehicleCurrentDay(
			@RequestParam(value = "idVehicle") long idVehicle, 
			@RequestParam(value = "currentPage") int currentPage,
			@RequestParam(value = "keySearch") String keySearch,
			@RequestParam(value = "record") int record
	){
		ArrayList<Object> list=new ArrayList<Object>();
		vehicleService.delOutVehicle_byIdVehicle(idVehicle);
		int count = vehicleService.countAllOutVehicleCurDate_byKeySearch(keySearch);
		int total = count;
		int size = record;
		Pager pager = new Pager();
		pager.initPage(size, total, currentPage);
		ArrayList<OutVehicle> listOV=vehicleService.getPageOutVehicleCurDate_byKeySearch(keySearch, currentPage, record);
		
		list.add(pager);
		list.add(listOV);
		return list;
	}
	@RequestMapping(value = "upDataLoadTime", method = RequestMethod.POST)
	@ResponseBody
	public boolean upStartLoadTime(
			@RequestParam(value = "idOutVehicle") long idOutVehicle,
			@RequestParam(value = "startTime") String sTime,
			@RequestParam(value = "endTime") String eTime
	){
		return vehicleService.upDataLoadTime(idOutVehicle, sTime, eTime);
	}
	@RequestMapping(value = "upFinishLoadTime", method = RequestMethod.POST)
	@ResponseBody
	public boolean upFinishLoadTime(
			@RequestParam(value = "idOutVehicle") long idOutVehicle
	){
		return vehicleService.upFinishLoadTime(idOutVehicle);
	}
	/* *****************************************************************************************
	 * *****************************************************************************************
	 * *****************************************************************************************
	 * 
	 * */
	@RequestMapping(value = "mtrinv", method = RequestMethod.GET)
	public ModelAndView mtrInVehicle(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int currentPage = 1;
			int sizePage = Constants.SIZE_PAGE;
			String key=Constants.EMPTY;
			String status=Constants.EMPTY;
			String date=new DateUtils().getDateSystem();
			int total=vehicleService.countInVehicle_byStatusAndKey(key, status);
			ArrayList<InVehicle> pageV=vehicleService.getPageInVehicle_byStatusAndKey(key, status, currentPage, sizePage);
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			
			mm.put("pager", pager);
			mm.put("pageV", pageV);
			mm.put("listP", pService.getListProduct());
			mm.put("listU", pService.getListUnit());
			mm.put("listOV", vehicleService.getListOutVehicle_byCurDate(date));
			return new ModelAndView("mtrinv.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "displayProductIV", method = RequestMethod.POST)
	@ResponseBody
	public Object displayProductIV(@RequestParam(value = "idVehicle") Long idVehicle){
		ArrayList<Object> list=new ArrayList<Object>();
		ArrayList<ProductInVehicle> listPIV=pService.getListProductIV_byVehicle(idVehicle);
		ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idVehicle);
		list.add(listPIV);
		list.add(listPT);
		return list;
	}
	@RequestMapping(value = "displayProductOV", method = RequestMethod.POST)
	@ResponseBody
	public Object displayProductOV(@RequestParam(value = "idVehicle") Long idVehicle){
		ArrayList<Object> list=new ArrayList<Object>();
		ArrayList<ProductOutVehicle> listPOV=pService.getListProductOV_byVehicle(idVehicle);
		ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byOutVehicle(idVehicle);
		list.add(listPOV);
		list.add(listPT);
		return list;
	}
	@RequestMapping(value = "insertProductIV", method = RequestMethod.POST)
	@ResponseBody
	public Object insertProductIV(
				@RequestParam(value = "idVehicle") Long idVehicle,
				@RequestParam(value = "idProduct") Long idProduct,
				@RequestParam(value = "quantity") int quantity,
				@RequestParam(value = "idUnit") Long idUnit
	){
		try {
			InVehicle iv = new InVehicle(); iv.setIdInVehicle(idVehicle);
			Product p=new Product(); p.setId(idProduct);
			Unit u=new Unit(); u.setId(idUnit);
			ProductInVehicle piv=new ProductInVehicle();
			piv.setIv(iv);
			piv.setP(p);
			piv.setU(u);
			piv.setQuantity(quantity);
			ProductInVehicle piv1=pService.checkInsertProductIV(idProduct, idVehicle);
			if(piv1 == null){
				pService.insertProductIV(piv);
			}else{
				quantity += piv1.getQuantity();
				pService.editProductIV(piv1.getId(), quantity);
			}
			ArrayList<Object> list=new ArrayList<Object>();
			ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idVehicle);
			ArrayList<ProductInVehicle> listPIV=pService.getListProductIV_byVehicle(idVehicle);
			list.add(listPIV);
			list.add(listPT);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "insertProductOV", method = RequestMethod.POST)
	@ResponseBody
	public Object insertProductOV(
				@RequestParam(value = "idInVehicle") Long idInVehicle,
				@RequestParam(value = "idOutVehicle") Long idOutVehicle,
				@RequestParam(value = "idProduct") Long idProduct,
				@RequestParam(value = "quantity") int quantity,
				@RequestParam(value = "idUnit") Long idUnit
	){
		ProductOutVehicle pov=new ProductOutVehicle();
		OutVehicle ov=new OutVehicle(); ov.setIdOutVehicle(idOutVehicle);
		Product p=new Product(); p.setId(idProduct);
		Unit u=new Unit(); u.setId(idUnit);
		pov.setOv(ov);
		pov.setP(p);
		pov.setU(u);
		pov.setQuantity(quantity);
		ProductInVehicle piv=new ProductInVehicle();
		InVehicle iv=new InVehicle(); iv.setIdInVehicle(idInVehicle);
		piv.setIv(iv);
		ProductTransform pt=new ProductTransform();
		pt.setPiv(piv);
		pt.setPov(pov);
		pService.insertProductTransform(pt);
		ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idInVehicle);
		return listPT;
	}
	@RequestMapping(value = "editProductIV", method = RequestMethod.POST)
	@ResponseBody
	public Object editProductIV(
			@RequestParam(value = "idPIV") Long idPIV,
			@RequestParam(value = "quantity") int quantity,
			@RequestParam(value = "idVehicle") Long idVehicle
	){
		try {
			int uQuantity = pService.getQuantityTransform_byInVehicle(idVehicle);
			if(quantity > uQuantity){
				pService.editProductIV(idPIV, quantity);
				ArrayList<Object> list=new ArrayList<Object>();
				ArrayList<ProductInVehicle> listPIV=pService.getListProductIV_byVehicle(idVehicle);
				ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idVehicle);
				list.add(listPIV);
				list.add(listPT);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "editProductOV", method = RequestMethod.POST)
	@ResponseBody
	public Object editProductOV(
			@RequestParam(value = "idProduct") Long idProduct,
			@RequestParam(value = "idOutVehicle") Long idOutVehicle, 
			@RequestParam(value = "quantity") int quantity,
			@RequestParam(value = "idVehicle") Long idVehicle
	){
		try {
			int totalQ = vehicleService.getVolumnInVehicle(idVehicle);
			if(quantity < totalQ){
				pService.editProductOV(idProduct, idVehicle, idOutVehicle, quantity);
				ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idVehicle);
				return listPT;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "delProductIV", method = RequestMethod.POST)
	@ResponseBody
	public Object delProductIV(
			@RequestParam(value = "idPIV") Long idPIV,
			@RequestParam(value = "idVehicle") Long idVehicle
	){
		try {
			if(pService.checkDeleteProductIV(idVehicle)){
				pService.delProductIV(idPIV);
				ArrayList<Object> list=new ArrayList<Object>();
				ArrayList<ProductInVehicle> listPIV=pService.getListProductIV_byVehicle(idVehicle);
				ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idVehicle);
				list.add(listPIV);
				list.add(listPT);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "delProductOV", method = RequestMethod.POST)
	@ResponseBody
	public Object delProductOV(
			@RequestParam(value = "idProduct") Long idProduct,
			@RequestParam(value = "idOutVehicle") Long idOutVehicle, 
			@RequestParam(value = "idVehicle") Long idVehicle
	){
		try {
			pService.delProductOV(idProduct, idVehicle, idOutVehicle);
			ArrayList<ProductTransform> listPT=pService.getListProductTransfrom_byInVehicle(idVehicle);
			return listPT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "getPageInVehicle_byStatusAndKey", method = RequestMethod.POST)
	@ResponseBody
	public Object getPageInVehicle_byStatusAndKey(
			@RequestParam(value = "status") String status, 
			@RequestParam(value = "record") int record,
			@RequestParam(value = "key") String key,
			@RequestParam(value = "currentPage") int currentPage
	){
		ArrayList<Object> list=new ArrayList<Object>();
		int sizePage = record;
		int total = vehicleService.countInVehicle_byStatusAndKey(key, status);
		ArrayList<InVehicle> pageIV=vehicleService.getPageInVehicle_byStatusAndKey(key, status, currentPage, sizePage);
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		list.add(pager);
		list.add(pageIV);
		return list;
	}
	@RequestMapping(value = "mtroutv", method = RequestMethod.GET)
	public ModelAndView mtrOutVehicle(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int currentPage = 1;
			int sizePage = Constants.SIZE_PAGE;
			String key=Constants.EMPTY;
			String status=Constants.EMPTY;
			int total=vehicleService.countOutVehicle_byStatusAndKey(key, status);
			Pager pager=new Pager();
			pager.initPage(sizePage, total, currentPage);
			ArrayList<OutVehicle> pageV=vehicleService.getPageOutVehicle_byStatusAndKey(key, status, currentPage, sizePage);
			
			mm.put("pager", pager);
			mm.put("pageV", pageV);
			return new ModelAndView("mtroutv.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "getPageOutVehicle_byStatusAndKey", method = RequestMethod.POST)
	@ResponseBody
	public Object getPageOutVehicle_byStatusAndKey(
			@RequestParam(value = "status") String status, 
			@RequestParam(value = "record") int record,
			@RequestParam(value = "key") String key,
			@RequestParam(value = "currentPage") int currentPage
	){
		ArrayList<Object> list=new ArrayList<Object>();
		int sizePage = record;
		int total = vehicleService.countOutVehicle_byStatusAndKey(key, status);
		ArrayList<OutVehicle> pageOV=vehicleService.getPageOutVehicle_byStatusAndKey(key, status, currentPage, sizePage);
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		
		list.add(pager);
		list.add(pageOV);
		return list;
	}
	/**/
	/*@RequestMapping(value = "rpvehicle", method = RequestMethod.GET)
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
	}*/
	@RequestMapping(value = "saveIVCurrentDay", method = RequestMethod.POST)
	@ResponseBody
	public Object saveIVCurrentDay(
			@RequestParam(value = "idVehicle") int idVehicle,
			@RequestParam(value = "date") String date, 
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "currentPage") int currentPage, 
			@RequestParam(value = "keySearch") String key, 
			@RequestParam(value = "record") int record
	){
		return vehicleService.upInVehicle(idVehicle, date, arrivalTime);
	}
	@RequestMapping(value = "saveOVCurrentDay", method = RequestMethod.POST)
	@ResponseBody
	public Object saveOVCurrentDay(
			@RequestParam(value = "idVehicle") int idVehicle,
			@RequestParam(value = "date") String date, 
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "currentPage") int currentPage, 
			@RequestParam(value = "keySearch") String key, 
			@RequestParam(value = "record") int record
	){
		return vehicleService.upOutVehicle(idVehicle, date, arrivalTime);
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
		int total = count;
		int size = record;
		Pager pager = new Pager();
		pager.initPage(size, total, currentPage);
		list.add(pager);
		ArrayList<InVehicle> listIV=vehicleService.getPageInVehicleCurDate_byKeySearch(keySearch, currentPage, record);
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
		int total = count;
		int size = record;
		Pager pager = new Pager();
		pager.initPage(size, total, currentPage);
		ArrayList<OutVehicle> listOV=vehicleService.getPageOutVehicleCurDate_byKeySearch(keySearch, currentPage, record);
		
		list.add(pager);
		list.add(listOV);
		return list;
	}
	@RequestMapping(value = "getPagePIVReportCurMonth", method = RequestMethod.POST)
	@ResponseBody
	public Object getPagePIVReportCurMonth(
			@RequestParam(value = "code") String key, 
			@RequestParam(value = "company") String company,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "record") int record,
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage = record;
		ArrayList<Object> list=new ArrayList<Object>();
		ArrayList<ProductInVehicle> pagePIV = pService.getPagePIVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		int total = pService.countAllPIVCurMonth_byKeyAndDate(key, date, company);
		Pager pager = new Pager();
		pager.initPage(sizePage, total, currentPage);
		list.add(pager);
		list.add(pagePIV);
		return list;
	}
	@RequestMapping(value = "getPagePOVReportCurMonth", method = RequestMethod.POST)
	@ResponseBody
	public Object getPagePOVReportCurMonth(
			@RequestParam(value = "code") String key, 
			@RequestParam(value = "company") String company,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "record") int record,
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage = record;
		ArrayList<Object> list=new ArrayList<Object>();
		ArrayList<ProductOutVehicle> pagePOV = pService.getPagePOVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		int total = pService.countAllPOVCurMonth_byKeyAndDate(key, date, company);
		Pager pager=new Pager();
		pager.initPage(sizePage, total, currentPage);
		list.add(pager);
		list.add(pagePOV);
		return list;
	}
	@RequestMapping(value = "exportExcel", method = RequestMethod.GET)
	public ModelAndView exportExcel(){
		int currentPage = Constants.START_PAGE;
		String key = Constants.EMPTY;
		String date = Constants.EMPTY;
		String company = Constants.EMPTY;
		int total=pService.countAllPIVCurMonth_byKeyAndDate(key, date, company);
		int sizePage = total;
		ArrayList<ProductInVehicle> pagePIV=pService.getPagePIVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		ArrayList<ProductOutVehicle> pagePOV=pService.getPagePOVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
		ArrayList<Object> list=new ArrayList<Object>();
		list.add(pagePIV);
		list.add(pagePOV);
		return new ModelAndView("excelView", "list", list);
	}
	
	/*@RequestMapping(value = "assignDoorInVehicle", method = RequestMethod.POST)
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
	}*/
}
