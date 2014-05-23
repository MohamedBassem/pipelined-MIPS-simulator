package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.utils.Constants;

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
		int instructionNumber = instructionFetchDecodeRegisters
				.getInstruction();
		Instruction instruction = new Instruction(instructionNumber);
		this.toInstructionType(instruction.getOpcode());

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



	public void toInstructionType(int opCode) {
		switch (opCode) {
		case Constants.ADD_OPCODE:
			control =rformatControls; 
			break;
		case Constants.ADDI_OPCODE:
		case Constants.ORI_OPCODE:
		case Constants.ANDI_OPCODE:
			control=iformatControls; 
			break;
		case Constants.SW_OPCODE:
			control = storeControls; 
			break;
		case Constants.LW_OPCODE:
			control = loadControls; 
			break;
		case Constants.BEQ_OPCODE:
		case Constants.BNE_OPCODE:
			control = branchControls;
			break;
		case Constants.J_OPCODE:
		case Constants.JAL_OPCODE:
			control =jformatControls; 
			break;

		}
	}
}