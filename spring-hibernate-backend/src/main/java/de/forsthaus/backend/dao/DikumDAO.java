package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.Dikum;

public interface DikumDAO {

	public Dikum getNewDikum();
	
	public List<Dikum> getAllDikum();

	public int getCountAllDikums();

	public Dikum getDikumById(long id);
	
	public List<Dikum> getDikumLikeName(String string);
	
	public List<Dikum> getDikumForTingkatPendidikan();
	
	public Dikum getDikumByKtpu(String kodeTingkat);
	
	public void saveOrUpdate(Dikum entity);

	public void delete(Dikum entity);

	public void save(Dikum entity);

}
