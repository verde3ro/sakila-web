package com.appstracta.bean;

import java.io.Serializable;
import java.util.Date;

public class CityBean implements Serializable {

	private static final long serialVersionUID = 446231299383117017L;
	private Integer cityId;
	private String city;
	private Date lastUpdate;
	private CountryBean country;

	public CityBean() {
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public CountryBean getCountry() {
		return country;
	}

	public void setCountry(CountryBean country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "CityBean{" +
				"cityId=" + cityId +
				", city='" + city + '\'' +
				", lastUpdate=" + lastUpdate +
				", country=" + country +
				'}';
	}

}
