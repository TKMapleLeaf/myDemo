package com.example.moments.http.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.moments.MyApplication;
import com.example.moments.util.LogUtil;
import com.example.moments.util.NetWorkUtil;
import com.example.moments.util.ThreadUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class CacheManager {

    public static final String TAG = "CacheManager";


    // wifi缓存时间为5分钟
    private static long wifi_cache_time = 24 * 60 * 60 * 1000;
    // 其他网络环境为1小时
    private static long other_cache_time = 24 * 60 * 60 * 1000;

    //max cache size 10mb
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10;

    private static final int DISK_CACHE_INDEX = 0;

    private static final String CACHE_DIR = "responses";

    private DiskLruCache mDiskLruCache;

    private volatile static CacheManager mCacheManager;

    public static CacheManager getInstance() {
        if (mCacheManager == null) {
            synchronized (CacheManager.class) {
                if (mCacheManager == null) {
                    mCacheManager = new CacheManager();
                }
            }
        }
        return mCacheManager;
    }


    /**
     public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize)
     open()方法接收四个参数，第一个参数指定的是数据的缓存地址，第二个参数指定当前应用程序的版本号，
     第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，第四个参数指定最多可以缓存多少字节的数据。

     需要注意的是，每当版本号改变，缓存路径下存储的所有数据都会被清除掉，
     因为DiskLruCache认为当应用程序有版本更新的时候，所有的数据都应该从网上重新获取。
     */
    private CacheManager() {
        File diskCacheDir = getDiskCacheDir(MyApplication.applicationContext, CACHE_DIR);
        if (!diskCacheDir.exists()) {
            boolean b = diskCacheDir.mkdirs();
        }
        if (diskCacheDir.getUsableSpace() > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir
                        , getAppVersion(MyApplication.applicationContext)
                        , 1
                        , DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步设置缓存
     *
     * 写入的操作是借助DiskLruCache.Editor这个类完成的。
     * 需要调用DiskLruCache的edit()方法来获取实例
     */
    public void putCache(String key, String value) {
        if (mDiskLruCache == null) return;
        OutputStream os = null;
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(encryptMD5(key));
            if (editor != null){
                os = editor.newOutputStream(DISK_CACHE_INDEX);
                os.write(value.getBytes());
                os.flush();
                editor.commit();
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 异步设置缓存
     */
    public void setCache(final String key, final String value) {
        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                LogUtil.e(TAG,"setCache == " + Thread.currentThread().getName());
                putCache(key, value);
            }
        });
    }

    /**
     * 同步获取缓存
     *
     * 借助DiskLruCache的get()方法实现的
     * 调用它的getInputStream()方法就可以得到缓存文件的输入流了。
     * 同样地，getInputStream()方法也需要传一个index参数，这里传入0就好。
     */
    public String getCache(String key) {
        if (mDiskLruCache == null) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(encryptMD5(key));
            if (snapshot != null) {
                fis = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                byte[] data = bos.toByteArray();
                return new String(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 异步获取缓存
     */
    public void getCache(final String key, final ThreadCacheCall callback) {

        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                LogUtil.e(TAG,"getCache runOnSubThread() == " + Thread.currentThread().getName());
                final String cache = getCache(key);
                ThreadUtil.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e(TAG,"getCache runOnMainThread() == " + Thread.currentThread().getName());
                        callback.onCache(cache);
                    }
                });
            }
        });
    }

    /**
     * 移除缓存
     * 移除缓存主要是借助DiskLruCache的remove()方法实现的
     *
     * DiskLruCache会根据我们在调用open()方法时设定的缓存最大值来自动删除多余的缓存。
     * 只有你确定某个key对应的缓存内容已经过期，需要从网络获取最新数据的时候才应该调用remove()方法来移除缓存。
     */
    public boolean removeCache(String key) {
        if (mDiskLruCache != null) {
            try {
                return mDiskLruCache.remove(encryptMD5(key));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        if (context == null)
            return false;
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);  //file目录下  不是在cache目录下
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 判断缓存是否已经失效
     */
    public static boolean isCacheDataFailure(Context context, File data) {
//        File data = context.getFileStreamPath(cachefile); //file目录下
        LogUtil.e(TAG , " File data == " +data.exists() + "  " + data);
        if (!data.exists()) {

            return false;
        }
        long existTime = System.currentTimeMillis() - data.lastModified();
        LogUtil.e(TAG , " existTime == " +existTime +  "   " + NetWorkUtil.isWifiOpen());
        boolean failure = false;
        if (NetWorkUtil.isWifiOpen()) {
            failure = existTime < wifi_cache_time;
        } else {
            failure = existTime < other_cache_time;
        }
        return failure;
    }

    /**
     * 获取缓存目录文件
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取缓存文件
     */
    public File getDiskCachePath(Context context, String key) {
        String cachePath = getDiskCacheDir(context,CACHE_DIR).getPath();
        //这里直接写死0，因为open创建缓存文件的时候传参的缓存文件数量是1 ，如果参数大于1，则会有多个缓存文件，请在DiskLruCache中写方法
        return new File(cachePath + File.separator + (encryptMD5(key)+".0"));
    }

    /**
     * 对字符串进行MD5编码
     */
    public static String encryptMD5(String string) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获取APP版本号
     */
    private int getAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? 1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
