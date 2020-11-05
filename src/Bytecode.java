public class Bytecode {
    public static class Instruction {
        String name; // E.g., "iadd", "call"
        int n = 0;

        public Instruction(String name) {
            this(name, 0);
        }

        public Instruction(String name, int nargs) {
            this.name = name;
            this.n = nargs;
        }
    }

    public static final short IADD = 1;
    public static final short ISUB = 2;
    public static final short IMUL = 3;
    public static final short ILT = 4;
    public static final short IEQ = 5;
    public static final short BR = 6;
    public static final short BRT = 7;
    public static final short BRF = 8;
    public static final short ICONST = 9;
    public static final short LOAD = 10;
    public static final short GLOAD = 11;
    public static final short STORE = 12;
    public static final short GSTORE = 13;
    public static final short PRINT = 14;
    public static final short POP = 15;
    public static final short CALL = 16;
    public static final short RET = 17;
    public static final short HALT = 18;

    public static Instruction[] instructions = new Instruction[] { null, new Instruction("iadd"),
            new Instruction("isub"), new Instruction("imul"), new Instruction("ilt"), new Instruction("ieq"),
            new Instruction("br", 1), new Instruction("brt", 1), new Instruction("brf", 1),
            new Instruction("iconst", 1), new Instruction("load", 1), new Instruction("gload", 1),
            new Instruction("store", 1), new Instruction("gstore", 1), new Instruction("print"), new Instruction("pop"),
            new Instruction("call", 1), new Instruction("ret"), new Instruction("halt")

    };
}