package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.GajiPokok;

public interface GajiPokokDAO {

	public GajiPokok getNewGajiPokok();
	
	public List<GajiPokok> getAllGajiPokok();

	public int getCountAllGajiPokoks();

	public GajiPokok getGajiPokokById(long id);
	
	public List<GajiPokok> getGajiPokokLikeName(String string);
	
	public void saveOrUpdate(GajiPokok entity);

	public void delete(GajiPokok entity);

	public void save(GajiPokok entity);

}
