package com.jeecg.pas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.pas.entity.AccountEntity;
import com.jeecg.pas.service.AccountServiceI;

import io.swagger.annotations.Api;

/**
 * @Title: Controller
 * @Description: 收入支出
 * @date 2018-08-17 10:15:35
 * @version V1.0
 *
 */
@Api(value = "Account", tags = "accountController")
@Controller
@RequestMapping("/accountController")
public class AccountController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	private AccountServiceI accountService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/pas/accountList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(AccountEntity account, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(AccountEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, account, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.accountService.getDataGridReturn(cq, true);
		// 合计
		dataGrid.setFooter("bizDate:合计,goodsCost,expense,receivable,received,profit,actualProfit");
		TagUtil.datagrid(response, dataGrid);
	}

}
