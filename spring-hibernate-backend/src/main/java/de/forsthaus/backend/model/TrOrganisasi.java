package de.forsthaus.backend.model;

import java.util.Date;

public class TrOrganisasi {
	private int id;
	private int version;
	
	private String nip;
	private String kodeJnsOrg;
	private String numOrganisasi;
	private String jabOrg;
	private String pimpinan;
	private String tempat;
	private String tMulai;
	private String tAkhir;
	
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
	public String getKodeJnsOrg() {
		return kodeJnsOrg;
	}
	public void setKodeJnsOrg(String kodeJnsOrg) {
		this.kodeJnsOrg = kodeJnsOrg;
	}
	public String getNumOrganisasi() {
		return numOrganisasi;
	}
	public void setNumOrganisasi(String numOrganisasi) {
		this.numOrganisasi = numOrganisasi;
	}
	public String getJabOrg() {
		return jabOrg;
	}
	public void setJabOrg(String jabOrg) {
		this.jabOrg = jabOrg;
	}
	public String getPimpinan() {
		return pimpinan;
	}
	public void setPimpinan(String pimpinan) {
		this.pimpinan = pimpinan;
	}
	public String getTempat() {
		return tempat;
	}
	public void setTempat(String tempat) {
		this.tempat = tempat;
	}
	public String getTglMulai() {
		return tMulai;
	}
	public void setTglMulai(String tMulai) {
		this.tMulai = tMulai;
	}
	public String getTglAkhir() {
		return tAkhir;
	}
	public void setTglAkhir(String tAkhir) {
		this.tAkhir = tAkhir;
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
	
	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}
	
	
}
