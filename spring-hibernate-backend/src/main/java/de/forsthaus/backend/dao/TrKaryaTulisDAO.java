package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrKaryaTulis;

public interface TrKaryaTulisDAO {
	public TrKaryaTulis getNew();
	public List<TrKaryaTulis> getAll();
	public TrKaryaTulis getById(int id);
	public TrKaryaTulis getByNip(String nip);
	public int getCount();
	public void save(TrKaryaTulis entity);
	public void saveOrUpdate(TrKaryaTulis entity);
	public void delete(TrKaryaTulis entity);

}
