/**
 * Copyright 2010 the original author or authors.
 * 
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package de.forsthaus.backend.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


public class UnitKerja implements java.io.Serializable, Entity {

	private static final long serialVersionUID = -8977439920128356829L;
	private long id = Long.MIN_VALUE + 1;
	private int version = 0;
	private	String	kinsker	=	""	;
	private	String	kunker	=	""	;
	private	String	nunker	=	""	;
	private	String	alamat	=	""	;
	private	String	kota;
	private	String	telp	=	""	;
	private	String	tunit	=	""	;
	private	String	klokasi;
	private	Eselon	eselon;
	private	String	kpok	=	""	;
	private	String	npok	=	""	;
	private	String	bagan	=	""	;
	private	String	noperda	=	""	;
	private	Date	tgperda	=	new Date()	;
	private	String	perdadari	=	""	;


	public boolean isNew() {
		return (getId() == Long.MIN_VALUE + 1);
	}

	public UnitKerja() {
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

	public String getTunit() {
		return tunit;
	}

	public void setTunit(String tunit) {
		this.tunit = tunit;
	}

	public String getKpok() {
		return kpok;
	}

	public void setKpok(String kpok) {
		this.kpok = kpok;
	}

	public String getNpok() {
		return npok;
	}

	public void setNpok(String npok) {
		this.npok = npok;
	}

	public String getBagan() {
		return bagan;
	}

	public void setBagan(String bagan) {
		this.bagan = bagan;
	}

	public String getNoperda() {
		return noperda;
	}

	public void setNoperda(String noperda) {
		this.noperda = noperda;
	}

	public Date getTgperda() {
		return tgperda;
	}

	public void setTgperda(Date tgperda) {
		this.tgperda = tgperda;
	}

	public String getPerdadari() {
		return perdadari;
	}

	public void setPerdadari(String perdadari) {
		this.perdadari = perdadari;
	}

	public String getKlokasi() {
		return klokasi;
	}

	public void setKlokasi(String wilayah) {
		this.klokasi = wilayah;
	}

	public Eselon getEselon() {
		return eselon;
	}

	public void setEselon(Eselon eselon) {
		this.eselon = eselon;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(UnitKerja article) {
		return getId() == article.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof UnitKerja) {
			UnitKerja article = (UnitKerja) obj;
			return equals(article);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
