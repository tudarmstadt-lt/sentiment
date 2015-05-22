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

package jnsp.stat;

import jnsp.NGram;

public class Phi2D extends Measure {

	@Override
	public double calculateStatistic(NGram ngram) {
//		 TODO Auto-generated method stub
		if (ngram.size() != 2) {
			System.out.println("Only handle bigram!");
			return 0;
		}
				
		long n00, n01, n10, n11;
		
		int [] count = ngram.getCounts();
		n00 = count[0];		
		n10 = count[1] - count[0];
		n01 = count[2] - count[0]; 
		n11 = N - n00 - n10 - n01;
		//System.out.println(N + " " + n00 + " " + n10 + " " + n01 + " " + n11);
		
		double phi2d = (double) ((n00*n11 - n01 * n10))/((n00 + n01) * (n01 + n11));
		phi2d = (double)( (phi2d * (n00*n11 - n01 * n10))/((n00 + n10) * (n10 + n11)) );		
		
		return phi2d;
	}

	@Override
	public String getStatisticName() {
		// TODO Auto-generated method stub
		return "Phi2D";
	}

}
