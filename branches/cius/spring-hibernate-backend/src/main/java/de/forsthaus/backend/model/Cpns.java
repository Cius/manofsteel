package de.forsthaus.backend.model;

import java.util.Date;

public class Cpns {
	private int id;
	private int version;
	
	private MasterGabungan pejabat;
	private String noSK;
	private Date tglSK;
	private MasterGabungan gol;
	
	private Date tmtCpns;
	private MasterGabungan pendMasuk;
	private Date tmtTugas;
	private int masaKerjaTahun;
	private int masaKerjaBulan;
	private String latPraJabatan;
	private int tahunPraJabatan;
	
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
	public MasterGabungan getPejabat() {
		return pejabat;
	}
	public void setPejabat(MasterGabungan pejabat) {
		this.pejabat = pejabat;
	}
	public String getNoSK() {
		return noSK;
	}
	public void setNoSK(String noSK) {
		this.noSK = noSK;
	}
	public Date getTglSK() {
		return tglSK;
	}
	public void setTglSK(Date tglSK) {
		this.tglSK = tglSK;
	}
	public MasterGabungan getGol() {
		return gol;
	}
	public void setGol(MasterGabungan gol) {
		this.gol = gol;
	}
	public Date getTmtCpns() {
		return tmtCpns;
	}
	public void setTmtCpns(Date tmtCpns) {
		this.tmtCpns = tmtCpns;
	}
	public MasterGabungan getPendMasuk() {
		return pendMasuk;
	}
	public void setPendMasuk(MasterGabungan pendMasuk) {
		this.pendMasuk = pendMasuk;
	}
	public Date getTmtTugas() {
		return tmtTugas;
	}
	public void setTmtTugas(Date tmtTugas) {
		this.tmtTugas = tmtTugas;
	}
	public int getMasaKerjaTahun() {
		return masaKerjaTahun;
	}
	public void setMasaKerjaTahun(int masaKerjaTahun) {
		this.masaKerjaTahun = masaKerjaTahun;
	}
	public int getMasaKerjaBulan() {
		return masaKerjaBulan;
	}
	public void setMasaKerjaBulan(int masaKerjaBulan) {
		this.masaKerjaBulan = masaKerjaBulan;
	}
	public String getLatPraJabatan() {
		return latPraJabatan;
	}
	public void setLatPraJabatan(String latPraJabatan) {
		this.latPraJabatan = latPraJabatan;
	}
	public int getTahunPraJabatan() {
		return tahunPraJabatan;
	}
	public void setTahunPraJabatan(int tahunPraJabatan) {
		this.tahunPraJabatan = tahunPraJabatan;
	}
}
