package chatapp.magnus.chatapp.posts;

/**
 * Created by Magnus on 16-12-2017.
 */

public class UserDTO {
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    private String avatar;
    private String mail;
    private String name;
    private int upvotes;

    public UserDTO() {

    }

    public UserDTO(String avatar, String mail, String name, int upvotes) {
        this.avatar = avatar;
        this.mail = mail;
        this.name = name;
        this.upvotes = upvotes;
    }



}
