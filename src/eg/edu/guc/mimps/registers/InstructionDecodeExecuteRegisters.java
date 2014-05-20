package eg.edu.guc.mimps.registers;

public class InstructionDecodeExecuteRegisters {
	private int incrementedPc;
	private int register1Value;
	private int register2Value;
	private int signExtendedOffset;
	private int rt;
	private int rd;
	
	// Execute Phase Signals
	private boolean aluSrc;
	private boolean regDest;
	private int aluOpt;
	
	// Memory Phase Signals
	private boolean memRead;
	private boolean memWrite;
	private boolean branch;
	
	// Write Back Signals
	private boolean regWrite;
	private boolean memToReg;
	
	public boolean isAluSrc() {
		return aluSrc;
	}
	public void setAluSrc(boolean aluSrc) {
		this.aluSrc = aluSrc;
	}
	public boolean isRegDest() {
		return regDest;
	}
	public void setRegDest(boolean regDest) {
		this.regDest = regDest;
	}
	public int getAluOpt() {
		return aluOpt;
	}
	public void setAluOpt(int aluOpt) {
		this.aluOpt = aluOpt;
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
	
	public int getIncrementedPc() {
		return incrementedPc;
	}
	public void setIncrementedPc(int incrementedPc) {
		this.incrementedPc = incrementedPc;
	}
	public int getRegister1Value() {
		return register1Value;
	}
	public void setRegister1Value(int register1Value) {
		this.register1Value = register1Value;
	}
	public int getRegister2Value() {
		return register2Value;
	}
	public void setRegister2Value(int register2Value) {
		this.register2Value = register2Value;
	}
	public int getSignExtendedOffset() {
		return signExtendedOffset;
	}
	public void setSignExtendedOffset(int signExtendedOffset) {
		this.signExtendedOffset = signExtendedOffset;
	}
	public int getRt() {
		return rt;
	}
	public void setRt(int rt) {
		this.rt = rt;
	}
	public int getRd() {
		return rd;
	}
	public void setRd(int rd) {
		this.rd = rd;
	}


}