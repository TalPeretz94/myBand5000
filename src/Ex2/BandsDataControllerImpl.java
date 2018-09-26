package Ex2;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import com.sun.webkit.ThemeClient;

public class BandsDataControllerImpl implements BandsDataController {
	private BandsArrayList bandArr;

	private ListIterator<Band> iter;
	private BandsHashMap bandMap;
	private Stack<BandsDataCommand> commandsStackMem = new Stack<>();

	public Stack<BandsDataCommand> getCommandsStackMem() {
		return commandsStackMem;
	}

	private Band theLastBand;

	public Band getTheLastBand() {
		return theLastBand;
	}

	private Band bandAfterRemove;

	public Band getBandAfterRemove() {
		return bandAfterRemove;
	}

	private BandDataAccessObject bandData;

	public BandDataAccessObject getBandData() {
		return bandData;
	}

	public BandsArrayList getBandArr() {
		return bandArr;
	}

	public void setBandArr(BandsArrayList bandArr) {
		this.bandArr = bandArr;
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
		} else {
			theLastBand = null;
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
		Band tempLast;
		int tempIter;
		Comparator<Band> comparator;

		public Sort(Comparator<Band> comparator) {
			this.comparator = comparator;

		}

		@Override
		public void execute() {
			memCpy = new BandsArrayList(bandArr);
			tempLast = theLastBand;
			tempIter = iter.nextIndex();
			bandArr.Sort(comparator);
			iter = bandArr.listIterator();
			theLastBand = iter.next();
		}

		@Override
		public void undo() {
			bandArr = memCpy;
			theLastBand = tempLast;
			iter = bandArr.listIterator(tempIter);

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
			theLastBand = item;

		}

		@Override
		public void undo() {
			if (iter.hasPrevious()) {
				item = iter.previous();
			} else {
				iter = bandArr.listIterator(bandArr.size());
				item = iter.previous();

			}
			theLastBand = item;

		}

	}

	class Previous implements BandsDataCommand {

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
			theLastBand = item;

		}

		@Override
		public void undo() {
			if (iter.hasNext()) {
				item = iter.next();
			} else {
				iter = bandArr.listIterator();
				item = iter.next();
			}
			theLastBand = item;

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

		private Band theBand;

		private int index;

		public Remove() {

		}

		@Override
		public void execute() {
			index = bandArr.indexOf(theLastBand);

			bandArr.remove(theLastBand);
			bandMap.remove(theLastBand.getName(), theLastBand);

			theBand = theLastBand;
			if (index == bandArr.getSize()) {
				theLastBand = bandArr.get(0);
			} else {
				theLastBand = bandArr.get(index);

			}

		}

		@Override
		public void undo() {
			bandArr.add(index, theBand);
			bandMap.put(theBand.getName(), theBand);
			theLastBand = theBand;

		}
	}

}
