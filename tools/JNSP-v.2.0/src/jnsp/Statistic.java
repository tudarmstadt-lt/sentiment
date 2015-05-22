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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import jnsp.stat.Chi2D;
import jnsp.stat.MI2D;
import jnsp.stat.Measure;
import jnsp.stat.Phi2D;
import jnsp.stat.TScore2D;
import jnsp.stat.TScore3D;

public class Statistic {
	List<NGram> ngrams;
	Map<String, Double> statMap; //map from string presentation into statistic measure
	
	int window;
	int N=0;
	
	String [] freqCombo;
	Measure measure;
	boolean initialized = false;
	NSPOption option ;
	//----------------------------
	// Init Methods
	//----------------------------
	public Statistic(NSPOption option){		
		this.option = option;
		init(option);
		//this.ngrams =ngrams;
		initialized = true;
	}
	
	protected boolean init(NSPOption option){
		try {			
			this.window = option.window;
			this.window = option.N;
			this.option = option;
			
			initialized = true;
			
			Map<Integer, String[]> freqMap = Util.readFreqCombos(option.freqComboFile);
			if (freqMap == null)
				freqCombo = Util.genDefaultFreqCombo(window);
			else
				freqCombo = freqMap.get(window);				
			
			//window = size;
			if (option.statlib.equalsIgnoreCase("chi2d"))
				setMeasure(new Chi2D(),N);
			else if (option.statlib.equalsIgnoreCase("mi2d"))
				setMeasure(new MI2D(),N);
			else if (option.statlib.equalsIgnoreCase("tscore2d"))
				setMeasure(new TScore2D(),N);
			else if (option.statlib.equalsIgnoreCase("tscore3d"))
				setMeasure(new TScore3D(),N);
			else if (option.statlib.equalsIgnoreCase("phi2d")){
				setMeasure(new Phi2D(),N);
			}
			//this.ngrams =ngrams;

			
			if (option.cntFile != null){
				Dictionary dict = new Dictionary();
				System.out.println("size: "+ freqCombo.length + " "+window);
				
				ngrams = readCntFile(option.cntFile, window, dict,freqCombo);
			}
			
			System.out.println("NGRAMSET ");
			statMap = new HashMap<String, Double>();
			
			initialized = true;
			return true;
		}
		catch (Exception e){
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public void setNgrams(List<NGram> ngrams){
		this.ngrams=ngrams;
	}	
	
	
	public void setMeasure(Measure measure,int size){
		this.measure = measure;		
		if (measure.getStatisticName().equalsIgnoreCase("tscore3d")){
			TScore3D tscore3d = (TScore3D) measure;
			tscore3d.initilizeStatistic(size, "0:1:2=0|1|2", this.freqCombo);
		}
		else measure.initilizeStatistic(size);
	}
	//-------------------------------
	// calculate
	//-------------------------------
	public double calculate(NGram ngram){	
		if (!initialized || measure == null){
			ngram.setStatVal(0);
			return 0;
		}
		double ret = measure.calculateStatistic(ngram);
		ngram.setStatVal(ret);
		
		return ret;
	}
	
	public void calculate(){		
		if (measure == null){
			return;
		}
		
		statMap.clear();
		long start = 0;
		long end = (new Date()).getTime();
		long sum = 0;
		for (int i = 0; i < ngrams.size(); ++i){			
			
			if (i%1000 == 0 && i != 0) {
				start = end;
				end = (new Date()).getTime();
				//System.out.println("1000 ngrams in " + (end - start) + "; next 1000 from " + i);				
			}
			 
			double val = calculate(ngrams.get(i));			
			statMap.put(ngrams.get(i).toString(), val);
		
			if (i % 100000 == 0 && i != 0){
				writeStatFile(option.statFile, true);
			}
		}
	}	
	
	
	//------------------------------------
	// write file
	//------------------------------------
	
	public void writeStatFile(File statFile, boolean decOrder){		
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(statFile), "UTF-8"));
			
			System.out.println("writing");
			if (decOrder){ //value descending
				Set<Pair> statSet = new TreeSet<Pair>();
			
				Iterator<String> it = statMap.keySet().iterator();
				
				while (it.hasNext()){
					String key = it.next();
					Double val = statMap.get(key);
					statSet.add(new Pair(key, -val ));
				}
							
				for (Iterator<Pair> it2 = statSet.iterator(); it2.hasNext(); ){
					Pair p = it2.next();
					out.write(p.key + "\t" + (-p.value) + "\n");
				}				
			}
			else {
				Iterator<String> it = statMap.keySet().iterator();
				
				while (it.hasNext()){
					String key = it.next();
					Double val = statMap.get(key);
					out.write(key + "\t" + val + "\n");
				}
			}
			
			out.close();
		}
		catch (Exception e){
			System.out.println("Error while writing into stat file: " + e.getMessage());
			return;
		}
		
	}
	
	public List<NGram> readCntFile(String cntFile, int window, Dictionary dict, String[] freqCombo){
		
		try{
			List<NGram> ngrams = new LinkedList<NGram>();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(cntFile+window+".cnt"), "UTF-8"));
			
			// get the number of NGram
			int NoNGram = Integer.parseInt(in.readLine());
			N = NoNGram;
			//System.out.println(N);
			measure.initilizeStatistic(N);
			
			String line;
			int count = 0;
			while ((line = in.readLine()) != null){
				count++;
				
				String [] tokens = line.split("(<>)| ");	
				//System.out.println("tokens :" + tokens.length);
				if (tokens.length != window + freqCombo.length){
					System.out.println("The number of freq combinations are not as specified");
					return null;
				}
				
				String [] ngramTokens = new String[window];
				for (int i = 0; i < window; ++i){
					ngramTokens[i] = tokens[i];
				}
				
				int [] tokenids = new int[window];
				for (int i = 0; i < window; ++i){
					tokenids[i] = dict.addWord(tokens[i]);
				}
				NGram ngram = new NGram(window,tokenids,dict);
				ngram.initCounts(freqCombo);
				
				for (int i = 0; i < freqCombo.length; ++i){					 
					ngram.counts[i] = Integer.parseInt(tokens[window + i]);					
				}
				ngram.frequency = ngram.counts[0];
				ngrams.add(ngram);
				
				if (count >= NoNGram) break;
			}
			
			in.close();
			return ngrams;
		}
		catch (Exception e){
			System.out.println("Error while reading cnt file: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
//	--------------------------------------------
	// MAIN METHOD
	//--------------------------------------------
	public static void main(String[] args ){
		NSPOption option = new NSPOption(); 
		option = NSPOption.readOption(args[0]);
		
		try{			
			Statistic s = new Statistic(option);					
			long startTime = (new Date()).getTime();			
			s.calculate();
			
			long endTime = (new Date()).getTime();
			System.out.println("Calculate statistic in " + (endTime - startTime) + "miliseconds");
			s.writeStatFile(option.statFile, true);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
