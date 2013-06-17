package de.forsthaus.backend.model;

import java.util.Date;

public class TrMutasi {
	private int id;
	private int version;
	
	private String nip;
	private String nama;
	private String kodeUnKer;
	private String kodeGolRu;
	
	private String tmt;
	private String kodeJenisMutasi;
	private String kodePejabat;
	private String noSk;
	private String tgSk;
	private String kodeInsKer;
	
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

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getKodeUnKer() {
		return kodeUnKer;
	}

	public void setKodeUnKer(String kodeUnKer) {
		this.kodeUnKer = kodeUnKer;
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

	public String getKodeJenisMutasi() {
		return kodeJenisMutasi;
	}

	public void setKodeJenisMutasi(String kodeJenisMutasi) {
		this.kodeJenisMutasi = kodeJenisMutasi;
	}

	public String getKodePejabat() {
		return kodePejabat;
	}

	public void setKodePejabat(String kodePejabat) {
		this.kodePejabat = kodePejabat;
	}

	public String getNoSk() {
		return noSk;
	}

	public void setNoSk(String noSk) {
		this.noSk = noSk;
	}

	public String getTgSk() {
		return tgSk;
	}

	public void setTgSk(String tgSk) {
		this.tgSk = tgSk;
	}

	public String getKodeInsKer() {
		return kodeInsKer;
	}

	public void setKodeInsKer(String kodeInsKer) {
		this.kodeInsKer = kodeInsKer;
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
