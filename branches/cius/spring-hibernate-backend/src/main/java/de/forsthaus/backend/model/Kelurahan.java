package de.forsthaus.backend.model;

public class Kelurahan {
	private int id;
	private int version;
	
	private String value;
	private Kecamatan kec;
	
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
	public Kecamatan getKec() {
		return kec;
	}
	public void setKec(Kecamatan kec) {
		this.kec = kec;
	}
}
