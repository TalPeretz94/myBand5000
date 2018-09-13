package Ex2;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

public class BandsDataControllerImpl implements BandsDataController {
	private BandsArrayList bandArr;
	private ListIterator<Band> iter;
	private BandsHashMap bandMap;
	private Stack<BandsDataCommand> commandsStackMem = new Stack<>();
	private Band tempBand;

	public BandsArrayList getBandArr() {
		return bandArr;
	}



	public BandsHashMap getBandMap() {
		return bandMap;
	}

	

	private static BandsDataControllerImpl single_instance = null;

	private BandsDataControllerImpl() {
		
		BandDataAccessObject bandData = BandDataAccessObject.getInstance();
		try {
			bandArr = bandData.readAllBands();
			bandMap = bandData.getBandsMappedByName();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		iter = bandArr.listIterator();
	}

	public static BandsDataControllerImpl getInstance() {
		if (single_instance == null)
			single_instance = new BandsDataControllerImpl();
		return single_instance;
	}
	
//	class NameSort implements Comparator<Band> 
//	{
//
//		@Override
//		public int compare(Band o1, Band o2) {
//			return (o1.getName().compareTo(o2.getName()));
//			
//		}
//		
//	}
	public static void SortByName(BandsDataControllerImpl bsc)  {
		
		bsc.sort((o1,o2)-> {
			return (o1.getName().compareTo(o2.getName()));
		});
		
	}

	@Override
	public Band previous() {
		return null;
	}

	@Override
	public Band next() {
		 next cmd = new next();
		 cmd.execute();
		 commandsStackMem.push(cmd);
		return cmd.Get();
	}

	@Override
	public void sort(Comparator<Band> comparator) {
		sort cmd = new sort(comparator);
		cmd.execute();
		commandsStackMem.push(cmd);
	}

	@Override
	public void add(Band band) {
		add cmd = new add(band);
		cmd.execute();
		commandsStackMem.push(cmd);
		

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		if (!commandsStackMem.isEmpty())
		{
			commandsStackMem.pop().undo();
		}

	}

	@Override
	public void revert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Band getBandByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class sort implements BandsDataCommand{

		BandsArrayList memCpy;
		Comparator<Band> comparator;
		public sort (Comparator<Band> comparator) {
			this.comparator =comparator;
			
		}
		
		@Override
		public void execute() {
			memCpy = new BandsArrayList(bandArr);
			bandArr.sort(comparator);
		}

		@Override
		public void undo() {
			bandArr = memCpy;
		}
		
		
		
	}
	
	class next implements BandsDataCommand{
		private Band item ;
		
		public Band Get() {
			return item; 
		}
		@Override
		public void execute() {
			if(iter.hasNext()) {
				item = iter.next();
			}
			else
			{
				iter=  bandArr.listIterator(); 
				item = iter.next();
			}
			
		}

		@Override
		public void undo() {
			if(iter.hasPrevious()) {
				item = iter.previous();
			}
			else
			{
				iter=  bandArr.listIterator(bandArr.size()-1); 
				item = iter.previous();
			}
			
		}
		
	}
	
	class add implements BandsDataCommand{
		private Band theBand;
		
		public add(Band band) {
			this.theBand=band;
		}
		@Override
		public void execute() {
			bandArr.add(this.theBand);
			bandMap.put(theBand.getName(), theBand);
			
		}

		@Override
		public void undo() {
			bandArr.remove(theBand);
			bandMap.remove(theBand.getName(), theBand);
		}
		
	}
	
	class RemoveBandClass implements BandsDataCommand{
		private Band theBand;
		
		public RemoveBandClass(Band band){
			this.theBand=band;
			
		}

		@Override
		public void execute() {
			bandArr.add(this.theBand);
			bandMap.put(this.theBand.getName(), this.theBand);
			
		}

		@Override
		public void undo() {
			// TODO Auto-generated method stub
			
		}
	}

}



