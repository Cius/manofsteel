package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.JenisGuru;

public interface JenisGuruDAO {

	public JenisGuru getNewJenisGuru();
	
	public List<JenisGuru> getAllJenisGuru();

	public int getCountAllJenisGurus();

	public JenisGuru getJenisGuruById(long id);
	
	public List<JenisGuru> getJenisGuruLikeName(String string);
	
	public void saveOrUpdate(JenisGuru entity);

	public void delete(JenisGuru entity);

	public void save(JenisGuru entity);

}
