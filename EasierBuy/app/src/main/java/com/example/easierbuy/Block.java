package com.example.easierbuy;

import java.io.Serializable;

public class Block implements Serializable {
    private String burl;
    private String bname;
    private String bprice;
    private String bbrand;
    private String bsite;

    public Block() {
    }

    public Block(String u, String n, String p, String b, String s) {
        this.burl = u;
        this.bname = n;
        this.bprice = p;
        this.bbrand = b;
        this.bsite = s;
    }



    public String getburl() {
        return burl;
    }
    public String getbname() {
        return bname;
    }
    public String getbprice() {
        return bprice;
    }
    public String getbbrand() {
        return bbrand;
    }
    public String getbsite() {
        return bsite;
    }


    public void setburl(String burl) {
        this.burl = burl;
    }
    public void setbname(String bname) {
        this.bname = bname;
    }
    public void setbprice(String bprice) {
        this.bprice = bprice;
    }
    public void setbbrand(String bbrand) {
        this.bbrand = bbrand;
    }
    public void setbsite(String bsite) {
        this.bsite = bsite;
    }

}
