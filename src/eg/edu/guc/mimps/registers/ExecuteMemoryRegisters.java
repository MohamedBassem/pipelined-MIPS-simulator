package eg.edu.guc.mimps.registers;

public class ExecuteMemoryRegisters {
	private int branchAddress;
	private boolean zero;
	private int ALUResult;
	private int registerValueToMemory;
	private int writeBackRegister;
	
	// Memory Phase Signals
	private boolean memRead;
	private boolean memWrite;
	private boolean branch;
	
	// Write Back Signals
	private boolean regWrite;
	private boolean memToReg;
	
	public int getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(int branchAddress) {
		this.branchAddress = branchAddress;
	}
	public boolean isZero() {
		return zero;
	}
	public void setZero(boolean zero) {
		this.zero = zero;
	}
	public int getALUResult() {
		return ALUResult;
	}
	public void setALUResult(int aLUResult) {
		ALUResult = aLUResult;
	}
	public int getRegisterValueToMemory() {
		return registerValueToMemory;
	}
	public void setRegisterValueToMemory(int registerValueToMemory) {
		this.registerValueToMemory = registerValueToMemory;
	}
	public int getWriteBackRegister() {
		return writeBackRegister;
	}
	public void setWriteBackRegister(int writeBackRegister) {
		this.writeBackRegister = writeBackRegister;
	}
	public boolean isMemRead() {
		return memRead;
	}
	public void setMemRead(boolean memRead) {
		this.memRead = memRead;
	}
	public boolean isMemWrite() {
		return memWrite;
	}
	public void setMemWrite(boolean memWrite) {
		this.memWrite = memWrite;
	}
	public boolean isBranch() {
		return branch;
	}
	public void setBranch(boolean branch) {
		this.branch = branch;
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
	
	public ExecuteMemoryRegisters clone(){
		ExecuteMemoryRegisters clone = new ExecuteMemoryRegisters();
		clone.setALUResult(ALUResult);
		clone.setBranch(branch);
		clone.setBranchAddress(branchAddress);
		clone.setMemRead(memRead);
		clone.setMemToReg(memToReg);
		clone.setMemWrite(memWrite);
		clone.setRegisterValueToMemory(registerValueToMemory);
		clone.setWriteBackRegister(writeBackRegister);
		clone.setZero(zero);
		clone.setRegWrite(regWrite);
		return clone;
	}
		
}
