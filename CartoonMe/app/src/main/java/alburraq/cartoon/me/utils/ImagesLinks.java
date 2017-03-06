package alburraq.cartoon.me.utils;

public class ImagesLinks {
	private int id;
	private String link;
	public ImagesLinks()
	{
		
	}
	public ImagesLinks(int id, String link)
	{
		this.id = id;
		this.link = link;
	}
	public ImagesLinks(String link)
	{
		this.link = link;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getLink()
	{
		return this.link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
}
