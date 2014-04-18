package com.kashu.dao.jdbc.annotation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.kashu.dao.Dao;
import com.kashu.dao.jdbc.annotation.domain.User;

@Repository("userDao")
@Transactional(readOnly = true)
public class UserDaoImpl implements Dao<User> ,InitializingBean {
	
	private DataSource dataSource;
	private NamedParameterJdbcTemplate template;
	private JdbcTemplate jdbcTemplate;
	
	public void afterPropertiesSet() throws Exception {
		if (dataSource == null) {
			throw new BeanCreationException("Must set dataSource on UserDao");
		}
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.template = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User findOne(Long id) {
		String sql = "SELECT * FROM TB_USER WHERE ID = :id";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return template.queryForObject(sql, params, new UserMapper());
	}
	
	public List<User> findAll() {
		String sql = "SELECT * FROM TB_USER";
		return template.query(sql, new UserMapper());
	}

	public List<User> findByParams(Map<String, Object> params) {
		String sql = "SELECT * FROM TB_USER";
		sql += getWhereClause(params);
		sql += getOrderClause(params);
		sql += getLimitClause(params);
		System.out.println("[sql statement] = " + sql);
		return template.query(sql, params, new UserMapper());
	}
	
	//不要把%寫進SQL statement裡面，會導致悲慘結果，什麼都搜不到
	//Don't write % into your SQL statement , this will cause database return nothing
	public String getWhereClause(Map<String,Object> params){
		String operator = (params.get("operator")==null) ? "" : (String) params.get("operator");
		String where = "";
		if (params.get("uuid")!=null){
			where = " WHERE UUID LIKE :uuid";
		}else if (params.get("firstName")!=null){
			where = " WHERE FIRST_NAME LIKE :firstName";
		}else if(params.get("lastName")!=null){
			where = " WHERE LAST_NAME LIKE :lastName";
		}else if(params.get("displayName")!=null){
			where = " WHERE DISPLAY_NAME LIKE :displayName";
		}else if(params.get("male")!=null){
			where = " WHERE MALE = :male";
		}else if(params.get("birthday")!=null && operator.equals("gt")){
			where = " WHERE BIRTHDAY > :birthday";
		}else if(params.get("birthday")!=null && operator.equals("lt")){
			where = " WHERE BIRTHDAY < :birthday";
		}else if(params.get("birthday")!=null && operator.equals("eq")){
			where = " WHERE BIRTHDAY = :birthday";
		}else if(params.get("date1")!=null && params.get("date2")!=null){
			where = " WHERE BIRTHDAY BETWEEN :date1 AND :date2";
		}else if(params.get("address")!=null){
			where = " WHERE ADDRESS LIKE :address";
		}else if(params.get("phone")!=null){
			where = " WHERE PHONE LIKE :phone";
		}else if(params.get("mobile")!=null){
			where = " WHERE MOBILE LIKE :mobile";
		}else if(params.get("score")!=null && operator.equals("gt")){
			where = " WHERE SCORE > :score";
		}else if(params.get("score")!=null && operator.equals("lt")){
			where = " WHERE SCORE < :score";
		}else if(params.get("score")!=null && operator.equals("eq")){
			where = " WHERE SCORE = :score";
		}
		System.out.println("[where clause] = " + where);
		return where;
	}
	
	public String getOrderClause(Map<String,Object> params){
		String orderColumn = (String)params.get("orderColumn");
		String orderType = (String)params.get("orderType");
		String order = "";
		if( orderColumn!=null && orderType!=null){
			if(orderColumn.equals("uuid") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY UUID ASC";
			}else if(orderColumn.equals("uuid") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY UUID DESC";
			}else if(orderColumn.equals("firstName") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY FIRST_NAME ASC";
			}else if(orderColumn.equals("firstName") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY FIRST_NAME DESC";
			}else if(orderColumn.equals("lastName") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY LAST_NAME ASC";
			}else if(orderColumn.equals("lastName") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY LAST_NAME DESC";
			}else if(orderColumn.equals("displayName") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY DISPLAY_NAME ASC";
			}else if(orderColumn.equals("displayName") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY DISPLAY_NAME DESC";
			}else if(orderColumn.equals("male") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY MALE ASC";
			}else if(orderColumn.equals("male") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY MALE DESC";
			}else if(orderColumn.equals("birthday") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY BIRTHDAY ASC";
			}else if(orderColumn.equals("birthday") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY BIRTHDAY DESC";
			}else if(orderColumn.equals("address") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY ADDRESS ASC";
			}else if(orderColumn.equals("address") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY ADDRESS DESC";
			}else if(orderColumn.equals("phone") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY PHONE ASC";
			}else if(orderColumn.equals("phone") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY PHONE DESC";
			}else if(orderColumn.equals("mobile") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY MOBILE ASC";
			}else if(orderColumn.equals("mobile") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY MOBILE DESC";
			}else if(orderColumn.equals("score") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY SCORE ASC";
			}else if(orderColumn.equals("score") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY SCORE DESC";
			}
		}
		System.out.println("[order clause] = " + order);
		return order;
	}
	
	public String getLimitClause(Map<String,Object> params){
		String limit = "";
		Integer startIndex = (params.get("startIndex")==null) ? -1 : (Integer)params.get("startIndex");
		Integer rowCount = (params.get("rowCount")==null) ? -1 : (Integer)params.get("rowCount");
		if(startIndex > 0 && rowCount >0){
			limit = " LIMIT "+ startIndex + "," + rowCount;
		}
		System.out.println("[limit clause] = " + limit);
		return limit;
	}
	
	@SuppressWarnings("deprecation")
	public boolean exists(Long id) {
		String sql = "SELECT COUNT(*) FROM TB_USER WHERE ID = ?";
		return (jdbcTemplate.queryForLong(sql,id) == 1);
		
	}

	@SuppressWarnings("deprecation")
	public long count() {
		String sql = "SELECT COUNT(*) FROM TB_USER";
		return jdbcTemplate.queryForLong(sql);
	}

	@SuppressWarnings("deprecation")
	public long countByParams(Map<String, Object> params) {
		String sql = "SELECT COUNT(*) FROM TB_USER ";
		sql += getWhereClause(params);
		sql += getLimitClause(params);
		System.out.println("[sql statement] = " + sql);
		return template.queryForLong(sql, params);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public User insert(User user) {
		String sql = "INSERT INTO TB_USER "
				+ "(UUID, PASSWD, FIRST_NAME, LAST_NAME, DISPLAY_NAME, MALE, BIRTHDAY, ADDRESS, PHONE, MOBILE, SCORE) "
				+ "values "
				+ "(:uuid, :passwd, :firstName, :lastName, :displayName, :male, :birthday, :address, :phone, :mobile, :score)";
		Map<String,Object> params = getParams(user);
		KeyHolder keyholder = new GeneratedKeyHolder();
		template.update(sql, new MapSqlParameterSource(params), keyholder);
		user.setId(keyholder.getKey().longValue());
		return user;
	}

	public User insertWith(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public User update(User user) {
		String sql = getUpdateSQL();
		Map<String,Object> params = getParams(user);
		template.update(sql, params);
		return user;
	}
	
	public Map<String,Object> getParams(User user){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", user.getId());
		params.put("uuid", user.getUuid());
		params.put("passwd", user.getPasswd());
		params.put("firstName", user.getFirstName());
		params.put("lastName", user.getLastName());
		params.put("displayName", user.getDisplayName());
		params.put("male", user.isMale());
		params.put("birthday", user.getBirthday());
		params.put("address", user.getAddress());
		params.put("phone", user.getPhone());
		params.put("mobile", user.getMobile());
		params.put("score", user.getScore());
		return params;
	}
	
	public String getUpdateSQL(){
		String sql = "UPDATE TB_USER SET " +
				"FIRST_NAME = :firstName, " +
				"LAST_NAME = :lastName, " +
				"DISPLAY_NAME = :displayName, " +
				"MALE = :male, " +
				"BIRTHDAY = :birthday, " +
				"ADDRESS = :address, " +
				"PHONE = :phone, " + 
				"MOBILE = :mobile, " +
				"SCORE = :score " + 
				"WHERE ID = :id";
		return sql;
	}

	public User updateWith(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void delete(Long id) {
		String sql = "DELETE FROM TB_USER WHERE ID = :id";
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		template.update(sql, param);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void delete(User user) {
		delete(user.getId());		
	}
	
	//Inner Class，將資料庫取出的每一行Row封裝成對應的Usert物件
	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("ID"));
			user.setUuid(rs.getString("UUID"));
			user.setPasswd(rs.getString("PASSWD"));
			user.setFirstName(rs.getString("FIRST_NAME"));
			user.setLastName(rs.getString("LAST_NAME"));
			user.setDisplayName(rs.getString("DISPLAY_NAME"));
			user.setMale(rs.getBoolean("MALE"));
			user.setBirthday(rs.getDate("BIRTHDAY"));
			user.setAddress(rs.getString("ADDRESS"));
			user.setPhone(rs.getString("PHONE"));
			user.setMobile(rs.getString("MOBILE"));
			user.setScore(rs.getInt("SCORE"));
			return user;
		}
		
	}

}
