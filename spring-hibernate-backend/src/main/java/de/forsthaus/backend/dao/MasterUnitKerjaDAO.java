package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.MasterUnitKerja;

public interface MasterUnitKerjaDAO {	
	public List<MasterUnitKerja> getAll();
	public List<String> getUnitKerja(String tunit);
	public List<MasterUnitKerja> findbyUnitKerja(String tunit);
}
