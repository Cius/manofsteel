package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GajiPokok implements java.io.Serializable, Entity {
	
	private static final long serialVersionUID = 1769511865007424105L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	
	private GolonganRuang golonganRuang;
	private int masaKerja;
	public int getMasaKerja() {
		return masaKerja;
	}

	public GolonganRuang getGolonganRuang() {
		return golonganRuang;
	}

	public void setGolonganRuang(GolonganRuang golonganRuang) {
		this.golonganRuang = golonganRuang;
	}

	public void setMasaKerja(int masaKerja) {
		this.masaKerja = masaKerja;
	}

	public double getGajiPokokLama() {
		return gajiPokokLama;
	}

	public void setGajiPokokLama(double gajiPokokLama) {
		this.gajiPokokLama = gajiPokokLama;
	}

	public double getGajiPokokBaru() {
		return gajiPokokBaru;
	}

	public void setGajiPokokBaru(double gajiPokokBaru) {
		this.gajiPokokBaru = gajiPokokBaru;
	}

	private double gajiPokokLama;
	private double gajiPokokBaru;

	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public GajiPokok() {
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
	
	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(GajiPokok gr) {
		return getId() == gr.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GajiPokok) {
			GajiPokok gp = (GajiPokok) obj;
			return equals(gp);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
