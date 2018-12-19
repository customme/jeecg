package com.jeecg.sched.controller;
import com.jeecg.sched.entity.DbConnEntity;
import com.jeecg.sched.entity.DbTypeEntity;
import com.jeecg.sched.service.DbConnServiceI;
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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 数据库连接
 * @author onlineGenerator
 * @date 2018-05-10 17:39:49
 * @version V1.0   
 *
 */
@Api(value="DbConn",description="数据库连接",tags="dbConnController")
@Controller
@RequestMapping("/dbConnController")
public class DbConnController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DbConnController.class);

	@Autowired
	private DbConnServiceI dbConnService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 数据库连接列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/sched/dbConnList");
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
	public void datagrid(DbConnEntity dbConn,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DbConnEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbConn, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.dbConnService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除数据库连接
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(DbConnEntity dbConn, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		dbConn = systemService.getEntity(DbConnEntity.class, dbConn.getId());
		message = "数据库连接删除成功";
		try{
			dbConnService.delete(dbConn);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "数据库连接删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除数据库连接
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "数据库连接删除成功";
		try{
			for(String id:ids.split(",")){
				DbConnEntity dbConn = systemService.getEntity(DbConnEntity.class, 
				Integer.parseInt(id)
				);
				dbConnService.delete(dbConn);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "数据库连接删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加数据库连接
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(DbConnEntity dbConn, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "数据库连接添加成功";
		try{
			DbTypeEntity dbType = systemService.getEntity(DbTypeEntity.class, dbConn.getTypeId());
			dbConn.setPort(ObjectUtils.defaultIfNull(dbConn.getPort(), dbType.getDefaultPort()));
			dbConn.setCharset(StringUtils.defaultIfBlank(dbConn.getCharset(), dbType.getDefaultCharset()));
			dbConnService.save(dbConn);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "数据库连接添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新数据库连接
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(DbConnEntity dbConn, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "数据库连接更新成功";
		DbConnEntity t = dbConnService.get(DbConnEntity.class, dbConn.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(dbConn, t);
			DbTypeEntity dbType = systemService.getEntity(DbTypeEntity.class, t.getTypeId());
			t.setPort(ObjectUtils.defaultIfNull(t.getPort(), dbType.getDefaultPort()));
			t.setCharset(StringUtils.defaultIfBlank(t.getCharset(), dbType.getDefaultCharset()));
			dbConnService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "数据库连接更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 数据库连接新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(DbConnEntity dbConn, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbConn.getId())) {
			dbConn = dbConnService.getEntity(DbConnEntity.class, dbConn.getId());
			req.setAttribute("dbConnPage", dbConn);
		}
		return new ModelAndView("com/jeecg/sched/dbConn-add");
	}
	/**
	 * 数据库连接编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(DbConnEntity dbConn, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbConn.getId())) {
			dbConn = dbConnService.getEntity(DbConnEntity.class, dbConn.getId());
			req.setAttribute("dbConnPage", dbConn);
		}
		return new ModelAndView("com/jeecg/sched/dbConn-update");
	}
	/**
	 * 数据库连接复制页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goCopy")
	public ModelAndView goCopy(DbConnEntity dbConn, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbConn.getId())) {
			dbConn = dbConnService.getEntity(DbConnEntity.class, dbConn.getId());
			req.setAttribute("dbConnPage", dbConn);
		}
		return new ModelAndView("com/jeecg/sched/dbConn-copy");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","dbConnController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(DbConnEntity dbConn,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(DbConnEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbConn, request.getParameterMap());
		List<DbConnEntity> dbConns = this.dbConnService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"数据库连接");
		modelMap.put(NormalExcelConstants.CLASS,DbConnEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("数据库连接列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,dbConns);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(DbConnEntity dbConn,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"数据库连接");
    	modelMap.put(NormalExcelConstants.CLASS,DbConnEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("数据库连接列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<DbConnEntity> listDbConnEntitys = ExcelImportUtil.importExcel(file.getInputStream(),DbConnEntity.class,params);
				for (DbConnEntity dbConn : listDbConnEntitys) {
					dbConnService.save(dbConn);
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
	@ApiOperation(value="数据库连接列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<DbConnEntity>> list() {
		List<DbConnEntity> listDbConns=dbConnService.getList(DbConnEntity.class);
		return Result.success(listDbConns);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取数据库连接信息",notes="根据ID获取数据库连接信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		DbConnEntity task = dbConnService.get(DbConnEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取数据库连接信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建数据库连接")
	public ResponseMessage<?> create(@ApiParam(name="数据库连接对象")@RequestBody DbConnEntity dbConn, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<DbConnEntity>> failures = validator.validate(dbConn);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			dbConnService.save(dbConn);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("数据库连接信息保存失败");
		}
		return Result.success(dbConn);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新数据库连接",notes="更新数据库连接")
	public ResponseMessage<?> update(@ApiParam(name="数据库连接对象")@RequestBody DbConnEntity dbConn) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<DbConnEntity>> failures = validator.validate(dbConn);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			dbConnService.saveOrUpdate(dbConn);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新数据库连接信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新数据库连接信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除数据库连接")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			dbConnService.deleteEntityById(DbConnEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("数据库连接删除失败");
		}

		return Result.success();
	}
}
