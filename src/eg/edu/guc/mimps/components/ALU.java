package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.utils.BinaryManiplator;
import eg.edu.guc.mimps.utils.Constants;

public class ALU implements Executable {

	InstructionDecodeExecuteRegisters decodeExecuteRegister;
	ExecuteMemoryRegisters executeMemoryRegister;
	ExecuteMemoryRegisters newExecuteMemoryRegister;
	
	private final static int AND  = 0;
	private final static int OR   = 1;
	private final static int ADD  = 2;
	private final static int SUB  = 6;
	private final static int SLT  = 7;
	
	private final static int SLL  = 3;
	private final static int SRL  = 4;
	private final static int NOR  = 5;
	private final static int SLTU = 8;
	
	public ALU(
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters,
			ExecuteMemoryRegisters executeMemoryRegisters) {
			decodeExecuteRegister = instructionDecodeExecuteRegisters;
			executeMemoryRegister = executeMemoryRegisters;
	}


	public void execute() {
		int aluOpt = decodeExecuteRegister.getAluOpt();
		int functionCode = BinaryManiplator.getUnsignedPartialValue(decodeExecuteRegister.getSignExtendedOffset(), 0, 5);
		
		int aluOperation = 0;
		
		switch(aluOpt){
			// Lw or Sw
			case 0: aluOperation = ADD; break;
			// Beq
			case 1: aluOperation = SUB; break;
			// R-Instruction
			case 2: 
				switch(functionCode){
					case Constants.ADD_FUNC  : aluOperation = ADD;break;
					case Constants.OR_FUNC   : aluOperation = OR;break;
					case Constants.AND_FUNC  : aluOperation = AND;break;
					case Constants.NOR_FUNC  : aluOperation = NOR;break;
					case Constants.SUB_FUNC  : aluOperation = SUB;break;
					case Constants.SLL_FUNC  : aluOperation = SLL;break;
					case Constants.SRL_FUNC  : aluOperation = SRL;break;
					case Constants.SLT_FUNC  : aluOperation = SLT;break;
					case Constants.SLTU_FUNC : aluOperation = SLTU;break;
				}
				break;
			// Jump
			case 3: // DO NOTHING
				break;
			// BNE
			case 4: aluOperation = SUB; break;
			// ADDI
			case 5: aluOperation = ADD; break;
			// ANDI
			case 6: aluOperation = AND; break;
			// ORI
			case 7: aluOperation = OR; break;
		}
		
		int first = decodeExecuteRegister.getRegister1Value();
		int second;
		if(decodeExecuteRegister.isAluSrc()){
			second = decodeExecuteRegister.getSignExtendedOffset();
		}else{
			second = decodeExecuteRegister.getRegister2Value();
		}
		int shamt = decodeExecuteRegister.getShamt();
		int aluResult = 0;
		switch(aluOperation){
			case ADD : aluResult = add(first, second);break;
			case SUB : aluResult = sub(first, second);break;
			case AND : aluResult = and(first, second);break;
			case OR  : aluResult = or(first, second);break;
			case NOR : aluResult = nor(first, second);break;
			case SLL : aluResult = shiftLeftLogical(first, shamt);break;
			case SRL : aluResult = shiftRightLogical(first, shamt);break;
			case SLT : aluResult = setLessThan(first, second);break;
			case SLTU: aluResult = setLessThanUnsigned(first, second);break;
		}

		boolean zero = false;
		if(aluOpt == 1){
			zero = (aluResult == 0);
		}else if(aluOpt == 4){
			zero = (aluResult != 0);
		}
		newExecuteMemoryRegister = executeMemoryRegister.clone();
		
		newExecuteMemoryRegister.setZero(zero);
		newExecuteMemoryRegister.setBranchAddress(decodeExecuteRegister.getIncrementedPc() + decodeExecuteRegister.getSignExtendedOffset()*4);
		newExecuteMemoryRegister.setRegisterValueToMemory(decodeExecuteRegister.getRegister2Value());
		if(decodeExecuteRegister.isRegDest()){
			newExecuteMemoryRegister.setWriteBackRegister(decodeExecuteRegister.getRd());
		}else{
			newExecuteMemoryRegister.setWriteBackRegister(decodeExecuteRegister.getRt());
		}
		newExecuteMemoryRegister.setALUResult(aluResult);
		newExecuteMemoryRegister.setMemRead(decodeExecuteRegister.isMemRead());
		newExecuteMemoryRegister.setMemWrite(decodeExecuteRegister.isMemWrite());
		newExecuteMemoryRegister.setBranch(decodeExecuteRegister.isBranch());
		newExecuteMemoryRegister.setRegWrite(decodeExecuteRegister.isRegWrite());
		newExecuteMemoryRegister.setMemToReg(decodeExecuteRegister.isMemToReg());
		
	}

	
	public void write() {
		executeMemoryRegister.replace(newExecuteMemoryRegister);
	}
	
	private int add(int first, int second) {
		return (first + second);
	}
	
	private int sub(int first, int second) {
		return (first - second);
	}
	
	private int or(int first, int second) {
		return (first | second);
	}
	
	private int nor(int first, int second) {
		return ~(first | second);
	}
	
	private int and(int first, int second) {
		return (first & second);
	}
	
	private int setLessThan(int first, int second) {
		if(first < second)
			return 1;
		return 0;
	}
	
	private int setLessThanUnsigned(int first, int second) {
		for(int i = 31; i >= 0; i--) {
			boolean bitOfFiirst = BinaryManiplator.getBitByIndex(first, i);
			boolean bitOfSecond = BinaryManiplator.getBitByIndex(second, i);
			if(!bitOfFiirst && bitOfSecond)
				return 1;
			if(bitOfFiirst && !bitOfSecond)
				return 0;
		}
		
		return 0;
	}
	
	private int shiftLeftLogical(int first, int shamt) {
		return first << shamt;
	}
	
	private int shiftRightLogical(int first, int shamt) {
		return first >>> shamt;
	}

}
