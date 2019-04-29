package javabean;

import com.opencsv.bean.CsvBindByName;

public class FireBean
{
	/**
	 * ID
	 */
 	@CsvBindByName
	private int id;

	/**
	 * 等级
	 */
 	@CsvBindByName
	private int level;

	/**
	 * 本级所需经验
	 */
 	@CsvBindByName
	private int exp;

	/**
	 * buffID可以填多个
	 */
 	@CsvBindByName
	private String buff;

	/**
	 * 对应火力等级提升的攻击力
	 */
 	@CsvBindByName
	private int magicAtk;

	/**
	 * 对应火力等级提升的生命值
	 */
 	@CsvBindByName
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
