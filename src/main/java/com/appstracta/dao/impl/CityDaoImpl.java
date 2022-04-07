package com.appstracta.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.appstracta.bean.CityBean;
import com.appstracta.bean.CountryBean;
import com.appstracta.dao.ICityDao;
import com.appstracta.exceptions.InternalException;
import com.appstracta.utils.Conexion;

public class CityDaoImpl implements ICityDao {

	private final Logger log = Logger.getLogger(getClass().getName());

	@Override
	public List<CityBean> obtenerTodos() throws InternalException {
		Conexion conexion = null;
		List<CityBean> ciudades = new ArrayList<>();
		String sql = "SELECT c1.city_id, "
				+ "         c1.city, "
				+ "         c1.last_update, "
				+ "         c2.country_id, "
				+ "         c2.country, "
				+ "         c2.last_update AS last_update_c2 "
				+ "FROM     country c2 "
				+ "INNER JOIN city c1 ON c2.country_id = c1.country_id ORDER BY c1.city_id";

		try {
			log.info("::::::::: Consulta ciudades ::::::: ");
			conexion = new Conexion();
			conexion.connectar();

			try(PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
				try(ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						CityBean city = new CityBean();

						city.setCityId(rs.getInt("city_id"));
						city.setCity(rs.getString("city"));
						city.setLastUpdate(rs.getDate("last_update"));
						city.setCountry(new CountryBean(rs.getInt("country_id"), rs.getString("country"), rs.getDate("last_update_c2")));

						ciudades.add(city);
					}
				}
			}

			return ciudades;
		} catch (Exception ex) {
			log.error("Ocurrió un error al traer los datos de las ciudades.", ex);
			throw new InternalException("Ocurrió un error al traer los datos de las ciudades.");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception e) {
					log.error("Ocurrió un error al cerrar la conexión de la base de datos.");
					throw new InternalException("Ocurrió un error al cerrar la conexión de la base de datos.");
				}
			}
		}
	}

	@Override
	public CityBean guardar(CityBean city) throws InternalException {
		Conexion conexion = null;

		try {
			conexion = new Conexion();
			conexion.connectar();
			String sql = "INSERT INTO city (city, country_id, last_update) VALUES (?, ?, ?)";

			try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, city.getCity());
				ps.setInt(2, city.getCountry().getCountryId());
				ps.setDate(3, new java.sql.Date(city.getLastUpdate().getTime()));
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();

				if (rs.next()) {
					city.setCityId(rs.getInt(1));
				}
			}

			return city;
		} catch (Exception ex) {
			log.error("Ocurrió un error al insertar los datos de la ciudad.", ex);
			throw new InternalException("Ocurrió un error al insertar los datos de la ciudad.");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception e) {
					log.error("Ocurrió un error al cerrar la conexión de la base de datos.");
					throw new InternalException("Ocurrió un error al cerrar la conexión de la base de datos.");
				}
			}
		}
	}

}
