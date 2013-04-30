package de.forsthaus.backend.model;

import java.util.Date;

public class TrKaryaTulis {
	private int id;
	private int version;
	
	private String nip;
	private String jenis;
	private String judul;
	private String tahun;
	private String ket;
	
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
	public String getJenis() {
		return jenis;
	}
	public void setJenis(String jenis) {
		this.jenis = jenis;
	}
	public String getJudul() {
		return judul;
	}
	public void setJudul(String judul) {
		this.judul = judul;
	}
	public String getTahun() {
		return tahun;
	}
	public void setTahun(String tahun) {
		this.tahun = tahun;
	}
	public String getKet() {
		return ket;
	}
	public void setKet(String ket) {
		this.ket = ket;
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
