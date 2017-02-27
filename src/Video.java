
public class Video {

	private int id;
	private float risparmio;

	public Video(int id, float risparmio) {
		super();
		this.id = id;
		this.risparmio = risparmio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getRisparmio() {
		return risparmio;
	}

	public void setRisparmio(float risparmio) {
		this.risparmio = risparmio;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", risparmio=" + risparmio + "]";
	}

}
