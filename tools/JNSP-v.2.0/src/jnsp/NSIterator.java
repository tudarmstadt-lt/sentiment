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

import java.util.Iterator;

public class NSIterator {
	Iterator<Long> iterator;
	NGramSet ngramSet; //link to its ngramset
	int nActive;
	int count = 0;
	
	protected NSIterator(Iterator<Long> it, NGramSet ngramSet, int nActive){
		iterator = it;
		this.ngramSet = ngramSet;
		this.nActive = nActive;
	}
	
	//get the next not-null gram
	// null if there is not such gram available
	public Long next(){		
		while (iterator.hasNext()){
			Long next = iterator.next();
			if (ngramSet.getNGram(next) == null){
				continue;
			}
			else {
				count++;
				
			//	System.out.println(next + ":" + count + ":" + nActive + ":" + ngramSet.getNGram(next).size());
				return next;
			}
		}
		
		//System.out.println("here");
		return null;
	}	
	
	public boolean hasNext(){
		return count < nActive;
	}
}
