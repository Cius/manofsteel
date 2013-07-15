package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.MasterJabFung;

public interface MasterJabFungDAO {	
	public List<MasterJabFung> getAll();
	public List<MasterJabFung> findRumpun();
}
