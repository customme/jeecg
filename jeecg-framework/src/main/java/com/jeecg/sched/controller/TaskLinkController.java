package com.jeecg.sched.controller;
import com.jeecg.sched.entity.TaskLinkEntity;
import com.jeecg.sched.service.TaskLinkServiceI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.collections.CollectionUtils;
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
 * @Description: 任务依赖
 * @author onlineGenerator
 * @date 2018-05-12 15:19:56
 * @version V1.0   
 *
 */
@Api(value="TaskLink",description="任务依赖",tags="taskLinkController")
@Controller
@RequestMapping("/taskLinkController")
public class TaskLinkController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TaskLinkController.class);

	@Autowired
	private TaskLinkServiceI taskLinkService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 任务依赖列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(@RequestParam(required = false) Integer taskId) {
		return new ModelAndView("com/jeecg/sched/taskLinkList").addObject("taskId", taskId);
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
	public void datagrid(TaskLinkEntity taskLink,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TaskLinkEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, taskLink, request.getParameterMap());
		try{
			//自定义追加查询条件
			if(taskLink.getTask() != null && taskLink.getTask().getId() != null) {
				cq.eq("task.id", taskLink.getTask().getId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.taskLinkService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除任务依赖
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TaskLinkEntity taskLink, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		taskLink = systemService.getEntity(TaskLinkEntity.class, taskLink.getId());
		message = "任务依赖删除成功";
		try{
			taskLinkService.delete(taskLink);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务依赖删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除任务依赖
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务依赖删除成功";
		try{
			for(String id:ids.split(",")){
				TaskLinkEntity taskLink = systemService.getEntity(TaskLinkEntity.class, 
				Integer.parseInt(id)
				);
				taskLinkService.delete(taskLink);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "任务依赖删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加任务依赖
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TaskLinkEntity taskLink, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务依赖添加成功";
		try{
			taskLinkService.save(taskLink);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务依赖添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 保存新增/更新的行数据
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(TaskLinkEntity page){
		String message = null;
		List<TaskLinkEntity> taskLinkList=page.getTaskLinkList();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(taskLinkList)){
			for(TaskLinkEntity taskLink:taskLinkList){
				if (StringUtil.isNotEmpty(taskLink.getId())) {
					TaskLinkEntity t =taskLinkService.get(TaskLinkEntity.class, taskLink.getId());
					try {
						message = "TaskLink例子更新成功";
						MyBeanUtils.copyBeanNotNull2Bean(taskLink, t);
						taskLinkService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						message = "TaskLink例子添加成功";
						//jeecgDemo.setStatus("0");
						taskLinkService.save(taskLink);
						systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		return j;
	}
	
	/**
	 * 更新任务依赖
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TaskLinkEntity taskLink, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "任务依赖更新成功";
		TaskLinkEntity t = taskLinkService.get(TaskLinkEntity.class, taskLink.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(taskLink, t);
			taskLinkService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "任务依赖更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 任务依赖新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TaskLinkEntity taskLink, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskLink.getId())) {
			taskLink = taskLinkService.getEntity(TaskLinkEntity.class, taskLink.getId());
			req.setAttribute("taskLinkPage", taskLink);
		}
		return new ModelAndView("com/jeecg/sched/taskLink-add");
	}
	/**
	 * 任务依赖编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TaskLinkEntity taskLink, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(taskLink.getId())) {
			taskLink = taskLinkService.getEntity(TaskLinkEntity.class, taskLink.getId());
			req.setAttribute("taskLinkPage", taskLink);
		}
		return new ModelAndView("com/jeecg/sched/taskLink-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","taskLinkController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TaskLinkEntity taskLink,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TaskLinkEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, taskLink, request.getParameterMap());
		List<TaskLinkEntity> taskLinks = this.taskLinkService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务依赖");
		modelMap.put(NormalExcelConstants.CLASS,TaskLinkEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务依赖列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,taskLinks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TaskLinkEntity taskLink,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"任务依赖");
    	modelMap.put(NormalExcelConstants.CLASS,TaskLinkEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务依赖列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TaskLinkEntity> listTaskLinkEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TaskLinkEntity.class,params);
				for (TaskLinkEntity taskLink : listTaskLinkEntitys) {
					taskLinkService.save(taskLink);
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
	@ApiOperation(value="任务依赖列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TaskLinkEntity>> list() {
		List<TaskLinkEntity> listTaskLinks=taskLinkService.getList(TaskLinkEntity.class);
		return Result.success(listTaskLinks);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取任务依赖信息",notes="根据ID获取任务依赖信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TaskLinkEntity task = taskLinkService.get(TaskLinkEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取任务依赖信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建任务依赖")
	public ResponseMessage<?> create(@ApiParam(name="任务依赖对象")@RequestBody TaskLinkEntity taskLink, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskLinkEntity>> failures = validator.validate(taskLink);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskLinkService.save(taskLink);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务依赖信息保存失败");
		}
		return Result.success(taskLink);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新任务依赖",notes="更新任务依赖")
	public ResponseMessage<?> update(@ApiParam(name="任务依赖对象")@RequestBody TaskLinkEntity taskLink) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TaskLinkEntity>> failures = validator.validate(taskLink);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			taskLinkService.saveOrUpdate(taskLink);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新任务依赖信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新任务依赖信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除任务依赖")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			taskLinkService.deleteEntityById(TaskLinkEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("任务依赖删除失败");
		}

		return Result.success();
	}
}
