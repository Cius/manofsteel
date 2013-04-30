package de.forsthaus.backend.model;

import java.util.Date;

public class TrDikum {
	private int id;
	private int version;
	
	private String nip;
	private String kTpu;
	private String kFak;
	private String kJur;
	private String nJur;
	private String nSek;
	private String tempat;
	private String nKepSek;
	private String nSttb;
	private String tSttb;
	private String starec;
	
	private String userEnt;
	private Date tgEnt;
	private String userEdt;
	private Date tgEdt;
	
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
	public String getkTpu() {
		return kTpu;
	}
	public void setkTpu(String kTpu) {
		this.kTpu = kTpu;
	}
	public String getkFak() {
		return kFak;
	}
	public void setkFak(String kFak) {
		this.kFak = kFak;
	}
	public String getkJur() {
		return kJur;
	}
	public void setkJur(String kJur) {
		this.kJur = kJur;
	}
	public String getnJur() {
		return nJur;
	}
	public void setnJur(String nJur) {
		this.nJur = nJur;
	}
	public String getnSek() {
		return nSek;
	}
	public void setnSek(String nSek) {
		this.nSek = nSek;
	}
	public String getTempat() {
		return tempat;
	}
	public void setTempat(String tempat) {
		this.tempat = tempat;
	}
	public String getnKepSek() {
		return nKepSek;
	}
	public void setnKepSek(String nKepSek) {
		this.nKepSek = nKepSek;
	}
	public String getnSttb() {
		return nSttb;
	}
	public void setnSttb(String nSttb) {
		this.nSttb = nSttb;
	}
	public String gettSttb() {
		return tSttb;
	}
	public void settSttb(String tSttb) {
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
	public Date getTgEnt() {
		return tgEnt;
	}
	public void setTgEnt(Date tgEnt) {
		this.tgEnt = tgEnt;
	}
	public String getUserEdt() {
		return userEdt;
	}
	public void setUserEdt(String userEdt) {
		this.userEdt = userEdt;
	}
	public Date getTgEdt() {
		return tgEdt;
	}
	public void setTgEdt(Date tgEdt) {
		this.tgEdt = tgEdt;
	}
}
