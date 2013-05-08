package de.forsthaus.backend.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;

public class TbMasterDAOImpl extends BasisDAO<TbMaster> implements TbMasterDAO {

	@Override
	public TbMaster getNew() {
		return new TbMaster();
	}

	@Override
	public List<TbMaster> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TbMaster getById(int id) {
		return get(TbMaster.class, id);
	}

	@Override
	public TbMaster getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TbMaster) getHibernateTemplate().findByCriteria(criteria).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public  List<TbMaster> getDaftarCpns(Map<String,Object>criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.add(Restrictions.eq("statusPegawai", "PNS"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String masaKerjaAwal = (String)criterias.get(ConstantsText.MASA_KERJA_AWAL);
		String masaKerjaAkhir = (String)criterias.get(ConstantsText.MASA_KERJA_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitKerja));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitOrganisasi));
		}
		
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		List<TbMaster> listTemp = ( List<TbMaster>) getHibernateTemplate().findByCriteria(criteria);
		if(listTemp!=null){
			if(masaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
				listTbMaster = listTemp;
			}else if(masaKerjaAwal.equals(masaKerjaAkhir)){
				TbMaster tbMaster = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				for (Iterator<TbMaster> iterator = listTemp.iterator(); iterator.hasNext();) {
					tbMaster = (TbMaster) iterator.next();
					if(tbMaster.getTahunKerja()==iMasaKerjaAwal){
						listTbMaster.add(tbMaster);
					}
				}
			}else{
				TbMaster tbMaster = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				int iMasaKerjaAkhir = Integer.parseInt(masaKerjaAkhir);
				for (Iterator<TbMaster> iterator = listTemp.iterator(); iterator.hasNext();) {
					tbMaster = (TbMaster) iterator.next();
					if(tbMaster.getTahunKerja()>=iMasaKerjaAwal && tbMaster.getTahunKerja()<=iMasaKerjaAkhir){
						listTbMaster.add(tbMaster);
					}
				}
			}
		}
		return listTbMaster;
	}

	
	@SuppressWarnings("unchecked")
	public  List<TbMaster> getByCriterias(Map<String,Object>criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.add(Restrictions.eq("statusPegawai", "PNS"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String masaKerjaAwal = (String)criterias.get(ConstantsText.MASA_KERJA_AWAL);
		String masaKerjaAkhir = (String)criterias.get(ConstantsText.MASA_KERJA_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitKerja));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitOrganisasi));
		}
		
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		List<TbMaster> listTemp = ( List<TbMaster>) getHibernateTemplate().findByCriteria(criteria);
		if(listTemp!=null){
			if(masaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
				listTbMaster = listTemp;
			}else if(masaKerjaAwal.equals(masaKerjaAkhir)){
				TbMaster tbMaster = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				for (Iterator<TbMaster> iterator = listTemp.iterator(); iterator.hasNext();) {
					tbMaster = (TbMaster) iterator.next();
					if(tbMaster.getTahunKerja()==iMasaKerjaAwal){
						listTbMaster.add(tbMaster);
					}
				}
			}else{
				TbMaster tbMaster = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				int iMasaKerjaAkhir = Integer.parseInt(masaKerjaAkhir);
				for (Iterator<TbMaster> iterator = listTemp.iterator(); iterator.hasNext();) {
					tbMaster = (TbMaster) iterator.next();
					if(tbMaster.getTahunKerja()>=iMasaKerjaAwal && tbMaster.getTahunKerja()<=iMasaKerjaAkhir){
						listTbMaster.add(tbMaster);
					}
				}
			}
		}
		return listTbMaster;
	}

	@Override
	public List<TbMaster> getDaftarPnsUrutNipCtrl(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.add(Restrictions.eq("statusPegawai", "PNS"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String masaKerjaAwal = (String)criterias.get(ConstantsText.MASA_KERJA_AWAL);
		String masaKerjaAkhir = (String)criterias.get(ConstantsText.MASA_KERJA_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitKerja));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("unitkerja", unitOrganisasi));
		}
		
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		List<TbMaster> listTemp = ( List<TbMaster>) getHibernateTemplate().findByCriteria(criteria);
		if(listTemp!=null){
			if(masaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
				listTbMaster = listTemp;
			}else if(masaKerjaAwal.equals(masaKerjaAkhir)){
				TbMaster tbMaster = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				for (Iterator<TbMaster> iterator = listTemp.iterator(); iterator.hasNext();) {
					tbMaster = (TbMaster) iterator.next();
					if(tbMaster.getTahunKerja()==iMasaKerjaAwal){
						listTbMaster.add(tbMaster);
					}
				}
			}else{
				TbMaster tbMaster = null;
				int iMasaKerjaAwal = Integer.parseInt(masaKerjaAwal);
				int iMasaKerjaAkhir = Integer.parseInt(masaKerjaAkhir);
				for (Iterator<TbMaster> iterator = listTemp.iterator(); iterator.hasNext();) {
					tbMaster = (TbMaster) iterator.next();
					if(tbMaster.getTahunKerja()>=iMasaKerjaAwal && tbMaster.getTahunKerja()<=iMasaKerjaAkhir){
						listTbMaster.add(tbMaster);
					}
				}
			}
		}
		return listTbMaster;
	}
	
	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TbMaster")); 
	}

	
}
