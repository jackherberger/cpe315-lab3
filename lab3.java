// TYLER BAXTER AND JACK HERBERGER

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Enumeration;

public class lab3 extends instructions {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage java Main <filename>");
            System.exit(1);
        }
        String filename = args[0];
        String scriptname;
        ArrayList<String> scriptLines;
        if (args.length > 1) {
            scriptname = args[1];   
            Scanner scriptReader = new Scanner(new FileReader(scriptname)); 
            scriptLines = getScripts(scriptReader); 
        }
        Scanner reader = new Scanner(new FileReader(filename));
        Hashtable<String, String> reg_codes = buildRegisterTable();
        ArrayList<String> lines = getLines(reader);
        Hashtable<String, String> label_addresses = buildLabelTable(lines);
        ArrayList<Object> write = write(reg_codes, lines, label_addresses);
        
        spim(write, reg_codes, label_addresses);
    }

    public static void spim(ArrayList<Object> write, Hashtable<String, String> reg_codes, Hashtable<String, String> label_addresses) {
        int[] registers = new int[28];
        int[] data_memory = new int[8192];
        final int[] pc = {0};
        for (int i = 0; i < data_memory.length; i++) {
            data_memory[i] = 0;
        }
        Scanner scanner = new Scanner(System.in);
        Runnable runnable = new Runnable() {
            public void run() {
                Object curr = write.get(pc[0]);
                if (curr.getClass().equals(instructions.And.class)){
                    And obj = (And) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer rd = Integer.parseInt(obj.rd, 2);
                    registers[rd] = registers[rs] & registers[rt];
                }
                else if (curr.getClass().equals(instructions.Or.class)){
                    Or obj = (Or) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer rd = Integer.parseInt(obj.rd, 2);
                    registers[rd] = (registers[rs] | registers[rt]);
                }
                else if (curr.getClass().equals(instructions.Add.class)){
                    Add obj = (Add) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer rd = Integer.parseInt(obj.rd, 2);
                    registers[rd] = registers[rs] + registers[rt];
                }
                else if (curr.getClass().equals(instructions.Sll.class)){
                    Sll obj = (Sll) curr;
                    Integer rd = Integer.parseInt(obj.rd, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer sa = Integer.parseInt(obj.sa, 2);
                    registers[rd] = registers[rt] << sa;
                }
                else if (curr.getClass().equals(instructions.Sub.class)){
                    Sub obj = (Sub) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer rd = Integer.parseInt(obj.rd, 2);
                    registers[rd] = registers[rs] - registers[rt];
                }
                else if (curr.getClass().equals(instructions.Slt.class)){
                    Slt obj = (Slt) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer rd = Integer.parseInt(obj.rd, 2);
                    int set = 0;
                    if (rs < rt) { 
                        set = 1;
                    }
                    registers[rd] = set;
                }
                else if (curr.getClass().equals(instructions.Jr.class)){
                    Jr obj = (Jr) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    pc[0] = rs;
                }
                else if (curr.getClass().equals(instructions.Addi.class)){
                    Addi obj = (Addi) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer imm = Integer.parseInt(obj.imm, 2);
                    registers[rt] = registers[rs] + imm;
                }
                else if (curr.getClass().equals(instructions.Beq.class)){
                    Beq obj = (Beq) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer offset = Integer.parseInt(obj.offset, 2);
                    byte b_offset = (byte)((int)offset);
                    if (rs == rt){
                        pc[0] += b_offset;
                    }
                }
                else if (curr.getClass().equals(instructions.Bne.class)){
                    Bne obj = (Bne) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer offset = Integer.parseInt(obj.offset, 2);
                    byte b_offset = (byte)((int)offset);
                    if (rs != rt){
                        pc[0] += b_offset;
                    }
                }
                else if (curr.getClass().equals(instructions.Lw.class)){
                    Lw obj = (Lw) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer offset = Integer.parseInt(obj.offset, 2);
                    registers[rt] = data_memory[rs+offset];
                }
                else if (curr.getClass().equals(instructions.Sw.class)){
                    Sw obj = (Sw) curr;
                    Integer rs = Integer.parseInt(obj.rs, 2);
                    Integer rt = Integer.parseInt(obj.rt, 2);
                    Integer offset = Integer.parseInt(obj.offset, 2);
                    registers[rs+offset] = data_memory[rt];
                }
                else if (curr.getClass().equals(instructions.J.class)){
                    Jal obj = (Jal)curr;
                    pc[0] = Integer.parseInt(obj.target, 2);
                }
                else if (curr.getClass().equals(instructions.Jal.class)){
                    Jal obj = (Jal)curr;
                    registers[28] = pc[0];
                    pc[0] = Integer.parseInt(obj.target, 2);
                }
                pc[0]++;
            }
        };

        while (true) {
            System.out.print("mips> ");
            String input = scanner.nextLine();
            if (input.length() == 0) {
                System.out.print("Please enter a command");
            }
            if (input.charAt(0) == 'q') {
                break;
            }
            else if (input.charAt(0) == 'm') {
                int space1 = input.indexOf(' ');
                int space2 = input.indexOf(' ', space1+1);
                int loc1 = Integer.parseInt(input.substring(space1+1, space2));
                int loc2 = Integer.parseInt(input.substring(space2+1, input.length()));
                for (int i = loc1; i <= loc2; i++) {
                    System.out.println("[" + i + "]" + " = " + data_memory[i]);
                }
            }
            else if (input.charAt(0) == 's') {
                if (input.length() > 1) {
                    for (int i = 0; i < input.charAt(1); i ++) {
                        if (pc[0] < write.size()){
                            runnable.run();
                        }
                        else {
                            System.out.println("Program has ended.");
                            break;
                        }
                    }
                }
                else {
                    if (pc[0] < write.size()){
                        runnable.run();
                    }
                    else {
                        System.out.println("Program has ended.");
                        break;
                    }
                }
            }
            else if (input.charAt(0) == 'c'){
                for (int i = 0; i < 32; i++) {
                    registers[i] = 0;
                }
                for (int i = 0; i < 8192; i++) {
                    data_memory[i] = 0;
                }
                pc[0] = 0;
            }
            else if (input.charAt(0) == 'h') {
                System.out.print("\nh = show help\nd = dump register state\ns = single step through the program (i.e. execute 1 instruction and stop)\ns num = step through num instructions of the program\nr = run until the program ends\nm num1 num2 = display data memory from location num1 to num2\nc = clear all registers, memory, and the program counter to 0\nq = exit the program\n\n");
            }
            else if (input.charAt(0) == 'd') {
                ArrayList<String> keysList = new ArrayList<>();
                Enumeration<String> keys = reg_codes.keys();
                while (keys.hasMoreElements()) {
                    keysList.add(keys.nextElement());
                }
                System.out.println("pc = " + pc[0]);
                for (int i = 0; i < registers.length; i ++){
                    System.out.println("$" + keysList.get(i) + " = " + registers[i]);
                }
            }
        }
        scanner.close();
    }

    public static ArrayList<String> getScripts(Scanner reader) {
        ArrayList<String> lst = new ArrayList<>();
        while(reader.hasNextLine()) {
            lst.add(reader.next());
        }
        return lst;
    }

    public static ArrayList<Object> write(Hashtable<String, String> reg_codes, ArrayList<String> lines, Hashtable<String, String> label_addresses) {
        ArrayList<Object> instructions = new ArrayList<>();
        int pc = 0;
        for (String str : lines) {
            if (str.indexOf(':') != -1) {
                str = str.substring(str.indexOf(':')+1, str.length());
            }
            if (str.trim().isEmpty()) {
                continue;
            }
            String[] split = str.trim().split("[' '\\(\\)\\,\\$\\s]+");  
       
            if (split[0].equals("and")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                And curr = new And(rd, rs, rt);
                curr.printObj();
                instructions.add(curr);

            }
            else if (split[0].equals("add")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Add curr = new Add(rd, rs, rt);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("or")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Or curr = new Or(rd, rs, rt);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("sub")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Sub curr = new Sub(rd, rs, rt);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("sll")){
                String rd = reg_codes.get(split[1]);
                String rt = reg_codes.get(split[2]);
                String sa = Integer.toBinaryString(Integer.parseInt(split[3]));
                Sll curr = new Sll(rd, rt, sa);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("slt")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Slt curr = new Slt(rd, rs, rt);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("jr")){
                String rs = reg_codes.get(split[1]);
                Jr curr = new Jr(rs);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("addi")){
                String rt = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String temp = Integer.toBinaryString(Integer.parseInt(split[3]));
                String imm = changeBinLen(temp, 16, Integer.parseInt(split[3]));
                Addi curr = new Addi(rt, rs, imm);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("beq")){
                String rs = reg_codes.get(split[1]);
                String rt = reg_codes.get(split[2]);
                Integer address = Integer.parseInt(label_addresses.get(split[3]), 2);
                Integer num_offset = address - pc - 1;
                String bin_offset = Integer.toBinaryString(num_offset);
                String offset = changeBinLen(bin_offset, 16, num_offset);
                Beq curr = new Beq(rs, rt, offset);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("bne")){
                String rs = reg_codes.get(split[1]);
                String rt = reg_codes.get(split[2]);
                Integer address = Integer.parseInt(label_addresses.get(split[3]), 2);
                Integer num_offset = address - pc - 1;
                String bin_offset = Integer.toBinaryString(num_offset);
                String offset = changeBinLen(bin_offset, 16, num_offset);
                Bne curr = new Bne(rs, rt, offset);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("lw")){
                String rt = reg_codes.get(split[1]);
                String temp = Integer.toBinaryString(Integer.parseInt(split[2]));
                String offset = changeBinLen(temp, 16, Integer.parseInt(split[2]));
                String rs = reg_codes.get(split[3]);
                Lw curr = new Lw(rt, offset, rs);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("sw")){
                String rt = reg_codes.get(split[1]);
                String temp = Integer.toBinaryString(Integer.parseInt(split[2]));
                String offset = changeBinLen(temp, 16, Integer.parseInt(split[2]));
                String rs = reg_codes.get(split[3]);
                Sw curr = new Sw(rt, offset, rs);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("j")){
                String target = changeBinLen(label_addresses.get(split[1]), 26, 0);
                J curr = new J(target);
                curr.printObj();
                instructions.add(curr);
            }
            else if (split[0].equals("jal")){
                String target = changeBinLen(label_addresses.get(split[1]), 26, 0);
                Jal curr = new Jal(target);
                curr.printObj();
                instructions.add(curr);
            }
            else {
                System.out.println("invalid instruction: " + split[0]);
                break;
            }
            System.out.println();
            pc += 1;
        }
        return instructions;
    }

    public static ArrayList<String> getLines(Scanner reader) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.indexOf('#') != -1)
                line = line.substring(0, line.indexOf('#'));
            else if (line.indexOf('#') == 0) {
                continue;
            } 
            if (!(line.trim().isEmpty())){
                lines.add(line);
            }
            
        }
        return lines;
    }

    public static Hashtable<String, String> buildLabelTable(ArrayList<String> lines){
        Hashtable<String, String> codes = new Hashtable<String, String>();
        for (int i = 0; i < lines.size(); i++){
            if (lines.get(i).indexOf(':') != -1){
                String binaryString = Integer.toBinaryString(i);
                int padding = 32 - binaryString.length();
                
                if (padding > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < padding; j++) {
                        sb.append('0');
                    }
                    sb.append(binaryString);
                    binaryString = sb.toString();
                }

                codes.put(lines.get(i).substring(0, lines.get(i).indexOf(':')), binaryString);
            }
        }
        return codes;
    }

    public static String changeBinLen(String binstr, int length, int num) {
        char add_char = '0';
        if (num < 0) {
            add_char = '1';
        }
        if (binstr.length() < length) {
            while (binstr.length() < length) {
                binstr = add_char + binstr;
            }
        } else if (binstr.length() > length) {
            while (binstr.length() > length) {
                binstr = binstr.substring(1);
            }
        }
        return binstr;
    }

    public static Hashtable<String, String> buildRegisterTable() {
        Hashtable<String, String> codes = new Hashtable<String, String>();
        // R-TYPE INSTRUCTIONS = OP CODES
        codes.put("zero", "00000");
        codes.put("0", "00000");

        codes.put("v0", "00010");
        codes.put("v1", "00011");
        
        codes.put("a0", "00100");
        codes.put("a1", "00101");
        codes.put("a2", "00110");
        codes.put("a3", "00111");
        
        codes.put("t0", "01000");
        codes.put("t1", "01001");
        codes.put("t2", "01010");
        codes.put("t3", "01011");
        codes.put("t4", "01100");
        codes.put("t5", "01101");
        codes.put("t6", "01110");
        codes.put("t7", "01111");
        codes.put("t8", "11000");
        codes.put("t9", "11001");
       
        codes.put("s0", "10000");
        codes.put("s1", "10001");
        codes.put("s2", "10010");
        codes.put("s3", "10011");
        codes.put("s4", "10100");
        codes.put("s5", "10101");
        codes.put("s6", "10110");
        codes.put("s7", "10111");

        codes.put("sp", "11101");
        codes.put("ra", "11111");
        return codes;
    }

// R type - and, or, add, sll, sub, slt, jr
// I Type - addi, beq, bne, lw, sw, 
// J Type - j, jal

}