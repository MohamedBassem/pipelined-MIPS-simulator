package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;

public class Controller implements Executable {
	String rformatControls = "110000010";
	String iformatControls = "";
	String loadControls = "000101011";
	String storeControls = "X0010010X";
	String branchControls = "X0101000X";

	@Override
	public void execute() {
		// setControlSignals(input);

	}

	@Override
	public void write() {
		// TODO Auto-generated method stub

	}

	public void setControlSignals(String instructionType) {
		InstructionDecodeExecuteRegisters idRegisters = new InstructionDecodeExecuteRegisters();
		String control = getContolString();
		idRegisters.setRegDest(toBoolean(control.charAt(0)));
		idRegisters.setAluSrc(toBoolean(control.charAt(3)));
		idRegisters.setBranch(toBoolean(control.charAt(4)));
		idRegisters.setMemRead(toBoolean(control.charAt(5)));
		idRegisters.setMemWrite(toBoolean(control.charAt(6)));
		idRegisters.setRegWrite(toBoolean(control.charAt(7)));
		idRegisters.setMemToReg(toBoolean(control.charAt(8)));
	}

	public boolean toBoolean(char value) {
		if (value == '1') {
			return true;
		}
		return false;
	}

	public String getControlString(String instructionType) {
		switch (instructionType) {

		case "rType":
			return rformatControls;

		case "iType":
			return iformatControls;

		case "branch":
			return branchControls;

		case "load":
			return loadControls;

		case "store":
			return storeControls;

		}
	}
}
