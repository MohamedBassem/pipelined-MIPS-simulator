package eg.edu.guc.mimps.components;

import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.registers.MemoryWritebackRegisters;
import eg.edu.guc.mimps.registers.Registers;

public class RegisterFile implements Executable {

	InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	InstructionDecodeExecuteRegisters newIDER;
	MemoryWritebackRegisters memoryWritebackRegisters;
	Registers registers;

	public RegisterFile(
			InstructionFetchDecodeRegisters instructionFetchDecodeRegisters,
			InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters,
			MemoryWritebackRegisters memoryWritebackRegisters,
			Registers registers) {
		this.instructionFetchDecodeRegisters = instructionFetchDecodeRegisters;
		this.memoryWritebackRegisters = memoryWritebackRegisters;
		this.instructionDecodeExecuteRegisters = instructionDecodeExecuteRegisters;
		this.registers = registers; 
	}
	
	public static void main(String[]ar) {
		System.out.println(1<<31);
		String y = Integer.toBinaryString(signExtend((int)Math.pow(2, 4)));
		System.out.println(y);
	}
	
	private static int signExtend(int val) {
		int mostSigBit = val & (1 << 15);
		System.out.println(Integer.toBinaryString(val));
		System.out.println("most " + mostSigBit);
		if(mostSigBit != 0)
			for(int i=16;i<=31;i++) {
				System.out.println(Integer.toBinaryString(val));
				val |= (1 << i);
			}
		return val;
	}
	
	@Override
	public void execute() {
		newIDER = instructionDecodeExecuteRegisters.clone();
//		TODO get rs
		int rs = 0;
//		TODO get data
		int data = 0;
		int rd = instructionDecodeExecuteRegisters.getRd();
		int rt = instructionDecodeExecuteRegisters.getRt();
		boolean regDest = instructionDecodeExecuteRegisters.isRegDest();
		
		if(newIDER.isRegWrite()) {
			if(regDest)
				registers.setReg(rd, data);
			else
				registers.setReg(rt, data);
		}else {
			newIDER.setRegister1Value(registers.getReg(rs));
			if(regDest)
				newIDER.setRegister2Value(registers.getReg(rt));
		}
		int sign = instructionDecodeExecuteRegisters.getSignExtendedOffset();
		newIDER.setSignExtendedOffset(signExtend(sign));
	}

	@Override
	public void write() {
		instructionDecodeExecuteRegisters.replaceRegister(newIDER);
	}

}