package cn.com.navia.PhoneService.server.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




import cn.com.navia.PhoneService.assist.ShaUtil;
import cn.com.navia.PhoneService.bean.EntityMsg;
import cn.com.navia.PhoneService.bean.PhoneUser;
import cn.com.navia.PhoneService.bean.RespBody;
import cn.com.navia.PhoneService.bean.RespValue;
import cn.com.navia.PhoneService.bean.UserLogInfo;
import cn.com.navia.PhoneService.dao.UserDao;


@RestController
@RequestMapping("/userAction/user")
public class UserController {

	@Resource
	private UserDao userDao;
	@Resource
	private RestClient restClient;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespBody userLogin(HttpServletRequest request, @RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "did") String phoneMac){
		RespValue rv = null;
		try{
			PhoneUser pu = userDao.getUserByName(username);
			if (pu == null)
				rv = new RespValue((short)-1, "用户不存在！");
			else if (pu.getPassword().compareTo(ShaUtil.shaDigest(password)) != 0 )
				rv = new RespValue((short)-2, "密码错误！");
			else{
				String device_mac = phoneMac.toUpperCase();
				String updateMac = "";
				String saveLogInfo = "";
				int uid = pu.getUid();
				if (device_mac.compareTo(pu.getDid()) != 0 ){
					if (!userDao.updateMacByUid(uid, device_mac))
						updateMac = "MAC地址更新至数据库失败！";
				}
				if (!userDao.saveUserLogInfo(new UserLogInfo(uid, username, device_mac, null)))
					saveLogInfo = "保存用户登录信息失败！";
				HttpSession session = request.getSession();
				session.setAttribute("LoginSession", device_mac);
				session.setMaxInactiveInterval(60 * 30); //设置session超时时限为30分钟
				rv = new RespValue((short)0, "登录成功！" + updateMac + saveLogInfo);
			}
		}
		catch(Exception e){
			log.error("userLogin error: {}", e.getMessage());
			rv = new RespValue((short)-9, "服务抛出异常！");
		}
		log.info("/login: username: {}, phoneMac: {}, {}", username, phoneMac.toUpperCase(), rv);
		return new RespBody(rv);
	}


	@RequestMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespBody userLogout(HttpServletRequest request) {
		RespValue rv = null;
		String sessionMac = null;
		try{
			HttpSession session = request.getSession();
			sessionMac = (String) session.getAttribute("LoginSession");
			if (sessionMac != null)
				session.removeAttribute("LoginSession");
			session.invalidate();
			rv = new RespValue((short)0, "注销成功！");
		}
		catch(Exception e){
			log.error("userLogout error: {}", e.getMessage());
			rv = new RespValue((short)-9, "服务抛出异常！");
		}
		log.info("/logout: sessionMac: {}, {}", sessionMac, rv);
		return new RespBody(rv);
	}


	@RequestMapping(value = "/registe", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespBody userRegister(HttpServletRequest request, @RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "did") String phoneMac){
		RespValue rv = null;
		try{
			if ("".equals(username) || "".equals(password) || "".equals(phoneMac))
				rv = new RespValue((short)-1, "用户名、密码或MAC地址为空！");
			else if (userDao.isNameExist(username))
				rv = new RespValue((short)-2, "用户名已被注册！");
			else{
				PhoneUser pu = new PhoneUser();
				pu.setEmail("");
				pu.setName(username);
				pu.setPassword(ShaUtil.shaDigest(password));
				pu.setDid(phoneMac.toUpperCase());
				pu.setState((byte)1);
				pu.setReg(true);
				if (userDao.insertNewUser(pu))
					rv = new RespValue((short)0, "注册成功！");
				else
					rv = new RespValue((short)-3, "保存数据失败！");
			}
		}
		catch(Exception e){
			log.error("userRegister error: {}", e.getMessage());
			rv = new RespValue((short)-9, "服务抛出异常！");
		}
		log.info("/registe: username: {}, phoneMac: {}, {}", username, phoneMac.toUpperCase(), rv);
		return new RespBody(rv);
	}


	@RequestMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespBody userLocation(HttpServletRequest request, @RequestParam(value = "did") String phoneMac){
		RespValue rv = null;
		EntityMsg msg = null;
		try{
			String device_mac = phoneMac.toUpperCase();
			HttpSession session = request.getSession();
			String sessionMac = (String) session.getAttribute("LoginSession");
			if (sessionMac == null)
				rv = new RespValue((short)-2, "会话超时请重新登录！");
			else if (device_mac.compareTo(sessionMac) != 0)
				rv = new RespValue((short)-3, "所请求MAC地址与登录时不符！");
			else{
				msg = restClient.getLocationByMac(sessionMac);
				if (msg == null)
					rv = new RespValue((short)-1, "获取坐标失败！");
				else
					rv = new RespValue((short)0, "获取坐标成功！");
			}
		}
		catch(Exception e){
			log.error("userLocation error: {}", e.getMessage());
			rv = new RespValue((short)-9, "服务抛出异常！");
		}
		log.info("/location: phoneMac: {}, {}", phoneMac.toUpperCase(), rv);
		return new RespBody(rv, msg);
	}

}
