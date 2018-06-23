package aps2.reversestringfindmax;

public class ReverseStringFindMax {
	/**
	 * This function takes the string argument and reverses it.
	 * 
	 * @param str Input string.
	 * @return Reverse version of the string or null, if str is null.
	 */
	public String reverseString(String str) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if(str == null) return null;
		else if(str == "") return "";
		String temp = "";
		for(int i = 0; i < str.length(); i++){
			temp += str.toCharArray()[str.length()-i-1];
		}
		return temp;
	}

	/**
	 * This function finds and returns the maximum element in the given array.
	 * 
	 * @param arr Initialized input array.
	 * @return The maximum element of the given array, or the minimum Integer value, if array is empty.
	 */
	public int findMax(int[] arr){
		//throw new UnsupportedOperationException("You need to implement this function!");
		if(arr.length == 0) return Integer.MIN_VALUE;
		else if(arr.length == 1) return arr[0];
		int temp = arr[0];
		for(int i = 1; i < arr.length; i++){
			if(arr[i] > temp) temp = arr[i];
		}
		return temp;
	}
}
