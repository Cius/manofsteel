package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.model.Gabungan;

@Repository
public class GabunganDAOImpl extends BasisDAO<Gabungan> implements GabunganDAO, InitializingBean {

	@Override
	public Gabungan getNewGabungan() {
		return new Gabungan();
	}

	@Override
	public Gabungan getGabunganById(long id) {
		return get(Gabungan.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gabungan> getAllGabungan() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Gabungan.class);
		criteria.addOrder(Order.asc("nama"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllGabungans() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from Gabungan"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@Override
	public List<Gabungan> getGabunganLikeName(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Gabungan.class);
		criteria.add(Restrictions.like("namaGabungan", string, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("kode"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<Gabungan> getGabunganByKodeTabel(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Gabungan.class);
		criteria.add(Restrictions.eq("kodeTabel", string));
		criteria.addOrder(Order.asc("kode"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public Gabungan getGabunganByKodeTabelAndKode(String tabel, String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Gabungan.class);
		criteria.add(Restrictions.eq("kodeTabel", tabel));
		
		int iKode = Integer.parseInt(kode);
		criteria.add(Restrictions.eq("kode", String.valueOf(iKode)));
		
		try {
			return ((List<Gabungan>) getHibernateTemplate().findByCriteria(criteria)).get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

}
