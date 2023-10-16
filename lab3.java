// TYLER BAXTER AND JACK HERBERGER

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;

public class lab2 {
    public static class And {

        public String opcode;
        public String rd;
        public String rs;
        public String rt;
        public String funct;
        public String shampt;

        public And(String rd, String rs, String rt) {
            this.opcode = "000000";
            this.rd = rd;
            this.rs = rs;
            this.rt = rt;
            this.funct = "100100";
            this.shampt = "00000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.rd + " ");
            System.out.print(this.shampt + " ");
            System.out.print(this.funct);
        }
    }

    public static class Or {

        public String opcode;
        public String rd;
        public String rs;
        public String rt;
        public String funct;
        public String shampt;

        public Or(String rd, String rs, String rt) {
            this.opcode = "000000";
            this.rd = rd;
            this.rs = rs;
            this.rt = rt;
            this.funct = "100101";
            this.shampt = "00000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.rd + " ");
            System.out.print(this.shampt + " ");
            System.out.print(this.funct);
        }
    }

    public static class Add {

        public String opcode;
        public String rd;
        public String rs;
        public String rt;
        public String funct;
        public String shampt;

        public Add(String rd, String rs, String rt) {
            this.opcode = "000000";
            this.rd = rd;
            this.rs = rs;
            this.rt = rt;
            this.funct = "100000";
            this.shampt = "00000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.rd + " ");
            System.out.print(this.shampt + " ");
            System.out.print(this.funct);
        }
    }

    public static class Sll {

        public String opcode;
        public String rd;
        public String rt;
        public String sa;
        public String rs;
        public String funct;

        public Sll(String rd, String rt, String sa) {
            this.opcode = "000000";
            this.rd = rd;
            this.rt = rt;
            this.sa = changeBinLen(sa, 5, 0);
            this.rs = "00000";
            this.funct = "000000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.rd + " ");
            System.out.print(this.sa + " ");
            System.out.print(this.funct);
        }
    }

    public static class Sub {

        public String opcode;
        public String rd;
        public String rs;
        public String rt;
        public String funct;
        public String shampt;

        public Sub(String rd, String rs, String rt) {
            this.opcode = "000000";
            this.rd = rd;
            this.rs = rs;
            this.rt = rt;
            this.funct = "100010";
            this.shampt = "00000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.rd + " ");
            System.out.print(this.shampt + " ");
            System.out.print(this.funct);
        }
    }

    public static class Slt {

        public String opcode;
        public String rd;
        public String rs;
        public String rt;
        public String funct;
        public String shampt;

        public Slt(String rd, String rs, String rt) {
            this.opcode = "000000";
            this.rd = rd;
            this.rs = rs;
            this.rt = rt;
            this.funct = "101010";
            this.shampt = "00000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.rd + " ");
            System.out.print(this.shampt + " ");
            System.out.print(this.funct);
        }
    }

    public static class Jr {

        public String opcode;
        public String rs;
        public String shampt;
        public String funct;

        public Jr(String rs) {
            this.opcode = "000000";
            this.rs = rs;
            this.shampt = "000000000000000";
            this.funct = "001000";
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.shampt + " ");
            System.out.print(this.funct);
            }
    }
    
    public static class Addi {
        public String opcode;
        public String rs;
        public String rt;
        public String imm;
        
        public Addi(String rs, String rt, String imm) {
            this.opcode = "001000";
            this.rs = rs;
            this.rt = rt;
            this.imm = imm;
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.imm);
        }
    }

    public static class Beq {
        public String opcode;
        public String rs;
        public String rt;
        public String offset;
        
        public Beq(String rs, String rt, String offset) {
            this.opcode = "000100";
            this.rs = rs;
            this.rt = rt;
            this.offset = offset;
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.offset);
        }
    }

    public static class Bne {
        public String opcode;
        public String rs;
        public String rt;
        public String offset;
        
        public Bne(String rs, String rt, String offset) {
            this.opcode = "000101";
            this.rs = rs;
            this.rt = rt;
            this.offset = offset;
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.offset);
        }
    }

    public static class Lw {
        public String opcode;
        public String rs;
        public String rt;
        public String offset;
        
        public Lw(String rt, String offset, String rs) {
            this.opcode = "100011";
            this.rs = rs;
            this.rt = rt;
            this.offset = offset;
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.offset);
        }
    }
    
    public static class Sw {
        public String opcode;
        public String rs;
        public String rt;
        public String offset;
        
        public Sw(String rt, String offset, String rs) {
            this.opcode = "101011";
            this.rs = rs;
            this.rt = rt;
            this.offset = offset;
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.rs + " ");
            System.out.print(this.rt + " ");
            System.out.print(this.offset);
        }
    }

    public static class J {
        public String opcode;
        public String target;
        
        public J(String target) {
            this.opcode = "000010";
            this.target = target;
        }

        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.target);
        }
    }

    public static class Jal {
        public String opcode;
        public String target;
        
        public Jal(String target) {
            this.opcode = "000011";
            this.target = target;
        }
        public void printObj() {
            System.out.print(this.opcode + " ");
            System.out.print(this.target);
        }
    }


    public static void main(String[] args) throws IOException{
        if (args.length != 1) {
            System.out.println("Usage java Main <filename>");
            System.exit(1);
        }
        
        String filename = args[0];
        Scanner reader = new Scanner(new FileReader(filename));
        Hashtable<String, String> reg_codes = buildRegisterTable();
        ArrayList<String> lines = getLines(reader);
        Hashtable<String, String> label_addresses = buildLabelTable(lines);
        write(reg_codes, lines, label_addresses);

    }

    public static void write(Hashtable<String, String> reg_codes, ArrayList<String> lines, Hashtable<String, String> label_addresses) {

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

            }
            else if (split[0].equals("add")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Add curr = new Add(rd, rs, rt);
                curr.printObj();
            }
            else if (split[0].equals("or")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Or curr = new Or(rd, rs, rt);
                curr.printObj();
            }
            else if (split[0].equals("sub")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Sub curr = new Sub(rd, rs, rt);
                curr.printObj();
            }
            else if (split[0].equals("sll")){
                String rd = reg_codes.get(split[1]);
                String rt = reg_codes.get(split[2]);
                String sa = Integer.toBinaryString(Integer.parseInt(split[3]));
                Sll curr = new Sll(rd, rt, sa);
                curr.printObj();
            }
            else if (split[0].equals("slt")){
                String rd = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String rt = reg_codes.get(split[3]);
                Slt curr = new Slt(rd, rs, rt);
                curr.printObj();
            }
            else if (split[0].equals("jr")){
                String rs = reg_codes.get(split[1]);
                Jr curr = new Jr(rs);
                curr.printObj();
            }
            else if (split[0].equals("addi")){
                String rt = reg_codes.get(split[1]);
                String rs = reg_codes.get(split[2]);
                String temp = Integer.toBinaryString(Integer.parseInt(split[3]));
                String imm = changeBinLen(temp, 16, Integer.parseInt(split[3]));
                Addi curr = new Addi(rt, rs, imm);
                curr.printObj();
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
            }
            else if (split[0].equals("lw")){
                String rt = reg_codes.get(split[1]);
                String temp = Integer.toBinaryString(Integer.parseInt(split[2]));
                String offset = changeBinLen(temp, 16, Integer.parseInt(split[2]));
                String rs = reg_codes.get(split[3]);
                Lw curr = new Lw(rt, offset, rs);
                curr.printObj();
            }
            else if (split[0].equals("sw")){
                String rt = reg_codes.get(split[1]);
                String temp = Integer.toBinaryString(Integer.parseInt(split[2]));
                String offset = changeBinLen(temp, 16, Integer.parseInt(split[2]));
                String rs = reg_codes.get(split[3]);
                Sw curr = new Sw(rt, offset, rs);
                curr.printObj();
            }
            else if (split[0].equals("j")){
                String target = changeBinLen(label_addresses.get(split[1]), 26, 0);
                J curr = new J(target);
                curr.printObj();
            }
            else if (split[0].equals("jal")){
                String target = changeBinLen(label_addresses.get(split[1]), 26, 0);
                Jal curr = new Jal(target);
                curr.printObj();
            }
            else {
                System.out.println("invalid instruction: " + split[0]);
                break;
            }
            
            System.out.println();
            pc += 1;
        }
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
 