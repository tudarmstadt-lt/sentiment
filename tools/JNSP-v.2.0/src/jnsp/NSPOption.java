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
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Vector;


/**
 * @author DODO
 *
 */
public class NSPOption {
	
	public int window = 2;	
	
	//number of ngrams
	public int N = 0; 

	public File freqComboFile;	

	public File dataDir;
	
	public String cntFile;
	
	public File stopFile = null;
	
	public int freqCutOff = -1;
	
	public int rareCutOff = -1;
	
	public File statFile;
	
	public String  statlib;
	
	public boolean agressiveCount = true; 
	
	public boolean ngramCache = false;
	
	@SuppressWarnings("unchecked")
	public static NSPOption readOption(String optionFile){
		NSPOption option = new NSPOption();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(optionFile));
			String line;
			
			Class c = option.getClass();
			
			while ((line = reader.readLine()) != null){
				if (line.startsWith("#")) continue; // skip comment lines
				
				String [] tokens = line.split("=");
				
				if (tokens.length != 2)
					continue;
				
				String key = tokens[0].trim();
				String val = tokens[1].trim();
				
				Field field = c.getDeclaredField(key);
				if (field.getType().getName().equalsIgnoreCase("int")){
					field.set(option, Integer.parseInt(val));
				}
				else if (field.getType().getName().equalsIgnoreCase("double")){
					field.set(option, Double.parseDouble(val));
				}
				else if (field.getType().getName().equalsIgnoreCase("boolean")){
					field.set(option, Boolean.parseBoolean(val));
				}
				else if (field.getType().getName().equalsIgnoreCase("java.lang.String")){
					field.set(option, val);
				}
				else if (field.getType().getName().equalsIgnoreCase("java.io.File")){					
					field.set(option, new File( val));
				}
				else if (field.getType().getName().equalsIgnoreCase("java.util.Vector")){
					((Vector)field.get(option)).add(val);
					// vector of string
				}
			}
			
			reader.close();
			return option;
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
