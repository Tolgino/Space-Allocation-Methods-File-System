package comp304_project3;

public class FileAllocationTableEntry {

	private int blockID = 0;
	private int nextBlock = -1;


	public FileAllocationTableEntry(int blockID, int nextBlock) {
		this.blockID = blockID;
		this.nextBlock = nextBlock;
	}
	/*
	public FileAllocationTableEntry(int blockID) {
		this.blockID = blockID;
		nextBlock = -1;
	}*/
	
	public FileAllocationTableEntry() {
		
	}

	public int getBlockID() {
		return blockID;
	}


	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}


	public int getNextBlock() {
		return nextBlock;
	}


	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}



}
