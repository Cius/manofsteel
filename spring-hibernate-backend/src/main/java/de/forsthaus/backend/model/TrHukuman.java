package de.forsthaus.backend.model;

import java.util.Date;

public class TrHukuman {
	private int id;
	private int version;
	
	private String nip;
	private String tkHukum;
	private String jnsHukum;
	private String kodePej;
	private String numSkHukum;
	private String tSkHukum;
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
	public String getTkHukum() {
		return tkHukum;
	}
	public void setTkHukum(String tkHukum) {
		this.tkHukum = tkHukum;
	}
	public String getJnsHukum() {
		return jnsHukum;
	}
	public void setJnsHukum(String jnsHukum) {
		this.jnsHukum = jnsHukum;
	}
	public String getKodePej() {
		return kodePej;
	}
	public void setKodePej(String kodePej) {
		this.kodePej = kodePej;
	}
	public String getNumSkHukum() {
		return numSkHukum;
	}
	public void setNumSkHukum(String numSkHukum) {
		this.numSkHukum = numSkHukum;
	}
	public String getTglSkHukum() {
		return tSkHukum;
	}
	public void setTglSkHukum(String tSkHukum) {
		this.tSkHukum = tSkHukum;
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
	
	
}
