package de.forsthaus.backend.model;

import java.util.Date;

public class TrBahasa {
	private int id;
	private int version;
	
	private String nip;
	private String nBahasa;
	private String kodeBahasa;
	private String jBahasa;
	private String userEnt;
	private Date tglEnt;
	private String userEdt;
	private Date tglEdt;
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
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getnBahasa() {
		return nBahasa;
	}
	public void setnBahasa(String nBahasa) {
		this.nBahasa = nBahasa;
	}
	public String getKodeBahasa() {
		return kodeBahasa;
	}
	public void setKodeBahasa(String kodeBahasa) {
		this.kodeBahasa = kodeBahasa;
	}
	public String getjBahasa() {
		return jBahasa;
	}
	public void setjBahasa(String jBahasa) {
		this.jBahasa = jBahasa;
	}
	public String getUserEnt() {
		return userEnt;
	}
	public void setUserEnt(String userEnt) {
		this.userEnt = userEnt;
	}
	public Date getTglEnt() {
		return tglEnt;
	}
	public void setTglEnt(Date tglEnt) {
		this.tglEnt = tglEnt;
	}
	public String getUserEdt() {
		return userEdt;
	}
	public void setUserEdt(String userEdt) {
		this.userEdt = userEdt;
	}
	public Date getTglEdt() {
		return tglEdt;
	}
	public void setTglEdt(Date tglEdt) {
		this.tglEdt = tglEdt;
	}
	
	
}
