package friendsforever.fyp.app.friendsforever.Model;

/**
 * Created by usama on 5/16/2019.
 */

public class Users {
    private String userUID;
    private String username;
    private String email;
    private String pwd;
    private String phone;
    private String adress;
    private String imgurl;

    public Users() {
    }

    public Users(String userUID,String username, String email, String pwd, String phone, String adress, String imgurl) {
        this.userUID=userUID;
        this.username = username;
        this.email = email;
        this.pwd = pwd;
        this.phone = phone;
        this.adress = adress;
        this.imgurl = imgurl;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
