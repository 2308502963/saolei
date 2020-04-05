import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RecordDao {
	private String fileName;
	public RecordDao(){
		fileName = "record/record.dat";
	}
	/**
	 * 从文件中读取一个Record对象	
	 */	
	public Record readRecord(){
		FileInputStream  fis=null;
		ObjectInputStream ois=null;
		Record record = null;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			return null;
		}
		try {
			ois = new ObjectInputStream(fis);
			record = (Record) ois.readObject();
		}  catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("111");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return record;
	}	
	/**
	 * 向文件输出一个Record对象
	 */
	public void writeRecord(Record record){
		FileOutputStream  fos=null;
		ObjectOutputStream oos=null;
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(record);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
