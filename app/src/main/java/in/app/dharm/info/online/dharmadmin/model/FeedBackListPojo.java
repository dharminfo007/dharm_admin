package in.app.dharm.info.online.dharmadmin.model;

public class FeedBackListPojo {
    String feedBack, feedBackTime, getFeedBackUser;

    public FeedBackListPojo(String feedBack, String feedBackTime, String getFeedBackUser) {
        this.feedBack = feedBack;
        this.feedBackTime = feedBackTime;
        this.getFeedBackUser = getFeedBackUser;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getFeedBackTime() {
        return feedBackTime;
    }

    public void setFeedBackTime(String feedBackTime) {
        this.feedBackTime = feedBackTime;
    }

    public String getGetFeedBackUser() {
        return getFeedBackUser;
    }

    public void setGetFeedBackUser(String getFeedBackUser) {
        this.getFeedBackUser = getFeedBackUser;
    }
}
