package demo.igorzor.tdump.data.model;

import java.util.List;

import demo.igorzor.tdump.data.model.remote.quotelistresponse.QuoteListResponse;
import demo.igorzor.tdump.data.model.remote.quotelistresponse.SourceItem;
import demo.igorzor.tdump.data.model.remote.quotelistresponse.TagsItem;

public class Quote {

    private String mQuoteValue;
    private String mQuoteUrl;
    private String mDateAppearedAt;
    private List<String> mTagList;

    public Quote(String mQuoteValue, String mQuoteUrl, String mDateAppearedAt, List<String> mTagList) {
        this.mQuoteValue = mQuoteValue;
        this.mQuoteUrl = mQuoteUrl;
        this.mDateAppearedAt = mDateAppearedAt;
        this.mTagList = mTagList;
    }

    public String getmQuoteValue() {
        return mQuoteValue;
    }

    public String getmQuoteUrl() {
        return mQuoteUrl;
    }

    public String getmDateAppearedAt() {
        return mDateAppearedAt;
    }

    public List<String> getmTagList() {
        return mTagList;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "mQuoteValue='" + mQuoteValue + '\'' +
                ", mQuoteUrl='" + mQuoteUrl + '\'' +
                ", mDateAppearedAt='" + mDateAppearedAt + '\'' +
                ", mTagList=" + mTagList +
                '}';
    }

    public static Quote create(TagsItem tagsItem) {
       return new Quote(
               tagsItem.getValue(),
               tagsItem.getEmbeddedSecond().getSource().get(0) != null? tagsItem.getEmbeddedSecond().getSource().get(0).getUrl():"", //tags "source" and "author" always contain jsonarray with one entry
               tagsItem.getAppearedAt(),
               tagsItem.getTags()
       );
    }
}
