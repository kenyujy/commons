package upload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

public class UploadTest {
	
	//文件上传必须是POST请求 post表单
	
	/* <form action="" entype="multipart/form-data" method="post">
	 * 	<input type="file" />
	 * 	<input type="submit" value="upload" />
	 * </form>
	 *
	 * 文件上传以流的方式
	 */
	public void upload(HttpServletRequest request) throws FileUploadException {
		
		boolean b= ServletFileUpload.isMultipartContent(request);
		if (b) {
			// new 一个 FileItemFactory
			FileItemFactory fileItemFactory= new DiskFileItemFactory();
			
			ServletFileUpload uploadfile= new ServletFileUpload(fileItemFactory);
			//把文件上传请求解释成 FileItem 的 List
			List<FileItem> fileList= uploadfile.parseRequest(request);
			//解析每一项的类容
			for (FileItem fileItem : fileList){
				if(fileItem.isFormField()) {
					//donothing
				}
				else { //是真实的文件
					try {
						InputStream is= fileItem.getInputStream();//获取输入流
						//获取输入流
						OutputStream os= new FileOutputStream("/user/ken/data/"+fileItem.getName());
						IOUtils.copy(is, os); //把输入流写到输出流
						os.close();
						is.close();
					} catch (IOException e) {
						e.printStackTrace();} 
				}
			}
		}
	}
		
			//ServletInputStream inputStream= request.getInputStream();
			//byte[] temp= new byte[1024];
			//while(inputStream.read()!= -1) {}
			//IOUtils.toString(inputStream, "UTF-8");
		
}
