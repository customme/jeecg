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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.jeecg.sched.entity.TaskEntity;
import com.jeecg.sched.entity.TaskPoolEntity;
import com.jeecg.sched.service.TaskPoolServiceI;
import com.jeecg.sched.service.TaskServiceI;
import com.jeecg.sched.utils.ArrayUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 任务实例
 * @author onlineGenerator
 * @date 2018-05-14 14:30:07
 * @version V1.0   
 *
 */
@Api(value="TaskPool",description="任务实例",tags="taskPoolController")
@Controller
@RequestMapping("/taskPoolController")
public class TaskPoolController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TaskPoolController.class);

	@Autowired
	private TaskPoolServiceI taskPoolService;
	@Autowired
	private TaskServiceI taskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 任务实例列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(@RequestParam(required = false) Integer taskId) {
		TaskEntity task = null;
		if(taskId != null) {
			task = taskService.getEntity(TaskEntity.class, taskId);
		}else {
			task = new TaskEntity();
			task.setId(0);
		}
		return new ModelAndView("com/jeecg/sched/taskPoolList").addObject("task", task);
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
	public void datagrid(TaskPoolEntity taskPool, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TaskPoolEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, taskPool, request.getParameterMap());
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
		this.taskPoolService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除任务实例
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TaskPoolEntity taskPool, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		taskPool = systemService.getEntity(TaskPoolEntity.class, taskPool.getId());
		message = "任务实例删除成功";
		try{
			taskPoolService.delete(taskPool);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务实例删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除任务实例
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务实例删除成功";
		try{
			for(String id:ids.split(",")){
				TaskPoolEntity taskPool = systemService.getEntity(TaskPoolEntity.class, 
				Integer.parseInt(id)
				);
				taskPoolService.delete(taskPool);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "任务实例删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加任务实例
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TaskPoolEntity taskPool, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务实例添加成功";
		try{
			taskPoolService.save(taskPool.setDefault());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务实例添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新任务实例
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TaskPoolEntity taskPool, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务实例更新成功";
		TaskPoolEntity t = taskPoolService.get(TaskPoolEntity.class, taskPool.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(taskPool, t);
			taskPoolService.saveOrUpdate(t.setDefault());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "任务实例更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 任务实例新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TaskPoolEntity taskPool, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskPool.getId())) {
			taskPool = taskPoolService.getEntity(TaskPoolEntity.class, taskPool.getId());
			req.setAttribute("taskPoolPage", taskPool);
		}
		return new ModelAndView("com/jeecg/sched/taskPool-add");
	}
	/**
	 * 任务实例编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TaskPoolEntity taskPool, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskPool.getId())) {
			taskPool = taskPoolService.getEntity(TaskPoolEntity.class, taskPool.getId());
			req.setAttribute("taskPoolPage", taskPool);
		}
		return new ModelAndView("com/jeecg/sched/taskPool-update");
	}
	/**
	 * 任务实例复制页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goCopy")
	public ModelAndView goCopy(TaskPoolEntity taskPool, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskPool.getId())) {
			taskPool = taskPoolService.getEntity(TaskPoolEntity.class, taskPool.getId());
			req.setAttribute("taskPoolPage", taskPool);
		}
		return new ModelAndView("com/jeecg/sched/taskPool-copy");
	}

	/**
	 * 任务实例详情页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(TaskPoolEntity taskPool, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskPool.getId())) {
			taskPool = taskPoolService.getEntity(TaskPoolEntity.class, taskPool.getId());
			req.setAttribute("taskPoolPage", taskPool);
		}
		return new ModelAndView("com/jeecg/sched/taskPool-detail");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","taskPoolController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TaskPoolEntity taskPool,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TaskPoolEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, taskPool, request.getParameterMap());
		List<TaskPoolEntity> taskPools = this.taskPoolService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务实例");
		modelMap.put(NormalExcelConstants.CLASS,TaskPoolEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务实例列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,taskPools);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TaskPoolEntity taskPool,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"任务实例");
    	modelMap.put(NormalExcelConstants.CLASS,TaskPoolEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务实例列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TaskPoolEntity> listTaskPoolEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TaskPoolEntity.class,params);
				for (TaskPoolEntity taskPool : listTaskPoolEntitys) {
					taskPoolService.save(taskPool.setDefault());
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
	@ApiOperation(value="任务实例列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TaskPoolEntity>> list() {
		List<TaskPoolEntity> listTaskPools=taskPoolService.getList(TaskPoolEntity.class);
		return Result.success(listTaskPools);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取任务实例信息",notes="根据ID获取任务实例信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TaskPoolEntity task = taskPoolService.get(TaskPoolEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取任务实例信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建任务实例")
	public ResponseMessage<?> create(@ApiParam(name="任务实例对象")@RequestBody TaskPoolEntity taskPool, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskPoolEntity>> failures = validator.validate(taskPool);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskPoolService.save(taskPool.setDefault());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务实例信息保存失败");
		}
		return Result.success(taskPool);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新任务实例",notes="更新任务实例")
	public ResponseMessage<?> update(@ApiParam(name="任务实例对象")@RequestBody TaskPoolEntity taskPool) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskPoolEntity>> failures = validator.validate(taskPool);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskPoolService.saveOrUpdate(taskPool.setDefault());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新任务实例信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新任务实例信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除任务实例")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			taskPoolService.deleteEntityById(TaskPoolEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务实例删除失败");
		}

		return Result.success();
	}
}
