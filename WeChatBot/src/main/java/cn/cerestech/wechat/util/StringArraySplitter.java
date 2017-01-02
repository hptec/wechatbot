package cn.cerestech.wechat.util;

public class StringArraySplitter {

	private int blockSize = 50;

	private StringArraySplitter() {
	}

	private StringArraySplitter(int blockSize) {
		this.blockSize = blockSize;
	}

	public static StringArraySplitter on(int blockSize) {
		return new StringArraySplitter(blockSize);
	}

	public static StringArraySplitter on() {
		return new StringArraySplitter();
	}

	public String[][] split(String[] tArray) {
		int len = (tArray.length % blockSize == 0) ? (tArray.length / blockSize) : (tArray.length / blockSize + 1);
		String[][] ret = new String[len][];
		for (int i = 0; i < len; i++) {
			int srcPos = i * blockSize;
			int length = (i == len - 1) ? (tArray.length - srcPos) : blockSize;

			String[] dest = new String[length];
			System.arraycopy(tArray, srcPos, dest, 0, length);
			ret[i] = dest;
		}
		return ret;
	}

}
