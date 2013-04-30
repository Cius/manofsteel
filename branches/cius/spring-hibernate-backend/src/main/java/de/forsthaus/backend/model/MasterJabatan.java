package de.forsthaus.backend.model;

import java.util.Date;

public class MasterJabatan {
	private int id;
	private int version;
	
	private MasterGabungan pejabat;
	private String sk;
	private Date tgl;
	private MasterUnitKerja unKerja;
	private MasterGabungan jenisJabatan;
	private MasterEselon eselon;
	private MasterGabungan pokJabatan;
	private String namaJabatan;
	private Date tmtJabatan;
	
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
	public MasterUnitKerja getUnKerja() {
		return unKerja;
	}
	public void setUnKerja(MasterUnitKerja unKerja) {
		this.unKerja = unKerja;
	}
	public MasterGabungan getJenisJabatan() {
		return jenisJabatan;
	}
	public void setJenisJabatan(MasterGabungan jenisJabatan) {
		this.jenisJabatan = jenisJabatan;
	}
	public MasterEselon getEselon() {
		return eselon;
	}
	public void setEselon(MasterEselon eselon) {
		this.eselon = eselon;
	}
	public MasterGabungan getPokJabatan() {
		return pokJabatan;
	}
	public void setPokJabatan(MasterGabungan pokJabatan) {
		this.pokJabatan = pokJabatan;
	}
	public String getNamaJabatan() {
		return namaJabatan;
	}
	public void setNamaJabatan(String namaJabatan) {
		this.namaJabatan = namaJabatan;
	}
	public Date getTmtJabatan() {
		return tmtJabatan;
	}
	public void setTmtJabatan(Date tmtJabatan) {
		this.tmtJabatan = tmtJabatan;
	}
}
