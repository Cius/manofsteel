package de.forsthaus.backend.model;

import java.util.Date;

public class TrDPPP {
	private int id;
	private int version;
	
	private String nip;
	private String penilai;
	private String atasan;
	private String thNilai;
	
	private int nSetia;
	private int nPres;
	private int nTaat;
	private int nJujur;
	private int nKerja;
	private int nPkarsa;
	private int nPimpin;
	private int nTotal;
	private int nRata;
	
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
	public String getPenilai() {
		return penilai;
	}
	public void setPenilai(String penilai) {
		this.penilai = penilai;
	}
	public String getAtasan() {
		return atasan;
	}
	public void setAtasan(String atasan) {
		this.atasan = atasan;
	}
	public String getThNilai() {
		return thNilai;
	}
	public void setThNilai(String thNilai) {
		this.thNilai = thNilai;
	}
	public int getnSetia() {
		return nSetia;
	}
	public void setnSetia(int nSetia) {
		this.nSetia = nSetia;
	}
	public int getnPres() {
		return nPres;
	}
	public void setnPres(int nPres) {
		this.nPres = nPres;
	}
	public int getnTaat() {
		return nTaat;
	}
	public void setnTaat(int nTaat) {
		this.nTaat = nTaat;
	}
	public int getnJujur() {
		return nJujur;
	}
	public void setnJujur(int nJujur) {
		this.nJujur = nJujur;
	}
	public int getnKerja() {
		return nKerja;
	}
	public void setnKerja(int nKerja) {
		this.nKerja = nKerja;
	}
	public int getnPkarsa() {
		return nPkarsa;
	}
	public void setnPkarsa(int nPkarsa) {
		this.nPkarsa = nPkarsa;
	}
	public int getnPimpin() {
		return nPimpin;
	}
	public void setnPimpin(int nPimpin) {
		this.nPimpin = nPimpin;
	}
	public int getnTotal() {
		return nTotal;
	}
	public void setnTotal(int nTotal) {
		this.nTotal = nTotal;
	}
	public int getnRata() {
		return nRata;
	}
	public void setnRata(int nRata) {
		this.nRata = nRata;
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
