package de.forsthaus.backend.model;

public class MasterGabungan {
	private int id;
	private int version;
	
	private String kTabel;
	private String kode;
	private String nama;
	
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
	public String getkTabel() {
		return kTabel;
	}
	public void setkTabel(String kTabel) {
		this.kTabel = kTabel;
	}
	public String getKode() {
		return kode;
	}
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	
}
