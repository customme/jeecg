package com.jeecg.pas.service;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.jeecg.pas.entity.AccountEntity;

public interface AccountServiceI extends CommonService {

	public void delete(AccountEntity entity) throws Exception;

	public Serializable save(AccountEntity entity) throws Exception;

	public void saveOrUpdate(AccountEntity entity) throws Exception;

}
