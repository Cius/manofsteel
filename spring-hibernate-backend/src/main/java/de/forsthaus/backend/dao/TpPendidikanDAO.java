package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TpPendidikan;

public interface TpPendidikanDAO {
	public TpPendidikan getNew();
	public List<TpPendidikan> getAll();
	public TpPendidikan getById(int id);
	public TpPendidikan getByNip(String nip);
	public int getCount();
	public void save(TpPendidikan entity);
	public void saveOrUpdate(TpPendidikan entity);
	public void delete(TpPendidikan entity);

}
