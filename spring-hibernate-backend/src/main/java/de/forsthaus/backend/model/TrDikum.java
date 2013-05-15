package de.forsthaus.backend.model;

import java.util.Date;

public class TrDikum {
	private int id;
	private int version;
	
	private String nip;
	private String kodeTpu;
	private String kodeFak;
	private String kodeJur;
	private String numJur;
	private String numSek;
	private String tempat;
	private String numKepSek;
	private String numSttb;
	private String tSttb;
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
	public String getKodeTpu() {
		return kodeTpu;
	}
	public void setKodeTpu(String kodeTpu) {
		this.kodeTpu = kodeTpu;
	}
	public String getKodeFak() {
		return kodeFak;
	}
	public void setKodeFak(String kodeFak) {
		this.kodeFak = kodeFak;
	}
	public String getKodeJur() {
		return kodeJur;
	}
	public void setKodeJur(String kodeJur) {
		this.kodeJur = kodeJur;
	}
	public String getNumJur() {
		return numJur;
	}
	public void setNumJur(String numJur) {
		this.numJur = numJur;
	}
	public String getNumSek() {
		return numSek;
	}
	public void setNumSek(String numSek) {
		this.numSek = numSek;
	}
	public String getTempat() {
		return tempat;
	}
	public void setTempat(String tempat) {
		this.tempat = tempat;
	}
	public String getNumKepSek() {
		return numKepSek;
	}
	public void setNumKepSek(String numKepSek) {
		this.numKepSek = numKepSek;
	}
	public String getNumSttb() {
		return numSttb;
	}
	public void setNumSttb(String numSttb) {
		this.numSttb = numSttb;
	}
	public String getTglSttb() {
		return tSttb;
	}
	public void setTglSttb(String tSttb) {
		this.tSttb = tSttb;
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
