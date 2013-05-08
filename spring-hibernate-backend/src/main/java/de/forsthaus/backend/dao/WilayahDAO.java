package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.Wilayah;

public interface WilayahDAO {

	public Wilayah getNewWilayah();
	
	public List<Wilayah> getAllWilayah();

	public int getCountAllWilayahs();

	public Wilayah getWilayahById(long id);

	public List<Wilayah> getWilayahByType(String type);

	public List<Wilayah> getWilayahsLikeName(String string);

	public void saveOrUpdate(Wilayah entity);

	public void delete(Wilayah entity);

	public void save(Wilayah entity);
	
	public List<Wilayah> getKotaByPropinsi(Wilayah propinsi);
	
	public List<Wilayah> getKecamatanByKota(Wilayah propinsi);
	
	public List<Wilayah> getKelurahanByKecamatan(Wilayah propinsi);
	
	public Wilayah getWilayahByKode(String kode);

}
