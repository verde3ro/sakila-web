package com.appstracta.bo.impl;

import java.util.List;

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

}
