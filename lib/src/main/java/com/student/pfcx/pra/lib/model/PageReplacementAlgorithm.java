package com.student.pfcx.pra.lib.model;

public abstract class PageReplacementAlgorithm {
    public static enum Type {
        FIFO( 0x0000_0002, "First-In First-Out", 0 ),
        LRU( 0x0000_0004, "Least-recently-used", 0 ),
        LFU( 0x0000_0008, "Least-frequently-used", 0),
        MRU( 0x0000_0010, "Most-recently-used", 0 );

        public final int CODE;
        public final String NAME;
        public final int LEN_CHAR_SEQ;

        private Type(
                final int _CODE,
                final String _NAME,
                int _lenName
        ) {
            this.CODE = _CODE;
            this.NAME = _NAME;

            //REM: [TODO-HERE, OH-MY], re-alignment... Persistently getting the proper len.
            if( _lenName != this.NAME.length() )
                this.LEN_CHAR_SEQ = this.NAME.length();
            else
                this.LEN_CHAR_SEQ = _lenName;
        }

        public static int getLenWithLongestName() {
            int len = 0;
            for( Type type : Type.values() )
                len = Math.max( len, type.LEN_CHAR_SEQ );
            return len;
        }
    }
}
