package com.example.asus1.learnopengl;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by asus1 on 2018/2/21.
 *
 * 二进制STL文件用固定的字节数来给出三角面片的几何信息。
 * 文件起始的80个字节是文件头，用于存贮零件名；
 * 紧接着用4个字节的整数来描述模型的三角面片个数，
 * 后面逐个给出每个三角面片的几何信息。每个三角面片占用固定的50个字节，
 * 依次是3个4字节浮点数(角面片的法矢量)，3个4字节浮点数(1个顶点的坐标)，
 * 3个4字节浮点数(2个顶点的坐标)，3个4字节浮点数(3个顶点的坐标)，
 * 最后2个字节用来描述三角面片的属性信息。
 * 一个完整二进制STL文件的大小为三角形面片数乘以50再加上84个字节
 */

public class STLReader {

    private StlLoadListener stlLoadListener;

    public Model parseBinStlInSDCard(String path){
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            return parserBinStl(fis);

        }catch (IOException e){
            e.printStackTrace();
        }
      return  null;
    }

    public Model parserBinStlInAssets(Context context, String fileName)
            throws IOException {

        InputStream is = context.getAssets().open(fileName);
        return parserBinStl(is);
    }


    public  Model parserBinStl(InputStream in)throws IOException{

        if(stlLoadListener !=null){
            stlLoadListener.onStart();
        }

        Model model = new Model();
        //前面80字节是文件头，用来存储文件名
        in.skip(80);

        //用4个字节的整数来描述模型的三角面片个数
        byte[] bytes = new byte[4];
        in.read(bytes);
        int facetCount = Util.byte4ToInt(bytes,0);
        model.setFacetCount(facetCount);
        if(facetCount == 0){
            in.close();
            return model;
        }

        //每个三角面片占用固定的50个字节
        byte[] facetBytes = new byte[50*facetCount];
        in.read(facetBytes);
        in.close();

        parseModel(model,facetBytes);

        if(stlLoadListener!=null){
            stlLoadListener.onFinish();
        }

        return model;
    }

    private void parseModel(Model model,byte[] facetBytes){
        int facetCount = model.getFacetCount();

        /**
         * 每个三角面片占用固定的50个字节，50字节当中：
         * 三角片的法向量：（一个向量相当于一个点）*（3维/点）*（4字节浮点数/维） = 12字节
         * 三角片的三个点坐标：（3个点）*（3维/点）*（4字节浮点数/维） = 36字节
         * 最后两个字节用来描述三角面片的属性信息
         */

        //所有的顶点坐标信息
        float[] verts = new float[facetCount*3*3];

        //保存所有三角面对应的法向量位置
        //一个三角面对应一个法向量，一个法向量有3个点
        //绘制模型时，是针对需要每个顶点对应的法向量，因此存贮长度需要*3
        //一个三角面的三个顶点的法向量是相同的
        //因此后面写入法向量数据的时候，只需要连续写入三个相同的法向量即可
        float[] vnorms = new float[facetCount*3*3];

        //三角面的属性
        short[] remarks = new short[facetCount];

        int stlOffest = 0;
        try {

            for(int i = 0;i<facetCount;i++){
                if(stlLoadListener!=null){
                    stlLoadListener.onLoading(i,facetCount);
                }

                for(int j =0;j<4;j++){
                    float x = Util.byte4ToFloat(facetBytes,stlOffest);
                    float y = Util.byte4ToFloat(facetBytes,stlOffest+4);
                    float z = Util.byte4ToFloat(facetBytes,stlOffest+8);
                    stlOffest+=12;
                    if(j == 0) {

                        vnorms[i * 9] = x;
                        vnorms[i * 9 + 1] = y;
                        vnorms[i * 9 + 2] = z;
                        vnorms[i * 9 + 3] = x;
                        vnorms[i * 9 + 4] = y;
                        vnorms[i * 9 + 5] = z;
                        vnorms[i * 9 + 6] = x;
                        vnorms[i * 9 + 7] = y;
                        vnorms[i * 9 + 8] = z;
                    }else {//三个顶点
                            verts[i * 9 + (j - 1) * 3] = x;
                            verts[i * 9 + (j - 1) * 3 + 1] = y;
                            verts[i * 9 + (j - 1) * 3 + 2] = z;

                            //记录模型中三个坐标轴方向的最大最小值
                            if (i == 0 && j == 1) {
                                model.minX = model.maxX = x;
                                model.minY = model.maxY = y;
                                model.minZ = model.maxZ = z;
                            } else {
                                model.minX = Math.min(model.minX, x);
                                model.minY = Math.min(model.minY, y);
                                model.minZ = Math.min(model.minZ, z);
                                model.maxX = Math.max(model.maxX, x);
                                model.maxY = Math.max(model.maxY, y);
                                model.maxZ = Math.max(model.maxZ, z);
                            }
                        }
                    }
                    short r = Util.byte2ToShort(facetBytes, stlOffest);
                    stlOffest = stlOffest + 2;
                    remarks[i] = r;


                    }

        }catch( Exception e){

            if(stlLoadListener!=null){
                stlLoadListener.onFailure(e);
            }else {
                e.printStackTrace();
            }

        }

        model.setVerts(verts);
        model.setVnorms(vnorms);
        model.setRemarks(remarks);

    }

}
