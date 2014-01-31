package com.example.extraexam1;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 31.01.14
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class Buss implements Serializable {
    private String start;
    private String finish;
    private String cycle;

    public Buss() {}


    public String getBussTime() {
        return start;
    }

    public String getStartTime() {
        return start;
    }

    public void setStartTime(String carMark) {
        this.start = carMark;
    }

    public String getFinishTime() {
        return finish;
    }

    public void setFinishTime(String finish) {
        this.finish = finish;
    }

    public String getTime() {
        return cycle;
    }

    public void setTime(String cycle) {
        this.cycle = cycle;
    }

    public Buss(String start, String finish, String cycle) {

        this.start = start;
        this.finish = finish;
        this.cycle = cycle;
    }
}
