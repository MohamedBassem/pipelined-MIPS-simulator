package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;

public class Controller implements Executable {
	String rformatControls = "10000102";
	String iformatControls = "01000102";
	String jformatControls = "XXXXXXX3";
	String loadControls = "01010110";
	String storeControls = "X10010X0";
	String branchControls = "X01000X1";
	String control;

	public InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	public InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;

	public Controller(
			InstructionFetchDecodeRegisters instructionFetchDecodeRegisters,
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters) {
		this.instructionFetchDecodeRegisters = instructionFetchDecodeRegisters;
		this.instructionDecodeExecuteRegisters = instructionDecodeExecuteRegisters;

	}

	@Override
	public void execute() {
		Instruction instruction = instructionFetchDecodeRegisters
				.getInstruction();
		String instructionType = this.toInstructionType(instruction.getOpcode());
		this.control = getControlString(instructionType);

	}

	@Override
	public void write() {
		instructionDecodeExecuteRegisters.setRegDest(toBoolean(control
				.charAt(0)));
		instructionDecodeExecuteRegisters
				.setAluSrc(toBoolean(control.charAt(1)));
		instructionDecodeExecuteRegisters
				.setBranch(toBoolean(control.charAt(2)));
		instructionDecodeExecuteRegisters.setMemRead(toBoolean(control
				.charAt(3)));
		instructionDecodeExecuteRegisters.setMemWrite(toBoolean(control
				.charAt(4)));
		instructionDecodeExecuteRegisters.setRegWrite(toBoolean(control
				.charAt(5)));
		instructionDecodeExecuteRegisters.setMemToReg(toBoolean(control
				.charAt(6)));
		instructionDecodeExecuteRegisters.setAluOpt(Integer.parseInt(""
				+ control.charAt(7)));
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

		case "jType":
			return jformatControls;

		}
		return instructionType;
	}

	public String toInstructionType(int opCode) {
		String type = "";
		switch (opCode) {
		// will change case depending on opCodes
		case 0:
			type = "rType";
			break;
		case 1:
		case 4:
		case 5:
			type = "iType";
			break;
		case 3:
			type = "store";
			break;
		case 2:
			type = "load";
			break;
		case 6:
		case 7:
			type = "branch";
			break;
		case 9:
		case 10:
		case 11:
			type = "jType";
			break;

		}
		return type;
	}
}
