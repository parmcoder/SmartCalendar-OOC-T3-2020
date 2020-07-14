package ooc.squishtable.model;

import java.util.Date;

public class AppTask {
    String data;
    Date dateStart;
    Date dateEnd;
    String ownBy;

    public String getData() {
        return data;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getOwnBy() {
        return ownBy;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setOwnBy(String ownBy) {
        this.ownBy = ownBy;
    }
}
