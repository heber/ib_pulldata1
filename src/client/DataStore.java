package client;

import java.util.LinkedList;
import java.util.List;

public class DataStore {

	
	private List<Entry> entries;
	private int cutSize;
	
	private int fileCount;
	public DataStore(){
		entries=new LinkedList<Entry>();

		cutSize=(7200/5)*7-1;
		//cutSize=1000;
		fileCount=0;
	}
	
	
	public void addEntry(Entry entry){
		entries.add(entry);
		if (entries.size()>cutSize){
			outputEntries();
		}
	}
	
	private void outputEntries(){
		
		StringBuilder sb=new StringBuilder("");
		for (Entry e : entries){
			sb.append(e.toString());
		}
		String filePath="entries"+fileCount;
		fileCount++;
		FileWrite.write(filePath, sb.toString());
		entries.clear();
	}
	
	public void flush(){
		if (entries.size()==0) return;
		outputEntries();
	}
}
