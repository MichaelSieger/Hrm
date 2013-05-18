package de.hswt.hrm.scheme.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.google.common.base.Throwables;

public class DragDataTransfer extends ByteArrayTransfer{
	
	private static final String NAME = "dragData";
	private static final int ID = registerType(NAME);

	@Override
	protected String[] getTypeNames() {
		return new String[]{NAME};
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
	
	

}
