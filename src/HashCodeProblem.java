import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HashCodeProblem {

	public static final float ALPHA = 0.5f;

	private HashCodeProblem(){
		super();
	}
	
	public static void main(String[] args) throws IOException {
		resolve("problem/data/me_at_the_zoo.in");
		resolve("problem/data/trending_today.in");
		resolve("problem/data/videos_worth_spreading.in");
		resolve("problem/data/kittens.in");
	}

	public static void resolve(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(new File(fileName).toPath());

		int numberCacheUsed = 0;
		String[] inputData = getRow(lines);
		int nVideo = Integer.parseInt(inputData[0]);
		long nEndPoint = Long.parseLong(inputData[1]);
		long nRequest = Long.parseLong(inputData[2]);
		int nCacheServer = Integer.parseInt(inputData[3]);
		long nMegaCache = Long.parseLong(inputData[4]);

		inputData = getRow(lines);
		ArrayList<Integer> weightVideos = new ArrayList<>();
		for (String input : inputData) {
			weightVideos.add(Integer.valueOf(input));
		}

		List<EndPoint> endpoints = new ArrayList<>();
		for (int i = 0; i < nEndPoint; i++) {
			inputData = getRow(lines);

			EndPoint endPoint = new EndPoint();
			Integer dataCenterLatency = Integer.parseInt(inputData[0]);
			endPoint.setDataCenterLatency(dataCenterLatency);
			endPoint.setnCacheConnected(Integer.parseInt(inputData[1]));
			int[] latenciesToCache = new int[nCacheServer];
			endPoint.setLatenciesToCache(latenciesToCache);
			int[] idCaches = new int[endPoint.getnCacheConnected()];
			endPoint.setIdCaches(idCaches);

			for (int j = 0; j < endPoint.getnCacheConnected(); j++) {
				inputData = getRow(lines);
				int latenzaToCache = Integer.parseInt(inputData[1]);
				int idCache = Integer.parseInt(inputData[0]);
				idCaches[j] = idCache;
				latenciesToCache[idCache] = dataCenterLatency - latenzaToCache;
			}

			endpoints.add(endPoint);
		}

		CacheVideo[] cacheVideoArray = new CacheVideo[nCacheServer];

		for (int i = 0; i < cacheVideoArray.length; i++) {
			cacheVideoArray[i] = new CacheVideo(nVideo);
		}

		for (String line : lines) {
			String[] requestDescription = line.split(" ");
			int idVideo = Integer.parseInt(requestDescription[0]);
			int idEndPoint = Integer.parseInt(requestDescription[1]);
			int nRequestPartial = Integer.parseInt(requestDescription[2]);

			int[] idCaches = endpoints.get(idEndPoint).getIdCaches();
			for (int idCache : idCaches) {
				cacheVideoArray[idCache].getRisparmiVideo()[idVideo] += calculateLocalWeight(endpoints.get(idEndPoint),
						nRequestPartial, idCache);
			}
		}

		int carlo = 0;
		List<String> lineOut = new ArrayList<>();
		PrintWriter out = new PrintWriter(fileName + ".out");
		for (CacheVideo cache : cacheVideoArray) {

			float[] risparmiVideo = cache.getRisparmiVideo();
			// Normalization weight
			for (int i = 0; i < risparmiVideo.length; i++) {
				risparmiVideo[i] = ALPHA * risparmiVideo[i] / weightVideos.get(i);
			}

			List<Video> videos = new ArrayList<>();
			for (int i = 0; i < risparmiVideo.length; i++) {
				Video video = new Video(i, risparmiVideo[i]);
				videos.add(video);
			}

			videos = videos.stream().sorted((v1, v2) -> Float.compare(v2.getRisparmio(), v1.getRisparmio()))
					.collect(Collectors.toList());

			List<Integer> videoChosen = cache.getVideoChosen();
			int dimOcc = 0;
			for (int i = 0; i < videos.size(); i++) {

				int idVideoChosen = videos.get(i).getId();
				int dimOccTemp = dimOcc + weightVideos.get(idVideoChosen);

				if (dimOccTemp < nMegaCache) {
					videoChosen.add(idVideoChosen);
					dimOcc = dimOccTemp;
				}

			}

			if (!videoChosen.isEmpty()) {
				numberCacheUsed++;
				StringBuilder stringBuilder = new StringBuilder(String.valueOf(carlo));
				for (int video : videoChosen) {
					stringBuilder.append(" ").append(video);
				}
				lineOut.add(stringBuilder.toString());
			}
			carlo++;
		}

		// Print the result
		out.println(numberCacheUsed);
		for (String line : lineOut) {
			out.println(line);
		}
		out.close();

	}

	private static int calculateLocalWeight(EndPoint endPoint, int nRequestPartial, int idCache) {
		return endPoint.getLatenciesToCache()[idCache] * nRequestPartial;
	}

	private static String[] getRow(List<String> lines) {
		String[] input = lines.get(0).split(" ");
		lines.remove(0);
		return input;
	}

}
