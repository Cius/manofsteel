package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrOrganisasi;

public interface TrOrganisasiDAO {
	public TrOrganisasi getNew();
	public List<TrOrganisasi> getAll();
	public TrOrganisasi getById(int id);
	public TrOrganisasi getByNip(String nip);
	public int getCount();
	public void save(TrOrganisasi entity);
	public void saveOrUpdate(TrOrganisasi entity);
	public void delete(TrOrganisasi entity);

}
