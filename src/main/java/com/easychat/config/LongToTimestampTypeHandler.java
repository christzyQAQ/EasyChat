package com.easychat.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.Date;

@MappedTypes({Timestamp.class, Date.class})
public class LongToTimestampTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter instanceof Timestamp) {
            ps.setTimestamp(i, (Timestamp) parameter);
        } else if (parameter instanceof Date) {
            ps.setTimestamp(i, new Timestamp(((Date) parameter).getTime()));
        } else if (parameter instanceof Long) {
            ps.setTimestamp(i, new Timestamp((Long) parameter));
        } else {
            throw new SQLException("无法将 " + parameter.getClass().getName() + " 转换为 Timestamp");
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return timestamp;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return timestamp;
    }
}