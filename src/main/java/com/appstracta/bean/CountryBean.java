package com.appstracta.bean;

import java.io.Serializable;
import java.util.Date;

public class CountryBean implements Serializable {

	private static final long serialVersionUID = 8026220459167365031L;
	private Integer countryId;
	private String country;
	private Date lastUpdate;

	public CountryBean() {
	}

	public CountryBean(Integer countryId) {
		this.countryId = countryId;
	}

	public CountryBean(Integer countryId, String country, Date lastUpdate) {
		this.countryId = countryId;
		this.country = country;
		this.lastUpdate = lastUpdate;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "CountryBean{" +
				"countryId=" + countryId +
				", country='" + country + '\'' +
				", lastUpdate=" + lastUpdate +
				'}';
	}

}
