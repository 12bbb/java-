package com.x.testagain;

import java.io.*;

import java.util.*;

public class App {//文件管理器

	public static void main(String[] args) throws Exception {
		//创建一个菜单对象，调用菜单的StartMenu方法
		Menu menu=new Menu();
		menu.StartMenu();
	}
}

class CreateFile { // 创建文件夹
	CreateFile() throws IOException {
		System.out.println("Where to create?");
		Scanner s=new Scanner(System.in);
		String CurrentDirectory = s.next();// 读取用户输入的目录
		File file = new File(CurrentDirectory);// 根据用户给出的目录创建文件
		if (file.exists()) {// 目录存在时，创建子目录
			System.out.println("Enter the name:");
			String Name = s.next();
			File f = new File(CurrentDirectory + File.separator + Name); // 实例化File类的对象
			f.mkdir();// 创建此目录下指定的文件名
			System.out.println("success to create!");

		} 
		else
			System.out.println("the directory isn't exist");
		
	}
}

class DeleteFile {// 删除文件夹
	File path;// 文件路径
	public DeleteFile(File path) {// 删除文件的路径等于文件路径
		this.path = path;
	}

	void Delete(File path) {// 删除文件
		File filelist[] = path.listFiles();// 获取该目录下所有文件和目录的绝对路径
		int listlen = filelist.length;
		for (int i = 0; i < listlen; i++) {
			if (filelist[i].isDirectory()) {// 判断是否为目录
				Delete(filelist[i]);
			} else {
				filelist[i].delete();
			}
		}
		path.delete(); // 删除当前目录
	}

	void Success() { // 删除成功时，返回如下信息
		System.out.println("Success to delete!");
	}
}

class EnterDirectory {// 进入文件夹
	EnterDirectory(String path) throws IOException {
		String[] cmd = { "explorer.exe", path };
		Runtime.getRuntime().exec(cmd);
	}
}

class ListDirectory {// 当前文件夹下内容的罗列
	ListDirectory(String path) {
		File dirFile = new File(path);
		if (!dirFile.exists() || (!dirFile.isDirectory())) {// 如果路径对应的文件不存在，或者不是一个文件夹则退出
			System.out.println("List failed! cannot find the directory:" + path);

		}
		System.out.println(path + " contains below directorise or files:");
		String[] files = dirFile.list();// list方法返回该目录下的所有文件（包括目录）的文件名，文件名不包含路径信息
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
		}

	}

}

class CopyFile {// 拷贝文件
	void CopeFile(String source, String dest) throws IOException {
		File in = new File(source); // 生成源文件对象
		File out = new File(dest); // 生成目标文件对象
		// 生成文件流对象
		FileInputStream inFile = new FileInputStream(in);
		FileOutputStream outFile = new FileOutputStream(out);
		// 创建字节数组,用于读取文件内容
		byte[] buffer = new byte[1024];
		int i = 0;
		while ((i = inFile.read(buffer)) != -1) {// 如果源文件未读完，继续写入文件的操作
			outFile.write(buffer, 0, i);
		}
		// 关闭文件流
		inFile.close();
		outFile.close();
		// 输出拷贝成功的信息
		System.out.print("Success to copy!");
	}

}

class CopyDirectory {// 拷贝文件夹
	public void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs();// 如果文件夹不存在，则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();// list方法返回该目录下的所有文件（包括目录）的文件名，文件名不包含路径信息
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {// 如果temp是一个文件
					// 生成文件流对象
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(
							newPath + File.separator + (temp.getName()).toString());
					// 创建字节数组
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {// 如果源文件未读完，继续写入文件的操作
						output.write(b, 0, len);
					}
					output.flush();// 清空缓存
					// 关闭文件流
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果temp是一个文件夹
					copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
				}
			}
		} catch (Exception e) {// 处理异常
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	public void Success() {
		System.out.println("Success to copy the directory");
	}

}


class FileEncAndDec {//加密解密指定文件
	int numOfEncAndDec = 0x99; // 加密解密秘钥
	int dataOfFile = 0;
	FileEncAndDec() throws Exception {
		//输出提示信息
		System.out.println("encrypt:input source file and target file:");
		Scanner scan=new Scanner(System.in);
		//获得需要加密的文件路径和加密后文件的存储路径
		String sourcee = scan.next();
		String targete = scan.next();
		//新建两个File对象
		File srcFile = new File(sourcee);
		File encFile=new File(targete);
		try{
			EncFile(srcFile, encFile); // 加密操作
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("encryption is complete!");//加密成功，输出结果
		System.out.println("decrypt:input source file and target file:");
		//获得需要解密的文件路径和解密后文件的存储路径
		String sourced = scan.next();
		String targetd = scan.next();
		File decFile = new File(targetd);
		File encFilee=new File(sourced);
		try{
			DecFile(encFilee, decFile); // 解密操作
		}catch(
		Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("decryption is complete!");//解密成功，输出结果
		
		
	}
	//定义加密函数
	void EncFile(File srcFile, File encFile) throws Exception {
		if (!srcFile.exists()) {//需要加密的文件不存在，输出错误信息
			System.out.println("source file not exist");
			return;
		}

		if (!encFile.exists()) {//如果加密后的文件不存在，新建一个文件
			System.out.println("encrypt file created");
			encFile.createNewFile();
		}
		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(encFile);

		while ((dataOfFile = fis.read()) > -1) {
			fos.write(dataOfFile ^ numOfEncAndDec);//采用异或算法进行加密
		}
		fis.close();
		fos.flush();
		fos.close();
	}
	//定义解密函数
	void DecFile(File encFilee, File decFile) throws Exception {
		if(!encFilee.exists()){	//如果需要解密的文件不存在，输出错误信息
			System.out.println("encrypt file not exist");
			return;
		}
		if(!decFile.exists()){//如果解密后的文件不存在，新建一个文件
			System.out.println("decrypt file created");
			decFile.createNewFile();
		}
		InputStream fis  = new FileInputStream(encFilee);
		OutputStream fos = new FileOutputStream(decFile);
		while ((dataOfFile = fis.read()) > -1) {
			fos.write(dataOfFile^numOfEncAndDec);//采用异或算法解密
		}

		fis.close();
		fos.flush();
		fos.close();
	}
}

class Menu{//主菜单
	void StartMenu() throws Exception{
		//打印提示信息
		System.out.println("Please choose what you wan to do:");
		System.out.println("'1':Create a directory");
		System.out.println("'2':Delete a directory");
		System.out.println("'3':Enter a directory");
		System.out.println("'4':List a directory");
		System.out.println("'5':Copy a file");
		System.out.println("'6':Copy a directory");
		System.out.println("'7':EncAndDec a directory");//加密并解密一个文件
		System.out.println("'0':Exit");
		//根据用户输入的数字判断应该执行的操作
		Scanner sc=new Scanner(System.in);
		int a=sc.nextInt();
		while(true) {
			switch(a) {
			case 1:
				System.out.println("you choose 1");
				new CreateFile();
				break;
			case 2:
				System.out.println("you choose 2");
				System.out.println("input the directory name that you want to delete:");
				File pathtodele=new File(sc.next());
				if(pathtodele.exists()) {
					DeleteFile DeleteDirectory=new DeleteFile(pathtodele);
					DeleteDirectory.Delete(DeleteDirectory.path);
					DeleteDirectory.Success();
				}else System.out.println("Cannot find the directory:"+pathtodele);
				break;
			case 3:
				System.out.println("you choose 3");
				System.out.println("input the directory that you want to enter:");
				String pathtoenter=sc.next();
				new EnterDirectory(pathtoenter);
				break;
			case 4:
				System.out.println("you choose 4");
				System.out.println("input the directory name that you want to list:");
				String pathtolist=sc.next();
				new ListDirectory(pathtolist);
				break;
			case 5:
				System.out.println("you choose 5");
				System.out.println("input the source file and the target file:");
				String sourcef=sc.next();
				String destf=sc.next();
				CopyFile f=new CopyFile();
				f.CopeFile(sourcef, destf);
				break;
			case 6:
				System.out.println("you choose 6");
				CopyDirectory copydirectory=new CopyDirectory();
				System.out.println("input the source directory:");
				String sourcedir=sc.next();
				System.out.println("input the target directory:");
				String destdir=sc.next();
				copydirectory.copyFolder(sourcedir, destdir);
				copydirectory.Success();	
				break;
			case 7:
				System.out.println("you choose 7");
				new FileEncAndDec();
				
				break;
			case 0:
				System.out.println("you choose to exit");
				sc.close();
				System.exit(0);
			default:
				System.out.print("error");
			}
			a=sc.nextInt();
		}
		
	}
}
