package com.cds.service.impl.door;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.door.DoorDAO;
import com.cds.entity.door.Cost;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.service.door.DoorService;

@Component
public class DoorServiceImpl implements DoorService{
	@Autowired
	private DoorDAO doorDAO;

	public ArrayList<InDoor> getListInDoorActive() {
		return doorDAO.getListInDoorActive();
	}

	public ArrayList<InDoor> getListInDoor() {
		return doorDAO.getListInDoor();
	}

	public boolean checkInsertInDoor(InDoor iDoor) {
		return doorDAO.checkInsertInDoor(iDoor);
	}

	public boolean insertInDoor(InDoor iDoor) {
		return doorDAO.insertInDoor(iDoor);
	}

	public boolean editInDoor(InDoor iDoor) {
		return doorDAO.editInDoor(iDoor);
	}

	public boolean checkTransitonStatusActiveIndoor(long idInDoor) {
		return doorDAO.checkTransitonStatusActiveIndoor(idInDoor);
	}

	public ArrayList<OutDoor> getListOutDoorActive() {
		return doorDAO.getListOutDoorActive();
	}

	public ArrayList<OutDoor> getListOutDoor() {
		return doorDAO.getListOutDoor();
	}

	public boolean checkInsertOutDoor(OutDoor oDoor) {
		return doorDAO.checkInsertOutDoor(oDoor);
	}

	public boolean insertOutDoor(OutDoor oDoor) {
		return doorDAO.insertOutDoor(oDoor);
	}

	public boolean editOutDoor(OutDoor oDoor) {
		return doorDAO.editOutDoor(oDoor);
	}

	public boolean checkTransitonStatusActiveOutdoor(long idOutDoor) {
		return doorDAO.checkTransitonStatusActiveOutdoor(idOutDoor);
	}

	public boolean insertCost(Cost cost) {
		return doorDAO.insertCost(cost);
	}

	public boolean editCost(Cost cost) {
		return doorDAO.editCost(cost);
	}

	public boolean delIndoor(Long idDoor) {
		return doorDAO.delIndoor(idDoor);
	}

	public boolean delOutDoor(Long idDoor) {
		return doorDAO.delOutDoor(idDoor);
	}

	public double getTotalCostCurDate() {
		return doorDAO.getTotalCostCurDate();
	}

	public double getTotalCostNotInVehicle(Long idInVehicle) {
		return doorDAO.getTotalCostNotInVehicle(idInVehicle);
	}

	public double getTotalCostNotOutVehicle(Long idOutVehicle) {
		return doorDAO.getTotalCostNotOutVehicle(idOutVehicle);
	}

	public double getCostInVehicleAssign(Long idInVehicle, Long idInDoor) {
		return doorDAO.getCostInVehicleAssign(idInVehicle, idInDoor);
	}

	public double getCostOutVehicleAssign(Long idOutVehicle, Long idOutDoor) {
		return doorDAO.getCostOutVehicleAssign(idOutVehicle, idOutDoor);
	}

	public boolean assignDoorIV(Long idInVehicle, Long idInDoor) {
		return doorDAO.assignDoorIV(idInVehicle, idInDoor);
	}

	public boolean assignDoorOV(Long idOutVehicle, Long idOutDoor) {
		return doorDAO.assignDoorOV(idOutVehicle, idOutDoor);
	}

	public boolean checkUpdateInDoor(Long idInDoor) {
		return doorDAO.checkUpdateInDoor(idInDoor);
	}

	public boolean checkUpdateOutDoor(Long idOutDoor) {
		return doorDAO.checkUpdateOutDoor(idOutDoor);
	}

	public boolean checkEditIndoor(Long idInDoor) {
		return doorDAO.checkEditIndoor(idInDoor);
	}

	public boolean checkDelInDoor(Long idInDoor) {
		return doorDAO.checkDelInDoor(idInDoor);
	}

	public int getCapUseInDoorbyId(Long idInDoor) {
		return doorDAO.getCapUseInDoorbyId(idInDoor);
	}

	public boolean checkEditOutDoor(Long idOutDoor) {
		return doorDAO.checkEditOutDoor(idOutDoor);
	}

	public boolean checkDelOutDoor(Long idOutDoor) {
		return doorDAO.checkDelOutDoor(idOutDoor);
	}

	public int getCapUseOutDoorbyId(Long idOutDoor) {
		return doorDAO.getCapUseOutDoorbyId(idOutDoor);
	}

	public ArrayList<Cost> getListCostByIdInDoor(Long idDoor) {
		return doorDAO.getListCostByIdInDoor(idDoor);
	}

	public ArrayList<Cost> getListCostByIdOutDoor(Long idDoor) {
		return doorDAO.getListCostByIdOutDoor(idDoor);
	}

	public ArrayList<ProductTransfer> getListProductTransfer(Long[] arrIV, Long[] arrOV) {
		return doorDAO.getListProductTransfer(arrIV, arrOV);
	}

	@Override
	public ArrayList<Cost> getAllListCost() {
		return doorDAO.getAllListCost();
	}

	@Override
	public boolean upAssignDoorAI(ArrayList<InVehicle> listIV, 
			ArrayList<OutVehicle> listOV, int[] arrInDoorAI,int[] arrOutDoorAI) {
		return doorDAO.upAssignDoorAI(listIV, listOV, arrInDoorAI, arrOutDoorAI);
	}

	@Override
	public boolean useAssignDoorAI(ArrayList<InVehicle> listIV, ArrayList<OutVehicle> listOV) {
		return doorDAO.useAssignDoorAI(listIV, listOV);
	}

	@Override
	public boolean checkExistsAssignDoorAI() {
		return doorDAO.checkExistsAssignDoorAI();
	}

	@Override
	public double getTotalCostAICurDate() {
		return doorDAO.getTotalCostAICurDate();
	}

}
