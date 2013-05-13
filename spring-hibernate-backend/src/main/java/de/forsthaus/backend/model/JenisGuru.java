package de.forsthaus.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class JenisGuru implements java.io.Serializable, Entity {
	
	private static final long serialVersionUID = 1769511865007424105L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	
	private String kjnsGuru;
	private String njnsGuru;
	
	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public JenisGuru() {
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
	
	
	public String getKjnsGuru() {
		return kjnsGuru;
	}

	public void setKjnsGuru(String kjnsGuru) {
		this.kjnsGuru = kjnsGuru;
	}

	public String getNjnsGuru() {
		return njnsGuru;
	}

	public void setNjnsGuru(String njnsGuru) {
		this.njnsGuru = njnsGuru;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(JenisGuru gr) {
		return getId() == gr.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof JenisGuru) {
			JenisGuru gp = (JenisGuru) obj;
			return equals(gp);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
