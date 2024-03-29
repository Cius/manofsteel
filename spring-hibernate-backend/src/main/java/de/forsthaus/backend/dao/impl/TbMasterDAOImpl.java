package de.forsthaus.backend.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import de.forsthaus.backend.util.MonthEqExpression;

public class TbMasterDAOImpl extends BasisDAO<TbMaster> implements TbMasterDAO {

	@Override
	public TbMaster getNew() {
		return new TbMaster();
	}
	
	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TbMaster")); 
	}

	@SuppressWarnings("unchecked")
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
	public  List<TbMaster> getDaftar01Cpns(Map<String,Object>criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String masaKerjaAwal = (String)criterias.get(ConstantsText.MASA_KERJA_AWAL);
		String masaKerjaAkhir = (String)criterias.get(ConstantsText.MASA_KERJA_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		criteria.add(Restrictions.eq("statusPegawai", "CPNS"));
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		criteria.createAlias("tpjabatan", "b");
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
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
	public  List<TbMaster> getDaftar02Pns(Map<String,Object>criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String masaKerjaAwal = (String)criterias.get(ConstantsText.MASA_KERJA_AWAL);
		String masaKerjaAkhir = (String)criterias.get(ConstantsText.MASA_KERJA_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		criteria.add(Restrictions.eq("statusPegawai", "PNS"));
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		criteria.createAlias("tpjabatan", "b");
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar03PnsUrutNip(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.addOrder(Order.asc("nip"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String kriteria = (String)criterias.get(ConstantsText.KRITERIA);
		String tahun = (String)criterias.get(ConstantsText.SUB_KRITERIA);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		try{
			if(!kriteria.equals(ConstantsText.SELURUH)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date dYearStart = sdf.parse(tahun);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dYearStart);
				cal.add(Calendar.YEAR, 1);
				Date dYearEnd = cal.getTime();
				if(kriteria.equals(ConstantsText.TAHUN_CPNS)){
					criteria.add(Restrictions.gt("tmtcpns", dYearStart));
					criteria.add(Restrictions.lt("tmtcpns", dYearEnd));
				}else if(kriteria.equals(ConstantsText.TAHUN_LAHIR)){
					criteria.add(Restrictions.gt("tglLahir", dYearStart));
					criteria.add(Restrictions.lt("tglLahir", dYearEnd));
				}
			}
		}catch(ParseException pe){
			pe.printStackTrace();
		}
		
		criteria.createAlias("tpjabatan", "b");
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar05PnsUrutNama(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "c");
		criteria.addOrder(Order.asc("c.nama"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String kriteria = (String)criterias.get(ConstantsText.KRITERIA);
		String huruf = (String)criterias.get(ConstantsText.SUB_KRITERIA);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		
		if(!kriteria.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.like("c.nama", huruf+'%'));
		}
		
		criteria.createAlias("tpjabatan", "b");
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar04PnsUrutPangkat(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("b.jnsJab"));
		String kriteria = (String)criterias.get(ConstantsText.KRITERIA);
		String subKriteria = (String)criterias.get(ConstantsText.SUB_KRITERIA);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!kriteria.equals(ConstantsText.SELURUH)){
			if(kriteria.equals(ConstantsText.GOLONGAN)){
				criteria.add(Restrictions.like("kodeGol", subKriteria+"_"));
			}else if(kriteria.equals(ConstantsText.JABATAN)){
				criteria.add(Restrictions.eq("b.jnsJab", subKriteria));
			}
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar06PnsUrutJabatan(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.add(Restrictions.eq("b.jnsJab", "1"));
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar07Kepangkatan(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("kodeGol"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String golAwal = (String)criterias.get(ConstantsText.GOLONGAN_AWAL);
		String golAkhir = (String)criterias.get(ConstantsText.GOLONGAN_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		if(!golAwal.equals(ConstantsText.SELURUH)){
			String [] kodeGols =ConstantsText.kodeGols;
			List<String> listGol = new ArrayList<String>();
			
			if(golAwal.equals(golAkhir)){
				listGol.add(golAwal);
			}else{
				boolean start = false;
				for (int i = 0; i < kodeGols.length; i++) {
					if(start==true){
						listGol.add(kodeGols[i]);
						if(kodeGols[i].equals(golAkhir)){
							break;
						}
					}else{
						if(kodeGols[i].equals(golAwal)){
							listGol.add(kodeGols[i]);
							start = true;
						}
					}
				}
			}
			criteria.add(Restrictions.in("kodeGol", listGol));
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar08JabatanStruktural(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		criteria.add(Restrictions.eq("b.jnsJab", "1"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String eselonAwal = (String)criterias.get(ConstantsText.ESELON_AWAL);
		String eselonAkhir = (String)criterias.get(ConstantsText.ESELON_AKHIR);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		System.out.println("jk:"+jenisKelamin);
		System.out.println("aw:"+eselonAwal);
		System.out.println("ek:"+eselonAkhir);
		System.out.println("uk:"+unitKerja);
		System.out.println("uo:"+unitOrganisasi);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		if(!eselonAwal.equals(ConstantsText.SELURUH)){
			String [] kodeEselons ={"11","12","21","22","31","32","41","42","51"};
			List<String> listEselon = new ArrayList<String>();
			
			if(eselonAwal.equals(eselonAkhir)){
				listEselon.add(eselonAwal);
			}else{
				boolean start = false;
				for (int i = 0; i < kodeEselons.length; i++) {
					if(start==true){
						listEselon.add(kodeEselons[i]);
						if(kodeEselons[i].equals(eselonAkhir)){
							break;
						}
					}else{
						if(kodeEselons[i].equals(eselonAwal)){
							listEselon.add(kodeEselons[i]);
							start = true;
						}
					}
				}
			}
			criteria.add(Restrictions.in("kodeEselon", listEselon));
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar09JabatanFungsionalKhusus(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		criteria.add(Restrictions.eq("b.jnsJab", "2"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String rumpun = (String)criterias.get(ConstantsText.RUMPUN);
		String golongan = (String)criterias.get(ConstantsText.GOLONGAN);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		if(!rumpun.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.eq("kodeJab", rumpun));
		}
		
		if(!golongan.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.eq("kodeGol", golongan));
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar10JabatanFungsionalUmum(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		criteria.add(Restrictions.eq("b.jnsJab", "2"));
		String jenisKelamin = (String)criterias.get(ConstantsText.JENIS_KELAMIN);
		String rumpun = (String)criterias.get(ConstantsText.RUMPUN);
		String golongan = (String)criterias.get(ConstantsText.GOLONGAN);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(!jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			criteria.add(Restrictions.eq("kelamin", jenisKelamin.equals(ConstantsText.JENIS_KELAMIN_PRIA)?"LAKI-LAKI":"PEREMPUAN"));
		}
		
		if(!rumpun.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.eq("kodeJab", rumpun));
		}
		
		if(!golongan.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.eq("kodeGol", golongan));
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar35PurnaBhakti(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.add(Restrictions.eq("statusPegawai", "PNS"));
		criteria.addOrder(Order.asc("nip"));
		int bulan = (Integer)criterias.get(ConstantsText.BULAN);
		
		criteria.add(new MonthEqExpression("tglLahir", bulan));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar36UlangTahun(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.addOrder(Order.asc("nip"));
		int bulan = (Integer)criterias.get(ConstantsText.BULAN);
		
		criteria.add(new MonthEqExpression("tglLahir", bulan));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar34PenguasaanBahasa(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("trBahasa", "b");
		criteria.addOrder(Order.asc("nip"));
		criteria.add(Restrictions.eq("statusPegawai", "PNS"));
		criteria.add(Restrictions.eq("b.kodeBahasa", "A"));
		String namaBahasa = (String)criterias.get(ConstantsText.NAMA_BAHASA);
		String jenisBahasa = (String)criterias.get(ConstantsText.JENIS_BAHASA);
		
		criteria.add(Restrictions.eq("b.jBahasa", jenisBahasa));
		criteria.add(Restrictions.eq("b.nBahasa", namaBahasa));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar31AlamatKantorRumah(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		String jenisJabatan = (String)criterias.get(ConstantsText.JABATAN);
		String eselon = (String)criterias.get(ConstantsText.ESELON);
		
		System.out.println("jk:"+jenisJabatan);
		System.out.println("aw:"+eselon);
		
		if(!jenisJabatan.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.eq("b.jnsJab", jenisJabatan));
		}
		
		if(!eselon.equals(ConstantsText.SELURUH)){
			criteria.add(Restrictions.like("kodeEselon", eselon+"_"));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar30KepemilikianKartu(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar29Persyaratan(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar28MasaKerja(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar27Pensiun(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar25KenaikanGaji(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar24KenaikanPangkat(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar23PendidikanAlumniJurusan(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar22PendidikanSekolahAkademi(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar20PendidikanPraJabatan(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMaster> getDaftar19PendidikanKursusNonKedinasan(Map<String, Object> criterias) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TbMaster.class);
		
		criteria.createAlias("identitas", "i");
		criteria.createAlias("tpjabatan", "b");
		criteria.addOrder(Order.asc("nip"));
		
		String kartu = (String)criterias.get(ConstantsText.KARTU);
		String unitKerja = (String)criterias.get(ConstantsText.UNIT_KERJA);
		String unitOrganisasi = (String)criterias.get(ConstantsText.UNIT_ORGANISASI);
		
		if(kartu.equals(ConstantsText.KARTU_KARPEG)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarPeg", ""))
					.add(Restrictions.eq("i.nKarPeg", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_ASKES)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nAsKes", ""))
					.add(Restrictions.eq("i.nAsKes", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_TASPEN)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nTasPen", ""))
					.add(Restrictions.eq("i.nTasPen", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARIS)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("i.nKarIs", ""))
					.add(Restrictions.eq("i.nKarIs", "-"))
			);
		}else if(kartu.equals(ConstantsText.KARTU_KARSU)){
			
		}
		
		if(!unitKerja.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitKerja.substring(0, 5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitKerja.substring(5, 7)));
		}else if(!unitOrganisasi.equals("")){
			criteria.add(Restrictions.eq("b.kodeUnKer", unitOrganisasi.substring(0,5)));
			criteria.add(Restrictions.eq("b.kodeSatKer", unitOrganisasi.substring(5, 7)));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<TbMaster> getDaftar12JabatanJenisGuru(
			Map<String, Object> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbMaster> getDaftar14JabatanStrukturalBelumDiklatpim(
			Map<String, Object> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbMaster> getDaftar15PendidikanUmumTerakhir(
			Map<String, Object> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbMaster> getDaftar16PendidikanDiklatJabatan(
			Map<String, Object> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbMaster> getDaftar17PendidikanDiklatKader(
			Map<String, Object> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbMaster> getDaftar18PendidikanPenataranSeminarCtrl(
			Map<String, Object> criterias) {
		// TODO Auto-generated method stub
		return null;
	}
}
