package com.jeecg.pas.controller;
import com.jeecg.pas.entity.GoodsEntity;
import com.jeecg.pas.entity.ReceiptItemEntity;
import com.jeecg.pas.service.ReceiptItemServiceI;
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
 * @Description: 小票明细
 * @author onlineGenerator
 * @date 2018-06-01 19:37:51
 * @version V1.0   
 *
 */
@Api(value="ReceiptItem",description="小票明细",tags="receiptItemController")
@Controller
@RequestMapping("/receiptItemController")
public class ReceiptItemController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReceiptItemController.class);

	@Autowired
	private ReceiptItemServiceI receiptItemService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 订单明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(@RequestParam(required = false) Integer receiptId) {
		return new ModelAndView("com/jeecg/pas/receiptItemList").addObject("receiptId", receiptId);
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
	public void datagrid(ReceiptItemEntity receiptItem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReceiptItemEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, receiptItem, request.getParameterMap());
		try{
			//自定义追加查询条件
			if(receiptItem.getReceipt() != null && receiptItem.getReceipt().getId() != null) {
				cq.eq("receipt.id", receiptItem.getReceipt().getId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.receiptItemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除订单明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ReceiptItemEntity receiptItem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		receiptItem = systemService.getEntity(ReceiptItemEntity.class, receiptItem.getId());
		message = "订单明细删除成功";
		try{
			receiptItemService.delete(receiptItem);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除订单明细
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "订单明细删除成功";
		try{
			for(String id:ids.split(",")){
				ReceiptItemEntity receiptItem = systemService.getEntity(ReceiptItemEntity.class, 
				Integer.parseInt(id)
				);
				receiptItemService.delete(receiptItem);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订单明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加订单明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ReceiptItemEntity receiptItem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "订单明细添加成功";
		try{
			receiptItemService.save(receiptItem);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单明细添加失败";
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
	public AjaxJson saveRows(ReceiptItemEntity page){
		String message = null;
		List<ReceiptItemEntity> receiptItemList=page.getReceiptItemList();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(receiptItemList)){
			for(ReceiptItemEntity receiptItem:receiptItemList){
				if (StringUtil.isNotEmpty(receiptItem.getId())) {
					ReceiptItemEntity t =receiptItemService.get(ReceiptItemEntity.class, receiptItem.getId());
					try {
						message = "ReceiptItem例子更新成功";
						MyBeanUtils.copyBeanNotNull2Bean(receiptItem, t);
						receiptItemService.saveOrUpdate(t);

						// 更新库存 进价
						GoodsEntity goods = systemService.getEntity(GoodsEntity.class, t.getGoods().getId());
						goods.setStock(goods.getStock() + t.getQuantity());
						// 如果商品价格上涨则更新
						if(t.getUnitPrice() > goods.getPrice()) {
							goods.setPrice(t.getUnitPrice());
						}
						systemService.updateEntitie(goods);

						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						message = "ReceiptItem例子添加成功";
						//jeecgDemo.setStatus("0");
						receiptItemService.save(receiptItem);
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
	 * 更新订单明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ReceiptItemEntity receiptItem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "订单明细更新成功";
		ReceiptItemEntity t = receiptItemService.get(ReceiptItemEntity.class, receiptItem.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(receiptItem, t);
			receiptItemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "订单明细更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 订单明细新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ReceiptItemEntity receiptItem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(receiptItem.getId())) {
			receiptItem = receiptItemService.getEntity(ReceiptItemEntity.class, receiptItem.getId());
			req.setAttribute("receiptItemPage", receiptItem);
		}
		return new ModelAndView("com/jeecg/pas/receiptItem-add");
	}
	/**
	 * 订单明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ReceiptItemEntity receiptItem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(receiptItem.getId())) {
			receiptItem = receiptItemService.getEntity(ReceiptItemEntity.class, receiptItem.getId());
			req.setAttribute("receiptItemPage", receiptItem);
		}
		return new ModelAndView("com/jeecg/pas/receiptItem-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","receiptItemController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ReceiptItemEntity receiptItem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ReceiptItemEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, receiptItem, request.getParameterMap());
		List<ReceiptItemEntity> receiptItems = this.receiptItemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"订单明细");
		modelMap.put(NormalExcelConstants.CLASS,ReceiptItemEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("订单明细列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,receiptItems);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ReceiptItemEntity receiptItem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"订单明细");
    	modelMap.put(NormalExcelConstants.CLASS,ReceiptItemEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("订单明细列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<ReceiptItemEntity> listReceiptItemEntitys = ExcelImportUtil.importExcel(file.getInputStream(),ReceiptItemEntity.class,params);
				for (ReceiptItemEntity receiptItem : listReceiptItemEntitys) {
					receiptItemService.save(receiptItem);
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
	@ApiOperation(value="订单明细列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<ReceiptItemEntity>> list() {
		List<ReceiptItemEntity> listReceiptItems=receiptItemService.getList(ReceiptItemEntity.class);
		return Result.success(listReceiptItems);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取订单明细信息",notes="根据ID获取订单明细信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		ReceiptItemEntity task = receiptItemService.get(ReceiptItemEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取订单明细信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建订单明细")
	public ResponseMessage<?> create(@ApiParam(name="订单明细对象")@RequestBody ReceiptItemEntity receiptItem, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<ReceiptItemEntity>> failures = validator.validate(receiptItem);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			receiptItemService.save(receiptItem);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("订单明细信息保存失败");
		}
		return Result.success(receiptItem);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新订单明细",notes="更新订单明细")
	public ResponseMessage<?> update(@ApiParam(name="订单明细对象")@RequestBody ReceiptItemEntity receiptItem) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<ReceiptItemEntity>> failures = validator.validate(receiptItem);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			receiptItemService.saveOrUpdate(receiptItem);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新订单明细信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新订单明细信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除订单明细")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			receiptItemService.deleteEntityById(ReceiptItemEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("订单明细删除失败");
		}

		return Result.success();
	}
}
