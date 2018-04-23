package demo.igorzor.tdump.data.model.remote.taglistresponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TagListResponse {

	@SerializedName("total")
	private int total;

	@SerializedName("_embedded")
	private List<String> tagList;


	@SerializedName("count")
	private int count;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setTagList(List<String> tagList){
		this.tagList = tagList;
	}

	public List<String> getTagList(){
		return tagList;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"total = '" + total + '\'' + 
			",_embedded = '" + tagList + '\'' +
			",count = '" + count + '\'' +
			"}";
		}
}