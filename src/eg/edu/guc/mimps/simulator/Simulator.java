package eg.edu.guc.mimps.simulator;

import java.io.StringReader;

import eg.edu.guc.mimps.assembler.Assembler;
import eg.edu.guc.mimps.components.ALU;
import eg.edu.guc.mimps.components.Controller;
import eg.edu.guc.mimps.components.DataMemory;
import eg.edu.guc.mimps.components.RegisterFile;
import eg.edu.guc.mimps.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.registers.MemoryWritebackRegisters;

public class Simulator {
	
	InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	ExecuteMemoryRegisters executeMemoryRegisters;
	MemoryWritebackRegisters memoryWritebackRegisters;
	
	Assembler assembler;
	Controller controller;
	RegisterFile registerFile;
	ALU alu;
	DataMemory dataMemory;
	
	int pc;
	
	public Simulator(){
		this.reset();
		//new GUI(this);
	}
	
	public void reset(){
		instructionFetchDecodeRegisters = new InstructionFetchDecodeRegisters();
		instructionDecodeExecuteRegisters = new InstructionDecodeExecuteRegisters();
		executeMemoryRegisters = new ExecuteMemoryRegisters();
		memoryWritebackRegisters = new MemoryWritebackRegisters();
		
		controller = new Controller(instructionFetchDecodeRegisters,instructionDecodeExecuteRegisters);
		registerFile = new RegisterFile(instructionFetchDecodeRegisters,instructionDecodeExecuteRegisters,memoryWritebackRegisters);
		alu = new ALU(instructionDecodeExecuteRegisters,executeMemoryRegisters);
		dataMemory = new DataMemory(executeMemoryRegisters,memoryWritebackRegisters);
	}
	
	public void run(int origin,String data){
		assembler = new Assembler(origin,new StringReader(data),instructionFetchDecodeRegisters);
		pc = origin;
		while(assembler.execute(pc)){
			registerFile.execute();
			controller.execute();
			alu.execute();
			dataMemory.execute();
			
			assembler.write();
			registerFile.write();
			controller.write();
			alu.write();
			dataMemory.write();
			
			if(executeMemoryRegisters.isBranch() && !executeMemoryRegisters.isZero()){
				pc = executeMemoryRegisters.getBranchAddress();
			}else{
				pc += 4;
			}
		}
	}
	
	public static void main(String[] args) {
		new Simulator();
	}
}
