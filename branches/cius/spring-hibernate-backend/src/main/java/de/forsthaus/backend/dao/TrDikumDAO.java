package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrDikum;

public interface TrDikumDAO {
	public TrDikum getNew();
	public List<TrDikum> getAll();
	public TrDikum getById(int id);
	public TrDikum getByNip(String nip);
	public int getCount();
	public void save(TrDikum entity);
	public void saveOrUpdate(TrDikum entity);
	public void delete(TrDikum entity);

}
