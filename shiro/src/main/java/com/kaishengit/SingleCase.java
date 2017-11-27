package com.kaishengit;

/**
 * Created by Administrator on 2017/11/25.
 */
public class SingleCase {
    private static SingleCase singleCase;
    private SingleCase (){};
    public static synchronized SingleCase getSingleCase(){
        if(singleCase == null){
            singleCase = new SingleCase();
        }
        return singleCase;
    }

}
