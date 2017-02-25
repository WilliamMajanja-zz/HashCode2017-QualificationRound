import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HashCodeProblem {

	public static final float ALPHA = 1f;

	public static void main(String[] args) throws IOException {
		resolve("me_at_the_zoo.in");
		resolve("trending_today.in");
		resolve("videos_worth_spreading.in");
		resolve("kittens.in");
	}

	public static void resolve(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(new File(fileName).toPath());

		int numeroCacheUtilizzate = 0;
		String inputData[] = getRow(lines);
		int nVideo = Integer.valueOf(inputData[0]);
		// System.out.println(nVideo);
		long nEndPoint = Long.valueOf(inputData[1]);
		// System.out.println(nEndPoint);
		long nRequest = Long.valueOf(inputData[2]);
		// System.out.println(nRequest);
		int nCacheServer = Integer.valueOf(inputData[3]);
		// System.out.println(nCacheServer);
		long nMegaCache = Long.valueOf(inputData[4]);
		// System.out.println(nMegaCache);

		inputData = getRow(lines);
		ArrayList<Integer> pesiVideo = new ArrayList<>();
		for (String input : inputData) {
			pesiVideo.add(Integer.valueOf(input));
		}
		// System.out.println(pesiVideo);

		List<EndPoint> endpoints = new ArrayList<>();
		for (int i = 0; i < nEndPoint; i++) {
			inputData = getRow(lines);

			EndPoint endPoint = new EndPoint();
			endPoint.dataCenterLatency = Integer.valueOf(inputData[0]);
			endPoint.nCacheConnected = Integer.valueOf(inputData[1]);
			endPoint.latenzeToCache = new int[nCacheServer];
			endPoint.idCaches = new int[endPoint.nCacheConnected];

			for (int j = 0; j < endPoint.nCacheConnected; j++) {
				inputData = getRow(lines);
				int latenzaToCache = Integer.valueOf(inputData[1]);
				int idCache = Integer.valueOf(inputData[0]);
				endPoint.idCaches[j] = idCache;
				endPoint.latenzeToCache[idCache] = endPoint.dataCenterLatency - latenzaToCache;
			}

			endpoints.add(endPoint);

			// System.out.println(endPoint);
		}

		CacheVideo[] cacheVideoArray = new CacheVideo[nCacheServer];

		for (int i = 0; i < cacheVideoArray.length; i++) {
			cacheVideoArray[i] = new CacheVideo();
			cacheVideoArray[i].risparmiVideo = new float[nVideo];
		}

		for (String line : lines) {
			String[] requestDescription = line.split(" ");
			int idVideo = Integer.valueOf(requestDescription[0]);
			int idEndPoint = Integer.valueOf(requestDescription[1]);
			int nRequestPartial = Integer.valueOf(requestDescription[2]);

			int[] idCaches = endpoints.get(idEndPoint).idCaches;
			for (int idCache : idCaches) {
				cacheVideoArray[idCache].risparmiVideo[idVideo] += calcolaPesoLocale(endpoints.get(idEndPoint),
						nRequestPartial, idCache);
			}
			// System.out.println("request wooooww " + idVideo + " " +
			// idEndPoint + " " + nRequestPartial);
		}

		int carlo = 0;
		List<String> lineOut = new ArrayList<>();
		PrintWriter out = new PrintWriter(fileName + ".out");
		for (CacheVideo cache : cacheVideoArray) {
			// Normalizzazione pesi
			for (int i = 0; i < cache.risparmiVideo.length; i++) {
				cache.risparmiVideo[i] = ALPHA * cache.risparmiVideo[i] / pesiVideo.get(i);
			}

			List<Video> videos = new ArrayList<>();
			for (int i = 0; i < cache.risparmiVideo.length; i++) {
				Video video = new Video();
				video.id = i;
				video.risparmio = cache.risparmiVideo[i];
				videos.add(video);
			}

			videos = videos.stream().sorted((v1, v2) -> Float.compare(v2.risparmio, v1.risparmio))
					.collect(Collectors.toList());
			// System.out.println(videos);

			cache.videoScelti = new ArrayList<>();
			int dimensioneOcc = 0;
			for (int i = 0; i < videos.size(); i++) {

				int idVideoScelto = videos.get(i).id;
				int dimensioneOccTemp = dimensioneOcc + pesiVideo.get(idVideoScelto);

				if (dimensioneOccTemp < nMegaCache) {
					cache.videoScelti.add(idVideoScelto);
					dimensioneOcc = dimensioneOccTemp;
				}

			}

			if (cache.videoScelti.size() > 0) {
				numeroCacheUtilizzate++;
				StringBuilder stringBuilder = new StringBuilder(String.valueOf(carlo));
				for (int video : cache.videoScelti) {
					stringBuilder.append(" ").append(video);
				}
				lineOut.add(stringBuilder.toString());
			}
			carlo++;

		}

		out.println(numeroCacheUtilizzate);
		for (String line : lineOut) {
			out.println(line);
		}

		out.close();
		// System.out.println(numeroCacheUtilizzate);

	}

	private static int calcolaPesoLocale(EndPoint endPoint, int nRequestPartial, int idCache) {
		return endPoint.latenzeToCache[idCache] * nRequestPartial;
	}

	private static String[] getRow(List<String> lines) {
		String[] input = lines.get(0).split(" ");
		lines.remove(0);
		return input;
	}

}