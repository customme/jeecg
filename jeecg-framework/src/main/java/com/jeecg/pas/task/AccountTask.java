package com.jeecg.pas.task;

import java.util.List;
import java.util.Map;

import org.jeecgframework.web.system.service.SystemService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 收入支出统计
 * 
 * @author Administrator
 * @date 2018-08-22 10:22:09
 *
 */
public class AccountTask implements Job {

	private static final int TAR_CUR = 0;// 结算币种
	private static final int RATE_DAY = 15;// 取最近多少天的汇率数据

	@Autowired
	private SystemService systemService;

	@Override
	public void execute(JobExecutionContext arg) throws JobExecutionException {
		// 商品成本
		String filter1 = " WHERE create_date >= CURDATE() OR update_date >= CURDATE()";
		String sql1 = "SELECT create_by, DATE(buy_time) buy_date, SUM(actual_amount) amount FROM ps_receipt" + filter1
				+ " GROUP BY create_by, buy_date HAVING SUM(actual_amount) > 0";
		List<Map<String, Object>> list1 = systemService.findForJdbc(sql1);
		String insert1 = "INSERT INTO ps_account(create_by, biz_date, goods_cost) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE goods_cost = ?";
		for (Map<String, Object> map : list1) {
			systemService.executeSql(insert1, map.get("create_by"), map.get("buy_date"), map.get("amount"), map.get("amount"));
		}

		// 订单收入
		String filter2 = " WHERE create_date >= CURDATE() OR update_date >= CURDATE()";
		String sql2 = "SELECT create_by, DATE(create_date) order_date, SUM(price + freight - discount_amount) amount, SUM(paid) income FROM ps_order"
				+ filter2 + " GROUP BY create_by, order_date HAVING amount > 0";
		List<Map<String, Object>> list2 = systemService.findForJdbc(sql2);
		String insert2 = "INSERT INTO ps_account(create_by, biz_date, receivable, received) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE receivable = ?, received = ?";
		for (Map<String, Object> map : list2) {
			systemService.executeSql(insert2, map.get("create_by"), map.get("order_date"), map.get("amount"), map.get("income"), map.get("amount"), map.get("income"));
		}

		// 其他成本
		String filter3 = " AND (a.create_date >= CURDATE() OR a.update_date >= CURDATE())";
		String sql3 = "SELECT a.create_by, a.cost_date, SUM(IF(a.currency = ?, a.cost, a.cost * b.max_rate)) expense FROM ps_cost a INNER JOIN (SELECT DATE(update_time) update_date, src_cur, MAX(rate) max_rate FROM ps_rate WHERE tar_cur = ? AND update_time >= CURDATE() - INTERVAL ? DAY GROUP BY DATE(update_time), src_cur) b ON a.cost_date = b.update_date"
				+ filter3 + " GROUP BY a.create_by, a.cost_date;";
		List<Map<String, Object>> list3 = systemService.findForJdbc(sql3, TAR_CUR, TAR_CUR, RATE_DAY);
		String insert3 = "INSERT INTO ps_account(create_by, biz_date, expense) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE expense = ?";
		for (Map<String, Object> map : list3) {
			systemService.executeSql(insert3, map.get("create_by"), map.get("cost_date"), map.get("expense"), map.get("expense"));
		}
	}

}
