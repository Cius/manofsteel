package de.forsthaus.backend.model;

import java.util.Date;

public class Pns {
	private int id;
	private int version;
	
	private MasterGabungan pejabat;
	private String sk;
	private Date tgl;
	
	private MasterGabungan pangkat;
	private String tmtPns;
	private boolean sumpah;
	
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
	public MasterGabungan getPangkat() {
		return pangkat;
	}
	public void setPangkat(MasterGabungan pangkat) {
		this.pangkat = pangkat;
	}
	public String getTmtPns() {
		return tmtPns;
	}
	public void setTmtPns(String tmtPns) {
		this.tmtPns = tmtPns;
	}
	public boolean isSumpah() {
		return sumpah;
	}
	public void setSumpah(boolean sumpah) {
		this.sumpah = sumpah;
	}
}
