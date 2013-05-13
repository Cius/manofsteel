package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.Wilayah;

@Repository
public class WilayahDAOImpl extends BasisDAO<Wilayah> implements WilayahDAO, InitializingBean {

	@Override
	public Wilayah getNewWilayah() {
		return new Wilayah();
	}

	@Override
	public Wilayah getWilayahById(long id) {
		return get(Wilayah.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wilayah> getAllWilayah() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.addOrder(Order.asc("kodeWilayah"));

		return getHibernateTemplate().findByCriteria(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Wilayah> getWilayahsLikeName(String string) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.add(Restrictions.ilike("namaWilayah", string, MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllWilayahs() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from Wilayah"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wilayah> getWilayahByType(String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.add(Restrictions.ilike("tipeWilayah", type, MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<Wilayah> getKotaByPropinsi(Wilayah propinsi) {
		String kode = propinsi.getKodeWilayah().substring(0, 2);
		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.add(Restrictions.eq("tipeWilayah", "2"))
			    .add(Restrictions.ilike("kodeWilayah", kode, MatchMode.START));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<Wilayah> getKecamatanByKota(Wilayah propinsi) {
		String kode = propinsi.getKodeWilayah().substring(0, 4);
		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.add(Restrictions.eq("tipeWilayah", "3"))
			    .add(Restrictions.ilike("kodeWilayah", kode, MatchMode.START));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<Wilayah> getKelurahanByKecamatan(Wilayah propinsi) {
		String kode = propinsi.getKodeWilayah().substring(0, 6);
		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.add(Restrictions.eq("tipeWilayah", "4"))
			    .add(Restrictions.ilike("kodeWilayah", kode, MatchMode.START));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public Wilayah getWilayahByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Wilayah.class);
		criteria.add(Restrictions.eq("kodeWilayah", kode));

		try {
			return (Wilayah)getHibernateTemplate().findByCriteria(criteria).get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

}
