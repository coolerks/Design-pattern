package com.xiaoxu.principle.prototype;

import java.io.*;

public class User implements Cloneable, Serializable {
    String s;
    StringBuilder sss = new StringBuilder();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public User deepCopy() {
        User user = null;
        try {
            // 字节数组输出流暂时的存储当前对象的二进制内容
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 对象构建输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // 将当前对象输出到字节数组输出流中
            objectOutputStream.writeObject(this);
            // 字节数组输入流，将字节数组输出流中的内容读入
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            // 对象输入流，用来序列化一个对象
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            // 序列化一个对象
            user = (User) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
