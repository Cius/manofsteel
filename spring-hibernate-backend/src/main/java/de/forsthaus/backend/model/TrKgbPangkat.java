package de.forsthaus.backend.model;

import java.util.Date;

public class TrKgbPangkat {
	private int id;
	private int version;
	
	private String nip;
	private String kJenis;
	private String kPej;
	private String nSkPang;
	private String tSkPang;
	private String kGolRu;
	private String tmt;
	private String kKenpa;
	private String thKerja;
	private String blKerja;
	private String gaji;
	private String kWilGaji;
	
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
	public String getkJenis() {
		return kJenis;
	}
	public void setkJenis(String kJenis) {
		this.kJenis = kJenis;
	}
	public String getkPej() {
		return kPej;
	}
	public void setkPej(String kPej) {
		this.kPej = kPej;
	}
	public String getnSkPang() {
		return nSkPang;
	}
	public void setnSkPang(String nSkPang) {
		this.nSkPang = nSkPang;
	}
	public String gettSkPang() {
		return tSkPang;
	}
	public void settSkPang(String tSkPang) {
		this.tSkPang = tSkPang;
	}
	public String getkGolRu() {
		return kGolRu;
	}
	public void setkGolRu(String kGolRu) {
		this.kGolRu = kGolRu;
	}
	public String getTmt() {
		return tmt;
	}
	public void setTmt(String tmt) {
		this.tmt = tmt;
	}
	public String getkKenpa() {
		return kKenpa;
	}
	public void setkKenpa(String kKenpa) {
		this.kKenpa = kKenpa;
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
	public String getkWilGaji() {
		return kWilGaji;
	}
	public void setkWilGaji(String kWilGaji) {
		this.kWilGaji = kWilGaji;
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
