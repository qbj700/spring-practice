package com.sbs.springPractice.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenFileDao {
	void saveMeta(Map<String, Object> param);
}