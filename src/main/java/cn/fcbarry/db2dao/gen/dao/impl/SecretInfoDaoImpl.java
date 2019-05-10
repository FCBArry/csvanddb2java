package cn.fcbarry.db2dao.gen.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import cn.fcbarry.db2dao.base.BaseDao;
import cn.fcbarry.db2dao.base.DBParamWrapper;
import cn.fcbarry.db2dao.base.DataExecutor;
import cn.fcbarry.db2dao.base.DBHelper;

import cn.fcbarry.db2dao.base.DataOption;
import cn.fcbarry.db2dao.gen.dao.ISecretInfoDao;
import cn.fcbarry.db2dao.gen.entity.SecretInfo;

public class SecretInfoDaoImpl extends BaseDao<SecretInfo> implements ISecretInfoDao
{
	public SecretInfoDaoImpl(DBHelper helper)
	{
		super(helper);
	}


	@Override
	public boolean add(SecretInfo secretInfo)
	{
		boolean result = false;
		String sql = "insert into t_u_secret(`UserID`, `Platform`, `Region`, `DailyPass`, `DailySweep`, `WeekReward`, `HasGotWeekReward`, `LastWeekReward`) values(?, ?, ?, ?, ?, ?, ?, ?);";
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.BIGINT, secretInfo.getUserID());
		params.put(Types.SMALLINT, secretInfo.getPlatform());
		params.put(Types.SMALLINT, secretInfo.getRegion());
		params.put(Types.INTEGER, secretInfo.getDailyPass());
		params.put(Types.SMALLINT, secretInfo.getDailySweep());
		params.put(Types.INTEGER, secretInfo.getWeekReward());
		params.put(Types.TINYINT, secretInfo.getHasGotWeekReward());
		params.put(Types.INTEGER, secretInfo.getLastWeekReward());
			
		result = getDbHelper().execNoneQuery(sql, params) > -1 ? true : false;
		if (result) 
			secretInfo.setOp(DataOption.NONE);
			
		return result;
	}

	@Override
	public boolean update(SecretInfo secretInfo)
	{
		if(secretInfo.getOp() == DataOption.NONE)
			return true;

		boolean result = false;
		String sql = "update t_u_secret set `Platform`=?, `Region`=?, `DailyPass`=?, `DailySweep`=?, `WeekReward`=?, `HasGotWeekReward`=?, `LastWeekReward`=? where `UserID`=?;";
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.SMALLINT, secretInfo.getPlatform());
		params.put(Types.SMALLINT, secretInfo.getRegion());
		params.put(Types.INTEGER, secretInfo.getDailyPass());
		params.put(Types.SMALLINT, secretInfo.getDailySweep());
		params.put(Types.INTEGER, secretInfo.getWeekReward());
		params.put(Types.TINYINT, secretInfo.getHasGotWeekReward());
		params.put(Types.INTEGER, secretInfo.getLastWeekReward());
		params.put(Types.BIGINT, secretInfo.getUserID());
	
		result = getDbHelper().execNoneQuery(sql, params) > -1 ? true : false;
		if (result) 
			secretInfo.setOp(DataOption.NONE);
			
		return result;
	}

	@Override
	public boolean delete(SecretInfo secretInfo)
	{
		boolean result = false;
		String sql = "delete from t_u_secret where `UserID`=?;";
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.BIGINT, secretInfo.getUserID());
		result = getDbHelper().execNoneQuery(sql, params) > -1 ? true : false;
		return result;
	}

	@Override
	public boolean addOrUpdate(SecretInfo secretInfo)
	{
		boolean result = false;
		String sql = "insert into t_u_secret(`UserID`, `Platform`, `Region`, `DailyPass`, `DailySweep`, `WeekReward`, `HasGotWeekReward`, `LastWeekReward`) values(?, ?, ?, ?, ?, ?, ?, ?) on DUPLICATE KEY update `Platform`=?,`Region`=?,`DailyPass`=?,`DailySweep`=?,`WeekReward`=?,`HasGotWeekReward`=?,`LastWeekReward`=?;";
		if(secretInfo.getOp() == DataOption.NONE)
			return true;
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.BIGINT, secretInfo.getUserID());
		params.put(Types.SMALLINT, secretInfo.getPlatform());
		params.put(Types.SMALLINT, secretInfo.getRegion());
		params.put(Types.INTEGER, secretInfo.getDailyPass());
		params.put(Types.SMALLINT, secretInfo.getDailySweep());
		params.put(Types.INTEGER, secretInfo.getWeekReward());
		params.put(Types.TINYINT, secretInfo.getHasGotWeekReward());
		params.put(Types.INTEGER, secretInfo.getLastWeekReward());
		params.put(Types.SMALLINT, secretInfo.getPlatform());
		params.put(Types.SMALLINT, secretInfo.getRegion());
		params.put(Types.INTEGER, secretInfo.getDailyPass());
		params.put(Types.SMALLINT, secretInfo.getDailySweep());
		params.put(Types.INTEGER, secretInfo.getWeekReward());
		params.put(Types.TINYINT, secretInfo.getHasGotWeekReward());
		params.put(Types.INTEGER, secretInfo.getLastWeekReward());
		result = getDbHelper().execNoneQuery(sql, params) > -1 ? true : false;
		if (result) 
			secretInfo.setOp(DataOption.NONE);
			
		return result;
	}

	@Override
	public boolean deleteByKey(Object... ids)
	{
		boolean result = false;
		String sql = "delete from t_u_secret where `UserID`=?;";
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.BIGINT, ids[0]);
		result = getDbHelper().execNoneQuery(sql, params) > -1 ? true : false;
		return result;
	}

	@Override
	public SecretInfo getByKey(Object... ids)
	{
		String sql = "select * from t_u_secret where `UserID`=?;";
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.BIGINT, ids[0]);
		SecretInfo secretInfo = query(sql, params);		return secretInfo;
	}


	@Override
	public List<SecretInfo> listAll()
	{
		String sql = "select * from t_u_secret;";
		List<SecretInfo> secretInfos = queryList(sql);		return secretInfos;
	}

	@Override
	public int[] addOrUpdateBatch(List<SecretInfo> secretInfos)
	{
		String sql = "insert into t_u_secret(`UserID`, `Platform`, `Region`, `DailyPass`, `DailySweep`, `WeekReward`, `HasGotWeekReward`, `LastWeekReward`) values(?, ?, ?, ?, ?, ?, ?, ?) on DUPLICATE KEY update `Platform`=?,`Region`=?,`DailyPass`=?,`DailySweep`=?,`WeekReward`=?,`HasGotWeekReward`=?,`LastWeekReward`=?;";
		int[] effectedRows = getDbHelper().sqlBatch(sql, secretInfos, new DataExecutor<int[]>()
			{
				@Override
				public int[] execute(PreparedStatement statement, Object... objects) throws Exception
				{
					@SuppressWarnings("unchecked")
					List<SecretInfo> secretInfos = (List<SecretInfo>)objects[0];
					for (SecretInfo secretInfo : secretInfos)
					{
						if(secretInfo.getOp() == DataOption.NONE)
							continue;
						DBParamWrapper params = new DBParamWrapper();
						params.put(Types.BIGINT, secretInfo.getUserID());
						params.put(Types.SMALLINT, secretInfo.getPlatform());
						params.put(Types.SMALLINT, secretInfo.getRegion());
						params.put(Types.INTEGER, secretInfo.getDailyPass());
						params.put(Types.SMALLINT, secretInfo.getDailySweep());
						params.put(Types.INTEGER, secretInfo.getWeekReward());
						params.put(Types.TINYINT, secretInfo.getHasGotWeekReward());
						params.put(Types.INTEGER, secretInfo.getLastWeekReward());
						params.put(Types.SMALLINT, secretInfo.getPlatform());
						params.put(Types.SMALLINT, secretInfo.getRegion());
						params.put(Types.INTEGER, secretInfo.getDailyPass());
						params.put(Types.SMALLINT, secretInfo.getDailySweep());
						params.put(Types.INTEGER, secretInfo.getWeekReward());
						params.put(Types.TINYINT, secretInfo.getHasGotWeekReward());
						params.put(Types.INTEGER, secretInfo.getLastWeekReward());
		
						statement = getDbHelper().prepareCommand(statement, params);
						statement.addBatch();
					}
					return statement.executeBatch();
				}
			});
			for(int i=0; i<effectedRows.length; ++i)
			{
				if (effectedRows[i] > -1)
					secretInfos.get(i).setOp(DataOption.NONE);
			}
		return effectedRows;
	}

	@Override
	public int[] deleteBatch(List<SecretInfo> secretInfos)
	{
		String sql = "delete from t_u_secret where `UserID`=?;";
		int[] effectedRows = getDbHelper().sqlBatch(sql, secretInfos, new DataExecutor<int[]>()
		{
			@Override
			public int[] execute(PreparedStatement statement, Object... objects) throws Exception
			{
				@SuppressWarnings("unchecked")
				List<SecretInfo>secretInfos = (List<SecretInfo>)objects[0];
				for (SecretInfo secretInfo : secretInfos)
				{
					DBParamWrapper params = new DBParamWrapper();
					params.put(Types.BIGINT, secretInfo.getUserID());
					statement = getDbHelper().prepareCommand(statement, params);
					statement.addBatch();
				}
				return statement.executeBatch();
			}
		});
		return effectedRows;
	}
	
	@Override
	public SecretInfo rsToEntity(ResultSet rs) throws Exception
	{
		SecretInfo secretInfo = null;
		if (pool != null)
			secretInfo = pool.borrowObject();
		else
			secretInfo = new SecretInfo();

		secretInfo.setUserID(rs.getLong("UserID"));
		secretInfo.setPlatform(rs.getInt("Platform"));
		secretInfo.setRegion(rs.getInt("Region"));
		secretInfo.setDailyPass(rs.getInt("DailyPass"));
		secretInfo.setDailySweep(rs.getInt("DailySweep"));
		secretInfo.setWeekReward(rs.getInt("WeekReward"));
		secretInfo.setHasGotWeekReward(rs.getBoolean("HasGotWeekReward"));
		secretInfo.setLastWeekReward(rs.getInt("LastWeekReward"));
		
		secretInfo.setOp(DataOption.NONE);
		return secretInfo;
	}

	@Override
	public List<SecretInfo> getSecretInfoByUserID(long userID)
	{
		String sql = "select * from t_u_secret where `userID` = ?;";
		DBParamWrapper params = new DBParamWrapper();
		params.put(Types.BIGINT, userID);
		List<SecretInfo> secretInfo = queryList(sql,params);
		return secretInfo;
	}
}