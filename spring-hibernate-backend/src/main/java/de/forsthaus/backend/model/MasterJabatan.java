package de.forsthaus.backend.model;

import java.util.Date;

public class MasterJabatan {
	private int id;
	private int version;
	
	private String kInsKer;
	private String kUnKer;
	private String jnsJab;
	private String kEselon;
	private String kJab;
	private String nJab;
	private String nip;
	private String nipPejLama;
	private Date tmt;
	private String jnsMutasi;
	
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
	public String getkInsKer() {
		return kInsKer;
	}
	public void setkInsKer(String kInsKer) {
		this.kInsKer = kInsKer;
	}
	public String getkUnKer() {
		return kUnKer;
	}
	public void setkUnKer(String kUnKer) {
		this.kUnKer = kUnKer;
	}
	public String getJnsJab() {
		return jnsJab;
	}
	public void setJnsJab(String jnsJab) {
		this.jnsJab = jnsJab;
	}
	public String getkEselon() {
		return kEselon;
	}
	public void setkEselon(String kEselon) {
		this.kEselon = kEselon;
	}
	public String getkJab() {
		return kJab;
	}
	public void setkJab(String kJab) {
		this.kJab = kJab;
	}
	public String getnJab() {
		return nJab;
	}
	public void setnJab(String nJab) {
		this.nJab = nJab;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getNipPejLama() {
		return nipPejLama;
	}
	public void setNipPejLama(String nipPejLama) {
		this.nipPejLama = nipPejLama;
	}
	public Date getTmt() {
		return tmt;
	}
	public void setTmt(Date tmt) {
		this.tmt = tmt;
	}
	public String getJnsMutasi() {
		return jnsMutasi;
	}
	public void setJnsMutasi(String jnsMutasi) {
		this.jnsMutasi = jnsMutasi;
	}
}
