package com.appstracta.dao;

import java.util.List;

import com.appstracta.bean.CityBean;
import com.appstracta.exceptions.InternalException;

public interface ICityDao {

	List<CityBean> obtenerTodos() throws InternalException;

	CityBean guardar(CityBean city) throws InternalException;

}
