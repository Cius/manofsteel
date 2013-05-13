package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Dikum implements java.io.Serializable, Entity {
	
	private static final long serialVersionUID = 7630114408811620256L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	
	private String ktpu;
	private String kjur;
	private String njur;
	private String ndik;
	private GolonganRuang gawal;
	private GolonganRuang gakhir;
	private String krumpun;
	
	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public Dikum() {
	}
	
	public Dikum(String kjur, String njur) {
		this.kjur = kjur;
		this.njur = njur;
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
		return ktpu;
	}

	public void setKodeTabel(String kodeTabel) {
		this.ktpu = kodeTabel;
	}

	public String getKode() {
		return kjur;
	}

	public void setKode(String kode) {
		this.kjur = kode;
	}

	public String getNama() {
		return njur;
	}

	public void setNama(String nama) {
		njur = nama;
	}

	public String getKtpu() {
		return ktpu;
	}

	public void setKtpu(String ktpu) {
		this.ktpu = ktpu;
	}

	public String getKjur() {
		return kjur;
	}

	public void setKjur(String kjur) {
		this.kjur = kjur;
	}

	public String getNjur() {
		return njur;
	}

	public void setNjur(String njur) {
		this.njur = njur;
	}

	public String getNdik() {
		return ndik;
	}

	public void setNdik(String ndik) {
		this.ndik = ndik;
	}

	public GolonganRuang getGawal() {
		return gawal;
	}

	public void setGawal(GolonganRuang gawal) {
		this.gawal = gawal;
	}

	public GolonganRuang getGakhir() {
		return gakhir;
	}

	public void setGakhir(GolonganRuang gakhir) {
		this.gakhir = gakhir;
	}

	public String getKrumpun() {
		return krumpun;
	}

	public void setKrumpun(String krumpun) {
		this.krumpun = krumpun;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(Dikum gr) {
		return getId() == gr.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Dikum) {
			Dikum gp = (Dikum) obj;
			if (equals(gp))
				return true;
			else {
				if (gp.getKtpu().equals(getKtpu()))
					return true;
				return false;
			}
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
