package de.forsthaus.backend.util;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;
import org.hibernate.type.IntegerType;

public class MonthEqExpression implements Criterion {

	private static final long serialVersionUID = -6649609089130587939L;
	private final String propertyName;
    private final int month;

    public MonthEqExpression(String propertyName, int month) {
        this.propertyName = propertyName;
        this.month = month;
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
        throws HibernateException {
            String[] columns = criteriaQuery.getColumnsUsingProjection(criteria,propertyName);
            if (columns.length!=1) {
                throw new HibernateException("monthEq may only be used with single-column properties");
            }
            return "month(" + columns[0] + ") = ?";
        }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[] {new TypedValue(new IntegerType(), month, EntityMode.POJO)};
    }

    @Override
    public String toString() {
        return "month(" + propertyName + ") = " + month;
    }
}