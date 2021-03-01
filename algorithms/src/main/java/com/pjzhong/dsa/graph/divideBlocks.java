package com.pjzhong.dsa.graph;

/**
 * 把长宽为X , Y的矩形，切成 h * w的块状。然后每个x,y分配一个regionId
 * */
public class divideBlocks {

  public static void main(String[] args) {
    int height = 61;
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
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < height; y++) {
        int id = regionId(height, regionHeight, regionWidth, x, y);
        System.out.format("%4d (%d %d) ", id, id / height, id % height);
      }
      System.out.println();
    }
  }

  private static int regionId(int height, int regionHeight, int regionWidth, int x, int y) {
    return x / regionHeight * height + y / regionWidth;
  }

}
