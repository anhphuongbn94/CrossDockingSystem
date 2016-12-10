package com.cds.service.door;

import java.util.ArrayList;

import com.cds.entity.door.Cost;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;

public interface DoorService {
	/****************InDoor*********************/
	public ArrayList<InDoor> getListInDoorActive();
	public ArrayList<InDoor> getListInDoor();
	public boolean checkInsertInDoor(InDoor iDoor);
	
	public boolean checkUpdateInDoor(Long idInDoor);
	
	public boolean insertInDoor(InDoor iDoor);
	public boolean checkEditIndoor(Long idInDoor);
	public boolean editInDoor(InDoor iDoor);
	public boolean checkDelInDoor(Long idInDoor);
	public boolean delIndoor(Long idDoor);
	public boolean checkTransitonStatusActiveIndoor(long idInDoor);
	public int getCapUseInDoorbyId(Long idInDoor);
	/****************OutDoor*********************/
	public ArrayList<Cost> getAllListCost();
	public ArrayList<OutDoor> getListOutDoorActive();
	public ArrayList<OutDoor> getListOutDoor();
	
	public boolean checkUpdateOutDoor(Long idOutDoor);
	
	public boolean checkInsertOutDoor(OutDoor oDoor);
	public boolean checkEditOutDoor(Long idOutDoor);
	public boolean insertOutDoor(OutDoor oDoor);
	public boolean checkDelOutDoor(Long idOutDoor);
	public boolean editOutDoor(OutDoor oDoor);
	public boolean delOutDoor(Long idDoor);
	public boolean checkTransitonStatusActiveOutdoor(long idOutDoor);
	public int getCapUseOutDoorbyId(Long idOutDoor);
	/****************Cost*********************/
	public ArrayList<Cost> getListCostByIdInDoor(Long idDoor);
	public ArrayList<Cost> getListCostByIdOutDoor(Long idDoor);
	public boolean insertCost(Cost cost);
	public boolean editCost(Cost cost);
	
	public double getTotalCostCurDate();
	public double getTotalCostAICurDate();
	public double getTotalCostNotInVehicle(Long idInVehicle);
	public double getTotalCostNotOutVehicle(Long idOutVehicle);
	public double getCostInVehicleAssign(Long idInVehicle, Long idInDoor);
	public double getCostOutVehicleAssign(Long idOutVehicle, Long idOutDoor);
	
	/**************************************************************/
	public boolean checkExistsAssignDoorAI();
	public boolean assignDoorIV(Long idInVehicle, Long idInDoor);
	public boolean assignDoorOV(Long idOutVehicle, Long idOutDoor);
	public boolean upAssignDoorAI(ArrayList<InVehicle> listIV, ArrayList<OutVehicle> listOV, int [] arrInDoorAI, int [] arrOutDoorAI);
	public boolean useAssignDoorAI(ArrayList<InVehicle> listIV, ArrayList<OutVehicle> listOV);
	public ArrayList<ProductTransfer> getListProductTransfer(Long [] arrIV, Long [] arrOV);
	
}
