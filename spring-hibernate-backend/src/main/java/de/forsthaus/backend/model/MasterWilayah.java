package de.forsthaus.backend.model;

public class MasterWilayah {
	private int id;
	private int version;
	
	private String kWil;
	private String nWil;
	private String ibukota;
	private String kInsDuk;
	private String kd_lokker;
	
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
	public String getkWil() {
		return kWil;
	}
	public void setkWil(String kWil) {
		this.kWil = kWil;
	}
	public String getnWil() {
		return nWil;
	}
	public void setnWil(String nWil) {
		this.nWil = nWil;
	}
	public String getIbukota() {
		return ibukota;
	}
	public void setIbukota(String ibukota) {
		this.ibukota = ibukota;
	}
	public String getkInsDuk() {
		return kInsDuk;
	}
	public void setkInsDuk(String kInsDuk) {
		this.kInsDuk = kInsDuk;
	}
	public String getKd_lokker() {
		return kd_lokker;
	}
	public void setKd_lokker(String kd_lokker) {
		this.kd_lokker = kd_lokker;
	}
}
