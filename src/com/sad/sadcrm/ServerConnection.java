package com.sad.sadcrm;

import java.util.LinkedList;
import java.util.List;

public class ServerConnection {
    private final List<Runnable> onRequestStart = new LinkedList<>();
    private final List<Runnable> onRequestEnd = new LinkedList<>();

    public void addOnStartListener(Runnable listener) {
        onRequestStart.add(listener);
    }

    public void addOnEndListener(Runnable listener) {
        onRequestEnd.add(listener);
    }


}
