package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.GolonganRuang;

public interface GolonganRuangDAO {

	public GolonganRuang getNewGolonganRuang();
	
	public List<GolonganRuang> getAllGolonganRuang();

	public int getCountAllGolonganRuangs();

	public GolonganRuang getGolonganRuangById(long id);
	
	public List<GolonganRuang> getGolonganRuangByKode(String string);
	
	public List<GolonganRuang> getGolonganRuangLikeName(String string);
	
	public void saveOrUpdate(GolonganRuang entity);

	public void delete(GolonganRuang entity);

	public void save(GolonganRuang entity);

}
