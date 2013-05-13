package de.forsthaus.backend.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.backend.util.PegawaiUtil;

public class TbMaster {
	private int id;
	private int version;

	private String nip;
	private String nama;
	private String tempatLahir;
	private Date tglLahir;
	private String kelamin;
	private String agama;
	private String statusPegawai;
	private String statusKawin;
	private String alamatTinggal;
	private String kelurahan;
	private String kecamatan;
	private String kabupaten;
	private String propinsi;
	private String karpeg;
	private String npwp;
	private Date tmtcpns;
	private Date tmtpns;
	private String kodeGol;
	private String golruang;
	private String pangkat;
	private Date tmtpangkat;
	private String pejabatGol;
	private String noskgol;
	private Date tgskgol;
	private String thkerjagol;
	private String blkerjagol;
	private String gajigol;
	private String pejabatgaber;
	private String noskgaber;
	private String tgskgaber;
	private Date tmtgaber;
	private String gajigaber;
	private String jenisJabatan;
	private String kodeEselon;
	private String eselon;
	private String jabatan;
	private Date tmtJabatan;
	private Date tmtEselon;
	private String unitkerja;
	private String jenisGuru;
	private String kodePendidikan;
	private String jurusan;
	private String namaSekolah;
	private String tempatSekolah;
	private String tahunLulus;
	private String kodeDikstruk;
	private String diklatStruktural;
	private String tahunLulusDiklat;
	private String angkatanDiklat;
	private String jamDiklat;
	private String niplama;
	
	private TpIdentitas identitas;
	private TpCpns cpns;
	private TpJabatan tpjabatan;
	private Set<TrDiklat> trdiklat = new HashSet<TrDiklat>();
	
	private String masaKerja;
	private int tahunKerja;
	private int bulanKerja;
	private String usia;
	private int usiaTahun;
	private int usiaBulan;
	private String kodePendidikanDesc;

	public TbMaster() {

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

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTglLahir() {
		return tglLahir;
	}

	public void setTglLahir(Date tglLahir) {
		this.tglLahir = tglLahir;
	}

	public String getKelamin() {
		return kelamin;
	}

	public void setKelamin(String kelamin) {
		this.kelamin = kelamin;
	}

	public String getAgama() {
		return agama;
	}

	public void setAgama(String agama) {
		this.agama = agama;
	}

	public String getStatusPegawai() {
		return statusPegawai;
	}

	public void setStatusPegawai(String statusPegawai) {
		this.statusPegawai = statusPegawai;
	}

	public String getStatusKawin() {
		return statusKawin;
	}

	public void setStatusKawin(String statusKawin) {
		this.statusKawin = statusKawin;
	}

	public String getAlamatTinggal() {
		return alamatTinggal;
	}

	public void setAlamatTinggal(String alamatTinggal) {
		this.alamatTinggal = alamatTinggal;
	}

	public String getKelurahan() {
		return kelurahan;
	}

	public void setKelurahan(String kelurahan) {
		this.kelurahan = kelurahan;
	}

	public String getKecamatan() {
		return kecamatan;
	}

	public void setKecamatan(String kecamatan) {
		this.kecamatan = kecamatan;
	}

	public String getKabupaten() {
		return kabupaten;
	}

	public void setKabupaten(String kabupaten) {
		this.kabupaten = kabupaten;
	}

	public String getPropinsi() {
		return propinsi;
	}

	public void setPropinsi(String propinsi) {
		this.propinsi = propinsi;
	}

	public String getKarpeg() {
		return karpeg;
	}

	public void setKarpeg(String karpeg) {
		this.karpeg = karpeg;
	}

	public String getNpwp() {
		return npwp;
	}

	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}

	public Date getTmtcpns() {
		return tmtcpns;
	}

	public void setTmtcpns(Date tmtcpns) {
		this.tmtcpns = tmtcpns;
	}

	public Date getTmtpns() {
		return tmtpns;
	}

	public void setTmtpns(Date tmtpns) {
		this.tmtpns = tmtpns;
	}

	public String getKodeGol() {
		return kodeGol;
	}

	public void setKodeGol(String kodeGol) {
		this.kodeGol = kodeGol;
	}

	public String getGolruang() {
		return golruang;
	}

	public void setGolruang(String golruang) {
		this.golruang = golruang;
	}

	public String getPangkat() {
		return pangkat;
	}

	public void setPangkat(String pangkat) {
		this.pangkat = pangkat;
	}

	public Date getTmtpangkat() {
		return tmtpangkat;
	}

	public void setTmtpangkat(Date tmtpangkat) {
		this.tmtpangkat = tmtpangkat;
	}

	public String getPejabatGol() {
		return pejabatGol;
	}

	public void setPejabatGol(String pejabatGol) {
		this.pejabatGol = pejabatGol;
	}

	public String getNoskgol() {
		return noskgol;
	}

	public void setNoskgol(String noskgol) {
		this.noskgol = noskgol;
	}

	public Date getTgskgol() {
		return tgskgol;
	}

	public void setTgskgol(Date tgskgol) {
		this.tgskgol = tgskgol;
	}

	public String getThkerjagol() {
		return thkerjagol;
	}

	public void setThkerjagol(String thkerjagol) {
		this.thkerjagol = thkerjagol;
	}

	public String getBlkerjagol() {
		return blkerjagol;
	}

	public void setBlkerjagol(String blkerjagol) {
		this.blkerjagol = blkerjagol;
	}

	public String getGajigol() {
		return gajigol;
	}

	public void setGajigol(String gajigol) {
		this.gajigol = gajigol;
	}

	public String getPejabatgaber() {
		return pejabatgaber;
	}

	public void setPejabatgaber(String pejabatgaber) {
		this.pejabatgaber = pejabatgaber;
	}

	public String getNoskgaber() {
		return noskgaber;
	}

	public void setNoskgaber(String noskgaber) {
		this.noskgaber = noskgaber;
	}

	public String getTgskgaber() {
		return tgskgaber;
	}

	public void setTgskgaber(String tgskgaber) {
		this.tgskgaber = tgskgaber;
	}

	public Date getTmtgaber() {
		return tmtgaber;
	}

	public void setTmtgaber(Date tmtgaber) {
		this.tmtgaber = tmtgaber;
	}

	public String getGajigaber() {
		return gajigaber;
	}

	public void setGajigaber(String gajigaber) {
		this.gajigaber = gajigaber;
	}

	public String getJenisJabatan() {
		return jenisJabatan;
	}

	public void setJenisJabatan(String jenisJabatan) {
		this.jenisJabatan = jenisJabatan;
	}

	public String getKodeEselon() {
		return kodeEselon;
	}

	public void setKodeEselon(String kodeEselon) {
		this.kodeEselon = kodeEselon;
	}

	public String getEselon() {
		return eselon;
	}

	public void setEselon(String eselon) {
		this.eselon = eselon;
	}

	public String getJabatan() {
		return jabatan;
	}

	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}

	public Date getTmtJabatan() {
		return tmtJabatan;
	}

	public void setTmtJabatan(Date tmtJabatan) {
		this.tmtJabatan = tmtJabatan;
	}

	public Date getTmtEselon() {
		return tmtEselon;
	}

	public void setTmtEselon(Date tmtEselon) {
		this.tmtEselon = tmtEselon;
	}

	public String getUnitkerja() {
		return unitkerja;
	}

	public void setUnitkerja(String unitkerja) {
		this.unitkerja = unitkerja;
	}

	public String getJenisGuru() {
		return jenisGuru;
	}

	public void setJenisGuru(String jenisGuru) {
		this.jenisGuru = jenisGuru;
	}

	public String getKodePendidikan() {
		return kodePendidikan;
	}

	public void setKodePendidikan(String kodePendidikan) {
		this.kodePendidikan = kodePendidikan;
	}

	public String getJurusan() {
		return jurusan;
	}

	public void setJurusan(String jurusan) {
		this.jurusan = jurusan;
	}

	public String getNamaSekolah() {
		return namaSekolah;
	}

	public void setNamaSekolah(String namaSekolah) {
		this.namaSekolah = namaSekolah;
	}

	public String getTempatSekolah() {
		return tempatSekolah;
	}

	public void setTempatSekolah(String tempatSekolah) {
		this.tempatSekolah = tempatSekolah;
	}

	public String getTahunLulus() {
		return tahunLulus;
	}

	public void setTahunLulus(String tahunLulus) {
		this.tahunLulus = tahunLulus;
	}

	public String getKodeDikstruk() {
		return kodeDikstruk;
	}

	public void setKodeDikstruk(String kodeDikstruk) {
		this.kodeDikstruk = kodeDikstruk;
	}

	public String getDiklatStruktural() {
		return diklatStruktural;
	}

	public void setDiklatStruktural(String diklatStruktural) {
		this.diklatStruktural = diklatStruktural;
	}

	public String getTahunLulusDiklat() {
		return tahunLulusDiklat;
	}

	public void setTahunLulusDiklat(String tahunLulusDiklat) {
		this.tahunLulusDiklat = tahunLulusDiklat;
	}

	public String getAngkatanDiklat() {
		return angkatanDiklat;
	}

	public void setAngkatanDiklat(String angkatanDiklat) {
		this.angkatanDiklat = angkatanDiklat;
	}

	public String getJamDiklat() {
		return jamDiklat;
	}

	public void setJamDiklat(String jamDiklat) {
		this.jamDiklat = jamDiklat;
	}

	public String getNiplama() {
		return niplama;
	}

	public void setNiplama(String niplama) {
		this.niplama = niplama;
	}

	public String getMasaKerja() {
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tmtcpns);
		masaKerja = list!=null?list.get(0)+" THN "+list.get(1)+" BLN":"";
		return masaKerja;
	}

	public void setMasaKerja(String masaKerja) {
		this.masaKerja = masaKerja;
	}

	public int getTahunKerja() {
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tmtcpns);
		tahunKerja = list!=null?list.get(0):0;
		return tahunKerja;
	}

	public void setTahunKerja(int tahunKerja) {
		this.tahunKerja = tahunKerja;
	}

	public int getBulanKerja() {
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tmtcpns);
		bulanKerja = list!=null?list.get(1):0;
		return bulanKerja;
	}

	public void setBulanKerja(int bulanKerja) {
		this.bulanKerja = bulanKerja;
	}

	public String getUsia() {
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tglLahir);
		usia = list!=null?list.get(0)+" THN "+list.get(1)+" BLN":"";
		return usia;
	}

	public void setUsia(String usia) {
		this.usia = usia;
	}

	public int getUsiaTahun() {
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tglLahir);
		usiaTahun = list!=null?list.get(0):0;
		return usiaTahun;
	}

	public void setUsiaTahun(int usiaTahun) {
		this.usiaTahun = usiaTahun;
	}

	public int getUsiaBulan() {
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tglLahir);
		usiaBulan = list!=null?list.get(1):0;
		return usiaBulan;
	}

	public void setUsiaBulan(int usiaBulan) {
		this.usiaBulan = usiaBulan;
	}

	public String getKodePendidikanDesc() {
		kodePendidikanDesc = "-";
		if(kodePendidikan.equals("1")){
			kodePendidikanDesc = "SD";
		}
		return kodePendidikanDesc;
	}

	public void setKodePendidikanDesc(String kodePendidikanDesc) {
		this.kodePendidikanDesc = kodePendidikanDesc;
	}

	public TpIdentitas getIdentitas() {
		return identitas;
	}

	public void setIdentitas(TpIdentitas identitas) {
		this.identitas = identitas;
	}

	public TpCpns getCpns() {
		return cpns;
	}

	public void setCpns(TpCpns cpns) {
		this.cpns = cpns;
	}

	public TpJabatan getTpjabatan() {
		return tpjabatan;
	}

	public void setTpjabatan(TpJabatan tpjabatan) {
		this.tpjabatan = tpjabatan;
	}

	public Set<TrDiklat> getTrdiklat() {
		return trdiklat;
	}

	public void setTrdiklat(Set<TrDiklat> trdiklat) {
		this.trdiklat = trdiklat;
	}

	public String getDiklatTerakhir() {
		String diklatTerakhir="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int i = 1;
		if(getTrdiklat()!=null){
			TrDiklat trDiklat = null;
			for (Iterator<TrDiklat> iterator = getTrdiklat().iterator(); iterator.hasNext();) {
				trDiklat = (TrDiklat) iterator.next();
				diklatTerakhir = i + ". "+trDiklat.getnDiklat()+" TAHUN "+sdf.format(trDiklat.gettAkhir()+"<br/>");
				i++;
			}
		}
		if(i<3){
			for (int j = i; j <= 3; j++) {
				diklatTerakhir += i + ". "+"---"+"<br/>";
				i++;
			}
		}
		
		return diklatTerakhir;
	}
	
	public Date getTmtTerakhir(){
		Date tmtTerakhir=null;
		if(statusPegawai.equals(ConstantsText.STATUS_PNS)){
			tmtTerakhir = tmtpns;
		}else{
			tmtTerakhir = tmtcpns;
		}
		return tmtTerakhir;
	}

	public String getGolTerakhir(){
		String golTerakhir="";
		if(statusPegawai.equals(ConstantsText.STATUS_PNS)){
			golTerakhir= golruang;
		}else{
			golTerakhir= cpns.getkGolRuCpnsDesc();
		}
		return golTerakhir;
	}
	
	public String getLamaJabatan(){
		List<Integer> list = PegawaiUtil.getTanggalBulanSekarang(tmtJabatan);
		return list!=null?list.get(0)+" THN "+list.get(1)+" BLN":"";
	}
	
	public String getKEselonDesc(){
		return PegawaiUtil.convertEselon(kodeEselon);
	}
}
