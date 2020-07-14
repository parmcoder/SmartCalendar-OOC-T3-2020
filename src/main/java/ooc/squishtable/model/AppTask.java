package ooc.squishtable.model;

import java.sql.Timestamp;
import java.util.Date;

public class AppTask {

    String title;
    String description;
    Timestamp dateStart;
    Timestamp dateEnd;
    long uid;
    long tid;

    public AppTask() {

    }

    public AppTask(String title,
                   String description,
                   Timestamp dateStart,
                   Timestamp dateEnd,
                   long uid,
                   long tid) {
        this.tid = tid;
        this.uid = uid;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }
}
