package com.clb.api;

import com.clb.dao.AuthorityRepository;
import com.clb.dao.FileUploadDao;
import com.clb.entity.FileUpload;
import com.clb.entity.User1;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


@Controller
@Slf4j
public class UploadController {

    @Value("${file.upload.path}")
    private String path;

    @Value("${file.upload.temporarypath}")
    private String temporarypath;

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

    @PostMapping("/saveimg")
    public String saveimg(HttpServletRequest request,@RequestParam( value="files",required=false)MultipartFile[] files) throws IllegalStateException, IOException {
        StringBuffer message = new StringBuffer();
        String houzui = "";
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;
        if(files.length > 0){
            for (MultipartFile file : files) {
                FileUpload newFile = new FileUpload();
                String fileName = file.getOriginalFilename();
                String filePath = courseFile + temporarypath + "/" + year + "/" + month + "/" + day + "/" + fileName;
                File dest = new File(filePath);
                if  (!dest.exists()  && !dest.isDirectory())
                {
                    dest.mkdirs();
                }
                Files.copy(file.getInputStream(), dest.toPath(),REPLACE_EXISTING);
                file.getInputStream().close();
            }
        }
        return "html/show";
    }
    @Autowired
    private DocumentConverter converter;  //用于转换

    @ResponseBody
    @RequestMapping("testPreview")
    public void toPdfFile(HttpServletResponse response) {
        File file = new File("D:\\testMyDoc\\doc\\test.docx");//需要转换的文件
        try {
            File newFile = new File("D:/testMyDoc");//转换之后文件生成的地址
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            String savePath="D:/testMyDoc/"; //pdf文件生成保存的路径
            String fileName="JCccc"+ UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
            String fileType=".pdf"; //pdf文件后缀
            String newFileMix=savePath+fileName+fileType;  //将这三个拼接起来,就是我们最后生成文件保存的完整访问路径了

            //文件转化
            converter.convert(file).to(new File(newFileMix)).execute();
            //使用response,将pdf文件以流的方式发送的前端浏览器上
            ServletOutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(new File(newFileMix));// 读取文件
            int i = IOUtils.copy(in, outputStream);   // copy流数据,i为字节数
            in.close();
            outputStream.close();
            System.out.println("流已关闭,可预览,该文件字节大小："+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/mp3")
    public void mp3(HttpServletResponse response, HttpServletRequest request) {

        String database = path + "test.mp3";
        // 文件目录（偷懒没有改变量，名称，此处和FTP没关系，就是文件的绝对路径）
        // 也就是 File music = new File("C:\Users\Administrator\AppData\Local\Temp\a.mp3");

        File music = new File(database);
        String range = request.getHeader("Range");
        //开始下载位置
        long startByte = 0;
        //结束下载位置
        long endByte = music.length() - 1;
        //有range的话
        if (range != null && range.contains("bytes=") && range.contains("-")) {
            range = range.substring(range.lastIndexOf("=") + 1).trim();
            String ranges[] = range.split("-");
            try {
                //判断range的类型
                if (ranges.length == 1) {
                    //类型一：bytes=-2343
                    if (range.startsWith("-")) {
                        endByte = Long.parseLong(ranges[0]);
                    }
                    //类型二：bytes=2343-
                    else if (range.endsWith("-")) {
                        startByte = Long.parseLong(ranges[0]);
                    }
                }
                //类型三：bytes=22-2343
                else if (ranges.length == 2) {
                    startByte = Long.parseLong(ranges[0]);
                    endByte = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException e) {
                startByte = 0;
                endByte = music.length() - 1;
            }
        }
        //要下载的长度
        long contentLength = endByte - startByte + 1;
        //文件名
        String fileName = music.getName();
        //文件类型
        String contentType = request.getServletContext().getMimeType(fileName);
        //各种响应头设置
        //参考资料：https://www.ibm.com/developerworks/cn/java/joy-down/index.html
        //坑爹地方一：看代码
        response.setHeader("Accept-Ranges", "bytes");
        //坑爹地方二：http状态码要为206
        response.setStatus(206);
        response.setContentType(contentType);
        response.setHeader("Content-Type", contentType);
        //这里文件名换你想要的，inline表示浏览器直接实用（我方便测试用的）
        //参考资料：http://hw1287789687.iteye.com/blog/2188500
        // response.setHeader("Content-Disposition", "inline;filename=test.mp3");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        //坑爹地方三：Content-Range，格式为
        // [要下载的开始位置]-[结束位置]/[文件总大小]
        response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + music.length());
        BufferedOutputStream outputStream = null;
        RandomAccessFile randomAccessFile = null;
        //已传送数据大小
        long transmitted = 0;
        try {
            randomAccessFile = new RandomAccessFile(music, "r");
            outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[4096];
            int len = 0;
            randomAccessFile.seek(startByte);
            //坑爹地方四：判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
            //不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
            while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                transmitted += len;
                //停一下，方便测试，用的时候删了就行了
                Thread.sleep(10);
            }
            //处理不足buff.length部分
            if (transmitted < contentLength) {
                len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                outputStream.write(buff, 0, len);
                transmitted += len;
            }
            outputStream.flush();
            response.flushBuffer();
            randomAccessFile.close();
            System.out.println("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
