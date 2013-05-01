package de.forsthaus.backend.model;

import java.util.Date;

public class TrOrganisasi {
	private int id;
	private int version;
	
	private String nip;
	private String kJnsOrg;
	private String nOrganisasi;
	private String jabOrg;
	private String pimpinan;
	private String tempat;
	private String tMulai;
	private String tAkhir;
	
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
	public String getkJnsOrg() {
		return kJnsOrg;
	}
	public void setkJnsOrg(String kJnsOrg) {
		this.kJnsOrg = kJnsOrg;
	}
	public String getnOrganisasi() {
		return nOrganisasi;
	}
	public void setnOrganisasi(String nOrganisasi) {
		this.nOrganisasi = nOrganisasi;
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
	public String gettMulai() {
		return tMulai;
	}
	public void settMulai(String tMulai) {
		this.tMulai = tMulai;
	}
	public String gettAkhir() {
		return tAkhir;
	}
	public void settAkhir(String tAkhir) {
		this.tAkhir = tAkhir;
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
