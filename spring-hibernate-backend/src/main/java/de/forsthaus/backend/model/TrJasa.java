package de.forsthaus.backend.model;

import java.util.Date;

public class TrJasa {
	private int id;
	private int version;
	
	private String nip;
	private String kodeBintang;
	private String numBintang;
	private String pemberi;
	
	private String noSkJasa;
	private String tgSkJasa;
	private String thJasa;
	private String starec;
	
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
	public String getKodeBintang() {
		return kodeBintang;
	}
	public void setKodeBintang(String kodeBintang) {
		this.kodeBintang = kodeBintang;
	}
	public String getNumBintang() {
		return numBintang;
	}
	public void setNumBintang(String numBintang) {
		this.numBintang = numBintang;
	}
	public String getPemberi() {
		return pemberi;
	}
	public void setPemberi(String pemberi) {
		this.pemberi = pemberi;
	}
	public String getNoSkJasa() {
		return noSkJasa;
	}
	public void setNoSkJasa(String noSkJasa) {
		this.noSkJasa = noSkJasa;
	}
	public String getTgSkJasa() {
		return tgSkJasa;
	}
	public void setTgSkJasa(String tgSkJasa) {
		this.tgSkJasa = tgSkJasa;
	}
	public String getThJasa() {
		return thJasa;
	}
	public void setThJasa(String thJasa) {
		this.thJasa = thJasa;
	}
	public String getStarec() {
		return starec;
	}
	public void setStarec(String starec) {
		this.starec = starec;
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
