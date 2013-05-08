package de.forsthaus.backend.model;

import java.util.Date;

public class TpCpnsReport {
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
	private Date tgEnt;
	private String userEdt;
	private Date tgEdt;

	private TbMaster master;
	private String latPrajabDesc;

	public TpCpnsReport() {

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

	public Date getTgEnt() {
		return tgEnt;
	}

	public void setTgEnt(Date tgEnt) {
		this.tgEnt = tgEnt;
	}

	public String getUserEdt() {
		return userEdt;
	}

	public void setUserEdt(String userEdt) {
		this.userEdt = userEdt;
	}

	public Date getTgEdt() {
		return tgEdt;
	}

	public void setTgEdt(Date tgEdt) {
		this.tgEdt = tgEdt;
	}

	public TbMaster getMaster() {
		return master;
	}

	public void setMaster(TbMaster master) {
		this.master = master;
	}

	public String getLatPrajabDesc() {
		if(latPrajab.equals("1")){
			latPrajabDesc = "I";
		}else if (latPrajab.equals("2")){
			latPrajabDesc = "II";
		}else if (latPrajab.equals("3")){
			latPrajabDesc = "III";
		}
		return latPrajabDesc;
	}

	public void setLatPrajabDesc(String latPrajabDesc) {
		this.latPrajabDesc = latPrajabDesc;
	}

}
