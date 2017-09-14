package ontology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.RDFRDBException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;

public class animal {
	public static final String strDriver = "com.mysql.jdbc.Driver"; // path of
	public static final String strURL = "jdbc:mysql://localhost/ontodb"; // URL
	public static final String strUser = "root"; // database user id
	public static final String strPassWord = "root"; // database password
	public static final String strDB = "MySQL"; // database type

	public static void main(String[] args) {
		try {
			// 创建一个数据库连接
			IDBConnection conn = new DBConnection(strURL, strUser, strPassWord, strDB);
			// 加载数据库驱动类，需要处理异常
			try {
				Class.forName(strDriver);
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException, Driver is not available...");
			}
			// 使用数据库连接参数创建一个模型制造器
			ModelMaker maker = ModelFactory.createModelRDBMaker(conn);
			// 创建一个默认模型，命名为 MyOntology
			Model defModel = maker.createModel("MyOntology");
			// 准备需要存入数据库的本体文件，建立输入文件流
			FileInputStream inputSreamfile = null;
			try {
				inputSreamfile = new FileInputStream("E:\\研究生课程作业\\ontologyWorkspace\\annimalNew.owl");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Ontology File is not available...");
			}
			InputStreamReader in = null;
			try {
				in = new InputStreamReader(inputSreamfile, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 读取文件
			defModel.read(in, null);
			// 关闭输入流读取器
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 执行数据转换，将本体数据存入数据库
			defModel.commit();
			// 关闭数据库连接
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (RDFRDBException e) {
			System.out.println("Exceptions occur...");
		}
	}
}