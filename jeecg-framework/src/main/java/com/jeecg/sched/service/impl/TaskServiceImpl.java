package com.jeecg.sched.service.impl;
import com.jeecg.sched.service.TaskServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.jeecg.sched.entity.TaskEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("taskService")
@Transactional
public class TaskServiceImpl extends CommonServiceImpl implements TaskServiceI {

	
 	public void delete(TaskEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TaskEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TaskEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TaskEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TaskEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TaskEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TaskEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
		map.put("name", t.getName());
		map.put("task_group", t.getTaskGroup());
		map.put("type_id", t.getTypeId());
		map.put("task_status", t.getTaskStatus());
		map.put("task_cycle", t.getTaskCycle());
		map.put("cycle_value", t.getCycleValue());
		map.put("priority", t.getPriority());
		map.put("max_try_times", t.getMaxTryTimes());
		map.put("timeout", t.getTimeout());
		map.put("cluster_id", t.getClusterId());
		map.put("server_id", t.getServerId());
		map.put("start_time", t.getStartTime());
		map.put("end_time", t.getEndTime());
		map.put("first_time", t.getFirstTime());
		map.put("description", t.getDescription());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TaskEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{task_group}",String.valueOf(t.getTaskGroup()));
 		sql  = sql.replace("#{type_id}",String.valueOf(t.getTypeId()));
 		sql  = sql.replace("#{task_status}",String.valueOf(t.getTaskStatus()));
 		sql  = sql.replace("#{task_cycle}",String.valueOf(t.getTaskCycle()));
 		sql  = sql.replace("#{cycle_value}",String.valueOf(t.getCycleValue()));
 		sql  = sql.replace("#{priority}",String.valueOf(t.getPriority()));
 		sql  = sql.replace("#{max_try_times}",String.valueOf(t.getMaxTryTimes()));
 		sql  = sql.replace("#{timeout}",String.valueOf(t.getTimeout()));
 		sql  = sql.replace("#{cluster_id}",String.valueOf(t.getClusterId()));
 		sql  = sql.replace("#{server_id}",String.valueOf(t.getServerId()));
 		sql  = sql.replace("#{start_time}",String.valueOf(t.getStartTime()));
 		sql  = sql.replace("#{end_time}",String.valueOf(t.getEndTime()));
 		sql  = sql.replace("#{first_time}",String.valueOf(t.getFirstTime()));
 		sql  = sql.replace("#{description}",String.valueOf(t.getDescription()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("t_task",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}