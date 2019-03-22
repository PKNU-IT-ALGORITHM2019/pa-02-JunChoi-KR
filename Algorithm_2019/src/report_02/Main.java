package report_02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] x;
	static int[] y;
	static int[] shortestRoot;
	static int[] currentRoot;
	static double shortestDistance;
	static Scanner keyboard;
	static String fileReader;
	
	public static void main(String[] args){
		keyboard = new Scanner(System.in);
	 	
		System.out.println("텍스트 파일이 위치하는 폴더명 입력");
	 	String filePath = keyboard.next();
	
	 	for(int i = 0; i<7; i++) {
			read(filePath, i);
		}
  
         return;
	}
	
	public static void read(String filePath, int textFileNum) {
		 try{
				shortestDistance = Double.MAX_VALUE;
				
		        //파일 객체 생성
	            File file = new File(filePath + "\\input" + textFileNum + ".txt" );
	            System.out.println("현재 파일 : "+ filePath + "\\input" + textFileNum + ".txt" );
	            
	            //입력 스트림 생성
	            FileReader filereader = new FileReader(file);
	            
	            //입력 버퍼 생성
	            BufferedReader bufReader = new BufferedReader(filereader);
	            StringTokenizer line;
	            
	            N = Integer.parseInt(bufReader.readLine());
	            shortestRoot = new int[N];
	            currentRoot = new int[N];	            
			 	x = new int[N];
			 	y = new int[N];
			 	
	            //.readLine()은 끝에 개행문자를 읽지 않는다.
	            for(int i = 0; i<N; i++) {
	            	line = new StringTokenizer(bufReader.readLine());
	            	currentRoot[i] = i;
	            	x[i] = Integer.parseInt(line.nextToken(" "));
	            	y[i] = Integer.parseInt(line.nextToken(" "));
	            }
	            
	            bufReader.close();	
	        }catch (FileNotFoundException e) {
	        	System.out.println(e);
	        }catch(IOException e){
	            System.out.println(e);
	        }
		 
		 dfsTrees(0,0);
         printResult();
	}
	
	
	static double getDistance(int x1, int y1, int x2, int y2) {
			return Math.sqrt(Math.pow(Math.abs(x1-x2), 2) + Math.pow(Math.abs(y1-y2), 2));
	}
	
	
	static void swap(int orgIndex, int chgIndex) {
	//	System.out.println(Arrays.toString(currentRoot));
		int temp = currentRoot[orgIndex];
		currentRoot[orgIndex] = currentRoot[chgIndex];
		currentRoot[chgIndex] = temp;
	}
	

	static void dfsTrees(int startIndex, double currentDistance) {

		// 가지치기
		if (shortestDistance < currentDistance) {
			return;
		}
		
		// 깊이우선탐색을 끝낸 경우
		else if (startIndex == N) {
			double distanceSum = currentDistance;
			distanceSum = currentDistance;
			distanceSum += getDistance(x[currentRoot[N - 1]], y[currentRoot[N - 1]], x[currentRoot[0]], y[currentRoot[0]]); // 마지막 점에서 첫 점 까지 거리
		
			if(shortestDistance > distanceSum) {
				shortestDistance = distanceSum;
				System.arraycopy( currentRoot, 0, shortestRoot, 0, N);			
			}
			
			return;
		}

		// 깊이우선탐색 진행
		else {
			for (int i = startIndex; i < N; i++) {
				double distanceSum = currentDistance;
			
				// 첫 인덱스의 경우 계산을 하지 않아도 된다.
				if(startIndex != 0) {
					swap(startIndex, i);
					distanceSum += getDistance(x[currentRoot[startIndex]], y[currentRoot[startIndex]], x[currentRoot[startIndex - 1]], y[currentRoot[startIndex - 1]]);
				}
				
				dfsTrees(startIndex + 1, distanceSum);
			}
			return;
		}
	}
	
	
	static void printResult() {
		System.out.println("투어 : " + Arrays.toString(shortestRoot));
		System.out.println("최단거리 : " +shortestDistance);
		return;
	}
}