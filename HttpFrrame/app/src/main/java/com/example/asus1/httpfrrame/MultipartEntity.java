package com.example.asus1.httpfrrame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class MultipartEntity extends HttpEntity {

    private final static char[] MUUTIPART_CHARS = "-1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQZZZ"
            .toCharArray();

    private final String NEW_LINE_STR = "\r\n";
    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_DISPOSITION = "Content-Disposition";

    private final String TYPE_TEXT_CHARSET = "text/plain; charset = UTF-8";

    private final String TYPE_OCTET_STREAM = "application/octet-stream";//字节流参数

    //字节数组参数
    private final byte[] BINARY_ENCODING = "Content--Transfer-Encoding: binary\r\n\r\n".getBytes();

    //文本参数
    private final byte[] BIT_ENCODING = "Content-Tranfer-Encoding: 8bit\r\n\r\n".getBytes();

    //参数分隔符
    private String mBoundary = null;

    //输出流，用于缓存参数数据
    ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();

    public MultipartEntity() {
        this.mBoundary = generateBoumdary();
    }

    private final String generateBoumdary(){
        final StringBuffer buffer = new StringBuffer();
        final Random random = new Random();
        for(int i = 0;i<30;i++){
            buffer.append(MUUTIPART_CHARS[random.nextInt(MUUTIPART_CHARS.length)]);

        }
        return buffer.toString();
    }

    private void writeFirstBoundary()throws IOException{
        mOutputStream.write(("--"+mBoundary+"\r\n").getBytes());
    }

    public void addStringPart(final String paramName,final String value) {
        writeToOutputStream(paramName, value.getBytes(),
                TYPE_TEXT_CHARSET, BIT_ENCODING, ""
        );
    }

    //将数据写入到输出流中
    private void writeToOutputStream(String paramName,byte[] rawData,String type,
                                     byte[] encodingBytes,String fileName){
        try {
            writeFirstBoundary();;
            mOutputStream.write((CONTENT_TYPE+type+NEW_LINE_STR).getBytes());
            mOutputStream
                    .write(getContentDispostionBytes(paramName,fileName));
            mOutputStream.write(encodingBytes);
            mOutputStream.write(rawData);
            mOutputStream.write(NEW_LINE_STR.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addByteArrayPart(String paramName,final byte[] rawData){
        writeToOutputStream(paramName,rawData,TYPE_OCTET_STREAM,BINARY_ENCODING,"no-file");

    }


}
