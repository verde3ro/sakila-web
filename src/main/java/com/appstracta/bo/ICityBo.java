package com.appstracta.bo;

import java.util.List;

import com.appstracta.bean.CityBean;
import com.appstracta.exceptions.InternalException;

public interface ICityBo {

	List<CityBean> obtenerTodos() throws InternalException;

}
