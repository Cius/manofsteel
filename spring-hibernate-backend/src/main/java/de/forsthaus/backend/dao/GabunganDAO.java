package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.Gabungan;

public interface GabunganDAO {

	public Gabungan getNewGabungan();
	
	public List<Gabungan> getAllGabungan();

	public int getCountAllGabungans();

	public Gabungan getGabunganById(long id);
	
	public List<Gabungan> getGabunganLikeName(String string);
	
	public List<Gabungan> getGabunganByKodeTabel(String string);
	
	public Gabungan getGabunganByKodeTabelAndKode(String tabel, String kode);
	
	public void saveOrUpdate(Gabungan entity);

	public void delete(Gabungan entity);

	public void save(Gabungan entity);

}
