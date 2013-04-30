package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TpKgbPangkat;

public interface TpKgbPangkatDAO {
	public TpKgbPangkat getNew();
	public List<TpKgbPangkat> getAll();
	public TpKgbPangkat getById(int id);
	public TpKgbPangkat getByNip(String nip);
	public int getCount();
	public void save(TpKgbPangkat entity);
	public void saveOrUpdate(TpKgbPangkat entity);
	public void delete(TpKgbPangkat entity);

}
