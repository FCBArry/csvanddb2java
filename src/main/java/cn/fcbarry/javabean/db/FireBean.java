package cn.fcbarry.javabean.db;

import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;

@PersistentEntity(sourceDomain = "t_s_fire", targetDomain = "", primaryKey = "id")
public class FireBean
{
	/**
	 * ID
	 */
 	@PersistentField
	private int id;

	/**
	 * 等级
	 */
 	@PersistentField
	private int level;

	/**
	 * 本级所需经验
	 */
 	@PersistentField
	private int exp;

	/**
	 * buffID可以填多个
	 */
 	@PersistentField
	private String buff;

	/**
	 * 对应火力等级提升的攻击力
	 */
 	@PersistentField
	private int magicAtk;

	/**
	 * 对应火力等级提升的生命值
	 */
 	@PersistentField
	private int magicHp;

	/**
	 * ID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * ID
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 等级
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * 等级
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	/**
	 * 本级所需经验
	 */
	public int getExp()
	{
		return exp;
	}

	/**
	 * 本级所需经验
	 */
	public void setExp(int exp)
	{
		this.exp = exp;
	}

	/**
	 * buffID可以填多个
	 */
	public String getBuff()
	{
		return buff;
	}

	/**
	 * buffID可以填多个
	 */
	public void setBuff(String buff)
	{
		this.buff = buff;
	}

	/**
	 * 对应火力等级提升的攻击力
	 */
	public int getMagicAtk()
	{
		return magicAtk;
	}

	/**
	 * 对应火力等级提升的攻击力
	 */
	public void setMagicAtk(int magicAtk)
	{
		this.magicAtk = magicAtk;
	}

	/**
	 * 对应火力等级提升的生命值
	 */
	public int getMagicHp()
	{
		return magicHp;
	}

	/**
	 * 对应火力等级提升的生命值
	 */
	public void setMagicHp(int magicHp)
	{
		this.magicHp = magicHp;
	}
}
