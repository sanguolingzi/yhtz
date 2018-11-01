package com.yinhetianze.systemservice.report.reportutil;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public abstract class BaseProcedure {
	
	protected SXSSFWorkbook SWorkbook;
	
	protected Sheet sheet;
	
	protected Map<String, CellStyle> cellStyle;

	protected Row row;
	
	protected Cell cell;
	
	protected String datePattern = "yyyy-MM-dd'T'HH:mm:ss";

	protected int sheetMaxRow = 1000000;
	
	protected int defaultRow = 0;

	protected int defaultCell = 0;

	protected int defaultDataStartRow = 10;
	
	protected int allRowNum = 0;
	
	protected int currentRowNum = 0;
	
	protected List<String> headerClose;
	
	protected String[] coord = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","L","S","T","U","V","W","X","Y","Z"};
	
	protected String NUMBER_TYPE = "NUMBER";
	
	protected String DATE_TYPE = "DATE";
	
	protected String OTHER_TYPE = "OTHER";
	
	/**
	 * @created 2016/10/09
	 * @description produce file object
	 * @param templatePath : excel template file path
	 * @param SWorkSheets : excel sheets data
	 * @return excel file object
	 */
	public abstract SXSSFWorkbook doProduce(SXSSFWorkbook SWorkbook, List<SWorkSheetModel> SWorkSheets);

	protected void initCellStyle(){
		cellStyle = new HashMap<String, CellStyle>();
	}
	
	/**
	 * @created 2016/10/20
	 * @description identify all report column
	 * @param clazz : excel data model class
	 * @return all report column
	 */
	protected final Field[] parseAimFields(Class<?> clazz) {
		Field [] fields = clazz.getDeclaredFields();
		Field [] annotationFields = new Field [0];
		
		int i=0;
		for(Field field : fields){
			if(clazz.isAnnotationPresent(ReportColumn.class) || field.isAnnotationPresent(ReportColumn.class)) {
				if((headerClose==null || !headerClose.contains(i+++""))){
					annotationFields = Arrays.copyOf(annotationFields, annotationFields.length + 1);
					annotationFields[annotationFields.length - 1] = field;
				}
			}
		}
		
		return annotationFields;
	}
	
	protected final int createInnerCell(List<?> cellsModelList, Field outfield, Row row, int startCell, CellStyle styleBold) throws IllegalArgumentException, IllegalAccessException {
		if(cellsModelList != null && cellsModelList.size() > 0) {
			Cell cell = null;
			
			Type fc = outfield.getGenericType();
			if (fc instanceof ParameterizedType) {// if generic parameter type
	            ParameterizedType pt = (ParameterizedType) fc;
	            Class<?> fieldClazz = (Class<?>) pt.getActualTypeArguments()[0]; //get generic parameter type
	            
	            if(isPrimitive(fieldClazz)) {
	            	
	            	if(fieldClazz == String.class) {
	            		for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(model.toString());
		            		}
		            		cell.setCellStyle(styleBold);
						}
        			} else if (fieldClazz == Integer.class) {
        				for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(Integer.valueOf(model.toString()));
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				} else if(fieldClazz == Double.class) {
	   					for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(Double.valueOf(model.toString()));
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				} else if(fieldClazz == Float.class) {
	   					for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(Float.valueOf(model.toString()));
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				} else if(fieldClazz == Long.class) {
	   					for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(Long.valueOf(model.toString()));
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				} else if(fieldClazz == Byte.class) {
	   					for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(Byte.valueOf(model.toString()));
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				} else if (fieldClazz == Date.class) {
	   					String pattern = outfield.getAnnotation(ReportColumn.class).dateFormat();
	   					for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		String dateStr = "";
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			dateStr = ReportDateUtil.format((Date) model, pattern);
		            			cell.setCellValue(dateStr);
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				} else if(fieldClazz == Boolean.class) {
	   					for (Object model : cellsModelList) {
		            		cell = row.createCell(startCell++);
		            		if(cell == null) continue;
		            		
		            		if(model != null) {
		            			cell.setCellValue(Boolean.valueOf(model.toString()));
		            		}
		            		cell.setCellStyle(styleBold);
						}
	   				}
	            } else {
	            	throw new RuntimeException("Class can only be Primitive or packaging group or java.util.List which can only include Primitive!");
	            }
			}
		}
		return startCell;
	}
	
	/**
	 * @created 2016/11/03
	 * @description check data type
	 * @param clazz : model class
	 * @return yes or no
	 */
	protected final boolean isPrimitive(Class<?> clazz) {
		if(clazz == null) return false;
		return (
//				clazz.isPrimitive() ||
				clazz == String.class ||
				clazz == Double.class ||
				clazz == Integer.class ||
				clazz == BigDecimal.class ||
				clazz == Date.class ||
				clazz == Long.class ||
				clazz == Float.class ||
//				clazz == Character.class ||
				clazz == Short.class ||
				clazz == Boolean.class ||
//				clazz == boolean.class ||
				clazz == Byte.class
				);
	}
	
	protected final String getCellValue(Cell cell) {
		int cellType = cell.getCellType();
		String value = null;
		
		switch (cellType) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)) {
				value = ReportDateUtil.format(cell.getDateCellValue(), datePattern);
			} else {
				DecimalFormat df = new DecimalFormat("########################.########################");
				value = df.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue()+"";
			break;
		case Cell.CELL_TYPE_ERROR:
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		default:
			break;
		}
		return value;
	}
	
	public final String calculatePrecision(int Precision, String value){
		return new BigDecimal(value).setScale(Precision, RoundingMode.HALF_UP).toString();
	}
	
	public int getDefaultRow() {
		return defaultRow;
	}

	public void setDefaultRow(int defaultRow) {
		this.defaultRow = defaultRow;
	}

	public int getDefaultCell() {
		return defaultCell;
	}

	public void setDefaultCell(int defaultCell) {
		this.defaultCell = defaultCell;
	}

	public int getDefaultDataStartRow() {
		return defaultDataStartRow;
	}

	public void setDefaultDataStartRow(int defaultDataStartRow) {
		this.defaultDataStartRow = defaultDataStartRow;
	}

	public int getSheetMaxRow() {
		return sheetMaxRow;
	}

	/**
	 * @param  sheetMaxRow : max = 50000
	 */
	public void setSheetMaxRow(int sheetMaxRow) {
		if(sheetMaxRow>50000){
			sheetMaxRow = 50000;
		}
		this.sheetMaxRow = sheetMaxRow;
	}
	
	public int getAllRowNum() {
		return allRowNum;
	}

	public void setAllRowNum(int allRowNum) {
		this.allRowNum = allRowNum;
	}

	public int getCurrentRowNum() {
		return currentRowNum;
	}

	public void setCurrentRowNum(int currentRowNum) {
		this.currentRowNum = currentRowNum;
	}
	
	public void initRedProgress() {
		this.allRowNum = 0;
		this.currentRowNum = 0;
	}
	
	public String getRedProgress() {
		System.out.println("currentRowNum="+currentRowNum+",allRowNum="+allRowNum);
		return ReportUtil.divide(currentRowNum+"", allRowNum+"").toString();
	}
	
}
