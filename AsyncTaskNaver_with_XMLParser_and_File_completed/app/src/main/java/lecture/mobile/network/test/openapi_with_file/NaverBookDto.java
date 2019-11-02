package lecture.mobile.network.test.openapi_with_file;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by cooling
 */

public class NaverBookDto {

    private int _id;
    private String title;
    private String author;
    private String link;
    private String image;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
//        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NaverBookDto{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
