package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GolonganRuang implements java.io.Serializable, Entity {
	
	private static final long serialVersionUID = 8107856056135597372L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	
	private String kodeJenisGolongan = "1";
	private String kodeGolonganRuang = "";
	private String namaGolonganRuang = "";
	private String pangkat = "";
	private String pangkatGolonganRuang = "";

	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public GolonganRuang() {
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

	public String getKodeJenisGolongan() {
		return kodeJenisGolongan;
	}

	public void setKodeJenisGolongan(String kodeJenisGolongan) {
		this.kodeJenisGolongan = kodeJenisGolongan;
	}

	public String getKodeGolonganRuang() {
		return kodeGolonganRuang;
	}

	public void setKodeGolonganRuang(String kodeGolonganRuang) {
		this.kodeGolonganRuang = kodeGolonganRuang;
	}

	public String getNamaGolonganRuang() {
		return namaGolonganRuang;
	}

	public void setNamaGolonganRuang(String namaGolonganRuang) {
		this.namaGolonganRuang = namaGolonganRuang;
	}

	public String getPangkat() {
		return pangkat;
	}

	public void setPangkat(String pangkat) {
		this.pangkat = pangkat;
	}

	public String getPangkatGolonganRuang() {
		return pangkatGolonganRuang;
	}

	public void setPangkatGolonganRuang(String pangkatGolonganRuang) {
		this.pangkatGolonganRuang = pangkatGolonganRuang;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(GolonganRuang gr) {
		return getId() == gr.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GolonganRuang) {
			GolonganRuang wilayah = (GolonganRuang) obj;
			return equals(wilayah);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
