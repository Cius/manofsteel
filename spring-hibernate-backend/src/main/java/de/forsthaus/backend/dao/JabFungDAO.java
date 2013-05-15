package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.JabFung;

public interface JabFungDAO {
	public JabFung getNew();
	public List<JabFung> getAll();
	public JabFung getById(int id);
	public JabFung getByKode(String kode);
	public int getCount();
	public void save(JabFung entity);
	public void saveOrUpdate(JabFung entity);
	public void delete(JabFung entity);
}
