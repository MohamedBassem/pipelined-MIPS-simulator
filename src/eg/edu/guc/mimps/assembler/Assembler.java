package eg.edu.guc.mimps.assembler;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.mimps.exceptions.SyntaxErrorException;

public class Assembler {
	
	private Map<String, Integer> labels;
	private Map<Integer, Instruction> instructions;
	private Map<Integer, Integer> originalLine;
	private Map<String, String> types;
	private int origin;
	private static final int wordSize = 4;
	
	public Assembler(int origin, Reader sourceCode) {
		labels = new HashMap<String, Integer>();
		instructions = new HashMap<Integer, Instruction>();
		originalLine = new HashMap<Integer, Integer>();
	}
	
	private void initTypes() {
		types = new HashMap<String, String>();
		types.put("add", "R");
		types.put("sub", "R");
		types.put("and", "R");
		types.put("or", "R");
		types.put("nor", "R");
		types.put("slt", "R");
		types.put("sltu", "R");
		types.put("lw", "I");
		types.put("sw", "I");
		types.put("addi", "I");
		types.put("andi", "I");
		types.put("ori", "I");
		types.put("sll", "I");
		types.put("srl", "I");
		types.put("beq", "I");
		types.put("bne", "I");
		types.put("j", "J");
		types.put("jal", "R");
		
	}
	
	public boolean execute(int line) {
		return false;
	}
	
	public void assemble() throws SyntaxErrorException{
		
	}
	
	private void parseSourceCode(Reader sourceCode) {
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
			instruction = cleanLine(sc.nextLine());
			originalLine.put(cursor, line);
			if (isLabeled(instruction)) {
					String label = getLabel(instruction);
					instruction = trimLabel(instruction);
					labels.put(label, cursor);
					
			}
			
			cursor += wordSize;
		}
	}
	
	private Instruction decodeInstruction(String instruction) {
		String pattern;
		Matcher matcher;
		Pattern pat;
		if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *\\$([^ ,]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			decodeRInstruction(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)), 0);
			
		} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *\\$([^ ,]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
			decodeIInstruction(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(3)));;
		
		a} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *([0-9]+)\\( *\\$([^ ]+) *\\)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
		} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *([0-9]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
		} else if (instruction.matches((pattern = "([^ ,]+) *\\$([^ ,]+) *,? *\\$([^ ,]+) *,? *([^ ]+)"))) {
			pat = Pattern.compile(pattern);
			matcher = pat.matcher(instruction);
		}
	}
	
	private boolean decodeRInstruction(String name, int rd, int rs, int rt, int shamt) {
		
		return false;
	}
	
	private boolean decodeIInstruction(String name, int rt, int rs, int address) {
		
		return false;
	}
	
	private String trimLabel(String instruction) {
		return null;
	}
	
	private String getLabel(String instruction) {
		return null;
	}
	
	private boolean isLabeled(String instuction) {
		return false;
	}
	
	private String cleanLine(String line) {
		String result = line.replaceAll(" +", " ");
		result = trimEnd(result).trim();
		return result;
	}
	
	private String trimEnd(String str) {
		String result = str;
		if (str.charAt(str.length() - 1) == ' ') {
			result = str.substring(0, str.length() - 1);
		}
		return result;
	}
	
}
