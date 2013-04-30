package de.forsthaus.backend.model;

import java.util.Date;

public class TrCuti {
	private int id;
	private int version;
	
	private String nip;
	private String kJnsCuti;
	private String kPej;
	private String nSk;
	private String tSk;
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
	public String getkJnsCuti() {
		return kJnsCuti;
	}
	public void setkJnsCuti(String kJnsCuti) {
		this.kJnsCuti = kJnsCuti;
	}
	public String getkPej() {
		return kPej;
	}
	public void setkPej(String kPej) {
		this.kPej = kPej;
	}
	public String getnSk() {
		return nSk;
	}
	public void setnSk(String nSk) {
		this.nSk = nSk;
	}
	public String gettSk() {
		return tSk;
	}
	public void settSk(String tSk) {
		this.tSk = tSk;
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
