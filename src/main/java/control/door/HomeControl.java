package control.door;
//package control;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import dao.vehicle.VehicleDAO;
//import entity.door.Door;
//import entity.vehicle.Vehicle;
//
//@Controller
//public class HomeControl {
//	@Autowired
//	private VehicleDAO carDAO;
//	
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public ModelAndView index(ModelMap mm){
//		int sizePage=5;
//		int status=1;
//		ArrayList<Vehicle> cars= carDAO.getPageCar_byStatus(1, sizePage, status);
//		mm.put("cars", cars);
//		int numPage=0;
//		int total=carDAO.count(status);
//		if(total%sizePage == 0) numPage=total/sizePage;
//		else numPage=total/sizePage + 1;
//		mm.put("numPage", numPage);
//		return new ModelAndView("views/car_manager");
//	}
//	@RequestMapping(value = "add_car", method = RequestMethod.POST)
//	@ResponseBody
//	public Vehicle add_carIn(
//			@RequestParam(value = "bxe") String bxe,
//			@RequestParam(value = "volume") int volume,
//			@RequestParam(value = "time") String time,
//			@RequestParam(value = "idDoor") int idDoor
//	){
//		try {
//			Door door=new Door(); door.setIdDoor(idDoor);
//			Vehicle car=new Vehicle(bxe, volume, time, door, 1);
//			carDAO.insertCar(car);
//			return car;
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		return null;
//	}
//	@RequestMapping(value = "delCar", method = RequestMethod.POST)
//	@ResponseBody
//	public ArrayList<Vehicle> delCar(
//			@RequestParam(value = "idCar") int idCar,
//			@RequestParam(value = "currentPage") int currentPage
//	){
//		try {
//			int status = 1;
//			int sizePage = 5;
//			carDAO.delCarby_idCar(idCar);
//			ArrayList<Vehicle> pageCar=carDAO.getPageCar_byStatus(currentPage, sizePage, status);
//			return pageCar;
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		return null;
//	}
//	@RequestMapping(value = "getPageCar", method = RequestMethod.POST)
//	@ResponseBody
//	public ArrayList<Vehicle> getPageCar(@RequestParam(value = "currentPage") int currentPage){
//		int sizePage=5;
//		int status=1;
//		try {
//			ArrayList<Vehicle> pageCar=carDAO.getPageCar_byStatus(currentPage, sizePage, status);
//			return pageCar;
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		return null;
//	}
//}
