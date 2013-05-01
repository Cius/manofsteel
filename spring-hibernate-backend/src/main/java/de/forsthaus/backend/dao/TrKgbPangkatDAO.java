package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrKgbPangkat;

public interface TrKgbPangkatDAO {
	public TrKgbPangkat getNew();
	public List<TrKgbPangkat> getAll();
	public TrKgbPangkat getById(int id);
	public TrKgbPangkat getByNip(String nip);
	public int getCount();
	public void save(TrKgbPangkat entity);
	public void saveOrUpdate(TrKgbPangkat entity);
	public void delete(TrKgbPangkat entity);

}
