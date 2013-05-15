package de.forsthaus.backend.dao;

import java.util.List;

import de.forsthaus.backend.model.UnitKerja;

public interface UnitKerjaDAO {

	public UnitKerja getNewUnitKerja();
	
	public List<UnitKerja> getAllUnitKerja();

	public int getCountAllUnitKerjas();

	public UnitKerja getUnitKerjaById(long id);

	public List<UnitKerja> getUnitKerjaByType(String type);
	
	public List<UnitKerja> getUnitKerjasLikeNameAndType(String string, String type);

	public void saveOrUpdate(UnitKerja entity);

	public void delete(UnitKerja entity);

	public void save(UnitKerja entity);
	
	public UnitKerja getUnitKerjaByKode(String kode);

}
