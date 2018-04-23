package demo.igorzor.tdump.data.model.remote.quotelistresponse;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmbeddedFirst {

	@SerializedName("tags")
	private List<TagsItem> tags;



    public List<TagsItem> getTags() {
        return tags;
    }

    public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }




    @Override
    public String toString() {
        return "Embedded{" +
                "tags=" + tags +
                '}';
    }
}