package com.pjzhong.dsa.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * 把长宽为X , Y的矩形，切成 h * w的块状。然后每个x,y分配一个regionId
 * */
public class divideBlocks {

  public static void main(String[] args) {
    int height = 60;
    int regionHeight = 3;
    int regionWidth = 4;
    //可以用来求邻居的regionId
    int blockRowDiff =
        regionId(height, regionHeight, regionWidth, regionHeight, 0) - regionId(height,
            regionHeight, regionWidth, 0, 0);
    int blockColDiff =
        regionId(height, regionHeight, regionWidth, 0, regionWidth) - regionId(height, regionHeight,
            regionWidth, 0, 0);
    System.out.println("block-row-diff:" + blockRowDiff);//正常来说应该和height相等
    System.out.println("block-coll-diff:" + blockColDiff);
    System.out.println("width:" + height / regionWidth);
    Set<Integer> ids = new HashSet<>();
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < height; y++) {
        int id = regionId(height, regionHeight, regionWidth, x, y);
        ids.add(id);
        System.out.format("%4d (%d %d) ", id, id / height, id % height);
      }
      System.out.println();
    }

    int rx = 18;
    int ry = 1;
    for(int i = rx - 1, sx = rx + 1; i <= sx;i++) {
      for(int j = ry - 1, sy = ry + 1; j <= sy; j++) {
        System.out.format("%d %d ",i,j);
      }
      System.out.println();
    }
    System.out.println(ids.size());
  }

  private static int regionId(int height, int regionHeight, int regionWidth, int x, int y) {
    return x / regionHeight * height + y / regionWidth;
  }

}
