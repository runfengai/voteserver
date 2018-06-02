package com.jarry.chat.util;

import java.io.IOException;

/**
 * 视频截取工具
 *
 * @author Administrator
 */

public class VideoThumbTaker {
    // windows版本测试用
    public static final String FFMPEG_PATH = "E:/ffmpeg.exe";
    public static final String FFMPEG_MP4FILE = "e:/a.mp4";

    // linux版本
    // public static final String FFMPEG_PATH = "E:/ffmpeg.exe";
    // public static final String FFMPEG_MP4FILE = "e:/a.mp4";

    /****
     * 获取指定时间内的图片
     *
     * @param ffmpegApp
     *            :ffmpeg程序地址
     * @param videoFilename
     *            :视频路径
     * @param thumbFilename
     *            :图片保存路径
     * @param width
     *            :图片长
     * @param height
     *            :图片宽
     * @param hour
     *            :指定时
     * @param min
     *            :指定分
     * @param sec
     *            :指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean getThumbWindows(String ffmpegApp,
                                          String videoFilename, String thumbFilename, int width, int height,
                                          int hour, int min, float sec) {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                + ":" + sec, "-t", "0.001", "-f", "mjpeg", "-s", width + "*" + height,
                "-an", thumbFilename);

        Process process;
        try {
            process = processBuilder.start();
            // InputStream stderr = process.getErrorStream();
            // InputStreamReader isr = new InputStreamReader(stderr);
            // BufferedReader br = new BufferedReader(isr);
            // process.waitFor();
            // if (br != null)
            // br.close();
            // if (isr != null)
            // isr.close();
            // if (stderr != null)
            // stderr.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * linux下ffmpeg截图
     *
     * @param inFile
     * @param outFile
     * @param width
     * @param height
     * @return
     */
    public static boolean getThumbLinux(String inFile, String outFile,
                                        int width, int height) {
        String command = "ffmpeg -i " + inFile + " -y -f image2 -ss 0:0:0"
                + " -t 0.001 -s " + width + "x" + height + " " + outFile;
//		String command = "ffmpeg -i " + inFile + " -y -f image2 -ss 1"
//		+ " -t 00:00:01 -s " + width + "x" + height + " " + outFile;
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(command);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
    }

    /**
     * 根据平台类型，截取第一秒图片操作
     * <p>
     * 程序所在目录
     *
     * @param fileName     文件名字
     * @param mediaPicPath 目标图片地址
     */
    public static boolean takeThumb(String fileName, String mediaPicPath) {

        String osType = System.getProperty("os.name").toLowerCase();
        int width = 590;
        int height = 358;

        System.out.println(osType);
        // 判断平台
        if (osType.contains("windows")) {
            String ffmpegPath = "e:/ffmpeg.exe";// 本地工具地址
            try {
                return VideoThumbTaker.getThumbWindows(ffmpegPath, fileName,
                        mediaPicPath, width, height, 0, 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (osType.contains("linux")) {
            boolean transfer = VideoThumbTaker.getThumbLinux(fileName,
                    mediaPicPath, width, height);
            System.out.print(transfer);
        }
        return true;
    }

}
