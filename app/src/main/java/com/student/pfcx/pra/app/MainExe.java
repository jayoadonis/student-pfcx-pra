package com.student.pfcx.pra.app;

import com.student.pfcx.pra.lib.model.PageReplacementAlgorithm;

public class MainExe {
    public static void main(String[] args) {
        System.out.println( MainExe.class.getCanonicalName() );
        System.out.println( PageReplacementAlgorithm.Type.LRU.NAME );
    }
}
