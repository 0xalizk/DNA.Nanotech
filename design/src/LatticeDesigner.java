import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;



public class LatticeDesigner {
	public static int length_of_subseq = 8;//the run of bases to be checked for commonality
	public static int length_of_sub_subseq = 6;
	
	public static String lattice = "TAACATCTTTGATTGGTCTATACTACTCTCAAGGACGAATGTAGTCTTGGTGAACTTAGTGCTCGTGTTAAAGTTATTATACCTTATCCATCTACGTCATTACAAGTCAACCTCTTCATATTCCAACAGACACACATAAAGACATTTGCATCCAGTGAGCTGTCCACGATAGCTCCATAGCAAGGTTCATCACTTTCAGTAGACTCCACATCATTTCGTAAGAACATACATGGTTCTCCTAGCTTGTGAGACTGCTCAGCTTCGTGAGGACCTATTCTCGTTTGTGTAGAAGATCCTGACTGTGCAGAAATTGGACGTGTGCTTCTTGACCAGTCGATTTAATATCTCGATACGATTCACACCAGAGTTCCATTAAGCATTGAGTCACCTGCTACCAAACTTGAAATACGTTGTTACTAACTAGCATAAGTAAACAGGTTTCTCTGAATTAGGACTAATCATACCATTCCTGTGATCTATGACAACTGAGTTTCCAGATTATTTAGATAGTCATGCAGCTAATACAATCTTAAACGTCGTCAAATCCATTCCATCGTTACCATCTCAGGTCACCTCTACATTGTCTAAGCAAAGGAATCGTGAATCACTGTTACGTTAGCTTGATATTGTAACTTCATTGCACTATGGATATAGGTATATTATAGACTATCGTAGCACTCCTCAAGAGCTGTACTGGAGATTAGGATAATGATACGTGGAAGCATTAAACTACCAACGTCACATGGTGGTTAAGTAAGCTGCACATTAGAAGTGAGTTGATGAAACACAAGGATTTCTACTGTAAACCAGACATAGAGATATGCTAATAGGAGCTACACACGAAGTAGTCAGTGGACTTGTTAAATTCGATTATTACTCAATAACCTTTACGACGACATTCTAGCAGGAACTTATTCGTCTTTGGTGTTCAAGTATAAAGCAGAGTCTGATCCTCTTCTTTAGGTTCGTTCGACTGAAGGTAATTGATCTTGTCCTCGACACAGCAGCACCAGTCATTCAAACAACCATATAACATACTAGAGCACAGATCGAGACAGAAACTCTTAGTGTTGTATTTATTTGTACGAGTTACAAACTAACTCTATCTACGAGGACTTCTATGAAATAGTACATAACCTAATGGACTTAAATCTTATATTAGAACAATGCAATAACGAACATTCGAGAAACAGAGTGACATGAATAGACCTGGATGAACACGAAACACCTAGCTCAGCAATCAACTAGAGGAATGCTGGAAGCTAATCTGAGAAGAGACGTGCAGACCTTCGATCTTGAAGTAGCTGTGGTCTTACTGAACCTAGAGATGCACTGCATGGAATAGTGCTTTGACACTCCTGTCCTAACATAGTTACGATGCTTCGAGGAGATGACGATTGACAAAGAAAGGAACACTCGTGCTCATACCTCCAACACTGTAAGGATCACACATCAACAAGACACGTATTGGTGATGCTATGACGTTCGATATTCTTCAGCAGTTATCTATCGTTTGCAGAGCTGGTAATACCTGTCGTTTAGTATCCTCCACAACTTAGACTCAGTGAACATATACTGTCCAAAGATATTGACTCTCTCGTCGTGGAATTTCCAATGACTACGAAGTGGATAAACTCACACTCTGAAGACTCAACCACTCGATTAAGTTTAGGATGAGGTCTAAGACGACCAATGAATGTAAGTGTAAACATGAGACTACCAGGATTATGTCGTGTACGAGAAAGAGTTCAAATACACCAGCAAGCAACTGATCTAATTTACGAACTAGAATAGATTCACCATTTCTATAACGATCTTACTTTCACTCCATTGTGAAGCTATGTTGTTGGACAAGGAGCAGAACGAAGGACCTTTATGCAAGATGTACTCTAGCAAACGTCTAGGAATTACAATTATTAACGTATGGATTCGTCGAGCTACGTGTGGTCACAGTACCTATCTCAAGTTGTATAGGACTGTCAGCTCTACGACTGCTGTCCTGACCTGCTTCTGATGTGATAACAAAGGATACCAAGAGGAGGTAACCTAGTCCACATACTGAATAAGGTGACATTTATCGAAGAACACAGAGACATCGTCCTCAGTGCTTATATCTGTCTGAGAGAGGTGTTGAAATCTGACTATGAGCTTGGATATGATTGATGATGGAAATAAAGATTTCTCTATTAGTCTGTTAATACACTTTCTTATGCAGGTAGTTATTGGTCCACAGCACGAAAGGTGGTTGGAAGTAACACGTCTACCAAGCAGCATGAGGAGACGACACTCTCTGACACCACTGTCAACATCGAGTATTCATCTGAACGAATATGCTCATCACACGATGGTCATGGTTATACTATTGTCTTTCCTAAGCTATACGTCCTGGTGCAAACTCTGTCCAGTTTATCTCATTGTGTAATGCACTTGTAGGATAGGACATAAATTCCTTAGTGAACTAGCTGATTCTATATTGAAGCTCTAACTTGCATCAAGAATTGTTGTTCATAGGTCGTAACCTGTAAGTCGAGACTGTTCCTCGTGGAGCACAACCTCACTCAGTAGTCGTTTGAGAAGGATGATTTGTCGAACACTACAGTACAACTAAATCTCTTCCTGTTTCGATCCATTGATATGACGAGTCTAGCACTGGTTCTTTACCTCTCGTTATCAGAACTCAACTGACTGCTAACATTTGGTTTGTGAAAGCACCTAGTAAAAACCAGAAGTATCATGCACACTGTGGTAACCACGAATGATGACGTGAGCAGACAAGACGAGAAGGTCCAATGTGCTTAGTGGAGGTGGATTTGTGGAAGGAATAGAACAGTAGTACCTCTGTAATCTCGACGTTAGTTGAATCTTACGTTGATAACTCCTTTAATCGTTTATATCGTACATGCTCTCAGCATACACAACTGGTGAAGAAGACTTAGGAGAGTATAATGAGACCTAGCATCCTGATAGATGTTGTCCAGCTATGAGTTTGGATGGAGAAACTTATCTAGTGCTATCTTCGAGTGCAGGTTAGGTGCAAGCTGTGTTATTGTAGTTAATAGTGAATACCTTTCCAAGAAAGCTAGACAGGAACCAACCATTTGAGCTGAAATTGACCTCGTATGTAACATGGATCTGAGGATCGTGTTTGTTTCCTATTGAGGTTCAAGAGAATGTATCTGTGACGACAGTTCCAGTCAAGCAATACTATAAGAACGAGCAAATTCATAAGCTCTTAATTGCAGTGACAGAATCGACATTAGTAAACAATGAGCAATGCACGATTATGAAGGAACTCATCTAGGTCATCAAAGGTCTACAAATGCTCCTCTGGATATTTATAGGTTGGTATAGAAGGTGAAGTTCAGGACTTTACATAGTGATTGTACCAGATACGACACCATTACGATACTCTATGGACCACATAAGCATGGAGTTGACTATTCTGTGATATGTCTCCTACCTGATAAACAATTTAAGACACTGCACAATGTTAAACGAGTAATCCACTAGCAGTAAGTGACAACCAATGGAAACATCATCGAGCACGTCCTTATTTCGATTGAATTTCTTACACATTTACGTTTCAGCAACAGGATTCAGAAAGCAAGTACACTAACTTTCAAACTGAAGCAGAAGAACTGTAACTACTAATCTACCATGTCCAGGTGGAGCTTCCTGCAAGAGTCTCGTCAAGACTTGGATCTCACTGTGCTATATTAACCTTAGGATCAAGTGTTTGGTCTCTTATCCTCACACAAGAAGTCATTTCCTAGTTGCATAGCTCTGATTAAGCTGTTCAACGACGAAATTCTACAGCATCAGCTCCTTCACGTAAACCTAACAACAGCTAACTTCCTCTTTCATTTAGAAACTGCTCTTGCTAATTGACGTTAAATATGACACAAGTTTATACCAGTGTGATAGTGGACAATCCACGAAGACACCTGGTGAGTGATTACTTCTCATCGTATTTGTTGGTACGACAGAACTAAGTCAAATTATCAGTATAGTAGTGAGAGACAAGGTCAACGATCAATACTCCAAATGGATGTAAGCACCATCCTGTAAAGATTCCTGGAAGGTATCTGCTACTTGCACGTTCACATAATGGTAATTCATCATCTAACCTCCTGACATTGAATGCACAACTACGTCGTGTCCTACATTAAAGCTCGAACTTAACTAGATTTATCTTAATCTCAGTTCCTAATAAGTGTTCAGTCTTTGAGGTCCATCGACTTCATGCTCAGATGCAGTCACTTTGCTGACTCATGTCTAATCGTCCAGATCATTCTTACTCTTCTATTATTTCTGATTTCGTTACACTCTATAAGCTGGTTGTATCGAATAAAGACTTTGCACGAACCAGAGAAGTGTTATCTTGTTTCAACCTGTCTTACATATCGTGACACATACAAGGTTTCCAACTGCTACGTTATTACAACATGATAAGCAACACGTAACTTGGAGCAAGAATCGTTAGTGATGTGCTCAAGTAGGTCCAGTCCATCAGCAGGTATAGCAATCTCATCAAGCACTCGTCTACACCATATTAAGTGAAGTATGGAATCTGATAGTTCAAGGAATGAGTTAAGATGCAATTTATTCTCCAGAAAGACTCGACAATACAGCTTCATCGTCAATCAGTGACTCTTTACTACCTAGTGGTCTCACCAAATAAGTCCTACCAATGTTCCTATGCTGCATATACATCTCGTGGTACGTCGATTGTGTGTAACAGATAGATCCTTCGAGACGTTGTCCTGCACACGACCTCTAGGAGGTGAATATCTAACTAATCCAAAGCTAAGTAATTCGTTGATTACGAGCATTAGAGTAGATGACTTGCAGTACATTATCGAGCTGAGTATTTAAGGTACACTGTTGGTTATGAAACAATGGACAAACTTCTTCGTAACATTAGCACGATGCACCTGAAACTGTCATTATTGTTCAAACCAGGAGCTTACAATGCTGTGCTTTAGTCTAAATCCTAACTCGTTGTTTACCATACAGACAATTCAGTCACGTTGAGTGTCTACCTCGAATACATGAAAGGACTACTAGAACTGCATTCTCATTTAATTGAATCATAGAGGATGTGTTCCAGGTTAAGCAGGATATAATACGTCACCTAGACCATGTTCTGCAATAGTGTATCACAACACCAGTGATCGTCTCTCGATGGTTGACACCTTACTTGTCGTCCTGAGTCATGGAGGTCCTAGCAGAGCACCACGTACATAGGTTCCTCACTTGAAGAGAACGACCACCAAGCTATCCAACATAAGTTGATAGAAGCAATGATATTACTGGATCTTATCTCCACACAGATGTCTTGATCTCTACTGCTGGACTTCGACTTTCGTCAGATATGAATGAAGGTCATAATTCCAAATTACAGTCGTGAGTTCACACTTAGAATATGTCAGTGTTTCTATCAGTAGCTCAACTATTTGTCAGACATTCCAGACGAACAACTTTATGAGAGTCCACCTAATTGTGCTGACCAGATTGTTGAATGGATACTCCTTAATTCACAGTCTATGTTTGACCTGGTACTATCTTTCAATACCTCTTAGCAGCAACGATACGTTTATTAAACAGCAGAATCTACAAGCATTTCTGTGGATTCATGGTGATCTTCCTACAGGTACGATCTCCTATAACCTCATTAACGAAGCAGTCCTGGAGTTACCTATTGTATAAATCGTGTATGACTAATGGTCCTCTGTCTAACCACTTTCCACTACGTGCATGTTATTTCAGTTCGACGACTAGATGCTACACTTGTGGTAGAGTGAACCTGAGCAAACCTACGAGAGCTGCAAATTGATTCGATGAGTGCAAGACCAAGATGGAACGTGTCATCACGTCTTTAGAACCAATATACTAGCACCTCGTTTCATTGAGACACCAACTCGATCATTTGATGTCCAAGGTCTCTGAGATACAGTATGCTCTATATGGAGACTTCAGCTAAAGAGCATCAATAGACATAACTCTGATGATCCTGTCACAAAGACACAGTGCTCCTACGTCCACGTTACATGGAAGTCAAGATTAGAAAGTTCCACATTTCGACAGCAAGTGGAGTGTTGAGGACACTAGCTCTTCTGTTTGTACTTACACCTATCATAAGAGTATGAGTAGCACACATGACAATGGTTCAGAATACTAACCTTCCTTTATATGTGATGGTAGCTGCTTTCCTCCAATCATGCTATTCGTGTCGTTGGTCGAGAACTACATCGTAGAATTGAGTTTAGTGCATAATAGTCTTAGGTTGTTGTCTCGTTCATCTTCAGTACGACTCACAGACTGACCTCACCTTTGGTACAGTTACTTTGAATAATTTAGCAACCAAGGATAGAGAGATTCAGGAATATAGTGACCATAAACCACAGGTCTGGTGACTAGGATGCAGATCAGCTTAGTCGATACTGGTAACACACCACCTGCAACTTCAACTGGAGCTGGATGTTAAGTCTAGTTTCTTAATGTCAAATCGAAGCTCGTGATAAATAAATAAGACCTATACTTAAACTTTGTAAACGAACTCCTCTCACGTATAAGTGCTGGTCTGAAAGCTCTGTTCATTTCACCACACTAATTTCTCCTGTGTAGTATCAGTTATATTTAGGACGTTCTGAGGTAATGATTATCTGGATTGATCCATCCTTAGAGAATCCTCATCTGCTTGCATTTACTCGAACCTTATGGACGAAGTTAATCTGTCAAGTTCTTAGTAGAAGGAGGACTCGTATTCAGCATCCACTCACTAGAGTTGTAGGTATGTATCCTATCTATTTCGTGAAGCATAACAGTTCAGTGGTAAGAGCAATTCGACCTGATGTTGCACAAGACTGGTTAAAGGTAGCAAATCACTTCATAGTAAGTCACATTGTTTGGACTAACAAGGACAGTCAAAGAGACAGCTATTGGAAGAATTTGTGTCCACAATATGATCTAGCATTCACTGCTCGACTCCAACGTACTCAAACTATAATTGGTGCAGAGGTTTAGATTTCCAGTGTCGATGTCGTAGCTTATTCCACCATTCTATGGTTTCTATAATAGCAGCTTGTCTTGGAGAAGCTAGTAACGACTCCTGATTAGGTAAGCATACCACGTTGCAGACAGTAATGAATAACAACTGTAGAGACACATTGCTGTTAGAGTCAGGTGTGCTGCACCTCTGAAATCACGATCATAGTTCCAATTCTCTCTGCTGAGTGCTCAGTGTCACAGGTAGCTCTATCGAGGAAATTGTACGTCTCATCTTGTGATGTCATACTAATACTTATATGAGGTACATCCTTGACGTGTAGTCCATATTGAGAACATTTCTTGAAACTCCACCAGATGCTCTGTGCATCAGTTTAACTTAAAGACTTTGATACAGGACCAAGTGATCCAACGATGATATACGAACACACAGTCGAGTACAGAAGTTGGTTTAGCATTATCACCACTAACCATCTAAAGTGTGGAAACGAATCGTCTTCTAAGAATCATGTGAATGTCCTCCAGAGTAGGATTGTTTCTGACAGACTCATTGATGGATGACCACCTACCATAGGACGAGTTGAACGTAATCGACCTTACAAATTAAACTTGTTTGTCTACGAATGGTTAATCACACCTCAGTAACTCTTGACACACTATCTCCTTATCTTCACTCATTAGGAGTAGCTTTATCCAAATAGGACCAGTACCACATTACAGCAACTCAAAGTTGACCAATTCAAGGTAAGTTTGATCTGATTCATACAACGATTTCCTGATCCACCAAACCTGCTCAATGTTTAGAGGACTAGCAAGTCTCAGAATTTAGTACGAGTTCATGCAAAGATTGATAATAATGGAGCATAGAACTATTCACGTCGAATCCTAGAAGTTATAGCTGACTAACTGTTGTCAGTCATGTTCCATAACGTGTTCTCTCCAACCTCCAGGTATGCAGTAGGTTCTGGAAGTGCTCTTTAACGAGCTAAGCAGACCATCTGCACTGTATTACGTCTATCACTGAAGTACAGGTGAGTCCATGTGTATCTACTTTCTCACGAGGTACGTGCTATGTCATTGTTAGAAATACCATGAGATGTGACCTAAGTATGTAGTAATCTATACATTTCAAATGCACCATAGTCGTATAAAGTGAACGATTGTTCGATCAGCAAGGACTCCAGCACATGAAGCAAACAAAGTATCTCTTACAAGCTCTCCTCCATACATCAAGTCGTAAGGTTTGACTAAGATAGACTCTACACTGATATAGATCGTCGTCCACTCCTATGATGATACCTAACTGACGTTTCTTGTCGATAAGTACGAAACTCGAATCTGTGGTGGTATATGAAAGTCAGACTTCTCGATTCCAAGTGTGTACCATAATAAATGGTCAGGTTATCTGAATGTGTTAAGGAAGTGAAACATTACCAAATTGCTAACGAGTGATGCAGGATTTGAGATGCTTTACACGAGAGTTGGACCAGGTACTCATGGATAGTCCAGTATTGTGAGCTAAATAGGTGATTATATTGCACAGGTGTTCTACGTGACCTTTCTGTTATACAGAGTAAACTTCAGGAGCAACCTACCTTCGTCTAGTACAGTAAGCAGTTCAATAAGAATAATGCAACAACTATACGAGCTGTGAAGACATACGTCTGCTATAAAGATGACAGCATAAACAGTCATATAGACTGCACTACGATTACCTGTTGAGAGCTAATGACTCTGACCAAATCTTGTATCTTTAGTAGGAACGTACACAGTAGCATGTTCAGCACTCTTGGACTTTGGAACAGAACAAGTACCATTGAGGAAGACAGTGAAGGTTTAATTCTGGACCATGCAAGGTGTATGTCGAATATCAGACAACCTGATTTATTGGATTTCGAGATCCTCCTAACGTGCTTGTGGATGATCTTAGAGGTTCTCACATTATGATGGTGACGATACCAGCAGGAGACCAGGAAACTTGGTTCGATGTGTCCTTACTCAGTCAACGTCCATCTGTATTCCAGCTTGAGCTTTGCTAAGGTAGAAGTAGAGTTTACATTTAAGCAATTAGAATGAACTGGTTTCGTCTCAGGATTGCAGCAAGACTCCTCGTCGATAGCAGTGTGTTTATAGTCACGAATCTAACATCACCTCGACCTCAGCTACATATTAGTGACACGAGTCGTGGTGTTATGTGCAAATACCTAGAAACGACGTGGAGTACAATGATTGTCGTAATAGACAAACGTGAACCAGGTTCTATCTCTGATACAAAGCTGAATCAACATATTTGCTGGATAACATTCCTACGAAAGAATCCAACTATCCTCGTTACGTCAGCATGTGGATCTAAGTCGTGCTTCAAATTGTGACTACCTCTATTGTTAAGCTAAACAGACGTTCATTAAGTACCTTGGTGCTAACCAAAGTGCAACGAACGACATGACCTGTCAATAATCAGTCAGAAGGACTTGAATAGGATTAAATTAACTGCAAAGGTTGTAGTGATCTCATACAGTCTGGTAAGTGGTCAGTTTCGACTCTCGACAGTGTCCATGATGAGTATCTTACAGAATGAGCTATATCGAGTCATAAATAGCAACAAGTGAATTGAACATCTCCAATACATTGCATACTTGGACGAGCAGATTTACCTGAGTGTGTGAAATTCGTCACGAGTAGTTCCTCTCCTTTCCTGTAGACTAGACCTAATACCACACCTACACATAGATATGGTCTTCTCTCAATCTAGCTGGTCGATGACAAGAGAGCAAGTTGAGGTGAGGTCCTAAGAAGAAAGATCAGAGGATATTATGTTACTCCAGTCTCTGGAACGATCCAATTAGTACACATCTTCCACTTTGTGTTGTCGTTAGAAGCTCAGGAAGTCTTATTCTGCATTGACTAGAGAGTTCTTGGTTTCTATCCAGGAGAGATGATAATCCATTACTTTAGCACAAGTATTCGTATTACATGATGTAGGTTAAATCATTTACCAATCTTGCTTACGATTGCATGTCTGTGTCAGTTCACGACCATCAAGGTTGATCGACTCATAACTAAGGTGCTGATAGCATAGTTTAGACGACTGAGAATCTCACACGTTGTGGTGTAACTGGTCTACGTTTGGAGACACGATAACGTCTTCAAAGAATGCTTTCGATGCTCAACATTAACAAATGTACCACCTCTTGTTCAGACTATATGACTTATGGTGAGGAGGAATCACAGCAAACTGTATATCTACAGATTTGATTAGAGCAACTTACAACTGCACGAGAACAGGACTCTTACTACGAGTATCAATAAACCTGAAGGATTGAAATTATACTGGAAGAGTTGAGAATTAGACTTGACTCCTACTTCCTATCGTCTGTCCTCTCGAACATCGTTCTACCAGAGCAGCTCAGTATCTGAGTTATTCTATTTGACCATGATTACACGTACTACGTTGCTTTGTCTTATACATAAACTAATAAAGAGGTCATTGAAGAACCTTCTTAAAGTCACTCAAGATATGTGTTTGAGCAAGCTACAATTCCATTTCATCTCACCTAAATCACATGCAGAGTACCAATAGTCGAATGAGGAAACCACTATAACAAGCAGTGGAGACCTGACAGCTTTAGAGATTGTCACACTACTCCATCACGACACAACAGAGAACTGATTCGTAATTTATCCTGTGCTGTATGCACATCTAAGAGAGACTCACGTTCGTCAACAAACCTCGATCCTTTACTGTTTCAGTCCAATCCAAGGACCACGTCAGAATGTTGGTCTGGATGGTTCCACCTTAACTGTGAGGTATCCACACCAAGTAACCAGCATCTTCGTTTGGTTGATACGAGATAATATGAGACATTACTAAA";
	
	
	public static boolean vlattice = true;//if true, "lattice" above is assumed to be a vertical lattice
	public static int hap_size = 32;
	public static int h_scaffold_size = 320; 
	public static int stapler_size = 32;
	public static int bridge_size = 16;//can't be anything else
	public static int primer_size = 24;
	public static int MAX_PRIMER_SIZE = 32;
	public static String [][] staples = new String [lattice.length()/h_scaffold_size][h_scaffold_size/hap_size-1];
	public static String [][] bridges = new String [h_scaffold_size/bridge_size][(lattice.length()/h_scaffold_size)-1];
	public static String [][] merged_bridges = new String [(h_scaffold_size/bridge_size)][(lattice.length()/h_scaffold_size)/2];//see the last part of outputVerticalScaffolds()
	public static String [] v_scaffolds = new String[h_scaffold_size/bridge_size];
	public static String [] h_scaffolds = new String[lattice.length()/h_scaffold_size];
	public static String [][] haps = new String [lattice.length()/h_scaffold_size][h_scaffold_size/hap_size];
	public static String [][] fillers = new String [2][h_scaffold_size/hap_size];
	public static String [][] template_primers = new String [lattice.length()/h_scaffold_size][2];
	public static BufferedWriter writer;
	public static Hashtable<String, Integer> common_sub = new Hashtable<String, Integer>();
	public static Hashtable<Integer, ArrayList<Integer>> shapeCoords = new Hashtable<Integer, ArrayList<Integer>>();
	public static Hashtable<Integer,ArrayList<String>> shapeHorizPrimers = new Hashtable<Integer,ArrayList<String>>();
	public static Hashtable<Integer,ArrayList<String>> shapeVertiPrimers = new Hashtable<Integer,ArrayList<String>>();
	public static Hashtable<Integer, ArrayList<String>> shape_h_runs = new Hashtable<Integer,ArrayList<String>>();//holds  sequences of haps involved in each horizental in the shape
	public static Hashtable<Integer, ArrayList<String>> shape_v_runs = new Hashtable<Integer,ArrayList<String>>();//holds  sequences of connected bridges involved in each horizental in the shape

	public static String [] template_v_runs =           new String [(h_scaffold_size/bridge_size)];	    
	
	public static String [] vert_shape_trail = new String [(h_scaffold_size/bridge_size)];
	public static String [] horiz_shape_trail = new String [lattice.length()/h_scaffold_size];
	
	public static void main(String[] args) throws DocumentException, IOException { 	
		lattice = lattice.toUpperCase();
		if (vlattice)
			vLattice_to_hLattice ();
		
		printLatticeStats();
		findRepeats();		
		outputHorizentalScaffolds (lattice,hap_size, h_scaffold_size/hap_size);	 
		outputStaples();
		outputFillerStrands();
		
		
		outputVerticalScaffoldsTemplate();
		outputTemplatePrimers();
		
		//outputHorizShapePrimers ("Z.Template.coords");
		//outputVertShapePrimers("Z.Template.coords");
		
		//outputHorizShapePrimers ("X.Smiley.coords.02");
		//outputVertShapePrimers("X.Smiley.coords.02");
		//outputHorizShapePrimers ("Z.dotted.coords");
		//outputVertShapePrimers("Z.dotted.coords");
		
		//outputHorizShapePrimers ("Z.dotted.coords");
		//outputVertShapePrimers("Z.dotted.coords");
				
		outputHorizShapePrimers ("X.Smiley.coords.03");
		outputVertShapePrimers("X.Smiley.coords.03");
		
		//outputHorizShapePrimers ("X.rhombus.Coords");
		//outputVertShapePrimers("X.rhombus.Coords");
		
		
		//outputCanvas();
		//canvasPDF();
		//staplesPDF01();
		//staplesPDF02();
		//bridgesPDF01();
		//bridgesPDF02();
		//hapsPDF();
		
	
		System.out.println("\nDone.");
										
	}
	
	public static void vLattice_to_hLattice () {
		//called when "lattice" is assumed to contain a concatenation of verticals
		//horizentals are extracted accordingly, because all the code in this program assumes that "lattice" contains a concatenation of horizentals
				
		String [] verticals = new String[20];
		String [] horizentals = new String[34];
		int parity=0,index=0;
		for (int m=0;m<lattice.length();m=m+544){
			if (parity%2==0)
				verticals[index++] = lattice.substring(m, m+544);
			else
				verticals[index++] = reverse(lattice.substring(m, m+544));
			parity++;			
		}	
		for (int m=0;m<34;m++)//initialize verticals
			horizentals[m] = "";
			
		int start =0,end =16,filler_offset =0,multiplier=16,dum =0;
		index = 0;

		for (int i=0;i<34;i++){//for each h-scaffold	
			
			if(i==0) filler_offset=8;
			else if (i==33)filler_offset=-8;
			else filler_offset=0;
			if(i>0) dum =8;
					
			for (int k=0;k<20;k++){//for each v-scaffold				
				if(k%2==0){
					start 	= i*multiplier+dum;   end 	= i*multiplier+dum+16;	
				}			
				else{
					start 	= i*multiplier-dum;    end 	= i*multiplier-dum+16;					
				}				
				if(k%2==0){
					if(i%2==0)
						horizentals[i] += reverse(complement(verticals[k].substring(start,end+filler_offset)));
					else 
						horizentals[i] = reverse(complement(verticals[k].substring(start,end+filler_offset)))+horizentals[i];
				}
				else{
					if(i%2==0)
						horizentals[i] += complement(verticals[k].substring(start,end-filler_offset));
					else
						horizentals[i] = complement(verticals[k].substring(start,end-filler_offset)) + horizentals[i];
				}								
			}			
		}
		lattice="";
		for (int j=0;j<34;j++){
				lattice+=horizentals[j];		
		}
	}
	
	public static void outputVertShapePrimers(String file){
		try{
			//merged_bridges = new String [(h_scaffold_size/bridge_size)][(lattice.length()/h_scaffold_size)/2];
			writer = new BufferedWriter(new FileWriter(new File("shapes.and.primers/"+file+".V-Runs.and.primers.txt")));  
			//System.out.println("template_v_runs\n\n");
			for (int i=0;i<(h_scaffold_size/bridge_size);i++){ 
				template_v_runs[i]="";
				for (int j=0;j<(lattice.length()/h_scaffold_size)/2;j++){
					if(i%2==0) template_v_runs[i]+=merged_bridges[i][j];	
					else template_v_runs[i]+=merged_bridges[i][((lattice.length()/h_scaffold_size)/2)-j-1];							
				}				
			}	
			for (int k=0;k<(h_scaffold_size/bridge_size);k++){
				vert_shape_trail[k]="";
				for(int r=0;r<544;r++)
				vert_shape_trail[k]+="_";
			}
			int odd_shift_multiplier=0,even_shift_multiplier=67,filler_offset = 0,X1,X2,Bs,Be,Is, Ie;
			//for each element in merged_bridges, add that element to its analogue in template_v_runs, if it falls within shape
			for (int i=0;i<((lattice.length())/h_scaffold_size);i++){ //34 h_scaffolds to scan
				if (i>0){
					odd_shift_multiplier = (2*i)+1;
					even_shift_multiplier = 67-((2*i))-(i/33);
				}
				for (int j=0;j<(h_scaffold_size/bridge_size);j++){//20 jumps horiz. 			
						if (i==0) filler_offset = 8;
						else if (i==(((lattice.length())/h_scaffold_size)-1)) filler_offset = 8;
						else filler_offset =0;
						
						Is=0;Ie=0;
						if (j%2==0){										
							Bs = (j/2)*32+1;
							Be = (j/2)*32+16+filler_offset - ((i/(((lattice.length())/h_scaffold_size)-1)))*16;								
						}else{
							Bs = (j)*16+1+filler_offset - (i/(((lattice.length())/h_scaffold_size)-1))*16;
							Be = (j)*16+16;							
						}
						for (int m=0;m<shapeCoords.get(i+1).size();m=m+2){//
							Is=0;Ie=0;
							X1  = shapeCoords.get(i+1).get(m);
							X2   = shapeCoords.get(i+1).get(m+1);		
																		
							if (X1<=Bs && X2>=Be){
								Is = 0;
								Ie = (Be-Bs)+1;
							}else if ((X1<=Bs) && (Bs<=X2) && (X2 <=Be)){
								Is = 0;
								Ie = (X2 - X1)- (Bs-X1)+1;
							}else if ((X1>=Bs) && (X1 <=Be) && (X2>=Be)){
								Is = X1-Bs;
								Ie = (X1-Bs)+(Be - X1)+1;
							}else if ((X1>=Bs) && (X1 < Be) && (X2>Bs) && (X2<=Be)){
								Is = X1-Bs;
								Ie = (X1-Bs) + (X2-X1)+1;
							}
							
							if (!((Is==0) && (Ie==0))){
								//flag=0;

								if(j%2==0){		
									if (i%2==0){//reverse indexing, for directionality
										int temp = Is;
										Is = ((Be-Bs))-Ie+1;
										Ie = ((Be-Bs)-temp)+1; 
									}
									String left =   vert_shape_trail[j].substring(0, Is+(8*odd_shift_multiplier));
									String right =  vert_shape_trail[j].substring(Ie+(8*odd_shift_multiplier),  vert_shape_trail[j].length());
									String insert = template_v_runs[j].substring(Is+(8*odd_shift_multiplier), Ie+(8*odd_shift_multiplier));
									vert_shape_trail[j] = left + insert + right;	
					
								}else{	
									if (i%2==0){//reverse indexing, for directionality
										int temp = Is;
										Is = ((Be-Bs))-Ie+1;	
										Ie = ((Be-Bs)-temp)+1; 
									}
									String left =   vert_shape_trail[j].substring(0, Is+(8*even_shift_multiplier));
									String right =  vert_shape_trail[j].substring(Ie+(8*even_shift_multiplier),  vert_shape_trail[j].length());
									String insert = template_v_runs[j].substring(Is+(8*even_shift_multiplier), Ie+(8*even_shift_multiplier));	
									vert_shape_trail[j] = left + insert + right;
									
								}
							}
						}
					}//j				
			}//i
			
			
			
//--------OUTPUT SHAPE with vertical scaffolds -------///
			//1.transform shape_h_runs to haps			
			String [][] dotted_haps = new String [lattice.length()/h_scaffold_size][h_scaffold_size/hap_size];
			for (int x=0;x<lattice.length()/h_scaffold_size;x++){
				for (int y=0;y<h_scaffold_size/hap_size;y++){
					if(x%2==0)
						dotted_haps[x][y] =  horiz_shape_trail[x].substring(y*32, y*32+32);
					else
						dotted_haps[x][(h_scaffold_size/hap_size)-y-1] =  reverse(horiz_shape_trail[x].substring(y*32, y*32+32));
				}
			}
			
			//2.transform vert_shape_trail to bridges[]
			String [][] dotted_bridges = new String [h_scaffold_size/bridge_size][(lattice.length()/h_scaffold_size)-1];
			
			filler_offset=16;
			for (int i=0;i<(h_scaffold_size/bridge_size);i++){
				for (int j=0;j<((lattice.length()/h_scaffold_size)-1);j++){
					if (i%2==0){
						
						dotted_bridges[i][j] = vert_shape_trail[i].substring(filler_offset+ j*16,  filler_offset+ j*16 + 16);
					}
					else
						dotted_bridges[i][j] = vert_shape_trail[i].substring(vert_shape_trail[i].length() - (j*16) - 16, vert_shape_trail[i].length() - (j*16));
				}
			}
			
		
			writer.append ("\nBridges with orientation and h-scaffolds\n\n\n                ");
			for (int i=0;i<(h_scaffold_size/bridge_size);i++)
				writer.append ("-"+(i+1)/10+(i+1)%10+"             ");	
			
			for (int i=0;i<(lattice.length()/h_scaffold_size)-1;i++){//34
				writer.append ("\nH-"+((i+1)/10)+((i+1)%10)+"    ");
				if (i%2==0){
					for (int j=0;j<h_scaffold_size/hap_size;j++)
						writer.append(dotted_haps[i][j].subSequence(0,( hap_size/2))+"."+dotted_haps[i][j].subSequence(( hap_size/2),hap_size)+"-");						
				}else{
					for (int j=0;j<h_scaffold_size/hap_size;j++)
						writer.append(reverse(dotted_haps[i][(h_scaffold_size/hap_size)-j-1].substring((hap_size/2),hap_size))+"."+reverse(dotted_haps[i][(h_scaffold_size/hap_size)-j-1].substring(0,(hap_size/2)))+"-");
				}
				for (int d=0;d<2;d++){	
					if 	(i%2 ==0) {
						writer.append("\n        ");					
						if(d%2==0){
								for (int k=0;k<(h_scaffold_size/bridge_size);k++){
									if (k%2==0) writer.append(reverse(dotted_bridges[k][i].subSequence(0, 8)+"")+"-5             ");
									else 		writer.append("3-"+reverse(dotted_bridges[k][i].subSequence(8, 16)+"")+".");
								}
						}else{
								for (int k=0;k<(h_scaffold_size/bridge_size);k++){
									if (k%2==0) writer.append((dotted_bridges[k][i].subSequence(8, 16)+"")+"-3             ");
									else 		writer.append("5-"+(dotted_bridges[k][i].subSequence(0, 8)+"")+".");
								}
						}
					}else{
						writer.append("\n              ");
						if(d%2==0){
							for (int k=0;k<(h_scaffold_size/bridge_size);k++){
								if (k%2==0) writer.append("5-"+(dotted_bridges[k][i].subSequence(0, 8)+"")+".");
								else 		writer.append((dotted_bridges[k][i].subSequence(8, 16)+"")+"-3             ");
							}
						}else{
							for (int k=0;k<(h_scaffold_size/bridge_size);k++){
								if (k%2==0) writer.append("3-"+reverse(dotted_bridges[k][i].subSequence(8, 16)+"")+".");
								else 		writer.append(reverse(dotted_bridges[k][i].subSequence(0, 8)+"")+"-5             ");
							}
						}
					}
				}						
			}	
			writer.append("\nH-34    ");
			
			
			for (int j=0;j<10;j++){//10
				writer.append(reverse(dotted_haps[33][(h_scaffold_size/hap_size)-j-1].substring((hap_size/2),hap_size))+"."+reverse(dotted_haps[33][(h_scaffold_size/hap_size)-j-1].substring(0,(hap_size/2)))+"-");
			}
			writer.append("\n\n\nvert_shape_trails:\n");
			for (int i=0;i<(h_scaffold_size/bridge_size);i++){ 						
					writer.append("\nV-"+((i+1)/10)+""+((i+1)%10)+" "+vert_shape_trail[i]);			
			}
			
	//------ Vertical Primers ------ ///
	//transform runs from trails into shape_v_runs (eliminating dots)
			
			for (int i=0;i<(h_scaffold_size/bridge_size);i++){ 		
					int start =0,end =0, counter=0;
					while (counter <vert_shape_trail[i].length()){
						while ((counter <vert_shape_trail[i].length()) && vert_shape_trail[i].charAt(counter++)=='_'){
							start++; end++;
						}while ((counter <vert_shape_trail[i].length()) && vert_shape_trail[i].charAt(counter++)!='_')
							end++;
						
						if (shape_v_runs.containsKey(i+1)){		
									if (start != end)
										shape_v_runs.get(i+1).add(vert_shape_trail[i].substring(start, end+1));
						}else {
									if (start != end){
										ArrayList<String> a = new ArrayList<String>();
										a.add(vert_shape_trail[i].substring(start, end+1));
										shape_v_runs.put(i+1, a);
									}
									else 
										shape_v_runs.put(i+1, new ArrayList<String>());									
						}
						
						if (counter >= vert_shape_trail[i].length()) break;
						
						counter--;
						start = end +1;
						end++;
					}					
			}
		//---------- shape_v_runs ---///
			writer.append("\n\nshape_v_runs");
			for (int i=1;i<=shape_v_runs.size();i++){
				writer.append("\nV-"+(i/10)+""+(i%10));
				for (int j=0;j<shape_v_runs.get(i).size();j++){
					writer.append("       "+shape_v_runs.get(i).get(j));
				}
			}
			
	//--- VERTICAL PRIMERS ----- ///
			//Hashtable<Integer,ArrayList<String>> shapeVertiPrimers
			for (int i=1;i<=shape_v_runs.size();i++){
				writer.append("\n\n\nPrimers for V-"+(i/10)+""+(i%10)+"\n\n");
				for (int j=0;j<shape_v_runs.get(i).size();j++){
					if (shape_v_runs.get(i).get(j).length() > (2*primer_size)){
						//reverse primer
						writer.append("\n\t\t");
						for (int k=0;k<shape_v_runs.get(i).get(j).length()-primer_size;k++) writer.append(" "); 
						writer.append("3-"+complement((shape_v_runs.get(i).get(j).substring(shape_v_runs.get(i).get(j).length()-primer_size, shape_v_runs.get(i).get(j).length())))+"-5");
						writer.append("\n\t\t5-"+shape_v_runs.get(i).get(j)+"-3");
						writer.append("\n\t\t3-"+complement((shape_v_runs.get(i).get(j)).toLowerCase())+"-5");
						writer.append("\n\t\t5-"+shape_v_runs.get(i).get(j).substring(0, primer_size)+"-3");
						String gc="";
						//add to primer list
						if (shapeVertiPrimers.containsKey(i)){
							gc = GC_content(shape_v_runs.get(i).get(j).substring(0, primer_size));
							shapeVertiPrimers.get(i).add("V-"+(i/10)+""+(i%10)+"-F-"+(j/10)+""+(j%10)+"    "+shape_v_runs.get(i).get(j).substring(0, primer_size)+" *don't order for template"+"\t"+gc);
							gc = GC_content(reverse(complement((shape_v_runs.get(i).get(j).substring(shape_v_runs.get(i).get(j).length()-primer_size, shape_v_runs.get(i).get(j).length()))))); 
							shapeVertiPrimers.get(i).add("V-"+(i/10)+""+(i%10)+"-B-"+(j/10)+""+(j%10)+"    "+reverse(complement((shape_v_runs.get(i).get(j).substring(shape_v_runs.get(i).get(j).length()-primer_size, shape_v_runs.get(i).get(j).length()))))+"\t"+gc);
						}else {
							ArrayList<String> a = new ArrayList<String>();
							gc = GC_content (shape_v_runs.get(i).get(j).substring(0, primer_size));
							a.add("V-"+(i/10)+""+(i%10)+"-F-"+(j/10)+""+(j%10)+"    "+shape_v_runs.get(i).get(j).substring(0, primer_size)+" *don't order for template"+"\t"+gc);
							shapeVertiPrimers.put(i,a);
							gc = GC_content(reverse(complement((shape_v_runs.get(i).get(j).substring(shape_v_runs.get(i).get(j).length()-primer_size, shape_v_runs.get(i).get(j).length())))));
							shapeVertiPrimers.get(i).add("V-"+(i/10)+""+(i%10)+"-B-"+(j/10)+""+(j%10)+"    "+reverse(complement((shape_v_runs.get(i).get(j).substring(shape_v_runs.get(i).get(j).length()-primer_size, shape_v_runs.get(i).get(j).length()))))+"\t"+gc);
						}
						
					}else //type the singleton by itself
						writer.append("\n\n\t\t"+shape_v_runs.get(i).get(j));
				}
			}
			
			
			//----output vert. primers in a list ----//
			writer.append("\n\n\n\nVertical SINGLETONS in a list\n\n");
			for (int i=1;i<=shape_v_runs.size();i++){
				
				for (int j=0;j<shape_v_runs.get(i).size();j++){
					if (!(shape_v_runs.get(i).get(j).length() > (2*primer_size))){
						writer.append("\n"+"VSG-"+(i/10)+""+(i%10)+"-"+(j/10)+""+(j%10)+"  "+shape_v_runs.get(i).get(j));
					}
				}
			}
			writer.append("\n\n\n\nVertical Primers in a list\n\n");
			for (int i = 1; i<= shapeVertiPrimers.size();i++){
				//writer.append("\n\n");
				if (shapeVertiPrimers.containsKey(i)){
					for (int j=0;j<shapeVertiPrimers.get(i).size()-1;j=j+2){
						writer.append("\n"+shapeVertiPrimers.get(i).get(j));
						writer.append("\n"+shapeVertiPrimers.get(i).get(j+1));
					}
				}
			}
			// merged_bridges = new String [(h_scaffold_size/bridge_size)][(lattice.length()/h_scaffold_size)/2];
		/*	writer.append("\n\n\n\nMerged Bridges in a list HEREEEEE\n\n");
			for (int j=0;j<(h_scaffold_size/(bridge_size));j++){//20
				//writer.append();
				if (j%2==0){
					for (int i=0;i<((lattice.length()/h_scaffold_size)/2);i++){//17					
						writer.append("\nV-"+((j+1)/10)+""+((j+1)%10)+"-"+((i+1)/10)+""+((i+1)%10)+"    "+merged_bridges[j][i]+"-");										
					}
				}else{
					int r = ((lattice.length()/h_scaffold_size)/2);
					for (int i=r-1;i>=0;i--){//17
						writer.append("\nV-"+((j+1)/10)+""+((j+1)%10)+"-"+((r-i+1)/10)+""+((r-i)%10)+"    "+merged_bridges[j][i]+"-");									
					}
				}
			}
			*/
	writer.close();			
	}catch (IOException e) { System.err.println(e);System.exit(1); } 
	}
	
	public static void outputHorizShapePrimers(String coordinates){
		try{
			writer = new BufferedWriter(new FileWriter(new File("shapes.and.primers/"+coordinates+".H-Runs.and.Primers.txt")));  
			BufferedReader reader =  new BufferedReader(new FileReader(new File("coordinates/"+coordinates))); 
			String line="";			
			Scanner scan;
			int left,right, scaffold_counter=0,primer_counter=0;
			while ((line = reader.readLine()) != null) {
				//System.out.println("");
				ArrayList<Integer> pair = new ArrayList<Integer>();
				scan = new Scanner(line).useDelimiter(",|-");
				scaffold_counter++;
				primer_counter=0;
					while(scan.hasNextInt()){
						left = scan.nextInt();
						right = scan.nextInt();
						//System.out.print((left-17)+"-"+(right+5)+",");
						pair.add(left);
						pair.add(right);
						primer_counter+=2;
					}
					shapeCoords.put(scaffold_counter, pair);
					
					if(primer_counter==0 || primer_counter%2 !=0) {reader.close();throw new IOException ("Error 1, check coordinate file format "+primer_counter);}
			}
			for (int r=0;r<(lattice.length()/h_scaffold_size);r++){
				horiz_shape_trail [r]="";
			}
			for (int r=0;r<(lattice.length()/h_scaffold_size);r++){
				for (int q=0;q<h_scaffold_size;q++)
					horiz_shape_trail [r] += "-";
			}
			//int h_index=0;
			for (int i=0;i<shapeCoords.size();i++){
				String s="";
				for (int j=0;j<shapeCoords.get(i+1).size();j+=2){
					left  = shapeCoords.get(i+1).get(j)-1;
					right = shapeCoords.get(i+1).get(j+1);
					if ((right-left)>(2*primer_size)){
						//generate primers for this segment if it's long enough, add to shapeHorizPrimers
						String left_primer="",right_primer="";
						if(i%2==0){
							 s = h_scaffolds[i].replace("|","").substring(left, right);
							left_primer = s.substring(0, primer_size);
							right_primer = reverse(complement(""+s.subSequence(s.length()-primer_size, s.length())));
							
						}else{
							String temp1 = h_scaffolds[i].replace("|","");
							String temp2 = reverse (temp1);
							s = temp2.substring(left, right);
							right_primer = reverse(""+s.substring(s.length()-primer_size, s.length()));
							left_primer =  complement(s.substring(0, primer_size));
						}
						if (shapeHorizPrimers.containsKey(i+1)){
							shapeHorizPrimers.get(i+1).add(left_primer);
							shapeHorizPrimers.get(i+1).add(right_primer);
						}else{
							ArrayList<String> a = new ArrayList<String>();
							a.add(left_primer);
							a.add(right_primer);
							shapeHorizPrimers.put(i+1, a);
						}
	
					}else{
						 if (i%2==0)  s = h_scaffolds[i].replace("|","").substring(left, right);
						 else {	  
							 	String temp1 = h_scaffolds[i].replace("|","");
								String temp2 = reverse (temp1);
								s = temp2.substring(left, right);
						}
						
					}
				
					if (shape_h_runs.containsKey(i+1)){
						shape_h_runs.get(i+1).add(s);//add this short segment to singletons
					}else{
						ArrayList<String> a = new ArrayList<String>();
						a.add(s);
						shape_h_runs.put(i+1, a);
					}		
					
					String L = horiz_shape_trail[i].substring(0, left);
					String R = horiz_shape_trail[i].substring(right, horiz_shape_trail[i].length());
					
					horiz_shape_trail[i] = L+s+R;
				}
			}
/* ---------OUTPUT SHAPE Horizontal scaffolds -------------*/		
			for (int i=0;i<shape_h_runs.size();i++){//for each h-scaffold
				writer.append(((i+1)/10)+""+((i+1)%10)+"  ");
				int [] spaces = new int[shapeCoords.get(i+1).size()/2];
				spaces [0]=shapeCoords.get(i+1).get(0);//leading spaces
				int pos =1;
				for (int k=1;k<spaces.length;k++){
					spaces[k]= shapeCoords.get(i+1).get(pos+1)-shapeCoords.get(i+1).get(pos);
					pos=pos+2;			
				}
				int index =0;
				for (int j=0;j<shape_h_runs.get(i+1).size();j++){//for each segment in that h-scaffold	
					for (int x=0;x<spaces[index];x++)	{				
						writer.append(" ");					
					}
					index++;					
					writer.append(shape_h_runs.get(i+1).get(j));	
				}
				writer.append("\n\n");
			}			
/*------------OUTPUT horizontal PRIMERS and SINGLETONS------------*/
			writer.append("\n\n\nH-SINGLETONS for short segments (less than 2*primer_size)\n\n");
			for(int j=1;j<= shape_h_runs.size();j++){
				if (shape_h_runs.get(j)!=null)
					for (int k=0;k<shape_h_runs.get(j).size();k++){
						if (shape_h_runs.get(j).get(k).length()<2*primer_size)
							if(j%2!=0)
								writer.append("\nHSG-"+j+"-"+k+"  "+shape_h_runs.get(j).get(k));
							else
								writer.append("\nHSG-"+j+"-"+k+"  "+reverse(shape_h_runs.get(j).get(k)));
					}
			}
			
			writer.append("\n\n\nH-PRIMERS for segments long enough\n\n");
			for (int i=1;i<=lattice.length()/h_scaffold_size;i++){
				//writer.append("\n");
				if(shapeHorizPrimers.containsKey(i)){
					int counter=1;
					for (int j=0;j<shapeHorizPrimers.get(i).size();j++){
						
						if (j%2==0){ writer.append("\nH-"+(i/10)+""+(i%10)+"-L-"+(counter/10)+""+(counter%10)+"  ");counter++;}
						else 	    writer.append("\nH-"+(i/10)+""+(i%10)+"-R-"+((counter-1)/10)+""+((counter-1)%10)+"  ");
						
						writer.append(shapeHorizPrimers.get(i).get(j)+"\t"+GC_content(shapeHorizPrimers.get(i).get(j)));
					}
					
				}
			}
			

			
			reader.close();
			writer.close();
			if(scaffold_counter <(lattice.length()/h_scaffold_size)) throw new IOException ("Error 2, check coordinate file format");
		}catch (IOException e) { System.err.println(e);System.exit(1); } 
	}
	
	public static void outputTemplatePrimers (){
		try{
			
			writer = new BufferedWriter(new FileWriter(new File("H.Template.Primers.txt")));  
			writer.append("Left Primers:\n\n");
			for (int i=0;i<(lattice.length()/h_scaffold_size);i++){
				writer.append("\nLeft-"+((i+1)/10)+""+((i+1)%10)+"    ");
				
				if(i%2==0) template_primers [i][0]=(String) haps[i][0].subSequence(0, primer_size); 					
				else 	   template_primers [i][0]=reverse(complement(""+haps[i][(h_scaffold_size/hap_size)-1].subSequence(MAX_PRIMER_SIZE-primer_size, primer_size+(MAX_PRIMER_SIZE-primer_size))));
				
				//if (i%)
				
			writer.append(template_primers [i][0]);
			if (i%2==0) writer.append(" * don't order");
				
			}
			writer.append("\n\n\nRight Primers:\n\n");
			for (int i=0;i<(lattice.length()/h_scaffold_size);i++){
				writer.append("\nRight-"+((i+1)/10)+""+((i+1)%10)+"    ");
				
				if(i%2==0)  template_primers[i][1]=reverse(complement(""+haps[i][(h_scaffold_size/hap_size)-1].subSequence(MAX_PRIMER_SIZE-primer_size, primer_size+(MAX_PRIMER_SIZE-primer_size))));
									
				else 		template_primers[i][1]= (String)(haps[i][0].subSequence(0, primer_size)); 					
									
				writer.append(template_primers[i][1]);		
				if (i%2!=0) writer.append(" * don't order");
			}
			writer.append("\n\n");
			for (int i=0;i<(lattice.length()/h_scaffold_size);i++){
				writer.append("\n\n");
				String clean_h_scaffold = h_scaffolds[i].replace("|", "-");
				if(i%2==0){
					for (int j=0;j<h_scaffold_size-15;j++)	
						writer.append(" ");
					writer.append("3-"+reverse(template_primers[i][1])+"-5");//left primer
					writer.append("\n5-"+clean_h_scaffold+"3");
					writer.append("\n3-"+complement(clean_h_scaffold).toLowerCase()+"5\n");					
					writer.append("5-"+template_primers[i][0]);//right primer
					
				}else{
					writer.append("5-"+template_primers[i][0]+"-3");//left primer
					writer.append("\n3"+reverse(clean_h_scaffold)+"-5");
					writer.append("\n5"+complement(reverse(clean_h_scaffold).toLowerCase())+"-3\n");
					for (int j=0;j<h_scaffold_size-15;j++)	
						writer.append(" ");
					writer.append("3-"+reverse(template_primers[i][1]+"-5"));//right primer
				}
			}
			
			writer.close();			
		}catch (IOException e) { System.err.println(e);System.exit(1); } 
		
	}
	
	public static void outputVerticalScaffoldsTemplate(){//pre-condition: outputHorizentalScaffolds() has been called
		try{			
			writer = new BufferedWriter(new FileWriter(new File("D.Bridges.txt")));  
			//haps = new String [lattice.length()/h_scaffold_size][h_scaffold_size/hap_size];
			//					[34]							  [10]
			String leg1,leg2;
			int m=0;
			for (int i=0;i<(h_scaffold_size/hap_size);i++){
				for (int k=0;k<2;k++){
					for (int j=0;j<(lattice.length()/h_scaffold_size)-1;j++){
						if(k==0){
						
							if(j%2==0){
								leg1 	  = reverse(complement(haps[j][i].substring(0,8)));	
								leg2      = reverse(complement(haps [j+1][(h_scaffold_size/hap_size)-1-i].substring(24, 32)));
							}else{							
								leg1 	  = reverse(complement(haps [j][(h_scaffold_size/hap_size)-1-i].substring(16, 24)));
								leg2      = reverse(complement(haps[j+1][i].substring(8,16)));	 
							}												
						}else{				
							if(j%2==0){
								leg1      =  reverse(complement(haps [j+1][(h_scaffold_size/hap_size)-1-i].substring(0,8)));
								leg2      =  reverse(complement(haps[j][i].substring(24,32)));	
							}else{
								leg1   	  = reverse(complement(haps[j+1][i].substring(16,24)));	
								leg2      =  reverse(complement(haps [j][(h_scaffold_size/hap_size)-1-i].substring(8, 16)));
							}															
						}
						bridges [m][j] = leg1 + leg2;						
					}
					m++;
				}
			}
			
			writer.append("\nBridges written horizentally\n\n     ");
			for (int i=0;i<(lattice.length()/h_scaffold_size)-1;i++)
				writer.append("       "+((i+1)/10)+((i+1)%10)+"        ");
			
			for (int i=0;i<2*(h_scaffold_size/hap_size);i++){
				writer.append("\nV-"+((i+1)/10)+((i+1)%10)+"-");
						for (int j=0;j<(lattice.length()/h_scaffold_size)-1;j++){
							writer.append(bridges[i][j]+"|");
						}
			}		
			
	//-------------------
			writer.append("\n\nBridges written vertically\n\n            ");
			for (int i=0;i<2*(h_scaffold_size/hap_size);i++)
				writer.append("V-"+((i+1)/10)+""+((i+1)%10)+"-            ");
			
			
			for (int i=0;i<(lattice.length()/h_scaffold_size)-1;i++){
				writer.append("\n"+((i+1)/10)+""+((i+1)%10)+"    ");
				for (int j=0;j<2*(h_scaffold_size/hap_size);j++){
					writer.append(bridges[j][i]+"-");
				}
			}		
			
	//
			writer.append ("\n\n\nBridges with orientation and h-scaffolds\n\n\n                ");
			for (int i=0;i<(h_scaffold_size/bridge_size);i++)
				writer.append ("-"+(i+1)/10+(i+1)%10+"             ");	
			
			for (int i=0;i<(lattice.length()/h_scaffold_size)-1;i++){//34
				writer.append ("\nH-"+((i+1)/10)+((i+1)%10)+"    ");
				if (i%2==0){
					for (int j=0;j<h_scaffold_size/hap_size;j++)
						writer.append(haps[i][j].subSequence(0,( hap_size/2))+"."+haps[i][j].subSequence(( hap_size/2),hap_size)+"-");						
				}else{
					for (int j=0;j<h_scaffold_size/hap_size;j++)
						writer.append(reverse(haps[i][(h_scaffold_size/hap_size)-j-1].substring((hap_size/2),hap_size))+"."+reverse(haps[i][(h_scaffold_size/hap_size)-j-1].substring(0,(hap_size/2)))+"-");
				}
				for (int d=0;d<2;d++){	
					if 	(i%2 ==0) {
						writer.append("\n        ");					
						if(d%2==0){
								for (int k=0;k<(h_scaffold_size/bridge_size);k++){
									if (k%2==0) writer.append(reverse(bridges[k][i].subSequence(0, 8)+"")+"-5             ");
									else 		writer.append("3-"+reverse(bridges[k][i].subSequence(8, 16)+"")+".");
								}
						}else{
								for (int k=0;k<(h_scaffold_size/bridge_size);k++){
									if (k%2==0) writer.append((bridges[k][i].subSequence(8, 16)+"")+"-3             ");
									else 		writer.append("5-"+(bridges[k][i].subSequence(0, 8)+"")+".");
								}
						}
					}else{
						writer.append("\n              ");
						if(d%2==0){
							for (int k=0;k<(h_scaffold_size/bridge_size);k++){
								if (k%2==0) writer.append("5-"+(bridges[k][i].subSequence(0, 8)+"")+".");
								else 		writer.append((bridges[k][i].subSequence(8, 16)+"")+"-3             ");
							}
						}else{
							for (int k=0;k<(h_scaffold_size/bridge_size);k++){
								if (k%2==0) writer.append("3-"+reverse(bridges[k][i].subSequence(8, 16)+"")+".");
								else 		writer.append(reverse(bridges[k][i].subSequence(0, 8)+"")+"-5             ");
							}
						}
					}
				}		
				
			}	
			writer.append("\nH-34    ");
			for (int j=0;j<10;j++){//10
				writer.append(reverse(haps[33][(h_scaffold_size/hap_size)-j-1].substring((hap_size/2),hap_size))+"."+reverse(haps[33][(h_scaffold_size/hap_size)-j-1].substring(0,(hap_size/2)))+"-");
			}

			
//	public static String [][] bridges = new String [h_scaffold_size/bridge_size][(lattice.length()/h_scaffold_size)-1];
//	public static String [][] merged_bridges = new String [(h_scaffold_size/bridge_size)][(lattice.length()/h_scaffold_size)/2];//see the last part of outputVerticalScaffolds()
//output merged bridges (32-mer). bridges on first and last horizental scaffold are merged with fillers.
			
			//1.merge zero-bridges (i.e. bridges spanning the first h-scaffold) with fillers F-01-01 to F-01-10
			int filler_counter =0;
			for (int i=0;i<(h_scaffold_size/bridge_size);i=i+2){
				merged_bridges [i][0]= fillers[0][filler_counter]+bridges [i][0];
				filler_counter++;
			}
			//2. merge 33-bridges (i.e. bridges spanning the 34-th h-scaffold) with filler F-02-01 to F02-10
			filler_counter =9;
			for (int i=1;i<(h_scaffold_size/bridge_size);i=i+2){
				merged_bridges [i][16]= fillers[1][filler_counter]+bridges [i][32];
				filler_counter--;
			}
			//3.merge internal bridges
			int even_counter=1;
			int odd_counter=0;
			for (int i=0;i<(h_scaffold_size/bridge_size);i++){//20				
				if (i%2==0){
					even_counter=1;
					for (int j=1;j<((lattice.length()/h_scaffold_size)/2);j++)//17
						merged_bridges [i][j]= bridges [i][even_counter++]+bridges [i][even_counter++];
				}else{
					odd_counter=0;
					for (int j=0;j<((lattice.length()/h_scaffold_size)/2)-1;j++){//17
						merged_bridges [i][j]= bridges [i][odd_counter+1]+bridges [i][odd_counter];
						odd_counter+=2;
					}
				}				
			}
			
			//4. OUTPUT MERGED bridges
			writer.append("\n\n\nMERGED bridges:\n\n\n                    ");
			for (int i=0;i<(h_scaffold_size/(bridge_size));i++){
				writer.append("V-"+((i+1)/10)+""+((i+1)%10)+"-                            ");
				v_scaffolds[i]="";
			}
			for (int i=0;i<((lattice.length()/h_scaffold_size)/2);i++){//34
				writer.append("\n"+((i+1)/10)+""+((i+1)%10)+"    ");
				for (int j=0;j<(h_scaffold_size/(bridge_size));j++){//20
					writer.append(merged_bridges[j][i]+"-");
					if (j%2==0)
						v_scaffolds [j]+=merged_bridges[j][i]+"-";	
					else
						v_scaffolds [j]=merged_bridges[j][i]+"-"+v_scaffolds [j];
				}
			}
			//5.output v_scaffolds
			writer.append("\n\n\nv_scaffolds 5'-3' (V01, V03 etc start on top, V02, V04 start at the bottom):\n\n");
			for (int i=0;i<(h_scaffold_size/(bridge_size));i++){
				writer.append("\nV"+((i+1)/10)+""+((i+1)%10)+" "+v_scaffolds[i]);
			}
//----merged bridges written in list for ordering ----
			writer.append("\n\n\n\nMerged Bridges in a list: outputVerticalScaffoldsTemplate() \n\n");

			for (int j=0;j<(h_scaffold_size/(bridge_size));j++){//20
				//writer.append();
				if (j%2==0){
					for (int i=0;i<((lattice.length()/h_scaffold_size)/2);i++){//17					
						writer.append("\nV-"+((j+1)/10)+""+((j+1)%10)+"-"+((i+1)/10)+""+((i+1)%10)+"    "+merged_bridges[j][i]);										
					}
				}else{
					int r = ((lattice.length()/h_scaffold_size)/2);
					for (int i=r-1;i>=0;i--){//17
						writer.append("\nV-"+((j+1)/10)+""+((j+1)%10)+"-"+((r-i+1)/10)+""+((r-i)%10)+"    "+merged_bridges[j][i]);									
					}
				}
			}
			writer.close();		
		}catch (IOException e) { System.err.println(e);System.exit(1); } 
	}
	
	public static void outputHorizentalScaffolds(String sentence,int word_size, int words_per_line){
		try{	
			writer = new BufferedWriter(new FileWriter(new File("C.Haps.txt")));
			writer.append ("\n                    ");
			for (int i=0;i<words_per_line;i++)
				writer.append ("-"+(i+1)/10+(i+1)%10+"                              ");			
			for (int i=0;i<lattice.length()/h_scaffold_size;i++){	
				writer.append ("\nH-"+((i+1)/10)+((i+1)%10)+"    ");
				h_scaffolds [i] = "";
				for (int m=0;m<h_scaffold_size/hap_size;m++){
					h_scaffolds [i] += sentence.subSequence((i*h_scaffold_size)+(m*hap_size), (i*h_scaffold_size)+(m*hap_size)+hap_size)+"|";
					haps [i][m] = sentence.subSequence((i*h_scaffold_size)+(m*hap_size), (i*h_scaffold_size)+(m*hap_size)+hap_size)+"";
					writer.append(haps[i][m]+"|");
				}
			}
			writer.append("\n\n\n");
			for (int i=0;i<lattice.length()/h_scaffold_size;i++)
				for (int m=0;m<h_scaffold_size/hap_size;m++)
					writer.append("\nH-"+((i+1)/10)+((i+1)%10)+"-"+((m+1)/10)+((m+1)%10)+"    "+haps[i][m]);
					
			writer.close();
			
		}catch (IOException e) { System.err.println(e);System.exit(1); } 				
	}
	
	public static void outputStaples(){
		try{
			int h_counter = 1,v_counter=0;
			writer = new BufferedWriter(new FileWriter(new File("E.Staples.txt")));  
			for (int i=0;i<lattice.length()/h_scaffold_size;i++){	//for each scaffold
				v_counter=0;
				if(i%2==0){
					for (int j=0;j<(h_scaffold_size/hap_size)-1;j++){						
							staples[i][j] = reverse(complement(lattice.substring((i*h_scaffold_size)+(j+1)*hap_size-(stapler_size/2), (i*h_scaffold_size)+(j+1)*hap_size+(stapler_size/2))));					
							writer.append("\nS-"+(h_counter/10)+(h_counter%10)+"-"+(++v_counter/10)+(v_counter%10)+"    "+staples[i][j]+"    "+((i*h_scaffold_size)+(j+1)*hap_size-(stapler_size/2))+".."+((i*h_scaffold_size)+(j+1)*hap_size+(stapler_size/2)));
							//System.out.println("\nS-"+h_counter+"-"+(v_counter)+"    "+staples[i][j]+"    "+((i*h_scaffold_size)+(j+1)*hap_size-(stapler_size/2))+".."+((i*h_scaffold_size)+(j+1)*hap_size+(stapler_size/2)));
					}
				}else{
					int index=0;
					for (int j=((h_scaffold_size/hap_size)-2);j>=0;j--){						
						staples[i][index] = reverse(complement(lattice.substring((i*h_scaffold_size)+(j+1)*hap_size-(stapler_size/2),       (i*h_scaffold_size)+(j+1)*hap_size+(stapler_size/2))));					
						writer.append("\nS-"+(h_counter/10)+(h_counter%10)+"-"+(++v_counter/10)+(v_counter%10)+"    "+staples[i][index++]+"    "+        ((i*h_scaffold_size)+(j+1)*hap_size-(stapler_size/2))+".."+((i*h_scaffold_size)+(j+1)*hap_size+(stapler_size/2)));
						//System.out.println("\nS-"+h_counter+"-"+(v_counter)+"    "+staples[i][j]+"    "+     ((i*h_scaffold_size)+(j+1)*hap_size-(stapler_size/2))+".."+((i*h_scaffold_size)+(j+1)*hap_size+(stapler_size/2)));
					}
				}
				h_counter++;	
			}
			writer.append("\n\n\n                  ");
			for (int j=0;j<(h_scaffold_size/hap_size)-1;j++)
				writer.append("-"+((j+1)/10)+((j+1)%10)+"                     ");
			for (int i=0;i<lattice.length()/h_scaffold_size;i++){
				writer.append("\nS-"+((i+1)/10)+((i+1)%10)+"-    ");
				for (int j=0;j<(h_scaffold_size/hap_size)-1;j++){
					writer.append(staples[i][j]+"    ");
				}
			}
			writer.append("\n\n\n\n                                       ");
			
			for (int j=0;j<(h_scaffold_size/hap_size)-1;j++)
				writer.append(((j+1)/10)+""+((j+1)%10)+"                               ");
			for (int i=0;i<lattice.length()/h_scaffold_size;i++){//34
			//	writer.append("\n\n\nH-"+((i+1)/10)+((i+1)%10));
				if(i%2==0){
					writer.append("\n\n\nH-"+((i+1)/10)+((i+1)%10)+"                        ");
					for (int j=0;j<(h_scaffold_size/hap_size)-1;j++)
						writer.append("3-"+reverse(staples[i][j]).substring(0, stapler_size/2)+"-"+reverse(staples[i][j]).substring(stapler_size/2, stapler_size)+"-5        ");
					writer.append("\n      5-");
					for (int j=0;j<(h_scaffold_size/hap_size);j++)
						writer.append(haps[i][j]+"-");					
				}else{
					writer.append("\n\n\nH-"+((i+1)/10)+((i+1)%10)+"                        ");
					for (int j=0;j<(h_scaffold_size/hap_size)-1;j++)
						writer.append("5-"+staples[i][j].substring(0, stapler_size/2)+"-"+staples[i][j].substring(stapler_size/2, stapler_size)+"-3        ");
					writer.append("\n      3-");
					for (int j=0;j<(h_scaffold_size/hap_size);j++)
						writer.append(reverse(haps[i][(h_scaffold_size/hap_size)-j-1])+"-");	
				}
			}
			
			writer.close();			
		}catch (IOException e) { System.err.println(e);System.exit(1); } 
		
	}
	
	public static void printLatticeStats(){
		try{	  
			int A =0, T = 0, C =0, G =0;
			String current="";
			writer = new BufferedWriter(new FileWriter(new File("A.Summary.txt")));   	
	    	for (int i=0;i<lattice.length();i++){
	    		current = lattice.substring(i, i+1);
	    		if 		(current.equals("A")) A++;
	    		else if (current.equals("T")) T++;
	    		else if (current.equals("C")) C++;
	    		else if (current.equals("G")) G++;
	    		else {System.out.println("Error: printLatticeStats(): invalid DNA sequence");
	    				writer.append("Error: printLatticeStats(): invalid DNA sequence");
	    				System.exit(1);}
	    			
	    	}
			writer.append("length: "+lattice.length()+"\nA's: "+A+"\nT's: "+T+"\nC's: "+C+"\nG's: "+G+"\nGC-Content "+((double) (C+G))/((double) (C+G+A+T))*100 );
	
	    	writer.close();		
			}catch (IOException e) {System.err.println(e); System.exit(1); }   
	}
	
	public static void findRepeats(){
		for (int i=0;i<(lattice.length()-length_of_subseq);i=i+length_of_subseq){		
			for (int k=0;k<=(length_of_subseq-length_of_sub_subseq);k++){
				String current = lattice.substring(i+k, i+k+length_of_sub_subseq);
				int count;
				if (!common_sub.containsKey(current)){
					for (int j=i+length_of_subseq;j<(lattice.length()-length_of_subseq+1);j=j+length_of_subseq){					
						for (int m=0;m<=(length_of_subseq-length_of_sub_subseq);m++){
							String compared_to = (String) lattice.subSequence(j+m, j+m+length_of_sub_subseq);			
							if(current.equals(compared_to)){					
									if (!common_sub.containsKey(current)) count = 2;
									else count = common_sub.get(current) +1; 
									common_sub.put(current, count);			
							}
						}
					}
				}
			}
		}
		try{
			writer = new BufferedWriter(new FileWriter(new File("B.Repeats.txt")));				
			
			writer.append("Sequence length:     "+lattice.length()+"\n");
			writer.append("Number of repeats:     "+common_sub.size()+"\n");
			
			Enumeration<String> e1 = common_sub.keys();			
			int total_repeats = 0;
			while (e1.hasMoreElements()) 				
				total_repeats+=common_sub.get(e1.nextElement());	
			writer.append("Total no. of repeats: "+total_repeats+"\n\n");	
			for (int k=0;k<lattice.length()-length_of_subseq;k=k+length_of_subseq)
				writer.append(lattice.subSequence(k, k+length_of_subseq)+"|");
			
			Enumeration<String> e2 = common_sub.keys();	
			writer.append("\n\nList of Repeats:\n");	
			while (e2.hasMoreElements()) {
				String next = e2.nextElement(); 
				writer.append(next+"    "+common_sub.get(next)+"\n");	
			}			
			writer.close();
		}catch (IOException e) {System.err.println(e); System.exit(1); } 
	}
	
	public static void outputFillerStrands(){
		try{
			writer = new BufferedWriter(new FileWriter(new File("F.Fillers.txt")));			
			for (int i=0;i<2;i++){
				if (i%2==0){
					for (int j=0;j<h_scaffold_size/hap_size;j++){
						fillers[i][j] = reverse(complement(haps[i*33][j].substring(8, 24)));
						writer.append("\nF-"+((i+1)/10)+""+((i+1)%10)+"-"+((j+1)/10)+""+((j+1)%10)+"    "+fillers[i][j]+"  ");
					}
					writer.append("\n\n");	
				}else{
					for (int j=(h_scaffold_size/hap_size-1);j>=0;j--){
						fillers[i][((h_scaffold_size/hap_size-1)-j)] = reverse(((complement(haps[i*33][(h_scaffold_size/hap_size-1)-j].substring(8, 24)))));
						writer.append("\nF-"+((i+1)/10)+""+((i+1)%10)+"-"+((((h_scaffold_size/hap_size-1)-j)+1)/10)+""+((((h_scaffold_size/hap_size-1)-j)+1)%10)+"    "+fillers[i][(h_scaffold_size/hap_size-1)-j]+"  ");
					}
				}
			}
			writer.close();
		}catch (IOException e) { System.err.println(e);System.exit(1); }
	}
 	
	public static String complement (String sense){
		String antisense ="";
		for (int i=0;i<sense.length();i++){
	    		if      (sense.charAt(i) == 'A') antisense +="T";
	    		else if (sense.charAt(i) == 'T') antisense +="A";
	    		else if (sense.charAt(i) == 'C') antisense +="G";
	    		else if (sense.charAt(i) == 'G') antisense +="C";
	    		else if (sense.charAt(i) == 'a') antisense +="t";
	    		else if (sense.charAt(i) == 't') antisense +="a";
	    		else if (sense.charAt(i) == 'c') antisense +="g";
	    		else if (sense.charAt(i) == 'g') antisense +="c";
	    		else {System.out.println("Warning: contains non-DNA Sequence. complement() "+sense.charAt(i));antisense +=sense.charAt(i);}
	    	}
		return antisense;
	}
	
	public static String reverse (String forward){
		String backward ="";
		for (int i=forward.length();i>0;i--){
			backward+=forward.substring(i-1, i);
		}
		return backward;
	}

	public static boolean isLoggedAlready(String s){
		for (int i=0;i<common_sub.size();i++){
			if (common_sub.containsKey(s))return true;}						
		return false;
	}
	
	public static void outputCanvas(){
		try{
				writer = new BufferedWriter(new FileWriter(new File("G.Canvas.txt")));  
				writer.append("          ");
				for (int k=0;k<=h_scaffold_size;k=k+16){
					//writer.append(((k+10)/16)+"");
					writer.append(k+"              ");
				}			
				for (int i=0;i<lattice.length()/h_scaffold_size;i++){
					writer.append("\n"+((i+1)/10)+((i+1)%10)+"        ");
					for (int j=0;j<h_scaffold_size;j++)
						if ((j+1)%16==0) writer.append("|");
						else writer.append(".");
				}
				writer.close();
			}catch (IOException e) { System.err.println(e);System.exit(1); }
	}

	public static void canvasPDF() throws DocumentException, IOException {
		       
		Document document = new Document(PageSize.A3.rotate()); 
		PdfWriter.getInstance(document, new FileOutputStream("Canvas.pdf"));
		document.open();
		Paragraph p = new Paragraph ("") ;
		Font f = new Font();
		f.setSize(11);
		Font f2 = new Font();
		f2.setSize(3);
		//f2.setColor(0, 0, 0);
		//f.setFamily("Courier");
	    p.setFont(f);
	    p.setAlignment(Element.ALIGN_JUSTIFIED);
		BufferedReader reader = new BufferedReader(new FileReader("G.canvas.txt"));
		String line =""; 
		line = reader.readLine();
	
		while ((line = reader.readLine()) != null) {
			p.add(line); 
			document.add(p);
			p.clear();
			
			p.setFont(f2);
			p.add("L");
			document.add(p);
			p.clear();
			p.setFont(f);
			
		}
		reader.close();       
		document.close();		       		      
	}
	
	public static void staplesPDF01() throws DocumentException, IOException {
	       
		Document document = new Document(PageSize.B2.rotate()); 
		PdfWriter.getInstance(document, new FileOutputStream("Staples.01.B2.pdf"));
		document.open();
		Paragraph p = new Paragraph ("") ;
		Font f = new Font();
		f.setSize(12);
		f.setFamily("Courier");//("Courier", "Helvetica", "Times New Roman", "Symbol" or "ZapfDingbats").
	    p.setFont(f);
	    p.setIndentationLeft(130f);
		BufferedReader reader = new BufferedReader(new FileReader("Staples.01.B2.txt"));
		String line;
		while ((line = reader.readLine()) != null) {
			p.add(line); 
			document.add(p);
			p.clear();
			p.add(" ");
			document.add(p);
			p.clear();
		}
		reader.close();       
		document.close();		       		      
	}

	public static void staplesPDF02() throws DocumentException, IOException {
	       
		Document document = new Document(PageSize.B0.rotate()); 
		PdfWriter.getInstance(document, new FileOutputStream("Staples.02.B0.pdf"));
		document.open();
		Paragraph p = new Paragraph ("") ;
		Font f = new Font();
		f.setSize(18);
		f.setFamily("Courier");//("Courier", "Helvetica", "Times New Roman", "Symbol" or "ZapfDingbats").
	    p.setFont(f);
	    p.setIndentationLeft(130f);
		BufferedReader reader = new BufferedReader(new FileReader("Staples.02.B0.txt"));
		String line;
		int counter =1;
		while ((line = reader.readLine()) != null) {

			p.add(line); 
			document.add(p);
		
			p.clear();
			
			if (counter%4==0) {p.add(" ");document.add(p);}
			counter++;
			p.clear();
		}
		reader.close();       
		document.close();		       		      
	}

	public static void bridgesPDF01() throws DocumentException, IOException {
	       
		Document document = new Document(PageSize.B0.rotate()); 
		PdfWriter.getInstance(document, new FileOutputStream("Bridges.01.pdf"));
		document.open();
		Paragraph p = new Paragraph ("") ;
		Font f = new Font();
		f.setSize(11);
		f.setFamily("Courier");//("Courier", "Helvetica", "Times New Roman", "Symbol" or "ZapfDingbats").
	    p.setFont(f);
		
		BufferedReader reader = new BufferedReader(new FileReader("Bridges.01.txt"));
		String line;
		while ((line = reader.readLine()) != null) {

			p.add(line); 
			document.add(p);
		
			p.clear();
			
			p.add(" ");
			document.add(p);
			p.clear();
		}
		reader.close();       
		document.close();		       		      
	}
	
	public static void bridgesPDF02() throws DocumentException, IOException {
	       
		Document document = new Document(PageSize.B1.rotate()); 
		PdfWriter.getInstance(document, new FileOutputStream("Bridges.02.pdf"));
		document.open();
		Paragraph p = new Paragraph ("") ;
		Font f = new Font();
		f.setSize(11);
		f.setFamily("Courier");//("Courier", "Helvetica", "Times New Roman", "Symbol" or "ZapfDingbats").
	    p.setFont(f);
	    p.setIndentationLeft(200f);
	    p.setAlignment(Element.ALIGN_JUSTIFIED);
		BufferedReader reader = new BufferedReader(new FileReader("Bridges.02.B1.txt"));
		String line;
		//int counter =1;
		while ((line = reader.readLine()) != null) {

			p.add(line); 
			document.add(p);
		
			p.clear();
			
			//if (0==0) {p.add(" ");document.add(p);}
			//counter++;
			//p.clear();
		}
		reader.close();       
		document.close();		       		      
	}

	public static void hapsPDF() throws DocumentException, IOException {
	       
		Document document = new Document(PageSize.B0.rotate()); 
		PdfWriter.getInstance(document, new FileOutputStream("Haps.pdf"));
		document.open();
		Paragraph p = new Paragraph ("") ;
		Font f = new Font();
		f.setSize(19);
		f.setFamily("Courier");//("Courier", "Helvetica", "Times New Roman", "Symbol" or "ZapfDingbats").
	    p.setFont(f);
	    p.setIndentationLeft(10f);
	    p.setAlignment(Element.ALIGN_JUSTIFIED); 
		BufferedReader reader = new BufferedReader(new FileReader("Haps.txt"));
		String line;
		p.add(" "); 
		document.add(p);
		p.clear();
		p.add(" "); 
		document.add(p);
		p.clear();
		p.add(" "); 
		document.add(p);
		p.clear();
		p.add(" "); 
		document.add(p);
		p.clear();
		p.add(" "); 
		document.add(p);
		p.clear();
		while ((line = reader.readLine()) != null) {

			p.add(line); 
			document.add(p);
		
			p.clear();
			
			p.add(" ");
			document.add(p);
			//counter++;
			p.clear();
		}
		reader.close();       
		document.close();		       		      
	}
	
	public static String GC_content(String strand){
		double gc_count=0;
		for (int i=0;i<strand.length();i++){
			if(strand.charAt(i)=='G' ||strand.charAt(i)=='C' ||strand.charAt(i)=='g' ||strand.charAt(i)=='c')
				gc_count++;
		}
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format((gc_count/strand.length())*100)+"%";
		 
	}
	
}
