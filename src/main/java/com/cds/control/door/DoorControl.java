package com.cds.control.door;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cds.entity.door.Cost;
import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.employee.Employee;
import com.cds.field.Constant;
import com.cds.service.door.DoorService;

@Controller
public class DoorControl {
	@Autowired
	private DoorService doorService;
	
	/* Income Door
	 * 
	 * */
	@RequestMapping(value = "indoor", method = RequestMethod.GET)
	public ModelAndView indoor(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			mm.put("listIDoor", doorService.getListInDoor());
			mm.put("listODoor", doorService.getListOutDoor());
			return new ModelAndView("indoor.data.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
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
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD,
			@RequestParam(value = "statusD") Integer status
	){
		ArrayList<Object> list=new ArrayList<Object>();
		
		InDoor iDoor=new InDoor();
		iDoor.setIdDoor(idDoor);
		iDoor.setNameDoor(nameD);
		iDoor.setCapacity(capD);
		iDoor.setStatus(status);
//		if(doorService.checkInsertInDoor(iDoor)){
		boolean flag=true;
		if(status==1){
			if(!doorService.checkTransitonStatusActiveIndoor(idDoor)){
				list.add(Constant.FAIL_TRANSITION_STATUS);
				flag=false;
			}
		}
		if(flag) {
			doorService.editInDoor(iDoor);
			list.add(null);
		}
		ArrayList<InDoor> listID=doorService.getListInDoor();
		list.add(listID);
		return list;
	}
	/* Outcome Door
	 * 
	 * */
	@RequestMapping(value = "outdoor", method = RequestMethod.GET)
	public ModelAndView outdoor(ModelMap mm, HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			mm.put("listODoor", doorService.getListOutDoor());
			return new ModelAndView("outdoor.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
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
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD,
			@RequestParam(value = "statusD") Integer status
	){
		ArrayList<Object> list=new ArrayList<Object>();
		OutDoor oDoor=new OutDoor();
		oDoor.setIdDoor(idDoor);
		oDoor.setNameDoor(nameD);
		oDoor.setCapacity(capD);
		oDoor.setStatus(status);
		boolean flag=true;
		if(status==1){
			if(!doorService.checkTransitonStatusActiveIndoor(idDoor)){
				list.add(Constant.FAIL_TRANSITION_STATUS);
				flag=false;
			}
		}
		if(flag) {
			doorService.editOutDoor(oDoor);
			list.add(null);
		}
		ArrayList<OutDoor> listOD=doorService.getListOutDoor();
		list.add(listOD);
		return list;
	}
	/*
	 * 
	 * */
	@RequestMapping(value = "setcost", method = RequestMethod.GET)
	public ModelAndView setcost(ModelMap mm, HttpSession session){
//		Employee em=(Employee) session.getAttribute("em");
//		if(em != null){
			int currentPage=1, sizePage=15;
			int numPage=0;
			int total=doorService.countCost();
			if(total%sizePage == 0) numPage=total/sizePage;
			else numPage=total/sizePage + 1;
			mm.put("numPage", numPage);
			mm.put("pageCost", doorService.getPageCost(currentPage, sizePage));
			return new ModelAndView("setcost.def");
//		}else{
//			return new ModelAndView("login.def");
//		}
	}
	@RequestMapping(value = "getPageCost", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Cost> getPageCost(@RequestParam(value = "currentPage") int currentPage){
		int sizePage = 15;
		ArrayList<Cost> pageCost=doorService.getPageCost(currentPage, sizePage);
		return pageCost;
	}
	@RequestMapping(value = "editCost", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Cost> editCost(
			@RequestParam(value = "idCost") int idCost,
			@RequestParam(value = "cost") double cost,
			@RequestParam(value = "currentPage") int currentPage
	){
		int sizePage=15;
		Cost c=new Cost(); c.setIdCost(idCost); c.setCost(cost);
		if(doorService.editCost(c)){
			ArrayList<Cost> pageCost=doorService.getPageCost(currentPage, sizePage); 
			return pageCost;
		}
		return null;
	}
}
