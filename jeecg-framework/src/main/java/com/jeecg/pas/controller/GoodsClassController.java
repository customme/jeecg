package com.jeecg.pas.controller;
import com.jeecg.pas.entity.GoodsClassEntity;
import com.jeecg.pas.service.GoodsClassServiceI;
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
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
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
 * @Description: 商品分类
 * @author onlineGenerator
 * @date 2018-06-01 19:28:06
 * @version V1.0   
 *
 */
@Api(value="GoodsClass",description="商品分类",tags="goodsClassController")
@Controller
@RequestMapping("/goodsClassController")
public class GoodsClassController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GoodsClassController.class);

	@Autowired
	private GoodsClassServiceI goodsClassService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 商品分类列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/pas/goodsClassList");
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
	public void datagrid(GoodsClassEntity goodsClass,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(GoodsClassEntity.class, dataGrid);
		if(goodsClass.getId() == null){
			cq.isNull("parent");
		}else{
			cq.eq("parent.id", goodsClass.getId());
			goodsClass.setId(null);
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, goodsClass, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.goodsClassService.getDataGridReturn(cq, true);
		TagUtil.treegrid(response, dataGrid);
	}
	
	/**
	 * 删除商品分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(GoodsClassEntity goodsClass, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		goodsClass = systemService.getEntity(GoodsClassEntity.class, goodsClass.getId());
		message = "商品分类删除成功";
		try{
			goodsClassService.delete(goodsClass);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品分类
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品分类删除成功";
		try{
			for(String id:ids.split(",")){
				GoodsClassEntity goodsClass = systemService.getEntity(GoodsClassEntity.class, 
				Integer.parseInt(id)
				);
				goodsClassService.delete(goodsClass);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商品分类
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(GoodsClassEntity goodsClass, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品分类添加成功";
		try{
			if(goodsClass.getParent() == null) {
			}else if(StringUtils.isBlank(goodsClass.getParent().getId())) {
				goodsClass.setParent(null);
			}
			goodsClassService.save(goodsClass);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品分类添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商品分类
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(GoodsClassEntity goodsClass, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品分类更新成功";
		GoodsClassEntity t = goodsClassService.get(GoodsClassEntity.class, goodsClass.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(goodsClass, t);
			if(t.getParent() == null) {
			}else if(StringUtils.isBlank(t.getParent().getId())) {
				t.setParent(null);
			}
			goodsClassService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品分类更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 商品分类新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(GoodsClassEntity goodsClass, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(goodsClass.getId())) {
			goodsClass = goodsClassService.getEntity(GoodsClassEntity.class, goodsClass.getId());
			req.setAttribute("goodsClassPage", goodsClass);
		}
		return new ModelAndView("com/jeecg/pas/goodsClass-add");
	}
	/**
	 * 商品分类编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(GoodsClassEntity goodsClass, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(goodsClass.getId())) {
			goodsClass = goodsClassService.getEntity(GoodsClassEntity.class, goodsClass.getId());
			req.setAttribute("goodsClassPage", goodsClass);
		}
		return new ModelAndView("com/jeecg/pas/goodsClass-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","goodsClassController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(GoodsClassEntity goodsClass,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(GoodsClassEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, goodsClass, request.getParameterMap());
		List<GoodsClassEntity> goodsClasss = this.goodsClassService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品分类");
		modelMap.put(NormalExcelConstants.CLASS,GoodsClassEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品分类列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,goodsClasss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(GoodsClassEntity goodsClass,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"商品分类");
    	modelMap.put(NormalExcelConstants.CLASS,GoodsClassEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品分类列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<GoodsClassEntity> listGoodsClassEntitys = ExcelImportUtil.importExcel(file.getInputStream(),GoodsClassEntity.class,params);
				for (GoodsClassEntity goodsClass : listGoodsClassEntitys) {
					goodsClassService.save(goodsClass);
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
	@ApiOperation(value="商品分类列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<GoodsClassEntity>> list() {
		List<GoodsClassEntity> listGoodsClasss=goodsClassService.getList(GoodsClassEntity.class);
		return Result.success(listGoodsClasss);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取商品分类信息",notes="根据ID获取商品分类信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		GoodsClassEntity task = goodsClassService.get(GoodsClassEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取商品分类信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建商品分类")
	public ResponseMessage<?> create(@ApiParam(name="商品分类对象")@RequestBody GoodsClassEntity goodsClass, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<GoodsClassEntity>> failures = validator.validate(goodsClass);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			goodsClassService.save(goodsClass);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("商品分类信息保存失败");
		}
		return Result.success(goodsClass);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新商品分类",notes="更新商品分类")
	public ResponseMessage<?> update(@ApiParam(name="商品分类对象")@RequestBody GoodsClassEntity goodsClass) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<GoodsClassEntity>> failures = validator.validate(goodsClass);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			goodsClassService.saveOrUpdate(goodsClass);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新商品分类信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新商品分类信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除商品分类")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			goodsClassService.deleteEntityById(GoodsClassEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("商品分类删除失败");
		}

		return Result.success();
	}

	/**
	 * 父级DEMO下拉菜单
	 */
	@RequestMapping(params = "getComboTreeData")
	@ResponseBody
	public List<ComboTree> getComboTreeData(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(GoodsClassEntity.class);
		if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<GoodsClassEntity> demoList = goodsClassService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "className", "children");
		comboTrees =  goodsClassService.ComboTree(demoList, comboTreeModel, null, false);
		return comboTrees;
	}
	
}
