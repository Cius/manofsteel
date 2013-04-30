package de.forsthaus.backend.model;

import java.util.Date;

public class KgbPangkat {
	private int id;
	private int version;
	
	private MasterGabungan pejabat;
	private String sk;
	private Date tgl;
	private MasterGabungan golongan;
	private Date tmtPangkat;
	private int masaKerjaTahun;
	private int masaKerjaBulan;
	private int gajiPokok;
	private MasterGabungan jenisNaik;
	
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
	public String getSk() {
		return sk;
	}
	public void setSk(String sk) {
		this.sk = sk;
	}
	public Date getTgl() {
		return tgl;
	}
	public void setTgl(Date tgl) {
		this.tgl = tgl;
	}
	public MasterGabungan getGolongan() {
		return golongan;
	}
	public void setGolongan(MasterGabungan golongan) {
		this.golongan = golongan;
	}
	public Date getTmtPangkat() {
		return tmtPangkat;
	}
	public void setTmtPangkat(Date tmtPangkat) {
		this.tmtPangkat = tmtPangkat;
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
	public int getGajiPokok() {
		return gajiPokok;
	}
	public void setGajiPokok(int gajiPokok) {
		this.gajiPokok = gajiPokok;
	}
	public MasterGabungan getJenisNaik() {
		return jenisNaik;
	}
	public void setJenisNaik(MasterGabungan jenisNaik) {
		this.jenisNaik = jenisNaik;
	}
}
