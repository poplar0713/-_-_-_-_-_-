package com.tistory.lte36.travolo2.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dao.EffectDao;
import com.tistory.lte36.travolo2.dto.Crawling_tour;
import com.tistory.lte36.travolo2.service.EffectService;

@Service
public class EffectServiceImple implements EffectService{
	@Autowired
	EffectDao effectdao;

	//주소 검색 기능
	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> Search() {
		return effectdao.Search();
	}

	//일정 리스트 표시 기능
	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> ScheduleList(List<ModelMap> model) {
		String uid = model.get(0).get("user_id").toString();
		return effectdao.ScheduleList(uid);
	}

	// 만든 일정 출력 기능
	// 선택 일정 출력 기능
	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> Schedule(List<ModelMap> model, int flag) {
		String uid = model.get(0).get("user_id").toString();
		String group_no = model.get(0).get("group_no").toString();
		if(flag==1)
			return effectdao.makeSchedule(uid, group_no);
		else if(flag==2)
			return effectdao.printSchedule(uid, group_no);
		else return null;
	}
	
	//여행지 상세 설명 페이지 표시 기능
	@Override
	@Transactional(readOnly=false)
	public ModelMap TourInfo(ModelMap model) {
		ModelMap result = new ModelMap();
		String tid = model.get("tid").toString();
		Crawling_tour craw = effectdao.TourInfo(tid);
		try {
			if(craw == null)
				throw new NullPointerException("craw is null");
			if(tid==null)
				throw new InputMismatchException("tid is null");
		}catch(NullPointerException e) {
			result.addAttribute("error", "-1");
		}catch(InputMismatchException e) {
			result.addAttribute("error", "1");
		}
		
		try {
			result.addAttribute("label", craw.getLabel());
			result.addAttribute("address", craw.getAddress());
			result.addAttribute("tel", craw.getTel());
			result.addAttribute("opentime", craw.getOpentime());
			result.addAttribute("closed", craw.getClosed());
			result.addAttribute("fee", craw.getFee());
			result.addAttribute("uri", craw.getUri());
			result.addAttribute("gps_lat", craw.getGps_lat());
			result.addAttribute("gps_long", craw.getGps_long());
			result.addAttribute("depiction", craw.getDepiction());
			result.addAttribute("description", craw.getDescription());
		}catch(Exception e) {
			result.addAttribute("error", "-2");
			result.addAttribute("desc", e.getMessage().toString());
		}
		return result;
	}

	//일정 평가 기능
	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> evaluateGrade(List<ModelMap> model) {
		List<ModelMap> resultModelList = new ArrayList<>();
		ModelMap resultModel = new ModelMap();
		try {
			for(ModelMap iterModel:model) {
				String result = effectdao.evaluate(Double.parseDouble(iterModel.get("grade").toString()), iterModel.get("tid").toString());
				if(result != "1") {
					resultModel.addAttribute("success",result+" -2");
					throw new SQLException("No grade");
				}
			}
			resultModel.addAttribute("success","1");
		}catch(Exception e) {
			resultModel.addAttribute("error", e.getMessage());
		}
		resultModelList.add(resultModel);
		return resultModelList;
	}

	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> optimalRoad(List<ModelMap> model) {
		return effectdao.optimalRoad(model.get(0).get("user_id").toString(), model.get(0).get("group_no").toString());
	}

	@Override
	@Transactional(readOnly=false)
	public ModelMap editSchedule(ModelMap model) {
		ModelMap resultModel = new ModelMap();
		String sqlResult="0";
		try {
			sqlResult=effectdao.editSchedule(model);
			resultModel.addAttribute("success", sqlResult);
		}catch(Exception e) {
			resultModel.addAttribute("success", "0");
			resultModel.addAttribute("error", sqlResult);
		}
		return resultModel;
	}

	@Override
	@Transactional(readOnly=false)
	public ModelMap changeScheduleName(ModelMap model) {
		ModelMap resultModel = new ModelMap();
		if(effectdao.changeScheduleName(model.get("schedule_name").toString(), model.get("group_no").toString(), model.get("user_id").toString())>0)
			resultModel.addAttribute("success", "1");
		else
			resultModel.addAttribute("success", "0");
		return resultModel;
	}

	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> searchTour(List<ModelMap> model) {
		String key = model.get(0).get("key").toString();
		String base_point = model.get(0).get("base_point").toString();
		if(key.equals("") || key.equals(" ")) {
			List<ModelMap> resultList = new ArrayList<ModelMap>();
			ModelMap resultModel = new ModelMap();
			resultModel.addAttribute("");
			resultList.add(resultModel);
			return resultList;
		}
		return effectdao.searchTour(key, base_point);
	}
}
