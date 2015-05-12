package pro.likeit.recipes;

import java.util.ArrayList;
/**
 * Created by admin on 5/12/15.
 */
public class RecipeCategory {

    private String title, thumbnailUrl;

    public RecipeCategory() {
    }

    public RecipeCategory(String name, String thumbnailUrl) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}