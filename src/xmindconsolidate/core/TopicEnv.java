package xmindconsolidate.core;

import org.xmind.core.ITopic;

public class TopicEnv
{

	ITopic topic;
	String id;
	int level;
	
	/**
	 * Constructor
	 * @param _topic
	 * @param _id
	 * @param _level
	 */
	public TopicEnv(ITopic _topic,String _id,	int _level)
	{
		this.topic = _topic;
		this.id = _id;
		this.level = _level;
		
	}

	public ITopic getTopic()
	{
		return topic;
	}

	public void setTopic(ITopic topic)
	{
		this.topic = topic;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

}
