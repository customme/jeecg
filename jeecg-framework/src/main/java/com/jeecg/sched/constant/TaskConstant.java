package com.jeecg.sched.constant;

import java.util.HashMap;
import java.util.Map;

public abstract class TaskConstant {

	// 默认组
	public static final String DEFAULT_GROUP = "default";

	// 最大任务并行数
	public static final int TASK_MAXIMUM = 5;

	// 任务优先级
	public static final int PRIORITY = 0;

	// 任务超时时间（秒）
	public static final int TIMEOUT = 1800;

	// 任务重做
	public static final int REDO_FLAG = 0;

	// 时间串行
	public static final int DATE_SERIAL_NO = 0;
	public static final int DATE_SERIAL_YES = 1;

	// 任务周期
	public static final String TASK_CYCLE_DAY = "day";
	public static final String TASK_CYCLE_WEEK = "week";
	public static final String TASK_CYCLE_MONTH = "month";
	public static final String TASK_CYCLE_HOUR = "hour";
	public static final String TASK_CYCLE_INTERVAL = "interval";
	public static final String TASK_CYCLE_INSTANT = "instant";

	// 任务状态
	public static final int TASK_STATUS_INVALID = 0;// 暂停
	public static final int TASK_STATUS_VALID = 1;// 正常
	public static final int TASK_STATUS_DELETE = -1;// 删除

	// 任务实例状态
	public static final int TASK_STATE_WAITING = 0;// 等待
	public static final int TASK_STATE_READY = 1;// 就绪
	public static final int TASK_STATE_ASSIGNED = 2;// 已经分配
	public static final int TASK_STATE_RUNNING = 3;// 正在运行
	public static final int TASK_STATE_SUCCESS = 6;// 运行成功
	public static final int TASK_STATE_KILLED = 8;// 被杀死
	public static final int TASK_STATE_FAILED = 9;// 运行失败

	public static final int WEEK_1 = 1;
	public static final int WEEK_2 = 2;
	public static final int WEEK_3 = 3;
	public static final int WEEK_4 = 4;
	public static final int WEEK_5 = 5;
	public static final int WEEK_6 = 6;
	public static final int WEEK_7 = 7;

	// 最大重试次数
	public static final int MAX_TRY_TIMES = 3;

	public static final Map<Integer, String> TASK_STATUS = new HashMap<Integer, String>();
	public static final Map<Integer, String> TASK_STATE = new HashMap<Integer, String>();
	public static final Map<String, String> TASK_CYCLE = new HashMap<String, String>();
	public static final Map<Integer, String> WEEK = new HashMap<Integer, String>();

	static {
		TASK_STATUS.put(TASK_STATUS_INVALID, "暂停");
		TASK_STATUS.put(TASK_STATUS_VALID, "正常");
		TASK_STATUS.put(TASK_STATUS_DELETE, "删除");

		TASK_STATE.put(TASK_STATE_WAITING, "等待");
		TASK_STATE.put(TASK_STATE_READY, "就绪");
		TASK_STATE.put(TASK_STATE_ASSIGNED, "已经分配");
		TASK_STATE.put(TASK_STATE_RUNNING, "正在运行");
		TASK_STATE.put(TASK_STATE_SUCCESS, "运行成功");
		TASK_STATE.put(TASK_STATE_FAILED, "运行失败");

		TASK_CYCLE.put(TASK_CYCLE_DAY, "天");
		TASK_CYCLE.put(TASK_CYCLE_WEEK, "周");
		TASK_CYCLE.put(TASK_CYCLE_MONTH, "月");
		TASK_CYCLE.put(TASK_CYCLE_INTERVAL, "时间间隔");

		WEEK.put(WEEK_1, "一");
		WEEK.put(WEEK_2, "二");
		WEEK.put(WEEK_3, "三");
		WEEK.put(WEEK_4, "四");
		WEEK.put(WEEK_5, "五");
		WEEK.put(WEEK_6, "六");
		WEEK.put(WEEK_7, "日");
	}

}
