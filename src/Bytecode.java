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
}