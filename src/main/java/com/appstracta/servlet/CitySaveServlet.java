package com.appstracta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appstracta.bean.CityBean;
import com.appstracta.bean.CountryBean;
import com.appstracta.bo.ICityBo;
import com.appstracta.bo.impl.CityBoImpl;
import com.appstracta.exceptions.InternalException;
import com.google.gson.Gson;

/**
 * Servlet implementation class CitySaveServlet
 */
@WebServlet("/city/save")
public class CitySaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ICityBo cityBo = new CityBoImpl();
		Gson gson = new Gson();
		Map<String, Object> resultado = new HashMap<>();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try(PrintWriter out = response.getWriter()) {
			try {
				CityBean cityBean = new CityBean();
				String city = request.getParameter("txtCity").trim();
				Integer idCountry = Integer.parseInt(request.getParameter("cbmPais").trim());

				cityBean.setCity(city);
				cityBean.setCountry(new CountryBean(idCountry));
				cityBean = cityBo.guardar(cityBean);

				resultado.put("estatus", "success");
				resultado.put("datos", cityBean);

				out.print(gson.toJson(resultado));
			} catch (InternalException ex) {
				resultado.put("estatus", "error");
				resultado.put("mensaje", ex.getMessage());

				out.print(gson.toJson(resultado));
			}
		}

	}

}
