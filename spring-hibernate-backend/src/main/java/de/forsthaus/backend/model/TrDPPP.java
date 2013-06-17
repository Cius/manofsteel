package de.forsthaus.backend.model;

import java.util.Date;

public class TrDPPP {
	private int id;
	private int version;
	
	private String nip;
	private String penilai;
	private String atasan;
	private String thNilai;
	
	private int numSetia;
	private int numPres;
	private int numTangJawab;
	private int numTaat;
	private int numJujur;
	private int numKerja;
	private int numPkarsa;
	private int numPimpin;
	private int numTotal;
	private int numRata;
	
	private String userEnt;
	private Date tglEnt;
	private String userEdt;
	private Date tglEdt;
	
	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}
	
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
	public int getNumSetia() {
		return numSetia;
	}
	public void setNumSetia(int numSetia) {
		this.numSetia = numSetia;
	}
	public int getNumPres() {
		return numPres;
	}
	public void setNumPres(int numPres) {
		this.numPres = numPres;
	}
	public int getNumTaat() {
		return numTaat;
	}
	public void setNumTaat(int numTaat) {
		this.numTaat = numTaat;
	}
	public int getNumJujur() {
		return numJujur;
	}
	public void setNumJujur(int numJujur) {
		this.numJujur = numJujur;
	}
	public int getNumKerja() {
		return numKerja;
	}
	public void setNumKerja(int numKerja) {
		this.numKerja = numKerja;
	}
	public int getNumPkarsa() {
		return numPkarsa;
	}
	public void setNumPkarsa(int numPkarsa) {
		this.numPkarsa = numPkarsa;
	}
	public int getNumPimpin() {
		return numPimpin;
	}
	public void setNumPimpin(int numPimpin) {
		this.numPimpin = numPimpin;
	}
	public int getNumTotal() {
		return numTotal;
	}
	public void setNumTotal(int numTotal) {
		this.numTotal = numTotal;
	}
	public int getNumRata() {
		return numRata;
	}
	public void setNumRata(int numRata) {
		this.numRata = numRata;
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
	public int getNumTangJawab() {
		return numTangJawab;
	}
	public void setNumTangJawab(int numTangJawab) {
		this.numTangJawab = numTangJawab;
	}
}
