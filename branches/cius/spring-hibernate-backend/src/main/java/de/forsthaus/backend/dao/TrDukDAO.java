package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrDuk;

public interface TrDukDAO {
	public TrDuk getNew();
	public List<TrDuk> getAll();
	public TrDuk getById(int id);
	public TrDuk getByNip(String nip);
	public int getCount();
	public void save(TrDuk entity);
	public void saveOrUpdate(TrDuk entity);
	public void delete(TrDuk entity);

}
