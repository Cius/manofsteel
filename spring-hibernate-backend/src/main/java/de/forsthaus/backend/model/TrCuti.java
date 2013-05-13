package de.forsthaus.backend.model;

import java.util.Date;

public class TrCuti {
	private int id;
	private int version;
	
	private String nip;
	private String kodeJnsCuti;
	private String kodePej;
	private String nSk;
	private String tSk;
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
	public String getKodeJnsCuti() {
		return kodeJnsCuti;
	}
	public void setKodeJnsCuti(String kodeJnsCuti) {
		this.kodeJnsCuti = kodeJnsCuti;
	}
	public String getKodePej() {
		return kodePej;
	}
	public void setKodePej(String kodePej) {
		this.kodePej = kodePej;
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
