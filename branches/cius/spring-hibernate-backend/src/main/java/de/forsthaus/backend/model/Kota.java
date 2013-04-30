package de.forsthaus.backend.model;

public class Kota {
	private int id;
	private int version;
	
	private String value;
	private Provinsi prov;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Provinsi getProv() {
		return prov;
	}
	public void setProv(Provinsi prov) {
		this.prov = prov;
	}
}
