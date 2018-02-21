package com.example.asus1.learnopengl;



import java.nio.FloatBuffer;

/**
 * Created by asus1 on 2018/2/21.
 */

public class Model {

    //三角面个数
    private int facetCount;

    private float[] verts;

    private float[] vnorms;

    private short[] remarks;

    private FloatBuffer vertBuffer;

    private FloatBuffer vnormBuffer;

    float maxX;
    float minX;
    float maxY;
    float minY;
    float maxZ;
    float minZ;

   public Point getCenterPoint(){
       float cx = minX+(maxX-minX)/2;
       float cy = minY + (maxY-minY)/2;
       float cz = minZ + (maxZ-minZ)/2;

       return  new Point(cx,cy,cz);

   }

   public float getR(){
       float dx = maxX-minX;
       float dy = maxY -minY;
       float dz = maxZ - minZ;
       float max = Math.max(dx,dy);
       max = Math.max(max,dz);

       return max;
   }

   public  void setVerts(float[] verts){
       this.verts = verts;
       vertBuffer = Util.floatToBuffer(verts);
   }

   public void  setVnorms(float[] vnorms){
       this.vnorms = vnorms;
       vnormBuffer = Util.floatToBuffer(vnorms);
   }

    public int getFacetCount() {
        return facetCount;
    }

    public void setFacetCount(int facetCount) {
        this.facetCount = facetCount;
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(float maxZ) {
        this.maxZ = maxZ;
    }

    public float getMinZ() {
        return minZ;
    }

    public void setMinZ(float minZ) {
        this.minZ = minZ;
    }

    public FloatBuffer getVertBuffer() {
        return vertBuffer;
    }



    public FloatBuffer getVnormBuffer() {
        return vnormBuffer;
    }


}
