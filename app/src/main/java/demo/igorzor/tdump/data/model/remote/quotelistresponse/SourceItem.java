package demo.igorzor.tdump.data.model.remote.quotelistresponse;

import com.google.gson.annotations.SerializedName;

public class SourceItem{

	@SerializedName("filename")
	private Object filename;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("quote_source_id")
	private String quoteSourceId;

	@SerializedName("remarks")
	private Object remarks;

	@SerializedName("url")
	private String url;

	public void setFilename(Object filename){
		this.filename = filename;
	}

	public Object getFilename(){
		return filename;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setQuoteSourceId(String quoteSourceId){
		this.quoteSourceId = quoteSourceId;
	}

	public String getQuoteSourceId(){
		return quoteSourceId;
	}

	public void setRemarks(Object remarks){
		this.remarks = remarks;
	}

	public Object getRemarks(){
		return remarks;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"SourceItem{" + 
			"filename = '" + filename + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",quote_source_id = '" + quoteSourceId + '\'' + 
			",remarks = '" + remarks + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}