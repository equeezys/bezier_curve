package kri;

import java.awt.*;


public class SupportPoint extends Point {
    private double weight=1;
    public SupportPoint(int a,int b,int w){
        x=a;
        y=b;
        weight=w;
    }
    public SupportPoint(int a,int b){
        x=a;
        y=b;
        weight=1;
    }
    void weightUp(){
        weight+=0.1;
    }
    void weightDown(){
        weight-=0.1;
    }
    public double getWeight(){return weight;}
    SupportPoint plus(SupportPoint a){
        return (new SupportPoint(x+(int)(a.getX()),(y+(int)(a.getY()))));
    }
    SupportPoint minus(SupportPoint a){
        return (new SupportPoint(x-(int)(a.getX()),(y-(int)(a.getY()))));
    }
    SupportPoint mul(int a){
        return (new SupportPoint(x*a,(y*a)));
    }
    SupportPoint del(int a){
        return (new SupportPoint(x/a,(y/a)));
    }

}
