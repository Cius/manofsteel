package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrJasa;

public interface TrJasaDAO {
	public TrJasa getNew();
	public List<TrJasa> getAll();
	public TrJasa getById(int id);
	public TrJasa getByNip(String nip);
	public int getCount();
	public void save(TrJasa entity);
	public void saveOrUpdate(TrJasa entity);
	public void delete(TrJasa entity);

}
