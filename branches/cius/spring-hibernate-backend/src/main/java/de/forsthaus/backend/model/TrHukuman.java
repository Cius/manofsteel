package de.forsthaus.backend.model;

import java.util.Date;

public class TrHukuman {
	private int id;
	private int version;
	
	private String nip;
	private String tkHukum;
	private String jnsHukum;
	private String kPej;
	private String nSkHukum;
	private String tSkHukum;
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
	public String getkPej() {
		return kPej;
	}
	public void setkPej(String kPej) {
		this.kPej = kPej;
	}
	public String getnSkHukum() {
		return nSkHukum;
	}
	public void setnSkHukum(String nSkHukum) {
		this.nSkHukum = nSkHukum;
	}
	public String gettSkHukum() {
		return tSkHukum;
	}
	public void settSkHukum(String tSkHukum) {
		this.tSkHukum = tSkHukum;
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
