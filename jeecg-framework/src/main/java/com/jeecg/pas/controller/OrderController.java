package com.jeecg.pas.controller;
import com.jeecg.pas.entity.CartEntity;
import com.jeecg.pas.entity.CustomerEntity;
import com.jeecg.pas.entity.OrderEntity;
import com.jeecg.pas.service.OrderServiceI;
import com.jeecg.pas.utils.ArrayUtils;
import com.jeecg.pas.page.OrderPage;
import com.jeecg.pas.entity.OrderItemEntity;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller
 * @Description: 订单
 * @author onlineGenerator
 * @date 2018-07-19 11:48:34
 * @version V1.0   
 *
 */
@Api(value="Order",description="订单",tags="orderController")
@Controller
@RequestMapping("/orderController")
public class OrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderServiceI orderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 订单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request, @RequestParam(required = false) Integer customerId) {
		CustomerEntity customer = null;
		if(customerId != null) {
			customer = systemService.getEntity(CustomerEntity.class, customerId);
		}else {
			customer = new CustomerEntity();
			customer.setId(0);
		}
		return new ModelAndView("com/jeecg/pas/orderList").addObject("customer", customer);
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
	public void datagrid(OrderEntity order,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, order);
		try{
			//自定义追加查询条件
			String customerIds = request.getParameter("customerIds");
			if(StringUtils.isNotBlank(customerIds) && ! "0".equals(customerIds)) {
				cq.in("customer.id", ArrayUtils.str2Int(customerIds.split(",")));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.orderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除订单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OrderEntity order, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		order = systemService.getEntity(OrderEntity.class, order.getId());
		String message = "订单删除成功";
		try{
			orderService.delMain(order);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除订单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "订单删除成功";
		try{
			for(String id:ids.split(",")){
				OrderEntity order = systemService.getEntity(OrderEntity.class,
				Integer.parseInt(id)
				);
				orderService.delMain(order);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加订单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OrderEntity order,OrderPage orderPage, HttpServletRequest request) {
		List<OrderItemEntity> orderItemList =  orderPage.getOrderItemList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			orderService.addMain(order.setDefault(), orderItemList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新订单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OrderEntity order,OrderPage orderPage, HttpServletRequest request) {
		List<OrderItemEntity> orderItemList =  orderPage.getOrderItemList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			orderService.updateMain(order, orderItemList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 订单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(@RequestParam Integer customerId, @RequestParam String cartIds) {
		CustomerEntity customer = systemService.getEntity(CustomerEntity.class, customerId);
		OrderEntity order = new OrderEntity();
		order.setCustomer(customer);
		order.setReceiver(customer.getRealname());
		order.setMobile(customer.getMobile());
		order.setRegion(customer.getRegion());
		order.setAddress(customer.getAddress());
		
		return new ModelAndView("com/jeecg/pas/order-add").addObject("orderPage", order).addObject("cartIds", cartIds);
	}
	
	/**
	 * 订单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OrderEntity order, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(order.getId())) {
			order = orderService.getEntity(OrderEntity.class, order.getId());
			req.setAttribute("orderPage", order);
		}
		return new ModelAndView("com/jeecg/pas/order-update");
	}
	
	
	/**
	 * 加载明细列表[订单明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "orderItemList")
	public ModelAndView orderItemList(@RequestParam(required = false) Integer orderId, @RequestParam(required = false) String cartIds) {
		List<OrderItemEntity> orderItemList = new ArrayList<OrderItemEntity>();
		try {
			if (orderId != null) {
				String hql0 = "from OrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
				orderItemList = systemService.findHql(hql0, orderId);
			} else if (StringUtils.isNotBlank(cartIds)) {
				CriteriaQuery cq = new CriteriaQuery(CartEntity.class);
				cq.in("id", ArrayUtils.str2Int(cartIds.split(",")));
				cq.add();
				List<CartEntity> cartList = systemService.getListByCriteriaQuery(cq, false);
				for (CartEntity cart : cartList) {
					OrderItemEntity orderItem = new OrderItemEntity();
					orderItem.setGoodsId(cart.getGoods().getId());
					orderItem.setGoodsName(cart.getGoods().getGoodsName());
					orderItem.setGoodsSpecs(cart.getGoods().getGoodsSpecs());
					orderItem.setGoodsImg(cart.getGoods().getGoodsImg());
					orderItem.setQuantity(cart.getQuantity());
					orderItem.setPrice(cart.getGoods().getPrice());
					orderItemList.add(orderItem);
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jeecg/pas/orderItemList").addObject("orderItemList", orderItemList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(OrderEntity order,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(OrderEntity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, order);
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<OrderEntity> list=this.orderService.getListByCriteriaQuery(cq, false);
    	List<OrderPage> pageList=new ArrayList<OrderPage>();
        if(list!=null&&list.size()>0){
        	for(OrderEntity entity:list){
        		try{
        		OrderPage page=new OrderPage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    Object id0 = entity.getId();
				    String hql0 = "from OrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
        	        List<OrderItemEntity> orderItemEntityList = systemService.findHql(hql0,id0);
            		page.setOrderItemList(orderItemEntityList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"订单");
        map.put(NormalExcelConstants.CLASS,OrderPage.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("订单列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

    /**
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
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
			params.setHeadRows(2);
			params.setNeedSave(true);
			try {
				List<OrderPage> list =  ExcelImportUtil.importExcel(file.getInputStream(), OrderPage.class, params);
				OrderEntity entity1=null;
				for (OrderPage page : list) {
					entity1=new OrderEntity();
					MyBeanUtils.copyBeanNotNull2Bean(page,entity1);
		            orderService.addMain(entity1, page.getOrderItemList());
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
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"订单");
		map.put(NormalExcelConstants.CLASS,OrderPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("订单列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	* 导入功能跳转
	*
	* @return
	*/
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "orderController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

 	
 	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="订单列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<OrderPage>> list() {
		List<OrderEntity> list= orderService.getList(OrderEntity.class);
    	List<OrderPage> pageList=new ArrayList<OrderPage>();
        if(list!=null&&list.size()>0){
        	for(OrderEntity entity:list){
        		try{
        			OrderPage page=new OrderPage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
					Object id0 = entity.getId();
				     String hql0 = "from OrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    			List<OrderItemEntity> orderItemOldList = this.orderService.findHql(hql0,id0);
            		page.setOrderItemList(orderItemOldList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
		return Result.success(pageList);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取订单信息",notes="根据ID获取订单信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		OrderEntity task = orderService.get(OrderEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取订单信息为空");
		}
		OrderPage page = new OrderPage();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(task, page);
				Object id0 = task.getId();
		    String hql0 = "from OrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
			List<OrderItemEntity> orderItemOldList = this.orderService.findHql(hql0,id0);
    		page.setOrderItemList(orderItemOldList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.success(page);
	}
 	
 	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建订单")
	public ResponseMessage<?> create(@ApiParam(name="订单对象")@RequestBody OrderPage orderPage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<OrderPage>> failures = validator.validate(orderPage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		List<OrderItemEntity> orderItemList =  orderPage.getOrderItemList();
		
		OrderEntity order = new OrderEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(orderPage,order);
		}catch(Exception e){
            logger.info(e.getMessage());
            return Result.error("保存订单失败");
        }
		orderService.addMain(order, orderItemList);

		return Result.success(order);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新订单",notes="更新订单")
	public ResponseMessage<?> update(@RequestBody OrderPage orderPage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<OrderPage>> failures = validator.validate(orderPage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		List<OrderItemEntity> orderItemList =  orderPage.getOrderItemList();
		
		OrderEntity order = new OrderEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(orderPage,order);
		}catch(Exception e){
            logger.info(e.getMessage());
            return Result.error("订单更新失败");
        }
		orderService.updateMain(order, orderItemList);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除订单")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			OrderEntity order = orderService.get(OrderEntity.class, id);
			orderService.delMain(order);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("订单删除失败");
		}

		return Result.success();
	}
}
