package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Wilayah implements java.io.Serializable, Entity {

	private static final long serialVersionUID = 8877635564725171638L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	private String kodeWilayah = "";
	private String namaWilayah = "";
	private String ibukota = "";
	private String kinsduk = "";
	private String tipeWilayah = "";
	private String kdLokker = "";

	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public Wilayah() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	/**
	 * EN: Hibernate version field. Do not touch this!.<br>
	 * DE: Hibernate Versions Info. Bitte nicht benutzen!<br>
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * EN: Hibernate version field. Do not touch this!.<br>
	 * DE: Hibernate Versions Info. Bitte nicht benutzen!<br>
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	
	public String getKodeWilayah() {
		return kodeWilayah;
	}

	public void setKodeWilayah(String kodeWilayah) {
		this.kodeWilayah = kodeWilayah;
	}

	public String getNamaWilayah() {
		return namaWilayah;
	}

	public void setNamaWilayah(String namaWilayah) {
		this.namaWilayah = namaWilayah;
	}

	public String getIbukota() {
		return ibukota;
	}

	public void setIbukota(String ibukota) {
		this.ibukota = ibukota;
	}

	public String getKinsduk() {
		return kinsduk;
	}

	public void setKinsduk(String kinsduk) {
		this.kinsduk = kinsduk;
	}

	public String getTipeWilayah() {
		return tipeWilayah;
	}

	public void setTipeWilayah(String twil) {
		this.tipeWilayah = twil;
	}

	public String getKdLokker() {
		return kdLokker;
	}

	public void setKdLokker(String kdLokker) {
		this.kdLokker = kdLokker;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(Wilayah wilayah) {
		return getId() == wilayah.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Wilayah) {
			Wilayah wilayah = (Wilayah) obj;
			return equals(wilayah);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
