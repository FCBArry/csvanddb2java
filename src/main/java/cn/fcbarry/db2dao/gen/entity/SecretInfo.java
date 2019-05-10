package cn.fcbarry.db2dao.gen.entity;


import cn.fcbarry.db2dao.base.DataObject;
import cn.fcbarry.db2dao.base.DataOption;

public class SecretInfo extends DataObject 
{
	/**
	 * 用户id
	 */
	private long userID;
	
	/**
	 * 平台
	 */
	private int platform;
	
	/**
	 * 小区
	 */
	private int region;
	
	/**
	 * 每日通关最大层数
	 */
	private int dailyPass;
	
	/**
	 * 每日扫荡
	 */
	private int dailySweep;
	
	/**
	 * 周宝箱
	 */
	private int weekReward;
	
	/**
	 * 周宝箱领取状态
	 */
	private boolean hasGotWeekReward;
	
	/**
	 * 上周宝箱
	 */
	private int lastWeekReward;
	

	/**
	 * 用户id
	 */
	public long getUserID()
	{
		return userID;
	}

	/**
	 * 用户id
	 */
	public void setUserID(long userID)
	{
		if(userID != this.userID)
		{
			this.userID = userID;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 平台
	 */
	public int getPlatform()
	{
		return platform;
	}

	/**
	 * 平台
	 */
	public void setPlatform(int platform)
	{
		if(platform != this.platform)
		{
			this.platform = platform;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 小区
	 */
	public int getRegion()
	{
		return region;
	}

	/**
	 * 小区
	 */
	public void setRegion(int region)
	{
		if(region != this.region)
		{
			this.region = region;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 每日通关最大层数
	 */
	public int getDailyPass()
	{
		return dailyPass;
	}

	/**
	 * 每日通关最大层数
	 */
	public void setDailyPass(int dailyPass)
	{
		if(dailyPass != this.dailyPass)
		{
			this.dailyPass = dailyPass;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 每日扫荡
	 */
	public int getDailySweep()
	{
		return dailySweep;
	}

	/**
	 * 每日扫荡
	 */
	public void setDailySweep(int dailySweep)
	{
		if(dailySweep != this.dailySweep)
		{
			this.dailySweep = dailySweep;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 周宝箱
	 */
	public int getWeekReward()
	{
		return weekReward;
	}

	/**
	 * 周宝箱
	 */
	public void setWeekReward(int weekReward)
	{
		if(weekReward != this.weekReward)
		{
			this.weekReward = weekReward;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 周宝箱领取状态
	 */
	public boolean getHasGotWeekReward()
	{
		return hasGotWeekReward;
	}

	/**
	 * 周宝箱领取状态
	 */
	public void setHasGotWeekReward(boolean hasGotWeekReward)
	{
		if(hasGotWeekReward != this.hasGotWeekReward)
		{
			this.hasGotWeekReward = hasGotWeekReward;
			setOp(DataOption.UPDATE);
		}
	}
	/**
	 * 上周宝箱
	 */
	public int getLastWeekReward()
	{
		return lastWeekReward;
	}

	/**
	 * 上周宝箱
	 */
	public void setLastWeekReward(int lastWeekReward)
	{
		if(lastWeekReward != this.lastWeekReward)
		{
			this.lastWeekReward = lastWeekReward;
			setOp(DataOption.UPDATE);
		}
	}
}