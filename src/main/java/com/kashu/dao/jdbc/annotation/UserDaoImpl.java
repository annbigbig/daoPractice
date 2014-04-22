package com.kashu.dao.jdbc.annotation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
import com.kashu.dao.jdbc.annotation.domain.Article;
import com.kashu.dao.jdbc.annotation.domain.Category;
import com.kashu.dao.jdbc.annotation.domain.Group;
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

	public User queryOne(Long id) {
		String sql = "SELECT * FROM TB_USER WHERE ID = :id";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return template.queryForObject(sql, params, new UserMapper());
	}
	
	public List<User> queryAll() {
		String sql = "SELECT * FROM TB_USER";
		return template.query(sql, new UserMapper());
	}

	public List<User> queryList(Map<String, Object> params) {
		String sql = "SELECT * FROM TB_USER u";
		sql += getWhereClause(params);
		sql += getOrderClause(params);
		sql += getLimitClause(params);
		System.out.println("[sql statement] = " + sql);
		return template.query(sql, params, new UserMapper());
	}
	
	public User queryOneWith(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> queryAllWith() {
		Map<String,Object> params = new HashMap<String,Object>();
		String sql = getJoinSQL(params);
		return null;
	}

	public List<User> queryListWith(Map<String, ?> params) {
		// TODO Auto-generated method stub
		return null;
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
	public long countList(Map<String, Object> params) {
		String sql = "SELECT COUNT(*) FROM TB_USER u";
		sql += getWhereClause(params);
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

	public User insertBatch(List<User> users) {
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

	public User updateBatch(List<User> users) {
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
	
	//不要把%寫進SQL statement裡面，會導致悲慘結果，什麼都搜不到
	//Don't write % into your SQL statement , this will cause database return nothing
	public String getWhereClause(Map<String,Object> params){
		String operator = (params.get("operator")==null) ? "" : (String) params.get("operator");
		String where = "";
		if(params.get("id")!=null){
			where = " WHERE u.ID = :id";
		}else if (params.get("uuid")!=null && params.get("passwd")!=null){
			where = " WHERE u.UUID = :uuid AND u.PASSWD = :passwd";
		}else if (params.get("uuid")!=null && operator.equals("like")){
			where = " WHERE u.UUID LIKE :uuid";
		}else if (params.get("uuid")!=null && operator.equals("eq")){
			where = " WHERE u.UUID = :uuid";
		}else if (params.get("firstName")!=null && operator.equals("like")){
			where = " WHERE u.FIRST_NAME LIKE :firstName";
		}else if (params.get("firstName")!=null && operator.equals("eq")){
			where = " WHERE u.FIRST_NAME = :firstName";
		}else if(params.get("lastName")!=null && operator.equals("like")){
			where = " WHERE u.LAST_NAME LIKE :lastName";
		}else if(params.get("lastName")!=null && operator.equals("eq")){
			where = " WHERE u.LAST_NAME = :lastName";
		}else if(params.get("displayName")!=null && operator.equals("like")){
			where = " WHERE u.DISPLAY_NAME LIKE :displayName";
		}else if(params.get("displayName")!=null && operator.equals("eq")){
			where = " WHERE u.DISPLAY_NAME = :displayName";
		}else if(params.get("male")!=null){
			where = " WHERE u.MALE = :male";
		}else if(params.get("birthday")!=null && operator.equals("gt")){
			where = " WHERE u.BIRTHDAY > :birthday";
		}else if(params.get("birthday")!=null && operator.equals("lt")){
			where = " WHERE u.BIRTHDAY < :birthday";
		}else if(params.get("birthday")!=null && operator.equals("eq")){
			where = " WHERE u.BIRTHDAY = :birthday";
		}else if(params.get("date1")!=null && params.get("date2")!=null){
			where = " WHERE u.BIRTHDAY BETWEEN :date1 AND :date2";
		}else if(params.get("address")!=null){
			where = " WHERE u.ADDRESS LIKE :address";
		}else if(params.get("phone")!=null){
			where = " WHERE u.PHONE LIKE :phone";
		}else if(params.get("mobile")!=null){
			where = " WHERE u.MOBILE LIKE :mobile";
		}else if(params.get("score")!=null && operator.equals("gt")){
			where = " WHERE u.SCORE > :score";
		}else if(params.get("score")!=null && operator.equals("lt")){
			where = " WHERE u.SCORE < :score";
		}else if(params.get("score")!=null && operator.equals("eq")){
			where = " WHERE u.SCORE = :score";
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
				order = " ORDER BY u.UUID ASC";
			}else if(orderColumn.equals("uuid") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.UUID DESC";
			}else if(orderColumn.equals("firstName") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.FIRST_NAME ASC";
			}else if(orderColumn.equals("firstName") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.FIRST_NAME DESC";
			}else if(orderColumn.equals("lastName") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.LAST_NAME ASC";
			}else if(orderColumn.equals("lastName") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.LAST_NAME DESC";
			}else if(orderColumn.equals("displayName") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.DISPLAY_NAME ASC";
			}else if(orderColumn.equals("displayName") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.DISPLAY_NAME DESC";
			}else if(orderColumn.equals("male") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.MALE ASC";
			}else if(orderColumn.equals("male") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.MALE DESC";
			}else if(orderColumn.equals("birthday") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.BIRTHDAY ASC";
			}else if(orderColumn.equals("birthday") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.BIRTHDAY DESC";
			}else if(orderColumn.equals("address") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.ADDRESS ASC";
			}else if(orderColumn.equals("address") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.ADDRESS DESC";
			}else if(orderColumn.equals("phone") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.PHONE ASC";
			}else if(orderColumn.equals("phone") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.PHONE DESC";
			}else if(orderColumn.equals("mobile") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.MOBILE ASC";
			}else if(orderColumn.equals("mobile") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.MOBILE DESC";
			}else if(orderColumn.equals("score") && orderType.equalsIgnoreCase("asc")){
				order = " ORDER BY u.SCORE ASC";
			}else if(orderColumn.equals("score") && orderType.equalsIgnoreCase("desc")){
				order = " ORDER BY u.SCORE DESC";
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
	
	//  [TB_USER] --- [JN_USER_GROUP] --- [TB_GROUP] --- [JN_USER_USER] --- [TB_USER] --- [TB_ARTICLE] 
	public String getJoinSQL(Map<String,Object> params){
		String sql = "SELECT ";
			sql += "u.ID, u.UUID, u.PASSWD, u.FIRST_NAME, u.LAST_NAME, u.DISPLAY_NAME, ";
			sql += "u.MALE, u.BIRTHDAY, u.ADDRESS, u.PHONE, u.MOBILE, u.SCORE, ";
			sql += "g.ID as GROUP_ID, g.NAME as GROUP_NAME, ";
			sql += "u2.ID as FRIEND_ID, u2.UUID as FRIEND_UUID, u2.FIRST_NAME as FRIEND_FIRST_NAME, ";
			sql += "u2.LAST_NAME as FRIEND_LAST_NAME, u2.DISPLAY_NAME as FRIEND_DISPLAY_NAME, ";
			sql += "u2.MALE as FRIEND_MALE, u2.BIRTHDAY as FRIEND_BIRTHDAY, u2.ADDRESS as FRIEND_ADDRESS, ";
			sql += "u2.PHONE AS FRIEND_PHONE, u2.MOBILE as FRIEND_MOBILE, u2.SCORE as FRIEND_SCORE, ";
			sql += "a.ID as ARTICLE_ID, a.SUBJECT as ARTICLE_SUBJECT, a.CONTENT as ARTICLE_CONTENT, ";
			sql += "a.CREATED_TIME as ARTICLE_CREATED_TIME, a.LAST_MODIFIED as ARTICLE_LAST_MODIFIED, ";
			sql += "a.CATEGORY_ID as ARTICLE_CATEGORY_ID ";
			sql += "c.ID as CATEGORY_ID, c.NAME as CATEGORY_NAME, c.PID as CATEGORY_PID";
			sql += "FROM TB_USER u ";
			sql += "LEFT OUTER JOIN JN_USER_GROUP ug ON (u.ID = ug.USER_ID) ";
			sql += "LEFT OUTER JOIN TB_GROUP g ON (ug.GROUP_ID = g.ID) ";
			sql += "LEFT OUTER JOIN JN_USER_USER uu ON (u.ID = uu.UID_1) ";
			sql += "LEFT OUTER JOIN TB_USER u2 ON (uu.UID_2 = u2.ID) ";
			sql += "LEFT OUTER JOIN TB_ARTICLE a ON (u.ID = a.USER_ID) ";
			sql += "LEFT OUTER JOIN TB_CATEGORY c ON (a.CATEGORY_ID = c.ID)";
			
			String operator = (params.get("operator")==null) ? "" : (String) params.get("operator");
			String where = "";
			if (params.get("id")!=null){
				where = " WHERE u.ID = :id";
			}else if (params.get("uuid")!=null){
				where = " WHERE u.UUID LIKE :uuid";
			}else if (params.get("firstName")!=null){
				where = " WHERE u.FIRST_NAME LIKE :firstName";
			}else if(params.get("lastName")!=null){
				where = " WHERE u.LAST_NAME LIKE :lastName";
			}else if(params.get("displayName")!=null){
				where = " WHERE u.DISPLAY_NAME LIKE :displayName";
			}else if(params.get("male")!=null){
				where = " WHERE u.MALE = :male";
			}else if(params.get("birthday")!=null && operator.equals("gt")){
				where = " WHERE u.BIRTHDAY > :birthday";
			}else if(params.get("birthday")!=null && operator.equals("lt")){
				where = " WHERE u.BIRTHDAY < :birthday";
			}else if(params.get("birthday")!=null && operator.equals("eq")){
				where = " WHERE u.BIRTHDAY = :birthday";
			}else if(params.get("date1")!=null && params.get("date2")!=null){
				where = " WHERE u.BIRTHDAY BETWEEN :date1 AND :date2";
			}else if(params.get("address")!=null){
				where = " WHERE u.ADDRESS LIKE :address";
			}else if(params.get("phone")!=null){
				where = " WHERE u.PHONE LIKE :phone";
			}else if(params.get("mobile")!=null){
				where = " WHERE u.MOBILE LIKE :mobile";
			}else if(params.get("score")!=null && operator.equals("gt")){
				where = " WHERE u.SCORE > :score";
			}else if(params.get("score")!=null && operator.equals("lt")){
				where = " WHERE u.SCORE < :score";
			}else if(params.get("score")!=null && operator.equals("eq")){
				where = " WHERE u.SCORE = :score";
			}
			
			sql += where;
			sql += " ORDER BY u.ID, g.ID, u2.ID, a.ID ASC;";
			System.out.println("[Join SQL] = " + sql);

		return sql;
	}
	
	//Inner Class，將資料庫取出的每一行Row封裝成對應的Usert物件 (僅適用於查詢 TB_USER單表時)
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

	private static final class UserExtractor implements ResultSetExtractor<List<User>> {
		public List<User> extractData(ResultSet rs) throws SQLException,DataAccessException {
			Map<Long,User> userMap = new HashMap<Long,User>();
			User user = null;
			Group group = null;
			User friend = null;
			Article article = null;
			Category category = null;
			
			while(rs.next()){
				Long id = rs.getLong("ID");
				if(userMap.get(id)==null){	//new user
					user = new User();
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
					userMap.put(id, user);
				}
				group = new Group();
				group.setId(rs.getLong("GROUP_ID"));
				group.setName(rs.getString("GROUP_NAME"));
				if(!user.getGroups().contains(group) && group.getId()!=null){
					user.getGroups().add(group);
				}
				friend = new User();
				friend.setId(rs.getLong("FRIEND_ID"));
				friend.setUuid(rs.getString("FRIEND_UUID"));
				friend.setFirstName(rs.getString("FRIEND_FIRST_NAME"));
				friend.setLastName(rs.getString("FRIEND_LAST_NAME"));
				friend.setDisplayName(rs.getString("FRIEND_DISPLAY_NAME"));
				friend.setMale(rs.getBoolean("FRIEND_MALE"));
				friend.setBirthday(rs.getDate("FRIEND_BIRTHDAY"));
				friend.setAddress(rs.getString("FRIEND_ADDRESS"));
				friend.setPhone(rs.getString("FRIEND_PHONE"));
				friend.setMobile(rs.getString("FRIEND_MOBILE"));
				friend.setScore(rs.getInt("FRIEND_SCORE"));
				if(!user.getFriends().contains(friend) && friend.getId()!=null){
					user.getFriends().add(friend);
				}
				category = new Category();
				category.setId(rs.getLong("CATEGORY_ID"));
				category.setName(rs.getString("CATEGORY_NAME"));
				
				article = new Article();
				article.setId(rs.getLong("ARTICLE_ID"));
				article.setSubject(rs.getString("ARTICLE_SUBJECT"));
				article.setContent(rs.getString("ARTICLE_CONTENT"));
				article.setCreatedTime(rs.getDate("ARTICLE_CREATED_TIME"));
				article.setLastModified(rs.getDate("ARTICLE_LAST_MODIFIED"));
				article.setUser(user);
				article.setCategory(category);
				if(!user.getArticles().contains(article) && article.getId()!=null){
					user.getArticles().add(article);
				}
			}
			
			return new ArrayList<User>(userMap.values());
		}
		
	}
	
}
