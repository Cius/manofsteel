package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrDiklat;

public interface TrDiklatDAO {
	public TrDiklat getNew();
	public List<TrDiklat> getAll();
	public TrDiklat getById(int id);
	public TrDiklat getByNip(String nip);
	public int getCount();
	public void save(TrDiklat entity);
	public void saveOrUpdate(TrDiklat entity);
	public void delete(TrDiklat entity);

}
