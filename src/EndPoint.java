import java.util.Arrays;

public class EndPoint {

	public int dataCenterLatency;
	public int nCacheConnected;
	public int[] latenzeToCache;
	public int[] idCaches;

	@Override
	public String toString() {
		return "EndPoint [dataCenterLatency=" + dataCenterLatency + ", nCacheConnected=" + nCacheConnected
				+ ", latenzeToCache=" + Arrays.toString(latenzeToCache) + "]";
	}

}
