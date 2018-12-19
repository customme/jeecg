package com.jeecg.sched.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.jeecg.sched.entity.TaskEntity;
import com.jeecg.sched.entity.TaskExtEntity;
import com.jeecg.sched.entity.TaskLinkEntity;
import com.jeecg.sched.service.TaskExtServiceI;
import com.jeecg.sched.service.TaskLinkServiceI;
import com.jeecg.sched.service.TaskServiceI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 任务
 * @author onlineGenerator
 * @date 2018-05-10 17:38:43
 * @version V1.0   
 *
 */
@Api(value="Task",description="任务",tags="taskController")
@Controller
@RequestMapping("/taskController")
public class TaskController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TaskController.class);

	@Autowired
	private TaskServiceI taskService;
	@Autowired
	private TaskExtServiceI taskExtService;
	@Autowired
	private TaskLinkServiceI taskLinkService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 任务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/sched/taskList");
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
	public void datagrid(TaskEntity task,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TaskEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, task, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_startTime_begin = request.getParameter("startTime_begin");
		String query_startTime_end = request.getParameter("startTime_end");
		if(StringUtil.isNotEmpty(query_startTime_begin)){
			cq.ge("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_startTime_begin));
		}
		if(StringUtil.isNotEmpty(query_startTime_end)){
			cq.le("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_startTime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.taskService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 任务编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(TaskEntity task, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(task.getId())) {
			task = taskService.getEntity(TaskEntity.class, task.getId());
			req.setAttribute("taskPage", task);
		}
		return new ModelAndView("com/jeecg/sched/task-detail");
	}

	/**
	 * 删除任务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TaskEntity task, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		task = systemService.getEntity(TaskEntity.class, task.getId());
		message = "任务删除成功";
		try{
			taskService.delete(task);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除任务
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务删除成功";
		try{
			for(String id:ids.split(",")){
				TaskEntity task = systemService.getEntity(TaskEntity.class, 
				Integer.parseInt(id)
				);
				taskService.delete(task);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "任务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加任务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TaskEntity task, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务添加成功";
		try{
			taskService.save(task.setDefault());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新任务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TaskEntity task, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务更新成功";
		TaskEntity t = taskService.get(TaskEntity.class, task.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(task, t);
			taskService.saveOrUpdate(t.setDefault());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "任务更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 复制任务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doCopy")
	@ResponseBody
	public AjaxJson doCopy(TaskEntity task, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务复制成功";
		try{
			Integer taskId = task.getId();
			task.setId(null);
			Integer newTaskId = (Integer)taskService.save(task.setDefault());

			// 复制扩展属性
			if(task.getCopyExt() != null) {
				List<TaskExtEntity> taskExtList = taskExtService.findByProperty(TaskExtEntity.class, "taskId", taskId);
				List<TaskExtEntity> newTaskExtList = new ArrayList<TaskExtEntity>();
				for(TaskExtEntity taskExt : taskExtList) {
					TaskExtEntity newTaskExt = new TaskExtEntity();
					MyBeanUtils.copyBeanNotNull2Bean(taskExt, newTaskExt);
					newTaskExt.setTaskId(newTaskId);
					newTaskExtList.add(newTaskExt.reset());
				}
				taskExtService.batchSave(newTaskExtList);
			}
			
			// 复制任务依赖
			if(task.getCopyLink() != null) {
				List<TaskLinkEntity> taskLinkList = taskLinkService.findByProperty(TaskLinkEntity.class, "taskId", taskId);
				List<TaskLinkEntity> newTaskLinkList = new ArrayList<TaskLinkEntity>();
				TaskEntity newTask = taskService.getEntity(TaskEntity.class, newTaskId);
				for(TaskLinkEntity taskLink : taskLinkList) {
					TaskLinkEntity newTaskLink = new TaskLinkEntity();
					MyBeanUtils.copyBeanNotNull2Bean(taskLink, newTaskLink);
					newTaskLink.setTask(newTask);
					newTaskLinkList.add(newTaskLink.reset());
				}
				taskLinkService.batchSave(newTaskLinkList);
			}
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务复制失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 任务新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TaskEntity task, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(task.getId())) {
			task = taskService.getEntity(TaskEntity.class, task.getId());
			req.setAttribute("taskPage", task);
		}
		return new ModelAndView("com/jeecg/sched/task-add");
	}
	/**
	 * 任务编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TaskEntity task, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(task.getId())) {
			task = taskService.getEntity(TaskEntity.class, task.getId());
			req.setAttribute("taskPage", task);
		}
		return new ModelAndView("com/jeecg/sched/task-update");
	}
	
	/**
	 * 任务复制页面跳转
	 * @param task
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goCopy")
	public ModelAndView goCopy(TaskEntity task, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(task.getId())) {
			task = taskService.getEntity(TaskEntity.class, task.getId());
			req.setAttribute("taskPage", task);
		}
		return new ModelAndView("com/jeecg/sched/task-copy");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","taskController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TaskEntity task,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TaskEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, task, request.getParameterMap());
		List<TaskEntity> tasks = this.taskService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务");
		modelMap.put(NormalExcelConstants.CLASS,TaskEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tasks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TaskEntity task,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"任务");
    	modelMap.put(NormalExcelConstants.CLASS,TaskEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TaskEntity> listTaskEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TaskEntity.class,params);
				for (TaskEntity task : listTaskEntitys) {
					taskService.save(task.setDefault());
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
	@ApiOperation(value="任务列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TaskEntity>> list() {
		List<TaskEntity> listTasks=taskService.getList(TaskEntity.class);
		return Result.success(listTasks);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取任务信息",notes="根据ID获取任务信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TaskEntity task = taskService.get(TaskEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取任务信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建任务")
	public ResponseMessage<?> create(@ApiParam(name="任务对象")@RequestBody TaskEntity task, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskEntity>> failures = validator.validate(task);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskService.save(task.setDefault());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务信息保存失败");
		}
		return Result.success(task);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新任务",notes="更新任务")
	public ResponseMessage<?> update(@ApiParam(name="任务对象")@RequestBody TaskEntity task) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskEntity>> failures = validator.validate(task);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskService.saveOrUpdate(task.setDefault());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新任务信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新任务信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除任务")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			taskService.deleteEntityById(TaskEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务删除失败");
		}

		return Result.success();
	}
}
