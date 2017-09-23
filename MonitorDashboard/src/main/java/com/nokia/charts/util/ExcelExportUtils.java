package com.nokia.charts.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nokia.charts.dto.qa.SRExportResultDto;

/**
 * Excel组件
 * 
 * @author Kin
 *
 */
@SuppressWarnings("unused")
public class ExcelExportUtils {

	public static final Logger logger = Logger
			.getLogger(ExcelExportUtils.class);
	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";
	/**
	 * 分隔符
	 */
	private final static String SEPARATOR = "|";
	/**
	 * NO DATA
	 */
	private final static String No_Data = "(No_Data)";

	/**
	 * Export data with .xls format
	 * 
	 * @param fieldNames
	 *            Excel Header
	 * @param list
	 *            Result data
	 * @param out
	 *            OutputStream
	 * @param sheetName
	 *            Chart Type
	 * @author Kin
	 *
	 */
	public static void exportXLSExcel(String[] fieldNames,
			List<Map<String, Object>> list, ServletOutputStream out,
			String sheetName) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setColor(IndexedColors.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setFontName("Calibri");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行（index 从0开始）
		HSSFRow row = sheet.createRow(0);

		for (int i = 0; i < fieldNames.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(fieldNames[i].toString());
		}

		if (list.size() > 0) {
			// 遍历集合数据，产生数据行
			for (int index = 0; index < list.size(); index++) {
				// 第一行放的是标题（index=0），这里index从1开始
				row = sheet.createRow(index + 1);
				// 遍历英文表头，逐个单元格塞值，取值的时候具体根据英文表头名称作为key去取值
				for (int i = 0; i < fieldNames.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(style2);
					cell.setCellValue(list.get(index).get(fieldNames[i]) == null ? ""
							: list.get(index).get(fieldNames[i]).toString());
				}
			}
		} else {
			row = sheet.createRow(1);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(No_Data);
		}
		try {
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Export data with generate new file
	 * 
	 * @param fieldNames
	 *            Excel Header
	 * @param list
	 *            Result data
	 * @param out
	 *            OutputStream
	 * @param chartType
	 *            Chart Type
	 * @author Kin
	 *
	 */
	public static void exportExcel(String[] fieldNames,
			List<Map<String, Object>> list, ServletOutputStream out,
			String chartType) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(chartType);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 为标题设置这些样式
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // Background
																			// color
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setColor(IndexedColors.AUTOMATIC.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setFontName("Calibri");
		font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行（index 从0开始）
		XSSFRow row = sheet.createRow(0);

		for (int i = 0; i < fieldNames.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(fieldNames[i].toString());
		}

		// 遍历集合数据，产生数据行
		for (int index = 0; index < list.size(); index++) {
			// 第一行放的是标题（index=0），这里index从1开始
			row = sheet.createRow(index + 1);

			// 遍历英文表头，逐个单元格塞值，取值的时候具体根据英文表头名称作为key去取值
			for (int i = 0; i < fieldNames.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				cell.setCellValue(list.get(index).get(fieldNames[i]) == null ? ""
						: list.get(index).get(fieldNames[i]).toString());
			}
		}
		try {
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String path = "D:\\myworkspace\\MonitorDashboard\\src\\main\\resources\\tmp\\template.xlsx";
		XSSFWorkbook wb = null;
		POIFSFileSystem fs = null;
		try {
			// read the tmp file
			wb = new XSSFWorkbook(new FileInputStream(path));

			XSSFCellStyle style = wb.getCellStyleAt(0);
			// Open the first sheet
			XSSFSheet st = wb.getSheetAt(0);
			// Get 3rd row to write
			XSSFRow row = st.getRow(2);

			Field[] fields = SRExportResultDto.class.getDeclaredFields();
			String[] fieldNames = new String[fields.length];

			FileOutputStream os = new FileOutputStream("e:\\workbook.xlsx");
			wb.write(os);
			os.close();
			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();

				// String a = row.getCell(i).toString();

				// System.out.println(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("excel导出成功！");
	}
}
