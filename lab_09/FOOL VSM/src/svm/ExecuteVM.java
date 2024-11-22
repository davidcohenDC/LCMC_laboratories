package svm;

public class ExecuteVM {

    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;

    // il codice lo crea il parser.
    private int[] code;
    private int[] memory = new int[MEMSIZE];

    private int ip = 0; //pc register
    private int sp = MEMSIZE; //punta al top dello stack
    private int tm;
    private int hp = 0;
    private int fp = sp;
    private int ra;

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) {
            int bytecode = code[ip++]; // fetch
            int v1, v2;
            int address;
            switch (bytecode) {

                case SVMParser.PUSH:
                    push(code[ip++]);
                    break;

                case SVMParser.POP:
                    pop();
                    break;

                case SVMParser.ADD:
                    v1 = pop(); // ultimo che ottengo
                    v2 = pop();
                    push(v2 + v1);
                    break;

                case SVMParser.SUB:
                    v1 = pop(); // ultimo che ottengo
                    v2 = pop();
                    push(v2 - v1);
                    break;

                case SVMParser.MULT:
                    v1 = pop(); // ultimo che ottengo
                    v2 = pop();
                    push(v2 * v1);
                    break;

                case SVMParser.DIV:
                    v1 = pop(); // ultimo che ottengo
                    v2 = pop();
                    push(v2 / v1);
                    break;

                case SVMParser.JS:
                    ra = ip;
                    ip = pop();
                    break;

                case SVMParser.STOREW:
                    address = pop();
                    memory[address] = pop();
                    break;

                case SVMParser.LOADW:
                    push(memory[pop()]);
                    break;

                case SVMParser.LOADRA:
                    push(ra);
                    break;

                case SVMParser.STORERA:
                    ra = pop();
                    break;

                case SVMParser.LOADFP:
                    push(fp);
                    break;

                case SVMParser.STOREFP:
                    fp = pop();
                    break;

                case SVMParser.COPYFP:
                    fp = sp;
                    break;

                case SVMParser.LOADHP:
                    push(hp);
                    break;

                case SVMParser.STOREHP:
                    hp = pop();
                    break;

                case SVMParser.BRANCH:
                    address = code[ip];
                    ip = address;
                    break;

                case SVMParser.BRANCHEQ:
                    address = code[ip++];
                    v1 = pop();
                    v2 = pop();
                    if (v2 == v1) {
                        ip = address;
                    }
                    break;

                case SVMParser.BRANCHLESSEQ:
                    address = code[ip++];
                    v1 = pop();
                    v2 = pop();
                    if (v2 <= v1) {
                        ip = address;
                    }
                    break;

                case SVMParser.STORETM:
                    tm = pop();
                    break;

                case SVMParser.LOADTM:
                    push(tm);
                    break;

                case SVMParser.PRINT:
                    System.out.println((sp < MEMSIZE) ? memory[sp] : "Empty Stack!");
                    break;

                case SVMParser.HALT:
                    return;
            }
        }
    }

    private int pop() {
        return memory[sp++];
    }

    private void push(int v) {
        memory[--sp] = v;
    }

}