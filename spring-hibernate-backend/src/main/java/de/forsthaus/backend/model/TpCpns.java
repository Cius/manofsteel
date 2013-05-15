package de.forsthaus.backend.model;

import java.util.Date;
import java.util.List;

import de.forsthaus.backend.util.PegawaiUtil;

public class TpCpns {
	private int id;
	private int version;

	private String nip;
	private String kodePejCpns;
	private String skCpns;
	private Date tglSkCpns;
	private Date tmtCpns;
	private String kodeGolRuCpns;
	private String latPrajab;
	private String thnPrajab;
	private Date tmtTugas;
	private String thnKerja;
	private String blnKerja;
	private String kodeTpuCpns;
	private String kodePejPns;
	private String skPns;
	private Date tglSkPns;
	private Date tmtPns;
	private String kodeGolRuPns;
	private String janjiPns;
	private String userEnt;
	private Date tglEnt;
	private String userEdt;
	private Date tglEdt;
	
	private TpIdentitas identitas;
	private TpKgbPangkat pangkat;
	private TpJabatan jabatan;
	
	private List<TrKgbPangkat> pangkats;
	
	private String kGolRuCpnsDesc;
	private String latPrajabDesc;

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


	public String getKodePejCpns() {
		return kodePejCpns;
	}


	public void setKodePejCpns(String kodePejCpns) {
		this.kodePejCpns = kodePejCpns;
	}

	public String getSkCpns() {
		return skCpns;
	}

	public void setSkCpns(String skCpns) {
		this.skCpns = skCpns;
	}

	public Date getTglSkCpns() {
		return tglSkCpns;
	}

	public void setTglSkCpns(Date tSkCpns) {
		this.tglSkCpns = tSkCpns;
	}

	public Date getTmtCpns() {
		return tmtCpns;
	}

	public void setTmtCpns(Date tmtCpns) {
		this.tmtCpns = tmtCpns;
	}


	public String getKodeGolRuCpns() {
		return kodeGolRuCpns;
	}


	public void setKodeGolRuCpns(String kodeGolRuCpns) {
		this.kodeGolRuCpns = kodeGolRuCpns;
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


	public String getKodeTpuCpns() {
		return kodeTpuCpns;
	}


	public void setKodeTpuCpns(String kodeTpuCpns) {
		this.kodeTpuCpns = kodeTpuCpns;
	}


	public String getKodePejPns() {
		return kodePejPns;
	}


	public void setKodePejPns(String kodePejPns) {
		this.kodePejPns = kodePejPns;
	}

	public String getSkPns() {
		return skPns;
	}

	public void setSkPns(String skPns) {
		this.skPns = skPns;
	}

	public Date getTglSkPns() {
		return tglSkPns;
	}

	public void setTglSkPns(Date tSkPns) {
		this.tglSkPns = tSkPns;
	}

	public Date getTmtPns() {
		return tmtPns;
	}

	public void setTmtPns(Date tmtPns) {
		this.tmtPns = tmtPns;
	}


	public String getKodeGolRuPns() {
		return kodeGolRuPns;
	}


	public void setKodeGolRuPns(String kodeGolRuPns) {
		this.kodeGolRuPns = kodeGolRuPns;
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
		kGolRuCpnsDesc = PegawaiUtil.convertGolongan(kodeGolRuCpns); 
		return kGolRuCpnsDesc;
	}

	public void setkGolRuCpnsDesc(String kGolRuCpnsDesc) {
		this.kGolRuCpnsDesc = kGolRuCpnsDesc;
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

	public TpKgbPangkat getPangkat() {
		return pangkat;
	}

	public void setPangkat(TpKgbPangkat pangkat) {
		this.pangkat = pangkat;
	}

	public TpJabatan getJabatan() {
		return jabatan;
	}

	public void setJabatan(TpJabatan jabatan) {
		this.jabatan = jabatan;
	}

	public List<TrKgbPangkat> getPangkats() {
		return pangkats;
	}

	public void setPangkats(List<TrKgbPangkat> pangkats) {
		this.pangkats = pangkats;
	}

}
