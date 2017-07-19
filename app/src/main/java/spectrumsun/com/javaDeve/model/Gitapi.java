package spectrumsun.com.javaDeve.model;

/**
 * Created by Spectrum Sun on 07/16/2017.
 */

public class Gitapi {
    private String title, thumbnailUrl, userUrl, score;

    public Gitapi() {

    }

    public Gitapi(String name, String thumbnailUrl, String userUrl, String score) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.userUrl = userUrl;
        this.score = score;

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

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getScore(){
        return score;
    }

    public void setScore(String score){
        this.score = score;
    }





}
