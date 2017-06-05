package kri;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Canvas extends JPanel {
  private ArrayList<SupportPoint> points;
  private ArrayList<SupportPoint> line;
  private ArrayList<Point> sline;
  private BezierCurve curveA = null;
  private SplineHermite spline = null;
  private BezierCurve curve = null;
  private Double t = null;
  private double delta = 0.0015;
  final Random random;

  Canvas() {
    addMouseListener(new ClickListener());
    addMouseWheelListener(new MouseWheelListener() {
        private int pointWheel;
        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            int notches = mouseWheelEvent.getWheelRotation();
            if (notches < 0) {
                for(SupportPoint point:points){
                    //System.out.println("индекс"+points.indexOf(point)+"вес"+point.getWeight());
                    if(checkPoints(mouseWheelEvent.getX(),mouseWheelEvent.getY())){
                        points.get(pointWheel).weightUp();
                    }
                }
            } else {
                if(checkPoints(mouseWheelEvent.getX(),mouseWheelEvent.getY())){
                    points.get(pointWheel).weightDown();
                }
            }
            //System.out.println(pointWheel+points.get(pointWheel).getWeight());
            if(spline!= null) plotSpline();
            if(curve!= null) plot();
            if(curveA!= null) {
                plotAnimation();
            }
        }
        boolean checkPoints(int x,int y){
            boolean q;
            SupportPoint pointmp = new SupportPoint(x,y);
            for(SupportPoint point:points){
                if (distance(point,pointmp)<10){
                    pointWheel=points.indexOf(point);
                    return true;
                }
            }
            return false;
        }
    });

    points = new ArrayList<>();
    line = new ArrayList<>();
    sline = new ArrayList<>();
    random=new Random(System.currentTimeMillis());
  }

  public class ClickListener extends MouseInputAdapter {
      private int pointMove;
      @Override
    public void mousePressed(MouseEvent mouseEvent) {
      if (checkPoints(mouseEvent.getX(), mouseEvent.getY())) {
          points.add(new SupportPoint(mouseEvent.getX(), mouseEvent.getY()));
      }
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(pointMove>=0){
            points.get(pointMove).setLocation(mouseEvent.getX(),mouseEvent.getY());
            if(spline!= null) plotSpline();
            if(curve!= null) plot();
            if(curveA!= null) {
                plotAnimation();
            }
            }
        //repaint();
    }
    boolean checkPoints(int x,int y){
          boolean q;
          SupportPoint pointmp = new SupportPoint(x,y);
          for(SupportPoint point:points){
              if (distance(point,pointmp)<10){
                  pointMove=points.indexOf(point);
                  return false;
              }
          }
          pointMove=-1;
          return true;
      }
      double distance(SupportPoint a,SupportPoint b){
          double q1=a.getX()-b.getX();
          double q2=a.getY()-b.getY();
          return Math.pow(q1*q1+q2*q1,0.5);
      }
  }
  @Override
  public void paintComponent(Graphics g) {
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

    g.setColor(Color.GREEN);
    for (Point2D point : line)
      g.fillOval((int) point.getX(), (int) point.getY(), 3, 3);//Безье
    g.setColor(Color.BLUE);
    for (Point2D point : sline)
      g.fillOval((int) point.getX(), (int) point.getY(), 3, 3);//Сплайны

      g.setColor(Color.WHITE);
      for (SupportPoint point : points)
          if(point.getWeight()>=0) {
              g.setColor(Color.WHITE);
              g.fillOval((int) point.getX(), (int) point.getY(), (int) (2 * point.getWeight()+1), (int) ( 2* point.getWeight()+1));
          }
          else{
              g.setColor(Color.RED);
              g.fillOval((int) point.getX(), (int) point.getY(), (int) (-20 * point.getWeight()), (int) (-20 * point.getWeight()));

          }                                  //Опорные точки


      // Add new points
    if (curveA != null && t != null) { //Анимация Кривой Безье
      final SupportPoint pointAt = curveA.getPointAt(t);

      line.add(pointAt);
      t += delta/Math.pow(curveA.supportPoints.size(),0.5);
      if(line.size() > 1)

      if (t > 1.0) {
        t = null;
        //curveA = null;
      }

    }
    try {
        Thread.sleep(3);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    repaint();
  }

  void erase() { //Удаляем все
      points.clear();
      line.clear();
      sline.clear();
      curve =null;
      curveA = null;
      spline = null;
      t=null;
      repaint();
  }
  void eraseLast() {
      points.remove(points.size()-1);
      repaint();
  }

  void plotAnimation() {
      line.clear();
    t = 0.0;
    curveA =  new BezierCurve(new ArrayList<>(points));
    repaint();
  }
  void plot() {
    t = 0.0;
    curve =  new BezierCurve(new ArrayList<>(points));
    line = curve.plot(delta);
    repaint();
  }

  void plotSpline(){
      t=0.0;
      spline = new SplineHermite(new ArrayList<>(points));
      sline=spline.plot(delta);
      repaint();
  }
  void eraseSpline(){
        sline.clear();
        spline=null;
        repaint();
    }
    double distance(SupportPoint a,SupportPoint b){
        double q1=a.getX()-b.getX();
        double q2=a.getY()-b.getY();
        return Math.pow(q1*q1+q2*q1,0.5);
    }
}


