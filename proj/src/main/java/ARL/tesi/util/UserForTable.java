package ARL.tesi.util;

import ARL.tesi.modelobject.User;

import javax.xml.crypto.Data;

public class UserForTable {

    private User u;
    private int totThisMonth;
    private int totPreviousMonth;

    public UserForTable(User u, int totThisMonth, int totPreviousMonth) {
        this.u = u;
        this.totThisMonth = totThisMonth;
        this.totPreviousMonth = totPreviousMonth;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public int getTotThisMonth() {
        return totThisMonth;
    }

    public void setTotThisMonth(int totThisMonth) {
        this.totThisMonth = totThisMonth;
    }

    public int getTotPreviousMonth() {
        return totPreviousMonth;
    }

    public void setTotPreviousMonth(int totPreviousMonth) {
        this.totPreviousMonth = totPreviousMonth;
    }
}
