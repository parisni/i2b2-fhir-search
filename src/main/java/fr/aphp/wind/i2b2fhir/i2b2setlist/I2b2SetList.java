package fr.aphp.wind.i2b2fhir.i2b2setlist;

import java.util.ArrayList;
import java.util.Iterator;

import fr.aphp.wind.i2b2fhir.i2b2set.*;
public  class I2b2SetList  implements Iterable<I2b2Set>{
	protected ArrayList<I2b2Set> setList = new ArrayList<I2b2Set>();
	
	public I2b2SetList(){}

	public void addResult(I2b2Set result){
		this.setList.add(result);
	}
	
	public ArrayList<I2b2Set> getSetList() {
		return setList;
	}
	
	public String toCsv(){
		String str = "";
		for(I2b2Set s : this.setList){
			str += s.toCsv() + "\n" ;
		}
		return str.replaceAll("\n$", "");
	}

	public Iterator<I2b2Set> iterator() {
		 Iterator<I2b2Set> it = new Iterator<I2b2Set>() {

	            private int currentIndex = 0;

	            public boolean hasNext() {
	            	
	                return currentIndex < setList.size() && setList.get(currentIndex) != null;
	            }

	            public I2b2Set next() {
	                return setList.get(currentIndex++);
	            }

	            public void remove() {
	                throw new UnsupportedOperationException();
	            }
	        };
	        return it;
	}

}
