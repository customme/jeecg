package com.jeecg.sched.controller;
import com.jeecg.sched.entity.TaskEntity;
import com.jeecg.sched.entity.TaskLogEntity;
import com.jeecg.sched.service.TaskLogServiceI;
import com.jeecg.sched.service.TaskServiceI;
import com.jeecg.sched.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;

import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 任务日志
 * @author onlineGenerator
 * @date 2018-05-10 17:40:00
 * @version V1.0   
 *
 */
@Api(value="TaskLog",description="任务日志",tags="taskLogController")
@Controller
@RequestMapping("/taskLogController")
public class TaskLogController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TaskLogController.class);

	@Autowired
	private TaskLogServiceI taskLogService;
	@Autowired
	private TaskServiceI taskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 任务日志列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(@RequestParam(required = false) Integer taskId, @RequestParam(required = false) Date runTime) {
		TaskEntity task = null;
		if(taskId != null) {
			task = taskService.getEntity(TaskEntity.class, taskId);
		}else {
			task = new TaskEntity();
			task.setId(0);
		}
		return new ModelAndView("com/jeecg/sched/taskLogList").addObject("task", task).addObject("runTime", DateUtils.date2Str(runTime, DateUtils.datetimeFormat));
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TaskLogEntity taskLog, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TaskLogEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, taskLog, request.getParameterMap());
		try {
			// 自定义追加查询条件
			String taskIds = request.getParameter("taskIds");
			if (StringUtil.isNotEmpty(taskIds) && ! "0".equals(taskIds)) {
				String[] taskIdArr = taskIds.split(",");
				cq.in("task.id", ArrayUtils.str2Int(taskIdArr));
			}
			String query_runTime_begin = request.getParameter("runTime_begin");
			String query_runTime_end = request.getParameter("runTime_end");
			if (StringUtil.isNotEmpty(query_runTime_begin)) {
				cq.ge("runTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_runTime_begin));
			}
			if (StringUtil.isNotEmpty(query_runTime_end)) {
				cq.le("runTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_runTime_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.taskLogService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除任务日志
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TaskLogEntity taskLog, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		taskLog = systemService.getEntity(TaskLogEntity.class, taskLog.getId());
		message = "任务日志删除成功";
		try{
			taskLogService.delete(taskLog);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务日志删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除任务日志
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务日志删除成功";
		try{
			for(String id:ids.split(",")){
				TaskLogEntity taskLog = systemService.getEntity(TaskLogEntity.class, 
				Integer.parseInt(id)
				);
				taskLogService.delete(taskLog);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "任务日志删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加任务日志
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TaskLogEntity taskLog, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务日志添加成功";
		try{
			taskLogService.save(taskLog);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务日志添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新任务日志
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TaskLogEntity taskLog, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务日志更新成功";
		TaskLogEntity t = taskLogService.get(TaskLogEntity.class, taskLog.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(taskLog, t);
			taskLogService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "任务日志更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 任务日志新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TaskLogEntity taskLog, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskLog.getId())) {
			taskLog = taskLogService.getEntity(TaskLogEntity.class, taskLog.getId());
			req.setAttribute("taskLogPage", taskLog);
		}
		return new ModelAndView("com/jeecg/sched/taskLog-add");
	}
	/**
	 * 任务日志编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TaskLogEntity taskLog, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskLog.getId())) {
			taskLog = taskLogService.getEntity(TaskLogEntity.class, taskLog.getId());
			req.setAttribute("taskLogPage", taskLog);
		}
		return new ModelAndView("com/jeecg/sched/taskLog-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","taskLogController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TaskLogEntity taskLog,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TaskLogEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, taskLog, request.getParameterMap());
		List<TaskLogEntity> taskLogs = this.taskLogService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务日志");
		modelMap.put(NormalExcelConstants.CLASS,TaskLogEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务日志列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,taskLogs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TaskLogEntity taskLog,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"任务日志");
    	modelMap.put(NormalExcelConstants.CLASS,TaskLogEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务日志列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TaskLogEntity> listTaskLogEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TaskLogEntity.class,params);
				for (TaskLogEntity taskLog : listTaskLogEntitys) {
					taskLogService.save(taskLog);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="任务日志列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TaskLogEntity>> list() {
		List<TaskLogEntity> listTaskLogs=taskLogService.getList(TaskLogEntity.class);
		return Result.success(listTaskLogs);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取任务日志信息",notes="根据ID获取任务日志信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TaskLogEntity task = taskLogService.get(TaskLogEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取任务日志信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建任务日志")
	public ResponseMessage<?> create(@ApiParam(name="任务日志对象")@RequestBody TaskLogEntity taskLog, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskLogEntity>> failures = validator.validate(taskLog);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskLogService.save(taskLog);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务日志信息保存失败");
		}
		return Result.success(taskLog);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新任务日志",notes="更新任务日志")
	public ResponseMessage<?> update(@ApiParam(name="任务日志对象")@RequestBody TaskLogEntity taskLog) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskLogEntity>> failures = validator.validate(taskLog);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskLogService.saveOrUpdate(taskLog);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新任务日志信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新任务日志信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除任务日志")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			taskLogService.deleteEntityById(TaskLogEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务日志删除失败");
		}

		return Result.success();
	}
}
