package de.forsthaus.backend.model;

import java.util.Date;

public class MasterUnitKerja {
	private int id;
	private int version;
	
	private String kinsker;
	private String kunker;
	private String nunker;
	
	private String alamat;
	private String kota;
	private String telp;
	private String tUnit;
	private String kLokasi;
	private String kEselon;
	private String kPok;
	private String nPok;
	
	private String bagan;
	private String noPerda;
	private Date tgPerda;
	private String perdadari;
	
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
	public String getKinsker() {
		return kinsker;
	}
	public void setKinsker(String kinsker) {
		this.kinsker = kinsker;
	}
	public String getKunker() {
		return kunker;
	}
	public void setKunker(String kunker) {
		this.kunker = kunker;
	}
	public String getNunker() {
		return nunker;
	}
	public void setNunker(String nunker) {
		this.nunker = nunker;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public String getKota() {
		return kota;
	}
	public void setKota(String kota) {
		this.kota = kota;
	}
	public String getTelp() {
		return telp;
	}
	public void setTelp(String telp) {
		this.telp = telp;
	}
	public String gettUnit() {
		return tUnit;
	}
	public void settUnit(String tUnit) {
		this.tUnit = tUnit;
	}
	public String getkLokasi() {
		return kLokasi;
	}
	public void setkLokasi(String kLokasi) {
		this.kLokasi = kLokasi;
	}
	public String getkEselon() {
		return kEselon;
	}
	public void setkEselon(String kEselon) {
		this.kEselon = kEselon;
	}
	public String getkPok() {
		return kPok;
	}
	public void setkPok(String kPok) {
		this.kPok = kPok;
	}
	public String getnPok() {
		return nPok;
	}
	public void setnPok(String nPok) {
		this.nPok = nPok;
	}
	public String getBagan() {
		return bagan;
	}
	public void setBagan(String bagan) {
		this.bagan = bagan;
	}
	public String getNoPerda() {
		return noPerda;
	}
	public void setNoPerda(String noPerda) {
		this.noPerda = noPerda;
	}
	public Date getTgPerda() {
		return tgPerda;
	}
	public void setTgPerda(Date tgPerda) {
		this.tgPerda = tgPerda;
	}
	public String getPerdadari() {
		return perdadari;
	}
	public void setPerdadari(String perdadari) {
		this.perdadari = perdadari;
	}
}
