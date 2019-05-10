package cn.fcbarry.db2dao.gen.dao;

import cn.fcbarry.db2dao.base.IBaseDao;
import java.util.List;

import cn.fcbarry.db2dao.gen.entity.SecretInfo;

public interface ISecretInfoDao extends IBaseDao<SecretInfo>
{
	List<SecretInfo> getSecretInfoByUserID(long userID);
}
