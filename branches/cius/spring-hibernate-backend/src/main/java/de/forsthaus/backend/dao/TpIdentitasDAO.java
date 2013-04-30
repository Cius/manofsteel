package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TpIdentitas;

public interface TpIdentitasDAO {
	public TpIdentitas getNew();
	public List<TpIdentitas> getAll();
	public TpIdentitas getById(int id);
	public TpIdentitas getByNip(String nip);
	public int getCount();
	public void save(TpIdentitas entity);
	public void saveOrUpdate(TpIdentitas entity);
	public void delete(TpIdentitas entity);
}
