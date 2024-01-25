package comp304_project3;

import java.util.*;

public class DirectoryTable {


	private DirectoryTableEntry[] list;
	private int size = 32768;
	private int index = 0;
	private DirectoryTableEntry entry;
	private boolean check = true;
	private boolean check2 = false;
	private int position;
	private boolean check3 = false;
	private int lastposition;
	private int nullCount=0;
	private boolean check4 = false;
	private int createFail = 0;
	private int extendFail = 0;
	private int blockSize = 1024;
	//private int size = 32768/blockSize;


	public DirectoryTable() {
		list = new DirectoryTableEntry[size];
	}


	public void createfile(int id, int length) {
		entry = new DirectoryTableEntry();

		if(length%blockSize == 0) {
			length = length / blockSize;
		} else {
			length = (length / blockSize) + 1;
		}


		if(id + length < size) {

			// checks if there is enough space in the desired place
			for(int i = 0; i< length; i++) {
				if(list[i+id] != null) {
					check = false;
				}
			}

			// checks if there is a hole somewhere else
			int count = 0;
			for(int i=0;i<list.length;i++) {
				if(list[i]== null) {
					count++;
				}else {
					count = 0;
				}

				if(count == length) {
					check2 = true;
					position = i - length +1 ;
					break;
				}
			}


			if(check) {
				// if there is enough space
				entry.setStartPosition(id);
				entry.setSize(length);

				for(int i =0; i< length; i++) {
					list[i+id] = entry;
				}
				check2= false;
				System.out.println("File created!");

			} else if(check2 && position+length<size){
				// if there is a hole somewhere else

				//System.out.println(position);

				entry.setStartPosition(position);
				entry.setSize(length);

				for(int i =0; i< length; i++) {
					list[i+position] = entry;
				}

				System.out.println("File created! 2");
				check2 = false;

			} else {
				// defragmentation

				DirectoryTableEntry[] list2 = new DirectoryTableEntry[size];
				int a = 0;
				//starting positionlari degistirmek lazim
				for(int i = 0; i< list.length; i++) {
					if(list[i]!= null) {
						list2[a] = list[i];
						a++;
					}
				}

				if(a+length < size) {
					entry.setStartPosition(a);
					for(int i = a; i< a+length; i++) {
						list2[i] = entry;
					}

					list = list2;

					System.out.println("File created! 3");
				} else {
					createFail++;
					System.out.println("File "+ list[id] + " cannot be created");
				}
			}
			//fileID++;
		} else {
			createFail++;
			System.out.println("File "+ list[id] + " cannot be created");
		}

	}

	public int access(int file_id, int byte_offset) {
		DirectoryTableEntry entry = list[file_id];
		int location = -1;
		byte_offset = byte_offset/blockSize;
		if(entry != null) {
			
			if(entry.getStartPosition()+byte_offset <= entry.getSize()) {
				location = entry.getStartPosition()+byte_offset -1;
				//return location;
			}
			return location;
		}else {
			System.out.println("Cannot access file! Location: " + location);
			return location;
		}

	}

	public void extend(int file_id, int extension) {
		DirectoryTableEntry entry = list[file_id];

		if(entry != null) {
			// returns the last position of the list
			for(int i = list.length-1; i>= 0; i--) {
				if(list[i]!=null) {
					lastposition = i+1;
					break;
				}
			}

			// returns the null count
			if(lastposition+extension>list.length-1) {
				for(int i = lastposition-1; i>= 0; i--) {
					if(list[i]==null) {
						nullCount++;
					}
				}
				// checks if there is enough space to extend
				if(list.length-lastposition+nullCount>extension) {
					check3=true;
				}
			}

			// adds the block after the last block of the file
			if(lastposition+extension<list.length ) {
				for(int i=lastposition;i<lastposition+extension;i++) {
					list[i] = entry;
				}
				entry.setSize(entry.getSize()+extension);
				System.out.println("File "+ list[file_id] + " extended");

			} else if(check3) {
				// defragmentation
				DirectoryTableEntry[] list2 = new DirectoryTableEntry[size];
				int a = 0;
				//starting positionlari degistirmek lazim
				for(int i = 0; i< list.length; i++) {
					if(list[i]!= null) {
						list2[a] = list[i];
						a++;
					}
				}
				list = list2;

				// returns the last position of the list
				for(int i = list.length-1; i>= 0; i--) {
					if(list[i]!=null) {
						lastposition = i+1;
						break;
					}
				}

				// extends if there is enough space
				if(lastposition+extension<list.length ) {
					for(int i=lastposition;i<lastposition+extension;i++) {
						list[i] = entry;
					}
					entry.setSize(entry.getSize()+extension);

				}

				System.out.println("File "+ list[file_id] + " extended");
			}
		}else {
			extendFail++;

			System.out.println("File "+ list[file_id] + " cannot be extended");
		}
	}

	public void shrink(int file_id, int shrinking) {
		DirectoryTableEntry entry = list[file_id];
		if(entry!= null) {
			// checks if there is a single block or a defragmentation
			for(int i = entry.getStartPosition(); i< entry.getStartPosition() + entry.getSize(); i++) {
				//System.out.println("list[i]: " + list[i]);
				//System.out.println("list[i+1]: " + list[i+1]);
				if(list[i] == list[i+1] || (list[entry.getStartPosition() + entry.getSize()-1 ]== entry && list[entry.getStartPosition() + entry.getSize()]== null)) {
					check4 = true;
				}else {
					check4 = false;
					break;
				}
			}

			//System.out.println("entry.getStartPosition(): " + entry.getStartPosition());
			//System.out.println("entry.getSize()): " + entry.getSize());
			//System.out.println(check4);

			// if there is a single block
			if(check4 && entry.getSize() >= shrinking) {
				int entryEndPos = entry.getStartPosition() + entry.getSize();
				if(shrinking <= entry.getSize()) {
					for(int i=0;i< shrinking+1;i++) {
						list[entryEndPos-i] = null;
					}
					entry.setSize(entry.getSize()-shrinking);

					System.out.println("File Shrinked! 1");
				} 

			}else if(!check4 && entry.getSize() >= shrinking ) {
				// if the list is defragmentated
				for(int i = list.length-1; i>=0; i--) {
					if(list[i] == entry && shrinking != 0) {
						list[i] = null;
						shrinking--;
					}
				}

				entry.setSize(entry.getSize()-shrinking);
				System.out.println("File Shrinked! 2");
			} 
		}else{
			System.out.println("File "+ list[file_id] + " cannot be shrinked");
		}
	}

	public int getCreateFail() {
		return createFail;
	}


	public int getExtendFail() {
		return extendFail;
	}


	public DirectoryTableEntry[] getList() {
		return list;
	}


	public int getSize() {
		return blockSize;
	}


	public void setSize(int blockSize) {
		this.blockSize = blockSize;
	}

	//public int getFileID() {
	//	return fileID;
	//}



}
