package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrHukuman;

public interface TrHukumanDAO {
	public TrHukuman getNew();
	public List<TrHukuman> getAll();
	public TrHukuman getById(int id);
	public TrHukuman getByNip(String nip);
	public int getCount();
	public void save(TrHukuman entity);
	public void saveOrUpdate(TrHukuman entity);
	public void delete(TrHukuman entity);

}
