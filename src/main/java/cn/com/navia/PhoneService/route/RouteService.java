package cn.com.navia.PhoneService.route;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.navia.PhoneService.assist.ResourceUtil;

@Service
public class RouteService {

	@Resource
	private RouteDataArray rda;

	@Resource
	private ResourceUtil resourceUtil;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ArrayList<BeanRouteSave> routeSaveList = new ArrayList<BeanRouteSave>();
	private ArrayList<BeanAdjacent[]> adjacentList = new ArrayList<BeanAdjacent[]>();

	@PostConstruct
	private void init() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		List<String> fileNameList = rda.getJson();
		File jsonFile;
		BeanRouteSave brs;
		BeanEdge[] eList;
		BeanAdjacent[] aList;
		int fromId;
		try {
			for (int idx=0; idx<fileNameList.size(); idx++) {
				jsonFile = resourceUtil.getResource("classpath:" + fileNameList.get(idx)).getFile();
				brs = mapper.readValue(jsonFile, BeanRouteSave.class);
				eList = brs.geteList();
				aList = new BeanAdjacent[brs.getvList().length + 1];
				for (int i=0; i<eList.length; i++){
					fromId = eList[i].from;
					if (aList[fromId] == null)
						aList[fromId] = new BeanAdjacent(fromId, i);
					aList[fromId].eNum++;
				}
				routeSaveList.add(brs);
				adjacentList.add(aList);
			}
			log.info("RouteService init success!");
		} catch (Exception e) {
			log.error("RouteService init error: {}, Message: {}, StackTrace: {}", e.getClass().getName(), e.getMessage(), e.getStackTrace());
		}
	}





}
