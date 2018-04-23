package demo.igorzor.tdump.data.model.remote.quotelistresponse;

import com.google.gson.annotations.SerializedName;

public class QuoteListResponse {

	@SerializedName("total")
	private int total;

	@SerializedName("_embedded")
	private EmbeddedFirst embeddedFirst;

	@SerializedName("count")
	private int count;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setEmbeddedFirst(EmbeddedFirst embeddedFirst){
		this.embeddedFirst = embeddedFirst;
	}

	public EmbeddedFirst getEmbeddedFirst(){
		return embeddedFirst;
	}

	@Override
	public String toString() {
		return "QuoteListResponse{" +
				"total=" + total +
				", embedded=" + embeddedFirst +
				", count=" + count +
				'}';
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

}