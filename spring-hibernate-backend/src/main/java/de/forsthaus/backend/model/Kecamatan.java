package de.forsthaus.backend.model;

public class Kecamatan {
	private int id;
	private int version;
	
	private String value;
	private Kota kota;
	
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
	public Kota getKota() {
		return kota;
	}
	public void setKota(Kota kota) {
		this.kota = kota;
	}
}
