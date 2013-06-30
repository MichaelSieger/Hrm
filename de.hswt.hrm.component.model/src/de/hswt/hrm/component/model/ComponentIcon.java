package de.hswt.hrm.component.model;

import java.util.Arrays;

public final class ComponentIcon {
	private final int id;
	private final byte[] blob;
	private final String filename;
	
	public ComponentIcon(final byte[] blob, final String filename) {
		this(-1, blob, filename);
	}
	
	public ComponentIcon(final int id, final byte[] blob, final String filename) {
		this.id = id;
		this.blob = blob;
		this.filename = filename;
	}

	public int getId() {
		return id;
	}

	public byte[] getBlob() {
		return blob;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(blob);
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComponentIcon other = (ComponentIcon) obj;
		if (!Arrays.equals(blob, other.blob))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
