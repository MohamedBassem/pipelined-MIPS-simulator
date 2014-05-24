package eg.edu.guc.mimps.registers;

import java.lang.reflect.Field;

public class InstructionDecodeExecuteRegisters {
	private int incrementedPc;
	private int register1Value;
	private int register2Value;
	private int signExtendedOffset;
	private int rt;
	private int rd;
	private int shamt;

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
	public int getShamt() {
		return shamt;
	}
	public void setShamt(int shamt) {
		this.shamt = shamt;
	}
	
	
	public InstructionDecodeExecuteRegisters clone(){
		InstructionDecodeExecuteRegisters clone = new InstructionDecodeExecuteRegisters();
		clone.setAluOpt(aluOpt);
		clone.setAluSrc(aluSrc);
		clone.setBranch(branch);
		clone.setIncrementedPc(incrementedPc);
		clone.setMemRead(memRead);
		clone.setMemToReg(memToReg);
		clone.setMemWrite(memWrite);
		clone.setRd(rd);
		clone.setRegDest(regDest);
		clone.setRegister1Value(register1Value);
		clone.setRegister2Value(register2Value);
		clone.setRegWrite(regWrite);
		clone.setRt(rt);
		clone.setSignExtendedOffset(signExtendedOffset);
		clone.setShamt(shamt);
		return clone;
	}
	
	public void replaceRegister(InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters){
		this.setIncrementedPc(instructionDecodeExecuteRegisters.getIncrementedPc());
		this.setRd(instructionDecodeExecuteRegisters.getRd());
		this.setRt(instructionDecodeExecuteRegisters.getRt());
		this.setRegister1Value(instructionDecodeExecuteRegisters.getRegister1Value());
		this.setRegister2Value(instructionDecodeExecuteRegisters.getRegister2Value());
		this.setSignExtendedOffset(instructionDecodeExecuteRegisters.getSignExtendedOffset());
		this.setShamt(instructionDecodeExecuteRegisters.getShamt());
	}
	
	public void replaceControll(InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters){
		this.setAluOpt(instructionDecodeExecuteRegisters.getAluOpt());
		this.setAluSrc(instructionDecodeExecuteRegisters.isAluSrc());
		this.setBranch(instructionDecodeExecuteRegisters.isBranch());
		this.setMemRead(instructionDecodeExecuteRegisters.isMemRead());
		this.setMemToReg(instructionDecodeExecuteRegisters.isMemToReg());
		this.setMemWrite(instructionDecodeExecuteRegisters.isMemWrite());
		this.setRegDest(instructionDecodeExecuteRegisters.isRegDest());
		this.setRegWrite(instructionDecodeExecuteRegisters.isRegWrite());
	}
	
	public String toString() {
		  StringBuilder result = new StringBuilder();
		  String newLine = System.getProperty("line.separator");

		  result.append( this.getClass().getName() );
		  result.append( " Object {" );
		  result.append(newLine);

		  //determine fields declared in this class only (no fields of superclass)
		  Field[] fields = this.getClass().getDeclaredFields();

		  //print field names paired with their values
		  for ( Field field : fields  ) {
		    result.append("  ");
		    try {
		      result.append( field.getName() );
		      result.append(": ");
		      //requires access to private field:
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(newLine);
		  }
		  result.append("}");

		  return result.toString();
		}
	

}
