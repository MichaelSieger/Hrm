package de.hswt.hrm.scheme.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorMap;
import java.awt.datatransfer.SystemFlavorMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.google.common.base.Throwables;

/**
 * This class convert DragData objects to a plattform specific representation.
 * 
 * @author Michael Sieger
 *
 */
public class DragDataTransfer extends ByteArrayTransfer{
	
	private static final DragDataTransfer INST = new DragDataTransfer();
	
	private static final String MIME_TYPE = "custom/dragData";
	private static final int ID = registerType(MIME_TYPE);
	
	public DragDataTransfer(){
		DataFlavor f = new DataFlavor(MIME_TYPE, "dragData");
		FlavorMap map = SystemFlavorMap.getDefaultFlavorMap();
		if(map instanceof SystemFlavorMap){
			SystemFlavorMap systemMap = (SystemFlavorMap) map;
			systemMap.addFlavorForUnencodedNative(MIME_TYPE, f);
		}
	}

	@Override
	protected String[] getTypeNames() {
		return new String[]{MIME_TYPE};
	}

	@Override
	protected int[] getTypeIds() {
		return new int[]{ID};
	}

	@Override
	protected void javaToNative(Object object, TransferData transferData) {
		try {
			super.javaToNative(toByteArray((DragData) object), transferData);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	private byte[] toByteArray(DragData data) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try(ObjectOutputStream oout = new ObjectOutputStream(bout)){
			oout.writeObject(data);
			return bout.toByteArray();
		}
	}
	
	private DragData fromByteArray(byte[] bytes) throws ClassNotFoundException, IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		try(ObjectInputStream oin = new ObjectInputStream(bin)){
			return (DragData) oin.readObject();
		}
	}

	@Override
	protected Object nativeToJava(TransferData transferData) {
		try {
			return fromByteArray((byte[]) super.nativeToJava(transferData));
		} catch (ClassNotFoundException | IOException e) {
			throw Throwables.propagate(e);
		} 
	}

	public static DragDataTransfer getInstance(){
		return INST;
	}

}
