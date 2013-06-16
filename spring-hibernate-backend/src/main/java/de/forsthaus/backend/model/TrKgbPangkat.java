package de.forsthaus.backend.model;

import java.util.Date;

public class TrKgbPangkat {
	private int id;
	private int version;
	
	private String nip;
	private String kodeJenis;
	private String kodePej;
	private String numSkPang;
	private String tSkPang;
	private String kodeGolRu;
	private String tmt;
	private String kodeKenpa;
	private String thKerja;
	private String blKerja;
	private String gaji;
	private String kodeWilGaji;
	
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
	public String getKodeJenis() {
		return kodeJenis;
	}
	public void setKodeJenis(String kodeJenis) {
		this.kodeJenis = kodeJenis;
	}
	public String getKodePej() {
		return kodePej;
	}
	public void setKodePej(String kodePej) {
		this.kodePej = kodePej;
	}
	public String getNumSkPang() {
		return numSkPang;
	}
	public void setNumSkPang(String numSkPang) {
		this.numSkPang = numSkPang;
	}
	public String getTglSkPang() {
		return tSkPang;
	}
	public void setTglSkPang(String tSkPang) {
		this.tSkPang = tSkPang;
	}
	public String getKodeGolRu() {
		return kodeGolRu;
	}
	public void setKodeGolRu(String kodeGolRu) {
		this.kodeGolRu = kodeGolRu;
	}
	public String getTmt() {
		return tmt;
	}
	public void setTmt(String tmt) {
		this.tmt = tmt;
	}
	public String getKodeKenpa() {
		return kodeKenpa;
	}
	public void setKodeKenpa(String kodeKenpa) {
		this.kodeKenpa = kodeKenpa;
	}
	public String getThKerja() {
		return thKerja;
	}
	public void setThKerja(String thKerja) {
		this.thKerja = thKerja;
	}
	public String getBlKerja() {
		return blKerja;
	}
	public void setBlKerja(String blKerja) {
		this.blKerja = blKerja;
	}
	public String getGaji() {
		return gaji;
	}
	public void setGaji(String gaji) {
		this.gaji = gaji;
	}
	public String getKodeWilGaji() {
		return kodeWilGaji;
	}
	public void setKodeWilGaji(String kodeWilGaji) {
		this.kodeWilGaji = kodeWilGaji;
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
