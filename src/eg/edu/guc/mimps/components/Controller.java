package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.utils.Constants;

public class Controller implements Executable {
	private InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	private InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	private String rformatControls = "1000010";
	private String iformatControls = "0100010";
	private String jformatControls = "XXXXXXX";
	private String loadControls = "0101011";
	private String storeControls = "X10010X";
	private String branchControls = "X01000X";
	private String control;
	private int aluOp;

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
		aluOp = this.toInstructionType(instruction.getOpcode());

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
		instructionDecodeExecuteRegisters.setAluOpt(aluOp);
	}

	public boolean toBoolean(char value) {
		if (value == '1') {
			return true;
		}
		return false;
	}

	public int toInstructionType(int opCode) {
		switch (opCode) {
		case Constants.ADD_OPCODE:
			control = rformatControls;
			aluOp = 2;
			break;
		case Constants.ADDI_OPCODE:
			control = iformatControls;
			aluOp = 5;
			break;
		case Constants.ORI_OPCODE:
			control = iformatControls;
			aluOp = 7;
			break;
		case Constants.ANDI_OPCODE:
			control = iformatControls;
			aluOp = 6;
			break;
		case Constants.SW_OPCODE:
			control = storeControls;
			aluOp = 0;
			break;
		case Constants.LW_OPCODE:
			control = loadControls;
			aluOp = 0;
			break;
		case Constants.BEQ_OPCODE:
			control = branchControls;
			aluOp = 1;
			break;
		case Constants.BNE_OPCODE:
			control = branchControls;
			aluOp = 4;
			break;
		case Constants.J_OPCODE:
		case Constants.JAL_OPCODE:
			control = jformatControls;
			aluOp = 3;
			break;
		}
		return aluOp;
	}
}