package cn.com.navia.PhoneService.server.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.navia.PhoneService.bean.BeanAB;
import cn.com.navia.PhoneService.bean.BeanBike;
import cn.com.navia.PhoneService.bean.BeanBikeRetMsg;
import cn.com.navia.PhoneService.bean.BeanBikeStatus;
import cn.com.navia.PhoneService.bean.BeanBusDynaInfo;
import cn.com.navia.PhoneService.bean.BeanBusStatus;
import cn.com.navia.PhoneService.bean.BeanCoachBasic;
import cn.com.navia.PhoneService.bean.BeanCoachInfo;
import cn.com.navia.PhoneService.bean.BeanCoachItem;
import cn.com.navia.PhoneService.bean.BeanCoachRetItem;
import cn.com.navia.PhoneService.bean.BeanCoachRetMsg;
import cn.com.navia.PhoneService.bean.BeanCoachStat;
import cn.com.navia.PhoneService.bean.BeanFloor;
import cn.com.navia.PhoneService.bean.BeanHub;
import cn.com.navia.PhoneService.bean.BeanItemBody;
import cn.com.navia.PhoneService.bean.BeanItemHead;
import cn.com.navia.PhoneService.bean.BeanMB;
import cn.com.navia.PhoneService.bean.BeanPoiSearch;
import cn.com.navia.PhoneService.bean.BeanPoiSelected;
import cn.com.navia.PhoneService.bean.BeanRetMsg;
import cn.com.navia.PhoneService.bean.BeanRetVehicle;
import cn.com.navia.PhoneService.bean.BeanSearchItem;
import cn.com.navia.PhoneService.bean.BeanShop;
import cn.com.navia.PhoneService.bean.BeanSpecInfo;
import cn.com.navia.PhoneService.bean.BeanTrans;
import cn.com.navia.PhoneService.bean.BeanTransInfo;
import cn.com.navia.PhoneService.bean.RespHub;
import cn.com.navia.PhoneService.bean.RespList;
import cn.com.navia.PhoneService.dao.HubDao;



@RestController
@RequestMapping("/TrHub/V0")
public class HubControllerV0 {

	@Resource
	private HubDao hubDao;
	@Resource
	private RestClient restClient;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static SimpleDateFormat simpleDF = new SimpleDateFormat("yyyyMMddHHmmss");

	@Value("${trhub.content.baseurl}")
	private String contentBaseUrl;

	@Value("${trhub.mainbanner.path}")
	private String mbPath;

	@Value("${trhub.adbanner.path}")
	private String adPath;

	@Value("${trhub.item.fullpath}")
	private String itemFullPath;


	private boolean isTokenValid(String token){
		boolean result = false;
		try {
			result = hubDao.isTokenExist(token);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/hubList", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub hubList(HttpServletRequest request, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanHub[] hubs = null;
		try {
			hubs = hubDao.getHubList();
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (hubs == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(hubs));
	}

	@RequestMapping(value = "/" +
			"", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub hubNearby(HttpServletRequest request, @RequestParam(value = "lon") float lon,
			@RequestParam(value = "lat") float lat, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanHub[] hubs = null;

		try {
			hubs = hubDao.getHubList();
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
		if (hubs == null)
			return new RespHub((byte) -3, "查询结果为空！");

		float disLon, disLat, distance;
		float minDis = 300;
		byte minIndex = 0;
		for(byte i=0; i<hubs.length; i++){
			disLon = lon - hubs[i].getLon();
			disLat = lat - hubs[i].getLat();
			distance = (float) Math.sqrt(disLon*disLon + disLat*disLat);
			if (distance < minDis){
				minDis = distance;
				minIndex = i;
			}
		}

		log.debug("/hubNearby: lon: {}, lat: {}", lon, lat);
		return new RespHub((byte) 0, "获取成功！", hubs[minIndex]);
	}

	@RequestMapping(value = "/mainBanner", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub mainBanner(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanMB[] mbs = null;
		try {
			mbs = hubDao.getMainBannerListByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (mbs == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(contentBaseUrl + mbPath, mbs));
	}

	@RequestMapping(value = "/adBanner", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub adBanner(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanAB[] abs = null;
		try {
			abs = hubDao.getAdBannerListByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (abs == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(contentBaseUrl + adPath, abs));
	}

	@RequestMapping(value = "/itemList", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub itemList(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam(value = "page") short page, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		if (page <= 0)
			return new RespHub((byte) -1, "参数错误！");

		BeanItemHead[] itemheads = null;
		try {
			itemheads = hubDao.getItemListByHid(hid, page);
			//itemheads = hubDao.getItemListByHid((short) 1, page);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (itemheads == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(itemFullPath, itemheads));
	}

	@RequestMapping(value = "/itemInfo", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub itemInfo(HttpServletRequest request, @RequestParam(value = "itemId") int itemId,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanItemBody itembody = null;
		try {
			itembody = hubDao.getItemInfoByItemId(itemId);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
		if (itembody == null)
			return new RespHub((byte) -3, "查询结果为空！");

		itembody.setPic(itemFullPath + itembody.getPic());
		itembody.setPubTime(itembody.getPubTime().substring(0, 16));
		return new RespHub((byte) 0, "获取成功！", itembody);
	}

	@RequestMapping(value = "/bikeList", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub bikeList(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanBike[] bikes = null;
		try {
			bikes = hubDao.getBikeListByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (bikes == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(bikes));
	}

	@RequestMapping(value = "/bikeStatus", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub bikeStatus(HttpServletRequest request, @RequestParam(value = "statCode") String statCode,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		String pubTime = "";
		short totalNum = 0;
		short lockedNum = 0;
		try {
			totalNum = hubDao.getTotalNumByStatCode(statCode);
			if (totalNum == 0)
				return new RespHub((byte) -2, "网点编号无效或查询失败！");

			BeanBikeRetMsg retMsg = restClient.getBikeRealtimeData(statCode);
			if (retMsg==null)
				return new RespHub((byte) -2, "实时数据网络请求失败！");

			pubTime = retMsg.getPublishTime();
			if (retMsg.getInfo().compareTo("ERR") == 0 || pubTime.length() == 0)
				return new RespHub((byte) -3, retMsg.getMsg());

			lockedNum = retMsg.getStatInfo().getLockedNum();
			totalNum = (short) (lockedNum + retMsg.getStatInfo().getUnlockNum());
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

//		lockedNum = (short) (totalNum/2 - 1 + Math.round(Math.random() * 10));
//		pubTime = simpleDF.format(new Date(System.currentTimeMillis() - 60*1000));
		
		return new RespHub((byte) 0, "获取成功！", new BeanBikeStatus(pubTime, totalNum, lockedNum));
	}

	@RequestMapping(value = "/busList", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub busList(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam(value = "page") short page, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		if (page <= 0)
			return new RespHub((byte) -1, "参数错误！");

		BeanTrans[] busList = null;
		try {
			busList = hubDao.getBusListByHid(hid, page);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (busList == null)
			return new RespHub((byte) -3, "查询结果为空！");

		return new RespHub((byte) 0, "获取列表成功！", new RespList(busList));
	}

	@RequestMapping(value = "/transInfo", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub transInfo(HttpServletRequest request, @RequestParam(value = "route") String route,
			@RequestParam(value = "updown") byte updown, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanTransInfo transInfo = null;
		try {
			transInfo = hubDao.getTransInfoByRouteUpdownHid(route, updown, hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (transInfo == null)
			return new RespHub((byte) -3, "查询结果为空！");
		
		return new RespHub((byte) 0, "获取成功！", transInfo);
	}

	@RequestMapping(value = "/airportBus", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub airportBus(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanTrans[] airportBusList = null;
		try {
			airportBusList = hubDao.getAirportBusByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (airportBusList == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(airportBusList));
	}

	@RequestMapping(value = "/airportExp", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub airportExp(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanTrans[] airportExpList = null;
		try {
			airportExpList = hubDao.getAirportExpByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (airportExpList == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(airportExpList));
	}

	@RequestMapping(value = "/busStatus", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub busStatus(HttpServletRequest request, @RequestParam(value = "route") String route,
			@RequestParam(value = "updown") byte updown, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		byte curSeq = 0;
		String pubTime = "";
		BeanBusDynaInfo[] timeArray = null;
		String[] bus123Str = {"暂无实时数据", "暂无实时数据", "暂无实时数据"};

		try {
			curSeq = hubDao.getCurrentSeqByRouteUpdownHid(route, updown, hid);
			if (curSeq==0)
				return new RespHub((byte) -2, "查询失败，此线路在该枢纽无站点！");

			String lastStr = route.substring(route.length()-1);
			if ((curSeq==1) && (lastStr.compareTo("内") != 0) && (lastStr.compareTo("外") != 0) && !route.contains("机")) {
				updown = (byte) ((updown==0) ? 1 : 0);
				curSeq = hubDao.getCurrentSeqByRouteUpdownHid(route, updown, hid);
				if (curSeq==0)
					return new RespHub((byte) -2, "查询失败，此公交反向线路不经过该枢纽！");
			}

			BeanRetMsg retMsg = restClient.getBusRealtimeData(route, updown, curSeq);
			if (retMsg==null)
				return new RespHub((byte) -2, "实时数据网络请求失败！");

			if (retMsg.getInfo().compareTo("OK") == 0 && retMsg.getMsg().compareTo("无车辆数据") == 0)
				return new RespHub((byte) 0, "获取成功！", new BeanBusStatus("", bus123Str[0], bus123Str[1], bus123Str[2]));

			pubTime = retMsg.getPublishTime();
			byte timeListLen = (byte) retMsg.getStatTimeList().length;
			if (retMsg.getInfo().compareTo("ERR") == 0 || pubTime.length() == 0 || timeListLen == 0)
				return new RespHub((byte) -3, retMsg.getMsg());

			long fromLastPub = Long.MAX_VALUE;
			fromLastPub = System.currentTimeMillis() - simpleDF.parse(pubTime).getTime();
			if (fromLastPub > 3600*1000L){
				log.error("/busStatus: 实时数据超过1小时未更新，或者发布时间格式错误！最后更新时间：{}", pubTime);
				return new RespHub((byte) -3, "实时数据超过1小时未更新，或者发布时间格式错误！最后更新时间：" + pubTime);
			}

			BeanRetVehicle[] rvs = retMsg.getStatTimeList();
			ArrayList<BeanBusDynaInfo> timeAL = new ArrayList<BeanBusDynaInfo>();
			String nextStatTime, dstStatTime;
			long timeMillisecond;
			byte nextStatSeq;
			int nextDistance;
			long curTimeMillis = System.currentTimeMillis();
//			long curTimeMillis = simpleDF.parse(pubTime).getTime();
			for (byte i=0; i<timeListLen; i++){
				nextStatSeq = Byte.parseByte(rvs[i].getNextStatSeq());
				nextDistance = Integer.parseInt(rvs[i].getNextDistance());
				if (nextStatSeq == curSeq){
					nextStatTime = rvs[i].getNextStatTime();
					if (nextStatTime.compareTo("-1") == 0)
						timeAL.add(new BeanBusDynaInfo(-1, nextDistance, curSeq));
					else if (nextStatTime.length() == 14){
						timeMillisecond = simpleDF.parse(nextStatTime).getTime() - curTimeMillis;
						if (timeMillisecond < 0)
							timeAL.add(new BeanBusDynaInfo(0, nextDistance, curSeq));
						else
							timeAL.add(new BeanBusDynaInfo(timeMillisecond, nextDistance, curSeq));
					} 
				}
				else{
					dstStatTime = rvs[i].getDstStatTime();
					if (dstStatTime.length() == 14){
						timeMillisecond = simpleDF.parse(dstStatTime).getTime() - curTimeMillis;
						if (timeMillisecond >= 0)
							timeAL.add(new BeanBusDynaInfo(timeMillisecond, nextDistance, nextStatSeq));
					}						
				}
			}
			if ( ! timeAL.isEmpty() ){
				timeArray = timeAL.toArray(new BeanBusDynaInfo[0]);
				Arrays.sort(timeArray, new Comparator<BeanBusDynaInfo>(){
					@Override
					public int compare(BeanBusDynaInfo o1, BeanBusDynaInfo o2) {
						if (o1.getMilliSecond() == o2.getMilliSecond())
							return 0;
						else
							return (o1.getMilliSecond() < o2.getMilliSecond()) ? -1 : 1;
					}
				});
				long timeMinute;
				for (byte i=0; i<timeArray.length; i++){
					if (timeArray[i].getMilliSecond() == -1)
						bus123Str[i] = "已到站";
					else{
						timeMinute = timeArray[i].getMilliSecond() / 60000;
						if (timeMinute == 0)
							bus123Str[i] = "即将进站";
						else {
							bus123Str[i] = "预计" + String.valueOf(timeMinute) + "分钟到达";
							byte diff = (byte) (curSeq - timeArray[i].getNextStatSeq());
							if (diff >= 0)
								bus123Str[i] = "距离" + String.valueOf(diff + 1) + "站，" + bus123Str[i];
						}
					}
					if (i == 2)
						break;
				}
			}
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		log.debug("busStatus: route:{}, updown:{}, curSeq:{}, timeArray:{}", route, updown, curSeq, timeArray);
		return new RespHub((byte) 0, "获取成功！", new BeanBusStatus(pubTime, bus123Str[0], bus123Str[1], bus123Str[2]));
	}

	@RequestMapping(value = "/map2Floor", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub map2Floor(HttpServletRequest request, @RequestParam("mapId") int mapId,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanFloor floor = null;
		try {
			floor = hubDao.getFloorByMapId(mapId);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (floor == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取成功！", floor);
	}

	@RequestMapping(value = "/poi2Floor", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub poi2Floor(HttpServletRequest request, @RequestParam("poiId") String poiId,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanFloor floor = null;
		try {
			floor = hubDao.getFloorByPoiId(poiId);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (floor == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取成功！", floor);
	}

	@RequestMapping(value = "/floorList", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub floorList(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanFloor[] floors = null;

		try {
			floors = hubDao.getFloorListByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (floors == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(floors));
	}

	@RequestMapping(value = "/spectrum", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub spectrum(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanSpecInfo specInfo = null;
		try {
			specInfo = hubDao.getSpecInfoByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (specInfo == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取成功！", specInfo);
	}

	@RequestMapping(value = "/poiSelected", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub poiSelected(HttpServletRequest request, @RequestParam("poiId") String poiId,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanPoiSelected bps = null;
		try {
			bps = hubDao.getSelectedByPoiId(poiId);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (bps == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取成功！", bps);
	}

	@RequestMapping(value = "/shopInfo", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub shopInfo(HttpServletRequest request, @RequestParam("shopId") short shopId,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		
		BeanShop shopInfo = null;
		try {
			shopInfo = hubDao.getShopInfoByShopId(shopId);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (shopInfo == null)
			return new RespHub((byte) -3, "查询结果为空！");
		return new RespHub((byte) 0, "获取成功！", shopInfo);
	}

	@RequestMapping(value = "/hubSearch", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub hubSearch(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("kw") String keyword, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		if (keyword.compareTo("") == 0 || keyword == null)
			return new RespHub((byte) -5, "未搜索到‘’");
		
		BeanSearchItem[] resItems = null;
		try {
			resItems = hubDao.getHubSearchByHidKeyword(hid, keyword);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}
	
		if (resItems == null)
			return new RespHub((byte) -5, "未搜索到‘" + keyword + "’");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(resItems));
	}

	@RequestMapping(value = "/poiSearch", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub poiSearch(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("kw") String keyword, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");
		if (keyword.compareTo("") == 0 || keyword == null)
			return new RespHub((byte) -5, "未搜索到‘’");

		BeanPoiSearch[] resItems = null;
		try {
			resItems = hubDao.getPoiSearchByHidKeyword(hid, keyword);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (resItems == null)
			return new RespHub((byte) -5, "未搜索到‘" + keyword + "’");
		return new RespHub((byte) 0, "获取列表成功！", new RespList(resItems));
	}

	@RequestMapping(value = "/coachBasic", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub coachBasic(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanCoachBasic coachBasic = null;
		try {
			coachBasic = hubDao.getCoachBasicByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (coachBasic == null)
			return new RespHub((byte) -3, "查询结果为空！");

		return new RespHub((byte) 0, "获取成功！", coachBasic);
	}

	@RequestMapping(value = "/coachStat", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub coachStat(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam(value = "kw", defaultValue = "") String keyword,
			@RequestParam(value = "page") short page, @RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		if (page <= 0)
			return new RespHub((byte) -1, "参数错误！");

		BeanCoachStat[] statList = null;
		try {
			statList = hubDao.getCoachStatListByHidKeyword(hid, keyword, page);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (statList == null)
			return new RespHub((byte) -3, "查询结果为空！");

		return new RespHub((byte) 0, "获取列表成功！", new RespList(statList));
	}

	@RequestMapping(value = "/coachHotStat", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub coachHotStat(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanCoachStat[] statList = null;
		try {
			statList = hubDao.getCoachHotStatListByHid(hid);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		if (statList == null)
			return new RespHub((byte) -3, "查询结果为空！");

		return new RespHub((byte) 0, "获取列表成功！", new RespList(statList));
	}

	@RequestMapping(value = "/coachInfo", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public RespHub coachInfo(HttpServletRequest request, @RequestParam(value = "hid") short hid,
			@RequestParam("daySeq") byte daySeq, @RequestParam("dest") String dest,
			@RequestParam("token") String token) {
		if ( ! isTokenValid(token))
			return new RespHub((byte) -7, "鉴权失败！");

		BeanCoachInfo coachInfo = null;
		String pubTime = "";
		String restInfo, restMsg;
		byte schListLen;
		try {
			coachInfo = hubDao.getCoachInfoByHid(hid, daySeq, dest);
			if (coachInfo == null)
				return new RespHub((byte) -1, "参数无效或查询失败！");

			coachInfo.setDest(dest);
			BeanCoachRetMsg retMsg = restClient.getCoachRealtimeData(coachInfo.getDrvDate(), coachInfo.getStart(), dest);
			if (retMsg == null)
				return new RespHub((byte) -2, "实时数据网络请求失败！");

			restInfo = retMsg.getInfo();
			restMsg = retMsg.getMsg();
			if (restInfo.compareTo("ERR") == 0 && restMsg.compareTo("无相关数据") == 0){
				coachInfo.setPubTime("该日期无此线路客车");
				return new RespHub((byte) -4, "该日期无此线路客车！", coachInfo);
			}

			pubTime = retMsg.getPublishTime();
			schListLen = (byte) retMsg.getSchList().length;
			if (restInfo.compareTo("ERR") == 0 || pubTime.length() == 0 || schListLen == 0)
				return new RespHub((byte) -3, restMsg);

			coachInfo.setPubTime(pubTime.substring(4, 6) + "-" + pubTime.substring(6, 8) + " " + pubTime.substring(8, 10) + ":" + pubTime.substring(10, 12));
			BeanCoachRetItem[] cris = retMsg.getSchList();
			BeanCoachItem[] coachItems = new BeanCoachItem[schListLen];
			String yuPiao;
			for (byte i=0; i<schListLen; i++){
				yuPiao = cris[i].getYuPiao();
				if (yuPiao.compareTo("-1") == 0)
					yuPiao = "--";
				coachItems[i] = new BeanCoachItem(cris[i].getDrvtime().substring(11, 16), cris[i].getSchid(),
						"(" + cris[i].getRouteName() + ")", cris[i].getPrice(), cris[i].getSeats(),
						yuPiao, cris[i].getMile());
			}
			Arrays.sort(coachItems, new Comparator<BeanCoachItem>(){
				@Override
				public int compare(BeanCoachItem o1, BeanCoachItem o2) {
					return o1.getDrvTime().compareTo(o2.getDrvTime());
				}
			});
			coachInfo.setList(coachItems);
		} catch (Exception e) {
			log.error("HubControllerV0 error: {}", e.getMessage());
		}

		return new RespHub((byte) 0, "获取成功！", coachInfo);
	}





	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest req, Exception e) {
		log.error("HubControllerV0 Exception from {}, RequestURI: {}, Message: {}",
				req.getRemoteAddr(), req.getRequestURI(), e.getMessage());

		return null;
	}

}
