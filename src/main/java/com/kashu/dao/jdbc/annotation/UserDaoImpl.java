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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
	private String select_by_uuid = "SELECT * FROM TB_USER WHERE UUID LIKE '%:uuid%'";
	private String select_by_first_name = "SELECT * FROM TB_USER WHERE FIRST_NAME LIKE '%:first_name%'";
	private String select_by_last_name = "SELECT * FROM TB_USER WHERE LAST_NAME LIKE '%:last_name%'";
	private String select_by_display_name = "SELECT * FROM TB_USER WHERE DISPLAY_NAME LIKE '%:display_name%'";
	private String select_by_male = "SELECT * FROM TB_USER WHERE MALE = :male";
	private String select_by_birthday_gt = "SELECT * FROM TB_USER WHERE BIRTHDAY > :birthday";
	private String select_by_birthday_lt = "SELECT * FROM TB_USER WHERE BIRTHDAY < :birthday";
	private String select_by_birthday_eq = "SELECT * FROM TB_USER WHERE BIRTHDAY = :birthday";
	private String select_by_birthday_between = "SELECT * FROM TB_USER WHERE BIRTHDAY BETWEEN :date1 AND :date2";
	private String select_by_address = "SELECT * FROM TB_USER WHERE ADDRESS LIKE '%:address%'";
	private String select_by_phone = "SELECT * FROM TB_USER WHERE PHONE LIKE '%:phone%'";
	private String select_by_score_gt = "SELECT * FROM TB_USER WHERE SCORE > :score";
	private String select_by_score_lt = "SELECT * FROM TB_USER WHERE SCORE < :score";
	private String select_by_score_eq = "SELECT * FROM TB_USER WHERE SCORE = :score";
	private String order_by_asc = " ORDER BY :order_column ASC";
	private String order_by_desc = " ORDER BY :order_column DESC";
	
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
	}

	public List<User> findAll() {
		String sql = "SELECT * FROM TB_USER";
		return template.query(sql, new UserMapper());
	}

	@Override
	public List<User> findByParams(Map<String, Object> params) {
		String sql = "";
		if (params.get("uuid")!=null){
			sql = select_by_uuid;
		}else if (params.get("first_name")!=null){
			sql = select_by_first_name;
		}else if(params.get("last_name")!=null){
			sql = select_by_last_name;
		}else if(params.get("display_name")!=null){
			sql = select_by_display_name;
		}
		
		if (params.get("order_column")!=null){
			String order_type = (String)params.get("order_type");
			if(order_type!=null){
				if(order_type.equals("DESC")){
					sql += order_by_desc;
				}else{
					sql += order_by_asc;
				}
			}
		}
		return null;
	}		
	
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public User insert(User user) {
		String sql = "INSERT INTO TB_USER "
				+ "(UUID, PASSWD, FIRST_NAME, LAST_NAME, DISPLAY_NAME, MALE, BIRTHDAY, ADDRESS, PHONE, MOBILE, SCORE) "
				+ "values "
				+ "(:uuid, :passwd, :first_name, :last_name, :display_name, :male, :birthday, :address, :phone, :mobile, :score)";
		Map<String,Object> params = new HashMap<String,Object>();
			params.put("uuid", user.getUuid());
			params.put("passwd", user.getPasswd());
			params.put("first_name", user.getFirstName());
			params.put("last_name", user.getLastName());
			params.put("display_name", user.getDisplayName());
			params.put("male", user.isMale());
			params.put("birthday", user.getBirthday());
			params.put("address", user.getAddress());
			params.put("phone", user.getPhone());
			params.put("mobile", user.getMobile());
			params.put("score", user.getScore());
			KeyHolder keyholder = new GeneratedKeyHolder();
			template.update(sql, new MapSqlParameterSource(params), keyholder);
			user.setId(keyholder.getKey().longValue());
		return user;
	}

	public User insertWith(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public User update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public User updateWith(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void delete(User entity) {
		// TODO Auto-generated method stub
		
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
