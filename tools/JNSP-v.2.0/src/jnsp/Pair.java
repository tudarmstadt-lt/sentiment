/**
 * Copyright (C) 2008 by
 * 
 * 	Cam-Tu Nguyen
 *  ncamtu@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *  
 *  Thu-Trang Nguyen
 *  trangnt84@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *
 * JNSP is a free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JNSP is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JNSP; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package jnsp;


public class Pair implements Comparable {
	String key;
	double value;
		
	//Contructors
	public Pair(String key, double val){
		this.key = key;
		value = val;
	}
	
	public String getKey (){
		return key;
	}
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		 if (o instanceof Pair) {
	            int cmp = Double.compare(value, ((Pair) o).value);
	            if (cmp != 0) {
	                return cmp;
	            }
	            return key.compareTo(((Pair) o).key);
	        }
	        throw new ClassCastException("Cannot compare Pair with " + o.getClass().getName());		
	}

}
