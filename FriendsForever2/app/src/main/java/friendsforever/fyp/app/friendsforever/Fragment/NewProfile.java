package friendsforever.fyp.app.friendsforever.Fragment;

public class NewProfile {
    private static String Name;
    private static String Password;
    private static String Email;
    private static String Phone;
    private static String Adress;
    public NewProfile() {
    }

    public NewProfile(String name, String email, String password, String phone, String adress) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Adress = adress;
    }

    public static String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public static String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public static String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public static String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public static String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }
}
