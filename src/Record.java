import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Record implements Serializable {
	private static final long serialVersionUID = 175042325L;
	private String higherName;    //�߼�����
	private int higherScore;       //�߼���ʱ
	private String medialName;    //�м�����
	private int medialScore;      //�м���ʱ
	private String lowerName;     //�ͼ�����
	private int lowerScore;       //�ͼ���ʱ
	public Record(){		
	}
	public Record(String higherName, int higherScore, String medialName,
			int medialScore, String lowerName, int lowerScore) {
		super();
		this.higherName = higherName;
		this.higherScore = higherScore;
		this.medialName = medialName;
		this.medialScore = medialScore;
		this.lowerName = lowerName;
		this.lowerScore = lowerScore;
	}
	public String getHigherName() {
		return higherName;
	}
	public void setHigherName(String higherName) {
		this.higherName = higherName;
	}
	public int getHigherScore() {
		return higherScore;
	}
	public void setHigherScore(int higherScore) {
		this.higherScore = higherScore;
	}
	public String getMedialName() {
		return medialName;
	}
	public void setMedialName(String medialName) {
		this.medialName = medialName;
	}
	public int getMedialScore() {
		return medialScore;
	}
	public void setMedialScore(int medialScore) {
		this.medialScore = medialScore;
	}
	public String getLowerName() {
		return lowerName;
	}
	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}
	public int getLowerScore() {
		return lowerScore;
	}
	public void setLowerScore(int lowerScore) {
		this.lowerScore = lowerScore;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
