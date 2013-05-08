package de.forsthaus.backend.dao;

import java.util.List;
import java.util.Map;

import de.forsthaus.backend.model.TpCpnsReport;

public interface TpCpnsReportDAO {
	public TpCpnsReport getNew();
	public List<TpCpnsReport> getAll();
	public TpCpnsReport getById(int id);
	public TpCpnsReport getByNip(String nip);
	public List<TpCpnsReport> getByCriterias(Map<String,Object>criterias);
	public int getCount();
	public void save(TpCpnsReport entity);
	public void saveOrUpdate(TpCpnsReport entity);
	public void delete(TpCpnsReport entity);
}
