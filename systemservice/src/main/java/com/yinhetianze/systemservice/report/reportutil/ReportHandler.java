package com.yinhetianze.systemservice.report.reportutil;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportHandler {
	
	// map : title, params, headers, data
	private List<SWorkSheetModel> SWorkSheets;
	
	private BaseProcedure produceProcedure;

	private Logger LOG = LoggerFactory.getLogger(ReportHandler.class);
	
	public ReportHandler(){
		initReportData();
	}

	/**
	 * @description create SWorkbook
	 * @param templatePath : excel template file path
	 */
	public SXSSFWorkbook doProduce(String templatePath) {
		SXSSFWorkbook SWorkbook = null;
		if(StringUtils.isNotEmpty(templatePath)){
			try {
				SWorkbook = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(templatePath)),1000);
			} catch (FileNotFoundException e) {
				LOG.error(e.getMessage(), e);
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}else{
			SWorkbook = new SXSSFWorkbook (1000);
		}
		return doProduce(SWorkbook);
	}
	
	/**
	 * @description create SWorkbook
	 */
	public SXSSFWorkbook doProduce(SXSSFWorkbook SWorkbook) {
		
		return produceProcedure.doProduce(SWorkbook, SWorkSheets);
		
	}
	
	/**
	 * @description create SWorkbook
	 */
	public SXSSFWorkbook doProduce() {
		return doProduce("");
	}
	
	/**
	 * @deprecated
	 */
	public void downloadXlsFile(HttpServletResponse response, Workbook wb, String fileName) {
		ServletOutputStream out = null;
		try {
//			HSSFWorkbook wb = (HSSFWorkbook)file;
			if(wb == null){
				return;
			}
			response.setHeader("Pragma", "No-cache");  
			response.setHeader("Cache-Control", "No-cache");  
			response.setDateHeader("Expires", 0);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(), "ISO-8859-1"));
			out = response.getOutputStream();
			wb.write(out);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	public void clearSWorkSheet() {
		initSWorkSheet();
	}
	
	public void initSWorkSheet() {
		SWorkSheets =  new ArrayList<SWorkSheetModel>();
	}

	public void addSWorkSheet(SWorkSheetModel sWorkSheet) {
		checkSWorkSheets();
		SWorkSheets.add(sWorkSheet);
	}
	
	public void addSWorkSheets(List<SWorkSheetModel> sWorkSheets) {
		checkSWorkSheets();
		SWorkSheets.addAll(sWorkSheets);
	}
	
	public void checkSWorkSheets(){
		if(SWorkSheets==null){
			initSWorkSheet();
		}
	}
	
	public void setProduceProcedure(BaseProcedure produceProcedure) {
		this.produceProcedure = produceProcedure;
	}
	
	public BaseProcedure getProduceProcedure() {
		return this.produceProcedure;
	}
	
	public void initReportData() {
		produceProcedure = new ProduceProcedure();
	}

	public List<SWorkSheetModel> getSWorkSheets() {
		return SWorkSheets;
	}

	public void setSWorkSheets(List<SWorkSheetModel> sWorkSheets) {
		SWorkSheets = sWorkSheets;
	}
	
	// set|get excel default parameter -start-
	public int getDefaultRow() {
		if(produceProcedure == null) return 0;
		return produceProcedure.getDefaultRow();
	}

	public void setDefaultRow(int defaultRow) {
		if(produceProcedure == null) return;
		produceProcedure.setDefaultRow(defaultRow);
	}

	public int getDefaultCell() {
		if(produceProcedure == null) return 0;
		return produceProcedure.getDefaultCell();
	}

	public void setDefaultCell(int defaultCell) {
		if(produceProcedure == null) return;
		produceProcedure.setDefaultCell(defaultCell);
	}

	public int getDefaultDataStartRow() {
		if(produceProcedure == null) return 0;
		return produceProcedure.getDefaultDataStartRow();
	}

	public void setDefaultDataStartRow(int defaultDataStartRow) {
		if(produceProcedure == null) return;
		produceProcedure.setDefaultDataStartRow(defaultDataStartRow);
	}

	public int getSheetMaxRow() {
		if(produceProcedure == null) return 0;
		return produceProcedure.getSheetMaxRow();
	}

	/**
	 * @param  sheetMaxRow : max = 50000
	 */
	public void setSheetMaxRow(int sheetMaxRow) {
		if(produceProcedure == null) return;
		produceProcedure.setSheetMaxRow(sheetMaxRow);
	}
	// set|get excel default parameter -end-
	
	// 10-24
	public String getRedProgress() {
		return produceProcedure.getRedProgress();
	}
	
	public void initRedProgress() {
		produceProcedure.initRedProgress();
	}
	
}
