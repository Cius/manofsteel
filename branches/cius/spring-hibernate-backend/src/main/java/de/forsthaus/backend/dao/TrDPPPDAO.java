package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrDPPP;

public interface TrDPPPDAO {
	public TrDPPP getNew();
	public List<TrDPPP> getAll();
	public TrDPPP getById(int id);
	public TrDPPP getByNip(String nip);
	public int getCount();
	public void save(TrDPPP entity);
	public void saveOrUpdate(TrDPPP entity);
	public void delete(TrDPPP entity);

}
