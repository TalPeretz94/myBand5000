package Ex2;
import java.io.IOException;
import java.rmi.server.SocketSecurityException;
import java.util.Collection;
import java.util.HashMap;

public class testtest {

	public static void main(String[] args) {
////		BandsArrayList arr = new BandsArrayList();
////		System.out.println(arr.size());
//		Band a = new Band(1, "aaa", 2, 2, "aa", true, "aa");
//		Band b = new Band(1, "bbb", 2, 2, "bb", true, "bb");
//		Band c = new Band(1, "ccc", 2, 2, "cc", true, "cc");
//		Band d = new Band(1, "ddd", 2, 2, "dd", true, "dd");
////		 arr.add(a);
////		 arr.add(b);
////		 System.out.println(arr.size());
////		 //arr.remove(1);
////		 System.out.println(arr.size());
////		 for(Band e : arr) {
////		 System.out.println(e.getName());
////		 }
////		
////		 arr.add(b);
////		 arr.add(c);
////		 arr.add(0, d);
////		 for(Band e : arr) {
////		 System.out.println(e.getName());
////		 }
//		
////
//		BandsHashMap zobi = new BandsHashMap();
//		zobi.put(a.getName(), a);
//		System.out.println(zobi.size());
//		zobi.put(b.getName(), a);
//		System.out.println(zobi.size());
//		System.out.println((zobi.get(a.getName())).getName());
//		zobi.put(a.getName(), a);
//		System.out.println(zobi.size());
//		
////		System.out.println(zobi.size());
////		zobi.put(a.getName(), a);
////		System.out.println(zobi.size());
////		System.out.println("should be true: " + zobi.containsKey(a.getName()));
////		System.out.println("should be false: " + zobi.containsKey(a.getName() + "S"));
////		zobi.put(b.getName(), b);
////		System.out.println(zobi.size());
////		System.out.println(zobi.remove(a.getName()));
////		System.out.println("should be false: " + zobi.containsKey(a.getName()));
////		System.out.println(zobi.size());
////
////		System.out.println(zobi.keySet());
////
////		Collection<Band> ii = zobi.values();
////		
////		for(int i = 0 ; i < ii.size(); i++) {
////			if (ii.toArray()[i] != null)
////				System.out.println(((Band) ii.toArray()[i]).getName());
////		}
//
//		// https://github.com/lutean/MyHashMap/blob/master/src/com/prepod/MyHashMap.java
//		
//		BandDataAccessObject zobi1 = BandDataAccessObject.getInstance();
//		BandsArrayList arr=null;
//		BandsHashMap map=null;
//		try {
//			arr=zobi1.readAllBands();
//			map=zobi1.getBandsMappedByName();
//		} catch (ClassNotFoundException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(arr.size());
//		for(int i=0;i<arr.size();i++) {
//			System.out.println(arr.get(i).getName());
//		}
		
		
		
		BandsDataControllerImpl zobi = BandsDataControllerImpl.getInstance();
		BandDataAccessObject kobaba = BandDataAccessObject.getInstance();
		BandsArrayList arr = new BandsArrayList();
		try {
			arr=kobaba.readAllBands();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(arr.get(0).getName());
//		System.out.println(arr.get(arr.getSize()-1).getName());
		
		System.out.println(zobi.next().getName());
		System.out.println(zobi.next().getName());
		System.out.println(zobi.next().getName());

		System.out.println(zobi.previous().getName());
		System.out.println(zobi.previous().getName());
		System.out.println(zobi.previous().getName());
		System.out.println(zobi.previous().getName());
		zobi.undo();
		System.out.println(zobi.previous().getName());
		
		
	}

}
