package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Gabungan implements java.io.Serializable, Entity {
	
	private static final long serialVersionUID = 1769511865007424105L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	
	private String kodeTabel;
	private String kode;
	private String Nama;
	
	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public Gabungan() {
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
	
	public String getKodeTabel() {
		return kodeTabel;
	}

	public void setKodeTabel(String kodeTabel) {
		this.kodeTabel = kodeTabel;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getNama() {
		return Nama;
	}

	public void setNama(String nama) {
		Nama = nama;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(Gabungan gr) {
		return getId() == gr.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Gabungan) {
			Gabungan gp = (Gabungan) obj;
			return equals(gp);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
