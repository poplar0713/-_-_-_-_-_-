package com.tistory.lte36.travolo2.service;

import java.util.List;

import org.springframework.ui.ModelMap;

public interface EffectService {
	public List<ModelMap> Search();
	public List<ModelMap> Schedule(List<ModelMap> model, int flag);
	public List<ModelMap> ScheduleList(List<ModelMap> model);
	public ModelMap TourInfo(ModelMap model);
	public List<ModelMap> evaluateGrade(List<ModelMap> model);
	public List<ModelMap> optimalRoad(List<ModelMap> model);
	
	public ModelMap editSchedule(ModelMap model);
	public ModelMap changeScheduleName(ModelMap model);
	public List<ModelMap> searchTour(List<ModelMap> model);
}
