package de.forsthaus.backend.model;

public class MasterEselon {
	private int id;
	private int version;
	
	private String kEselon;
	private String nEselon;
	private String gAwal;
	private String gAkhir;
	private String tunJab;
	private int bup;
	private String insentif;
	
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
	public String getkEselon() {
		return kEselon;
	}
	public void setkEselon(String kEselon) {
		this.kEselon = kEselon;
	}
	public String getnEselon() {
		return nEselon;
	}
	public void setnEselon(String nEselon) {
		this.nEselon = nEselon;
	}
	public String getgAwal() {
		return gAwal;
	}
	public void setgAwal(String gAwal) {
		this.gAwal = gAwal;
	}
	public String getgAkhir() {
		return gAkhir;
	}
	public void setgAkhir(String gAkhir) {
		this.gAkhir = gAkhir;
	}
	public String getTunJab() {
		return tunJab;
	}
	public void setTunJab(String tunJab) {
		this.tunJab = tunJab;
	}
	public int getBup() {
		return bup;
	}
	public void setBup(int bup) {
		this.bup = bup;
	}
	public String getInsentif() {
		return insentif;
	}
	public void setInsentif(String insentif) {
		this.insentif = insentif;
	}
	
}
