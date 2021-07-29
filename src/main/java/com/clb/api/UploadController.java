package com.clb.api;

import com.clb.dto.FileData;
import com.clb.dto.FxData;
import com.clb.entity.FileUpload;
import com.clb.entity.ProjectAttachment;
import com.clb.service.ProjectService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


@RestController
@Slf4j
public class UploadController {

    @Value("${file.upload.path}")
    private String path;

    @Value("${file.upload.temporarypath}")
    private String temporarypath;

    @Resource
    ProjectService projectService;

    @GetMapping("/")
    public String uploadPage() {
        return "html/upload";
    }
 
    @PostMapping("/upload")
    public String create(HttpServletResponse response,HttpServletRequest request) throws IOException {
        StringBuffer message = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;



        String[] filename = request.getParameterValues("name");
        for(int i=0;i<filename.length;i++){
            String filePathNow = courseFile + temporarypath + "/" + year + "/" + month + "/" + day + "/" + filename[i];
            String filePathNew = courseFile + path + "/" + year + "/" + month + "/" + day + "/" + filename[i];
            File dest = new File(filePathNew);
            if  (!dest.exists()  && !dest.isDirectory())
            {
                dest.mkdirs();
            }
            InputStream in = new FileInputStream(new File(filePathNow));// 读取文件
            Files.copy(in, dest.toPath(),REPLACE_EXISTING);
            in.close();
            message.append("Upload file success : " + dest.getAbsolutePath()).append(dest.length()).append(filePathNow).append("<br>");
        }
        return "html/show";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(String data,int projectId) throws IOException {
        StringBuffer message = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        Gson gson = new Gson();
        List<FileData> list = gson.fromJson(data, new TypeToken<List<FileData>>(){}.getType());

        for(int i=0;i<list.size();i++){
            int index = list.get(i).getName().lastIndexOf('.');

            ProjectAttachment projectAttachment = new ProjectAttachment();
            projectAttachment.setPFolder(list.get(i).getFolderVal());
            projectAttachment.setRefId(projectId);
            // projectAttachment.setFileName(list.get(i).getName().substring(0,index));
            projectAttachment.setFileName(list.get(i).getName());

            projectAttachment.setFileUrl(year + "/" + month + "/" + day + "/" + list.get(i).getName());
            projectAttachment.setFileSize(Integer.parseInt(list.get(i).getSize()));
            projectAttachment.setFileExt(list.get(i).getName().substring(index+1,list.get(i).getName().length()));
            projectAttachment.setFileUploadtime((int) (System.currentTimeMillis()/1000));
            projectService.saveFileUpload(projectAttachment);

            String filePathNow =   temporarypath  + year + "/" + month + "/" + day + "/" + list.get(i).getName();
            String filePathNew =   path + year + "/" + month + "/" + day + "/" + list.get(i).getName();
            File dest = new File(filePathNew);
            if  (!dest.exists()  && !dest.isDirectory())
            {
                dest.mkdirs();
            }
            InputStream in = new FileInputStream(new File(filePathNow));// 读取文件
            Files.copy(in, dest.toPath(),REPLACE_EXISTING);
            in.close();
        }


        return "";
    }

    @PostMapping("/saveFile")
    public String saveimg(HttpServletRequest request,@RequestParam( value="files",required=false)MultipartFile[] files) throws IllegalStateException, IOException {
        StringBuffer message = new StringBuffer();
        String houzui = "";
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        if(files.length > 0){
            for (MultipartFile file : files) {
                FileUpload newFile = new FileUpload();
                String fileName = file.getOriginalFilename();
                String filePath = temporarypath + "/" + year + "/" + month + "/" + day + "/" + fileName;
                File dest = new File(filePath);
                if  (!dest.exists()  && !dest.isDirectory())
                {
                    dest.mkdirs();
                }
                Files.copy(file.getInputStream(), dest.toPath(),REPLACE_EXISTING);
                file.getInputStream().close();
            }
        }
        return "success";
    }

    @RequestMapping("/download/file")
    @ResponseBody
    public void downLoadFile(HttpServletRequest request,HttpServletResponse response, String url) throws IOException {

        downloadFile(path+url, request,response);


    }


    public void downloadFile(String address,HttpServletRequest request, HttpServletResponse response) throws IOException {

        OutputStream output = response.getOutputStream();
        String[] dir = address.split("/");
        String fileName = dir[dir.length - 1];
        String filenamedisplay = fileName;
        File downloadFile = new File(address);
        response.addHeader("Content-Length",""+downloadFile.length());

        String userAgent = request.getHeader("User-agent");
        byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
        filenamedisplay =new String(bytes,"ISO-8859-1");
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filenamedisplay));
        int BUFSIZE =65536;
        FileInputStream fis=null;
        try{
            fis=new FileInputStream(address);
            int s;
            byte[] buf =new byte[BUFSIZE];
            while((s = fis.read(buf))>-1){
                output.write(buf,0, s);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            output.flush();
            fis.close();
            output.close();
        }

    }

}
