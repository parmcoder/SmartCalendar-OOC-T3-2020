package ooc.squishtable.main.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APP_TASKS")
public class AppTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long tid;
    long uid;
    String title;
    String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateEnd;

    String inputDateStart;
    String inputDateEnd;

    public AppTask() {

    }

    public AppTask(String title,
                   String description,
                   Date dateStart,
                   Date dateEnd,
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

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getInputDateStart() {
        return inputDateStart;
    }

    public void setInputDateStart(String inputDateStart) {
        this.inputDateStart = inputDateStart;
    }

    public String getInputDateEnd() {
        return inputDateEnd;
    }

    public void setInputDateEnd(String inputDateEnd) {
        this.inputDateEnd = inputDateEnd;
    }

}
