package demo.igorzor.tdump.data.model.remote.quotelistresponse;

import com.google.gson.annotations.SerializedName;

public class AuthorItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("bio")
	private Object bio;

	@SerializedName("author_id")
	private String authorId;

	@SerializedName("slug")
	private String slug;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setBio(Object bio){
		this.bio = bio;
	}

	public Object getBio(){
		return bio;
	}

	public void setAuthorId(String authorId){
		this.authorId = authorId;
	}

	public String getAuthorId(){
		return authorId;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"AuthorItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",bio = '" + bio + '\'' + 
			",author_id = '" + authorId + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}