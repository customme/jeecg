package com.jeecg.pas.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
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
import com.jeecg.pas.entity.CustomerEntity;
import com.jeecg.pas.constant.Constant;
import com.jeecg.pas.entity.CartEntity;
import com.jeecg.pas.entity.GoodsEntity;
import com.jeecg.pas.service.CartServiceI;
import com.jeecg.pas.utils.ArrayUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 购物车
 * @author onlineGenerator
 * @date 2018-06-01 19:32:35
 * @version V1.0   
 *
 */
@Api(value="Cart",description="购物车",tags="cartController")
@Controller
@RequestMapping("/cartController")
public class CartController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CartController.class);

	@Autowired
	private CartServiceI cartService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 购物车列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(@RequestParam(required = false) Integer customerId, @RequestParam(required = false) Integer goodsId) {
		CustomerEntity customer = null;
		if(customerId != null) {
			customer = systemService.getEntity(CustomerEntity.class, customerId);
		}else {
			customer = new CustomerEntity();
			customer.setId(0);
		}
		GoodsEntity goods = null;
		if(goodsId != null) {
			goods = systemService.getEntity(GoodsEntity.class, goodsId);
		}else {
			goods = new GoodsEntity();
			goods.setId(0);
		}
		return new ModelAndView("com/jeecg/pas/cartList").addObject("customer", customer).addObject("goods", goods);
	}
	
	/**
	 * 购物车
	 * @param customerId
	 * @return
	 */
	@RequestMapping(params = "cart")
	public ModelAndView cart(@RequestParam(required = false) Integer customerId) {
		return new ModelAndView("com/jeecg/pas/cart").addObject("customerId", customerId);
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
	public void datagrid(CartEntity cart,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CartEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cart.setFuzzy(), request.getParameterMap());
		try{
			//自定义追加查询条件
			String customerIds = request.getParameter("customerIds");
			if(StringUtils.isNotBlank(customerIds) && ! "0".equals(customerIds)) {
				cq.in("customer.id", ArrayUtils.str2Int(customerIds.split(",")));
			}
			String goodsIds = request.getParameter("goodsIds");
			if(StringUtils.isNotBlank(goodsIds) && ! "0".equals(goodsIds)) {
				cq.in("goods.id", ArrayUtils.str2Int(goodsIds.split(",")));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.cartService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除购物车
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CartEntity cart, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		cart = systemService.getEntity(CartEntity.class, cart.getId());
		message = "购物车删除成功";
		try{
			cartService.delete(cart);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "购物车删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 设置购物车状态为“已购买”
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "setBought")
	@ResponseBody
	public AjaxJson setBought(@RequestParam(required = false) Integer id) {
		String message = "购物车更新成功";
		AjaxJson j = new AjaxJson();
		CartEntity cart = systemService.getEntity(CartEntity.class, id);
		cart.setBuyStatus(Constant.CART_BOUGHT);
		cart.setBuyTime(new java.util.Date());
		try {
			cartService.saveOrUpdate(cart);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e) {
			e.printStackTrace();
			message = "购物车更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除购物车
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物车删除成功";
		try{
			for(String id:ids.split(",")){
				CartEntity cart = systemService.getEntity(CartEntity.class, 
				Integer.parseInt(id)
				);
				cartService.delete(cart);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "购物车删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加购物车
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(CartEntity cart, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物车添加成功";
		try{
			cartService.save(cart);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "购物车添加失败";
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
	public AjaxJson saveRows(CartEntity page){
		String message = null;
		List<CartEntity> cartList=page.getCartList();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(cartList)){
			for(CartEntity cart:cartList){
				if (StringUtil.isNotEmpty(cart.getId())) {
					CartEntity t =cartService.get(CartEntity.class, cart.getId());
					try {
						message = "Cart例子更新成功";
						MyBeanUtils.copyBeanNotNull2Bean(cart, t);
						cartService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						message = "Cart例子添加成功";
						//jeecgDemo.setStatus("0");
						cartService.save(cart);
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
	 * 更新购物车
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CartEntity cart, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物车更新成功";
		CartEntity t = cartService.get(CartEntity.class, cart.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(cart, t);
			cartService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "购物车更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 购物车新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CartEntity cart, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cart.getId())) {
			cart = cartService.getEntity(CartEntity.class, cart.getId());
			req.setAttribute("cartPage", cart);
		}
		return new ModelAndView("com/jeecg/pas/cart-add");
	}
	/**
	 * 购物车编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CartEntity cart, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cart.getId())) {
			cart = cartService.getEntity(CartEntity.class, cart.getId());
			req.setAttribute("cartPage", cart);
		}
		return new ModelAndView("com/jeecg/pas/cart-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","cartController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(CartEntity cart,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(CartEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cart, request.getParameterMap());
		List<CartEntity> carts = this.cartService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"购物车");
		modelMap.put(NormalExcelConstants.CLASS,CartEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("购物车列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,carts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(CartEntity cart,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"购物车");
    	modelMap.put(NormalExcelConstants.CLASS,CartEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("购物车列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<CartEntity> listCartEntitys = ExcelImportUtil.importExcel(file.getInputStream(),CartEntity.class,params);
				for (CartEntity cart : listCartEntitys) {
					cartService.save(cart);
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
	@ApiOperation(value="购物车列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<CartEntity>> list() {
		List<CartEntity> listCarts=cartService.getList(CartEntity.class);
		return Result.success(listCarts);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取购物车信息",notes="根据ID获取购物车信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		CartEntity task = cartService.get(CartEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取购物车信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建购物车")
	public ResponseMessage<?> create(@ApiParam(name="购物车对象")@RequestBody CartEntity cart, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<CartEntity>> failures = validator.validate(cart);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			cartService.save(cart);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("购物车信息保存失败");
		}
		return Result.success(cart);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新购物车",notes="更新购物车")
	public ResponseMessage<?> update(@ApiParam(name="购物车对象")@RequestBody CartEntity cart) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<CartEntity>> failures = validator.validate(cart);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			cartService.saveOrUpdate(cart);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新购物车信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新购物车信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除购物车")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			cartService.deleteEntityById(CartEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("购物车删除失败");
		}

		return Result.success();
	}
}
