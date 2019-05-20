package cn.fcbarry.javabean.csv;

import java.util.List;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvBindAndSplitByName;
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

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int getExp()
	{
		return exp;
	}

	public void setExp(int exp)
	{
		this.exp = exp;
	}

	public String getBuff()
	{
		return buff;
	}

	public void setBuff(String buff)
	{
		this.buff = buff;
	}

	public int getMagicAtk()
	{
		return magicAtk;
	}

	public void setMagicAtk(int magicAtk)
	{
		this.magicAtk = magicAtk;
	}

	public int getMagicHp()
	{
		return magicHp;
	}

	public void setMagicHp(int magicHp)
	{
		this.magicHp = magicHp;
	}
}
