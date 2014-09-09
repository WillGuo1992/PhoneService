package cn.com.navia.PhoneService.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.com.navia.PhoneService.bean.PhoneUser;
import cn.com.navia.PhoneService.bean.UserLogInfo;


@Repository
public class UserDao extends PhoneDao {
	private static String GET_USER_BY_NAME = "select * from user where name = ?";
	private static String INSERT_NEW_USER = "insert into user (email, name, password, device_mac, state, isregisted) values (?, ?, ?, ?, ?, ?)";
	private static String IS_NAME_EXIST = "select count(*) from user where name = ?";
	private static String UPDATE_MAC_BY_UID = "update user set device_mac = ? where uid = ?";
	private static String SAVE_USER_LOGINFO = "insert into userlog(uid, name, device_mac, login_time) values(?, ?, ?, ?)";
	private static String GET_LAST_LOGIN_UID_BY_MAC = "select * from userlog where device_mac = ? order by login_time desc limit 1"; 
	private static SimpleDateFormat simpleDF = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final class phoneUserMapper implements RowMapper<PhoneUser> {
		@Override
		public PhoneUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			int uid = rs.getInt("uid");
			String email = rs.getString("email");
			String name = rs.getString("name");
			String password = rs.getString("password");
			String device_mac = rs.getString("device_mac");
			byte state = rs.getByte("state");
			boolean reg = rs.getBoolean("isregisted");
			return new PhoneUser(uid, email, name, password, device_mac, state, reg);
		}
	}
	
	public PhoneUser getUserByName(String name) throws Exception {
		List<PhoneUser> pus = phoneJdbcTemplate.query(GET_USER_BY_NAME, new Object[] { name }, new phoneUserMapper());
		return (pus.isEmpty()) ? null : pus.get(0);

	}

	public boolean insertNewUser(PhoneUser pu) throws Exception {
		int rowsAffected = phoneJdbcTemplate.update(INSERT_NEW_USER, pu.getEmail(), pu.getName(), 
				pu.getPassword(), pu.getDid(), pu.getState(), pu.isReg());
		return (rowsAffected > 0) ? true : false;
	}

	public boolean isNameExist(String name) throws Exception {
		int count = phoneJdbcTemplate.queryForObject(IS_NAME_EXIST, Integer.class, name);
		return (count > 0) ? true : false;
	}

	public boolean updateMacByUid(int uid, String device_mac) throws Exception {
		int rowsAffected = phoneJdbcTemplate.update(UPDATE_MAC_BY_UID, device_mac, uid);
		return (rowsAffected > 0) ? true : false;
	}

	public boolean saveUserLogInfo(UserLogInfo ULinfo) throws Exception {
		int rowsAffected = phoneJdbcTemplate.update(SAVE_USER_LOGINFO, ULinfo.getUid(), ULinfo.getName(),
				ULinfo.getDevice_mac(), simpleDF.format(ULinfo.getLogin_time()));
		return (rowsAffected > 0) ? true : false;
	}

	public int getLastLoginUidByMac(String device_mac) throws Exception{
		Integer uid = null;
		try{
			uid = phoneJdbcTemplate.queryForObject(GET_LAST_LOGIN_UID_BY_MAC, Integer.class, device_mac);
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			return 0;
		}
		return (uid == null) ? 0 : uid.intValue();
	}

}
