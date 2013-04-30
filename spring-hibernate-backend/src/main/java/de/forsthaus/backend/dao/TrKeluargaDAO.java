package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.TrKeluarga;

public interface TrKeluargaDAO {
	public TrKeluarga getNew();
	public List<TrKeluarga> getAll();
	public TrKeluarga getById(int id);
	public TrKeluarga getByNip(String nip);
	public int getCount();
	public void save(TrKeluarga entity);
	public void saveOrUpdate(TrKeluarga entity);
	public void delete(TrKeluarga entity);

}
