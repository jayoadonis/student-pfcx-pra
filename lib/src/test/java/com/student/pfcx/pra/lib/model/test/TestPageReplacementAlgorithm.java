package com.student.pfcx.pra.lib.model.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.student.pfcx.pra.lib.model.PageReplacementAlgorithm;

public class TestPageReplacementAlgorithm {

    @Test
    public void testTypeName() {
        final String FIFO = PageReplacementAlgorithm.Type.FIFO.NAME;
        final String LRU = PageReplacementAlgorithm.Type.LRU.NAME;
        final String LFU = PageReplacementAlgorithm.Type.LFU.NAME;
        final String MRU = PageReplacementAlgorithm.Type.MRU.NAME;

        Assertions.assertNotEquals(
                FIFO, LRU
        );
        Assertions.assertNotEquals(
                LRU, LFU
        );
        Assertions.assertNotEquals(
                LFU, MRU
        );
        Assertions.assertNotEquals(
                MRU, FIFO
        );
        Assertions.assertNotEquals(
                FIFO, LFU
        );
        Assertions.assertNotEquals(
                MRU, LRU
        );
    }

    @Test
    public void testTypeNameLen() {
        Assertions.assertEquals(
                PageReplacementAlgorithm.Type.FIFO.NAME.length(),
                PageReplacementAlgorithm.Type.FIFO.LEN_CHAR_SEQ
        );
        Assertions.assertEquals(
                PageReplacementAlgorithm.Type.LRU.NAME.length(),
                PageReplacementAlgorithm.Type.LRU.LEN_CHAR_SEQ
        );
    }

    @Test
    public void testTypeNameLongestLen() {
        int typeNameLongestLenA = 0;
        int typeNameLongestLenB =
                PageReplacementAlgorithm.Type.getLenWithLongestName();
        Assertions.assertNotEquals(
                typeNameLongestLenA,
                typeNameLongestLenB
        );

        for( PageReplacementAlgorithm.Type type :
            PageReplacementAlgorithm.Type.values()
        ) {
            typeNameLongestLenA = Math.max( typeNameLongestLenA, type.LEN_CHAR_SEQ );
        }
        Assertions.assertEquals(
                typeNameLongestLenA,
                typeNameLongestLenB
        );
    }

    @Test
    public void testTypeNameCode() {
        Assertions.assertNotEquals(
                0, PageReplacementAlgorithm.Type.FIFO.CODE
        );
        Assertions.assertNotEquals(
                0, PageReplacementAlgorithm.Type.LRU.CODE
        );
        Assertions.assertNotEquals(
                0, PageReplacementAlgorithm.Type.LFU.CODE
        );
        Assertions.assertNotEquals(
                0, PageReplacementAlgorithm.Type.MRU.CODE
        );


        Assertions.assertEquals(
                2, PageReplacementAlgorithm.Type.FIFO.CODE
        );
        Assertions.assertEquals(
                4, PageReplacementAlgorithm.Type.LRU.CODE
        );
        Assertions.assertEquals(
                8, PageReplacementAlgorithm.Type.LFU.CODE
        );
        Assertions.assertEquals(
                16, PageReplacementAlgorithm.Type.MRU.CODE
        );
    }
}
