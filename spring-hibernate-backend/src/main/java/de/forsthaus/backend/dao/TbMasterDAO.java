package de.forsthaus.backend.dao;

import java.util.List;
import java.util.Map;

import de.forsthaus.backend.model.TbMaster;

public interface TbMasterDAO {
	public TbMaster getNew();
	public List<TbMaster> getAll();
	public TbMaster getById(int id);
	public TbMaster getByNip(String nip);
	public List<TbMaster> getByCriterias(Map<String,Object>criterias);
	public List<TbMaster> getDaftarCpns(Map<String,Object>criterias);
	public List<TbMaster> getDaftarPnsUrutNipCtrl(Map<String,Object>criterias);
	public int getCount();
	public void save(TbMaster entity);
	public void saveOrUpdate(TbMaster entity);
	public void delete(TbMaster entity);
}
