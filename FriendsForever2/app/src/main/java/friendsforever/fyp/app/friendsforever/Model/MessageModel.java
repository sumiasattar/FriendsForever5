package friendsforever.fyp.app.friendsforever.Model;

public class MessageModel {
    private String sendBy;
    private String sendTo;
    private String textMessage;
    private String timeStamp;
    private String messagePhotoUrl;
    private String messageStatus;

    public MessageModel(String sendBy, String sendTo, String textMessage, String timeStamp,String messagePhotoUrl,String messageStatus) {
        this.sendBy = sendBy;
        this.sendTo = sendTo;
        this.textMessage = textMessage;
        this.timeStamp=timeStamp;
        this.messagePhotoUrl=messagePhotoUrl;
        this.messageStatus=messageStatus;
    }
    public MessageModel(){}
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getMessagePhotoUrl() {
        return messagePhotoUrl;
    }

    public void setMessagePhotoUrl(String messagePhotoUrl) {
        this.messagePhotoUrl = messagePhotoUrl;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
