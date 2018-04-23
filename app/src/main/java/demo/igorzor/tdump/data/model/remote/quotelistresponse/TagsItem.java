package demo.igorzor.tdump.data.model.remote.quotelistresponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TagsItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("_embedded")
	private EmbeddedSecond embeddedSecond;

	@SerializedName("quote_id")
	private String quoteId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("appeared_at")
	private String appearedAt;

	@SerializedName("value")
	private String value;

	@SerializedName("tags")
	private List<String> tags;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setEmbeddedSecond(EmbeddedSecond embeddedSecond){
		this.embeddedSecond = embeddedSecond;
	}

	public EmbeddedSecond getEmbeddedSecond(){
		return embeddedSecond;
	}

	public void setQuoteId(String quoteId){
		this.quoteId = quoteId;
	}

	public String getQuoteId(){
		return quoteId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setAppearedAt(String appearedAt){
		this.appearedAt = appearedAt;
	}

	public String getAppearedAt(){
		return appearedAt;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	public void setTags(List<String> tags){
		this.tags = tags;
	}

	public List<String> getTags(){
		return tags;
	}

	@Override
 	public String toString(){
		return 
			"TagsItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",_embedded = '" + embeddedSecond + '\'' +
			",quote_id = '" + quoteId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",appeared_at = '" + appearedAt + '\'' + 
			",value = '" + value + '\'' + 
			",tags = '" + tags + '\'' + 
			"}";
		}
}