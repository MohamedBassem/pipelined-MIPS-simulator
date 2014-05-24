package eg.edu.guc.mips.registers;

import java.lang.reflect.Field;

public class Registers {
	private int[] registers;
	
	public Registers() {
		registers = new int[32];
	}
	
	public int getReg(int reg) {
		return registers[reg];
	}
	
	public void setReg(int reg, int data) {
		registers[reg] = data;
	}
	
	public String toString() {
		String ret = "{\n";
		 for(int i=0;i<31;i++)
			 ret += "\t" + i + " : " + registers[i] + "\n";
		ret += "}";
		return ret;
	}

}