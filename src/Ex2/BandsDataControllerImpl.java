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
	private Band theLastBand;
	private BandDataAccessObject bandData;

	public BandsArrayList getBandArr() {
		return bandArr;
	}

	public BandsHashMap getBandMap() {
		return bandMap;
	}

	private static BandsDataControllerImpl single_instance = null;

	private BandsDataControllerImpl() {

		bandData = BandDataAccessObject.getInstance();
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

	@Override
	public Band previous() {
		Previous cmd = new Previous();
		cmd.execute();
		commandsStackMem.push(cmd);
		theLastBand = cmd.Get();
		return cmd.Get();
	}

	@Override
	public Band next() {
		Next cmd = new Next();
		cmd.execute();
		commandsStackMem.push(cmd);
		theLastBand = cmd.Get();
		return cmd.Get();
	}

	@Override
	public void sort(Comparator<Band> comparator) {
		Sort cmd = new Sort(comparator);
		cmd.execute();
		commandsStackMem.push(cmd);
	}

	@Override
	public void add(Band band) {

		Add cmd = new Add(band);
		cmd.execute();
		commandsStackMem.push(cmd);

	}

	@Override
	public void remove() {
		Remove cmd = new Remove();
		cmd.execute();
		commandsStackMem.push(cmd);

	}

	@Override
	public void undo() {
		if (!commandsStackMem.isEmpty()) {
			commandsStackMem.pop().undo();
		}

	}

	@Override
	public void revert() {
		while (!commandsStackMem.isEmpty()) {
			commandsStackMem.pop().undo();
		}

	}

	@Override
	public void save() throws IOException {
		bandData.saveBands(bandArr.toArray());

	}

	@Override
	public Band getBandByName(String name) {
		return bandMap.get(name);
	}

	class Sort implements BandsDataCommand {

		BandsArrayList memCpy;
		Comparator<Band> comparator;

		public Sort(Comparator<Band> comparator) {
			this.comparator = comparator;

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

	class Next implements BandsDataCommand {
		private Band item;

		public Band Get() {
			return item;
		}

		@Override
		public void execute() {
			if (iter.hasNext()) {
				item = iter.next();
			} else {
				iter = bandArr.listIterator();
				item = iter.next();
			}

		}

		@Override
		public void undo() {
			if (iter.hasPrevious()) {
				item = iter.previous();
			} else {
				iter = bandArr.listIterator(bandArr.size() - 1);
				item = iter.previous();
			}

		}

	}
	
	class Previous implements BandsDataCommand{
		
		private Band item;

		public Band Get() {
			return item;
		}

		@Override
		public void execute() {
			if (iter.hasPrevious()) {
				item = iter.previous();
			} else {
				iter = bandArr.listIterator(bandArr.size());
				item = iter.previous();
			}
			
		}

		@Override
		public void undo() {
			if (iter.hasNext()) {
				item = iter.next();
			} else {
				iter = bandArr.listIterator();
				item = iter.next();
			}
			
		}
		
	}

	class Add implements BandsDataCommand {
		private Band theBand;

		public Add(Band band) {
			this.theBand = band;
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

	class Remove implements BandsDataCommand {
		


		@Override
		public void execute() {
			bandArr.remove(theLastBand);
			bandMap.remove(theLastBand.getName(), theLastBand);

		}

		@Override
		public void undo() {
			bandArr.add(theLastBand);
			bandMap.put(theLastBand.getName(), theLastBand);

		}
	}

}

// class NameSort implements Comparator<Band>
// {
//
// @Override
// public int compare(Band o1, Band o2) {
// return (o1.getName().compareTo(o2.getName()));
//
// }
//
// }
// public static void SortByName(BandsDataControllerImpl bsc) {
//
// bsc.sort((o1,o2)-> {
// return (o1.getName().compareTo(o2.getName()));
// });
//
// }
