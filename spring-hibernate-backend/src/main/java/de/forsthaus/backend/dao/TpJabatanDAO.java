package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TpJabatan;

public interface TpJabatanDAO {
	public TpJabatan getNew();
	public List<TpJabatan> getAll();
	public TpJabatan getById(int id);
	public TpJabatan getByNip(String nip);
	public int getCount();
	public void save(TpJabatan entity);
	public void saveOrUpdate(TpJabatan entity);
	public void delete(TpJabatan entity);
}
