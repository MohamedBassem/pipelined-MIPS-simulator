package eg.edu.guc.mimps.registers;

public class MemoryWritebackRegisters {
	private int ALUResult;
	private int memoryWord;
	private int writeBackRegister;
	
	// Write Back Signals
	private boolean regWrite;
	private boolean memToReg;
	
	
	public int getALUResult() {
		return ALUResult;
	}
	public void setALUResult(int aLUResult) {
		ALUResult = aLUResult;
	}
	public int getMemoryWord() {
		return memoryWord;
	}
	public void setMemoryWord(int memoryWord) {
		this.memoryWord = memoryWord;
	}
	public int getWriteBackRegister() {
		return writeBackRegister;
	}
	public void setWriteBackRegister(int writeBackRegister) {
		this.writeBackRegister = writeBackRegister;
	}
	public boolean isRegWrite() {
		return regWrite;
	}
	public void setRegWrite(boolean regWrite) {
		this.regWrite = regWrite;
	}
	public boolean isMemToReg() {
		return memToReg;
	}
	public void setMemToReg(boolean memToReg) {
		this.memToReg = memToReg;
	}
	
	public MemoryWritebackRegisters clone(){
		MemoryWritebackRegisters clone = new MemoryWritebackRegisters();
		clone.setALUResult(ALUResult);
		clone.setMemoryWord(memoryWord);
		clone.setMemToReg(memToReg);
		clone.setRegWrite(regWrite);
		clone.setWriteBackRegister(writeBackRegister);
		return clone;
	}
	
	public void replace(MemoryWritebackRegisters memoryWritebackRegisters){
		this.setALUResult(memoryWritebackRegisters.getALUResult());
		this.setMemoryWord(memoryWritebackRegisters.getMemoryWord());
		this.setMemToReg(memoryWritebackRegisters.isMemToReg());
		this.setRegWrite(memoryWritebackRegisters.isRegWrite());
		this.setWriteBackRegister(memoryWritebackRegisters.getWriteBackRegister());
	}
}
