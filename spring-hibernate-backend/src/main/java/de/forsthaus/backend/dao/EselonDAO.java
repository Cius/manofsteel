package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.Eselon;

public interface EselonDAO {

	public Eselon getNewEselon();
	
	public List<Eselon> getAllEselon();

	public int getCountAllEselons();

	public Eselon getEselonById(long id);

	public List<Eselon> getEselonByType(String type);

	public List<Eselon> getEselonsLikeName(String string);

	public void saveOrUpdate(Eselon entity);

	public void delete(Eselon entity);

	public void save(Eselon entity);
	
	public Eselon getEselonByKode(String kode);

}
