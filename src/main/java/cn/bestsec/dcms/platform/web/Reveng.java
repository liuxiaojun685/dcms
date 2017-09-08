package cn.bestsec.dcms.platform.web;

import java.sql.Types;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reveng extends DelegatingReverseEngineeringStrategy {
	static Logger log = LoggerFactory.getLogger(Reveng.class);

	public Reveng(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	public String columnToHibernateTypeName(TableIdentifier table, String columnName, int sqlType, int length,
			int precision, int scale, boolean nullable, boolean generatedIdentifier) {
		switch (sqlType) {
		case Types.VARCHAR:
			return "String";
		case Types.INTEGER:
			return "Integer";
		case Types.BIGINT:
			return "Long";
		}
		return table.getName() + "_" + columnName + "_" + sqlType;
	}

	

}
