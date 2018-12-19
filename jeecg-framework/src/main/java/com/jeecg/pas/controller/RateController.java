package com.jeecg.pas.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.pas.entity.RateEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Title: Controller
 * @Description: 汇率
 * @date 2018-08-21 10:16:23
 * @version V1.0
 *
 */
@Api(value = "Rate", description = "汇率", tags = "rateController")
@Controller
@RequestMapping("/rateController")
public class RateController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RateController.class);

	@Autowired
	private SystemService systemService;

	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/pas/rateList");
	}

	@RequestMapping(params = "datagrid")
	public void datagrid(RateEntity rate, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RateEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rate, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 批量删除汇率
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "汇率删除成功";
		try {
			for (String id : ids.split(",")) {
				RateEntity rate = systemService.getEntity(RateEntity.class, Integer.parseInt(id));
				systemService.delete(rate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "汇率删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 保存新增/更新的行数据
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(RateEntity page) {
		String message = null;
		List<RateEntity> rateList = page.getRateList();
		AjaxJson j = new AjaxJson();
		if (CollectionUtils.isNotEmpty(rateList)) {
			for (RateEntity rate : rateList) {
				if (StringUtil.isNotEmpty(rate.getId())) {
					RateEntity t = systemService.get(RateEntity.class, rate.getId());
					try {
						message = "Rate例子更新成功";
						MyBeanUtils.copyBeanNotNull2Bean(rate, t);
						systemService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						message = "Rate例子添加成功";
						// jeecgDemo.setStatus("0");
						systemService.save(rate);
						systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
		return j;
	}

	@RequestMapping(params = "get", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取汇率信息", notes = "获取汇率信息", httpMethod = "Get", produces = "application/json")
	public ResponseMessage<?> get(@RequestParam Integer srcCur, @RequestParam Integer tarCur, @RequestParam java.util.Date updateTime) {
		String sql = "SELECT rate FROM ps_rate WHERE src_cur = ? AND tar_cur = ? AND update_time > ? - INTERVAL 24 HOUR AND update_time < ? + INTERVAL 24 HOUR ORDER BY ABS(TIMESTAMPDIFF(SECOND,update_time,?)) LIMIT 1";
		Map<String, Object> result = systemService.findOneForJdbc(sql, srcCur, tarCur, updateTime, updateTime, updateTime);
		Object rate = 0;
		if(result != null) {
			rate = result.get("rate");
		}
		return Result.success(ObjectUtils.defaultIfNull(rate, 0));
	}

}
