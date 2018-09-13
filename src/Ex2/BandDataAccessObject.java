package Ex2;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import Ex2.*;

public class BandDataAccessObject implements BandDataAccess {

	private static BandDataAccessObject single_instance = null;
	private static final String FILENAME = "bands.bin";

	private BandDataAccessObject() {
	}

	public static BandDataAccessObject getInstance() {
		if (single_instance == null)
			single_instance = new BandDataAccessObject();

		return single_instance;
	}

	@Override
	public BandsArrayList readAllBands() throws IOException, ClassNotFoundException {

		ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILENAME));

		BandsArrayList arr = new BandsArrayList();

		Object[] obj = null;
		try {

			obj = (Object[]) input.readObject();

		} catch (EOFException e) {

		}
		for (int i = 0; i < obj.length; i++) {
			arr.add((Band) obj[i]);
		}

		input.close();
		return arr;
	}

	@Override
	public BandsHashMap getBandsMappedByName() throws IOException, ClassNotFoundException {

		ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILENAME));

		BandsHashMap myMap = new BandsHashMap();

		Object[] obj = null;
		try {

			obj = (Object[]) input.readObject();

		} catch (EOFException e) {

		}
		for (int i = 0; i < obj.length; i++) {
			
			myMap.put(((Band) obj[i]).getName(), (Band) obj[i]);
		}

		input.close();
		return myMap;
	}

	@Override
	public void saveBands(Band[] bands) throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILENAME));
		output.writeObject(bands);

		output.close();

	}

}
