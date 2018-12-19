package com.jeecg.pas.controller;
import com.jeecg.pas.entity.CustomerClassEntity;
import com.jeecg.pas.service.CustomerClassServiceI;
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
 * @Description: 客户分级
 * @author onlineGenerator
 * @date 2018-06-01 19:38:18
 * @version V1.0   
 *
 */
@Api(value="CustomerClass",description="客户分级",tags="customerClassController")
@Controller
@RequestMapping("/customerClassController")
public class CustomerClassController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerClassController.class);

	@Autowired
	private CustomerClassServiceI customerClassService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 客户分级列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/pas/customerClassList");
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
	public void datagrid(CustomerClassEntity customerClass,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerClassEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerClass, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.customerClassService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除客户分级
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerClassEntity customerClass, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		customerClass = systemService.getEntity(CustomerClassEntity.class, customerClass.getId());
		message = "客户分级删除成功";
		try{
			customerClassService.delete(customerClass);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户分级删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除客户分级
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户分级删除成功";
		try{
			for(String id:ids.split(",")){
				CustomerClassEntity customerClass = systemService.getEntity(CustomerClassEntity.class, 
				Integer.parseInt(id)
				);
				customerClassService.delete(customerClass);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "客户分级删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加客户分级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(CustomerClassEntity customerClass, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户分级添加成功";
		try{
			customerClassService.save(customerClass);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户分级添加失败";
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
	public AjaxJson saveRows(CustomerClassEntity page){
		String message = null;
		List<CustomerClassEntity> customerClassList=page.getCustomerClassList();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(customerClassList)){
			for(CustomerClassEntity customerClass:customerClassList){
				if (StringUtil.isNotEmpty(customerClass.getId())) {
					CustomerClassEntity t =customerClassService.get(CustomerClassEntity.class, customerClass.getId());
					try {
						message = "CustomerClass例子更新成功";
						MyBeanUtils.copyBeanNotNull2Bean(customerClass, t);
						customerClassService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						message = "CustomerClass例子添加成功";
						//jeecgDemo.setStatus("0");
						customerClassService.save(customerClass);
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
	 * 更新客户分级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CustomerClassEntity customerClass, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户分级更新成功";
		CustomerClassEntity t = customerClassService.get(CustomerClassEntity.class, customerClass.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(customerClass, t);
			customerClassService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "客户分级更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 客户分级新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CustomerClassEntity customerClass, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerClass.getId())) {
			customerClass = customerClassService.getEntity(CustomerClassEntity.class, customerClass.getId());
			req.setAttribute("customerClassPage", customerClass);
		}
		return new ModelAndView("com/jeecg/pas/customerClass-add");
	}
	/**
	 * 客户分级编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerClassEntity customerClass, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerClass.getId())) {
			customerClass = customerClassService.getEntity(CustomerClassEntity.class, customerClass.getId());
			req.setAttribute("customerClassPage", customerClass);
		}
		return new ModelAndView("com/jeecg/pas/customerClass-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","customerClassController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(CustomerClassEntity customerClass,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(CustomerClassEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerClass, request.getParameterMap());
		List<CustomerClassEntity> customerClasss = this.customerClassService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"客户分级");
		modelMap.put(NormalExcelConstants.CLASS,CustomerClassEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户分级列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,customerClasss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(CustomerClassEntity customerClass,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"客户分级");
    	modelMap.put(NormalExcelConstants.CLASS,CustomerClassEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户分级列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<CustomerClassEntity> listCustomerClassEntitys = ExcelImportUtil.importExcel(file.getInputStream(),CustomerClassEntity.class,params);
				for (CustomerClassEntity customerClass : listCustomerClassEntitys) {
					customerClassService.save(customerClass);
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
	@ApiOperation(value="客户分级列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<CustomerClassEntity>> list() {
		List<CustomerClassEntity> listCustomerClasss=customerClassService.getList(CustomerClassEntity.class);
		return Result.success(listCustomerClasss);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取客户分级信息",notes="根据ID获取客户分级信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		CustomerClassEntity task = customerClassService.get(CustomerClassEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取客户分级信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建客户分级")
	public ResponseMessage<?> create(@ApiParam(name="客户分级对象")@RequestBody CustomerClassEntity customerClass, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<CustomerClassEntity>> failures = validator.validate(customerClass);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			customerClassService.save(customerClass);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("客户分级信息保存失败");
		}
		return Result.success(customerClass);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新客户分级",notes="更新客户分级")
	public ResponseMessage<?> update(@ApiParam(name="客户分级对象")@RequestBody CustomerClassEntity customerClass) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<CustomerClassEntity>> failures = validator.validate(customerClass);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			customerClassService.saveOrUpdate(customerClass);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新客户分级信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新客户分级信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除客户分级")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			customerClassService.deleteEntityById(CustomerClassEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("客户分级删除失败");
		}

		return Result.success();
	}
}
