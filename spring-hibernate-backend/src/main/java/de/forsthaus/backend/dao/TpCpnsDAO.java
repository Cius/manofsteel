package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TpCpns;

public interface TpCpnsDAO {
	public TpCpns getNew();
	public List<TpCpns> getAll();
	public TpCpns getById(int id);
	public TpCpns getByNip(String nip);
	public int getCount();
	public void save(TpCpns entity);
	public void saveOrUpdate(TpCpns entity);
	public void delete(TpCpns entity);
}
