package com.elite.tools.imgtools.hook;

public class PoolHook extends Thread {

    private GlobalEventListener globalEventListener;

    public PoolHook(GlobalEventListener globalEventListener) {
        this.globalEventListener = globalEventListener;
    }

    public void run() {
        SysHook hook = new SysHook();
        hook.registerHook(globalEventListener);
    }

}