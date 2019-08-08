import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class Cluster {
	public static void main(String[] args) throws IOException {
		try{
			String inFile = "input.txt";
			String outFile = "output.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));  
		
			BufferedReader reader =  new BufferedReader(new FileReader(new File(inFile))); 
			
			String line ="";
			int num_rows = -1;//skip the first line
			while ((line=reader.readLine()) != null) num_rows++;
				num_rows++;
			
			Scanner scan;
			
			
			int [][] rows = new int [num_rows-1][3];
			reader.close();
			reader = new BufferedReader(new FileReader(new File(inFile))); 
			line = reader.readLine();//skip first line
			int row_tracker = 0;
			while ((line = reader.readLine()) != null) {
				
				scan = new Scanner(line).useDelimiter(";");
				int attribute1 = scan.nextInt();
				int attribute2 = scan.nextInt();
				rows [row_tracker][0] = row_tracker+1;
				rows [row_tracker][1] = attribute1;
				rows [row_tracker][2] = attribute2;
				row_tracker++;				
			} 
			
		//	int [][] clusters = new int [3][num_rows];//first element = cluster ID (0, 1, or 2), second element = row number of data belonging to that cluster
			Hashtable<Integer, ArrayList<Integer>> clusters = new Hashtable<Integer,ArrayList <Integer>>();
			ArrayList<Integer> temp1 = new ArrayList<Integer>();
			ArrayList<Integer> temp2 = new ArrayList<Integer>();
			ArrayList<Integer> temp3 = new ArrayList<Integer>();
			//initialize clusters
			clusters.put(0, temp1);
			clusters.put(1, temp2);
			clusters.put(2, temp3);
			//assign data into three random groups (tentative clusters)			
			int i =0,j=0;
			for (i=0;i<(num_rows/3);i++)  clusters.get(0).add(rows[i][0]);
			for (j=i;j<2*(num_rows/3);j++) clusters.get(1).add(rows[j][0]);
		
			for (int k=j;k<num_rows-1;k++) clusters.get(2).add(rows[k][0]);

			//k=3, so there are three centroids			
			double centroid_01_a = 0,centroid_01_b=0,centroid_02_a=0,centroid_02_b=0,centroid_03_a=0,centroid_03_b=0;			
			
			int flag = 0;//to flag when to stop re-assigning data points, the flag = 1 when every data is in the correct cluster
			int runs=0;
			while (flag==0){//loop until no re-assigning of data results in better clustering, based on distance measure from current centroids
				runs++;
				flag =1; //exit of no more tuples are moved between clusters ==> all tuples are in the correct cluster
				
				//calculate centroids of current clusters
				//1.first cluster
				double sum1=0,sum2=0;
				for (int m=0;m<clusters.get(0).size();m++){
					sum1 +=  rows [clusters.get(0).get(m)-1][1];
					sum2 +=  rows [clusters.get(0).get(m)-1][2];
				}
				centroid_01_a = ((double) sum1)/((double)clusters.get(0).size());
				centroid_01_b = ((double) sum2)/((double)clusters.get(0).size());
				
				//2.second cluster
				sum1=0;sum2=0;
				for (int m=0;m<clusters.get(1).size();m++){
					//centroid_02
					sum1 +=  rows [clusters.get(1).get(m)-1][1];
					sum2 +=  rows [clusters.get(1).get(m)-1][2];
				}
				centroid_02_a = ((double) sum1)/((double)clusters.get(0).size());
				centroid_02_b = ((double) sum2)/((double)clusters.get(0).size());
				
				//3.third cluster
				sum1=0;sum2=0;				
				for (int m=0;m<clusters.get(2).size();m++){
					//centroid_02
					sum1 +=  rows [clusters.get(2).get(m)-1][1];
					sum2 +=  rows [clusters.get(2).get(m)-1][2];
				}
				centroid_03_a = ((double) sum1)/((double)clusters.get(2).size());
				centroid_03_b = ((double) sum2)/((double)clusters.get(2).size());
				
				// Re-assigning of a tuple to a another cluster if that tuple is closer to it (based on Euclidean distance between each tuple and centroid of alternative cluster)
				
				
				double attr1=0,attr2=0;
				double Euclid_distance_current    = 0;
				double Euclid_distance_alternative_1 = 0;
				double Euclid_distance_alternative_2 = 0;
				
				// examining tuples in the first cluster
				for (int m=0;m<clusters.get(0).size();m++){
					attr1 =  rows [clusters.get(0).get(m)-1][1];
					attr2 =  rows [clusters.get(0).get(m)-1][2];
					// distance between the tuple of the centroid of the current 
					Euclid_distance_current 	= Math.sqrt(Math.pow((centroid_01_a-attr1), 2)-Math.pow((centroid_01_b-attr2), 2));
					
					// check if the second cluster is more appropriate for this particular point, if so, move it to that cluster					
					Euclid_distance_alternative_1 = Math.sqrt(Math.pow((centroid_02_a-attr1), 2)-Math.pow((centroid_02_b-attr2), 2));
					
					// check if the third cluster is more appropriate for this particular point, if so, move it to that cluster
					Euclid_distance_alternative_2 = Math.sqrt(Math.pow((centroid_03_a-attr1), 2)-Math.pow((centroid_03_b-attr2), 2));
					
					
					if       ((Euclid_distance_alternative_1 < Euclid_distance_current) && (Euclid_distance_alternative_1 < Euclid_distance_alternative_2)){												
									//re-assign point to cluster 2 
										//1.remove it from current cluster
										clusters.get(0).remove(m);
										//2.add it to the second cluster
										clusters.get(1).add(m);
										flag=0;
						
					}else if ((Euclid_distance_alternative_2 < Euclid_distance_current) && (Euclid_distance_alternative_2 < Euclid_distance_alternative_1)){
									//re-assign point to cluster 2 
										//1.remove it from current cluster
										clusters.get(0).remove(m);
										//2.add it to the third cluster
										clusters.get(2).add(m);
										flag=0;
					}//else the tuple is already in the current cluster		
			}
				// examining tuples in the second cluster
				for (int m=0;m<clusters.get(1).size();m++){
					attr1 =  rows [clusters.get(1).get(m)-1][1];
					attr2 =  rows [clusters.get(1).get(m)-1][2];
					// distance between the tuple of the centroid of the current 
					Euclid_distance_current 	= Math.sqrt(Math.pow((centroid_02_a-attr1), 2)-Math.pow((centroid_02_b-attr2), 2));
					
					// check if the first cluster is more appropriate for this particular point, if so, move it to that cluster					
					Euclid_distance_alternative_1 = Math.sqrt(Math.pow((centroid_01_a-attr1), 2)-Math.pow((centroid_01_b-attr2), 2));
					
					// check if the third cluster is more appropriate for this particular point, if so, move it to that cluster
					Euclid_distance_alternative_2 = Math.sqrt(Math.pow((centroid_03_a-attr1), 2)-Math.pow((centroid_03_b-attr2), 2));
					
					
					if       ((Euclid_distance_alternative_1 < Euclid_distance_current) && (Euclid_distance_alternative_1 < Euclid_distance_alternative_2)){												
									//re-assign point to cluster 1
										//1.remove it from current cluster
										clusters.get(1).remove(m);
										//2.add it to the first cluster
										clusters.get(0).add(m);
										flag=0;
						
					}else if ((Euclid_distance_alternative_2 < Euclid_distance_current) && (Euclid_distance_alternative_2 < Euclid_distance_alternative_1)){
									//re-assign point to cluster 2 
										//1.remove it from current cluster
										clusters.get(1).remove(m);
										//2.add it to the third cluster
										clusters.get(2).add(m);
										flag=0;
					}//else the tuple is already in the current cluster	
				
			}
				// examining tuples in the third cluster
				for (int m=0;m<clusters.get(2).size();m++){
					attr1 =  rows [clusters.get(2).get(m)-1][1];
					attr2 =  rows [clusters.get(2).get(m)-1][2];
					// distance between the tuple of the centroid of the current 
					Euclid_distance_current 	= Math.sqrt(Math.pow((centroid_03_a-attr1), 2)-Math.pow((centroid_03_b-attr2), 2));
					
					// check if the first cluster is more appropriate for this particular point, if so, move it to that cluster					
					Euclid_distance_alternative_1 = Math.sqrt(Math.pow((centroid_01_a-attr1), 2)-Math.pow((centroid_01_b-attr2), 2));
					
					// check if the second cluster is more appropriate for this particular point, if so, move it to that cluster
					Euclid_distance_alternative_2 = Math.sqrt(Math.pow((centroid_02_a-attr1), 2)-Math.pow((centroid_02_b-attr2), 2));
					
					
					if       ((Euclid_distance_alternative_1 < Euclid_distance_current) && (Euclid_distance_alternative_1 < Euclid_distance_alternative_2)){												
									//re-assign point to cluster 1
										//1.remove it from current cluster
										clusters.get(2).remove(m);
										//2.add it to the first cluster
										clusters.get(0).add(m);
										flag=0;
						
					}else if ((Euclid_distance_alternative_2 < Euclid_distance_current) && (Euclid_distance_alternative_2 < Euclid_distance_alternative_1)){
									//re-assign point to cluster 2 
										//1.remove it from current cluster
										clusters.get(2).remove(m);
										//2.add it to the second cluster
										clusters.get(1).add(m);
										flag=0;
					}//else the tuple is already in the current cluster	
				
			}
			}
		
		DecimalFormat decFor = new DecimalFormat("0.00");
		writer.append("Number of iterations: "+runs+"\n\n");
		writer.append("First cluster, centroid = ["+decFor.format(centroid_01_a)+","+decFor.format(centroid_01_b)+"]:\n\n\tTuples: ");
		for (int h=0;h<clusters.get(0).size();h++){
			writer.append("("+rows [clusters.get(0).get(h)-1][1]+", "+rows[clusters.get(0).get(h)-1][2]+"), ");
			
		}
		writer.append("\n\n\n\n\nSecond cluster, centroid = ["+decFor.format(centroid_02_a)+","+decFor.format(centroid_02_b)+"]:\n\n\tTuples: ");
		for (int h=0;h<clusters.get(1).size();h++){
			writer.append("("+rows [clusters.get(1).get(h)-1][1]+", "+rows[clusters.get(1).get(h)-1][2]+"), ");
			
		}
		writer.append("\n\n\n\n\nThird cluster, centroid = ["+decFor.format(centroid_03_a)+","+decFor.format(centroid_03_b)+"]:\n\n\tTuples:  ");
		for (int h=0;h<clusters.get(2).size();h++){
			writer.append("("+rows [clusters.get(2).get(h)-1][1]+", "+rows[clusters.get(2).get(h)-1][2]+"), ");
			
		}
		reader.close();
		writer.close();
		System.out.println("Done .. output.txt has been generated");		
		}catch (IOException e) { System.err.println(e);System.exit(1); } 
	}
	
}
