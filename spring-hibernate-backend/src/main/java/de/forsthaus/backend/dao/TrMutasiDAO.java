package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrMutasi;

public interface TrMutasiDAO {
	public TrMutasi getNew();
	public List<TrMutasi> getAll();
	public TrMutasi getById(int id);
	public TrMutasi getByNip(String nip);
	public int getCount();
	public void save(TrMutasi entity);
	public void saveOrUpdate(TrMutasi entity);
	public void delete(TrMutasi entity);

}
