package de.forsthaus.backend.model;


public class MasterJabFung {

	private int id;
	private int version;

	private String kodeJabFung;
	private String namaJabFung;
	private String golAwal;
	private String golAkhir;
	private String tunJab;
	private String bup;
	private String akredit;
	private String ktpu;
	private String status;
	
	public MasterJabFung() {
		super();
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

	public String getKodeJabFung() {
		return kodeJabFung;
	}

	public void setKodeJabFung(String kodeJabFung) {
		this.kodeJabFung = kodeJabFung;
	}

	public String getNamaJabFung() {
		return namaJabFung;
	}

	public void setNamaJabFung(String namaJabFung) {
		this.namaJabFung = namaJabFung;
	}

	public String getGolAwal() {
		return golAwal;
	}

	public void setGolAwal(String golAwal) {
		this.golAwal = golAwal;
	}

	public String getGolAkhir() {
		return golAkhir;
	}

	public void setGolAkhir(String golAkhir) {
		this.golAkhir = golAkhir;
	}

	public String getTunJab() {
		return tunJab;
	}

	public void setTunJab(String tunJab) {
		this.tunJab = tunJab;
	}

	public String getBup() {
		return bup;
	}

	public void setBup(String bup) {
		this.bup = bup;
	}

	public String getAkredit() {
		return akredit;
	}

	public void setAkredit(String akredit) {
		this.akredit = akredit;
	}

	public String getKtpu() {
		return ktpu;
	}

	public void setKtpu(String ktpu) {
		this.ktpu = ktpu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
