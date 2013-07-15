package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrBahasa;

public interface TrBahasaDAO {
	public TrBahasa getNew();
	public List<TrBahasa> getAll();
	public TrBahasa getById(int id);
	public TrBahasa getByNip(String nip);
	public int getCount();
	public void save(TrBahasa entity);
	public void saveOrUpdate(TrBahasa entity);
	public void delete(TrBahasa entity);
	public List<String> getBahasaByJenis(String nip);
}
