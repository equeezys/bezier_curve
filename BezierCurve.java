package kri;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Tanaev Vladislav
 */
class BezierCurve {
  final ArrayList<SupportPoint> supportPoints;
  final int NMAX=30;
  static private double[][] bcr_cache;
  BezierCurve(ArrayList<SupportPoint> supportPoints) {
    bcr_cache = new double[NMAX][NMAX];
    this.supportPoints = supportPoints;
  }


  ArrayList<SupportPoint> plot(double delta) {
    final ArrayList<SupportPoint> res = new ArrayList<>((int) (1 / delta + 1));
    double t = 0;
    while (t < 1) {
      res.add(getPointAt(t));
      t = t + delta/Math.pow(supportPoints.size(),0.5);
    }
    return res;
  }

  SupportPoint getPointAt(double t) {
    double k, a = 0, b = 0,c =0;
    for (SupportPoint point : supportPoints) {
      int i = supportPoints.indexOf(point);
      k = polynomBern(i, supportPoints.size() - 1, t);
      a = k * point.getX()*point.getWeight()+ a;
      b = k * point.getY()*point.getWeight() + b;
      c= k*point.getWeight()+c;
        //System.out.println(c);
    }
    return new SupportPoint((int) (a/c), (int)( b/c));
  }
  static private double polynomBern(int i, int n, double t) {
    double a = bcr2(n,i) * Math.pow(t, i) * Math.pow(1 - t, n - i);
    return a;
  }
  static private double bcr2(int n, int k) {
    if (k > n / 2) k = n - k;
    if (k == 1) return n;
    if (k == 0) return 1;
    if (bcr_cache[n][k] == 0)
      bcr_cache[n][k] = bcr2(n - 1, k) + bcr2(n - 1, k - 1);
    return bcr_cache[n][k];
  }
}
