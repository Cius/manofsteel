package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Eselon {
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	
	private String kEselon;
	private String nEselon;
	private GolonganRuang gAwal;
	private GolonganRuang gAkhir;
	private Double tunJab;
	private Integer bup;
	private Double insentif;
	
	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public GolonganRuang getgAwal() {
		return gAwal;
	}
	public void setgAwal(GolonganRuang gAwal) {
		this.gAwal = gAwal;
	}
	public GolonganRuang getgAkhir() {
		return gAkhir;
	}
	public void setgAkhir(GolonganRuang gAkhir) {
		this.gAkhir = gAkhir;
	}
	public Double getTunJab() {
		return tunJab;
	}
	public void setTunJab(Double tunJab) {
		this.tunJab = tunJab;
	}
	public Integer getBup() {
		return bup;
	}
	public void setBup(Integer bup) {
		this.bup = bup;
	}
	public Double getInsentif() {
		return insentif;
	}
	public void setInsentif(Double insentif) {
		this.insentif = insentif;
	}
	
	public boolean equals(Eselon gr) {
		return getId() == gr.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Eselon) {
			Eselon gp = (Eselon) obj;
			return equals(gp);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}
