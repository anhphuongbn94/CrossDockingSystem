package com.cds.control.door;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cds.entity.Pager;
import com.cds.entity.door.Cost;
import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.employee.Employee;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.field.Constants;
import com.cds.service.door.DoorService;
import com.cds.service.vehicle.ProductService;
import com.cds.service.vehicle.VehicleService;
import com.cds.services.vehicle.VehicleServices;

@Controller
public class DoorControl {
	@Autowired
	private DoorService doorService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ProductService productService;
//	private DateUtils myTool=new DateUtils();
	/* Income Door
	 * 
	 * */
	@RequestMapping(value = "doorManager", method = RequestMethod.GET)
	public ModelAndView indoor(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			mm.put("listIDoor", doorService.getListInDoor());
			mm.put("listODoor", doorService.getListOutDoor());
			return new ModelAndView("doorManager.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "insertInDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InDoor> insertInDoor(
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD
	){
		int status=0;
		CrossDockingSystem cDS=new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);
		InDoor iDoor=new InDoor();
		iDoor.setNameDoor(nameD);
		iDoor.setCapacity(capD);
		iDoor.setStatus(status);
		iDoor.setcDS(cDS);
		if(doorService.checkInsertInDoor(iDoor)){
			doorService.insertInDoor(iDoor);
			ArrayList<InDoor> listID=doorService.getListInDoor();
			return listID;
		}
		return null;
	}
	@RequestMapping(value = "editInDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object editInDoor(
			@RequestParam(value = "idInDoor") Long idDoor,
			@RequestParam(value = "capD") int capD,
			@RequestParam(value = "statusD") int status
	){
		InDoor idoor=new InDoor();
		idoor.setIdDoor(idDoor);
		idoor.setCapacity(capD);
		idoor.setStatus(status);
		if(doorService.checkUpdateInDoor(idDoor)){
			if(status == 1){
				if(doorService.checkTransitonStatusActiveIndoor(idDoor)){
					doorService.editInDoor(idoor);
				}else{
					return "errTransitionStatus";
				}
			}else{
				doorService.editInDoor(idoor);
			}
		}else{
			return "errIsUpdate";
		}
		return "success";
	}
	@RequestMapping(value = "delInDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object delInDoor(@RequestParam(value = "idDoor") long idDoor){
		if(doorService.checkUpdateInDoor(idDoor)){
			doorService.delIndoor(idDoor);
			return true;
		}
		return false;
	}
	/* Outcome Door
	 * 
	 * */
	@RequestMapping(value = "insertOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutDoor> insertOutDoor(
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD
	){
		int status=0;
		CrossDockingSystem cDS=new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);
		OutDoor oDoor=new OutDoor();
		oDoor.setNameDoor(nameD);
		oDoor.setCapacity(capD);
		oDoor.setStatus(status);
		oDoor.setcDS(cDS);
		if(doorService.checkInsertOutDoor(oDoor)){
			doorService.insertOutDoor(oDoor);
			ArrayList<OutDoor> listOD=doorService.getListOutDoor();
			return listOD;
		}
		return null;
	}
	@RequestMapping(value = "editOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object editOutDoor(
			@RequestParam(value = "idOutDoor") Long idDoor,
			@RequestParam(value = "capD") Integer capD,
			@RequestParam(value = "statusD") Integer status
	){
		ArrayList<Object> list=new ArrayList<Object>();
		OutDoor oDoor=new OutDoor();
		oDoor.setIdDoor(idDoor);
		oDoor.setCapacity(capD);
		oDoor.setStatus(status);
		if(doorService.checkUpdateInDoor(idDoor)){
			if(status == 1){
				if(doorService.checkTransitonStatusActiveOutdoor(idDoor)){
					doorService.editOutDoor(oDoor);
				}else{
					return "errTransitionStatus";
				}
			}else{
				doorService.editOutDoor(oDoor);
			}
		}else{
			return "errIsUpdate";
		}
		return "success";
	}
	@RequestMapping(value = "delOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object delOutDoor(@RequestParam(value = "idDoor") long idDoor){
		if(doorService.checkUpdateOutDoor(idDoor)){
			doorService.delOutDoor(idDoor);
			return true;
		}
		return false;
	}
	/*
	 * 
	 * */
	@RequestMapping(value = "getInforCostByIdIndoor", method = RequestMethod.POST)
	@ResponseBody
	public Object getInforCostByIdIndoor(@RequestParam(value = "idDoor") Long idDoor){
		ArrayList<Cost> list=doorService.getListCostByIdInDoor(idDoor);
		return list;
	}
	@RequestMapping(value = "getInforCostByIdOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object getInforCostByIdOutDoor(@RequestParam(value = "idDoor") Long idDoor){
		ArrayList<Cost> list=doorService.getListCostByIdOutDoor(idDoor);
		return list;
	}
	@RequestMapping(value = "editCost", method = RequestMethod.POST)
	@ResponseBody
	public Object editCost(
			@RequestParam(value = "idCost") long idCost,
			@RequestParam(value = "cost") int cost
	){
		Cost c=new Cost();
		c.setIdCost(idCost);
		c.setCost(cost);
		return doorService.editCost(c);
	}
	
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public ModelAndView invehicle_assign(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			int currentPage = Constants.START_PAGE;
			int sizePage = Constants.SIZE_PAGE;
//			int sizePage = 5;
			int totalI=vehicleService.countInVehicle_whereAssignDoor();
			int totalO=vehicleService.countOutVehicle_whereAssignDoor();
			Pager pagerI = new Pager();
			pagerI.initPage(sizePage, totalI, currentPage);
			Pager pagerO = new Pager();
			pagerO.initPage(sizePage, totalO, currentPage);
			
			ArrayList<InVehicle> pageIV = vehicleService.getPageInVehicle_whereAssignDoor(currentPage, sizePage);
			mm.put("pagerI", pagerI);
			mm.put("listInDoor", doorService.getListInDoorActive());
			mm.put("pInVehicle", pageIV);
			
			ArrayList<OutVehicle> pageOV = vehicleService.getPageOutVehicle_whereAssignDoor(currentPage, sizePage);
			mm.put("pagerO", pagerO);
			mm.put("listOutDoor", doorService.getListOutDoorActive());
			mm.put("pOutVehicle", pageOV);
			
			mm.put("checkExistsAI", doorService.checkExistsAssignDoorAI());
			mm.put("totalCostAI", doorService.getTotalCostAICurDate());
			mm.put("totalCost", doorService.getTotalCostCurDate());
			return new ModelAndView("assign.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "assignDoorIV", method = RequestMethod.POST)
	@ResponseBody
	public Double assignDoorIV(
			@RequestParam(value = "idInVehicle") Long idInVehicle,
			@RequestParam(value = "idInDoor") Long idInDoor
	){
		doorService.assignDoorIV(idInVehicle, idInDoor);
		double ttCost = doorService.getTotalCostCurDate();
		return ttCost;
	}
	@RequestMapping(value = "getTotalCost", method = RequestMethod.POST)
	@ResponseBody
	public Double getTotalCost(){
		double ttCost = doorService.getTotalCostCurDate();
		return ttCost;
	}
	@RequestMapping(value = "assignDoorOV", method = RequestMethod.POST)
	@ResponseBody
	public Double assignDoorOV(
			@RequestParam(value = "idOutVehicle") Long idOutVehicle,
			@RequestParam(value = "idOutDoor") Long idOutDoor
	){
		doorService.assignDoorOV(idOutVehicle, idOutDoor);
		double ttCost = doorService.getTotalCostCurDate();
		return ttCost;
	}
	@RequestMapping(value = "getTotalCostAssignInDoor", method = RequestMethod.POST)
	@ResponseBody
	public double getTotalCostAssignInDoor(
			@RequestParam(value = "idInVehicle") Long idInVehicle,
			@RequestParam(value = "idInDoor") Long idInDoor
	){
		double ttCostNotV=doorService.getTotalCostNotInVehicle(idInVehicle);
		double ttCostByV=doorService.getCostInVehicleAssign(idInVehicle, idInDoor);
		double ttCost = ttCostNotV + ttCostByV;
		return ttCost;
	}
	@RequestMapping(value = "assignDoorAI", method = RequestMethod.POST)
	@ResponseBody
	public Object assignDoorAI(
			@RequestParam(value = "sizeIDoor") int sizeIDoor, 
			@RequestParam(value = "sizeODoor") int sizeODoor,
			@RequestParam(value = "arrCid[]") int [] arrCid,
			@RequestParam(value = "arrCod[]") int [] arrCod,
			@RequestParam(value = "arrIdInDoor[]") int [] arrID,
			@RequestParam(value = "arrIdOutDoor[]") int [] arrOD
	){
		ArrayList<Cost> listCost=doorService.getAllListCost();
		ArrayList<ProductTransfer> listPT=productService.getAllProductTransferCurDate();
		ArrayList<InVehicle> listIV=vehicleService.getAllInVehicleCurDate();
		ArrayList<OutVehicle> listOV=vehicleService.getAllOutVehicleCurDate();
		
		int sizeInVehicle = listIV.size();
		int sizeOutVehicle = listOV.size();
		long [] arrIV = new long[sizeInVehicle];
		long [] arrOV = new long[sizeOutVehicle];
		int [] arrIDC = arrCid;
		int [] arrODC = arrCod;
		int [] arrIVD = new int[sizeInVehicle];
		int [] arrOVD = new int[sizeOutVehicle];
		int [][] costs = new int[sizeIDoor][sizeODoor];
		int [][] trips = new int[sizeInVehicle][sizeOutVehicle];
		
		int k=0;
		for(int i=0; i<sizeIDoor; i++){
			for(int j=0; j<sizeODoor; j++){
				costs[i][j] = listCost.get(k).getCost();
				k++;
			}
		}
		for(int i=0; i<sizeInVehicle; i++){
			arrIV[i] = listIV.get(i).getIdInVehicle();
			arrIVD[i] = listIV.get(i).getVolumn();
			for(int j=0; j<sizeOutVehicle; j++){
				arrOV[j] = listOV.get(j).getIdOutVehicle();
				arrOVD[j] = listOV.get(j).getDemand();
				trips[i][j]=0;
				for(k=0; k<listPT.size(); k++){
					if(listPT.get(k).getIv().getIdInVehicle().equals(listIV.get(i).getIdInVehicle()) 
							&& listPT.get(k).getOv().getIdOutVehicle().equals(listOV.get(j).getIdOutVehicle())){
						trips[i][j] = listPT.get(k).getTransfer();
						break;
					}
				}
			}
		}
		VehicleServices vServices=new VehicleServices();
		String str = vServices.callAPIAssignDoor(sizeIDoor, sizeODoor, sizeInVehicle, sizeOutVehicle, arrIDC, arrODC, arrIVD, arrOVD, costs, trips);
		int [] arrInDoorAI = new int[sizeInVehicle];
		int [] arrOutDoorAI = new int[sizeOutVehicle];
		try {
			JSONObject jsonObject=new JSONObject(str);
			JSONArray inVehicleAssignments = jsonObject.getJSONArray("inVehicleAssignments");
			for(int i=0; i<inVehicleAssignments.length(); i++){
				arrInDoorAI[i] = (Integer) inVehicleAssignments.get(i);
				if(inVehicleAssignments.get(i) != null){
					Integer index = (Integer) inVehicleAssignments.get(i);
					arrInDoorAI[i] = arrID[index];
				}
			}
			JSONArray outVehicleAssignments = jsonObject.getJSONArray("outVehicleAssignments");
			for(int i=0; i<outVehicleAssignments.length(); i++){
				arrOutDoorAI[i] = (Integer) outVehicleAssignments.get(i);
				if(outVehicleAssignments.get(i) != null){
					Integer index = (Integer) outVehicleAssignments.get(i);
					arrOutDoorAI[i] = arrOD[index];
				}
			}
			doorService.upAssignDoorAI(listIV, listOV, arrInDoorAI, arrOutDoorAI);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		int currentPage = 1;
		int sizePage = Constants.SIZE_PAGE;
		ArrayList<InVehicle> pageIV = vehicleService.getPageInVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<OutVehicle> pageOV = vehicleService.getPageOutVehicle_whereAssignDoor(currentPage, sizePage);
		ArrayList<Object> list=new ArrayList<Object>();
		list.add(pageIV);
		list.add(pageOV);
		return str;
	}
	@RequestMapping(value = "useAssignDoorAI", method = RequestMethod.POST)
	@ResponseBody
	public Object useAssignDoorAI(){
		ArrayList<InVehicle> listIV=vehicleService.getAllInVehicleCurDate();
		ArrayList<OutVehicle> listOV=vehicleService.getAllOutVehicleCurDate();
		return doorService.useAssignDoorAI(listIV, listOV);
	}
}
