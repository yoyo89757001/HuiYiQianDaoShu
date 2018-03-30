package com.example.huiyiqiandaotv.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by chenjun on 2017/5/17.
 */

public class FileUtil {
    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATH = "ruitongPD";

    /**
     * 是否支持SDCard
     */
    public static boolean isSupportSDCard() {
        return Environment.getExternalStorageDirectory().exists();
    }
    // 创建一个临时目录，用于复制临时文件，如assets目录下的离线资源文件
    public static String createTmpDir(Context context) {
        String sampleDir = "dgxxxx";
        String tmpDir = Environment.getExternalStorageDirectory().toString() + "/" + sampleDir;
        if (!com.example.huiyiqiandaotv.tts.util.FileUtil.makeDir(tmpDir)) {
            tmpDir = context.getExternalFilesDir(sampleDir).getAbsolutePath();
            if (!com.example.huiyiqiandaotv.tts.util.FileUtil.makeDir(sampleDir)) {
                throw new RuntimeException("create model resources dir failed :" + tmpDir);
            }
        }
        return tmpDir;
    }
    /**
     * 检测文件或者路径是否存在
     * <p>
     * 可以给值为Null，如果给值null,判断路径是否存在
     */

    public static boolean isExists(String path, String fileName) {
        if (null == path && null == fileName) {
            return false;
        }
        String name;
        name = SDPATH + File.separator + path;
        File file = new File(name);
        if (!file.exists()) {
            file.mkdirs();
        }
        File fileNmae = new File(name, fileName);
        return fileNmae.exists();
    }

    public static boolean isExists(String path) {
        if (null == path) {
            return false;
        }
        String name;

        name = SDPATH + File.separator + path;

        File file = new File(name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.exists();
    }

    /**
     * 检查SD卡是否可用
     */
    public static boolean isAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }


    /***
     *保存bitmap对象到文件中
     * @param bm
     * @param path
     * @param quality
     * @return
     */
    public static boolean saveBitmap2File(Bitmap bm, String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            return false;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != bm) {
                if (!bm.isRecycled()) {
                    bm.recycle();
                }
                bm = null;
            }
        }
    }
}
