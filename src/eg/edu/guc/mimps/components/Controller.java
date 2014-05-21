package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.assembler.Instruction;
import eg.edu.guc.mimps.assembler.InstructionFormat;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;

public class Controller implements Executable {
	String rformatControls = "110000010";
	String iformatControls = "0XX100010";
	String jformatControls = ""; 
	String loadControls = "000101011";
	String storeControls = "X0010010X";
	String branchControls = "X0101000X";
	String control;
	
	public  InstructionFetchDecodeRegisters instructionFetchDecodeRegisters; 
	public InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	public Controller(
			InstructionFetchDecodeRegisters instructionFetchDecodeRegisters,
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters) {
		instructionFetchDecodeRegisters=this.instructionFetchDecodeRegisters;
		instructionDecodeExecuteRegisters =this.instructionDecodeExecuteRegisters;
		
	}

	@Override
	public void execute() {
		// InstructionFormat format = instructionFetchDecodeRegisters.getInstruction().format;  
		int opCode=0; 
		String instructionType=toInstructionType(opCode); 
		 this.control = getControlString(instructionType);

	}

	@Override
	public void write() {
		instructionDecodeExecuteRegisters.setRegDest(toBoolean(control.charAt(0)));
		instructionDecodeExecuteRegisters.setAluSrc(toBoolean(control.charAt(3)));
		instructionDecodeExecuteRegisters.setBranch(toBoolean(control.charAt(4)));
		instructionDecodeExecuteRegisters.setMemRead(toBoolean(control.charAt(5)));
		instructionDecodeExecuteRegisters.setMemWrite(toBoolean(control.charAt(6)));
		instructionDecodeExecuteRegisters.setRegWrite(toBoolean(control.charAt(7)));
		instructionDecodeExecuteRegisters.setMemToReg(toBoolean(control.charAt(8)));
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
	public String toInstructionType(int opCode){
	String type="";
	switch(opCode){
	case 1 :type= "rType" ; break;
	case 2: type= "iType"; break; 
	case 3: type= "jType"; break; 
	case 4: type= "branch"; break; 
	case 5: type= "store";break;
	case 6: type= "load"; break; 
	}
	return type;
	}
}
