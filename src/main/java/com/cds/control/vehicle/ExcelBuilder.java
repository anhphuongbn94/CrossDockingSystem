package com.cds.control.vehicle;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.cds.entity.product.ProductInVehicle;
import com.cds.entity.product.ProductOutVehicle;


@SuppressWarnings("deprecation")
public class ExcelBuilder extends AbstractExcelView {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CellStyle style = styleCell(workbook);
		
		ArrayList<Object> list=(ArrayList<Object>) model.get("list");
		ArrayList<ProductInVehicle> listPIV = (ArrayList<ProductInVehicle>) list.get(0);
		ArrayList<ProductOutVehicle> listPOV = (ArrayList<ProductOutVehicle>) list.get(1);
		
		HSSFSheet excelSheet = workbook.createSheet("Invehicle Data");
		setExcelHeader(style, excelSheet);
		setExcelRowsPIV(excelSheet, listPIV);
		
		excelSheet = workbook.createSheet("OutVehicle Data");
		setExcelHeader(style, excelSheet);
		setExcelRowsPOV(excelSheet, listPOV);
	}
	public void setExcelHeader(CellStyle style, HSSFSheet excelSheet) {
		HSSFRow excelTitle = excelSheet.createRow(0);
		excelTitle.createCell(2).setCellValue("Statistics Vehicle Month");
		excelTitle = excelSheet.createRow(1);
		excelTitle.createCell(2).setCellValue("InVehicle");
		
		HSSFRow excelHeader = excelSheet.createRow(2);
		excelSheet.setDefaultColumnWidth(14);
		
		excelHeader.createCell(0).setCellValue("Vehicle Code");
		excelHeader.getCell(0).setCellStyle(style);
		
		excelHeader.createCell(1).setCellValue("Company");
		excelHeader.getCell(1).setCellStyle(style);
		
		excelHeader.createCell(2).setCellValue("Date");
		excelHeader.getCell(2).setCellStyle(style);
		
		excelHeader.createCell(3).setCellValue("Arrival Time");
		excelHeader.getCell(3).setCellStyle(style);
		
		excelHeader.createCell(4).setCellValue("Product Name");
		excelHeader.getCell(4).setCellStyle(style);
		
		excelHeader.createCell(5).setCellValue("Quantity");
		excelHeader.getCell(5).setCellStyle(style);
		
		excelHeader.createCell(6).setCellValue("Unit");
		excelHeader.getCell(6).setCellStyle(style);
	}
	public void setExcelRowsPIV(HSSFSheet excelSheet, ArrayList<ProductInVehicle> list){
		int record = 1;
		for(ProductInVehicle p:list){
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(p.getIv().getVehicleCode());
			excelRow.createCell(1).setCellValue(p.getIv().getCompany());
			excelRow.createCell(2).setCellValue(p.getIv().getDate());
			excelRow.createCell(3).setCellValue(p.getIv().getArrivalTime());
			excelRow.createCell(4).setCellValue(p.getP().getName());
			excelRow.createCell(5).setCellValue(p.getQuantity());
			excelRow.createCell(6).setCellValue(p.getU().getName());
		}
	}
	public void setExcelRowsPOV(HSSFSheet excelSheet, ArrayList<ProductOutVehicle> list){
		int record = 1;
		for(ProductOutVehicle p:list){
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(p.getOv().getVehicleCode());
			excelRow.createCell(1).setCellValue(p.getOv().getCompany());
			excelRow.createCell(2).setCellValue(p.getOv().getDate());
			excelRow.createCell(3).setCellValue(p.getOv().getArrivalTime());
			excelRow.createCell(4).setCellValue(p.getP().getName());
			excelRow.createCell(5).setCellValue(p.getQuantity());
			excelRow.createCell(6).setCellValue(p.getU().getName());
		}
	}
	
	public CellStyle styleCell(HSSFWorkbook workbook){
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        return style;
	}
	public CellStyle styleTitle(HSSFWorkbook workbook){
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(HSSFColor.DARK_GREEN.index);
        font.setFontName("Arial");
		style.setFont(font);
        return style;
	}
	
}
