package comp304_project3;

import java.util.*;

public class FileAllocationTable {


	private FileAllocationTableEntry[] list;
	private int size = 32768;
	private FileAllocationTableEntry entry;
	private int lastposition;
	private int nullCount;
	private int createFail = 0;
	private int extendFail = 0;
	private int blockSize = 1024;
	//private int size = 32768/blockSize;


	public FileAllocationTable() {
		list = new FileAllocationTableEntry[size];
	}


	public void createfile(int id, int length) {		

		if(length%blockSize == 0) {
			length = length / blockSize;
		} else {
			length = (length / blockSize) + 1;
		}

		if(getBlockCount()+length<list.length) {

			for(int i = 0; i< length; i++) {
				// creates elements of count length
				if(list[id]==null) {
					entry = new FileAllocationTableEntry(id,nextEmptySpace());
					list[id] = entry;
				} else if (i != length -1){
					// new file_id and next pointer is added to the element
					id=emptySpace();
					int next =  nextEmptySpace();
					entry = new FileAllocationTableEntry(id,next);
					list[id] = entry;
				} else if(i == length -1) {
					// last element's next pointer is -1
					id=emptySpace();
					entry = new FileAllocationTableEntry(id,-1);
					list[id] = entry;
				}
				//System.out.println("blockid " + entry.getBlockID());
				//System.out.println("nextBlock " + entry.getNextBlock());

			}
			System.out.println("File created!");
			//fileID++;
		} else {
			createFail++;
			System.out.println("File "+ list[id] + " cannot be created");
		}

	}

	public int access(int file_id, int byte_offset) {
		FileAllocationTableEntry entry = list[file_id];
		int location = -1;
		// if file length is bigger than the offset finds the next file for offset count
		byte_offset = byte_offset/blockSize;
		if(entry != null) {
			if(fileLength(file_id) > byte_offset) {
				for(int i = 0; i< byte_offset; i++) {
					entry = getNext(file_id);
					file_id = entry.getBlockID();
				}
				location = entry.getBlockID();
				//System.out.println(location);
			}
			return location;
		} else {
			//System.out.println(location);
			System.out.println("Cannot access file! Location: " + location);
			return location;
		}
	}

	public void extend(int file_id, int extension) {

		nullCount =0;

		// returns the null count
		for(int i = 0; i< list.length - 1; i++) {
			if(list[i]== null) {
				nullCount++;
			}
		}

		// if there is enough space for extension
		if(extension <= nullCount) {
			if(extension != 0) {
				if(list[file_id] != null) {

					while(getNext(file_id) != null) {
						//System.out.println(file_id);
						//System.out.println(list[file_id]);
						//System.out.println(list[file_id].getBlockID());
						//System.out.println(list[file_id].getNextBlock());
						file_id =  list[file_id].getNextBlock();

					}
				}
				// extends the file
				int id=emptySpace();
				int next;
				list[file_id].setNextBlock(id);
				for(int i=1;i< extension; i++) {
					id=emptySpace();
					next =  nextEmptySpace();
					entry = new FileAllocationTableEntry(id,next);
					list[id] = entry;

					//	System.out.println("File id: " + list[id].getBlockID());
					//	System.out.println("File next: " + list[id].getNextBlock());
					//	System.out.println("File: " + list[id]);
				}

				// makes the last element's next pointer to -1
				id=emptySpace();
				entry = new FileAllocationTableEntry(id,-1);
				list[id] = entry;

				//System.out.println("File id: " + list[id].getBlockID());
				//System.out.println("File next: " + list[id].getNextBlock());
				//System.out.println("File: " + list[id]);


			}
			nullCount = 0;
			System.out.println("File "+ list[file_id] + " extended");
		} else {
			extendFail++;
			System.out.println("File "+ list[file_id] + " cannot be extended");
		}

	}


	public void shrink(int file_id, int shrinking) {


		FileAllocationTableEntry list2[] = new FileAllocationTableEntry[fileLength(file_id)];
		int a = 0;

		// if there is enough length for shrinking
		//System.out.println("aa " + fileLength(file_id));
		//System.out.println("bb " +shrinking);
		if(fileLength(file_id) >= shrinking) {

			// finds the last element of the file
			while(getNext(file_id) != null) {
				list2[a] = list[file_id];
				a++;
				file_id =  list[file_id].getNextBlock();


				//	System.out.println("File id: " + list[file_id].getBlockID());
				//	System.out.println("File next: " + list[file_id].getNextBlock());
				//	System.out.println("File: " + list[file_id]);
			}

			list2[a] = list[file_id];
			a++;
			//System.out.println("File id: " + list[file_id].getBlockID());
			//System.out.println("File next: " + list[file_id].getNextBlock());
			//System.out.println("File: " + list[file_id]);

			// sets the desired amount of blocks' ids to -1
			for(int i = list2.length-1; i>=list2.length -shrinking; i--) {
				list2[i].setBlockID(-1);
				list2[i].setNextBlock(-1);
			}

			// if the block id is -1 shrink it
			for(int i=0; i< list.length; i++) {
				if(list[i] != null) {
					if(list[i].getBlockID() == -1) {
						list[i] = null;
					}
				}
			}


			System.out.println("File Shrinked!");
		} else {
			System.out.println("File "+ list[file_id] + " cannot be shrinked");
		}

	}

	// returns the number of blocks
	public int getBlockCount() {
		int blockCount = 0;
		for(int i=0; i< list.length; i++) {
			if(list[i]!= null) {
				blockCount++;
			}
		}
		return blockCount;	
	}

	// returns the first empty space
	public int emptySpace() {
		int space = -1;
		for(int i = 0; i< list.length; i++) {
			if(list[i] == null) {
				space = i;
				return space;
			}
		}
		return space;
	}

	// returns the next empty space
	public int nextEmptySpace() {
		int space = -1;
		for(int i = 0; i< list.length; i++) {
			if(list[i] == null) {
				for(int j = i+1; j< list.length; j++) {
					if(list[j] == null) {
						space = j;
						return space;
					}
				}
			}
		}
		return space;
	}


	// returns the file length of a file
	public int fileLength(int blockID) {
		if(list[blockID] != null) {
			int len = 1;
			while(getNext(blockID) != null) {
				len++;
				blockID =  list[blockID].getNextBlock();
			}
			return len;
		} else {
			return 0;
		}
	}

	// returns the next entry
	public FileAllocationTableEntry getNext(int blockID) {
		FileAllocationTableEntry next;
		if(hasNext(blockID)) {
			next = list[list[blockID].getNextBlock()];
			return next;
		} else {
			return null;
		}
	}

	// if the entry has next blocks pointed to it
	public boolean hasNext(int blockID) {
		if(list[blockID].getNextBlock() != -1)
			return true;
		else
			return false;
	}


	public int getCreateFail() {
		return createFail;
	}


	public int getExtendFail() {
		return extendFail;
	}

	public FileAllocationTableEntry[] getList() {
		return list;
	}


	public int getSize() {
		return blockSize;
	}


	public void setSize(int size) {
		this.blockSize = size;
	}


	//public int getFileID() {
	//	return fileID;
	//}


}
