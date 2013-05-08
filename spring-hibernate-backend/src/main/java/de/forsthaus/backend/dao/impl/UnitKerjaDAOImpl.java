package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.UnitKerjaDAO;
import de.forsthaus.backend.model.UnitKerja;

@Repository
public class UnitKerjaDAOImpl extends BasisDAO<UnitKerja> implements UnitKerjaDAO, InitializingBean {

	@Override
	public UnitKerja getNewUnitKerja() {
		return new UnitKerja();
	}

	@Override
	public UnitKerja getUnitKerjaById(long id) {
		return get(UnitKerja.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitKerja> getAllUnitKerja() {
		DetachedCriteria criteria = DetachedCriteria.forClass(UnitKerja.class);
		criteria.addOrder(Order.asc("nwil"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllUnitKerjas() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from UnitKerja"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitKerja> getUnitKerjaByType(String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UnitKerja.class);
		criteria.add(Restrictions.ilike("tipeUnitKerja", type, MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public UnitKerja getUnitKerjaByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UnitKerja.class);
		criteria.add(Restrictions.eq("kodeUnitKerja", kode));

		return (UnitKerja)getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public List<UnitKerja> getUnitKerjasLikeNameAndType(String string, String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UnitKerja.class);
		criteria.add(Restrictions.ilike("nunker", type, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("tunit", type));

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
