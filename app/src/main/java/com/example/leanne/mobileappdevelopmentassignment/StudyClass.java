package com.example.leanne.mobileappdevelopmentassignment;

/**
 * Created by Leanne on 15. 12. 6..
 */
public class StudyClass {
    public int _id;
    public String region;
    public String pay;
    public String period;
    public String days;
    public String hours;
    public String nPeople;
    public String company;
    public String infoDetail;

    public StudyClass(int _id, String region, String pay, String period, String days,
                      String hours, String nPeople, String company, String infoDetail) {
        this._id = _id;
        this.region = region;
        this.pay = pay;
        this.period = period;
        this.days = days;
        this.hours = hours;
        this.nPeople = nPeople;
        this.company = company;
        this.infoDetail = infoDetail;
    }
}
