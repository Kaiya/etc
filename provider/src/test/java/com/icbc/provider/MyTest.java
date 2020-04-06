package com.icbc.provider;


import java.util.*;
import java.util.concurrent.*;

/**
 * @author Kaiya Xiong
 * @date 2019-09-11
 */
public class MyTest {

    static int COUNT = 0;
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ArrayList<String> hostname = new ArrayList<>();
        for (int i = 0; i< 6; i++){
            hostname.add("hkf"+(i+1));
        }
        for (int i = 0; i< 9; i++){
            hostname.add("usf"+(i+1));
        }
        final String passwd = "kyxiong123";
        final String enctypt = "aes-256-cfb";
        final String port = "31521";
        ArrayList<String> ssrArray = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        //hkf1.ss.kaiya.ml:31521:origin:aes-256-cfb:plain:a3l4aW9uZzEyMw/?obfsparam=&protoparam=&remarks=aGtmMQ
        for(int i = 0; i < hostname.size(); i++){
            String tmp = hostname.get(i)+".ss.kaiya.ml:"+port+":origin:"+enctypt+":plain:"+base64Util(passwd)+"/?obfsparam=&protoparam=&remarks="+base64Util(hostname.get(i));
            String ssrProto = "ssr://"+base64Util(tmp);
            ssrArray.add(ssrProto);
            if (i==hostname.size()-1){
                sb.append(ssrProto);
            }else{
                sb.append(ssrProto);
                sb.append("\n");
            }
        }


        System.out.println(base64Util(sb.toString()));

//        MyCallableTask task = new MyCallableTask();
//        FutureTask<String> ft = new FutureTask<>(task);
//        Thread thread = new Thread(ft);
//        thread.start();
//        System.out.println(ft.get());


        Future<String> future;
        ArrayList<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = Executors.newCachedThreadPool();
//        for (int i = 0; i< 100; i++){
//            future = es.submit(new MyCallableTask(COUNT));
//            futureList.add(future);
//        }
//        for(Future future1 : futureList){
//            System.out.println(future1.get());
//        }

//        es.execute(()->{
//            System.out.println("hello"+ ++COUNT);
//            System.out.println("world"+COUNT);
//        });
        SynchronizedExample se = new SynchronizedExample();
        es.execute(()->se.print());
        es.execute(()->se.print());
        SynchronizedExample se1 = new SynchronizedExample();
        es.execute(()->se1.print());
        es.execute(()->se1.print());




    }


    public static String base64Util(String src){
        Base64.Encoder encoder = Base64.getEncoder();
        String result = new String(encoder.encode(src.getBytes()));

        for (int i =0;i<2;i++){
            if (!Character.isAlphabetic(result.charAt(result.length() - 1))){
                result = result.substring(0, result.length() - 1);
            }
        }

        return result;
    }




}

class MyCallableTask implements Callable<String>{

    private int count;
    public MyCallableTask(int count){
        this.count = count;
    }
    @Override
    public String call() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello"+count++;
    }
}

class SynchronizedExample {
    public void print(){
        synchronized (SynchronizedExample.class){
            for (int i = 0; i < 10; i++) {
                System.out.println("Sync: "+i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

