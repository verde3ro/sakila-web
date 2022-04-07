package com.appstracta.bo.impl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;

import com.appstracta.bean.CityBean;
import com.appstracta.bo.ICityBo;
import com.appstracta.dao.ICityDao;
import com.appstracta.dao.impl.CityDaoImpl;
import com.appstracta.exceptions.InternalException;

public class CityBoImpl implements ICityBo {

	private final Logger log = Logger.getLogger(getClass().getName());

	@Override
	public List<CityBean> obtenerTodos() throws InternalException {
		try {
			ICityDao cityDao = new CityDaoImpl();

			return cityDao.obtenerTodos();
		} catch (InternalException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Ocurrio un error inesperado", ex);
			throw new InternalException("Ocurrio un error inesperado");
		}
	}

	@Override
	public CityBean guardar(CityBean city) throws InternalException {
		if (null == city || city.getCity() == null || city.getCity().trim().isEmpty() || city.getCountry() == null
				|| city.getCountry().getCountryId() == null) {
			throw new InternalException("El nombre de la ciudad y país nodeben ser nulos o vaciós.");
		}

		try {
			ICityDao cityDao = new CityDaoImpl();
			city.setLastUpdate(new Date());

			return cityDao.guardar(city);
		} catch (InternalException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Ocurrio un error inesperado", ex);
			throw new InternalException("Ocurrio un error inesperado");
		}
	}

	@Override
	public String obtenerExcel() throws InternalException {
		try {
			String base64 = null;
			String[] encabezado = {"Id", "Ciudad", "Fecha"};
			ICityDao cityDao = new CityDaoImpl();
			List<CityBean> ciudades = cityDao.obtenerTodos();

			// Creando libro
			try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				//Crear hoja
				XSSFSheet sheet = workbook.createSheet("Ciudades");
				// Creando estilo de la hoja
				CellStyle style = workbook.createCellStyle();
				// Creando Fuente
				XSSFFont font = workbook.createFont();
				font.setFontHeight(10);
				font.setFontName("Arial");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);
				font.setItalic(false);

				// Se establece color de celda
				style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setFont(font);

				// Creando fila 0 del encabezado
				XSSFRow rowHead = sheet.createRow(0);

				// Escribiendo encabezado
				int i = 0;
				for (String titulo: encabezado) {
					// Creando celda
					XSSFCell cell = rowHead.createCell(i);
					cell.setCellStyle(style);
					cell.setCellValue(titulo);
					i++;
				}

				// Escribiendo Datos
				int rownum = 1;
				for (CityBean ciudad : ciudades) {
					// Creando fila
					XSSFRow row = sheet.createRow(rownum);

					XSSFCell cell0 = row.createCell(0);
					cell0.setCellValue(ciudad.getCityId());

					XSSFCell cell1 = row.createCell(1);
					cell1.setCellValue(ciudad.getCity().trim());

					XSSFCell cell2 = row.createCell(2);
					cell2.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(ciudad.getLastUpdate()));

					rownum++;
				}

				// Se establece el ancho de columna al máximo para las columnas
				for(int position = 0; position < encabezado.length; position++) {
					sheet.autoSizeColumn(position);
				}

				workbook.write(bos);

				base64 = Base64.getEncoder().encodeToString(bos.toByteArray());
			}


			return base64;
		} catch (InternalException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Ocurrio un error al generar el archivo excel", ex);
			throw new InternalException("Ocurrio un error al generar el archivo excel");
		}
	}

}
