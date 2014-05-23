package eg.edu.guc.mimps.assembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import eg.edu.guc.mimps.exceptions.SyntaxErrorException;
import eg.edu.guc.mimps.components.Executable;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.utils.Constants;

public class Assembler {
	
	private Map<String, Integer> labels;
	private Map<Integer, Instruction> instructions;
	private Map<Integer, Integer> originalLine;
	private Map<String, Integer> opcodes;
	private Map<String, Integer> functionCodes;
	private Map<Character, Integer> registerPrefix;
	private Map<String, Integer> registers;
	private Map<String, String> types;
	private int origin;
	private Reader sourceCode;
	private static final int wordSize = 4;
	private Instruction currentInstruction;
	private InstructionFetchDecodeRegisters pipleLine;
	
	
	public static void main(String[] args) throws SyntaxErrorException, IOException {
		Assembler s = new Assembler(0, new FileReader("sourceCode.txt"), null);
		s.assemble();
		s.print();

	}

	public Assembler(int origin, Reader sourceCode, InstructionFetchDecodeRegisters instructionFetchDecodeRegisters) {
		this.origin = origin;
		this.sourceCode = sourceCode;
		this.pipleLine = instructionFetchDecodeRegisters;
		init();
	
	}
	
	private void init() {
		initCodes();
		initTypes();
		initRegisters();
		labels = new HashMap<String, Integer>();
		instructions = new HashMap<Integer, Instruction>();
		originalLine = new HashMap<Integer, Integer>();
	}
	
	public boolean execute(int pc) {
		if (!instructions.containsKey(pc)) {
			return false;
		}
		currentInstruction = instructions.get(pc);
		return true;
	}
	
	public void assemble() throws SyntaxErrorException{
		LinkedList<String> pureInstructions = firstPass();
		encodeInstructions(pureInstructions);
		
	}
	
	public void print() {
		for (Integer key : instructions.keySet()) {
			System.out.println(key + " : " + instructions.get(key).getOpcode());
		}
	}
	
	private LinkedList<String> firstPass() throws SyntaxErrorException{
		LinkedList<String> result = new LinkedList<String>();
		Scanner sc = new Scanner(sourceCode);
		int line = 0;
		int cursor = origin;
		String instruction;
		
		while (sc.hasNext()) {
			line++;
			instruction = cleanLine(sc.nextLine());
			if (instruction.length() == 0) {
				continue;
			}
			if (isLabeled(instruction)) {
					String[] splitted = instruction.split(":");
					String label = splitted[0];
					if (splitted.length == 1 || splitted[1].trim().length() == 0) {
						String nearestInstrution;
						while (sc.hasNext()) {
							nearestInstrution = cleanLine(sc.nextLine());
							line++;
							if (nearestInstrution.length() > 0) {
								instruction = nearestInstrution;
								break;
							}
						}
					} else {
						instruction = splitted[1].trim();
					}
					if (instruction.length() == 0) {
						throw new SyntaxErrorException(line);
					}
					
					labels.put(label, cursor);
					
			}
			originalLine.put(cursor, line);
			result.add(instruction);
			cursor += wordSize;
		}
		return result;
	}
	
	private void encodeInstructions(LinkedList<String> sourceCode) throws SyntaxErrorException {
		int cursor = origin;
		for (String instruction : sourceCode) {

			Instruction encoded = encodeInstruction(instruction, originalLine.get(cursor), cursor); 
			instructions.put(cursor, encoded);
			cursor += wordSize;
		}
	}
	
	private Instruction encodeInstruction(String instruction, int line, int cursor) throws SyntaxErrorException{
		String pattern;
		Matcher matcher;
		Pattern pat;
		Instruction result = null;
		//System.out.println(instruction);
		if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *\\$([^ ,]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			if (matcher.find()) {
				result = decodeRInstruction(matcher.group(1), getRegisterNumber(matcher.group(2), line), getRegisterNumber(matcher.group(3), line), getRegisterNumber(matcher.group(4), line), 0, line);
			}
		} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *\\$([^ ,]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			if (matcher.find()) {
				result = decodeIInstruction(matcher.group(1), getRegisterNumber(matcher.group(2), line), getRegisterNumber(matcher.group(4), line), Integer.parseInt(matcher.group(3)), line);
			}
			
		} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *([0-9]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			if (matcher.find()) {
				try {
					result = decodeIInstruction(matcher.group(1), getRegisterNumber(matcher.group(2), line), getRegisterNumber(matcher.group(3), line), Integer.parseInt(matcher.group(4)), line);
				} catch (SyntaxErrorException ex) {
					result = decodeRInstruction(matcher.group(1), getRegisterNumber(matcher.group(2), line), getRegisterNumber(matcher.group(3), line), 0, Integer.parseInt(matcher.group(4)), line);
				}
			}
			
		} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *([^ ]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			if (matcher.find()) {
				if (!labels.containsKey(matcher.group(4))) {
					throw new SyntaxErrorException("Cannot match any label + " + matcher.group(4), line);
				}
				int relativeAddress = (cursor - labels.get(matcher.group(4))) / 4;
				result = decodeIInstruction(matcher.group(1), getRegisterNumber(matcher.group(2), line),  getRegisterNumber(matcher.group(3), line), relativeAddress, line);
			}
			
		} else if (instruction.matches((pattern = "([^ ,]+) *([^ ]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			if (matcher.find()) {
				result = decodeJInstruction(matcher.group(1), matcher.group(2), line);
			}
		} else if (instruction.matches((pattern = "([^ ,]+) *([^ ]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			if (matcher.find()) {
				result = decodeRInstruction(matcher.group(1), 0, getRegisterNumber(matcher.group(2), line), 0, 0, line);
			}	
			
		} else {
			throw new SyntaxErrorException(line);
		}
		return result;
	}
	
	/*
	 * name(6 bits) LABEL (26 bits)
	 */
	private Instruction decodeJInstruction(String name, String label, int line) throws SyntaxErrorException {
		
		if (!types.containsKey(name) || !types.get(name).equals("J")) {
			throw new SyntaxErrorException("Invalid instruction", line);
		}
		if (!labels.containsKey(label)) {
			throw new SyntaxErrorException("No label matches " + label, line);
		}
		Instruction instruction = new Instruction();
		instruction.setJumpAddress(labels.get(label));
		instruction.setOpcode(opcodes.get(name));
		return instruction;
		
	}

	private Instruction decodeRInstruction(String name, int rd, int rs, int rt, int shamt, int line) throws SyntaxErrorException{
		
		if (!types.containsKey(name) || !types.get(name).equals("R")) {
			throw new SyntaxErrorException("Invalid instruction", line);
		}
		Instruction instruction = new Instruction();
		instruction.setOpcode(opcodes.get(name));
		instruction.setRd(rd);
		instruction.setRs(rs);
		instruction.setRt(rt);
		instruction.setShamt(shamt);
		instruction.setFunct(functionCodes.get(name));
		return instruction;
		
	}
	
	private Instruction decodeIInstruction(String name, int rt, int rs, int address, int line) throws SyntaxErrorException {
		
		if (!types.containsKey(name) || !types.get(name).equals("I")) {
			throw new SyntaxErrorException("Invalid instruction", line);
		}
		Instruction instruction = new Instruction();
		instruction.setOpcode(opcodes.get(name));
		instruction.setRt(rt);
		instruction.setRs(rs);
		instruction.setConstant(address);
		
		
		return instruction;
	}
	
	
	private boolean isLabeled(String instuction) {
		Pattern pattern = Pattern.compile("([a-zA-Z0-9:]+):.*");
		Matcher matcher = pattern.matcher(instuction);
		return matcher.matches();
	}
	
	private String cleanLine(String line) {
		String result = line.replaceAll(" +", " ");
		result = trimEnd(result).trim();
		result = result.toLowerCase();
		return result;
	}
	
	private String trimEnd(String str) {
		String result = str;
		if (str.length() > 0 && str.charAt(str.length() - 1) == ' ') {
			result = str.substring(0, str.length() - 1);
		}
		return result;
	}
	
	public void write(){
		pipleLine.setInstruction(currentInstruction.toInt());
	}
	

	
	private int getRegisterNumber(String register, int line) throws SyntaxErrorException {
		int result = 0;
		if (registers.containsKey(register)) {
			result = registers.get(register);
		} else {
			try {
				char prefix = register.charAt(0);
				register = register.substring(1);
				int number = Integer.parseInt(register);
				if (!registerPrefix.containsKey(prefix)) {
					throw new SyntaxErrorException(line);
				}
				result = registerPrefix.get(prefix) + number;
			} catch (Exception ex) {
				throw new SyntaxErrorException("Invlid register name", line);
			}
		}
		return result;
}
	
	private void initTypes() {
		types = new HashMap<String, String>();
		types.put(Constants.ADD, "R");
		types.put(Constants.SUB, "R");
		types.put(Constants.AND, "R");
		types.put(Constants.OR, "R");
		types.put(Constants.NOR, "R");
		types.put(Constants.SLT, "R");
		types.put(Constants.SLTU, "R");
		types.put(Constants.LW, "I");
		types.put(Constants.SW, "I");
		types.put(Constants.ADDI, "I");
		types.put(Constants.ANDI, "I");
		types.put(Constants.ORI, "I");
		types.put(Constants.SLL, "I");
		types.put(Constants.SRL, "I");
		types.put(Constants.BEQ, "I");
		types.put(Constants.BNE, "I");
		types.put(Constants.J, "J");
		types.put(Constants.JAL, "R");
	}
	
	private void initRegisters() {
		registerPrefix = new HashMap<Character, Integer>();
		registers = new HashMap<String, Integer>();
		registerPrefix.put('t', 8);
		registerPrefix.put('v', 6);
		registerPrefix.put('s', 18);
		registerPrefix.put('a', 2);
		registerPrefix.put('k', 26);
		registers.put("zero", 0);
		registers.put("0", 0);
		registers.put("at", 1);
		registers.put("gp", 28);
		registers.put("sp", 29);
		registers.put("fp", 30);
		registers.put("ra", 31);
		
		
	}
	

	private void initCodes() {
		opcodes = new HashMap<String, Integer>();
		functionCodes = new HashMap<String, Integer>();
		
		opcodes.put(Constants.ADD, Constants.ADD_OPCODE);
		opcodes.put(Constants.SUB, Constants.SUB_OPCODE);
		opcodes.put(Constants.AND, Constants.AND_OPCODE);
		opcodes.put(Constants.OR, Constants.OR_OPCODE);
		opcodes.put(Constants.NOR, Constants.NOR_OPCODE);
		opcodes.put(Constants.SLT, Constants.SLT_OPCODE);
		opcodes.put(Constants.SLTU, Constants.SLTU_OPCODE);
		opcodes.put(Constants.LW, Constants.LW_OPCODE);
		opcodes.put(Constants.SW, Constants.SW_OPCODE);
		opcodes.put(Constants.ADDI, Constants.ADDI_OPCODE);
		opcodes.put(Constants.ANDI, Constants.ANDI_OPCODE);
		opcodes.put(Constants.ORI, Constants.ORI_OPCODE);
		opcodes.put(Constants.SLL, Constants.SLL_OPCODE);
		opcodes.put(Constants.SRL, Constants.SRL_OPCODE);
		opcodes.put(Constants.BEQ, Constants.BEQ_OPCODE);
		opcodes.put(Constants.BNE, Constants.BNE_OPCODE);
		opcodes.put(Constants.J, Constants.J_OPCODE);
		opcodes.put(Constants.JAL, Constants.JAL_OPCODE);
		
		functionCodes.put(Constants.ADD, Constants.ADD_FUNC);
		functionCodes.put(Constants.NOR, Constants.NOR_FUNC);
		functionCodes.put(Constants.SLL, Constants.SLL_FUNC);
		functionCodes.put(Constants.SUB, Constants.SUB_FUNC);
		functionCodes.put(Constants.SRL, Constants.SRL_FUNC);
		functionCodes.put(Constants.SLTU, Constants.SLTU_FUNC);
		functionCodes.put(Constants.SLT, Constants.SLT_FUNC);
		functionCodes.put(Constants.JR, Constants.JR_FUNC);
	}

}
