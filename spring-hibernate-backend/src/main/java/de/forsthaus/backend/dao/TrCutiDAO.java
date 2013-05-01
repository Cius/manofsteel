package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrCuti;

public interface TrCutiDAO {
	public TrCuti getNew();
	public List<TrCuti> getAll();
	public TrCuti getById(int id);
	public TrCuti getByNip(String nip);
	public int getCount();
	public void save(TrCuti entity);
	public void saveOrUpdate(TrCuti entity);
	public void delete(TrCuti entity);

}
