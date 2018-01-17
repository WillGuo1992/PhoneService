package cn.com.navia.PhoneService.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.com.navia.PhoneService.bean.BeanAB;
import cn.com.navia.PhoneService.bean.BeanBike;
import cn.com.navia.PhoneService.bean.BeanBusOption;
import cn.com.navia.PhoneService.bean.BeanCoachBasic;
import cn.com.navia.PhoneService.bean.BeanCoachInfo;
import cn.com.navia.PhoneService.bean.BeanCoachStat;
import cn.com.navia.PhoneService.bean.BeanFloor;
import cn.com.navia.PhoneService.bean.BeanHub;
import cn.com.navia.PhoneService.bean.BeanItemBody;
import cn.com.navia.PhoneService.bean.BeanItemHead;
import cn.com.navia.PhoneService.bean.BeanMB;
import cn.com.navia.PhoneService.bean.BeanPoiSearch;
import cn.com.navia.PhoneService.bean.BeanPoiSelected;
import cn.com.navia.PhoneService.bean.BeanSearchItem;
import cn.com.navia.PhoneService.bean.BeanShop;
import cn.com.navia.PhoneService.bean.BeanSpecInfo;
import cn.com.navia.PhoneService.bean.BeanTransInfo;
import cn.com.navia.PhoneService.bean.BeanStation;
import cn.com.navia.PhoneService.bean.BeanTrans;



@Repository
public class HubDao extends PhoneDao {

	@Value("${trhub.map.baseurl}")
	private String mapBaseurl;

	@Value("${trhub.token.ttlsec}")
	private long tokenTTLsecond;

	@Value("${trhub.itemlist.pagesize}")
	private byte itemListPageSize;

	@Value("${trhub.buslist.pagesize}")
	private byte busListPageSize;

	@Value("${trhub.coachstat.pagesize}")
	private byte coachStatPageSize;

	@Value("${trhub.spectrum.baseurl}")
	private String specBaseUrl;

	@Value("${trhub.content.baseurl}")
	private String contentBaseUrl;

	@Value("${trhub.logo.path}")
	private String logoPath;

	private static String gonggongLogoStr = "gonggong.png";
	private static String zhantaiLogoStr = "zhantai.png";
	private static String coachZSetKeyStr = "coach.hot.station.hid_";
	private static long coachHotStatNum = 20;

	private static byte transTypeBus = 1;
	private static byte transTypeAirportBus = 2;
	private static byte transTypeAirportExpress = 3;

	private String[] hubNames = null;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static SimpleDateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dayNumSDF = new SimpleDateFormat("u");
	private static String[] dayOfWeek = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


	@Override
	public void init() throws Exception {
		super.init();
		List<String> nameList = phoneJdbcTemplate.query(
				"select name from hub order by hid",
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum)	throws SQLException {
						return rs.getString("name");
					}
				});
		this.hubNames = (nameList.isEmpty()) ? null : (String[]) nameList.toArray(new String[0]);
	}

	private String getDayNameStr(Date d) throws Exception {
		int dayNumber = Integer.parseInt(dayNumSDF.format(d));
		return dayOfWeek[dayNumber - 1];
	}

	private Date getDateBySeq(byte daySeq) {
		if ((daySeq < 1) || (daySeq > 3))
			daySeq = 1;
		return new Date(System.currentTimeMillis() + (daySeq - 1) * 24 * 3600 * 1000);
	}

	private String getFullDateStr(byte daySeq) {
		return dateSDF.format(getDateBySeq(daySeq));
	}

	private String getDateWeekStr(byte daySeq) throws Exception {
		return getFullDateStr(daySeq).substring(5) + "(" + getDayNameStr(getDateBySeq(daySeq)) + ")";
	}

	public BeanHub[] getHubList() throws Exception {
		List<BeanHub> hubs = phoneJdbcTemplate.query(
				"select hid, name, lon, lat, mall_id, brief from hub",
				new RowMapper<BeanHub>() {
					@Override
					public BeanHub mapRow(ResultSet rs, int rowNum)	throws SQLException {
						String mapUrl = mapBaseurl + String.valueOf(rs.getShort("mall_id"));
						return new BeanHub(rs.getShort("hid"), rs.getString("name"), rs.getFloat("lon"),
								rs.getFloat("lat"), rs.getString("brief"), mapUrl);
					}
				});
		return (hubs.isEmpty()) ? null : (BeanHub[]) hubs.toArray(new BeanHub[0]);
	}

	public BeanMB[] getMainBannerListByHid(short hid) throws Exception {
		List<BeanMB> mbs = phoneJdbcTemplate.query(
				"select mb_id, pic, link from main_banner where hid=? and enable=true order by order_seq",
				new Object[] {hid},
				new RowMapper<BeanMB>() {
					@Override
					public BeanMB mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanMB(rs.getShort("mb_id"), rs.getString("pic"), rs.getString("link"));
					}
				});
		return (mbs.isEmpty()) ? null : (BeanMB[]) mbs.toArray(new BeanMB[0]);
	}

	public BeanAB[] getAdBannerListByHid(short hid) throws Exception {
		List<BeanAB> abs = phoneJdbcTemplate.query(
				"select item_id, pic from ad_banner where hid=? and enable=true order by order_seq",
				new Object[] {hid},
				new RowMapper<BeanAB>() {
					@Override
					public BeanAB mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanAB(rs.getInt("item_id"), rs.getString("pic"));
					}
				});
		return (abs.isEmpty()) ? null : (BeanAB[]) abs.toArray(new BeanAB[0]);
	}

	public BeanItemHead[] getItemListByHid(short hid, short page) throws Exception {
		int rowsBefore = (page - 1) * itemListPageSize;
		List<BeanItemHead> itemheads = phoneJdbcTemplate.query(
				"select item.item_id, title, icon, head from item inner join hub_item on (item.item_id=hub_item.item_id) where hid=? order by order_seq desc limit ?, ?",
				new Object[] {hid, rowsBefore, itemListPageSize},
				new RowMapper<BeanItemHead>() {
					@Override
					public BeanItemHead mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanItemHead(rs.getInt("item_id"), rs.getString("title"), rs.getString("icon"), rs.getString("head"));
					}
				});
		return (itemheads.isEmpty()) ? null : (BeanItemHead[]) itemheads.toArray(new BeanItemHead[0]);
	}

	public BeanItemBody getItemInfoByItemId(int itemId) throws Exception {
		BeanItemBody itembody = null;
		try {
			itembody = phoneJdbcTemplate.queryForObject(
					"select item_id, title, time, pv, pic, body from item where item_id=?",
					new Object[] {itemId},
					new RowMapper<BeanItemBody>() {
						@Override
						public BeanItemBody mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new BeanItemBody(rs.getInt("item_id"), rs.getString("title"), rs.getString("time"), rs.getInt("pv") + 1, rs.getString("pic"), rs.getString("body"));
						}
					});
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getItemInfoByItemId error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}
		
		int rowsAffected = phoneJdbcTemplate.update("update item set pv=? where item_id=?", itembody.getPv(), itembody.getItemId());
		if (rowsAffected == 0)
			log.error("getItemInfoByItemId error: Increasing item.pv into database failed! item_id={}", itembody.getItemId());
		return itembody;
	}

	public BeanBike[] getBikeListByHid(short hid) throws Exception {
		List<BeanBike> bikes = phoneJdbcTemplate.query(
				"select stat_code, name, address, lon, lat from bike where hid=?",
				new Object[] {hid},
				new RowMapper<BeanBike>() {
					@Override
					public BeanBike mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanBike(rs.getString("stat_code"), rs.getString("name"), rs.getString("address"), rs.getFloat("lon"), rs.getFloat("lat"));
					}
				});
		return (bikes.isEmpty()) ? null : (BeanBike[]) bikes.toArray(new BeanBike[0]);
	}

	public byte getTotalNumByStatCode(String statCode) throws Exception {
		Byte totalNum = null;
		try{
			totalNum = phoneJdbcTemplate.queryForObject(
					"select total_num from bike where stat_code=?",
					new Object[] {statCode},
					Byte.class);
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getTotalNumByStatCode error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return 0;
		}

		return (totalNum == null) ? 0 : totalNum.byteValue();
	}

	public BeanTrans[] getBusListByHid(short hid, short page) throws Exception {
		if (hid <1 || hid > hubNames.length)
			return null;
		String nameSubStr = (hubNames[hid - 1].length() < 2) ? hubNames[hid - 1] : hubNames[hid - 1].substring(0, 2);
		int rowsBefore = (page - 1) * busListPageSize;
		List<BeanTrans> busList = phoneJdbcTemplate.query(
				"select trans_line.route, trans_line.updown, start, end, first, last, realtime, poi_id from trans_line inner join hub_trans on (trans_line.route=hub_trans.route and trans_line.updown=hub_trans.updown) where hid=? and trans_type=? and (end not like '" + nameSubStr + "%' or (start like '" + nameSubStr + "%' and end like '" + nameSubStr + "%')) order by (poi_id=''), trans_line.route limit ?, ?",
				new Object[] {hid, transTypeBus, rowsBefore, busListPageSize},
				new RowMapper<BeanTrans>() {
					@Override
					public BeanTrans mapRow(ResultSet rs, int rowNum) throws SQLException {
						BeanTrans bTrans = new BeanTrans(rs.getString("route"), rs.getByte("updown"),
								rs.getString("start"), rs.getString("end"),
								rs.getString("first"), rs.getString("last"),
								rs.getByte("realtime"));
						if (rs.getString("poi_id").compareTo("") != 0)
							bTrans.setCaption(bTrans.getCaption() + "（枢纽站内）");
						return bTrans;
					}
				});
		return (busList.isEmpty()) ? null : (BeanTrans[]) busList.toArray(new BeanTrans[0]);
	}

	public byte getCurrentSeqByRouteUpdownHid(String route, byte updown, short hid) throws Exception {
		Byte curSeq = null;
		try{
			curSeq = phoneJdbcTemplate.queryForObject(
					"select cur_seq from hub_trans where hid=? and route=? and updown=?",
					new Object[] {hid, route, updown},
					Byte.class);
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getCurrentSeqByRouteUpdownHid error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return 0;
		}

		return (curSeq == null) ? 0 : curSeq.byteValue();
	}

	public BeanTransInfo getTransInfoByRouteUpdownHid(final String route, final byte updown, short hid) throws Exception {
		BeanTransInfo bti = null;
		try{
			bti = phoneJdbcTemplate.queryForObject(
					"select start, end, first, last, extra, poi_id, realtime, cur_seq from trans_line inner join hub_trans on (trans_line.route=hub_trans.route and trans_line.updown=hub_trans.updown) where hid=? and hub_trans.route=? and hub_trans.updown=?",
					new Object[] {hid, route, updown},
					new RowMapper<BeanTransInfo>() {
						@Override
						public BeanTransInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new BeanTransInfo(route, updown, rs.getString("start"), rs.getString("end"),
									rs.getString("first"), rs.getString("last"),
									rs.getString("extra"), rs.getString("poi_id"),
									rs.getByte("realtime"), rs.getByte("cur_seq"));
						}
					});
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getTransInfoByRouteUpdownHid error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}

		if (bti.getPoiId().compareTo("") == 0)
			bti.setAddr("枢纽站外");
		else{
			BeanFloor floor = getFloorByPoiId(bti.getPoiId());
			if (floor != null) {
				bti.setFloorId(floor.getFloorId());
				bti.setAddr(floor.getName() + "（室内图）");
			}
		}

		List<BeanStation> statList = phoneJdbcTemplate.query(
				"select seq, name from trans_stat where route=? and updown=? order by seq",
				new Object[] {route, updown},
				new RowMapper<BeanStation>() {
					@Override
					public BeanStation mapRow(ResultSet rs, int rowNum) throws SQLException {
						String statName = rs.getString("name");
						statName = statName.replaceAll("[(（]", "︵").replaceAll("[)）]", "︶");
						return new BeanStation(rs.getByte("seq"), statName);
					}
				});
		BeanStation[] list = (statList.isEmpty()) ? null : (BeanStation[]) statList.toArray(new BeanStation[0]);
		bti.setList(list);
		return bti;
	}

	public BeanTrans[] getAirportBusByHid(short hid) throws Exception {
		List<BeanTrans> airportBusList = phoneJdbcTemplate.query(
				"select trans_line.route, trans_line.updown, start, end, first, last, realtime from trans_line inner join hub_trans on (trans_line.route=hub_trans.route and trans_line.updown=hub_trans.updown) where hid=? and trans_type=? order by trans_line.route",
				new Object[] {hid, transTypeAirportBus},
				new RowMapper<BeanTrans>() {
					@Override
					public BeanTrans mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanTrans(rs.getString("route"), rs.getByte("updown"),
								rs.getString("start"), rs.getString("end"),
								rs.getString("first"), rs.getString("last"),
								rs.getByte("realtime"));
					}
				});
		return (airportBusList.isEmpty()) ? null : (BeanTrans[]) airportBusList.toArray(new BeanTrans[0]);
	}

	public BeanTrans[] getAirportExpByHid(short hid) throws Exception {
		List<BeanTrans> airportExpList = phoneJdbcTemplate.query(
				"select trans_line.route, trans_line.updown, start, end, first, last, realtime from trans_line inner join hub_trans on (trans_line.route=hub_trans.route and trans_line.updown=hub_trans.updown) where hid=? and trans_type=? order by trans_line.route",
				new Object[] {hid, transTypeAirportExpress},
				new RowMapper<BeanTrans>() {
					@Override
					public BeanTrans mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanTrans(rs.getString("route"), rs.getByte("updown"),
								rs.getString("start"), rs.getString("end"),
								rs.getString("first"), rs.getString("last"),
								rs.getByte("realtime"));
					}
				});
		return (airportExpList.isEmpty()) ? null : (BeanTrans[]) airportExpList.toArray(new BeanTrans[0]);
	}

	private static final class beanFloorMapper implements RowMapper<BeanFloor> {
		@Override
		public BeanFloor mapRow(ResultSet rs, int rowNum) throws SQLException {
			byte num = rs.getByte("num");
			String label = "";
			if (num > 0)
				label = "F" + String.valueOf(num);
			else if (num < 0)
				label = "B" + String.valueOf(num).substring(1);
			else
				label = "G";
			return new BeanFloor(rs.getString("floor_id"), rs.getString("mdb_mapname"), label, rs.getString("name"), rs.getString("info"));
		}
	}

	public BeanFloor getFloorByPoiId(String poiId) throws Exception {
		BeanFloor floor = null;
		try {
			floor = mallJdbcTemplate.queryForObject(
					"select floor_id, mdb_mapname, num, name, info from storey where id=(select storey_id from map where id=(select map_id from area where poi_id=?))",
					new Object[] {poiId}, new beanFloorMapper());
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getFloorByPoiId error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}

		return floor;
	}

	public BeanFloor getFloorByMapId(int mapId) throws Exception {
		BeanFloor floor = null;
		try {
			floor = mallJdbcTemplate.queryForObject(
					"select floor_id, mdb_mapname, num, name, info from storey where id=(select storey_id from map where id=?)",
					new Object[] {mapId}, new beanFloorMapper());
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getFloorByMapId error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}

		return floor;
	}

	public BeanFloor[] getFloorListByHid(short hid) throws Exception {
		List<BeanFloor> floors = mallJdbcTemplate.query(
				"select floor_id, mdb_mapname, num, name, info from storey where mall_id=(select mall_id from phone_app.hub where hid=?)",
				new Object[] {hid}, new beanFloorMapper());

		return (floors.isEmpty()) ? null : (BeanFloor[]) floors.toArray(new BeanFloor[0]);
	}

	public BeanSpecInfo getSpecInfoByHid(short hid) throws Exception {
		BeanSpecInfo specInfo = null;
		try {
			specInfo = phoneJdbcTemplate.queryForObject(
					"select ver, spec from spectrum where mall_id=(select mall_id from hub where hid=?)",
					new Object[] {hid},
					new RowMapper<BeanSpecInfo>() {
						@Override
						public BeanSpecInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new BeanSpecInfo(rs.getString("ver"), specBaseUrl + rs.getString("spec"));
						}
					});
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getSpecInfoByHid error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}
		
		return specInfo;
	}

	public BeanShop getShopInfoByShopId(short shopId) throws Exception {
		BeanShop shopInfo = null;
		try {
			shopInfo = phoneJdbcTemplate.queryForObject(
					"select poi_id, name, `open`, `close`, logo, tele, intro from shop where shop_id=?",
					new Object[] {shopId},
					new RowMapper<BeanShop>() {
						@Override
						public BeanShop mapRow(ResultSet rs, int rowNum) throws SQLException {
							String info = "营业时间 " + rs.getString("open") + " - " + rs.getString("close");
							return new BeanShop(rs.getString("name"), info, contentBaseUrl + logoPath + rs.getString("logo"),
									rs.getString("poi_id"),rs.getString("tele"),rs.getString("intro"));
						}
					});
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getShopInfoByShopId error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}
		if (shopInfo.getPoiId().compareTo("") == 0)
			shopInfo.setAddr("枢纽站外底商");
		else{
			BeanFloor floor = getFloorByPoiId(shopInfo.getPoiId());
			if (floor != null) {
				shopInfo.setFloorId(floor.getFloorId());
				shopInfo.setAddr(floor.getName() + "（室内图）");
			}
		}

		return shopInfo;
	}

	public BeanPoiSearch[] getPoiSearchByHidKeyword(short hid, String keyword) throws Exception {
		List<BeanPoiSearch> resList = mallJdbcTemplate.query(
				"select search_name, poi_id, a_type, lon, lat from area inner join area_cpoint on (area.id=area_cpoint.area_id) where area.mall_id=(select mall_id from phone_app.hub where hid=?) and search_name like ?",
				new Object[] {hid, "%" + keyword + "%"},
				new RowMapper<BeanPoiSearch>() {
					@Override
					public BeanPoiSearch mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanPoiSearch(rs.getString("search_name"), rs.getString("poi_id"), rs.getInt("a_type"),
								String.valueOf(rs.getDouble("lon")), String.valueOf(rs.getDouble("lat")));
					}
				});

		if (resList.isEmpty())
			return null;
		else{
			BeanFloor floor = null;
			BeanPoiSearch[] resItems = (BeanPoiSearch[]) resList.toArray(new BeanPoiSearch[0]);
			for (byte i=0; i<resItems.length; i++){
				floor = getFloorByPoiId(resItems[i].getPoiId());
				if (floor != null) {
					resItems[i].setInfo(floor.getName());
					resItems[i].setMapName(floor.getMapName());
					resItems[i].setFloorId(floor.getFloorId());
				}
			}
			return resItems;			
		}
	}

	public BeanPoiSelected getSelectedByPoiId(String poiId) throws Exception {
		BeanPoiSelected bps = null;
		try {
			bps = mallJdbcTemplate.queryForObject(
					"select caption, a_type from area where poi_id=?",
					new Object[] {poiId},
					new RowMapper<BeanPoiSelected>() {
						@Override
						public BeanPoiSelected mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new BeanPoiSelected((byte) rs.getInt("a_type"), rs.getString("caption"));
						}
					});
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getSelectedByPoiId error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}

		bps.setLogo(contentBaseUrl + logoPath + gonggongLogoStr);
		byte areaType = bps.getType();
		if (areaType == 2) {
			try {
				bps = phoneJdbcTemplate.queryForObject(
						"select shop_id, name, `open`, `close`, logo from shop where poi_id=?",
						new Object[] {poiId},
						new RowMapper<BeanPoiSelected>() {
							@Override
							public BeanPoiSelected mapRow(ResultSet rs, int rowNum) throws SQLException {
								String info = "营业时间 " + rs.getString("open") + " - " + rs.getString("close");
								return new BeanPoiSelected((byte) 2, rs.getString("name"), info, contentBaseUrl + logoPath + rs.getString("logo"),
										rs.getShort("shop_id"));
							}
						});
			}
			catch (IncorrectResultSizeDataAccessException irsdae){
				log.error("getSelectedByPoiId error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
				bps.setType((byte) 1);
			}
		}
		else if (areaType == 3) {
			bps.setLogo(contentBaseUrl + logoPath + zhantaiLogoStr);
			List<BeanBusOption> optionList = phoneJdbcTemplate.query(
					"select hub_trans.route, hub_trans.updown, start, end from hub_trans inner join trans_line on (hub_trans.route=trans_line.route and hub_trans.updown=trans_line.updown) where poi_id=?",
					new Object[] {poiId},
					new RowMapper<BeanBusOption>() {
						@Override
						public BeanBusOption mapRow(ResultSet rs, int rowNum) throws SQLException {
							String caption = rs.getString("route") + "（" + rs.getString("start") + " - " + rs.getString("end") + "）";
							return new BeanBusOption(caption, rs.getString("route"), rs.getByte("updown"));
						}
					});
			if (optionList.isEmpty())
				bps.setType((byte) 1);
			else{
				BeanBusOption[] options = (BeanBusOption[]) optionList.toArray(new BeanBusOption[0]);
				bps.setOptions(options);
				if (options.length == 1){
					String info = options[0].getCaption();
					bps.setInfo(info.substring(info.indexOf("（")));
				}
			}
		}
		else{
			bps.setType((byte) 1);
		}
		return bps;
	}

	public BeanSearchItem[] getHubSearchByHidKeyword(short hid, String keyword) throws Exception {
		List<BeanSearchItem> type1List = mallJdbcTemplate.query(
				"select search_name, poi_id from area where ((a_type<>2 and a_type<>3) or a_type is null) and mall_id=(select mall_id from phone_app.hub where hid=?) and search_name like ?",
				new Object[] {hid, "%" + keyword + "%"},
				new RowMapper<BeanSearchItem>() {
					@Override
					public BeanSearchItem mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanSearchItem((byte) 1, rs.getString("search_name"), rs.getString("poi_id"));
					}
				});

		List<BeanSearchItem> type2List = phoneJdbcTemplate.query(
				"select shop_id, poi_id, name from shop where hid=? and name like ?",
				new Object[] {hid, "%" + keyword + "%"},
				new RowMapper<BeanSearchItem>() {
					@Override
					public BeanSearchItem mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new BeanSearchItem((byte) 2, rs.getString("name"), rs.getString("poi_id"), rs.getShort("shop_id"));
					}
				});

		List<BeanSearchItem> type3List = phoneJdbcTemplate.query(
				"select hub_trans.route, hub_trans.updown, poi_id, start, end from hub_trans inner join trans_line on (hub_trans.route=trans_line.route and hub_trans.updown=trans_line.updown) where hid=? and hub_trans.route like ?",
				new Object[] {hid, "%" + keyword + "%"},
				new RowMapper<BeanSearchItem>() {
					@Override
					public BeanSearchItem mapRow(ResultSet rs, int rowNum) throws SQLException {
						String info = "（" + rs.getString("start") + " - " + rs.getString("end") + "）";
						return new BeanSearchItem((byte) 3, rs.getString("route"), info,
								rs.getString("poi_id"), rs.getString("route"), rs.getByte("updown"));
					}
				});

		type1List.addAll(type2List);
		type1List.addAll(type3List);
		if (type1List.isEmpty())
			return null;
		else{
			BeanFloor floor = null;
			String poiId = null;
			BeanSearchItem[] resItems = (BeanSearchItem[]) type1List.toArray(new BeanSearchItem[0]);
			for (byte i=0; i<resItems.length; i++){
				poiId = resItems[i].getPoiId();
				if (poiId.compareTo("") != 0){
					floor = getFloorByPoiId(poiId);
					if (floor != null) {
						resItems[i].setFloorId(floor.getFloorId());
						if (resItems[i].getType() != 3)
							resItems[i].setInfo(floor.getName());
					}
				}
			}
			return resItems;			
		}
	}

	public BeanCoachBasic getCoachBasicByHid(short hid) throws Exception {
		if (hid <1 || hid > hubNames.length)
			return null;
		String[] day123 = new String[3];
		for (byte i=0; i<3; i++)
			day123[i] = getDateWeekStr((byte) (i + 1));
		return new BeanCoachBasic(hubNames[hid - 1] + "客运站", day123[0], day123[1], day123[2]);
	}

	public BeanCoachStat[] getCoachStatListByHidKeyword(short hid, String keyword, short page) throws Exception {
		int rowsBefore = (page - 1) * coachStatPageSize;
		List<BeanCoachStat> statList = phoneJdbcTemplate.query(
				"select initial, name from coach_stat where hid=? and (name like ? or initial like ? or pinyin like ?) order by pinyin limit ?, ?",
				new Object[] {hid, keyword + "%", keyword + "%", keyword + "%", rowsBefore, coachStatPageSize},
				new RowMapper<BeanCoachStat>() {
					@Override
					public BeanCoachStat mapRow(ResultSet rs, int rowNum) throws SQLException {
						String name = rs.getString("name");
						return new BeanCoachStat(rs.getString("initial").substring(0, 1) + "　　" + name, name);
					}
				});
		return (statList.isEmpty()) ? null : (BeanCoachStat[]) statList.toArray(new BeanCoachStat[0]);
	}

	public BeanCoachStat[] getCoachHotStatListByHid(short hid) throws Exception {
		String keyStr = coachZSetKeyStr + String.valueOf(hid);
		final byte[] key = keyStr.getBytes("UTF-8");

		Set<byte[]> zSetRevRange = phoneStrRedisTemplate.execute(new RedisCallback<Set<byte[]>>() {
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRevRange(key, 0, coachHotStatNum - 1);
			}
		});
		if (zSetRevRange.isEmpty())
			return null;

		int setSize = zSetRevRange.size();
		BeanCoachStat[] statList = new BeanCoachStat[setSize];
		Iterator<byte[]> iterator = zSetRevRange.iterator();
		String statName;
		for (int i=0; i<setSize; i++){
			if (iterator.hasNext())
				statName = new String(iterator.next(), "UTF-8");
			else
				statName = "";
			statList[i] = new BeanCoachStat(statName, statName);
		}

		return statList;
	}

	public BeanCoachInfo getCoachInfoByHid(short hid, byte daySeq, String dest) throws Exception {
		if (hid <1 || hid > hubNames.length)
			return null;

		Integer existNum = null;
		try{
			existNum = phoneJdbcTemplate.queryForObject(
					"select count(1) from coach_stat where name=?",
					new Object[] {dest},
					Integer.class);
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			log.error("getCoachInfoByHid error: IncorrectResultSizeDataAccessException ActualSize: {}", irsdae.getActualSize());
			return null;
		}
		if (existNum == null || existNum.intValue() < 1)
			return null;

		String[] day123 = new String[3];
		for (byte i=0; i<3; i++)
			day123[i] = getFullDateStr((byte) (i + 1));

		String keyStr = coachZSetKeyStr + String.valueOf(hid);
		final byte[] key = keyStr.getBytes("UTF-8");
		final byte[] member = dest.getBytes("UTF-8");
		phoneStrRedisTemplate.execute(new RedisCallback<Double>() {
			public Double doInRedis(RedisConnection connection) {
				return connection.zIncrBy(key, 1, member);
			}
		});

		return new BeanCoachInfo(getFullDateStr(daySeq), hubNames[hid - 1], day123[0], day123[1], day123[2]);
	}






	public boolean isTokenExist(String token) throws Exception {
//		Byte res = null;
//		try{
//			res = phoneJdbcTemplate.queryForObject(
//					"select 1 from device where token=?",
//					new Object[] {token},
//					Byte.class);
//		}
//		catch (IncorrectResultSizeDataAccessException irsdae){
//			return (irsdae.getActualSize() > 0) ? true: false;
//		}
//
//		return (res.byteValue() == 1) ? true : false;
		
		return phoneStrRedisTemplate.hasKey(token);
	}

	public boolean saveInitData(String phoneMac, String idFV, byte osType, String appVer, String token) throws Exception {
		final byte[] tokenKey = token.getBytes();
		final byte[] osTypeValue = String.valueOf((int) osType).getBytes();
		phoneStrRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				connection.setEx(tokenKey, tokenTTLsecond, osTypeValue);
				return true;
			}
		});

		int rowsAffected = phoneJdbcTemplate.update("INSERT INTO device (mac, uniq_id, os_type, app_ver, token, init_time) VALUES (?, ?, ?, ?, ?, now()) ON DUPLICATE KEY UPDATE app_ver=VALUES(app_ver), token=VALUES(token), init_time=VALUES(init_time);", 
				phoneMac, idFV, osType, appVer, token);

		return (rowsAffected > 0) ? true : false;
	}


}
