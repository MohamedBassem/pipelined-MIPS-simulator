package eg.edu.guc.mimps.registers;

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

}