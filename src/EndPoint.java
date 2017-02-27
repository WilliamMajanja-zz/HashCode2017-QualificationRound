import java.util.Arrays;

public class EndPoint {

	private int dataCenterLatency;
	private int nCacheConnected;
	private int[] latenciesToCache;
	private int[] idCaches;

	public EndPoint() {
		super();
	}

	public EndPoint(int dataCenterLatency, int nCacheConnected, int[] latenciesToCache, int[] idCaches) {
		super();
		this.dataCenterLatency = dataCenterLatency;
		this.nCacheConnected = nCacheConnected;
		this.latenciesToCache = latenciesToCache;
		this.idCaches = idCaches;
	}

	public int getDataCenterLatency() {
		return dataCenterLatency;
	}

	public void setDataCenterLatency(int dataCenterLatency) {
		this.dataCenterLatency = dataCenterLatency;
	}

	public int getnCacheConnected() {
		return nCacheConnected;
	}

	public void setnCacheConnected(int nCacheConnected) {
		this.nCacheConnected = nCacheConnected;
	}

	public int[] getLatenciesToCache() {
		return latenciesToCache;
	}

	public void setLatenciesToCache(int[] latenciesToCache) {
		this.latenciesToCache = latenciesToCache;
	}

	public int[] getIdCaches() {
		return idCaches;
	}

	public void setIdCaches(int[] idCaches) {
		this.idCaches = idCaches;
	}

	@Override
	public String toString() {
		return "EndPoint [dataCenterLatency=" + dataCenterLatency + ", nCacheConnected=" + nCacheConnected
				+ ", latenzeToCache=" + Arrays.toString(latenciesToCache) + "]";
	}

}
