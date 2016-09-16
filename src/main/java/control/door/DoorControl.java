package control.door;

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
import entity.door.Cost;
import entity.door.CrossDockingSystem;
import entity.door.InDoor;
import entity.door.OutDoor;

@Controller
public class DoorControl {
	@Autowired
	private DoorDAO doorDAO;
	
	/* Income Door
	 * 
	 * */
	@RequestMapping(value = "indoor", method = RequestMethod.GET)
	public ModelAndView indoor(ModelMap mm){
		mm.put("listIDoor", doorDAO.getListInDoor());
		mm.put("listODoor", doorDAO.getListOutDoor());
		return new ModelAndView("indoor.data.def");
	}
	@RequestMapping(value = "insertInDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InDoor> insertInDoor(
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD
	){
		int statusD=0;
		CrossDockingSystem cDS=new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);
		InDoor iDoor=new InDoor(nameD, capD, cDS, statusD);
		if(doorDAO.insertInDoor(iDoor)){
			iDoor.setIdDoor(doorDAO.getIdInDoorInsert());
			ArrayList<OutDoor> listOD=doorDAO.getListOutDoor();			
			for(int i=0; i<listOD.size(); i++){
				Cost cost=new Cost(iDoor, listOD.get(i), 0d);
				doorDAO.insertCost(cost);
			}
			ArrayList<InDoor> listID=doorDAO.getListInDoor();
			return listID;
		}
		return null;
	}
	@RequestMapping(value = "editInDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<InDoor> editInDoor(
			@RequestParam(value = "idInDoor") Integer idInDoor,
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD,
			@RequestParam(value = "statusD") Integer statusD
	){
		if(statusD == 1){
			if(doorDAO.checkTransitonStatusActiveIndoor(idInDoor)){
				InDoor iDoor=new InDoor(idInDoor, nameD, capD, statusD);
				if(doorDAO.editInDoor(iDoor)){
					ArrayList<InDoor> listID=doorDAO.getListInDoor();
					return listID;
				}
			}
		}else{
			InDoor iDoor=new InDoor(idInDoor, nameD, capD, statusD);
			if(doorDAO.editInDoor(iDoor)){
				ArrayList<InDoor> listID=doorDAO.getListInDoor();
				return listID;
			}
		}
		return null;
	}
	/* Outcome Door
	 * 
	 * */
	@RequestMapping(value = "outdoor", method = RequestMethod.GET)
	public ModelAndView outdoor(ModelMap mm){
		mm.put("listODoor", doorDAO.getListOutDoor());
		return new ModelAndView("outdoor.def");
	}
	@RequestMapping(value = "insertOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutDoor> insertOutDoor(
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD
	){
		int statusD=0;
		CrossDockingSystem cDS=new CrossDockingSystem(); cDS.setIdCrossDockingSystem(1);
		OutDoor oDoor=new OutDoor(nameD, capD, cDS, statusD);
		if(doorDAO.insertOutDoor(oDoor)){
			oDoor.setIdDoor(doorDAO.getIdOutDoorInsert());
			ArrayList<InDoor> listID=doorDAO.getListInDoor();			
			for(int i=0; i<listID.size(); i++){
				Cost cost=new Cost(listID.get(i), oDoor, 0d);
				doorDAO.insertCost(cost);
			}
			ArrayList<OutDoor> listOD=doorDAO.getListOutDoor();
			return listOD;
		}
		return null;
	}
	@RequestMapping(value = "editOutDoor", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<OutDoor> editOutDoor(
			@RequestParam(value = "idOutDoor") Integer idOutDoor,
			@RequestParam(value = "nameD") String nameD,
			@RequestParam(value = "capD") Integer capD,
			@RequestParam(value = "statusD") Integer statusD
	){
		if(statusD == 1){
			if(doorDAO.checkTransitonStatusActiveOutdoor(idOutDoor)){
				OutDoor oDoor=new OutDoor(idOutDoor, nameD, capD, statusD);
				if(doorDAO.editOutDoor(oDoor)){
					ArrayList<OutDoor> listOD=doorDAO.getListOutDoor();
					return listOD;
				}
			}
		}else{			
			OutDoor oDoor=new OutDoor(idOutDoor, nameD, capD, statusD);
			if(doorDAO.editOutDoor(oDoor)){
				ArrayList<OutDoor> listOD=doorDAO.getListOutDoor();
				return listOD;
			}
		}
		return null;
	}
	/*
	 * 
	 * */
	@RequestMapping(value = "setcost", method = RequestMethod.GET)
	public ModelAndView setcost(ModelMap mm){
		int currentPage=1, sizePage=15;
		int numPage=0;
		int total=doorDAO.countCost();
		if(total%sizePage == 0) numPage=total/sizePage;
		else numPage=total/sizePage + 1;
		mm.put("numPage", numPage);
		mm.put("pageCost", doorDAO.getPageCost(currentPage, sizePage));
		return new ModelAndView("setcost.def");
	}
	@RequestMapping(value = "getPageCost", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Cost> getPageCost(@RequestParam(value = "currentPage") int currentPage){
		int sizePage = 15;
		ArrayList<Cost> pageCost=doorDAO.getPageCost(currentPage, sizePage);
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
		if(doorDAO.editCost(c)){
			ArrayList<Cost> pageCost=doorDAO.getPageCost(currentPage, sizePage); 
			return pageCost;
		}
		return null;
	}
}
