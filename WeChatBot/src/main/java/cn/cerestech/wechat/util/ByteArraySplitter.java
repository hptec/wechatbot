package cn.cerestech.wechat.util;

public class ByteArraySplitter {

	private int blockSize = 526091;

	private ByteArraySplitter() {
	}

	private ByteArraySplitter(int blockSize) {
		this.blockSize = blockSize;
	}

	public static ByteArraySplitter on(int blockSize) {
		return new ByteArraySplitter(blockSize);
	}

	public static ByteArraySplitter on() {
		return new ByteArraySplitter();
	}

	public byte[][] split(byte[] tArray) {
		int len = (tArray.length % blockSize == 0) ? (tArray.length / blockSize) : (tArray.length / blockSize + 1);
		byte[][] ret = new byte[len][];
		for (int i = 0; i < len; i++) {
			int srcPos = i * blockSize;
			int length = (i == len - 1) ? (tArray.length - srcPos) : blockSize;

			byte[] dest = new byte[length];
			System.arraycopy(tArray, srcPos, dest, 0, length);
			ret[i] = dest;
		}
		return ret;
	}

}
