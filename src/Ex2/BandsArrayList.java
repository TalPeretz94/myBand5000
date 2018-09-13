package Ex2;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import Ex2.*;
public class BandsArrayList implements List<Band> {

	private int size = 0;

	private Band[] bandArr;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public BandsArrayList() {
		this.bandArr = new Band[this.size];

	}
	public BandsArrayList(BandsArrayList list) {
		System.arraycopy(list.toArray(), 0, bandArr, 0, list.getSize());
		size = list.getSize();
		
	}

	public BandsArrayList(int size) {
		this.size = size;
		this.bandArr = new Band[this.size];

	}
	
	public void Sort(Comparator <Band> comparator) {
		// bab sort 
		for (int i = 0 ; i< getSize(); i++)
		{
			for (int j = i ; i< getSize() - i; j++)
			{
				if (comparator.compare(bandArr[j], bandArr[j+1]) > 0) {
					Band temp = bandArr[j];
					bandArr[j] = bandArr[j+1];
					bandArr[j+1] =temp;
				}
			}
		}
		
	}

	@Override
	public boolean add(Band element) {
		bandArr = toEnlarge();
		bandArr[getSize()] = element;
		setSize(getSize() + 1);
		return true;

	}

	public Band[] toEnlarge() {
		if (getSize() >= bandArr.length) {

			Band[] temp = new Band[(bandArr.length * 2) + 1];
			System.arraycopy(bandArr, 0, temp, 0, bandArr.length);
			bandArr = temp;

		}
		return bandArr;

	}

	@Override
	public void add(int index, Band element) {
		if (index < 0 || index > getSize()) {
			throw new IndexOutOfBoundsException();
		}
		bandArr = toEnlarge();
		System.arraycopy(bandArr, index, bandArr, index + 1, getSize() - index);
		bandArr[index] = element;

	}

	@Override
	public boolean addAll(Collection<? extends Band> c) {
		boolean flag = true;
		for (Band element : c) {
			flag &= add(element);
		}
		return flag;

	}

	@Override
	public boolean addAll(int index, Collection<? extends Band> c) {
		if (index < 0 || index > getSize()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (c == null) {
			throw new NullPointerException();
		}
		int point = index;

		for (Band element : c) {
			add(point, element);
			point++;
		}
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < getSize(); i++) {
			bandArr[i] = null;
		}
		setSize(0);

	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Band get(int index) {
		if (index > getSize() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return bandArr[index];
	}

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (bandArr[i] == null)
					return i;
		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(bandArr[i]))
					return i;
		}
		return -1;

	}

	@Override
	public boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	public Iterator<Band> iterator() {
		return new myIterator();
	}

	private class myIterator implements Iterator<Band> {

		int current = 0;

		@Override
		public boolean hasNext() {
			return current < getSize();
		}

		@Override
		public Band next() {
			int i = current;
			Band[] tempArr = bandArr;
			if (i > getSize()) {
				
				throw new NoSuchElementException();
			}
			if (i > tempArr.length) {
				throw new ConcurrentModificationException();
			}
			current = i + 1;
			return (Band) tempArr[i];

		}

	}

	@SuppressWarnings("unused")
	@Override
	public int lastIndexOf(Object o) {
		for (int i = getSize() - 1; i >= 0; i--) {
			if (o.equals(bandArr[i])) {
			}
			return i;
		}
		return -1;
	}

	@Override
	public ListIterator<Band> listIterator() {
		return new ListItr(0);
	}

	@Override
	public ListIterator<Band> listIterator(int index) {
		 if (index < 0 || index > size)
	            throw new IndexOutOfBoundsException("Index: "+index);
		 return new ListItr(index);
	}
	
	
	private class ListItr extends myIterator implements ListIterator<Band>{
		int pos =0;
		
		 ListItr(int index) {
	            super();
	            pos = index;
	        }

		@Override
		public void add(Band e) {
			BandsArrayList.this.add(pos, e);
			pos++;
			
		}

		@Override
		public boolean hasNext() {
			return pos < bandArr.length;
		}

		@Override
		public boolean hasPrevious() {
			return pos > 0;
		}

		@Override
		public Band next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return bandArr[pos++];
		}

		@Override
		public int nextIndex() {
			return pos;
		}

		@Override
		public Band previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			return bandArr[--pos];
		}

		@Override
		public int previousIndex() {
			return pos-1;
		}

		@Override
		public void remove() {
			BandsArrayList.this.remove(pos--);
			
		}

		@Override
		public void set(Band e) {
			bandArr[pos]=e;
			
			
		}
		
	}

	@Override
	public boolean remove(Object o) {
		for (int i = 0; i < size; i++) {
            if (bandArr[i].equals(o)) {
                remove(i);
            }
        }
        return true;
	}

	@Override
	public Band remove(int index) {
		if (index > getSize()) {
			throw new IndexOutOfBoundsException();
		}
		
        int toRemove = size - index - 1;

        Band removedElement = (Band) bandArr[index];
        if (toRemove > 0) {
            System.arraycopy(bandArr, index + 1, bandArr, index, toRemove);
        }
        bandArr[getSize() - 1] = null;
        setSize(getSize()-1);
        return removedElement;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object object : c) {
			
			BandsArrayList.this.remove(object);
		}
		return true;
				
		
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		int oldSize = size;
        Iterator iterator = this.iterator();
        Object temp;
        while (iterator.hasNext()) {
        	temp = iterator.next();
            if (!BandsArrayList.this.contains(temp)) {
            	BandsArrayList.this.remove(temp);
            }
        }
        return getSize() != oldSize;
		
	}

	@Override
	public Band set(int index, Band element) {
		  if (index > getSize() || index < 0) {
	            throw new IndexOutOfBoundsException("Index : " + index);
	        }
		  Band oldValue=bandArr[index];
		  bandArr[index] = element;
		  
          return oldValue;
		
	}

	@Override
	public int size() {
		return getSize();
		
	}

	@Override
	public List<Band> subList(int fromIndex, int toIndex) {
		BandsArrayList subList = new BandsArrayList();

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        else if (fromIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        }
        else {
            for (int i = 0; i < getSize(); i++) {
                if (i >= fromIndex && i < toIndex) {
                    subList.add(bandArr[i]);
                }
            }
        }
        return subList;
	}

	@Override
	public Object[] toArray() {
		Object[] trimmedArr = (Object[]) new Object[getSize()];
        System.arraycopy(bandArr, 0, trimmedArr, 0, getSize());
        return trimmedArr;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	


}

