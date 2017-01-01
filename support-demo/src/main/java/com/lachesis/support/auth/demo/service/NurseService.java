package com.lachesis.support.auth.demo.service;

import java.util.List;

import com.lachesis.support.auth.demo.vo.SimpleUserVo;

public interface NurseService {
	List<SimpleUserVo> listAllOfDepartment(String deptId);
	SimpleUserVo findOneUser(String userId);
}
