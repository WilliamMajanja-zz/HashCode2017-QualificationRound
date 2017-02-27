import java.util.ArrayList;
import java.util.List;

public class CacheVideo {

	private float[] risparmiVideo;
	private List<Integer> videoChosen;

	public CacheVideo(int nVideo) {
		super();
		risparmiVideo = new float[nVideo];
		videoChosen = new ArrayList<>();
	}

	public float[] getRisparmiVideo() {
		return risparmiVideo;
	}

	public void setRisparmiVideo(float[] risparmiVideo) {
		this.risparmiVideo = risparmiVideo;
	}

	public List<Integer> getVideoChosen() {
		return videoChosen;
	}

	public void setVideoChosen(List<Integer> videoChosen) {
		this.videoChosen = videoChosen;
	}

}
