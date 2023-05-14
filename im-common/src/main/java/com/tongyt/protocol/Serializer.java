package com.tongyt.protocol;

import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author tongyt
 * @date 2023-05-08
 */
public interface Serializer {

    // 反序列化方法
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // 序列化方法
    <T> byte[] serialize(T object);

    @Slf4j
    enum Algorithm implements Serializer {

        JDK(0) {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    log.error("{} 反序列化失败, 异常信息:{}", clazz, e);
                    throw new RuntimeException(clazz + "反序列化失败");
                }
            }

            @Override
            public <T> byte[] serialize(T object) {
                try {
                    ByteOutputStream bos = new ByteOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    log.error("{} 序列化失败, 异常信息:{}", object, e);
                    throw new RuntimeException(object + "序列化失败");
                }
            }
        },

        Gson(1) {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return new Gson().fromJson(json, clazz);
            }

            @Override
            public <T> byte[] serialize(T object) {
                String json = new Gson().toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        };

        //序列化算法： 1个字节 jdk 0; json 1
        private int type;

        private Algorithm(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static Algorithm getAlgorithm(int type) {
            for (Algorithm algorithm : Algorithm.values()) {
                if (algorithm.type == type) {
                    return algorithm;
                }
            }
            throw new RuntimeException("序列化算法获取失败，不存在type：" + type + " 的序列化算法");
        }

    }

}
