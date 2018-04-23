package demo.igorzor.tdump.data.model.remote.quotelistresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmbeddedSecond {

    @SerializedName("author")
    private List<AuthorItem> author;

    @SerializedName("source")
    private List<SourceItem> source;

    public void setAuthor(List<AuthorItem> author){
        this.author = author;
    }

    public List<AuthorItem> getAuthor(){
        return author;
    }

    public void setSource(List<SourceItem> source){
        this.source = source;
    }

    public List<SourceItem> getSource(){
        return source;
    }

    @Override
    public String toString() {
        return "EmbeddedSecond{" +
                "author=" + author +
                ", source=" + source +
                '}';
    }
}
