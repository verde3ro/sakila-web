package com.appstracta.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {

	private Connection connection;

	public void connectar() {
		try {
			// Conexion a través JNDI
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/dsSakila");

			this.connection = ds.getConnection();
		} catch (SQLException | NamingException ex) {
			ex.printStackTrace();
		}
	}

	public void cerrar() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException ex ) {
				ex.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

}
