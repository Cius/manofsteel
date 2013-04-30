package de.forsthaus.backend.model;

import java.util.Date;

public class TrDuk {
	private int id;
	private int version;
	
	private String nip;
	private String tahun;
	private String dukIns;
	private String dukUnit;
	
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
	public String getTahun() {
		return tahun;
	}
	public void setTahun(String tahun) {
		this.tahun = tahun;
	}
	public String getDukIns() {
		return dukIns;
	}
	public void setDukIns(String dukIns) {
		this.dukIns = dukIns;
	}
	public String getDukUnit() {
		return dukUnit;
	}
	public void setDukUnit(String dukUnit) {
		this.dukUnit = dukUnit;
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
