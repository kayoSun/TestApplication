package com.example.myapplication.rxbus;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class RxEvent {
    public Object value;
    public String key;

    public RxEvent() {
    }

    public RxEvent(String key, Object value) {
        this.value = value;
        this.key = key;
    }


    @Override
    public String toString() {
        return "RxEvent{" +
                "value=" + value +
                ", key='" + key + '\'' +
                '}';
    }
}
