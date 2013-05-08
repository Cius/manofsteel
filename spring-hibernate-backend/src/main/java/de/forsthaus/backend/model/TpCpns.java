package de.forsthaus.backend.model;

import java.util.Date;

import de.forsthaus.backend.util.PegawaiUtil;

public class TpCpns {
	private int id;
	private int version;

	private String nip;
	private String kPejCpns;
	private String skCpns;
	private Date tSkCpns;
	private Date tmtCpns;
	private String kGolRuCpns;
	private String latPrajab;
	private String thnPrajab;
	private Date tmtTugas;
	private String thnKerja;
	private String blnKerja;
	private String kTpuCpns;
	private String kPejPns;
	private String skPns;
	private Date tSkPns;
	private Date tmtPns;
	private String kGolRuPns;
	private String janjiPns;
	private String userEnt;
	private Date tglEnt;
	private String userEdt;
	private Date tglEdt;

	private TpIdentitas identitas;
	
	private String kGolRuCpnsDesc;

	public TpCpns() {

	}

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

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getkPejCpns() {
		return kPejCpns;
	}

	public void setkPejCpns(String kPejCpns) {
		this.kPejCpns = kPejCpns;
	}

	public String getSkCpns() {
		return skCpns;
	}

	public void setSkCpns(String skCpns) {
		this.skCpns = skCpns;
	}

	public Date gettSkCpns() {
		return tSkCpns;
	}

	public void settSkCpns(Date tSkCpns) {
		this.tSkCpns = tSkCpns;
	}

	public Date getTmtCpns() {
		return tmtCpns;
	}

	public void setTmtCpns(Date tmtCpns) {
		this.tmtCpns = tmtCpns;
	}

	public String getkGolRuCpns() {
		return kGolRuCpns;
	}

	public void setkGolRuCpns(String kGolRuCpns) {
		this.kGolRuCpns = kGolRuCpns;
	}

	public String getLatPrajab() {
		return latPrajab;
	}

	public void setLatPrajab(String latPrajab) {
		this.latPrajab = latPrajab;
	}

	public String getThnPrajab() {
		return thnPrajab;
	}

	public void setThnPrajab(String thnPrajab) {
		this.thnPrajab = thnPrajab;
	}

	public Date getTmtTugas() {
		return tmtTugas;
	}

	public void setTmtTugas(Date tmtTugas) {
		this.tmtTugas = tmtTugas;
	}

	public String getThnKerja() {
		return thnKerja;
	}

	public void setThnKerja(String thnKerja) {
		this.thnKerja = thnKerja;
	}

	public String getBlnKerja() {
		return blnKerja;
	}

	public void setBlnKerja(String blnKerja) {
		this.blnKerja = blnKerja;
	}

	public String getkTpuCpns() {
		return kTpuCpns;
	}

	public void setkTpuCpns(String kTpuCpns) {
		this.kTpuCpns = kTpuCpns;
	}

	public String getkPejPns() {
		return kPejPns;
	}

	public void setkPejPns(String kPejPns) {
		this.kPejPns = kPejPns;
	}

	public String getSkPns() {
		return skPns;
	}

	public void setSkPns(String skPns) {
		this.skPns = skPns;
	}

	public Date gettSkPns() {
		return tSkPns;
	}

	public void settSkPns(Date tSkPns) {
		this.tSkPns = tSkPns;
	}

	public Date getTmtPns() {
		return tmtPns;
	}

	public void setTmtPns(Date tmtPns) {
		this.tmtPns = tmtPns;
	}

	public String getkGolRuPns() {
		return kGolRuPns;
	}

	public void setkGolRuPns(String kGolRuPns) {
		this.kGolRuPns = kGolRuPns;
	}

	public String getJanjiPns() {
		return janjiPns;
	}

	public void setJanjiPns(String janjiPns) {
		this.janjiPns = janjiPns;
	}

	public String getUserEnt() {
		return userEnt;
	}

	public void setUserEnt(String userEnt) {
		this.userEnt = userEnt;
	}

	public Date getTglEnt() {
		return tglEnt;
	}
	public void setTglEnt(Date tglEnt) {
		this.tglEnt = tglEnt;
	}

	public String getUserEdt() {
		return userEdt;
	}

	public void setUserEdt(String userEdt) {
		this.userEdt = userEdt;
	}

	public Date getTglEdt() {
		return tglEdt;
	}
	public void setTglEdt(Date tglEdt) {
		this.tglEdt = tglEdt;
	}

	public TpIdentitas getIdentitas() {
		return identitas;
	}

	public void setIdentitas(TpIdentitas identitas) {
		this.identitas = identitas;
	}

	public String getkGolRuCpnsDesc() {
		kGolRuCpnsDesc = PegawaiUtil.convertGolongan(kGolRuCpns); 
		return kGolRuCpnsDesc;
	}

	public void setkGolRuCpnsDesc(String kGolRuCpnsDesc) {
		this.kGolRuCpnsDesc = kGolRuCpnsDesc;
	}

}
