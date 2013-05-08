package de.forsthaus.backend.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TpCpnsReportDAO;
import de.forsthaus.backend.model.TpCpnsReport;
import de.forsthaus.backend.util.ConstantsText;

public class TpCpnsReportDAOImpl extends BasisDAO<TpCpnsReport> implements TpCpnsReportDAO {

	@Override
	public TpCpnsReport getNew() {
		return new TpCpnsReport();
	}

	@Override
	public List<TpCpnsReport> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpCpnsReport.class).createCriteria("master");
		criteria.addOrder(Order.asc("nip"));
		criteria.add(Restrictions.eq("statusPegawai", "CPNS"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TpCpnsReport getById(int id) {
		return get(TpCpnsReport.class, id);
	}

	@Override
	public TpCpnsReport getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpCpnsReport.class).createCriteria("master");;
		criteria.add(Restrictions.eq("nip", nip));
		criteria.add(Restrictions.eq("statusPegawai", "CPNS"));
		return (TpCpnsReport) getHibernateTemplate().findByCriteria(criteria).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public  List<TpCpnsReport> getByCriterias(Map<String,Object>criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpCpnsReport.class).createCriteria("master");
		
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String masaKerjaAwal = (String)criterias.get(ConstantsText.MASA_KERJA_AWAL);
		String masaKerjaAkhir = (String)criterias.get(ConstantsText.MASA_KERJA_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		criteria.add(Restrictions.eq("statusPegawai", "CPNS"));
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitKerja));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitOrganisasi));
		}
		
		List<TpCpnsReport> listTpCpnsReport = new ArrayList<TpCpnsReport>();
		List<TpCpnsReport> listTemp = ( List<TpCpnsReport>) getHibernateTemplate().findByCriteria(criteria);
		if(listTemp!=null){
			if(masaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
				listTpCpnsReport = listTemp;
			}else if(masaKerjaAwal.equals(masaKerjaAkhir)){
				TpCpnsReport TpCpnsReport = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				for (Iterator<TpCpnsReport> iterator = listTemp.iterator(); iterator.hasNext();) {
					TpCpnsReport = (TpCpnsReport) iterator.next();
					if(TpCpnsReport.getMaster().getTahunKerja()==iMasaKerjaAwal){
						listTpCpnsReport.add(TpCpnsReport);
					}
				}
			}else{
				TpCpnsReport TpCpnsReport = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				int iMasaKerjaAkhir = Integer.parseInt(masaKerjaAkhir);
				for (Iterator<TpCpnsReport> iterator = listTemp.iterator(); iterator.hasNext();) {
					TpCpnsReport = (TpCpnsReport) iterator.next();
					if(TpCpnsReport.getMaster().getTahunKerja()>=iMasaKerjaAwal && TpCpnsReport.getMaster().getTahunKerja()<=iMasaKerjaAkhir){
						listTpCpnsReport.add(TpCpnsReport);
					}
				}
			}
		}
		return listTpCpnsReport;
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TpCpnsReport")); 
	}
}
