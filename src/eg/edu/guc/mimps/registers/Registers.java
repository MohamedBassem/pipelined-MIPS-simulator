package eg.edu.guc.mimps.registers;

public class Registers {
	private int[] registers;

	public Registers() {
		registers = new int[32];
	}

	private int getRegister(int reg) {
		return registers[reg];
	}

	private void setRegister(int reg, int data) {
		registers[reg] = data;
	}

}