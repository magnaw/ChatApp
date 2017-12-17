package chatapp.magnus.chatapp.posts;

/**
 * Created by Magnus on 16-12-2017.
 */

public class ContentItem {
    public String id;
    public String content;
    public String title;
    public int votes;

    public ContentItem() {
    }

    public ContentItem(String id, String content, String title, int votes) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "ContentItem{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", votes=" + votes +
                '}';
    }
}