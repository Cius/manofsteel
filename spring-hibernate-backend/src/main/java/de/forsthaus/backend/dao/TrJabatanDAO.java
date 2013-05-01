package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrJabatan;

public interface TrJabatanDAO {
	public TrJabatan getNew();
	public List<TrJabatan> getAll();
	public TrJabatan getById(int id);
	public TrJabatan getByNip(String nip);
	public int getCount();
	public void save(TrJabatan entity);
	public void saveOrUpdate(TrJabatan entity);
	public void delete(TrJabatan entity);

}
