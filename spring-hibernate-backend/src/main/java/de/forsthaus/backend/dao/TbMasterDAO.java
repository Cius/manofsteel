package de.forsthaus.backend.dao;

import java.util.List;
import java.util.Map;

import de.forsthaus.backend.model.TbMaster;

public interface TbMasterDAO {
	public TbMaster getNew();
	public List<TbMaster> getAll();
	public TbMaster getById(int id);
	public TbMaster getByNip(String nip);
	public List<TbMaster> getByCriterias(Map<String,Object>criterias);
	public List<TbMaster> getDaftar01Cpns(Map<String,Object>criterias);
	public List<TbMaster> getDaftar02Pns(Map<String,Object>criterias);
	public List<TbMaster> getDaftar03PnsUrutNip(Map<String,Object>criterias);
	public List<TbMaster> getDaftar04PnsUrutPangkat(Map<String,Object>criterias);
	public List<TbMaster> getDaftar05PnsUrutNama(Map<String,Object>criterias);
	public List<TbMaster> getDaftar06PnsUrutJabatan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar07Kepangkatan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar08JabatanStruktural(Map<String,Object>criterias);
	public List<TbMaster> getDaftar09JabatanFungsionalKhusus(Map<String,Object>criterias);
	public List<TbMaster> getDaftar10JabatanFungsionalUmum(Map<String,Object>criterias);
	public List<TbMaster> getDaftar12JabatanJenisGuru(Map<String,Object>criterias);
	public List<TbMaster> getDaftar14JabatanStrukturalBelumDiklatpim(Map<String,Object>criterias);
	public List<TbMaster> getDaftar15PendidikanUmumTerakhir(Map<String,Object>criterias);
	public List<TbMaster> getDaftar16PendidikanDiklatJabatan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar17PendidikanDiklatKader(Map<String,Object>criterias);
	public List<TbMaster> getDaftar18PendidikanPenataranSeminarCtrl(Map<String,Object>criterias);
	public List<TbMaster> getDaftar19PendidikanKursusNonKedinasan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar20PendidikanPraJabatan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar22PendidikanSekolahAkademi(Map<String,Object>criterias);
	public List<TbMaster> getDaftar23PendidikanAlumniJurusan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar24KenaikanPangkat(Map<String,Object>criterias);
	public List<TbMaster> getDaftar25KenaikanGaji(Map<String,Object>criterias);
	public List<TbMaster> getDaftar27Pensiun(Map<String,Object>criterias);
	public List<TbMaster> getDaftar28MasaKerja(Map<String,Object>criterias);
	public List<TbMaster> getDaftar29Persyaratan(Map<String,Object>criterias);
	public List<TbMaster> getDaftar30KepemilikianKartu(Map<String,Object>criterias);
	public List<TbMaster> getDaftar31AlamatKantorRumah(Map<String,Object>criterias);
	public List<TbMaster> getDaftar34PenguasaanBahasa(Map<String,Object>criterias);
	public List<TbMaster> getDaftar35PurnaBhakti(Map<String,Object>criterias);
	public List<TbMaster> getDaftar36UlangTahun(Map<String,Object>criterias);
	public int getCount();
	public void save(TbMaster entity);
	public void saveOrUpdate(TbMaster entity);
	public void delete(TbMaster entity);
}
