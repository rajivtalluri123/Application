package com.hmhco.api.grading.controller.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.springframework.data.mapping.model.MappingException;

/**
 * Created by akellas on 05/06/2015.
 */
public class GenericEnumType implements UserType, ParameterizedType, Serializable {
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o)
            throws HibernateException, SQLException {

        String name = resultSet.getString(strings[0]);
        Object result = null;
        if (!resultSet.wasNull()) {
            result = Enum.valueOf(clazz, name);
        }

        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor)
            throws HibernateException, SQLException {

        if (null == o) {
            preparedStatement.setNull(i, Types.VARCHAR);
        } else {
            preparedStatement.setObject(i, ((Enum) o).name(), Types.OTHER);
        }
    }

    private Class clazz = null;

    public void setParameterValues(Properties params) {
        String enumClassName = params.getProperty("enumClassName");
        if (enumClassName == null) {
            throw new MappingException("enumClassName parameter not specified");
        }

        try {
            this.clazz = Class.forName(enumClassName);
        } catch (ClassNotFoundException e) {
            throw new MappingException("enumClass " + enumClassName + " not found", e);
        }
    }

    private static final int[] SQL_TYPES = { Types.VARCHAR };

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return clazz;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }

}
